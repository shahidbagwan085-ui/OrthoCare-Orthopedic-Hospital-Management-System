<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Doctors" active="doctors">
    <div class="page-header">
        <h1>Doctors</h1>
        <a href="${pageContext.request.contextPath}/doctors/new" class="btn btn-primary">+ New Doctor</a>
    </div>
    <table>
        <thead><tr><th>Name</th><th>Qualification</th><th>Specialization</th><th>Dept</th><th>Exp</th><th>Fee</th><th>Contact</th><th></th></tr></thead>
        <tbody>
        <c:forEach var="d" items="${doctors}">
            <tr>
                <td><c:out value="${d.fullName}"/></td>
                <td>${d.qualification}</td>
                <td>${d.specialization}</td>
                <td>${d.department}</td>
                <td>${d.experienceYears} y</td>
                <td><fmt:formatNumber value="${d.consultationFee}" type="currency" currencySymbol="₹"/></td>
                <td>${d.contact}</td>
                <td>
                    <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/doctors/edit?id=${d.id}">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/doctors/delete?id=${d.id}" onclick="return confirm('Delete this doctor?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty doctors}"><tr><td colspan="8" style="text-align:center;color:#94a3b8;padding:24px">No doctors yet.</td></tr></c:if>
        </tbody>
    </table>
</t:layout>
