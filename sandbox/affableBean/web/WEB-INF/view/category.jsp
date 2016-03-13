<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<sql:query var="categories" dataSource="jdbc/affablebean">
    SELECT * FROM category
</sql:query>
    
            <div id="categoryLeftColumn">
                <c:forEach var="category" items="${categories.rows}">
                    <c:choose>
                        <c:when test="${category.id == pageContext.request.queryString}">
                            <div class="categoryButton" id="selectedCategory">
                                <span class="categoryText">${category.name}</span>
                           </div>
                        </c:when>
                        <c:otherwise>
                            <a class="categoryButton" href="category?${category.id}">
                                <div class="categoryText">${category.name}</div>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div id="categoryRightColumn">
                <p id="categoryTitle">[ selected category ]</p>
                <!--<table class="categoryTable">-->
                <table id="productTable">
                    <tr>
                        <td>
                            <img src="#" alt="product image">
                        </td>
                        <td>
                            [ product name ]
                            <br>
                            <span class="smallText">[ product description ]</span>
                        </td>
                        <td>
                            [ price ]
                        </td>
                        <td>
                            <form action="checkout" method="post">
                                <input type="submit" value="purchase button">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="#" alt="product image"
                        </td>
                        <td>
                            [ product name ]
                            <br>
                            <span class="smallText">[ product description ]</span>
                        </td>
                        <td>
                            [ price ]
                        </td>
                        <td>
                            <form action="checkout" method="post">
                                <input type="submit" value="purchase button">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="#" alt="product image"
                        </td>
                        <td>
                            [ product name ]
                            <br>
                            <span class="smallText">[ product description ]</span>
                        </td>
                        <td>
                            [ price ]
                        </td>
                        <td>
                            <form action="checkout" method="post">
                                <input type="submit" value="purchase button">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="#" alt="product image"
                        </td>
                        <td>
                            [ product name ]
                            <br>
                            <span class="smallText">[ product description ]</span>
                        </td>
                        <td>
                            [ price ]
                        </td>
                        <td>
                            <form action="checkout" method="post">
                                <input type="submit" value="purchase button">
                            </form>
                        </td>
                    </tr>
                </table>
            </div>
