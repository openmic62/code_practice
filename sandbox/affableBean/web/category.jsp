<%-- 
    Document   : category
    Created on : Feb 10, 2016, 7:24:37 PM
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
                        [ checkout button ]
                    </div>

                    <div class="headerWidget">
                        [ shopping cart widget ]
                    </div>

                </div>

                <a href="#">
                    <img src="#" id="logo" alt="The Affable Bean">
                </a>

                <img src="#" id="logoText" alt="the affable bean">
            </div>


            <div id="categoryLeftColumn">
                <div id="selectedCategory">
                    <a href="#">
                        <span class="categoryText">dairy</span>
                    </a>
                </div>
                <div class="categoryButton">
                    <a href="#">
                        <span class="categoryText">meats</span>
                    </a>
                </div>
                <div class="categoryButton">
                    <a href="#">
                        <span class="categoryText">bakery</span>
                    </a>
                </div>
                <div class="categoryButton">
                    <a href="#">
                        <span class="categoryText">fruit & veg</span>
                    </a>
                </div>
            </div>
            <div id="categoryRightColumn">
                <p id="categoryTitle">[ selected category ]</p>
                <table class="categoryTable">
                    <tr>
                        <td>
                            <img src="#" alt="product image">
                        </td>
                        <td>
                            [ product name ]
                            <br>
                            <span class="smallText">[ product description ]</span>
                        </td>
                        <td>
                            [ price ]
                        </td>
                        <td>
                            <form action="#" method="post">
                                <input type="submit" value="purchase button">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="#" alt="product image"
                        </td>
                        <td>
                            [ product name ]
                            <br>
                            <span class="smallText">[ product description ]</span>
                        </td>
                        <td>
                            [ price ]
                        </td>
                        <td>
                            <form action="#" method="post">
                                <input type="submit" value="purchase button">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="#" alt="product image"
                        </td>
                        <td>
                            [ product name ]
                            <br>
                            <span class="smallText">[ product description ]</span>
                        </td>
                        <td>
                            [ price ]
                        </td>
                        <td>
                            <form action="#" method="post">
                                <input type="submit" value="purchase button">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="#" alt="product image"
                        </td>
                        <td>
                            [ product name ]
                            <br>
                            <span class="smallText">[ product description ]</span>
                        </td>
                        <td>
                            [ price ]
                        </td>
                        <td>
                            <form action="#" method="post">
                                <input type="submit" value="purchase button">
                            </form>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="footer">
                <hr>
                <p id="footerText">[ footer text ]</p>
            </div>
        </div>
    </div>
</body>
</html>
