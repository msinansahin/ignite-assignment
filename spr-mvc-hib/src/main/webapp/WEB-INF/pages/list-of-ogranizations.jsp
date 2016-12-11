<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>

<spring:message code="add.team" var="addTeam"></spring:message>
<custom:layout title="List of organizations">

	<h1>List of organizations</h1>
	<p><spring:message code="list.organization.desc"></spring:message> </p>
	<table class="table">
		<thead>
			<tr>
				<th width="10%">id</th>
				<th width="70%">name</th>
				<th width="20%">actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="organization" items="${organizations}">
				<tr>
					<td>${organization.id}</td>
					<td>${organization.name}</td>
					<td>
						<a href="${contextPath}/organization/edit/${organization.id}.html"><spring:message code="edit"></spring:message></a><br /> 
						<a href="${contextPath}/organization/delete/${organization.id}.html"><spring:message code="delete"></spring:message></a><br />
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty organizations}">
				<tr>
					<td colspan="3">
						<spring:message code="no.record.found"></spring:message>
					</td>
				</tr>
			</c:if>			
		</tbody>
	</table>

</custom:layout>

