<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Login Via Verification Code</title>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link type="text/css" rel="stylesheet"	href="<c:url value='/theme/default/css/default.css'/>" />
	</head>
	<body>
		<%@ include file="/WEB-INF/jsp/include/menu.jsp" %>
		<div class="container account-mgmt">
			<spring:message code="login.placeholder.cellphone" var="placeholderCellphone"/>
			<spring:message code="login.placeholder.verificationCode" var="placeholderVerificationCode"/>
			<c:url value="/account-management/performLoginViaVerificationCode" var="performLoginViaVerificationCodeAction"/>
			<c:url value="/account-management/loginViaPassword" var="loginViaPasswordAction"/>
			<form:form id="loginViaVerificationCodeForm" action="${performLoginViaVerificationCodeAction}" modelAttribute="loginViaVerificationCodeForm" method="POST">
				<form:hidden path="authenticationType"/>
				<div><form:input path="cellphoneNo" id="cellphoneNo" autocomplete="off" placeholder="${placeholderCellphone}" minlength="8" maxlength="16"/></div>
				<div><form:input path="verificationCode" id="verificationCode" autocomplete="off" placeholder="${placeholderVerificationCode}"/></div>
				<div><a href="${loginViaPasswordAction}" class="switchLink"><spring:message code="login.label.viaPassword"/></a></div>
				<div><input type="submit" value="<spring:message code='login.button.login'/>"/></div>
				<c:if test="${param.error != null}">
					<div class="message">
						Invalid cellphone number or verification code!
					</div>	
				</c:if>
			</form:form>
		</div>
	</body>
</html>