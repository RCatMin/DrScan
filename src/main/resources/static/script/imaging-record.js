import * as cornerstone from '@cornerstonejs/core';
import { init as coreInit, RenderingEngine, Enums } from '@cornerstonejs/core';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import dicomParser from 'dicom-parser';
import { init as dicomImageLoaderInit } from '@cornerstonejs/dicom-image-loader';
import { addTool, ToolGroupManager, PanTool, ZoomTool, WindowLevelTool, LengthTool, AngleTool } from '@cornerstonejs/tools';

let images = [];
let currentIndex = 0;
let imagesPerPage = 12;
let paginationSize = 5;
let currentPage = 0;
let totalPages = 0;
let paginationStart = 0;
let renderingEngine;

addTool(PanTool);
addTool(ZoomTool);
addTool(WindowLevelTool);
addTool(LengthTool);
addTool(AngleTool);

const toolGroupId = "DEFAULT_TOOLGROUP";

let toolGroup = ToolGroupManager.getToolGroup(toolGroupId);

if (!toolGroup) {
    console.log("🔨 툴 그룹 생성 중...");
    ToolGroupManager.createToolGroup(toolGroupId);
    toolGroup = ToolGroupManager.getToolGroup(toolGroupId);
}

// ✅ cornerstoneTools에 툴을 강제로 추가
const toolsToAdd = [PanTool, ZoomTool, WindowLevelTool, LengthTool, AngleTool];
toolsToAdd.forEach(tool => {
    if (!toolGroup.getToolInstance(tool.toolName)) {
        console.log(`🔧 툴 등록: ${tool.toolName}`);
        toolGroup.addTool(tool.toolName);
    }
});

// ✅ 툴 활성화 (디폴트: PanTool)
console.log("🚀 PanTool 활성화");
toolGroup.setToolActive(PanTool.toolName, { bindings: [{ mouseButton: 1 }] });

window.onload = function () {
    if (ensureWebGLContext()) {
        initializeCornerstone();
    }

};

document.addEventListener("DOMContentLoaded", async () => {
    console.log("페이지 로드 완료, 초기화 시작");

    // 유저 정보 확인
    await fetchAuthUser();
    
    // 🛠 초기 데이터 로드
    loadStudyAndSeriesInfo();
    await loadPatientInfo();

    // 🛠 판독 데이터 로드 (최신 데이터 가져오기)
    await loadLatestRadiologistReport();

    // 버튼 이벤트 등록
    setupEventListeners();
});

// 최신 판독 데이터 로드 함수
async function loadLatestRadiologistReport() {
    const patientIdElement = document.getElementById("patientId");
    if (!patientIdElement || !patientIdElement.textContent.trim()) {
        console.warn("환자 ID가 아직 설정되지 않음, 판독 데이터 로드를 중단합니다.");
        return;
    }

    const patientId = patientIdElement.textContent.trim();

    try {
        const response = await fetch(`/patientScan/action/latest/${patientId}`);
        if (!response.ok) throw new Error(`서버 오류: ${response.statusText}`);

        const report = await response.json();
        console.log("최신 판독 데이터 응답:", report);

        if (!report || !report.reportCode) {
            console.warn("판독 데이터가 없음!");
            return;
        }

        updateReportFields(report);
    } catch (error) {
        console.error("판독 데이터 로드 중 오류 발생:", error);
    }
}

// 버튼 이벤트 핸들러 설정
function setupEventListeners() {
    // 이미지 초기화 버튼
    const resetBtn = document.getElementById("resetBtn");
    if (resetBtn) resetBtn.addEventListener("click", resetImage);

    // 다중 뷰포트 버튼
    const multiViewportBtn = document.getElementById("multiViewportBtn");
    if (multiViewportBtn) multiViewportBtn.addEventListener("click", createMultiViewport);

    // 히스토그램 조정 버튼
    const histogramAdjustBtn = document.getElementById("histogramAdjustBtn");
    if (histogramAdjustBtn) histogramAdjustBtn.addEventListener("click", () => adjustHistogram(30, 200));

    // 판독 목록 버튼 (환자 ID를 URL에 추가)
    const editReportBtn = document.getElementById("editReportBtn");
    if (editReportBtn) {
        editReportBtn.addEventListener("click", () => {
            const pidElement = document.getElementById("patientId");
            const pid = pidElement ? pidElement.textContent.trim() : "defaultPid";

            if (!pid || pid === "-") {
                alert("환자 ID가 없습니다! 다시 확인해주세요.");
                console.error("환자 ID가 올바르지 않습니다.");
                return;
            }

            const targetUrl = `/patientScan/radiology/${pid}`;
            console.log(`페이지 이동: ${targetUrl}`);
            window.location.href = targetUrl;
        });
    }

    // 뷰어 툴 버튼들 가져오기 & 이벤트 연결
    setTimeout(() => {
        console.log("뷰어 툴 버튼 이벤트 등록 시작");
        const toolMappings = {
            "zoomBtn": ZoomTool.toolName,
            "panBtn": PanTool.toolName,
            "windowLevelBtn": WindowLevelTool.toolName,
            "lengthMeasureBtn": LengthTool.toolName,
            "angleMeasureBtn": AngleTool.toolName
        };

        for (const [buttonId, toolName] of Object.entries(toolMappings)) {
            const btn = document.getElementById(buttonId);
            if (btn) {
                btn.addEventListener("click", () => activateTool(toolName));
                console.log(`버튼 연결 완료: ${buttonId} → ${toolName}`);
            } else {
                console.error(`버튼을 찾을 수 없음: ${buttonId}`);
            }
        }
    }, 1000);
}


// 최신 데이터 화면에 반영하는 함수
function updateReportFields(report) {
    const severityLevelElem = document.getElementById("severityLevel");
    if (severityLevelElem) severityLevelElem.value = report.severityLevel || "1";

    const reportStatusElem = document.getElementById("reportStatus");
    if (reportStatusElem) reportStatusElem.value = report.reportStatus || "Draft";

    const reportTextElem = document.getElementById("reportText");
    if (reportTextElem) reportTextElem.value = report.reportText || "";
}

// 툴 활성화 함수
function activateTool(toolName) {
    const toolGroupId = "DEFAULT_TOOLGROUP";
    const toolGroup = ToolGroupManager.getToolGroup(toolGroupId);

    if (!toolGroup) {
        console.error("🔴 툴 그룹을 찾을 수 없습니다!");
        return;
    }

    // 툴이 등록되지 않았다면 자동 추가
    if (!toolGroup.getToolInstance(toolName)) {
        console.warn(`⚠ 툴이 등록되지 않음: ${toolName}, 자동 추가`);
        toolGroup.addTool(toolName);
    }

    // 기존 활성화된 툴 모두 비활성화
    ["Pan", "Zoom", "WindowLevel", "Length", "Angle"].forEach(tool => {
        if (toolGroup.getToolInstance(tool)) {
            toolGroup.setToolPassive(tool);
        }
    });

    console.log(`🔧 툴 활성화: ${toolName}`);
    toolGroup.setToolActive(toolName, { bindings: [{ mouseButton: 1 }] });
}




async function initializeCornerstone() {
    await coreInit();

    cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
    cornerstoneDICOMImageLoader.external.dicomParser = dicomParser;

    cornerstoneDICOMImageLoader.configure({
        webWorkerPath: 'https://unpkg.com/@cornerstonejs/dicom-image-loader/dist/umd/worker.min.js',
        taskConfiguration: {
            decodeTask: {
                initializeCodecsOnStartup: false,
                usePDFJS: false
            }
        }
    });

    initializeRenderingEngine();
    registerAllTools(); // ✅ cornerstoneTools 툴 등록 후
    createToolGroup();  // ✅ cornerstoneTools가 완전히 로드된 후 실행
    loadDicomImages();
}

function initializeRenderingEngine() {
    if (renderingEngine) {
        console.warn("⚠ 기존 WebGL 컨텍스트 제거 후 재생성")
        renderingEngine.destroy();
        renderingEngine = null;
    }

    renderingEngine = new RenderingEngine("cornerstoneRenderingEngine");

    console.log("✅ 새 렌더링 엔진 생성됨:", renderingEngine);

    // ✅ 뷰포트 등록
    const viewportId = "dicomViewport";
    const viewportInput = {
        viewportId,
        element: document.getElementById(viewportId),
        type: Enums.ViewportType.STACK,
    };

    renderingEngine.enableElement(viewportInput);

    // ✅ 뷰포트 활성화 후에 툴 그룹을 생성해야 함
    setTimeout(() => {
        createToolGroup();
    }, 1000);
}

function registerAllTools() {
    const tools = [PanTool, ZoomTool, WindowLevelTool, LengthTool, AngleTool];

    tools.forEach(tool => {
        addTool(tool);
        console.log(`🔧 cornerstoneTools에 툴 추가됨: ${tool.toolName}`);
    });

    console.log("✅ cornerstoneTools 툴 등록 완료!");
}

function createToolGroup() {
    const toolGroupId = "DEFAULT_TOOLGROUP";
    let toolGroup = ToolGroupManager.getToolGroup(toolGroupId);

    if (!toolGroup) {
        console.log("🛠 새 툴 그룹 생성 중...");
        ToolGroupManager.createToolGroup(toolGroupId);
        toolGroup = ToolGroupManager.getToolGroup(toolGroupId);
    }

    if (!toolGroup) {
        console.error("🔴 툴 그룹이 생성되지 않았습니다!");
        return;
    }

    console.log("✅ 툴 그룹 로드 완료!");

    // 🛠 툴 추가
    const toolsToAdd = [PanTool, ZoomTool, WindowLevelTool, LengthTool, AngleTool];

    toolsToAdd.forEach(tool => {
        if (!toolGroup.getToolInstance(tool.toolName)) {
            console.log(`🔧 툴 등록: ${tool.toolName}`);
            toolGroup.addTool(tool.toolName);
        }
    });

    // ✅ 뷰포트 추가
    const viewportId = "dicomViewport";
    toolGroup.addViewport(viewportId, "cornerstoneRenderingEngine");

    console.log(`📌 뷰포트 '${viewportId}' 툴 그룹에 추가 완료!`);
    toolGroup.setToolActive(PanTool.toolName, { bindings: [{ mouseButton: 1 }] });
}

function ensureWebGLContext() {
    const canvas = document.createElement("canvas");
    const gl = canvas.getContext("webgl") || canvas.getContext("experimental-webgl");
    if (!gl) {
        console.error("🚨 WebGL을 사용할 수 없습니다!");
        alert("WebGL을 활성화해주세요.");
        return false;
    }
    console.log("✅ WebGL 정상 작동!");
    return true;
}

// ✅ 초기화
function resetImage() {
    console.log("🔄 이미지 초기화!");
    displayImage(0);
}

// ✅
function updateToolPanel(activeTool) {
    document.getElementById("activeTool").innerText = `현재 툴: ${activeTool}`;
}

// ✅ 다중 뷰포트 생성
function createMultiViewport(rows = 2, cols = 2) {
    console.log(`📤 ${rows}x${cols} 다중 뷰포트 생성!`);

    const renderingEngine = new RenderingEngine("multiViewportEngine");

    const viewports = [];
    for (let i = 0; i < rows * cols; i++) {
        const viewportId = `viewport${i + 1}`;
        const viewportElement = document.getElementById(viewportId);

        if (!viewportElement) {
            console.error(`❌ '${viewportId}' 요소가 없습니다!`);
            return;
        }

        viewports.push({
            viewportId,
            element: viewportElement,
            type: "stack",
        });
    }

    renderingEngine.setViewports(viewports);
}

// ✅ 히스토그램 조정 기능
function adjustHistogram(minValue, maxValue) {
    console.log(`📊 히스토그램 조정: min=${minValue}, max=${maxValue}`);

    const renderingEngine = cornerstone.getRenderingEngine("cornerstoneRenderingEngine");
    if (!renderingEngine) {
        console.error("❌ 렌더링 엔진이 없습니다!");
        return;
    }

    const viewport = renderingEngine.getViewport("dicomViewport");
    if (!viewport) {
        console.error("❌ 뷰포트를 찾을 수 없습니다!");
        return;
    }

    if (!viewport.voi) {
        console.warn("⚠ VOI 정보가 없습니다. 기본값을 설정합니다.");
        viewport.voi = { windowWidth: 150, windowCenter: 75 }; // 기본값 설정
    }

    viewport.voi.windowWidth = maxValue - minValue;
    viewport.voi.windowCenter = (maxValue + minValue) / 2;
    viewport.render();
}

// ✅ 저장 버튼 이벤트 연결
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("saveReportBtn").addEventListener("click", saveRadiologistReport);
    setupAutoSave();
});

// ✅ 자동 저장 기능
function setupAutoSave() {
    console.log("자동 저장 기능 활성화됨!");
    setInterval(saveRadiologistReport, 60000);
}

// ✅ 마우스 휠 이벤트 추가 (이미지 스크롤)
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("viewerContainer").addEventListener("wheel", (event) => {
        event.preventDefault();
        if (event.deltaY > 0 && currentIndex < images.length - 1) {
            displayImage(currentIndex + 1);
        } else if (event.deltaY < 0 && currentIndex > 0) {
            displayImage(currentIndex - 1);
        }
    });
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

        console.log("DICOM 이미지 목록 불러오는 중...");

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
    console.log(`DICOM 파일 가져오기: ${dicomFilePath}`);

    fetch(`/patientScan/action/getDicomFile?path=${encodeURIComponent(dicomFilePath)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`서버 응답 오류: ${response.statusText}`);
            }
            return response.arrayBuffer();
        })
        .then(arrayBuffer => {
            console.log("DICOM 데이터 가져옴, 렌더링 시작...");
            renderImage(arrayBuffer, viewportId);
        })
        .catch(error => console.error("DICOM 파일 로딩 중 오류 발생:", error));
}

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

// yyyyMMdd → YYYY-MM-DDTHH:mm:ss 변환 (LocalDateTime 대응)
function formatTimestampString(dateString) {
    if (!dateString || dateString.length < 10) {
        return new Date().toISOString().substring(0, 19); // 현재 시간 ISO 형식
    }
    return `${dateString}T00:00:00`; // LocalDateTime 대응
}

async function saveRadiologistReport() {
    console.log("판독 데이터 저장 시도!");

    // 판독 내용이 없으면 저장하지 않음
    const reportTextElem = document.getElementById("reportText");
    if (!reportTextElem || reportTextElem.value.trim() === "") {
        console.warn("판독 내용이 없어 자동 저장을 중단합니다.");
        return;
    }

    function getElementValue(id, defaultValue = "N/A") {
        const elem = document.getElementById(id);
        return elem ? (elem.value || elem.innerText || defaultValue) : defaultValue;
    }

    function parseIntegerValue(id, defaultValue = 0) {
        const value = getElementValue(id, defaultValue);
        return isNaN(parseInt(value)) ? defaultValue : parseInt(value);
    }

    function calculateAge(birthDateString) {
        if (!birthDateString || birthDateString.length !== 8) {
            return "Unknown"; // 잘못된 입력값 처리
        }

        const birthYear = parseInt(birthDateString.substring(0, 4), 10);
        const birthMonth = parseInt(birthDateString.substring(4, 6), 10) - 1; // JS는 0부터 시작
        const birthDay = parseInt(birthDateString.substring(6, 8), 10);

        const today = new Date();
        const birthDate = new Date(birthYear, birthMonth, birthDay);

        let age = today.getFullYear() - birthDate.getFullYear();
        const monthDiff = today.getMonth() - birthDate.getMonth();
        const dayDiff = today.getDate() - birthDate.getDate();

        // 생일이 지나지 않았으면 나이 -1
        if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
            age--;
        }

        return age;
    }

    const seriesInsUid = window.location.pathname.split("/")[5];

    const reportData = {
        seriesInsUid: seriesInsUid,
        patientId: getElementValue("patientId"),
        patientName: getElementValue("patientName"),
        patientSex: getElementValue("patientSex"),
        patientBirthDate: formatTimestampString(getElementValue("patientBirth")),
        patientAge: calculateAge(getElementValue("patientBirth")),
        studyDate: formatTimestampString(getElementValue("studyDate")),
        studyName: getElementValue("studyDesc"),
        modality: getElementValue("modality"),
        bodyPart: getElementValue("bodyPart"),
        severityLevel: getElementValue("severityLevel"),
        reportStatus: getElementValue("reportStatus"),
        reportText: getElementValue("reportText"),
        userCode: parseIntegerValue("userCode"),
        approveUserCode: parseIntegerValue("approveUserCode"),
        approveStudyDate: formatTimestampString(getElementValue("approveStudyDate")),
        regDate: new Date().toISOString(),
        modDate: new Date().toISOString()
    };

    console.log("보낼 데이터:", reportData);

    try {
        let response = await fetch("/patientScan/action/reports/save", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(reportData),
        });

        let result = await response.json();
        console.log("저장 완료:", result);

        const statusElement = document.getElementById("autoSaveStatus");
        if (statusElement) {
            statusElement.innerText = "저장 완료!";
            statusElement.style.color = "green";

            // 5초 후 메시지 자동 삭제
            setTimeout(() => {
                statusElement.innerText = "";
            }, 5000);
        }
        document.getElementById("autoSaveStatus").innerText = "저장 완료!";
    } catch (error) {
        console.error("저장 오류:", error);
    }
}

async function fetchAuthUser() {
    try {
        const response = await fetch("/patientScan/action/authUser");
        if (!response.ok) throw new Error("유저 정보를 가져오지 못했습니다.");

        const authUser = await response.json();
        console.log("세션에서 가져온 유저 정보:", authUser);

        // HTML 요소 업데이트 (userCode 표시)
        document.getElementById("userCode").innerText = authUser.userCode;

    } catch (error) {
        console.error("세션 정보 가져오기 실패:", error);
        document.getElementById("userCode").innerText = "-"; // 에러 시 기본값
    }
}
