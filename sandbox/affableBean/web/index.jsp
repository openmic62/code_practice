<%-- 
    Document   : index
    Created on : Feb 7, 2016, 4:19:51 PM
    Author     : Mike
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/affablebean.css">
        <title>The Affable Bean</title>
    </head>
    <body>
        <div id="main">
            <div id="header">
                
                <div id="widgetBar">
                    
                    <div class="headerWidget">
                        [ language toggle ]
                    </div>
                    
                    <div class="headerWidget">
                        [ shopping cart widget ]
                    </div>
                    
                </div>
                
                <a href="#">
                    <img src="#" id="logo" alt="Affable Bean logo">
                </a>
                
                <img src="#" id="logoText" alt="the affable bean">
            </div>
            
            <div id="indexLeftColumn">
                <div id="welcomeText">
                    <p>[ welcome text ]</p>
                </div>
            </div>
            
            <div id="indexRightColumn">
                <div class="categoryBox">
                    <a href="category.jsp">
                        <span class="categoryLabelText">dairy</span>
                    </a>
                </div>
                <div class="categoryBox">
                    <a href="#">
                        <span class="categoryLabelText">meats</span>
                    </a>
                </div>
                <div class="categoryBox">
                    <a href="#">
                        <span class="categoryLabelText">bakery</span>
                    </a>
                </div>
                <div class="categoryBox">
                    <a href="#">
                        <span class="categoryLabelText">fruit & veg</span>
                    </a>
                </div>
            </div>
            
            <div id="footer">
                <hr>
                <p id="footerText">[ footer text ]</p>
            </div>
            
        </div>
    </body>
</html>
