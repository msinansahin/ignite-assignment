<!-- handles edit and new organization -->

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:layout title="${pageTitle}">

	<h1>${pageTitle}</h1>
	<p>${description}</p>
	
	<form:form method="POST" commandName="member"
		action="${contextPath}/member/edit/${member.id}">
		<div class="form-group">
			<label for="name"><spring:message code="name"></spring:message></label> 
			<form:input class="form-control" type="text" path="name" id="name"/>
			<form:errors path="name" cssClass="help-inline" />
		</div>
		<div class="form-group">
			<label for="teams"><spring:message code="teams"></spring:message></label> 
			<form:select id="teams" cssClass="form-control" path="teamIds" 
				items="${teamList}" itemLabel="name" itemValue="id" />
			<form:errors path="teams" cssClass="help-inline" />
			
		</div>		
		<button type="submit" class="btn btn-primary">Submit</button>
	</form:form>
</custom:layout>