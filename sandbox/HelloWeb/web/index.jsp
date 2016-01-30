<%-- 
    Document   : index
    Created on : Jan 24, 2016, 10:15:48 AM
    Author     : Mike
    Introduction to Developing Web Applications
    https://netbeans.org/kb/docs/web/quickstart-webapps.html
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Entry Form</h1>
        <form name="Name Input Form" action="response.jsp">
            Enter your name:
            <input type="text" name="name" value="" />
            <input type="submit" value="OK" name="submit" />
        </form>
    </body>
</html>
