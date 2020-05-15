package com.github.bigfly.factorybean;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author chengpengfei
 */
public class SqlSessionTemplateFactoryBean implements FactoryBean<SqlSessionTemplate> {
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSessionTemplateFactoryBean(){
        super();
    }

    public SqlSessionTemplateFactoryBean(SqlSessionTemplate sqlSessionTemplate){
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    @Override
    public SqlSessionTemplate getObject() throws Exception {
        return sqlSessionTemplate;
    }

    @Override
    public Class<?> getObjectType() {
        return SqlSessionTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
