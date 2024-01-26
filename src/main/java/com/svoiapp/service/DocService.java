package com.svoiapp.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.svoiapp.component.SchoolTypeHashmap;
import com.svoiapp.component.VisaFillFormHashmap;
import com.svoiapp.component.VisaReasonHashmap;
import com.svoiapp.formdata.CreateVisaExtendFormData;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocService {
    private final VisaReasonHashmap visaReasonHashmap;
    private final VisaFillFormHashmap visaFillFormHashmap;
    private final SchoolTypeHashmap schoolTypeHashmap;

    @Autowired
    public DocService(VisaReasonHashmap visaReasonHashmap, VisaFillFormHashmap visaFillFormHashmap, SchoolTypeHashmap schoolTypeHashmap) {
        this.visaReasonHashmap = visaReasonHashmap;
        this.visaFillFormHashmap = visaFillFormHashmap;
        this.schoolTypeHashmap = schoolTypeHashmap;
    }

    //prepare entity to fill visa
    public HashMap<String, String> prepareEntity (CreateVisaExtendFormData data, String visaType){
        data.convertNullFieldsToString(data);
        HashMap<String, String> newHashmap = visaFillFormHashmap.preFillHashMap(data, visaType);
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

    public void replaceText(HashMap<String, String> data, String visaType, String login) throws IOException {
        String docName = "";
        if(visaType.equals("other")){
            docName = "notVisaF4.docx";
        }
        else if (visaType.equals("f4")){
            docName = "visaF4.docx";
        }
        String filePath = getClass().getClassLoader()
                .getResource(docName)
                .getPath();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            XWPFDocument doc = new XWPFDocument(inputStream);
            doc = replaceText(doc, data);
            long timestampInMillis = System.currentTimeMillis();
            saveFile(docName, filePath, doc, login+"_"+timestampInMillis);
            doc.close();
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
        if (paragraphText.contains("@")) {
            String updatedParagraphText = paragraphText.replace(paragraphText, data.get(paragraphText));
            while (paragraph.getRuns().size() > 0) {
                paragraph.removeRun(0);
            }
            XWPFRun newRun = paragraph.createRun();
            newRun.setText(updatedParagraphText);
        }
    }

    private void saveFile(String document, String filePath, XWPFDocument doc, String newName) throws IOException {
        filePath = filePath.replace(document, newName+".docx");
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            doc.write(out);
        }
    }

}