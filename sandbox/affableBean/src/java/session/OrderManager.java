/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.OrderedProductPK;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
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

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCityRegion(cityRegion);
        customer.setCcNumber(ccNumber);
        
        return customer;

    }

    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {

        CustomerOrder order = new CustomerOrder();
        order.setCustomerId(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));
        
        // create i number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);
        
        return order;
    }

    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {

        List<ShoppingCartItem> items = cart.getItems();
        
        // iterate through shopping cart and create OrderedProducts
        for (ShoppingCartItem item : items) {
            
            // set up primary key object
            OrderedProductPK orderedProductPK = new OrderedProductPK();
            orderedProductPK.setCustomerOrderId(order.getId());
            orderedProductPK.setProductId(item.getProductId());
            
            // create ordered item using PK object
            short quantity = (short)item.getQuantity();
            OrderedProduct orderedProduct = new OrderedProduct(orderedProductPK, quantity);
            
        }

    }
}
