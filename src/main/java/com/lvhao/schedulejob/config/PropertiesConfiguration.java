package com.lvhao.schedulejob.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能简单描述
 * TODO http://stackoverflow.com/questions/25855795/spring-boot-and-multiple-external-configuration-files
 *
 * @author: lvhao
 * @since: 2016-7-29 19:03
 */
public class PropertiesConfiguration {
    @Bean
    public PropertyPlaceholderConfigurer properties() {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setIgnoreResourceNotFound(true);

        final List<Resource> resourceLst = new ArrayList<Resource>();

        resourceLst.add(new ClassPathResource("config/datasource.yml"));
        resourceLst.add(new FileSystemResource("datasource.yml"));
        resourceLst.add(new ClassPathResource("config/datasource.yml"));

        ppc.setLocations(resourceLst.toArray(new Resource[]{}));

        return ppc;
    }
}
