package com.drscan.web.secondary.reports.domain;

import java.nio.file.Paths;

public class DicomRequest {
    private DicomStudy dicomStudy;
    private DicomSeries dicomSeries;

    public DicomRequest(DicomStudy study, DicomSeries series){
        this.dicomStudy = study;
        this.dicomSeries = series;
    }

    public DicomStudy getDicomStudy() {
        return dicomStudy;
    }

    public DicomSeries getDicomSeries() {
        return dicomSeries;
    }

    public String getDirectoryPath(String baseDir){
        return Paths.get(baseDir, dicomStudy.getStudydate(),
                String.valueOf(dicomStudy.getStudykey()),
                dicomStudy.getPid(),
                dicomSeries.getModality(),
                String.valueOf(dicomSeries.getSerieskey())
        ).toString();
    }
}
