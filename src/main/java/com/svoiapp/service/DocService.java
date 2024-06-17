package com.svoiapp.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.svoiapp.component.DocumentNameHashmap;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;


import com.svoiapp.component.SchoolTypeHashmap;
import com.svoiapp.component.VisaFillFormHashmap;
import com.svoiapp.component.VisaReasonHashmap;
import com.svoiapp.formdata.CreateVisaExtendFormData;


@Service
public class DocService {
    private final VisaReasonHashmap visaReasonHashmap;
    private final SchoolTypeHashmap schoolTypeHashmap;
    private final DocumentNameHashmap documentNameHashmap;

    @Autowired
    public DocService(VisaReasonHashmap visaReasonHashmap, SchoolTypeHashmap schoolTypeHashmap, DocumentNameHashmap documentNameHashmap) {
        this.visaReasonHashmap = visaReasonHashmap;
        this.schoolTypeHashmap = schoolTypeHashmap;
        this.documentNameHashmap = documentNameHashmap;
    }

    //prepare entity to fill visa
    public HashMap<String, String> prepareEntity (CreateVisaExtendFormData data, String visaType){
        data.convertNullFieldsToString(data);
        VisaFillFormHashmap visaFillFormHashmap = new VisaFillFormHashmap();
        HashMap<String, String> hashMap = visaFillFormHashmap.createFormHashMap();
        HashMap<String, String> newHashmap = visaFillFormHashmap.preFillHashMap(data, visaType, hashMap);
        newHashmap = fillGenderVisa(newHashmap, data.getSex());
        newHashmap = fillVisaReason(newHashmap, data.getReason());
        return newHashmap;
    }

    public HashMap<String, String> fillSchoolWork (HashMap<String, String> hashMapData, CreateVisaExtendFormData data, String visaType,  Boolean isSchool, Boolean isWork){
        if(isSchool){
            //find school status
            HashMap<String, String> hashMap = schoolTypeHashmap.getHashMap();
            // convert to SchoolType
            String valueField = hashMap.get(data.getSchoolstatus());
            hashMapData.put(valueField, "x");

            //find school type
            if(data.getSchooltype().equals("аккредитованно"))
                hashMapData.put("@41", "x");
            else if (data.getSchooltype().equals("неаккредитованно"))
                hashMapData.put("42", "x");

            //fill rest of info
            hashMapData.put("@schoolname",data.getSchoolname());
            hashMapData.put("@schooltel",data.getSchooltel());
        }
        // fill if person is working
        if(isWork){
            hashMapData.put("@compname", data.getCompname());
            hashMapData.put("@compno", data.getCompno());
            hashMapData.put("@comptel", data.getComptel());
            hashMapData.put("@position", data.getPosition());
        }
        // fill only in case of change of work
        if(data.getReason().equals("работа")){
            hashMapData.put("@compname*", data.getCompnameNew());
            hashMapData.put("@compno*", data.getCompnoNew());
            hashMapData.put("@comptel*", data.getComptelNew());
        }
        return hashMapData;
    }

    //fill dropdown gender
    private HashMap<String, String> fillGenderVisa (HashMap<String, String> data, String gender){
        if(gender.equals("м")){
            data.put("@7", "x");
        } else if (gender.equals("ж")){
            data.put("@8","x");
        }
        return data;
    }

    //fill dropdown visa reason
    public HashMap<String, String> fillVisaReason (HashMap<String, String> data, String reason) {
        HashMap<String, String> hashMap = visaReasonHashmap.getHashMap();
        //convert to visaFillField
        String valueField = hashMap.get(reason);
        data.put(valueField, "x");
        return data;
    }

    public Boolean replaceText(HashMap<String, String> data, String visaType, String login) throws IOException {
        HashMap<String, List<String>> docInfo = documentNameHashmap.getHashMap();
        List<String> values = docInfo.get(visaType);
        String docName = values.get(0);
        String filePath = getClass().getClassLoader().getResource(docName).getPath();
        String docFilePath = values.get(1);
        try (InputStream inputStream = new FileInputStream(filePath)) {
            XWPFDocument doc = new XWPFDocument(inputStream);
            doc = replaceText(doc, data);
            long timestampInMillis = System.currentTimeMillis();
            Boolean result = saveFile(docFilePath+login+"/", doc, login+"_"+timestampInMillis);
            doc.close();
            return result;
        }
    }

    private XWPFDocument replaceText(XWPFDocument doc, HashMap<String, String> data) {
        List<XWPFTable> tables = doc.getTables();
        System.out.println(tables);
        XWPFTable table = tables.get(0);
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 0; i < rows.size(); i++) {
            List<XWPFTableCell> cells = rows.get(i).getTableCells();
            System.out.println(cells.get(0).getParagraphs().get(0).getFirstLineIndent());
        }
        replaceTextInParagraphs(doc.getParagraphs(), data);
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    replaceTextInParagraphs(cell.getParagraphs(), data);
                }
            }
        }
        return doc;
    }

    private void replaceTextInParagraphs(List<XWPFParagraph> paragraphs, HashMap<String, String> data) {
        paragraphs.forEach(paragraph -> replaceTextInParagraph(paragraph,  data));
    }

    private void replaceTextInParagraph(XWPFParagraph paragraph, HashMap<String, String> data) {
        String paragraphText = paragraph.getParagraphText();
        String updatedParagraphText = "";
        if (paragraphText.contains("@")) {
            if(paragraphText.contains("[")){
                updatedParagraphText = updateParahraph (paragraphText, data);
                System.out.println(updatedParagraphText);
            }
            else {
                updatedParagraphText = paragraphText.replace(paragraphText, checkAndGetValue(data, paragraphText));
                System.out.println(updatedParagraphText);
            }
            while (paragraph.getRuns().size() > 0) {
                paragraph.removeRun(0);
            }
            XWPFRun newRun = paragraph.createRun();
            newRun.setText(updatedParagraphText);
        }
    }

    private Boolean saveFile(String folderPath, XWPFDocument doc, String newName) throws IOException {
        Path directory = Paths.get(folderPath);
        String file = folderPath+newName+".docx";

        // Check if the folder exists
        if (!Files.exists(directory)) {
            // Create the directory
            Files.createDirectory(directory);
        }
        try (FileOutputStream out = new FileOutputStream(file)) {
            doc.write(out);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    // update paragraph with [] mark
    private String updateParahraph (String paragraph,HashMap<String, String> data ){
        // Define the regex pattern to match the desired substrings
        Pattern pattern = Pattern.compile("@\\d+");
        Matcher matcher = pattern.matcher(paragraph);
        while (matcher.find()) {
            String subParagraphText = matcher.group();
            paragraph = paragraph.replace(subParagraphText, checkAndGetValue(data, subParagraphText));
        }
        return paragraph;
    }

    private String checkAndGetValue (HashMap<String, String> data, String mark){
        if (data.get(mark).isEmpty() || data.get(mark).equals(null))
            return "";
        else
            return data.get(mark);
    }

    public Resource loadFileAsResource(String filename, String fileDir) {
        try {
            Path filePath = Paths.get(fileDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + filename);
            }
        } catch (IOException ex) {
            throw new RuntimeException("File not found: " + filename, ex);
        }
    }

    public String getLatestFile (String fileDir){
        // Create a File object representing the directory
        File directory = new File(fileDir);

        // List all files in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            // Filter files with .docx extension
            List<File> docxFiles = Arrays.stream(files)
                    .filter(file -> file.isFile() && file.getName().toLowerCase().endsWith(".docx"))
                    .collect(Collectors.toList());

            return docxFiles.get(files.length-1).getName();
        } else {
            return null;
        }
    }
}