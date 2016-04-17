/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import entity.Customer;
import entity.CustomerOrder;
import javax.ejb.Stateless;

/**
 *
 * @author mikerocha
 */
@Stateless
public class OrderManager {

    public int placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart) {

        Customer customer = addCustomer(name, email, phone, address, cityRegion, ccNumber);
        CustomerOrder order = addOrder(customer, cart);
        addOrderedItems(order, cart);
        
        return order.getId();
    }

    private Customer addCustomer(String name, String email, String phone, String address, String cityRegion, String ccNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
