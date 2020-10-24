<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script>

</script>
</head>
<body>
<div align="center">

<h3>Unlock Account Here</h3>
<font color='red'>${failMsg}</font>
<font color='green'>${succMsg}</font>


<form:form action="unlockAccount?email=${userAcc.email}" 
		   method="POST" modelAttribute="userAcc">
<table>

<tr>
<td>Email : </td>
<td>${userAcc.email}</td>
</tr>

<tr>
<td>Temp pass :</td>
<td><form:input path="tempPwd"/></td>
</tr>

<tr>
<td>choose new password :</td>
<td><form:input path="newPwd"/>

</td>
</tr>

<tr>
<td>Confirm password :</td>
<td><form:input path="confirmPwd"/>

</td>
</tr>

<tr>
<td><input type="reset" value="Reset"></td>
<td><input type="submit" value="Submit" id="Unlock Acc"/></td>
</tr>

</table>

</form:form>

</div>

</body>
</html>