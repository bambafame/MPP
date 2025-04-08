package prob2;

final class CustomerAndOrdersImpl implements CustomerAndOrders {
    private Customer customer;
    private Order order;

    CustomerAndOrdersImpl(Customer customer, Order order) {
        this.customer = customer;
        this.order = order;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public Order getOrder() {
        return order;
    }
}


