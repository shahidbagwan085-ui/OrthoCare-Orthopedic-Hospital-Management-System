<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Bill" active="billing">
    <div class="page-header">
        <h1>${bill.billNumber} <span class="badge badge-${bill.status.toLowerCase()}">${bill.status}</span></h1>
        <c:if test="${bill.status ne 'PAID'}">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/billing/pay?id=${bill.id}">Record Payment</a>
        </c:if>
    </div>
    <div class="card">
        <p><b>Patient:</b> <c:out value="${bill.patientName}"/></p>
        <div class="row">
            <div><b>Total:</b> <fmt:formatNumber value="${bill.totalAmount}" type="currency" currencySymbol="₹"/></div>
            <div><b>GST (18%):</b> <fmt:formatNumber value="${bill.gstAmount}" type="currency" currencySymbol="₹"/></div>
            <div><b>Discount:</b> <fmt:formatNumber value="${bill.discount}" type="currency" currencySymbol="₹"/></div>
            <div><b>Net Amount:</b> <fmt:formatNumber value="${bill.netAmount}" type="currency" currencySymbol="₹"/></div>
        </div>
    </div>

    <h3>Payments</h3>
    <table>
        <thead><tr><th>Date/Time</th><th>Amount</th><th>Method</th><th>Reference</th></tr></thead>
        <tbody>
        <c:forEach var="p" items="${bill.payments}">
            <tr>
                <td>${p.paidAt}</td>
                <td><fmt:formatNumber value="${p.amount}" type="currency" currencySymbol="₹"/></td>
                <td>${p.method}</td>
                <td>${p.referenceId}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty bill.payments}"><tr><td colspan="4" style="text-align:center;color:#94a3b8;padding:24px">No payments recorded.</td></tr></c:if>
        </tbody>
    </table>
</t:layout>
