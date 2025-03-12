package com.drscan.web.Controller;

import com.drscan.web.secondary.image.domain.Image;
import com.drscan.web.secondary.image.service.ImageService;
import jcifs.CIFSContext;
import jcifs.context.SingletonContext;
import jcifs.smb.NtlmPasswordAuthenticator;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import lombok.RequiredArgsConstructor;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reports/jinja/testing")
public class ReportsRESTController {

    @Value("${smb.username}")
    private String USERNAME;
    @Value("${smb.password}")
    private String PW;
    @Value("${smb.url}")
    private String SMB_URL;

    private final ImageService imageService;

    // 지정된 studykey 에 속한 시리즈별 이미지 메타데이터를 반환
    @GetMapping("/{studykey}")
    public ResponseEntity<List<Map<String, ?>>> getDicomMetadataBySeries(@PathVariable Integer studykey) {
        List<Image> images = imageService.getImagesByStudyKey(studykey);

        if (images == null || images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // 시리즈별 이미지 그룹화
        Map<Integer, List<Map<String, ?>>> groupBySeries = getIntegerListMap(images);

        // SeriesImages 리스트 생성
        List<Map<String, ?>> seriesImagesList = new ArrayList<>();
        for (Map.Entry<Integer, List<Map<String, ?>>> entry : groupBySeries.entrySet()) {
            Integer seriesKey = entry.getKey();
            List<Map<String, ?>> imageInfos = entry.getValue();

            NtlmPasswordAuthenticator authenticator = new NtlmPasswordAuthenticator(USERNAME, PW);
            CIFSContext baseContext = SingletonContext.getInstance();
            CIFSContext context = baseContext.withCredentials(authenticator);

            // 촬영 영상 시리즈 중 첫 번째 파일의 메타데이터를 추출
            Map<String, ?> firstImage = imageInfos.get(0);
            Map<String, ?> metadata = extractDicomMetadata(firstImage, context);

            Map<String, Object> seriesImages = new HashMap<>();
            seriesImages.put("studykey", studykey);
            seriesImages.put("serieskey", seriesKey);
            seriesImages.put("images", imageInfos);
            seriesImages.put("imageCount", imageInfos.size());
            seriesImages.put("modality", metadata.get("modality"));

            seriesImagesList.add(seriesImages);
        }

        // 시리즈 키 기준으로 오름차순 정렬
        seriesImagesList.sort(Comparator.comparingInt(
                map -> (int) map.get("serieskey")
            )
        );

        return ResponseEntity.ok(seriesImagesList);
    }

    // studykey 와 serieskey 에 해당하는 메타 데이터 불러오기
    @GetMapping("/{studykey}/{serieskey}")
    public ResponseEntity<?> getSeriesImages(@PathVariable Integer studykey, @PathVariable Integer serieskey) {
        if (studykey < 1 || serieskey < 1) {
            return ResponseEntity.badRequest().build();
        }

        List<Image> images = imageService.getSeriesImages(studykey, serieskey);
        if (images == null || images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Image image = images.get(0);
        String dicomFilePath = SMB_URL + image.getPath().replace("\\", "/") + image.getFname();

        Map<String, String> overlayData = new HashMap<>();

        NtlmPasswordAuthenticator auth = new NtlmPasswordAuthenticator(USERNAME, PW);
        CIFSContext baseContext = SingletonContext.getInstance();
        CIFSContext context = baseContext.withCredentials(auth);

        try (SmbFile dcmFile = new SmbFile(dicomFilePath, context);
             SmbFileInputStream dcmInputStream = new SmbFileInputStream(dcmFile);
             DicomInputStream dicomInputStream = new DicomInputStream(dcmInputStream)) {

            // DICOM 파일을 제대로 불러오는지 확인
            if (dcmFile.exists() && dcmFile.isFile()) {
                Attributes attributes = dicomInputStream.readDataset();
                overlayData.put("patientName", attributes.getString(Tag.PatientName, "알 수 없음"));
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("DICOM 파일 처리 중 오류가 발생하였습니다. " +
                    "오류 메시지 : " + e.getMessage());
        }
        return ResponseEntity.ok(overlayData);
    }

    // DICOM 파일을 Base64로 인코딩하여 리스트 반환
    @GetMapping("/wado")
    public ResponseEntity<List<String>> getDicomSeries(
            @RequestParam String requestType, @RequestParam Integer studykey, @RequestParam Integer serieskey
    ) throws IOException {
        if (!"WADO".equals(requestType) || studykey == null || serieskey == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Image> images = imageService.getSeriesImages(studykey, serieskey);
        if (images == null) return ResponseEntity.notFound().build();

        NtlmPasswordAuthenticator authorization = new NtlmPasswordAuthenticator(USERNAME, PW);
        CIFSContext baseContext = SingletonContext.getInstance();
        CIFSContext context = baseContext.withCredentials(authorization);

        List<String> encodingSeries = new ArrayList<>();
        for (Image image : images) {
            String dcmFilePath = SMB_URL + image.getPath().replace("\\", "/") + image.getFname();
            SmbFile dcmFile = new SmbFile(dcmFilePath, context);

            if (dcmFile.exists() && dcmFile.isFile()) {
                try (SmbFileInputStream inputStream = new SmbFileInputStream(dcmFile)) {

                    byte[] fileContent = inputStream.readAllBytes();

                    // Base64로 인코딩
                    String encodedString = Base64.getEncoder().encodeToString(fileContent);
                    encodingSeries.add(encodedString);
                }
            }
        }
        return ResponseEntity.ok(encodingSeries);
    }

    // 불러온 이미지를 시리즈 단위로 분류
    private static Map<Integer, List<Map<String, ?>>> getIntegerListMap(List<Image> images) {
        Map<Integer, List<Map<String, ?>>> seriesGroup = new HashMap<>();
        for (Image image : images) {
            Map<String, Object> imageInfo = new HashMap<>();
            imageInfo.put("studykey", image.getStudykey());
            imageInfo.put("serieskey", image.getSerieskey());
            imageInfo.put("imagekey", image.getImagekey());
            imageInfo.put("filename", image.getFname());
            imageInfo.put("path", image.getPath() != null ? image.getPath().replace("\\", "/") : null);

            int seriesKey = image.getSerieskey();
            List <Map <String, ?>> seriesImagesList = seriesGroup.computeIfAbsent(seriesKey, k -> new ArrayList<>());
            seriesImagesList.add(imageInfo);
        }
        return seriesGroup;
    }

    // 로컬 서버 스토리지에서 DICOM 파일의 촬영 장비 메타데이터를 추출
    private Map<String, ?> extractDicomMetadata(Map<String, ?> imageInfo, CIFSContext context) {
        String dcmFilePath = SMB_URL + imageInfo.get("path") + imageInfo.get("filename");
        Map<String, String> metadata = new HashMap<>();

        try (SmbFile file = new SmbFile(dcmFilePath, context);
             SmbFileInputStream dcmFile = new SmbFileInputStream(file);
             DicomInputStream dicomImageInputStream = new DicomInputStream(dcmFile)) {

            if (file.exists() && file.isFile()) {
                Attributes attributes = dicomImageInputStream.readDataset();
                metadata.put("modality", attributes.getString(Tag.Modality, "<알 수 없는 촬영 장비>"));
                return metadata;
            }
        } catch (IOException e) {
            metadata.put("modality", "<알 수 없는 촬영 장비>");
            return metadata;
        }
        // 파일이 존재하지 않을 때
        metadata.put("modality", "<알 수 없는 촬영 장비>");
        return metadata;
    }
}
