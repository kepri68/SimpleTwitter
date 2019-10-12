<%--
  Created by IntelliJ IDEA.
  User: akepr
  Date: 06.10.2019
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login JSP</title>
</head>
<body>
<form action="login" method="post">
    <div>
        <input name="login" placeholder="Login" type="text">
    </div>
    <br>
    <div>
        <input name="password" placeholder="******" type="password">
    </div>
    <div>
        <br>
        <button type="submit">
            Login
        </button>
    </div>
    <br>
    <p><a href="register">Sign Up</a></p>
</form>
</body>
</html>
