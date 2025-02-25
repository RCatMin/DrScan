window.onload = function () {
    loadDicomImages();
};

function loadDicomImages() {
    const urlParts = window.location.pathname.split("/");
    const pid = urlParts[3];
    const studyKey = urlParts[4];
    const seriesKey = urlParts[5];

    console.log("pid: " + pid + " studyKey: " + studyKey + " seriesKey: " + seriesKey);
    fetch(`/patientScan/action/images/${studyKey}/${seriesKey}`)
        .then(response => response.json())
        .then(images => {
            if (images.length === 0) {
                document.getElementById("dicomContainer").innerHTML = "<p>해당 시리즈에 대한 DICOM 이미지가 없습니다.</p>";
                return;
            }

            images.forEach(image => {
                let imgElement = document.createElement("img");
                imgElement.src = "/dicom-viewer?path=" + encodeURIComponent(image.path); // DICOM 뷰어 경로 설정
                imgElement.style.width = "512px";
                imgElement.style.height = "512px";
                imgElement.alt = "DICOM Image";

                document.getElementById("dicomContainer").appendChild(imgElement);
            });
        })
        .catch(error => console.error("이미지 로드 중 에러 발생:", error));
}
