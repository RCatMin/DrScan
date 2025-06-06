package com.drscan.web.secondary.image.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByStudykeyAndSerieskey(Integer studykey, Integer serieskey);
}
