/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

/**
 *
 * @author mikerocha
 */
public class ShoppingCartItem {

    private String sessionId;
    private int productId;

    public ShoppingCartItem(String sessionId, int productId) {
        this.sessionId = sessionId;
        this.productId = productId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public int getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof ShoppingCartItem) && 
                (((ShoppingCartItem) obj).sessionId == this.sessionId) && 
                (((ShoppingCartItem) obj).productId == this.productId)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 7877;
        result += (int) (Integer.parseInt(this.sessionId) / 11);
        result += (int) (this.productId / 11);
        return result;
    }
}
