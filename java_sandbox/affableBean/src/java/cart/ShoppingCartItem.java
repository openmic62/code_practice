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
    private short quantity;

    public ShoppingCartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }
    
    public Product getProduct() {
        return this.product;
    }

    public short getQuantity() {
        return quantity;
    }
    
    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }
    
    public void increment() {
        this.quantity++;
    }

    public String getName() {
        return product.getName();
    }
    
    public BigDecimal getPrice() {
        return product.getPrice();
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof ShoppingCartItem) && 
                (((ShoppingCartItem) obj).getProduct().getId() == this.getProduct().getId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 7877;
        result += (int) (this.getProduct().getId() / 11);
        return result;
    }
}
