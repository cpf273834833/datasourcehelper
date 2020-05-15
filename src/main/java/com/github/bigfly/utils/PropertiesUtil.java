package com.github.bigfly.utils;

import com.github.bigfly.properties.DataSourceProperties;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author chengpengfei
 */
public class PropertiesUtil {

    public static Map<String, DataSourceProperties> getDataSourceProperties(String prefix){

        HashMap<String, DataSourceProperties> map = new HashMap<>();

        Properties pro = new Properties();

        InputStream in = PropertiesUtil.class.getResourceAsStream("/application.properties");

        try{
            pro.load(in);
            Enumeration en = pro.propertyNames();
            Map<String,String> propertiesMap = new HashMap<>();
            while (en.hasMoreElements()){
                String key = (String)en.nextElement();
                if(key.startsWith(prefix)){
                    String k = key.replaceFirst(prefix,"");
                    String[] split = k.split("\\.");
                    if(split.length == 2){
                        propertiesMap.put(k,pro.getProperty(key));
                    }
                }
            }
            for(Map.Entry<String,String> entry : propertiesMap.entrySet()){
                String key = entry.getKey();
                String[] split = key.split("\\.");
                DataSourceProperties properties = map.get(split[0]);
                if(properties == null){
                    properties = new DataSourceProperties();
                    map.put(split[0],properties);
                }

                switch (split[1]){
                    case "url":
                        properties.setUrl(propertiesMap.get(key));
                        break;
                    case "username":
                        properties.setUserName(propertiesMap.get(key));
                        break;
                    case "password":
                        properties.setPassword(propertiesMap.get(key));
                        break;
                    case "driverClassName":
                        properties.setDriverClassName(propertiesMap.get(key));
                        break;
                    case "basePackages":
                        properties.setBasePackages(propertiesMap.get(key));
                        break;
                    case "mapperHelperRef":
                        properties.setMapperHelperRef(propertiesMap.get(key));
                        break;
                    case "properties":
                        properties.setProperties(propertiesMap.get(key));
                        break;
                    default:
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return map;
    }
}
