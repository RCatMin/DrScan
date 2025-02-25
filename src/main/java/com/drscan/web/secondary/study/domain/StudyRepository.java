package com.drscan.web.secondary.study.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Integer> {
    List<Study> findByPid(String pid);
}

