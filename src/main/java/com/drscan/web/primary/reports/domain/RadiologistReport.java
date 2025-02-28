package com.drscan.web.primary.reports.domain;

import com.drscan.web.primary.users.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "radiologist_reports")
public class RadiologistReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportCode; // 판독코드

    @Column(name = "seriesinsuid")
    private String seriesInsUid; // DICOM Series UID (FK)

    @Column(name = "pid")
    private String patientId; // 환자 ID (FK)

    @ManyToOne
    @JoinColumn(name = "user_code", referencedColumnName = "user_code", insertable = false, updatable = false)
    private User userCode; // 판독 의사 ID (FK)

    @ManyToOne
    @JoinColumn(name = "approve_user_code", referencedColumnName = "user_code", insertable = false, updatable = false)
    private User approveUserCode; // 승인 의사 ID (FK)

    private LocalDateTime studyDate; // 검사 날짜 (DEFAULT CURRENT_TIMESTAMP)
    private LocalDateTime approveStudyDate; // 판독 승인 날짜
    private String studyName; // 검사 이름
    private String modality; // 검사 장비
    private String bodyPart; // 검사 부위
    private String patientName; // 환자 이름
    private String patientSex; // 환자 성별 (ENUM 'M', 'F')
    private LocalDateTime patientBirthDate; // 환자 생년월일
    private String patientAge; // 환자 나이
    private String severityLevel; // 중증도 레벨 (ENUM '1', '2', '3', '4', '5')
    private String reportStatus; // 보고서 상태 (ENUM 'Draft', 'Finalized', 'Needs Revision')
    private LocalDateTime regDate = LocalDateTime.now(); // 등록일 (DEFAULT CURRENT_TIMESTAMP)
    private LocalDateTime modDate = LocalDateTime.now(); // 수정일 (DEFAULT CURRENT_TIMESTAMP ON UPDATE)
}
