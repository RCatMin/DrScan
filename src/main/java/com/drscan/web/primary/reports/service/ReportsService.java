package com.drscan.web.primary.reports.service;

import com.drscan.web.primary.reports.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportsService {

    private final SeriesListRepository seriesListRepository;
    private final PatientRepository patientRepository;
    private final ImageInfoRepository imageInfoRepository;

    // 시리즈 및 영상 목록 정보 가져오기
    public List<SeriesList> getSeriesAndImagesList(){
        return seriesListRepository.findAll();
    }

    // 환자 정보 가져오기
    public List<Patient> getPatientInfo(){
        return patientRepository.findAll();
    }

    // 영상 정보 가져오기
    public List<ImageInfo> getImageInfo(){
        return imageInfoRepository.findAll();
    }


}
