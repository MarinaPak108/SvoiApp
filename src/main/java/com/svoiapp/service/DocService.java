package com.svoiapp.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import com.svoiapp.component.VisaFillFormHashmap;
import com.svoiapp.component.VisaReasonHashmap;
import com.svoiapp.formdata.CreateVisaExtendFormData;
import com.svoiapp.formdata.FillVisaExtendFormData;
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

    @Autowired
    public DocService(VisaReasonHashmap visaReasonHashmap, VisaFillFormHashmap visaFillFormHashmap) {
        this.visaReasonHashmap = visaReasonHashmap;
        this.visaFillFormHashmap = visaFillFormHashmap;
    }

    //check if isSchool
    public Boolean checkIsSchool (CreateVisaExtendFormData data){
        if(data.getSchoolname()!= ""){
            return true;
        }
        else
            return false;
    }
    //prepare entity to fill visa
    public HashMap<String, String> prepareEntity (CreateVisaExtendFormData data, String visaType, Boolean isSchool){
        data.convertNullFieldsToString(data);
        HashMap<String, String> newHashmap = visaFillFormHashmap.preFillHashMap(data, visaType);
        newHashmap = fillGenderVisa(newHashmap, data.getSex());
        newHashmap = fillVisaReason(newHashmap, data.getReason());
        return newHashmap;
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

    public void replaceText(String UserNameAndVisaType) throws IOException {
        String filePath = getClass().getClassLoader()
                .getResource("visa_ext.docx")
                .getPath();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            XWPFDocument doc = new XWPFDocument(inputStream);
            doc = replaceText(doc, "Hello", "Updated");
            saveFile(filePath, doc, "newName");
            doc.close();
        }
    }

    private XWPFDocument replaceText(XWPFDocument doc, String originalText, String updatedText) {
        List<XWPFTable> tables = doc.getTables();
        System.out.println(tables);
        XWPFTable table = tables.get(0);
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 0; i < rows.size(); i++) {
            List<XWPFTableCell> cells = rows.get(i).getTableCells();
            System.out.println(cells.get(0).getParagraphs().get(0).getFirstLineIndent());
        }
        replaceTextInParagraphs(doc.getParagraphs(), originalText, updatedText);
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    replaceTextInParagraphs(cell.getParagraphs(), originalText, updatedText);
                }
            }
        }
        return doc;
    }

    private void replaceTextInParagraphs(List<XWPFParagraph> paragraphs, String originalText, String updatedText) {
        paragraphs.forEach(paragraph -> replaceTextInParagraph(paragraph, originalText, updatedText));
    }

    private void replaceTextInParagraph(XWPFParagraph paragraph, String originalText, String updatedText) {
        String paragraphText = paragraph.getParagraphText();
        if (paragraphText.contains(originalText)) {
            String updatedParagraphText = paragraphText.replace(originalText, updatedText);
            while (paragraph.getRuns().size() > 0) {
                paragraph.removeRun(0);
            }
            XWPFRun newRun = paragraph.createRun();
            newRun.setText(updatedParagraphText);
        }
    }

    private void saveFile(String filePath, XWPFDocument doc, String newName) throws IOException {
        filePath = filePath.replace("visa_ext.docx", newName+".docx");
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            doc.write(out);
        }
    }

}