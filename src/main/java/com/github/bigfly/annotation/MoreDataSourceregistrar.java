package com.github.bigfly.annotation;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.bigfly.factorybean.DataSourceFactoryBean;
import com.github.bigfly.factorybean.SqlSessionFactoryFactoryBean;
import com.github.bigfly.factorybean.SqlSessionTemplateFactoryBean;
import com.github.bigfly.properties.DataSourceProperties;
import com.github.bigfly.utils.PropertiesUtil;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;

import java.io.IOException;
import java.util.Map;

/**
 * @author chengpengfei
 */
public class MoreDataSourceregistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, DataSourceProperties> dataSourceMap = PropertiesUtil.getDataSourceProperties("spring.datasource.");

        for (Map.Entry<String, DataSourceProperties> entry : dataSourceMap.entrySet()){
            String dataSourceName = entry.getKey();
            DataSourceProperties dataSourceProperties = entry.getValue();

            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
            dataSource.setUrl(dataSourceProperties.getUrl());
            dataSource.setUsername(dataSourceProperties.getUserName());
            dataSource.setPassword(dataSourceProperties.getPassword());

            BeanDefinitionBuilder dataSourceBuild = BeanDefinitionBuilder.genericBeanDefinition(DataSourceFactoryBean.class);
            GenericBeanDefinition dataSourceBeanDefinition = (GenericBeanDefinition) dataSourceBuild.getBeanDefinition();
            dataSourceBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(dataSource);
            registry.registerBeanDefinition(dataSourceName+"DataSource",dataSourceBeanDefinition);

            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
            try {
                sqlSessionFactoryBean.setMapperLocations(
                        new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/"+dataSourceName+"/*.xml"));
            }catch (IOException e){
                e.printStackTrace();
            }

            BeanDefinitionBuilder sqlSessionFactoryBuild = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactoryFactoryBean.class);
            GenericBeanDefinition sqlSessionFactoryDefinition = (GenericBeanDefinition) sqlSessionFactoryBuild.getBeanDefinition();
            try {
                sqlSessionFactoryDefinition.getConstructorArgumentValues().addGenericArgumentValue(sqlSessionFactoryBean.getObject());
            } catch (Exception e) {
                e.printStackTrace();
            }
            registry.registerBeanDefinition(dataSourceName+"SqlSessionFactory",dataSourceBeanDefinition);

            SqlSessionTemplate sqlSessionTemplate = null;
            try {
                sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
            } catch (Exception e) {
                e.printStackTrace();
            }

            BeanDefinitionBuilder sqlSessionTemplateBuild = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionTemplateFactoryBean.class);
            GenericBeanDefinition sqlSessionTemplateBeanDefinition = (GenericBeanDefinition) sqlSessionTemplateBuild.getBeanDefinition();
            sqlSessionTemplateBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(sqlSessionTemplate);
            registry.registerBeanDefinition(dataSourceName+"SqlSessionTemplate",dataSourceBeanDefinition);
        }
    }
}
