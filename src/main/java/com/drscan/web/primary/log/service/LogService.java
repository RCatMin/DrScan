package com.drscan.web.primary.log.service;

import com.drscan.web.primary.log.domain.Log;
import com.drscan.web.primary.log.domain.LogRepository;
import com.drscan.web.primary.log.domain.LogRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;

    public Log createLog(LogRequestDto logDto) {
        Log newLog = new Log(
                logDto.getUserCode(),
                logDto.getReportCode(),
                logDto.getEvent()
        );
        return logRepository.save(newLog);
    }

}
