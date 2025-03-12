package com.drscan.web.secondary.image.service;

import com.drscan.web.secondary.image.domain.Image;
import com.drscan.web.secondary.image.domain.ImageSearchRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageSearchRepository imageSearchRepository;

    public List<Image> getImagesByStudyKey(Integer studykey) {
        return imageSearchRepository.findImageByStudykey(studykey);
    }
    public List<Image> getSeriesImages(Integer studykey, Integer serieskey) {
        return imageSearchRepository.findImageByStudykeyAndSerieskey(studykey,serieskey);
    }

}