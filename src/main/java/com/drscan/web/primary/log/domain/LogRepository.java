package com.drscan.web.primary.log.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
    Page<Log> findByUserCode(Long userCode, Pageable pageable);
    Page<Log> findAll(Pageable pageable);
}
