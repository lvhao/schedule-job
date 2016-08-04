package com.github.schedulejob.config.mvc;

import com.github.schedulejob.common.RetCodeConst;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 * 自定义异常返回信息格式
 *
 * @author: lvhao
 * @since: 2016-7-8 13:36
 */
@Configuration
public class CustomErrorAttributesConfig extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String,Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        errorAttributes.put("retCode", RetCodeConst.ERROR);
        errorAttributes.remove("timestamp");
        errorAttributes.remove("error");
        return errorAttributes;
    }
}
