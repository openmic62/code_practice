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


            <div id="singleColumn">
                <p>Your shopping cart contains x items</p>
                <!--<div id="cartBar">-->
                <div id="actionBar">

                    <div class="cartWidget">
                        <a href="checkout.jsp">
                            [ proceed to checkout ]
                        </a>
                    </div>

                    <div class="cartWidget">
                        [ continue shopping ]
                    </div>

                    <div class="cartWidget">
                        [ clear cart ]
                    </div>

                </div>

                <h4 id="subtotal">[ subtotal: xxx ]</h4>

                <table id="cartTable">
                    <tr>
                        <th>
                            product
                        </th>
                        <th>
                            name
                        </th>
                        <th>
                            price
                        </th>
                        <th>
                            quantity
                        </th>
                    <tr>
                    <tr>
                        <td>
                            <a href="#">
                                <img src="#" class="productImage" alt="product image"
                            </a>
                        </td>
                        <td>
                            [ product name ]
                        </td>
                        <td>
                            [ price ]
                        </td>
                        <td>
                            <form action="updateCart" method="post">
                                <input type="text"
                                       maxlength="2"
                                       size="2"
                                       value="1"
                                       name="quantity">
                                <input type="submit"
                                       name="submit"
                                       value="update button">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a href="#">
                                <img src="#" class="productImage" alt="product image"
                            </a>
                        </td>
                        <td>
                            [ product name ]
                        </td>
                        <td>
                            [ price ]
                        </td>
                        <td>
                            <form action="updateCart" method="post">
                                <input type="text"
                                       maxlength="2"
                                       size="2"
                                       value="1"
                                       name="quantity">
                                <input type="submit"
                                       name="submit"
                                       value="update button">
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
