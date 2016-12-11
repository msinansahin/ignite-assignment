<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<spring:message code="edit.team" var="editTeam"></spring:message>
<spring:message code="no.select" var="noSelect"></spring:message>
<custom:layout title="${editTeam}">

	<h1>${editTeam}</h1>
	<p>Here you can edit a new team.</p>
	
	<form:form method="POST" commandName="team" action="${contextPath}/team/edit/${team.id}">
		<div class="form-group">
			<label for="name"><spring:message code="name"></spring:message></label> 
			<form:input class="form-control" type="text" path="name" id="name"/>
		</div>
		
		<div class="form-group">
			<label for="rating"><spring:message code="rating"></spring:message></label> 
			<form:input type="hidden" class="rating" path="rating"/>	
		</div>
		
		<div class="form-group">
			<label for="organization"><spring:message code="organization"></spring:message></label> 
			<form:select id="organization" cssClass="form-control" path="organization.id">
				<form:option value="" label="${noSelect}"></form:option>
				<form:options items="${organizationList}"/>
			</form:select> 
			

		</div>
		<button type="submit" class="btn btn-primary"><spring:message code="submit"></spring:message> </button>
	</form:form>
	<script>
		$('#rating').rating();
	</script>
</custom:layout>