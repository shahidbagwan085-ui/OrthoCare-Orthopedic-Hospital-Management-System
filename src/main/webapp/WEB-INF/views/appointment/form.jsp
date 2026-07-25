<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Appointment Form" active="appointments">
    <div class="page-header"><h1><c:choose><c:when test="${appointment.id gt 0}">Edit Appointment</c:when><c:otherwise>Book Appointment</c:otherwise></c:choose></h1></div>
    <c:if test="${not empty error}"><div class="alert alert-error"><c:out value="${error}"/></div></c:if>
    <div class="card">
    <form method="post" action="${pageContext.request.contextPath}/appointments">
        <input type="hidden" name="id" value="${appointment.id}">
        <div class="row">
            <div class="field"><label>Appointment Code (auto)</label><input name="appointmentCode" value="${appointment.appointmentCode}"></div>
            <div class="field"><label>Patient *</label>
                <select name="patientId" required>
                    <option value="">-- select --</option>
                    <c:forEach var="p" items="${patients}">
                        <option value="${p.id}" ${appointment.patientId==p.id?'selected':''}><c:out value="${p.fullName}"/> (${p.patientCode})</option>
                    </c:forEach>
                </select>
            </div>
            <div class="field"><label>Doctor *</label>
                <select name="doctorId" required>
                    <option value="">-- select --</option>
                    <c:forEach var="d" items="${doctors}">
                        <option value="${d.id}" ${appointment.doctorId==d.id?'selected':''}><c:out value="${d.fullName}"/> — ${d.specialization}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="field"><label>Department</label>
                <select name="department">
                    <option value="ORTHOPEDICS" ${appointment.department=='ORTHOPEDICS'?'selected':''}>Orthopedics</option>
                    <option value="PHYSIOTHERAPY" ${appointment.department=='PHYSIOTHERAPY'?'selected':''}>Physiotherapy</option>
                </select>
            </div>
            <div class="field"><label>Date & Time *</label><input name="appointmentDt" type="datetime-local" required value="${appointment.appointmentDt}"></div>
            <div class="field"><label>Status</label>
                <select name="status">
                    <c:forEach var="s" items="PENDING,CONFIRMED,COMPLETED,CANCELLED">
                        <option value="${s}" ${appointment.status==s?'selected':''}>${s}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="field"><label>Reason</label><textarea name="reason" rows="3"><c:out value='${appointment.reason}'/></textarea></div>
        <div class="actions">
            <button class="btn btn-primary">Save</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/appointments">Cancel</a>
        </div>
    </form>
    </div>
</t:layout>
