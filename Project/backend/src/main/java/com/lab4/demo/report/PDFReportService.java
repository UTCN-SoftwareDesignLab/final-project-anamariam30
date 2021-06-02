package com.lab4.demo.report;

import com.lab4.demo.grade.model.dto.GradeDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDMMType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;


@Service
public class PDFReportService implements ReportService {

    private static List<String> createPDFDataSimple(List<GradeDTO> grades) {
        List<String> list = new ArrayList<>();
        list.add(grades.get(0).getStudent());
        for (GradeDTO grade : grades) {
            String record = grade.getCourse()+" :   "+ grade.getGrade() +"      ="+ grade.getData().toString().substring(0,10) ;
            list.add(record);
        }

        return list;
    }
    @Override
    public PDDocument export(List<GradeDTO> grades,Long id) {
        PDDocument pdf = createPDF(createPDFDataSimple(grades), id);

        return pdf;
    }

    public PDDocument createPDF(List<String> data,Long id)
    {
        PDDocument document=new PDDocument();
        PDPage page=new PDPage();
        document.addPage(page);
        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            addDataToPage(contentStream,data);
            document.save("backend\\src\\main\\resources\\ClassReport"+id+".pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    private void setConfiguration(PDPageContentStream contentStream ) throws IOException {
        contentStream.setLeading(16);
        contentStream.setFont(PDMMType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(50, 700);
        contentStream.showText("Class Grades");
        contentStream.newLine();
        contentStream.newLine();
    }

    private void addDataToPage(PDPageContentStream contentStream,List<String> dataSimple) throws IOException {
        contentStream.beginText();
        setConfiguration(contentStream);
        for (String record : dataSimple)
        {
            contentStream.showText(record);
            contentStream.newLine();
        }
        contentStream.endText();
        contentStream.close();
    }



    @Override
    public ReportType getType() {
        return PDF;
    }
}