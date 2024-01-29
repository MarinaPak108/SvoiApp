package com.svoiapp.component;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class DocumentNameHashmap {
    private HashMap<String, List<String>> hashMap;


    @PostConstruct
    public void init() {
        // Add key-value pairs to the HashMap
        List<String> list1 = new ArrayList<>(Arrays.asList("notVisaF4.docx", "/Services/Visa/"));
        List<String> list2 = new ArrayList<>(Arrays.asList("visaF4.docx", "/Services/Visa/"));
        List<String> list3 = new ArrayList<>(Arrays.asList("residence.docx", "/Services/Accomodation/"));
        // Initialize the HashMap with pre-filled values
        hashMap = new HashMap<>();
        hashMap.put("other",list1);
        hashMap.put("f4", list2);
        hashMap.put("res", list3);
    }

    public HashMap<String, List<String>> getHashMap() {
        return hashMap;
    }
}
