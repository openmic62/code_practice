<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


            <div id="categoryLeftColumn">
                
                <c:forEach var="category" items="${categories}">
                    
                    <c:choose>
                        <c:when test="${category.name == selectedCategory.name}">
                            <div class="categoryButton" id="selectedCategory">
                                <span class="categoryText">
                                    ${category.name}
                                </span>
                           </div>
                        </c:when>
                        <c:otherwise>
                            <a class="categoryButton" href="category?${category.id}">
                                <div class="categoryText">
                                    ${category.name}
                                </div>
                            </a>
                        </c:otherwise>
                    </c:choose>
                    
                </c:forEach>
                
            </div>
            
            <div id="categoryRightColumn">
                
                <p id="categoryTitle">${selectedCategory.name}</p>
                
                <table id="productTable">
                    
                    <c:forEach var="product" items="${categoryProducts}" varStatus="iter">
                        
                     <tr>
                        <td>
                            <img src="${initParam.productImagePath}${product.name}.png" 
                                 alt="${product.name}">
                        </td>
                        
                        <td>
                            ${product.name}
                            <br>
                            <span class="smallText">${product.description}</span>
                        </td>
                        
                        <td>&euro; ${product.price}</td>
                        
                        <td>
                            <form action="addToCart" method="post">
                                <input type="hidden" 
                                       name="productId" 
                                       value="${product.id}">
                                <input type="submit"
                                       name="submit"
                                       value="add to cart">
                            </form>
                        </td>
                    </tr>
                    
                    </c:forEach>
                    
                </table>
            </div>
