package com.drscan.web.Controller;

import com.drscan.web.secondary.image.domain.Image;
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
@RequestMapping("/reports")
@RestController
public class ReportsRESTController {

    private final PatientScanService patientScanService;

    // 환자 정보 가져오기
    @GetMapping("/{pid}")
    public ResponseEntity<?> getPatient(@PathVariable String pid) {
        return ResponseEntity.ok(patientScanService.getPatientByPid(pid));
    }

    // 환자 이미지 가져오기
    @GetMapping("/checking/{studykey}/{serieskey}")
    public ResponseEntity<?> getImagesByStudyAndSeries(@PathVariable Integer studykey, @PathVariable Integer serieskey) {
        List<Image> patientImage = patientScanService.getImagesByStudyAndSeries(studykey, serieskey);

        return ResponseEntity.ok(patientImage);
    }

    // DICOM Path
    private final String DICOMStoragePath = "Z:/";

    @GetMapping ("/getDicomImage")
    public ResponseEntity<Resource> getDicomImage(@RequestParam String path) {
        try{
            String normalizedPath = path.replace("\\", "/");

            // DICOM 파일을 절대 경로로 Return
            Path filePath = Paths.get(DICOMStoragePath, normalizedPath).normalize();

            // 파일 존재유무 확인
            if (!Files.exists(filePath)) {
                System.out.println ("해당 위치에 파일이 존재하지 않습니다. : " + filePath);
                return ResponseEntity.notFound().build();
            }

            System.out.println("파일을 찾았습니다. " + filePath);

            byte[] dicomFileData = Files.readAllBytes(filePath);
            Resource resource = new ByteArrayResource(dicomFileData);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e){
            System.out.println ("DICOM 파일을 불러오는 중 오류가 발생했습니다." + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

}