package com.drscan.web.secondary.patientScan.service;

import com.drscan.web.secondary.image.domain.Image;
import com.drscan.web.secondary.image.domain.ImageRepository;
import com.drscan.web.secondary.patientScan.domain.Patient;
import com.drscan.web.secondary.patientScan.domain.PatientRepository;
import com.drscan.web.secondary.series.domain.Series;
import com.drscan.web.secondary.series.domain.SeriesRepository;
import com.drscan.web.secondary.study.domain.Study;
import com.drscan.web.secondary.study.domain.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PatientScanService {

    private final PatientRepository patientRepository;
    private final StudyRepository studyRepository;
    private final SeriesRepository seriesRepository;
    private final ImageRepository imageRepository;

    public Map<String, Object> getPatientRecords(String pid) {
        Map<String, Object> result = new HashMap<>();

        // 환자 정보 조회
        Optional<Patient> patient = patientRepository.findById(pid);
        // 다시확인하기 @@@@@@@
        if (patient.isEmpty()) {
            result.put("error", "환자 정보 없음!");
            return result;
        }
        result.put("patient", patient.get());

        // Study 조회
        List<Study> studies = studyRepository.findByPid(pid);
        result.put("studies", studies);

        // 각 Study에 대한 Series 및 Image 조회
        List<Map<String, Object>> studyDetails = new ArrayList<>();
        for (Study study : studies) {
            Map<String, Object> studyData = new HashMap<>();
            studyData.put("study", study);

            // 해당 Study의 Series 조회
            List<Series> seriesList = seriesRepository.findSriesByStudykey(study.getStudykey());
            studyData.put("series", seriesList);

            // 각 Series에 대한 Image 조회
            List<Map<String, Object>> seriesDetails = new ArrayList<>();
            for (Series series : seriesList) {
                Map<String, Object> seriesData = new HashMap<>();
                seriesData.put("series", series);

                // Image 조회
                List<Image> images = imageRepository.findByStudykeyAndSerieskey(series.getStudyKey(), series.getSeriesKey());
                seriesData.put("images", images);

                seriesDetails.add(seriesData);
            }
            studyData.put("seriesDetails", seriesDetails);
            studyDetails.add(studyData);
        }
        result.put("studyDetails", studyDetails);

        return result;
    }
}
