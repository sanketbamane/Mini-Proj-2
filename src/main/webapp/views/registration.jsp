<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script>
$(document).ready(function(){
	$( "#uEmail" ).blur(function() {
		$("#errMsg").text("");	
		$.ajax({
			type : "GET",
			
			url : "uniqueMailCheck?Email="+$( "#uEmail" ).val(),
			
							
			success : function(data) {
				if(data=="DUPLICATE"){
						$("#errMsg").text ("Duplicate Email");
						$("#submitBtn").prop("disabled", true);
					}else{
							$("#submitBtn").prop("disabled", false);
						}
			}
		});
		});
	
			$("#cid").change(function(){
				$('#sid').find('option:not(:first)').remove();
				$('#cyid').find('option:not(:first)').remove();
			    //does some stuff;
				$.ajax({
					type : "GET",
					
					url : "countryChange?CId="+$( "#cid" ).val(),
					
									
					success : function(data) {
						
						$.each(data, function(key,value) {
				            $('#sid').append($('<option>').text(value).attr('value', key));

						}); 
						}
				});    
			});


			$("#sid").change(function(){
				$('#cyid').find('option:not(:first)').remove();
			    //does some stuff;
				$.ajax({
					type : "GET",
					
					url : "stateChange?stateId="+$( "#sid" ).val(),
					
									
					success : function(data) {
						
						$.each(data, function(key,value) {
				            $('#cyid').append($('<option>').text(value).attr('value', key));

						}); 
						}
				});    
			});
	
});

</script>
</head>
<body>
<div align="center">

<h3>Register Here</h3>
<font color='red'>${failMsg}</font>
<font color='green'>${succMsg}</font>


<form:form action="userRegistration" method="POST" modelAttribute="userAcc">
<table>

<tr>
<td>First name: </td>
<td><form:input path="fname"/></td>
</tr>

<tr>
<td>Last name:</td>
<td><form:input path="lname"/></td>
</tr>

<tr>
<td>Email :</td>
<td><form:input path="userEmail" id="uEmail"/>
<font color='red'><span id="errMsg"></span></font>
</td>
</tr>

<tr>
<td>Phone no:</td>
<td><form:input path="phno"/></td>
</tr>

<tr>
<td>DOB:</td>
<td><form:input path="dob"/></td>
</tr>

<tr>
<td>Gender:</td>
<td><form:radiobutton path="gender" value="M"/> Male
	<form:radiobutton path="gender" value="F"/> Female
</td>
</tr>

<tr>
<td>Select Country:</td>
<td><form:select path="countryId" id="cid">
	<form:option value="">-Select-</form:option>
	<form:options items="${countries}"/>
	</form:select>
</td>
</tr>

<tr>
<td>Select State:</td>
<td><form:select path="stateId" id="sid">
		<form:option value="">-Select-</form:option>
	</form:select>
</td>
</tr>

<tr>
<td>Select City:</td>
<td><form:select path="cityId" id="cyid">
		<form:option value="">-Select-</form:option>
	</form:select>
</td>
</tr>

<tr>
<td><input type="reset" value="Reset"></td>
<td><input type="submit" value="Submit" id="submitBtn"/></td>
</tr>

</table>

</form:form>

</div>

</body>
</html>