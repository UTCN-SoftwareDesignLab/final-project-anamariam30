package com.lab4.demo.report;

import com.lab4.demo.grade.model.dto.GradeDTO;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.List;

public interface ReportService {
    PDDocument export(List<GradeDTO> grades,Long id);

    ReportType getType();


}

