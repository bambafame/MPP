package prob2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

final public class Order {
    private final LocalDate orderDate;
    private final List<Item> items;

    Order(LocalDate orderDate) {
        this.orderDate = orderDate;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderDate=" + orderDate +
                ", items=" + items +
                '}';
    }
}

