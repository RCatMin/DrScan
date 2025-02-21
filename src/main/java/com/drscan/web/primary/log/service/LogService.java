package com.drscan.web.primary.log.service;

import com.drscan.web.primary.log.domain.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;


}
