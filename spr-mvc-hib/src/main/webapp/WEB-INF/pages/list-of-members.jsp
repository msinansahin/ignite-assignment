<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>

<custom:layout title="List of members">

	<h1>List of members</h1>
	<p><spring:message code="list.member.desc"></spring:message> </p>
	<table class="table">
		<thead>
			<tr>
				<th width="10%">id</th>
				<th width="70%">name</th>
				<th width="70%">count of team</th>
				<th width="20%">actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="member" items="${members}">
				<tr>
					<td>${member.id}</td>
					<td>${member.name}</td>
					<td>${member.teamCount}</td>
					<td>
						<a href="${pageContext.request.contextPath}/member/edit/${member.id}.html"><spring:message code="edit"></spring:message></a><br /> 
						<a href="${pageContext.request.contextPath}/member/delete/${member.id}.html"><spring:message code="delete"></spring:message></a><br />
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty members}">
				<tr>
					<td colspan="3">
						<spring:message code="no.record.found"></spring:message>
					</td>
				</tr>
			</c:if>			
		</tbody>
	</table>

</custom:layout>

