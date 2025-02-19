package com.drscan.web.secondary.study.domain;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeriesId implements Serializable {

    private Integer studyKey;
    private Integer seriesKey;


}
