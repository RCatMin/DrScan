package com.drscan.web.primary.reports.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Integer> {
}
