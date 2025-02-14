package com.drscan.web.secondary.study.service;

import com.drscan.web.secondary.study.domain.Study;
import com.drscan.web.secondary.study.domain.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;

    // 생성자 주입
//    public StudyService(StudyRepository studyRepository) {
//        this.studyRepository = studyRepository;
//    }

    public List<Study> findStudyAll(){
        return studyRepository.findAll();
    }

}
