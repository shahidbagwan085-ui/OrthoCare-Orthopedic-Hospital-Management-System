<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Doctor Form" active="doctors">
    <div class="page-header"><h1><c:choose><c:when test="${doctor.id gt 0}">Edit Doctor</c:when><c:otherwise>New Doctor</c:otherwise></c:choose></h1></div>
    <c:if test="${not empty error}"><div class="alert alert-error"><c:out value="${error}"/></div></c:if>
    <div class="card">
    <form method="post" action="${pageContext.request.contextPath}/doctors">
        <input type="hidden" name="id" value="${doctor.id}">
        <input type="hidden" name="userId" value="${doctor.userId}">
        <div class="row">
            <div class="field"><label>Full Name *</label><input name="fullName" required value="<c:out value='${doctor.fullName}'/>"></div>
            <c:if test="${empty doctor or doctor.id eq 0}">
                <div class="field"><label>Username *</label><input name="username" required></div>
                <div class="field"><label>Password *</label><input name="password" type="password" required></div>
            </c:if>
            <div class="field"><label>Qualification</label><input name="qualification" value="${doctor.qualification}"></div>
            <div class="field"><label>Experience (years)</label><input name="experienceYears" type="number" value="${doctor.experienceYears}"></div>
            <div class="field"><label>Specialization</label><input name="specialization" value="${doctor.specialization}"></div>
            <div class="field"><label>Consultation Fee</label><input name="consultationFee" type="number" step="0.01" value="${doctor.consultationFee}"></div>
            <div class="field"><label>Department</label>
                <select name="department">
                    <option value="ORTHOPEDICS" ${doctor.department=='ORTHOPEDICS'?'selected':''}>Orthopedics</option>
                    <option value="PHYSIOTHERAPY" ${doctor.department=='PHYSIOTHERAPY'?'selected':''}>Physiotherapy</option>
                </select>
            </div>
            <div class="field"><label>Available Days</label><input name="availableDays" placeholder="Mon, Tue, Wed" value="${doctor.availableDays}"></div>
            <div class="field"><label>Available Times</label><input name="availableTimes" placeholder="10:00 - 17:00" value="${doctor.availableTimes}"></div>
            <div class="field"><label>Contact</label><input name="contact" value="${doctor.contact}"></div>
            <div class="field"><label>Email</label><input name="email" type="email" value="${doctor.email}"></div>
        </div>
        <div class="actions">
            <button class="btn btn-primary">Save</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/doctors">Cancel</a>
        </div>
    </form>
    </div>
</t:layout>
