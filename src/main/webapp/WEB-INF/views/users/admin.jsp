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
            <button class="showDropdown" data-id="${list.index}">No. ${list.index+1} | CODE : ${user.userCode} | ID : ${user.username} | NAME : ${user.name}</button>

            <div id="dropdownMenu-${list.index}" class="dropdown-menu">
                <form data-id="${list.index}" id="first-tab">
                    <input type="hidden" id="userCode-tab1-${list.index}" name="userCode" value="${user.userCode}">

                    <label for="username-tab1-${list.index}">ID:</label>
                    <input type="text" id="username-tab1-${list.index}" name="username" value="${user.username}">
                    <p class="error-msg" id="error-message-username-${list.index}">아이디가 중복됩니다.</p>

                    <label for="email-tab1-${list.index}">Email:</label>
                    <input type="email" id="email-tab1-${list.index}" name="email" value="${user.email}">
                    <p class="error-msg" id="error-message-email-${list.index}">이메일이 중복됩니다.</p>

                    <div id="dropdown-container">
                        <label for="status-tab1-${list.index}">Status:</label>
                        <label>
                            <select id="status-tab1-${list.index}" name="status">
                                <option value="active" ${user.status == 'active' ? 'selected' : ''}>active</option>
                                <option value="pending" ${user.status == 'pending' ? 'selected' : ''}>pending</option>
                                <option value="suspended" ${user.status == 'suspended' ? 'selected' : ''}>suspended</option>
                            </select>
                        </label>
                        <label id="account" for="accountType-tab1-${list.index}">Account Type:</label>
                        <label>
                            <select id="accountType-tab1-${list.index}" name="accountType">
                                <option value="admin" ${user.accountType == 'admin' ? 'selected' : ''}>admin</option>
                                <option value="user" ${user.accountType == 'user' ? 'selected' : ''}>user</option>
                                <option value="temporary" ${user.accountType == 'temporary' ? 'selected' : ''}>temporary</option>
                            </select>
                        </label>
                    </div>
                    <button class="edit-submit" type="submit">수정 완료</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<div class="tab-content" id="tab2">
    <c:if test="${empty temporaryList}">
        <p>세션에 회원 데이터가 없습니다.</p>
    </c:if>
    <c:forEach var="temporary" items="${temporaryList}" varStatus="list">
        <div class="member-section">
            <button class="showDropdown" data-id="tab2-${list.index}">No. ${list.index+1} | CODE : ${temporary.userCode} | ID : ${temporary.username} | NAME : ${temporary.name}</button>

            <div id="dropdownMenu-tab2-${list.index}" class="dropdown-menu">
                <form data-id="${list.index}" id="second-tab">
                    <input type="hidden" id="userCode-tab2-${list.index}" name="userCode" value="${temporary.userCode}">

                    <label for="userCode-tab2-${list.index}">UserCode:</label>
                    <input type="text" id="userCode-tab2-${list.index}" name="userCode" value="${temporary.userCode}" disabled>

                    <label for="username-tab2-${list.index}">ID:</label>
                    <input type="text" id="username-tab2-${list.index}" name="username" value="${temporary.username}" disabled>

                    <label for="name-tab2-${list.index}">NAME:</label>
                    <input type="text" id="name-tab2-${list.index}" name="name" value="${temporary.name}" disabled>

                    <button class="temporary-submit" type="submit">승인</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<div class="tab-content" id="tab3">
    <c:if test="${empty deleteList}">
        <p>세션에 회원 데이터가 없습니다.</p>
    </c:if>
    <c:forEach var="delete" items="${deleteList}" varStatus="list">
        <div class="member-section">
            <button class="showDropdown" data-id="tab3-${list.index}">No. ${list.index+1} | CODE : ${delete.userCode} | ID : ${delete.username} | NAME : ${delete.name}</button>

            <div id="dropdownMenu-tab3-${list.index}" class="dropdown-menu">
                <form data-id="${list.index}" id="third-tab">
                    <input type="hidden" id="userCode-tab3-${list.index}" name="userCode" value="${delete.userCode}">

                    <label for="name-tab3-${list.index}">UserCode: </label>
                    <input type="text" id="name-tab3-${list.index}" name="name" value="${delete.userCode}" disabled>

                    <label for="email-tab3-${list.index}">ID: </label>
                    <input type="email" id="email-tab3-${list.index}" name="email" value="${delete.username}" disabled>

                    <label for="phone-tab3-${list.index}">NAME: </label>
                    <input type="text" id="phone-tab3-${list.index}" name="phone" value="${delete.name}" disabled>

                    <button class="delete-submit" type="submit">승인</button>
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
