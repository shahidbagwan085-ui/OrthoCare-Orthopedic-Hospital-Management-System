<%@ tag description="Main layout" pageEncoding="UTF-8" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="active" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${title} · OrthoCare</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>
<header class="navbar">
    <div class="brand"><i data-lucide="activity" style="color:#2563eb; width:20px; height:20px;"></i>Ortho<span>Care</span></div>
    <nav>
        <a href="${pageContext.request.contextPath}/dashboard" class="${active=='dashboard'?'active':''}"><i data-lucide="layout-dashboard" class="nav-icon"></i>Dashboard</a>
        <a href="${pageContext.request.contextPath}/patients" class="${active=='patients'?'active':''}"><i data-lucide="users" class="nav-icon"></i>Patients</a>
        <a href="${pageContext.request.contextPath}/doctors" class="${active=='doctors'?'active':''}"><i data-lucide="stethoscope" class="nav-icon"></i>Doctors</a>
        <a href="${pageContext.request.contextPath}/appointments" class="${active=='appointments'?'active':''}"><i data-lucide="calendar" class="nav-icon"></i>Appointments</a>
        <a href="${pageContext.request.contextPath}/records" class="${active=='records'?'active':''}"><i data-lucide="clipboard-list" class="nav-icon"></i>Records</a>
        <a href="${pageContext.request.contextPath}/prescriptions" class="${active=='prescriptions'?'active':''}"><i data-lucide="pill" class="nav-icon"></i>Prescriptions</a>
        <a href="${pageContext.request.contextPath}/billing" class="${active=='billing'?'active':''}"><i data-lucide="credit-card" class="nav-icon"></i>Billing</a>
    </nav>
    <div class="user">
        <c:if test="${not empty sessionScope.currentUser}">
            <i data-lucide="user" style="width:14px; height:14px; color:hsl(var(--muted-foreground));"></i>
            ${sessionScope.currentUser.fullName} (${sessionScope.currentUser.role})
            &nbsp;·&nbsp;
            <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
        </c:if>
    </div>
</header>
<main class="container">
    <jsp:doBody/>
</main>
<script>
    lucide.createIcons();
</script>
</body>
</html>
