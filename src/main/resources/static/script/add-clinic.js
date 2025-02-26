document.addEventListener("DOMContentLoaded",function (){
    const patientCode=getPatientCodeFromURL();
    const form = document .getElementById("addClinic-form");
    if (patientCode) {
        document.getElementById("patient-code").textContent = patientCode + "님 진료 등록";
    } else {
        console.error("URL에서 환자 코드를 찾을 수 없습니다.");
    }

    form.addEventListener("submit", async  (event)=>{
        event.preventDefault();

        const userCode = document.getElementById("userCode").value;
        const clinicDate = document.getElementById("clinicDate").value;
        const context = document.getElementById("context").value;

        await addAction(patientCode,userCode,clinicDate,context);
    });


});

// URL에서 환자 코드 추출
function getPatientCodeFromURL(){
    const pathSegments=window.location.pathname.split("/");
    return pathSegments.length > 3 ? pathSegments[3]:null; // /clinic/add/{patientCode} 구조
}

async function addAction(patientCode,userCode,clinicDate,context){
    const response = await fetch("/clinic/action",{
        method:"POST",
        headers:{
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "patientCode":patientCode,
            "userCode":userCode,
            "clinicDate":clinicDate,
            "context":context
        })
    });
    if (response.ok) {
        window.location.href = "/clinic/"+patientCode;
        alert("진료 등록 성공!");
        return true;
    } else {
        const json = await response.json();
        alert(`오류 : ${json.message}`);
        return json.isValid;
    }

}

