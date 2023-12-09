<%--
  Created by IntelliJ IDEA.
  User: ahogek
  Date: 12/7/23
  Time: 00:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xml:lang="en">
<head>
  <title>Calculate BMI</title>
</head>
<body>

<form name="bmiForm" action="calculateServlet" method="POST">
  <table>
    <caption>BMI Calculate Form</caption>
    <tr>
      <td>Your Weight (kg) :</td>
      <td><label>
        <input type="text" name="weight"/>
      </label></td>
    </tr>
    <tr>
      <td>Your Height (m) :</td>
      <td><label>
        <input type="text" name="height"/>
      </label></td>
    </tr>
    <th><input type="submit" value="Submit" name="find"/></th>
    <th><input type="reset" value="Reset" name="reset"/></th>
  </table>
  <% if (request.getAttribute("bmi") != null) { %>
  <p>Your BMI: <%= request.getAttribute("bmi") %>
  </p>
  <% } %>
  <% if (request.getAttribute("errorMessage") != null) { %>
  <p>Error: <%= request.getAttribute("errorMessage") %>
  </p>
  <% } %>
</form>

</body>
</html>
