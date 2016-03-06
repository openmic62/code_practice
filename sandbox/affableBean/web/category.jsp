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
                <div class="categoryButton">
                    <a href="#">
                        <span class="categoryLabelText">dairy</span>
                    </a>
                </div>
                <div class="categoryButton">
                    <a href="#">
                        <span class="categoryLabelText">meats</span>
                    </a>
                </div>
                <div class="categoryButton">
                    <a href="#">
                        <span class="categoryLabelText">bakery</span>
                    </a>
                </div>
                <div class="categoryButton">
                    <a href="#">
                        <span class="categoryLabelText">fruti & veg</span>
                    </a>
                </div>
            </div>
            <div id="categoryRightColumn">
                <div class="categorySelectedWidget">
                    [ selected category ]
                </div>
                <!--<div id="tableRightColumn">-->
                    <table class="categoryTable">
                        <tr>
                            <td>
                                 <a href="#">
                                    <img src="#" class="productImage" alt="product image"
                                </a>                           
                            </td>
                            <td>
                                <div class="productNameWidget">
                                    [ product name ]
                                </div>
                                <div class="productDescriptionWidget">
                                    [ product description ]
                                </div>
                            </td>
                            <td>
                                <div class="productPriceWidget">
                                    [ price ]
                                </div>
                            </td>
                            <td>
                                <div class="productPurchaseWidget">
                                    <button name="Purchase">Purchase button</button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                 <a href="#">
                                    <img src="#" class="productImage" alt="product image"
                                </a>                           
                            </td>
                            <td>
                                <div class="productNameWidget">
                                    [ product name ]
                                </div>
                                <div class="productDescriptionWidget">
                                    [ product description ]
                                </div>
                            </td>
                            <td>
                                <div class="productPriceWidget">
                                    [ price ]
                                </div>
                            </td>
                            <td>
                                <div class="productPurchaseWidget">
                                    <button name="Purchase">Purchase button</button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                 <a href="#">
                                    <img src="#" class="productImage" alt="product image"
                                </a>                           
                            </td>
                            <td>
                                <div class="productNameWidget">
                                    [ product name ]
                                </div>
                                <div class="productDescriptionWidget">
                                    [ product description ]
                                </div>
                            </td>
                            <td>
                                <div class="productPriceWidget">
                                    [ price ]
                                </div>
                            </td>
                            <td>
                                <div class="productPurchaseWidget">
                                    <button name="Purchase">Purchase button</button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                 <a href="#">
                                    <img src="#" class="productImage" alt="product image"
                                </a>                           
                            </td>
                            <td>
                                <div class="productNameWidget">
                                    [ product name ]
                                </div>
                                <div class="productDescriptionWidget">
                                    [ product description ]
                                </div>
                            </td>
                            <td>
                                <div class="productPriceWidget">
                                    [ price ]
                                </div>
                            </td>
                            <td>
                                <div class="productPurchaseWidget">
                                    <button name="Purchase">Purchase button</button>
                                </div>
                            </td>
                        </tr>
                   </table>
                <!--</div>-->
            </div>
            <div id="footer">
                <hr>
                <p id="footerText">[ footer text ]</p>
            </div>
        </div>
    </div>
</body>
</html>
