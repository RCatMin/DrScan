package com.drscan.web.Controller;

import com.drscan.web.secondary.image.domain.Image;
import com.drscan.web.secondary.image.domain.ImageRepository;
import com.drscan.web.secondary.patientScan.service.PatientScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/patientScan/action")
@RestController
public class PatientScanRESTController {

    private final PatientScanService patientScanService;

    // 환자 ID로 조회
    @GetMapping("/{pid}/records")
    public ResponseEntity<?> getPatientRecords(@PathVariable String pid) {
        return ResponseEntity.ok(patientScanService.getPatientRecords(pid));
    }

    // 이미지 불러오기
    @GetMapping("/images/{studykey}/{serieskey}")
    public ResponseEntity<?> getImages(@PathVariable Integer studykey, @PathVariable Integer serieskey) {
        List<Image> images = patientScanService.getImagesByStudyAndSeries(studykey, serieskey);
        return ResponseEntity.ok(images);
    }

    // 환자 아이디로 환자정보 가져오기
    @GetMapping("/{pid}")
    public ResponseEntity<?> getPatient(@PathVariable String pid) {
        return ResponseEntity.ok(patientScanService.getPatientByPid(pid));
    }

    // DICOM 파일
    private final String DICOM_STORAGE_PATH = "Z:/"; // 실제 DICOM 파일 저장소 경로

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
}
