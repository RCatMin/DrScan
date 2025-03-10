package com.drscan.web.Controller;

import com.drscan.web.secondary.reports.domain.DicomRequest;
import com.drscan.web.secondary.reports.domain.DicomSeries;
import com.drscan.web.secondary.reports.domain.DicomStudy;
import com.drscan.web.secondary.reports.service.DicomFileService;
import com.drscan.web.secondary.reports.service.DicomDecodingService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class ReportsRESTController {

    private final DicomFileService dicomFileService;
    private final DicomDecodingService dicomDecodingService;

    public ReportsRESTController(DicomFileService dicomFileService, DicomDecodingService dicomDecoderService) {
        this.dicomFileService = dicomFileService;
        this.dicomDecodingService = dicomDecoderService;
    }

    @GetMapping("/reports/viewer")
    public ResponseEntity<ByteArrayResource> getViewerImage(
            @RequestParam("studytime") String studytime,
            @RequestParam("studykey") Integer studykey,
            @RequestParam("pid") String pid,
            @RequestParam("modality") String modality,
            @RequestParam("serieskey") Integer serieskey,
            @RequestParam(value = "instance", required = false, defaultValue = "0") int instance
    ) throws IOException {

        DicomStudy dicomStudy = new DicomStudy(studytime, studykey, pid);
        DicomSeries dicomSeries = new DicomSeries(serieskey, modality);
        DicomRequest dicomRequest = new DicomRequest(dicomStudy, dicomSeries);

        // DicomFileService로 파일 목록 조회
        List<String> filePaths = dicomFileService.getDicomFilePaths(dicomRequest);
        if (filePaths.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        if (instance < 0 || instance >= filePaths.size()){
            instance = 0;
        }

        File dicomFile = new File (filePaths.get(instance));
        if (!dicomFile.exists()){
        return ResponseEntity.notFound().build();
        }

        // dcm 디코딩
        BufferedImage image = dicomDecodingService.decodeDicomFile(dicomFile);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(imageBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + dicomFile.getName() + ".png\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
