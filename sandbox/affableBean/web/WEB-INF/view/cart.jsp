<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <div id="singleColumn">
                <p>Your shopping cart contains ${cart.numberOfItems} items</p>
                
                <div id="actionBar">

                    <div class="cartWidget">
                        <c:if test="${!empty cart && cart.numberOfItems > 0}">
                            <a href="checkout">
                                [ proceed to checkout ]
                            </a>
                        </c:if>
                    </div>

                    <div class="cartWidget">
                        <a href="category">
                            continue shopping
                        </a>
                    </div>

                    <div class="cartWidget">
                        <a href="viewCart?clear">
                            clear cart
                        </a>
                    </div>

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
                                 &euro; ${cartItem.price}
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
                                           value="update button">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
