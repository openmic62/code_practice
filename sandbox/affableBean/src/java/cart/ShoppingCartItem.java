/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import entity.Product;
import java.math.BigDecimal;

/**
 *
 * @author mikerocha
 */
public class ShoppingCartItem {

    private Product product;
    private int quantity;

    public ShoppingCartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }
    
    private Product getProduct() {
        return this.product;
    }

    public int getProductId() {
        return product.getId();
    }
    
    public String getName() {
        return product.getName();
    }
    
    public BigDecimal getPrice() {
        return product.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void increment() {
        this.quantity++;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof ShoppingCartItem) && 
                (((ShoppingCartItem) obj).getProductId() == this.getProductId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 7877;
        result += (int) (this.getProductId() / 11);
        return result;
    }
}
