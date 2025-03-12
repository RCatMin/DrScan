package com.drscan.web.secondary.image.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ImageSearchRepository extends JpaRepository<Image, Integer> {
    List<Image> findImageByStudykey(Integer studykey);
    List<Image> findImageByStudykeyAndSerieskey(Integer studykey, Integer serieskey);
}

