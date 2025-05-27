package com.drscan.web.secondary.series.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<Series, SeriesId> {

    public List<Series> findSeriesByStudykey(Integer studykey);

}


