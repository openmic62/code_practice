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
