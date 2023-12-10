<%@ page import="com.ahogek.model.Student" %><%--
  Created by IntelliJ IDEA.
  User: ahogek
  Date: 12/10/23
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html xml:lang="en">
<head>
  <title>Student Record</title>
</head>
<body>

<%
  if (request.getAttribute("studentRecord") != null) {
    Student student = (Student) request.getAttribute("studentRecord");
%>

<h1>Student Record</h1>
<div>ID: <%= student.id()%>
</div>
<div>First Name: <%= student.firstName()%>
</div>
<div>Last Name: <%= student.lastName()%>
</div>

<%
} else {
%>

<h1>No student record found.</h1>

<% } %>

</body>
</html>