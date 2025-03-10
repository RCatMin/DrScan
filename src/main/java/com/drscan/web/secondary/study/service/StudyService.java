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

    public List<Study> searchStudies(String pid, String pname, String studydateStart, String studydateEnd, String studydesc, String modality, String accessnum) {
        return studyRepository.findByCriteria(pid, pname, studydateStart, studydateEnd, studydesc, modality, accessnum);
    }
}
