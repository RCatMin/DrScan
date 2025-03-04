import { init as coreInit, RenderingEngine, Enums } from '@cornerstonejs/core';
import { init as dicomImageLoaderInit } from '@cornerstonejs/dicom-image-loader';
// import {
//     addTool,
//     ZoomTool,
//     PanTool,
//     ToolGroupManager,
//     StackScrollTool,
//     LengthTool,
//     AngleTool,
//     BidirectionalTool,
//     ProbeTool
// } from '@cornerstonejs/tools';


let images = []; //이미지 정보 저장
let currentIndex = 0; //현재 이미지 인덱스
let imagesPerPage = 12; //한 페이지 갯수
let paginationSize = 5; //버튼 갯 수
let currentPage = 0;
let totalPages = 0;
let paginationStart = 0;

window.onload = function () {
    initializeCornerstone();
    addTools();
    setupSelectToolGroups();
};

async function initializeCornerstone() {
    await coreInit(); // cornerstone.js를 초기화
    await dicomImageLoaderInit(); // DICOM 이미지를 불러오기
    loadDicomImages(); // DICOM 이미지를 가져오기
}

document.addEventListener("DOMContentLoaded", () => {
    loadStudyAndSeriesInfo();
    loadPatientInfo();
});

async function loadStudyAndSeriesInfo() {
    try {
        const urlParts = window.location.pathname.split("/");
        const studyKey = urlParts[4];
        const seriesKey = urlParts[5];

        let response = await fetch(`/patientScan/action/study-series/${studyKey}/${seriesKey}`);
        let data = await response.json();

        if (!data || data.error) {
            console.error("Study/Series 정보를 불러올 수 없습니다!");
            return;
        }

        // Study 정보
        document.getElementById("studyDesc").innerText = data.study.studydesc || "N/A";
        document.getElementById("modality").innerText = data.study.modality || "N/A";
        document.getElementById("bodyPart").innerText = data.study.bodypart || "N/A";
        document.getElementById("accessNum").innerText = data.study.accessnum || "N/A";
        document.getElementById("studyDate").innerText = data.study.studydate || "N/A";
        document.getElementById("seriesCnt").innerText = data.study.seriescnt || "N/A";

        // Series 정보
        document.getElementById("seriesDesc").innerText = data.series.seriesdesc || "N/A";
        document.getElementById("seriesModality").innerText = data.series.modality || "N/A";
        document.getElementById("seriesDate").innerText = data.series.seriesdate || "N/A";
        document.getElementById("imageCnt").innerText = data.series.imagecnt || "N/A";
        document.getElementById("seriesNum").innerText = data.series.seriesnum || "N/A";

        // 판독 결과
        if (data.report) {
            document.getElementById("severityLevel").innerText = data.report.severityLevel || "N/A";
            document.getElementById("reportStatus").innerText = data.report.reportStatus || "N/A";
        }
    } catch (error) {
        console.error("Study/Series 정보 불러오는 중 오류 발생:", error);
    }
}


// 환자정보 불러오기
async function loadPatientInfo() {
    try {
        const urlParts = window.location.pathname.split("/");

        const pid = urlParts[3];

        let response = await fetch(`/patientScan/action/${pid}`);
        let patientData = await response.json();

        if (!patientData || patientData.length === 0 || patientData[0].error) {
            console.error("환자 정보를 불러올 수 없습니다!");
            return;
        }

        const patient = patientData[0];
        document.getElementById("patientName").innerText = patient.pname || "N/A";
        document.getElementById("patientId").innerText = patient.pid || "N/A";
        document.getElementById("patientSex").innerText = patient.psex || "N/A";
        document.getElementById("patientBirth").innerText = patient.pbirthdate || "N/A";
    } catch (error) {
        console.error("환자 정보 불러오는 중 오류 발생:", error);
    }
}

// DICOM 이미지 리스트 로드
async function loadDicomImages() {
    try {
        const urlParts = window.location.pathname.split("/");
        const studyKey = urlParts[4];
        const seriesKey = urlParts[5];

        // API 요청해서 JSON 데이터 저장
        let response = await fetch(`/patientScan/action/images/${studyKey}/${seriesKey}`);
        let imagesData = await response.json();

        // 예외
        if (!imagesData || imagesData.length === 0) {
            console.error("불러올 이미지가 없습니다!");
            return;
        }

        // 이미지 파일 경로, 파일 명만 저장
        images = imagesData.map(img => ({
            path: img.path,
            fname: img.fname
        }));

        // 퍼레이션 세팅
        totalPages = Math.ceil(images.length / imagesPerPage);
        updateThumbnailList(); // 시리즈 목록 업데이트
        updatePaginationControls(); //UI 업데이트
        displayImage(0); // 첫번쨰 사진 표시
    } catch (error) {
        console.error("DICOM 이미지 불러오는 중 오류 발생:", error);
    }
}

// cornerstone.js로 이미지 렌더링
function renderImage(arrayBuffer, viewportId) {
    const renderingEngine = new RenderingEngine(`renderingEngine-${viewportId}`);
    const viewportInput = {
        viewportId,
        element: document.getElementById(viewportId),
        type: Enums.ViewportType.STACK,
    };

    renderingEngine.enableElement(viewportInput);

    // cornerstone.js에서 DICOM 이미지 표시
    const imageId = "dicomweb:" + URL.createObjectURL(new Blob([arrayBuffer], { type: 'application/dicom' }));
    const viewport = renderingEngine.getViewport(viewportId);

    viewport.setStack([imageId], 0);
    viewport.render();
}

// 이미지 출력 함수
function displayImage(index) {
    if (index < 0 || index >= images.length) {
        return;
    }

    currentIndex = index;

    // 현재 보고 있는 이미지가 속한 페이지를 자동 변경
    let pageIndex = Math.floor(currentIndex / imagesPerPage);

    if (pageIndex !== currentPage) {
        currentPage = pageIndex;
        paginationStart = Math.floor(currentPage / paginationSize) * paginationSize;
        updateThumbnailList();
        updatePaginationControls();
    }

    // 선택한 이미지 가져와서 렌더링 요청
    updateActiveThumbnail();
    const image = images[index];
    const dicomFilePath = image.path + image.fname;
    fetchDicomFileAndRender(dicomFilePath, "dicomViewport");
}

// DICOM 파일 서버에서 가져오기(Z 드라이버에서 가져옴)
function fetchDicomFileAndRender(dicomFilePath, viewportId) {
    fetch(`/patientScan/action/getDicomFile?path=${encodeURIComponent(dicomFilePath)}`)
        .then(response => response.arrayBuffer())
        .then(arrayBuffer => renderImage(arrayBuffer, viewportId))
        .catch(error => console.error("DICOM 파일 로딩 중 오류 발생:", error));
}

document.addEventListener("DOMContentLoaded", () => {
    initializeCornerstone();
});

// 썸네일 리스트 업데이트 시 마우스 이벤트 추가
function updateThumbnailList() {
    const thumbnailContainer = document.getElementById("thumbnailContainer");
    thumbnailContainer.innerHTML = "";
    const start = currentPage * imagesPerPage;
    const end = Math.min(start + imagesPerPage, images.length);

    // 썸네일 생성
    for (let i = start; i < end; i++) {
        let image = images[i];
        let thumbnail = document.createElement("div");

        thumbnail.classList.add("thumbnail");
        thumbnail.dataset.index = i;

        // 현재 썸네일 강조
        if (i === currentIndex) {
            thumbnail.classList.add("active");
        }

        let viewport = document.createElement("div");
        viewport.classList.add("thumbnail-viewport");
        viewport.id = `thumbnail-${i}`;
        thumbnail.appendChild(viewport);

        // DICOM 이미지 불러와 썸네일에 표시
        fetchDicomFileAndRender(image.path + image.fname, viewport.id);

        // 클릭 & 마우스 이벤트 추가
        thumbnail.onclick = () => displayImage(i);
        thumbnail.onmouseover = () => thumbnail.classList.add("hover");
        thumbnail.onmouseout = () => thumbnail.classList.remove("hover");

        thumbnailContainer.appendChild(thumbnail);
    }

    updateActiveThumbnail();
}

// 현재 선택된 썸네일 강조
function updateActiveThumbnail() {
    document.querySelectorAll(".thumbnail").forEach(thumbnail => {
        let index = parseInt(thumbnail.dataset.index);

        if (index === currentIndex) {
            thumbnail.classList.add("active");
        } else {
            thumbnail.classList.remove("active");
        }
    });
}

// 페이지네이션 업데이트
function updatePaginationControls() {
    const pageButtons = document.getElementById("pageButtons");
    pageButtons.innerHTML = "";

    for (let i = paginationStart; i < Math.min(paginationStart + paginationSize, totalPages); i++) {
        let button = document.createElement("button");

        button.innerText = i + 1;
        button.classList.add("pagination-btn");

        if (i === currentPage) {
            button.classList.add("active");
        }

        // 버튼 클릭시 이동
        button.onclick = function () {
            currentPage = i;
            updateThumbnailList();
            updatePaginationControls();
        };
        pageButtons.appendChild(button);
    }

    // 이전/다음 버튼 활성화 여부 설정
    document.getElementById("prevPageBtn").disabled = paginationStart === 0;
    document.getElementById("nextPageBtn").disabled = paginationStart + paginationSize >= totalPages;
}

// 이전 페이지 버튼 이벤트
document.getElementById("prevPageBtn").onclick = () => {
    paginationStart = Math.max(0, paginationStart - paginationSize);
    updatePaginationControls();
};

// 다음 페이지 버튼 이벤트
document.getElementById("nextPageBtn").onclick = () => {
    paginationStart = Math.min(totalPages - paginationSize, paginationStart + paginationSize);
    updatePaginationControls();
};

// 마우스 휠 이벤트 추가
document.getElementById("viewerContainer").addEventListener("wheel", (event) => {
    event.preventDefault();
    if (event.deltaY > 0 && currentIndex < images.length - 1) {
        displayImage(currentIndex + 1);
    } else if (event.deltaY < 0 && currentIndex > 0) {
        displayImage(currentIndex - 1);
    }
});


// MySQL에서 판독 데이터 가져오기
async function loadRadiologistReport() {
    try {
        const seriesInsUid = window.location.pathname.split("/")[5];

        let response = await fetch(`/patientScan/action/reports/${seriesInsUid}`);
        let reports = await response.json();

        if (reports.length > 0) {
            document.getElementById("reportText").value = reports[0].reportText || "";
            document.getElementById("severityLevel").innerText = reports[0].severityLevel || "N/A";
            document.getElementById("reportStatus").innerText = reports[0].reportStatus || "N/A";
        }
    } catch (error) {
        console.error("판독 데이터 불러오기 오류:", error);
    }
}

/////////////////////////////
// 판독 도구
// function addTools(){
//     // 도구 등록
//     addTool(PanTool);
//     addTool(ZoomTool);
//     addTool(StackScrollTool);
//     addTool(LengthTool);
//     addTool(AngleTool);
//     addTool(BidirectionalTool);
//     addTool(ProbeTool);
// }
//
// // 기능 활성화
// function setupSelectToolGroups(){
//     const toolGroupId = 'ctToolGroup';
//     const ctToolGroup = ToolGroupManager.createToolGroup(toolGroupId);
//
//     // 영상 탐색 도구
//     ctToolGroup.addTool(PanTool.toolName); // 영상을 상하좌우로 이동시키는 도구
//     ctToolGroup.addTool(ZoomTool.toolName); // 영상 확대 및 축소 도구
//     ctToolGroup.addTool(StackScrollTool.toolName); // 마우스 휠로 영상 움직임
//
//     // 기본 활성화
//     ctToolGroup.setToolActive(StackScrollTool.toolName, {
//         bindings: []
//     });
//
//     const zoomBtn = document.getElementById("zoomBtn");
//     let zoomActive = false;
//     zoomBtn.addEventListener('click', () => {
//         if (!zoomActive) {
//             ctToolGroup.setToolActive(ZoomTool.toolName, { bindings: [{ mouseButton: 1 }] });
//             zoomActive = true;
//             console.log("Zoom 도구 활성화됨");
//         } else {
//             ctToolGroup.setToolDisabled(ZoomTool.toolName);
//             zoomActive = false;
//             console.log("Zoom 도구 비활성화됨");
//         }
//     });
//
//     const panBtn = document.getElementById("panBtn");
//     let panActive = false;
//     panBtn.addEventListener('click', () => {
//         if (!panActive){
//             ctToolGroup.setToolActive(PanTool.toolName, { bindings: [{mouseButton: 1}]});
//             panActive = true;
//             console.log ("Pan 도구 활성화됨");
//         } else {
//             ctToolGroup.setToolDisabled(PanTool.toolName);
//             panActive = false;
//             console.log("Pan 도구 비활성화됨");
//         }
//     });
//
//     // 측정 도구
//     ctToolGroup.addTool(LengthTool.toolName); // 두 점 사이의 거리를 측정
//     ctToolGroup.addTool(AngleTool.toolName); // 각도 측정
//
//     const calLengthBtn = document.getElementById("calLengthBtn");
//     let calLengthActive = false;
//     calLengthBtn.addEventListener('click', () => {
//         if (!calLengthActive){
//             ctToolGroup.setToolActive(LengthTool.toolName, {bindings : [{mouseButton: 1}]});
//             calLengthActive = true;
//             console.log("거리 측정 도구 활성화");
//         } else {
//             ctToolGroup.setToolDisabled(LengthTool.toolName);
//             calLengthActive = false;
//             console.log("거리 측정 도구 비활성화");
//         }
//     });
//
//     const calAngleBtn = document.getElementById("calAngleBtn")
//     let calAngleActive = false;
//     calAngleBtn.addEventListener('click', () => {
//         if (!calAngleActive){
//             ctToolGroup.setToolActive(AngleTool.toolName, {bindings : [{mouseButton : 1}]});
//             calAngleActive = true;
//             console.log ("각도 측정 도구 활성화");
//         } else {
//             ctToolGroup.setToolDisabled(AngleTool.toolName);
//             calAngleActive = false;
//             console.log("각도 측정 도구 비활성화");
//         }
//     });
// }

/////////////////////////////
// MySQL에 판독 데이터 저장
async function saveRadiologistReport() {

    // 요소 존재 여부 확인
    const requiredIds = [
        "patientId", "patientName", "patientSex", "patientBirth",
        "studyDate", "studyDesc", "modality", "bodyPart",
        "severityLevel", "reportStatus", "reportText",
        "userCode", "approveUserCode", "approveStudyDate"
    ];

    requiredIds.forEach(id => {
        const elem = document.getElementById(id);
        console.log(id, ":", elem ? elem.innerText || elem.value : " 없음 (null)");
    });

    // 필수 요소 체크
    // for (let id of requiredIds) {
    //     if (!document.getElementById(id)) {
    //         console.error(` 필수 요소 ${id}가 존재하지 않습니다!`);
    //         return; // 실행 중단
    //     }
    // }

    const reportText = document.getElementById("reportText").value;
    const severityLevelElem = document.getElementById("severityLevel");
    const reportStatusElem = document.getElementById("reportStatus");

    const severityLevel = severityLevelElem ? severityLevelElem.value : "1";
    const reportStatus = reportStatusElem ? reportStatusElem.value : "Draft";

    const patientId = document.getElementById("patientId").innerText;
    const patientName = document.getElementById("patientName").innerText;
    const patientSex = document.getElementById("patientSex").innerText;
    const patientBirthDate = document.getElementById("patientBirth").innerText;
    const patientAge = document.getElementById("patientAge").innerText;
    const studyDate = document.getElementById("studyDate").innerText;
    const studyName = document.getElementById("studyDesc").innerText;
    const modality = document.getElementById("modality").innerText;
    const bodyPart = document.getElementById("bodyPart").innerText;
    const userCode = document.getElementById("userCode").innerText; // 판독 의사 ID
    const approveUserCode = document.getElementById("approveUserCode").innerText; // 승인 의사 ID
    const approveStudyDate = document.getElementById("approveStudyDate").innerText; // 판독 승인 날짜

    const seriesInsUid = window.location.pathname.split("/")[5];

    const reportData = {
        seriesInsUid: seriesInsUid,
        patientId: patientId,
        patientName: patientName,
        patientSex: patientSex,
        patientBirthDate: patientBirthDate,
        patientAge: patientAge,
        studyDate: studyDate,
        studyName: studyName,
        modality: modality,
        bodyPart: bodyPart,
        severityLevel: severityLevel,
        reportStatus: reportStatus,
        reportText: reportText,
        userCode: userCode,
        approveUserCode: approveUserCode,
        approveStudyDate: approveStudyDate,
        regDate: new Date().toISOString(),
        modDate: new Date().toISOString()
    };

    try {
        let response = await fetch("/patientScan/action/reports/save", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(reportData),
        });

        let result = await response.json();
        console.log("저장 완료:", result);
        document.getElementById("autoSaveStatus").innerText = "자동 저장 완료!";
    } catch (error) {
        console.error("저장 오류:", error);
    }
}

// 자동 저장 기능 (1분마다 저장)
function setupAutoSave() {
    console.log("자동 저장 기능 활성화됨!");
    setInterval(saveRadiologistReport, 60000);
}

// 저장 버튼 클릭 이벤트 연결 & 자동 저장 설정
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("saveReportBtn").addEventListener("click", saveRadiologistReport);
    loadRadiologistReport(); // 페이지 로드 시 자동으로 데이터 불러오기
    setupAutoSave(); // **자동 저장 기능 실행**
});
