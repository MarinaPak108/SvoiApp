package com.svoiapp.component;

import com.svoiapp.formdata.CreateVisaExtendFormData;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Component
public class VisaFillFormHashmap {

    public HashMap<String, String> createFormHashMap(){
        // Initialize the HashMap with pre-filled values
       HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("@1", " ");
        hashMap.put("@2", " ");
        hashMap.put("@3", " ");
        hashMap.put("@4", " ");
        hashMap.put("@5", " ");
        hashMap.put("@6", " ");
        hashMap.put("@7", " ");
        hashMap.put("@8", " ");
        hashMap.put("@35", " ");
        hashMap.put("@36", " ");
        hashMap.put("@37", " ");
        hashMap.put("@38", " ");
        hashMap.put("@41", " ");
        hashMap.put("@42", " ");
        // school related
        hashMap.put("@schoolname", " ");
        hashMap.put("@schooltel", " ");
        // work related
        hashMap.put("@compname", " ");
        hashMap.put("@compno", " ");
        hashMap.put("@comptel", " ");
        hashMap.put("@position", " ");
        return hashMap;
 }

    //transfer Styring to LocalDate format
    private LocalDate stringToDate (String dateString){
        // Parse the string into a LocalDate object using a formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }

    public HashMap<String, String> preFillHashMap (CreateVisaExtendFormData data, String visaType,  HashMap<String, String> hashMap){
        if(visaType.equals("other")){
            hashMap.put("@0", " ");
            hashMap.put("@00", " ");
            hashMap.put("@000", " ");
            // additional work related
            hashMap.put("@compname*", " ");
            hashMap.put("@compno*", " ");
            hashMap.put("@comptel*", " ");
            hashMap.put("@salary", " ");
        }
        LocalDate birthday = stringToDate(data.getBdate());
        // Get the current date
        LocalDate currentDateTime = LocalDate.now();

        // Format the current date as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = currentDateTime.format(formatter);

        hashMap.put("@surname", data.getSurname());
        hashMap.put("@name", data.getName());
        hashMap.put("@byear", String.valueOf(birthday.getYear()));
        hashMap.put("@bmon", String.valueOf(birthday.getMonthValue()));
        hashMap.put("@bday", String.valueOf(birthday.getDayOfMonth()));
        hashMap.put("@nationality", data.getNationality());
        if(!data.getIdNumber().equals("")) data.setIdNumber("             ");
        hashMap.put("@9", "" + data.getIdNumber().charAt(0));
        hashMap.put("@10", "" + data.getIdNumber().charAt(1));
        hashMap.put("@11", "" + data.getIdNumber().charAt(2));
        hashMap.put("@12", "" + data.getIdNumber().charAt(3));
        hashMap.put("@13", "" + data.getIdNumber().charAt(4));
        hashMap.put("@14", "" + data.getIdNumber().charAt(5));
        hashMap.put("@15", "" + data.getIdNumber().charAt(6));
        hashMap.put("@16", "" + data.getIdNumber().charAt(7));
        hashMap.put("@17", "" + data.getIdNumber().charAt(8));
        hashMap.put("@18", "" + data.getIdNumber().charAt(9));
        hashMap.put("@19", "" + data.getIdNumber().charAt(10));
        hashMap.put("@20", "" + data.getIdNumber().charAt(11));
        hashMap.put("@21", "" + data.getIdNumber().charAt(12));
        hashMap.put("@passno", data.getPassno());
        hashMap.put("@passdate", data.getPassdate());
        hashMap.put("@passexp", data.getPassexp());
        hashMap.put("@koraddress", data.getKoraddress());
        hashMap.put("@mobileno", data.getMobileno());
        hashMap.put("@homeaddress", data.getHomeaddress());
        hashMap.put("@data", formattedDateTime);
        return hashMap;
    }
}
