package com.drscan.web.secondary.series.domain;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SeriesId  implements Serializable {

    private Integer  studykey;

    private Integer serieskey;
}
