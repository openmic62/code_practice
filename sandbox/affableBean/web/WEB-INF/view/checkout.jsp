            <div id="singleColumn">
                <h2>checkout</h2>
                <p>In order to purchase the items in your cart, please provide us with the following information:</p>
                <form action="purchase" method="post">
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
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                prague: 
                                <select name="cityRegion">
                                    <c:forEach begin="1" end="10" var="regionNumber">
                                      <option value="${regionNumber}"
                                              <c:if test="${param.cityRegion eq regionNumber}">selected</c:if>>${regionNumber}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="name">credit card:</label></td>
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
                        <li>Next-day delivery is guaranteed.</li>
                        <li>A &euro; 3.00 delivery surcharge is applied to all purchase orders.</li>
                    </ul>
                    <table id="priceBox">
                        <tr>
                            <td>subtotal:</td>
                            <td class="checkoutPriceColumn">&euro; ${cart.subtotal}</td>
                        </tr>
                        <tr>
                            <td>delivery surcharge:</td>
                            <td class="checkoutPriceColumn">&euro; 3.00</td>
                        </tr>
                        <tr>
                            <td>total:</td>
                            <td class="checkoutPriceColumn">&euro; ${cart.subtotal + 3}</td>
                        </tr>
                    </table>
                </div>
            </div>
