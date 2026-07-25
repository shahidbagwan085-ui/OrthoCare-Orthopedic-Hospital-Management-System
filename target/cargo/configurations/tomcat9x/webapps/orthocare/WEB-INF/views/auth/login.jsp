<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Login · OrthoCare</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="login-wrap">
    <div class="login-card">
        <h1>Ortho<span>Care</span></h1>
        <p>Orthopedic Hospital Management System</p>
        <c:if test="${not empty error}"><div class="alert alert-error"><c:out value="${error}"/></div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/auth/login">
            <div class="field">
                <label>Username</label>
                <input name="username" required autofocus>
            </div>
            <div class="field">
                <label>Password</label>
                <input name="password" type="password" required>
            </div>
            <button class="btn btn-primary" style="width:100%">Sign In</button>
        </form>
        <p style="margin-top:16px;font-size:12px;color:#94a3b8">Default admin: <b>admin</b> / <b>admin123</b></p>
    </div>
</div>
</body>
</html>
