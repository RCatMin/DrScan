package com.drscan.web.log.service;

import com.drscan.web.log.domain.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;


}
