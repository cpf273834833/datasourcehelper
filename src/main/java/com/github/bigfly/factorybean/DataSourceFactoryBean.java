package com.github.bigfly.factorybean;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.FactoryBean;

import javax.sql.DataSource;

/**
 * @author chengpengfei
 * @Desc DataSourceFactoryBean
 */
public class DataSourceFactoryBean implements FactoryBean<DataSource> {
    private DataSource dataSource;

    public DataSourceFactoryBean(){
        super();
    }

    public DataSourceFactoryBean(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public DataSource getObject() throws Exception {
        return dataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return DruidDataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
