package com.indeed.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    static Properties properties;

    public PropertyReader(){
        loadAllProperties();
    }

    public void loadAllProperties(){
        properties = new Properties();
        try{
            String fileName = System.getProperty("user.dir")+"/src/main/resources/config.properties";
            properties.load(new FileInputStream(fileName));
        }catch (IOException e){
            throw new RuntimeException("Something went wrong when loading the property file, make sure you have a config.properties in your resource folder!");
        }
    }

    public static String readItem(String propertyName){
        return properties.getProperty(propertyName);
    }

}
