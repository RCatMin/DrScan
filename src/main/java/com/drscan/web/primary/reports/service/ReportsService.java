package com.drscan.web.primary.reports.service;

import com.drscan.web.primary.reports.domain.Reports;
import com.drscan.web.primary.reports.domain.ReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportsService {

    private final ReportsRepository reportsRepository;

}
