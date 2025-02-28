package com.drscan.web.primary.log.service;

import com.drscan.web.primary.log.domain.Log;
import com.drscan.web.primary.log.domain.LogRepository;
import com.drscan.web.primary.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;

    public void saveLog(User user, String event) {
        Log log = new Log(user.getUserCode(), event);

        logRepository.save(log);
    }

}