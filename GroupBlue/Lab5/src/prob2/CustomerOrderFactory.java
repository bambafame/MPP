package prob2;

import java.time.LocalDate;

final public class CustomerOrderFactory {
    private CustomerOrderFactory() {}

    public static CustomerAndOrders createCustomerAndOrder(String custName, LocalDate orderDate) {
        Customer customer = new Customer(custName);
        Order order = new Order(orderDate);
        customer.addOrder(order); // only Customer references Order
        return new CustomerAndOrdersImpl(customer, order);
    }
	/**
	After creating a Customer and their first Order, we may want to allow more orders to be added, if not no need for this method.
	*/
    public static Order createOrder(LocalDate orderDate) {
        return new Order(orderDate);
    }
}


