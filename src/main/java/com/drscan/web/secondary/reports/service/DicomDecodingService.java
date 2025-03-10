package com.drscan.web.secondary.reports.service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReader;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReaderSpi;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class DicomDecodingService {

    // dcm 파일을 받아서 dcm4che를 통해 Decoding
    public BufferedImage decodeDicomFile(File dicomFile) throws IOException {
        // SPI 인스턴스 생성
        DicomImageReaderSpi spi = new DicomImageReaderSpi();

        // ImageReader 인스턴스 생성 (한 줄로 작성하지 않고 별도의 변수에 할당)
        ImageReader reader = (DicomImageReader) spi.createReaderInstance();

        try (ImageInputStream iis = ImageIO.createImageInputStream(dicomFile)){
            reader.setInput(iis);
            return reader.read(0);
        }
    }
}
