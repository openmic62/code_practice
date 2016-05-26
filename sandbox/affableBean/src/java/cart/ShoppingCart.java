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

    /**
     * Adds a <code>ShoppingCartItem</code> to the <code>ShoppingCart</code>'s
     * <code>items</code> list. If item of the specified <code>product</code>
     * already exists in shopping cart list, the quantity of that item is
     * incremented.
     *
     * @param product the <code>Product</code> that defines the type of shopping cart item
     * @see ShoppingCartItem
     */
    public synchronized void addItem(Product product) {
        boolean itemExistsInCart = false;
        ShoppingCartItem item = null;
        for (ShoppingCartItem scItem : items) {
            if (scItem.getProduct().getId() == product.getId()) {
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

    /**
     * Updates the <code>ShoppingCartItem</code> of the specified
     * <code>product</code> to the specified quantity. If '<code>0</code>'
     * is the given quantity, the <code>ShoppingCartItem</code> is removed
     * from the <code>ShoppingCart</code>'s <code>items</code> list.
     *
     * @param product the <code>Product</code> that defines the type of shopping cart item
     * @param quantity the number which the <code>ShoppingCartItem</code> is updated to
     * @see ShoppingCartItem
     */
    public synchronized void update(int productId, short quantity) {

        Iterator<ShoppingCartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            ShoppingCartItem item = iterator.next();
            if (item.getProduct().getId() == productId) {
                if (quantity == 0) {
                    iterator.remove();
                } else {
                    item.setQuantity(quantity);
                }
            }
        }
    }

    /**
     * Returns the list of <code>ShoppingCartItems</code>.
     *
     * @return the <code>items</code> list
     * @see ShoppingCartItem
     */
    public synchronized List<ShoppingCartItem> getItems() {

        return items;
    }

    /**
     * Returns the sum of quantities for all items maintained in shopping cart
     * <code>items</code> list.
     *
     * @return the number of items in shopping cart
     * @see ShoppingCartItem
     */
    public synchronized int getNumberOfItems() {

        numberOfItems = 0;
        for (ShoppingCartItem item : items) {
            numberOfItems += item.getQuantity();
        }

        return numberOfItems;
    }

    /**
     * Returns the sum of the product price multiplied by the quantity for all
     * items in shopping cart list. This is the total cost excluding the surcharge.
     *
     * @return the cost of all items times their quantities
     * @see ShoppingCartItem
     */
    public synchronized double getSubtotal() {
        BigDecimal subtotal = new BigDecimal(0);
        for (ShoppingCartItem item : items) {
            subtotal = subtotal.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return subtotal.doubleValue();
    }

    /**
     * Calculates the total cost of the order. This method adds the subtotal to
     * the designated surcharge and sets the <code>total</code> instance variable
     * with the result.
     *
     * @param surcharge the designated surcharge for all orders
     * @see ShoppingCartItem
     */
    public synchronized void calculateTotal(String surcharge) {
        
        double amount = Double.parseDouble(surcharge);
        
        double subtotal = getSubtotal();
        total = subtotal + amount; 
    }

    /**
     * Returns the total cost of the order for the given
     * <code>ShoppingCart</code> instance.
     *
     * @return the cost of all items times their quantities plus surcharge
     */
    public synchronized double getTotal() {

        return total;
    }

    /**
     * Empties the shopping cart. All items are removed from the shopping cart
     * <code>items</code> list, <code>numberOfItems</code> and
     * <code>total</code> are reset to '<code>0</code>'.
     *
     * @see ShoppingCartItem
     */
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
