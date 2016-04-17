            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <div id="singleColumn">
                
                <c:choose>
                    <c:when test="${cart.numberOfItems > 1}">
                        <p>Your shopping cart contains ${cart.numberOfItems} items.</p>
                    </c:when>
                    <c:when test="${cart.numberOfItems == 1}">
                        <p>Your shopping cart contains ${cart.numberOfItems} item.</p>
                    </c:when>
                    <c:otherwise>
                        <p>Your shopping cart is empty.</p>
                    </c:otherwise>
                </c:choose>

                <div id="actionBar">
                    <%-- clear cart widget --%>
                    <c:if test="${!empty cart && cart.numberOfItems > 0}">
                        <c:url var="url" value="viewCart">
                            <c:param name="clear" value="true"/>
                        </c:url>
                        <a href="${url}" class="bubble hMargin">clear cart</a>
                    </c:if>

                    <%--continue shopping widget--%>
                    <c:set var="value">
                        <c:choose>
                            <%-- if 'selectedCategory' session object exists, send user to previously viewed category --%>
                            <c:when test="${!empty selectedCategory}">
                                <c:url value="category"/>
                            </c:when>
                            <%-- otherwise send user to welcome page --%>
                            <c:otherwise>
                                <c:url value="index.jsp"/>
                            </c:otherwise>
                        </c:choose>
                    </c:set>
                    
                    <a href="${value}" class="bubble hMargin">continue shopping</a>

                    <%--checkout widget--%>
        <c:if test="${!empty cart && cart.numberOfItems != 0}">
            <c:url var="url" value="checkout"/>
            <a href="${url}" class="bubble hMargin">proceed to checkout &#x279f;</a>
                    </c:if>
                </div>

                <c:if test="${!empty cart && cart.numberOfItems !=0}">
                    
                    <h4 id="subtotal">subtotal: &euro; 
                        <fmt:formatNumber type="number"
                                          minFractionDigits="2"
                                          value="${cart.subtotal}"/></h4>

                    <table id="cartTable">

                        <tr class="header">
                            <th>product</th>
                            <th>name</th>
                            <th>price</th>
                            <th>quantity</th>
                        </tr>

                        <c:forEach var="cartItem" items="${cart.items}" varStatus="iter">

                            <c:set var="product" value="${cartItem.product}"/>

                            <tr>
                                <td>
                                    <img src="${initParam.productImagePath}${product.name}.png"
                                         alt="${product.name}">
                                </td>

                                <td>${product.name}</td>

                                <td>
                                    &euro; ${product.price * cartItem.quantity}
                                    <br/>
                                    <span class="smallText">( &euro; ${product.price} / unit)</span>
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
                                               name="quantity"
                                               style="margin:5px">
                                        <input type="submit"
                                               name="submit"
                                               value="update quantity">
                                    </form>
                                </td>
                            </tr>

                        </c:forEach>

                    </table>

                </c:if>
            </div>
