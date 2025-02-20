package com.drscan.web.reports.service;

import com.drscan.web.reports.domain.ReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportsService {

    private final ReportsRepository reportsRepository;


}
