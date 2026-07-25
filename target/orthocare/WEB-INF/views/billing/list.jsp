<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Billing" active="billing">
    <div class="page-header">
        <h1>Bills</h1>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/billing/new">+ New Bill</a>
    </div>
    <table>
        <thead><tr><th>Bill #</th><th>Patient</th><th>Total</th><th>GST</th><th>Discount</th><th>Net</th><th>Status</th><th></th></tr></thead>
        <tbody>
        <c:forEach var="b" items="${bills}">
            <tr>
                <td>${b.billNumber}</td>
                <td><c:out value="${b.patientName}"/></td>
                <td><fmt:formatNumber value="${b.totalAmount}" type="currency" currencySymbol="₹"/></td>
                <td><fmt:formatNumber value="${b.gstAmount}" type="currency" currencySymbol="₹"/></td>
                <td><fmt:formatNumber value="${b.discount}" type="currency" currencySymbol="₹"/></td>
                <td><fmt:formatNumber value="${b.netAmount}" type="currency" currencySymbol="₹"/></td>
                <td><span class="badge badge-${b.status.toLowerCase()}">${b.status}</span></td>
                <td>
                    <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/billing/view?id=${b.id}">View</a>
                    <c:if test="${b.status ne 'PAID'}">
                        <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/billing/pay?id=${b.id}">Record Payment</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty bills}"><tr><td colspan="8" style="text-align:center;color:#94a3b8;padding:24px">No bills yet.</td></tr></c:if>
        </tbody>
    </table>
</t:layout>
