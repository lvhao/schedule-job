package com.github.schedulejob.config.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-29 11:36
 */
public class CustomPropertyPreFilter<T> extends SimplePropertyPreFilter {
    private Predicate<T> propertyFilterPredicate;

    public CustomPropertyPreFilter(Class<?> clazz, Predicate<T> propertyFilterPredicate, String... properties) {
        super(clazz, properties);
        this.propertyFilterPredicate = propertyFilterPredicate;
    }

    @Override
    public boolean apply(JSONSerializer serializer, Object source, String name) {
        Class<?> clazz = this.getClazz();
        if (Objects.nonNull(clazz)
                && Objects.nonNull(source)
                && clazz.isInstance(source)
                && this.getExcludes().contains(name)
                && Objects.nonNull(propertyFilterPredicate)) {
            try {
                return propertyFilterPredicate.test((T)source);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.apply(serializer, source, name);
    }
}
