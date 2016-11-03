package com.github.schedulejob.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.schedulejob.common.RetCode;
import com.github.schedulejob.common.Response;
import com.github.schedulejob.common.RetCodeConst;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 处理Http(JSON)
 * Thrift接口调用返回值
 * Created by lvhao on 2016-7-6.
 */
public class ResponseUtils {

    // 本地response类型
    private static final Class<Response> LOCAL_RESPONSE_CLASS = Response.class;

    // 字段类型
    private enum FieldType {

        // response 支持的类型
        RESP_STATUS("respStatus","respstatus","status"),

        // respStatus支持的类型
        RET_MSG("msg","errorMsg","retMsg"),
        RET_CODE("code","errorCode","retCode");

        private Set<String> supportedSet = Sets.newHashSet();
        FieldType(String... fieldName){
            supportedSet.addAll(Arrays.asList(fieldName));
        }
    }

    /**
     * 判断集合是否有数据
     * @param collection
     * @return
     */
    public static boolean hasData(Collection<?> collection){
        return !(Objects.isNull(collection) || collection.isEmpty());
    }

    /**
     * retCode 是否和Biz.SUCCESS相等
     * @param retCode
     * @return
     */
    public static boolean isBizSucCode(String retCode){
        return Objects.equals(RetCodeConst.OK.getCode(),retCode);
    }

    public static <R,L> void doneCallback(L lResp,R rResp,BiConsumer<L,R> consumer){
        consumer.accept(lResp,rResp);
    }

    /**
     * 处理json数据
     * @param resp
     * @param <L>
     * @param <R> predicate 和 resp 相同类型
     * @return
     */
    private static <L,R> L handleJsonResp(R resp,Predicate<R> retCodeJudge){
        JsonNode jsonNode = (JsonNode) resp;

        Optional<String> codeOptional = getJsonFieldValue(jsonNode, FieldType.RET_CODE);
        Optional<String> msgOptional = getJsonFieldValue(jsonNode, FieldType.RET_MSG);
        Integer code = codeOptional.map(Integer::new).orElse(Integer.MIN_VALUE);
        String msg = msgOptional.orElse("未找到msg相关字段或者msg为空");

        // 返回成功情况
        // 1.返回码判断器不存在且默认0为调用成功
        // 2.返回码判断器存在且符合判断器
        boolean isSucWithDefault = Objects.isNull(retCodeJudge) && Objects.equals(code,0);
        boolean isSucWithCustomJudge = Objects.nonNull(retCodeJudge) && retCodeJudge.test(resp);

        if (isSucWithDefault || isSucWithCustomJudge){
            return (L) buildSpecialBizStatusByClazz(LOCAL_RESPONSE_CLASS,RetCodeConst.OK);
        }

        RetCode customCode = null;
        if (Objects.nonNull(msg)) {
            String msgDetail = MessageFormat.format("({0}){1}",String.valueOf(code),String.valueOf(msg));
            customCode = RetCode.of(RetCodeConst.ERROR.getCode(),msgDetail);
        }
        return (L) buildSpecialBizStatusByClazz(LOCAL_RESPONSE_CLASS,customCode);
    }

    /**
     * 处理Thrift resp
     * 注意此时predicate为RT类型
     * @param resp
     * @param retCodeJudge
     * @param <L> 本地对象类型
     * @param <R> 返回对象类型
     * @param <RT> 返回对象TResponse类型
     * @return
     */
    private static <L,R,RT> L handleThriftResp(R resp, Predicate<RT> retCodeJudge){

        // 获取 TRespStatus 值
        Field[] fields = resp.getClass().getDeclaredFields();

        // 获取TResponse
        RT objRespStatus = null;
        if(matchByType(fields, FieldType.RESP_STATUS)){
            Field respStatusField = getSpecialFieldByType(fields, FieldType.RESP_STATUS);
            objRespStatus = (RT)invokeReadMethod(resp,respStatusField);
        }else if(matchByType(fields, FieldType.RET_CODE, FieldType.RET_MSG)){
            objRespStatus = (RT)resp;
        }
        checkNotNull(objRespStatus,"未匹配到FieldType");
        return (L)handleRespStatusIfBizFail(objRespStatus, LOCAL_RESPONSE_CLASS, retCodeJudge);
    }

    /**
     * 构造本地Response类,使用predicate来判断调用成功code
     * @param resp
     * @param retCodeJudge
     * @param <R>
     * @param <L>
     * @return
     */
    public static <R,L> L buildRetCodeOfLocalResponse(R resp, Predicate<R> retCodeJudge){
        // 如果 resp为空 则返回bizFail
        if(Objects.isNull(resp)){
            return (L) buildSpecialBizStatusByClazz(LOCAL_RESPONSE_CLASS,RetCodeConst.ERROR);
        }
        return  (resp instanceof JsonNode)
                ? handleJsonResp(resp, retCodeJudge)
                : handleThriftResp(resp, retCodeJudge);
    }

    /**
     * 处理Response里respStatus
     * 如果Response == null || respStatus == null || respStatus.retCode != 0
     * 返回 buildSpecialBizStatusByClazz Method 结果
     * @param resp
     * @param <R> 远程对象
     * @param <L> 本地对象
     * @return
     */
    public static <R,L> L buildRetCodeOfLocalResponse(R resp){

        // 如果 resp为空 则返回bizFail
        if(Objects.isNull(resp)){
            return (L) buildSpecialBizStatusByClazz(LOCAL_RESPONSE_CLASS,RetCodeConst.ERROR);
        }
        return  (resp instanceof JsonNode)
                ? handleJsonResp(resp, null)
                : handleThriftResp(resp,null);
    }

    /**
     * 获取指定json字段值
     * @param jn
     * @param fieldType
     * @return
     */
    private static Optional<String> getJsonFieldValue(JsonNode jn, FieldType fieldType){
        Set<String> supportedNameSet = fieldType.supportedSet;
        List<String> results = supportedNameSet.stream()
                .filter(name -> jn.has(name))
                .collect(Collectors.toList());
        return results.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(jn.get(results.get(0)).asText());
    }

    /**
     * 检测当前class 是ThriftResponse 还是 TRespStatus
     * @param fields
     * @return
     */
    private static boolean matchByType(Field[] fields,FieldType... typeArray){
        checkArgument(0 == typeArray.length,"缺少类别参数");
        Set<String> supportedNameSet = Sets.newHashSet();
        Arrays.asList(typeArray)
                .stream()
                .forEach(fieldType -> supportedNameSet.addAll(fieldType.supportedSet));
        boolean result = Arrays.asList(fields)
                .stream()
                .anyMatch(field -> supportedNameSet.contains(field.getName()));
        return result;
    }

    /**
     * 构造默认类型的实例
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T getDefaultInstance(Class<T> clazz){
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 调用set方法 设置值v
     * @param t
     * @param field
     * @param v
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void invokeWriteMethod(T t,Field field,Object v) {
        if(!field.isAccessible()){
            field.setAccessible(true);
        }
        try {
            field.set(t,v);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用域 读取方法
     * @param t
     * @param field
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    private static <T> Object invokeReadMethod(T t,Field field) {
        if(!field.isAccessible()){
            field.setAccessible(true);
        }
        try {
            return field.get(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定类型的 Field
     * @param fields
     * @param type {@link FieldType}
     * @return
     */
    private static Field getSpecialFieldByType(Field[] fields, FieldType type){

        // 不存在 属性集合为空 情况
        Predicate<Field[]> nonEmptyField = fieldArray -> (Objects.isNull(fieldArray) || fieldArray.length == 0);
        checkArgument(nonEmptyField.test(fields),"fields不包含属性");

        final Set<String> supportedNameSet = type.supportedSet;

        for(Field field : fields){
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            if(supportedNameSet.contains(field.getName())){
                return field;
            }
        }
        checkArgument(false,"返回值中不包含属性"+type.supportedSet.toString());
        return null;
    }

    /**
     * 获取默认失败对象
     * @param clazz 指定的类型clazz
     * @param retCode 指定的业务常量码
     * @param <L>
     * @return clazz 实例
     */
    private static <L> L buildSpecialBizStatusByClazz(Class<L> clazz, RetCode retCode){
        L result = getDefaultInstance(clazz);

        // 获取所有pub属性
        Field[] fields = clazz.getDeclaredFields();

        // 反射获取 msg,code 变量域
        Field codeField = getSpecialFieldByType(fields, FieldType.RET_CODE);
        Field msgField = getSpecialFieldByType(fields, FieldType.RET_MSG);

        // 设置默认值
        invokeWriteMethod(result,codeField,retCode.getCode());
        invokeWriteMethod(result,msgField,retCode.getMsg());
        return result;
    }

    /**
     * 调用方返回的状态码为失败或者为null时进行处理
     * @param thriftRespStatusInstance 服务方返回的 TRespStatus instance
     * @param localResponseClazz 本地对象Response.class
     * @param thriftRespStatusInstance
     * @param localResponseClazz
     * @param retCodeJudge
     * @param <RT>
     * @param <L>
     * @return
     */
    private static <RT,L> L handleRespStatusIfBizFail(
            RT thriftRespStatusInstance,
            Class<L> localResponseClazz,
            Predicate<RT> retCodeJudge){

        // 返回值 null 直接构造业务失败对象
        if(Objects.isNull(thriftRespStatusInstance)){
            return buildSpecialBizStatusByClazz(localResponseClazz,RetCodeConst.ERROR);
        }

        // 获取所有pub属性
        Field[] fields = thriftRespStatusInstance.getClass().getDeclaredFields();

        // 反射获取 msg,code 变量域
        Field codeField = getSpecialFieldByType(fields, FieldType.RET_CODE);
        Field msgField = getSpecialFieldByType(fields, FieldType.RET_MSG);

        // 指定属性字段不存在 返回 业务失败对象
        if(Objects.isNull(codeField) || Objects.isNull(msgField)){
            return buildSpecialBizStatusByClazz(localResponseClazz,RetCodeConst.ERROR);
        }

        // 获取返回值
        Object retCode = invokeReadMethod(thriftRespStatusInstance,codeField);
        int thriftRetCode = Integer.parseInt(String.valueOf(retCode));

        // 返回成功情况
        // 1.返回码判断器不存在默认0为调用成功
        // 2.返回码判断器存在且符合判断器
        boolean isSucWithDefault = Objects.isNull(retCodeJudge)
                && Objects.equals(thriftRetCode,RetCodeConst.ERROR.getCode());
        boolean isSucWithCustomJudge = Objects.nonNull(retCodeJudge)
                && retCodeJudge.test(thriftRespStatusInstance);

        if (isSucWithDefault || isSucWithCustomJudge){
            return (L) buildSpecialBizStatusByClazz(LOCAL_RESPONSE_CLASS,RetCodeConst.ERROR);
        }

        // 返回失败的业务码
        RetCode customCode = null;
        Object msg = invokeReadMethod(thriftRespStatusInstance,msgField);
        String msgStr = "("+retCode+")"+String.valueOf(msg);
        customCode = RetCode.of(RetCodeConst.ERROR.getCode(),msgStr);
        return buildSpecialBizStatusByClazz(localResponseClazz,customCode);
    }
}
