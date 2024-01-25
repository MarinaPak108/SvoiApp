package com.svoiapp.component;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class VisaReasonHashmap {
    private HashMap<String, String> hashMap;


    @PostConstruct
    public void init() {
        // Initialize the HashMap with pre-filled values
        hashMap = new HashMap<>();
        hashMap.put("впервые", "@1");
        hashMap.put("перевыпуск", "@5");
        hashMap.put("продление", "@2");
        hashMap.put("смена", "@4");
        hashMap.put("получение", "@6");
        hashMap.put("работа", "@0");
        hashMap.put("въезд", "@00");
        hashMap.put("адрес", "@3");
        hashMap.put("данные", "@000");
        // Add more pre-filled key-value pairs as needed
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }
}
