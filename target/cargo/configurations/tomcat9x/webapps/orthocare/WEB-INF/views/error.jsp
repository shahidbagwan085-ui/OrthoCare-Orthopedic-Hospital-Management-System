<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html><head><title>Error · OrthoCare</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"></head>
<body>
<div class="container">
    <div class="card">
        <h1>Something went wrong</h1>
        <p style="color:#64748b">An error occurred while processing your request.</p>
        <% if (exception != null) { %>
            <pre style="background:#f8fafc;padding:12px;border-radius:6px;overflow:auto;font-size:12px"><%= exception.getMessage() %></pre>
        <% } %>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/dashboard">Back to Dashboard</a>
    </div>
</div>
</body></html>
