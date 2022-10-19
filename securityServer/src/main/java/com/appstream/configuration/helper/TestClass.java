package com.appstream.configuration.helper;

import com.appstream.configuration.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestClass {

    @Autowired
    private ConfigProperties properties;

    public void printSomething() {
        System.out.println(properties.getKey());
    }

}
