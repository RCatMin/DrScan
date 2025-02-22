package com.drscan.web.primary.log.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogRequestDto {

    private long userCode;
    private long reportCode;
    private String event;

}
