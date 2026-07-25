<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="New Bill" active="billing">
    <div class="page-header"><h1>Generate Bill</h1></div>
    <div class="card">
    <form method="post" action="${pageContext.request.contextPath}/billing">
        <div class="row">
            <div class="field"><label>Patient *</label>
                <select name="patientId" required>
                    <option value="">-- select --</option>
                    <c:forEach var="p" items="${patients}">
                        <option value="${p.id}"><c:out value="${p.fullName}"/> (${p.patientCode})</option>
                    </c:forEach>
                </select>
            </div>
            <div class="field"><label>Appointment (optional)</label>
                <select name="appointmentId">
                    <option value="0">-- none --</option>
                    <c:forEach var="a" items="${appointments}">
                        <option value="${a.id}">${a.appointmentCode}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="field"><label>Total Amount *</label><input name="totalAmount" type="number" step="0.01" required></div>
            <div class="field"><label>Discount</label><input name="discount" type="number" step="0.01" value="0"></div>
        </div>
        <p style="color:#64748b;font-size:13px">GST (18%) is calculated automatically. Net = Total + GST − Discount.</p>
        <div class="actions">
            <button class="btn btn-primary">Generate Bill</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/billing">Cancel</a>
        </div>
    </form>
    </div>
</t:layout>
