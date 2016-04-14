            <div id="singleColumn">
                <h2>checkout</h2>
                <p>In order to purchase the items in your cart, please provide us with the following information:</p>
                <form action="purchase" method="post">
                    <table id="checkoutTable">
                        <tr>
                            <td align="right">name:</td>
                            <td align="right"><input type="text" name="name" width="60"></td>
                        </tr>
                        <tr>
                            <td align="right">email:</td>
                            <td align="right"><input type="email" name="email" width="60"></td>
                        </tr>
                        <tr>
                            <td align="right">phone:</td>
                            <td align="right"><input type="tel" name="phone" width="60"></td>
                        </tr>
                        <tr>
                            <td align="right">address:</td>
                            <td align="right"><input type="text" name="address" width="60"></td>
                        </tr>
                        <tr>
                            <td align="right" colspan="2">
                                prague: <input type="button" name="prague">
                            </td>
                        </tr>
                        <tr>
                            <td align="right">credit card:</td>
                            <td align="right"><input type="text" name="creditCard" width="60"></td>
                        </tr>
                        <tr>
                            <td align="right" colspan="2"><input type="submit" value="submit purchase"></td>
                        </tr>
                    </table>
                 </form>
                
                <div id="infoBox">
                    <div id="purchaseConditions">
                        <ul>
                            <li>Next-day delivery is guaranteed.</li>
                            <li>A &euro; 3.00 delivery surcharge is applied to all purchase orders.</li>
                        </ul>
                    </div>
                    <div id="priceBox">
                        <table>
                            <tr>
                                <td align="right">subtotal:</td>
                                <td align="right">&euro; ${cart.subtotal}</td>
                            </tr>
                            <tr>
                                <td align="right">delivery surcharge:</td>
                                <td align="right">&euro; 3.00</td>
                            </tr>
                            <tr>
                                <td align="right">total:</td>
                                <td align="right">&euro; ${cart.subtotal + 3}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
