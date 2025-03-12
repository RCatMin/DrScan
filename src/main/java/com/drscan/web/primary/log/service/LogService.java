package com.drscan.web.primary.log.service;

import com.drscan.web.primary.log.domain.Log;
import com.drscan.web.primary.log.domain.LogRepository;
import com.drscan.web.primary.reports.domain.RadiologistReport;
import com.drscan.web.primary.users.domain.AuthUser;
import com.drscan.web.primary.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;

    public List<Log> findLogAll() { return logRepository.findAll(); };

    public void saveLog(AuthUser user, String event) {
        Log log = new Log(user.getUserCode(), event);

        logRepository.save(log);
    }

    public void saveLog(User user, String event) {
        Log log = new Log(user.getUserCode(), event);

        logRepository.save(log);
    }

    public void saveLog(AuthUser user, RadiologistReport report, String event) {
        Log log = new Log(user.getUserCode(), report, event);

        logRepository.save(log);
    }

    public void saveLog(AuthUser user, long clinicCode, String event) {
        Log log = new Log(user.getUserCode(), clinicCode, event);

        logRepository.save(log);
    }

    public Page<Log> findByUserCode(Long userCode, Pageable pageable) {
        return logRepository.findByUserCode(userCode, pageable);
    }

    public Page<Log> findAll(Pageable pageable) {
        return logRepository.findAll(pageable);
    }


}