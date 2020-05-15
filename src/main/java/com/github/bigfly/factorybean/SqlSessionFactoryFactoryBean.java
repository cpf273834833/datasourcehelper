package com.github.bigfly.factorybean;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author chengpengfei
 */
public class SqlSessionFactoryFactoryBean implements FactoryBean<SqlSessionFactory> {
    private SqlSessionFactory sqlSessionFactory;

    public SqlSessionFactoryFactoryBean(){
        super();
    }

    public SqlSessionFactoryFactoryBean(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public SqlSessionFactory getObject() throws Exception {
        return sqlSessionFactory;
    }

    @Override
    public Class<?> getObjectType() {
        return SqlSessionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
