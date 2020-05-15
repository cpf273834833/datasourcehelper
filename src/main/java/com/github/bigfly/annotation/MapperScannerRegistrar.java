package com.github.bigfly.annotation;

import com.github.bigfly.properties.DataSourceProperties;
import com.github.bigfly.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import tk.mybatis.spring.mapper.ClassPathMapperScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author chengpengfei
 */
public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    public static final Logger LOGGER = LoggerFactory.getLogger(MapperScannerRegistrar.class);

    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, DataSourceProperties> dataSourceMap = PropertiesUtil.getDataSourceProperties("spring.datasource.");
        for (Map.Entry<String, DataSourceProperties> entry : dataSourceMap.entrySet()){
            String dataSourceName = entry.getKey();
            DataSourceProperties dataSourceProperties = entry.getValue();

            ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
            if(resourceLoader != null){
                scanner.setResourceLoader(resourceLoader);
            }

            scanner.setSqlSessionTemplateBeanName(dataSourceName+"SqlSessionTemplate");
            scanner.setSqlSessionFactoryBeanName("");

            List<String> basePackages = new ArrayList<>();
            for(String pkg : dataSourceProperties.getBasePackages().split(",")){
                if(StringUtils.hasText(pkg)){
                    basePackages.add(pkg);
                }
            }
            try {
                scanner.setMapperProperties(this.environment);
            }catch (Exception e){
                LOGGER.warn("只有 Spring Boot 环境中可以通过 Environment(配置文件,环境变量,运行参数等方式) 配置通用 Mapper，其他环境请通过 @MapperScan 注解中的 mapperHelperRef 或 properties 参数进行配置!如果你使用 tk.mybatis.mapper.session.Configuration 配置的通用 Mapper，你可以忽略该错误!", e);
            }

            scanner.registerFilters();
            scanner.doScan(StringUtils.toStringArray(basePackages));
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
