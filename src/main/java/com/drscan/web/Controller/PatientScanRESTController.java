package com.drscan.web.Controller;

import com.drscan.web.primary.log.service.LogService;
import com.drscan.web.primary.reports.domain.RadiologistReport;
import com.drscan.web.primary.reports.domain.RadiologistReportRepository;
import com.drscan.web.primary.reports.service.RadiologistReportService;
import com.drscan.web.primary.users.domain.AuthUser;
import com.drscan.web.secondary.image.domain.Image;
import com.drscan.web.secondary.patientScan.service.PatientScanService;
import com.drscan.web.secondary.series.domain.Series;
import com.drscan.web.secondary.series.domain.SeriesId;
import com.drscan.web.secondary.series.domain.SeriesRepository;
import com.drscan.web.secondary.study.domain.Study;
import com.drscan.web.secondary.study.domain.StudyRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/patientScan/action")
@RestController
public class PatientScanRESTController {

    // 오라클
    private final PatientScanService patientScanService;
    private final StudyRepository studyRepository;
    private final SeriesRepository seriesRepository;

    // MYSQL
    private final RadiologistReportService radiologistReportService;
    private final RadiologistReportRepository radiologistReportRepository;
    private final LogService logService;

    // 오라클 환자 ID로 조회
    @GetMapping("/{pid}/records")
    public ResponseEntity<?> getPatientRecords(@PathVariable String pid) {
        return ResponseEntity.ok(patientScanService.getPatientRecords(pid));
    }

    // 오라클 이미지 불러오기
    @GetMapping("/images/{studykey}/{serieskey}")
    public ResponseEntity<?> getImages(@PathVariable Integer studykey, @PathVariable Integer serieskey) {
        List<Image> images = patientScanService.getImagesByStudyAndSeries(studykey, serieskey);
        return ResponseEntity.ok(images);
    }

    // 오라클 환자 아이디로 환자정보 가져오기
    @GetMapping("/{pid}")
    public ResponseEntity<?> getPatient(@PathVariable String pid) {
        return ResponseEntity.ok(patientScanService.getPatientByPid(pid));
    }

    // 오라클 실제 DICOM 파일 저장소 경로
    private final String DICOM_STORAGE_PATH = "Z:/";

    @GetMapping("/getDicomFile")
    public ResponseEntity<Resource> getDicomFile(@RequestParam String path) {
        try {
            // 백슬래시(\)를 슬래시(/)로 변환 (윈도우 환경 대응)
            String normalizedPath = path.replace("\\", "/");

            // Z 드라이브에 있는 DICOM 파일 절대 경로로 변환
            Path filePath = Paths.get(DICOM_STORAGE_PATH, normalizedPath).normalize();

            // 파일이 실제 존재하는지 확인
            if (!Files.exists(filePath)) {
                System.out.println("파일을 찾을 수 없음: " + filePath);
                return ResponseEntity.notFound().build();
            }

            System.out.println("DICOM 파일 찾음: " + filePath);

            byte[] dicomData = Files.readAllBytes(filePath);
            Resource resource = new ByteArrayResource(dicomData);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {
            System.out.println("DICOM 파일 로드 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    // 오라클 시리즈의 판독 데이터 가져오기
    @GetMapping("/study-series/{studykey}/{serieskey}")
    public ResponseEntity<?> getStudyAndSeries(@PathVariable Integer studykey, @PathVariable Integer serieskey) {
        Map<String, Object> result = new HashMap<>();

        // Study 조회
        Optional<Study> study = studyRepository.findById(studykey);
        if (study.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Study 정보 없음");
        }
        result.put("study", study.get());

        // Series 조회
        Optional<Series> series = seriesRepository.findById(new SeriesId(studykey, serieskey));
        if (series.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Series 정보 없음");
        }
        result.put("series", series.get());

        return ResponseEntity.ok(result);
    }


    // MYSQL특정 시리즈의 판독 데이터 가져오기
    @PutMapping("/reports/{reportCode}")
    public ResponseEntity<RadiologistReport> updateReport(
            @PathVariable Integer reportCode,
            @RequestBody RadiologistReport updatedReport
    ) {
        return radiologistReportRepository.findById(reportCode).map(report -> {
            report.setSeverityLevel(updatedReport.getSeverityLevel());
            report.setReportStatus(updatedReport.getReportStatus());
            report.setReportText(updatedReport.getReportText());
            report.setModDate(LocalDateTime.now());
            radiologistReportRepository.save(report);
            return ResponseEntity.ok(report);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // MySQL 판독 데이터 저장
    @PostMapping("/reports/save")
    public ResponseEntity<?> saveRadiologistReport(@RequestBody RadiologistReport report, HttpSession session) {
        RadiologistReport savedReport = radiologistReportService.saveReport(report);

        AuthUser authUser = (AuthUser) session.getAttribute("authUser");

        logService.saveLog(authUser, report, "판독 데이터 저장");

        return ResponseEntity.ok(savedReport);
    }

    // MySQL 판독 데이터 삭제
    @DeleteMapping("/reports/{reportCode}")
    public ResponseEntity<Void> deleteReport(@PathVariable Integer reportCode) {
        if (radiologistReportRepository.existsById(reportCode)) {
            radiologistReportRepository.deleteById(reportCode);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 환자 ID로 판독 기록 조회 API
    @GetMapping("/reports/patient/{patientId}")
    public ResponseEntity<List<RadiologistReport>> getReportsByPatient(@PathVariable String patientId) {
        List<RadiologistReport> reports = radiologistReportService.getReportsByPatientId(patientId);
        return ResponseEntity.ok(reports);
    }

    // 상세페이지에서 불러오기
    @GetMapping("/reports/{reportCode}")
    public ResponseEntity<RadiologistReport> getReportById(@PathVariable Integer reportCode) {
        return radiologistReportRepository.findById(reportCode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
