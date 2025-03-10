package com.drscan.web.secondary.reports.service;

import com.drscan.web.secondary.reports.domain.DicomRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class DicomFileService {
    private final String DICOM_STORAGE_PATH = "/Volumes/STS/";

    // 파일 경로 조회용
    public List<String> getDicomFilePaths(DicomRequest dicomRequest) {
        // 예: /Volumes/STS/201608/22/MS0010/MR/7/
        String directoryPath = dicomRequest.getDirectoryPath(DICOM_STORAGE_PATH);
        List<String> filePath = new ArrayList<>();

        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()){
            for (File file : directory.listFiles()) {
                if (file.getName().toLowerCase().endsWith(".dcm")){
                    filePath.add(file.getAbsolutePath());
                }
            }
        }
        return filePath;
    }

}
