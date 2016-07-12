<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.kpi.travelagency.i18n.text" />
<!DOCTYPE html>
<html lang="${language }">
    <head>
		<title><fmt:message key="login.title"/></title>
		<link rel="stylesheet" href="css/style.css">
    </head>
    <body>
    <h4> <fmt:message key="login.langchoose"/>: </h4>
        <form>
        	<div class="styled-select">
            <select id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}>Українська</option>
            </select>
            </div>
        </form>
        <h3> <fmt:message key="login.request"/>.</h3>
        <hr/>
        <div class="login-card">
        <form name="loginForm" method="POST" action="Controller">
            <input type="hidden" name="command" value ="login"/><br/>
            <table>
            	<tr>
            		<td><label for="login"><fmt:message key="login.label.username" />:</label></td>
            		<td><input type="text" name="login" value=""></td>
            	</tr>
            	<tr>
            		<td><label for="password"><fmt:message key="login.label.password" />:</label></td>
            		<td><input type="password" name ="password" value=""></td>
            	</tr>
            	<tr>
            		<td></td>
            		<td><fmt:message key="login.button.submit" var="buttonValue" />
            		<input type ="submit" name="submit" class="login login-submit" value="${buttonValue}"></td>
            	</tr>
            </table>
        </form>
        </div>
        <div class="login-card">
        <form name="toRegisterForm" method="POST" action="Controller">
            <input type="hidden" name="command" value ="toreg"/>
             <fmt:message key="login.newrequest"/>:<br/>
             <fmt:message key="login.button.reg" var="buttonValue" />
            <input type ="submit" name="reg" class="login login-submit" value="${buttonValue}">
        </form>
        </div>
        <hr/>
    </body>
</html>
