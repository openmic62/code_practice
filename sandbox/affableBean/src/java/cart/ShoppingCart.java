/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author mikerocha
 */
public class ShoppingCart {

    private String sessionId;
    private List<ShoppingCartItem> cartContents;

    public ShoppingCart() {
        this("-1");
    }

    public ShoppingCart(String sessionId) {
        super();
        this.sessionId = sessionId;
        cartContents = makeCart();
    }

    public void addItem(String sessionId, int productID) {
        ShoppingCartItem item = makeItem(sessionId, productID);
        cartContents.add(item);
    }

    public void removeItem(ShoppingCartItem itemToRemove) {
        Iterator<ShoppingCartItem> iterator = cartContents.iterator();
        while (iterator.hasNext()) {
            ShoppingCartItem item = iterator.next();
            if (item.equals(itemToRemove)) {
                iterator.remove();
            }
        }
    }

    public int getNumberOfItems() {
        return cartContents.size();
    }
    
    public String getSessionId() {
        return this.sessionId;
    }

    private List<ShoppingCartItem> makeCart() {
        return new ArrayList<ShoppingCartItem>(0);
    }

    private ShoppingCartItem makeItem(String sessionId, int productId) {
        return new ShoppingCartItem(sessionId, productId);
    }
}
