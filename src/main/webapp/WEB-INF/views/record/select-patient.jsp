<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Medical Records" active="records">
    <div class="page-header"><h1>Select a patient</h1></div>
    <div class="card">
        <ul style="list-style:none;padding:0;margin:0">
            <c:forEach var="p" items="${patients}">
                <li style="padding:10px;border-bottom:1px solid #e5e7eb">
                    <a href="${pageContext.request.contextPath}/records?patientId=${p.id}"><c:out value="${p.fullName}"/></a>
                    <span style="color:#94a3b8;font-size:12px"> — ${p.patientCode}</span>
                </li>
            </c:forEach>
        </ul>
    </div>
</t:layout>
