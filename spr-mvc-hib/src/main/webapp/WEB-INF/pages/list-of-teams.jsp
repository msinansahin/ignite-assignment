<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<spring:message code="add.team" var="addTeam"></spring:message>
<custom:layout title="List of teams">

	<h1>List of teams</h1>
	<p><spring:message code="list.team.desc"></spring:message> </p>
	<table class="table" >
	<thead>
		<tr>
			<th width="10%">id</th>
			<th width="15%">name</th>
			<th width="10%">rating</th>
			<th width="10%">organization</th>
			<th width="10%">actions</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="team" items="${teams}">
			<tr>
				<td>${team.id}</td>
				<td>${team.name}</td>
				<td>${team.rating}</td>
				<td>${team.organization}</td>
				
				<td>
				<a href="${contextPath}/team/edit/${team.id}.html"><spring:message code="edit"></spring:message> </a><br/>
				<a href="${contextPath}/team/delete/${team.id}.html"><spring:message code="delete"></spring:message> </a><br/>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty teams}">
			<tr>
				<td colspan="5">
					<spring:message code="no.record.found"></spring:message>
				</td>
			</tr>
		</c:if>
	</tbody>
	</table>

</custom:layout>

