/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cart;

import entity.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author mikerocha
 */
public class ShoppingCart {

    private List<ShoppingCartItem> items;
    int numberOfItems;
    double total;

    public ShoppingCart() {
        super();
        items = makeCart();
        numberOfItems = 0;
        total = 0;
    }

    public synchronized void addItem(Product product) {
        boolean itemExistsInCart = false;
        ShoppingCartItem item = null;
        for (ShoppingCartItem scItem : items) {
            if (scItem.getProductId() == product.getId()) {
                itemExistsInCart = true;
                item = scItem;
            }
        }
        if (itemExistsInCart) {
            item.increment();
        } else {
            ShoppingCartItem newItem = makeItem(product);
            items.add(newItem);
        }
    }

    public synchronized void update(int productId, int quantity) {

        Iterator<ShoppingCartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            ShoppingCartItem item = iterator.next();
            if (item.getProductId() == productId) {
                if (quantity == 0) {
                    iterator.remove();
                } else {
                    item.setQuantity(quantity);
                }
            }
        }
    }

    public synchronized List<ShoppingCartItem> getItems() {

        return items;
    }

    public synchronized int getNumberOfItems() {

        numberOfItems = 0;
        for (ShoppingCartItem item : items) {
            numberOfItems += item.getQuantity();
        }

        return numberOfItems;
    }

    public synchronized double getSubtotal() {
        BigDecimal subtotal = new BigDecimal(0);
        for (ShoppingCartItem item : items) {
            subtotal = subtotal.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return subtotal.doubleValue();
    }
    
    public synchronized void calculateTotal(double surcharge) {
        double subtotal = getSubtotal();
        total = subtotal + surcharge; 
    }

    public synchronized double getTotal() {

        return total;
    }
    
    public synchronized void clear() {
        items = makeCart();
        numberOfItems = 0;
        total = 0;
    }

    private List<ShoppingCartItem> makeCart() {
        return new ArrayList<ShoppingCartItem>(0);
    }

    private ShoppingCartItem makeItem(Product product) {
        return new ShoppingCartItem(product);
    }
}
