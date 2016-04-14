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
    
    public void clear() {
        items = makeCart();
        numberOfItems = 0;
    }

    public int getNumberOfItems() {
        numberOfItems = 0;
        for (ShoppingCartItem item : items) {
            numberOfItems += item.getQuantity();
        }
        return numberOfItems;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }
    
    public BigDecimal getSubtotal() {
        BigDecimal subtotal = new BigDecimal(0);
        for (ShoppingCartItem item : items) {
            subtotal = subtotal.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return subtotal;
    }
    
    public void addItem(Product product) {
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

    public void removeItem(ShoppingCartItem itemToRemove) {
        Iterator<ShoppingCartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            ShoppingCartItem item = iterator.next();
            if (item.equals(itemToRemove)) {
                iterator.remove();
            }
        }
    }

    private List<ShoppingCartItem> makeCart() {
        return new ArrayList<ShoppingCartItem>(0);
    }

    private ShoppingCartItem makeItem(Product product) {
        return new ShoppingCartItem(product);
    }
}
