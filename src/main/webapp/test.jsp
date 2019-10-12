<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<h2>
    <br>
    <strong class="d-block text-gray-dark">${requestScope.user.name}</strong>
    <br>
    <strong class="d-block text-gray-dark">${user.email}</strong>
    <br>
    <strong class="d-block text-gray-dark">${user}</strong>
    <br>
    <fmt:formatDate value="${requestScope.now}" pattern="yyyy-MM-dd HH:mm:ss"/>
</h2>

<h3>
    <c:forEach items="${users}" var="user">
        <c:catch var="exp">
        <c:if test= '${!fn:startsWith(user.name, "V")}'>
            <div>
                Users : ${user}
            </div>
        </c:if>
         <br>
        </c:catch>
        <p>${exp}</p>
    </c:forEach>
</h3>

<p>
    Me from undefined scope = ${me} <br>
    Me from request scope = ${requestScope.me} <br>
    Me from session scope = ${sessionScope.me} <br>
    Me from session scope without get method= ${sessionScope.me} <br>
</p>
</body>
</html>