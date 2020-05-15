package com.github.bigfly.properties;

/**
 * @author chengpengfei
 */
public class DataSourceProperties {
    /**
     * driverClassName
     */
    private String driverClassName;

    /**
     * url
     */
    private String url;

    /**
     * userName
     */
    private String userName;

    /**
     * password
     */
    private String password;

    /**
     * basePackages
     */
    private String basePackages;

    /**
     * basePackages
     */
    private String mapperHelperRef;

    /**
     * basePackages
     */
    private String properties;


    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMapperHelperRef() {
        return mapperHelperRef;
    }

    public void setMapperHelperRef(String mapperHelperRef) {
        this.mapperHelperRef = mapperHelperRef;
    }

    public String getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
