<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <div id="singleColumn">
                <p>Your shopping cart contains ${cart.numberOfItems} items</p>
                
                <div id="actionBar">

                    <a href="viewCart?clear" class="bubble hMargin">
                        clear cart
                    </a>

                    <a href="category" class="bubble hMargin">
                        continue shopping
                    </a>

                    <c:if test="${!empty cart && cart.numberOfItems > 0}">
                        <a href="checkout" class="bubble hMargin">
                            proceed to checkout &#x279f
                        </a>
                    </c:if>

                </div>

                <h4 id="subtotal">subtotal: &euro; ${cart.subtotal}</h4>

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
                    <c:forEach var="cartItem" items="${cart.items}">
                        
                        <c:set var="product" value="${cartItem.product}"/>
                        
                        <tr>
                            <td>
                                <a href="#">
                                    <img src="${initParam.productImagePath}${cartItem.name}.png" class="productImage" alt="product image"
                                </a>
                            </td>
                            <td>
                                ${cartItem.name}
                            </td>
                            <td>
                                &euro; ${cartItem.price * cartItem.quantity}
                                <br/>
                                <span class="smallText">
                                    ( &euro; ${cartItem.price} / unit)
                                </span>
                            </td>
                            <td>
                                <form action="updateCart" method="post">
                                    <input type="hidden"
                                           name="productId"
                                           value="${product.id}">
                                    <input type="text"
                                           maxlength="2"
                                           size="2"
                                           value="${cartItem.quantity}"
                                           name="quantity">
                                    <input type="submit"
                                           name="submit"
                                           value="update quantity">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
