package com.svoiapp.component;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SchoolTypeHashmap {
    private HashMap<String, String> hashMap;

    @PostConstruct
    public void init() {
        // Initialize the HashMap with pre-filled values
        hashMap = new HashMap<>();
        hashMap.put("сад", "@35");
        hashMap.put("начальная", "@36");
        hashMap.put("средняя", "@37");
        hashMap.put("старшая", "@38");
        // Add more pre-filled key-value pairs as needed
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }
}
