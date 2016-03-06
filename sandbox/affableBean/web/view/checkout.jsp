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
                <h2>checkout</h2>
                <p>[ text ]</p>
                <form action="confirmation.jsp" method="post">
                    <table id="checkoutTable">
                        <tr>
                            <td>[ form containing fields to
                                <br>capture customer details ]</td>
                        </tr>
                        <tr>
                            <td></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="submit button"></td>
                        </tr>
                    </table>
                 </form>
                
                <div id="infoBox">
                    <div id="purchaseConditions">
                        [ purchase conditions ]
                    </div>
                    <div id="priceBox">
                        [ purchase calculations:
                        <br>
                        subtotal + delivery charge]
                    </div>
                </div>
            </div>
            <div id="footer">
                <hr>
                <p id="footerText">[ footer text ]</p>
            </div>
        </div>
    </div>
</body>
</html>
