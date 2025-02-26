package com.drscan.web.primary.log.service;

import com.drscan.web.primary.log.domain.Log;
import com.drscan.web.primary.log.domain.LogRepository;
import com.drscan.web.primary.log.domain.LogRequestDto;
import com.drscan.web.primary.reports.domain.SeriesList;
import com.drscan.web.primary.users.domain.User;
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

    public void saveLog(User user, String event) {
        Log log = new Log(user.getUserCode(), event);

        logRepository.save(log);
    }

    public void saveLog(User user, SeriesList seriesList, String event) {
        Log log = new Log(user.getUserCode(), seriesList.getSeriesKey(), event);

        logRepository.save(log);
    }
}