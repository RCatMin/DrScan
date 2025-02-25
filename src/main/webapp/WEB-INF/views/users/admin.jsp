<%--
  Created by IntelliJ IDEA.
  User: TJ
  Date: 2025-02-19
  Time: 오후 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>4개의 탭</title>
    <link rel="stylesheet" href="/style/admin.css">
    <script type="module" src="/script/admin.js"></script>
</head>
<c:import url="/header" />
<body>

<div class="tabs">
    <div id="none">관리자 : admin</div>
    <div class="tab active">회원관리</div>
    <div class="tab">가입승인</div>
    <div class="tab">탈퇴승인</div>
    <div class="tab" id="tab-4">로그</div>
</div>

<div class="tab-content active" id="tab1">
    <c:if test="${empty userList}">
        <p>세션에 회원 데이터가 없습니다.</p>
    </c:if>
    <c:forEach var="user" items="${userList}" varStatus="list">
        <div class="member-section">
            <button class="showDropdown" data-id="${list.index}">${user.name} 정보 수정</button>

            <div id="dropdownMenu-${list.index}" class="dropdown-menu">
                <form>
                    <label for="username-${list.index}">ID:</label>
                    <input type="text" id="username-${list.index}" name="username" value="${user.username}"><br>

                    <label for="email-${list.index}">Email:</label>
                    <input type="email" id="email-${list.index}" name="email" value="${user.email}"><br>

                    <label for="status-${list.index}">Status:</label>
                    <input type="text" id="status-${list.index}" name="status" value="${user.status}"><br>

                    <label for="accountType-${list.index}">Account Type:</label>
                    <input type="text" id="accountType-${list.index}" name="accountType" value="${user.accountType}"><br>

                    <button type="submit">수정 완료</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<div class="tab-content" id="tab2">
    <c:if test="${empty temporaryList}">
        <p>세션에 회원 데이터가 없습니다.</p>
    </c:if>
    <c:forEach var="temporary" items="${temporaryList}" varStatus="status">
        <div class="member-section">
            <button class="showDropdown" data-id="tab2-${status.index}">${temporary.name} 정보 수정</button>

            <div id="dropdownMenu-tab2-${status.index}" class="dropdown-menu">
                <form>
                    <label for="userCode-${status.index}">UserCode:</label>
                    <input type="text" id="userCode-${status.index}" name="userCode" value="${temporary.userCode}"><br>

                    <label for="username-${status.index}">ID:</label>
                    <input type="text" id="username-${status.index}" name="username" value="${temporary.username}"><br>

                    <label for="name-${status.index}">NAME:</label>
                    <input type="text" id="name-${status.index}" name="name" value="${temporary.name}"><br>

                    <button type="submit">승인</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<div class="tab-content" id="tab3">
    <c:if test="${empty deleteList}">
        <p>세션에 회원 데이터가 없습니다.</p>
    </c:if>
    <c:forEach var="delete" items="${deleteList}" varStatus="status">
        <div class="member-section">
            <button class="showDropdown" data-id="${status.index}">${delete.name} 정보 수정</button>

            <div id="dropdownMenu-${status.index}" class="dropdown-menu">
                <form>
                    <label for="name-${status.index}">이름:</label>
                    <input type="text" id="name-${status.index}" name="name" value="${delete.name}"><br>

                    <label for="email-${status.index}">이메일:</label>
                    <input type="email" id="email-${status.index}" name="email" value="${delete.email}"><br>

                    <label for="phone-${status.index}">전화번호:</label>
                    <input type="text" id="phone-${status.index}" name="phone" value="${delete.phone}"><br>

                    <button type="submit">승인</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>
<div class="tab-content" id="tab4">

</div>

</body>
<c:import url="/footer" />
</html>
