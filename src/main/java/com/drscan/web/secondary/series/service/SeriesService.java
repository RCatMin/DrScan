package com.drscan.web.secondary.series.service;

import com.drscan.web.secondary.series.domain.Series;
import com.drscan.web.secondary.series.domain.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public List<Series> findSeriesAll(int studykey){
        return seriesRepository.findSeriesByStudyKey(studykey);
    }
}
