<%--
  Created by IntelliJ IDEA.
  User: TJ
  Date: 2025-02-19
  Time: 오전 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" href="/style/userForm.css">
    <script type="module" src="/script/signup.js"></script>
</head>
<c:import url="/header" />
<body>

<form class="form-box" id="signup-form" action="/users/action/signup" method="POST">
    <h1 class="form-title">Sign Up</h1>
    <div class="input-group" id="input-username">
        <label id="label-username" for="username">ID : </label>
        <input class="input-box" type="text" id="username" name="username">
    </div>
    <p class="error-msg" id="error-username">&nbsp6~20자리의 영문, 숫자로 입력해주세요.</p>
    <div class="input-group" id="input-password">
        <label id="label-password" for="password">PW : </label>
        <input class="input-box" type="password" id="password" name="password">
    </div>
    <p class="error-msg" id="error-password">&nbsp특수기호를 포함한 8~16자리의 비밀번호를 입력해주세요.</p>
    <div class="input-group" id="input-hospital-department">
        <div class="hospital-department">
            <div class="hospital" id="input-hospital">
                <label id="label-hospital" for="hospital">HN : </label>
                <input class="input-box" type="text" id="hospital" name="hospital">
            </div>
            <div class="department" id="input-department">
                <label id="label-department"  for="department">DP : </label>
                <input class="input-box" type="text" id="department" name="department">
            </div>
        </div>
    </div>
    <p class="error-msg" id="error-hospital-department">&nbsp병원이름 혹은 진료과를 입력해주세요.</p>
    <div class="input-group" id="input-name">
        <label id="label-name"  for="name">NAME : </label>
        <input class="input-box" type="text" id="name" name="name">
    </div>
    <p class="error-msg" id="error-name">&nbsp2~5글자의 한글로 입력해주세요.</p>
    <div class="input-group" id="input-email">
        <label id="label-email" for="email">EMAIL : </label>
        <div class="email-container">
            <input class="input-box" type="text" id="email" name="email">
            <button type="button" id="email-verify-btn">Verify</button>
        </div>
    </div>
    <p class="error-msg" id="error-email">이메일 형식에 맞게 입력해주세요.</p>
    <div class="input-group" id="verification-code-container" style="display: none;">
        <label id="label-verification" for="verification-code">Verification Code:</label>
        <input class="input-box" type="text" id="verification-code" name="verification-code">
    </div>
    <p class="error-msg" id="error-verification">인증코드를 입력해주세요.</p>
    <div class="input-group" id="input-phone">
        <label id="label-phone" for="phone">PHONE : </label>
        <input class="input-box" type="text" id="phone" name="phone">
    </div>
    <p class="error-msg" id="error-phone">사용할 수 없는 전화번호입니다.</p>
    <div class="privacy-consent">
        <input type="checkbox" id="privacy-policy" name="privacy-policy" required />
        <label for="privacy-policy">
            개인정보 수집 및 이용 동의 <a href="#" id="privacy-link">전문보기</a>.
        </label>
    </div>
    <div id="privacy-modal" class="modal">
        <div class="privacy-modal-content">
            <span class="close">&times;</span>
            <h2>개인정보 수집 이용 동의</h2>
            <pre class="privacy-text">개인정보보호법에 따라 닥터스캔에 회원가입 신청하시는 분께 수집하는 개인정보의 항목, 개인정보의 수집 및 이용목적, 개인정보의 보유 및 이용기간, 동의 거부권 및 동의 거부 시 불이익에 관한 사항을 안내 드리오니 자세히 읽은 후 동의하여 주시기 바랍니다.


1. 수집하는 개인정보

닥터스캔은 서비스 이용을 위해 필요한 최소한의 개인정보를 수집합니다.

회원가입 시점에 닥터스캔이 이용자로부터 수집하는 개인정보는 아래와 같습니다.

- 회원 가입 시 필수항목으로 아이디, 비밀번호, 이름, 생년월일, 성별, 휴대전화번호, 이메일주소 등을 수집합니다.

서비스 이용 과정에서 이용자로부터 수집하는 개인정보는 아래와 같습니다.

- 회원정보에서 프로필 정보를 설정할 수 있습니다.
- 닥터스캔 내의 개별 서비스 이용 과정에서 해당 서비스의 이용자에 한해 추가 개인정보 수집이 발생할 수 있습니다. 추가로 개인정보를 수집할 경우에는 해당 개인정보 수집 시점에서 이용자에게 ‘수집하는 개인정보 항목, 개인정보의 수집 및 이용목적, 개인정보의 보관기간’에 대해 안내 드리고 동의를 받습니다.

서비스 이용 과정에서 IP 주소, 쿠키, 서비스 이용 기록, 기기정보, 위치정보가 생성되어 수집될 수 있습니다. 또한 이미지 및 음성을 이용한 검색 서비스 등에서 이미지나 음성이 수집될 수 있습니다.

구체적으로 1) 서비스 이용 과정에서 이용자에 관한 정보를 자동화된 방법으로 생성하거나 이용자가 입력한 정보를 저장(수집)하거나,
2) 이용자 기기의 고유한 정보를 원래의 값을 확인하지 못 하도록 안전하게 변환하여 수집합니다.
서비스 이용 과정에서 위치정보가 수집될 수 있으며, 이와 같이 수집된 정보는 개인정보와의 연계 여부 등에 따라 개인정보에 해당할 수 있고, 개인정보에 해당하지 않을 수도 있습니다.

생성정보 수집에 대한 추가 설명

IP(Internet Protocol) 주소란?
IP 주소는 인터넷 망 사업자가 인터넷에 접속하는 이용자의 PC 등 기기에 부여하는 온라인 주소정보 입니다. IP 주소가 개인정보에 해당하는지 여부에 대해서는 각국마다 매우 다양한 견해가 있습니다.

서비스 이용기록이란?
닥터스캔 접속 일시, 이용한 서비스 목록 및 서비스 이용 과정에서 발생하는 정상 또는 비정상 로그 일체 등을 의미합니다. 정보주체가 식별되는 일부 서비스 이용기록(행태정보 포함)과 관련한 처리 목적 등에 대해서는 본 개인정보 처리방침에서 규정하고 있는 수집하는 개인정보, 수집한 개인정보의 이용, 개인정보의 파기 등에서 설명하고 있습니다. 이는 서비스 제공을 위해 수반되는 것으로 이를 거부하시는 경우 서비스 이용에 제한이 있을 수 있으며, 관련하여서는 고객센터로 문의해주시길 바랍니다.

기기정보란?
본 개인정보처리방침에 기재된 기기정보는 생산 및 판매 과정에서 기기에 부여된 정보뿐 아니라, 기기의 구동을 위해 사용되는 S/W를 통해 확인 가능한 정보를 모두 일컫습니다. OS(Windows, MAC OS 등) 설치 과정에서 이용자가 PC에 부여하는 컴퓨터의 이름, PC에 장착된 주변기기의 일련번호, 스마트폰의 통신에 필요한 고유한 식별값(IMEI, IMSI), AAID 혹은 IDFA, 설정언어 및 설정 표준시, USIM의 통신사 코드 등을 의미합니다. 단, 닥터스캔은 IMEI와 같은 기기의 고유한 식별값을 수집할 필요가 있는 경우, 이를 수집하기 전에 닥터스캔도 원래의 값을 알아볼 수 없는 방식으로 암호화 하여 식별성(Identifiability)을 제거한 후에 수집합니다.

쿠키(cookie)란?
쿠키는 이용자가 웹사이트를 접속할 때에 해당 웹사이트에서 이용자의 웹브라우저를 통해 이용자의 PC에 저장하는 매우 작은 크기의 텍스트 파일입니다. 이후 이용자가 다시 웹사이트를 방문할 경우 웹사이트 서버는 이용자 PC에 저장된 쿠키의 내용을 읽어 이용자가 설정한 서비스 이용 환경을 유지하여 편리한 인터넷 서비스 이용을 가능케 합니다. 또한 방문한 서비스 정보, 서비스 접속 시간 및 빈도, 서비스 이용 과정에서 생성된 또는 제공(입력)한 정보 등을 분석하여 이용자의 취향과 관심에 특화된 서비스(광고 포함)를 제공할 수 있습니다. 이용자는 쿠키에 대한 선택권을 가지고 있으며, 웹브라우저에서 옵션을 설정함으로써 모든 쿠키를 허용하거나, 쿠키가 저장될 때마다 확인을 거치거나, 아니면 모든 쿠키의 저장을 거부할 수도 있습니다. 다만, 쿠키의 저장을 거부할 경우에는 로그인이 필요한 닥터스캔 일부 서비스의 이용에 불편이 있을 수 있습니다.
*웹 브라우저에서 쿠키 허용/차단 방법
- 크롬(Chrome) : 웹 브라우저 설정 > 개인정보 보호 및 보안 > 인터넷 사용 기록 삭제
- 엣지(Edge) : 웹 브라우저 설정 > 쿠키 및 사이트 권한 > 쿠키 및 사이트 데이터 관리 및 삭제

*모바일 브라우저에서 쿠키 허용/차단
- 크롬(Chrome) : 모바일 브라우저 설정 > 개인정보 보호 및 보안 > 인터넷 사용 기록 삭제
- 사파리(Safari) : 모바일 기기 설정 > 사파리(Safari) > 고급 > 모든 쿠키 차단
- 삼성 인터넷 : 모바일 브라우저 설정 > 인터넷 사용 기록 > 인터넷 사용 기록 삭제


2. 수집한 개인정보의 이용

닥터스캔 및 닥터스캔 관련 제반 서비스(모바일 웹/앱 포함)의 회원관리, 서비스 개발・제공 및 향상, 안전한 인터넷 이용환경 구축 등 아래의 목적으로만 개인정보를 이용합니다.
- 회원 가입 의사의 확인, 이용자 본인 확인, 이용자 식별, 회원탈퇴 의사의 확인 등 회원관리를 위하여 개인정보를 이용합니다.
- 콘텐츠 등 기존 서비스 제공(광고 포함)에 더하여, 인구통계학적 분석, 서비스 방문 및 이용기록의 분석, 개인정보 및 관심에 기반한 이용자간 관계의 형성, 지인 및 관심사 등에 기반한 맞춤형 서비스 제공 등 신규 서비스 요소의 발굴 및 기존 서비스 개선 등을 위하여 개인정보를 이용합니다. 신규 서비스 요소의 발굴 및 기존 서비스 개선 등에는 정보 검색, 다른 이용자와의 커뮤니케이션, 콘텐츠 생성·제공·추천, 상품 쇼핑 등에서의 인공지능(AI) 기술 적용이 포함됩니다.
- 법령 및 닥터스캔 이용약관을 위반하는 회원에 대한 이용 제한 조치, 부정 이용 행위를 포함하여 서비스의 원활한 운영에 지장을 주는 행위에 대한 방지 및 제재, 계정도용 및 부정거래 방지, 약관 개정 등의 고지사항 전달, 분쟁조정을 위한 기록 보존, 민원처리 등 이용자 보호 및 서비스 운영을 위하여 개인정보를 이용합니다.
- 유료 서비스 제공에 따르는 본인인증, 구매 및 요금 결제, 상품 및 서비스의 배송을 위하여 개인정보를 이용합니다.
- 이벤트 정보 및 참여기회 제공, 광고성 정보 제공 등 마케팅 및 프로모션 목적으로 개인정보를 이용합니다.
- 서비스 이용기록과 접속 빈도 분석, 서비스 이용에 대한 통계, 서비스 분석 및 통계에 따른 맞춤 서비스 제공 및 광고 게재 등에 개인정보를 이용합니다.
- 보안, 프라이버시, 안전 측면에서 이용자가 안심하고 이용할 수 있는 서비스 이용환경 구축을 위해 개인정보를 이용합니다.


3. 개인정보 보관기간

회사는 원칙적으로 이용자의 개인정보를 회원 탈퇴 시 지체없이 파기하고 있습니다. 단, 이용자에게 개인정보 보관기간에 대해 별도의 동의를 얻은 경우, 또는 법령에서 일정 기간 정보보관 의무를 부과하는 경우에는 해당 기간 동안 개인정보를 안전하게 보관합니다.

이용자에게 개인정보 보관기간에 대해 회원가입 시 또는 서비스 가입 시 동의를 얻은 경우는 아래와 같습니다.

- 부정 가입 및 이용 방지
부정 이용자의 DI : 탈퇴일로부터 6개월 보관
탈퇴한 이용자의 휴대전화번호(휴대전화 인증 시, 복호화가 불가능한 일방향 암호화(해시)하여 보관), DI(아이핀 인증 시) : 탈퇴일로부터 6개월 보관
- 닥터스캔 서비스 제공을 위한 본인 확인
통신사 정보, 휴대전화번호 : 수집 시점으로부터 1년 보관

전자상거래 등에서의 소비자 보호에 관한 법률, 전자문서 및 전자거래 기본법, 통신비밀보호법 등 법령에서 일정기간 정보의 보관을 규정하는 경우는 아래와 같습니다. 닥터스캔은 이 기간 동안 법령의 규정에 따라 개인정보를 보관하며, 본 정보를 다른 목적으로는 절대 이용하지 않습니다.

- 전자상거래 등에서 소비자 보호에 관한 법률
계약 또는 청약철회 등에 관한 기록: 5년 보관
대금결제 및 재화 등의 공급에 관한 기록: 5년 보관
소비자의 불만 또는 분쟁처리에 관한 기록: 3년 보관
- 전자문서 및 전자거래 기본법
공인전자주소를 통한 전자문서 유통에 관한 기록: 10년 보관
- 통신비밀보호법
로그인 기록: 3개월


4. 개인정보 수집 및 이용 동의를 거부할 권리

이용자는 개인정보의 수집 및 이용 동의를 거부할 권리가 있습니다. 회원가입 시 수집하는 최소한의 개인정보, 즉, 필수 항목에 대한 수집 및 이용 동의를 거부하실 경우, 회원가입이 어려울 수 있습니다.</pre>
        </div>
    </div>
    <button type="submit">Sign Up</button>
</form>


</body>
<c:import url="/footer" />
</html>
