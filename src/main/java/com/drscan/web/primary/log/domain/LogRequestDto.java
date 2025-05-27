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

    private Long userCode;
    private Long reportCode;
    private String event;
    private Long search;
    private int page;
    private int size;

}
