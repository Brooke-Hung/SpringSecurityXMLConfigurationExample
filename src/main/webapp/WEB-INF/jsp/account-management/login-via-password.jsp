<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Login Via Password</title>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link type="text/css" rel="stylesheet"	href="<c:url value='/theme/default/css/default.css'/>" />
	</head>
	<body>
		<%@ include file="/WEB-INF/jsp/include/menu.jsp" %>
		<div class="container account-mgmt">
			<spring:message code="login.placeholder.userName" var="placeholderUserName"/>
			<spring:message code="login.placeholder.password" var="placeholderPassword"/>
			<c:url value="/account-management/performLoginViaPassword" var="performLoginViaPassword"/>
			<c:url value="/account-management/loginViaVerificationCode" var="loginViaVerificationCodeAction"/>
			<form:form id="loginViaPasswordForm" action="${performLoginViaPassword}" modelAttribute="loginViaPasswordForm" method="POST">
				<form:hidden path="authenticationType"/>
				<div><form:input path="userName" id="userName" autocomplete="off" placeholder="${placeholderUserName}"/></div>
				<div><form:password path="password" id="password" autocomplete="off" placeholder="${placeholderPassword}"/></div>	
				<div><form:checkbox path="rememberMe" id="rememberMe"/><span><spring:message code="login.label.rememberMe"/></span></div>
				<div><a href="${loginViaVerificationCodeAction}" class="switchLink"><spring:message code="login.label.viaVerificationCode"/></a></div>
				<div><input type="submit" value="<spring:message code='login.button.login'/>"/></div>
				<c:if test="${param.error != null}">
					<div class="message">
						Invalid user name or password!
					</div>	
				</c:if>
			</form:form>
		</div>
	</body>
</html>