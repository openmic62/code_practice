            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


            <script src="js/libs/jquery-validate/additional-methods.js" type="text/javascript"></script>
            <script src="js/libs/jquery-validate/jquery.validate.js" type="text/javascript"></script>

            <script type="text/javascript">

                $(document).ready(function(){
                    $("#checkoutForm").validate({
                        rules:{
                            name: "required",
                            email: {
                                required:true,
                                email:true
                            },
                            phone: {
                                required:true,
                                number:true,
                                minlength:9
                            },
                            address: {
                                required:true
                            },
                            creditcard: {
                                required:true,
                                creditcard:true
                            }
                        }
                    });
                });
            </script>

            <div id="singleColumn">
                
                <h2>checkout</h2>
                
                <p>In order to purchase the items in your shopping cart, please provide us with the following information:</p>
                
                <form id="checkoutForm" action="purchase" method="post">
                    <table id="checkoutTable">
                        <tr>
                            <td><label for="name">name:</label></td>
                            <td class="inputField">
                                <input type="text"
                                       size="31"
                                       maxlength="45"
                                       id="name"
                                       name="name"
                                       value="${param.name}">
                            </td>
                        </tr>
                        <tr>
                            <td><label for="email">email:</label></td>
                            <td class="inputField">
                                <input type="text"
                                       size="31"
                                       maxlength="45"
                                       id="email"
                                       name="email"
                                       value="${param.email}">
                            </td>
                        </tr>
                        <tr>
                            <td><label for="phone">phone:</label></td>
                            <td class="inputField">
                                <input type="text"
                                       size="31"
                                       maxlength="16"
                                       id="phone"
                                       name="phone"
                                       value="${param.phone}">
                            </td>
                        </tr>
                        <tr>
                            <td><label for="address">address:</label></td>
                            <td class="inputField">
                                <input type="text"
                                       size="31"
                                       maxlength="45"
                                       id="address"
                                       name="address"
                                       value="${param.address}">

                                <br>
                                prague
                                <select name="cityRegion">
                                    <c:forEach begin="1" end="10" var="regionNumber">
                                      <option value="${regionNumber}"
                                              <c:if test="${param.cityRegion eq regionNumber}">selected</c:if>>${regionNumber}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="creditcard">credit card number:</label></td>
                            <td class="inputField">
                                <input type="text"
                                       size="31"
                                       maxlength="19"
                                       id="creditcard"
                                       name="creditcard"
                                       value="${param.creditcard}">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="submit purchase">
                            </td>
                        </tr>
                    </table>
                 </form>
                
                <div id="infoBox">
                    
                    <ul>
                        <li>Next-day delivery is guaranteed</li>
                        <li>A &euro;${initParam.deliverySurcharge}
                            delivery surcharge is applied to all purchase orders</li>
                    </ul>
                            
                    <table id="priceBox">
                        <tr>
                            <td>subtotal:</td>
                            <td class="checkoutPriceColumn">
                                &euro; 
                                <fmt:formatNumber type="number"
                                                  minFractionDigits="2"
                                                  value="${cart.subtotal}"/></td>
                        </tr>
                        <tr>
                            <td>delivery surcharge:</td>
                            <td class="checkoutPriceColumn">
                                <fmt:formatNumber type="number"
                                                  minFractionDigits="2"
                                                  value="${initParam.deliverySurcharge}"/></td>
                        </tr>
                        <tr>
                            <td class="total">total:</td>
                            <td class="total checkoutPriceColumn">
                                &euro; 
                                <fmt:formatNumber type="number"
                                                  minFractionDigits="2"
                                                  value="${cart.total}"/></td>
                        </tr>
                    </table>
                </div>
            </div>
