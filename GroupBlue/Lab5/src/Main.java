import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/*import prob1.DecoyDuck;
import prob1.Duck;
import prob1.DuckFactory;
import prob1.MallardDuck;
import prob1.RedHeadDuck;
import prob1.RubberDuck;*/
import prob2.*;

public class Main {
	public static void main(String[] args) {
		/**
		 Duck[] ducks = {
		 DuckFactory.createDuck("Mallard"), DuckFactory.createDuck("Decoy"), DuckFactory.createDuck("RedHead"), DuckFactory.createDuck("Rubber")
		 };

		 for(Duck duck : ducks) {
		 System.out.println(duck.getClass().getSimpleName()+ " : ");
		 //duck.display();
		 duck.fly();
		 duck.quack();
		 duck.swim();
		 System.out.println();
		 }
		 */
		CustomerAndOrders result = CustomerOrderFactory.createCustomerAndOrder("Cheick", LocalDate.now());

        Customer customer = result.getCustomer();
        Order order = result.getOrder();

        order.addItem(new Item("Laptop"));
        order.addItem(new Item("Headphones"));


        Order secondOrder = CustomerOrderFactory.createOrder(LocalDate.of(2025, 4, 8));
        secondOrder.addItem(new Item("Mouse"));
        customer.addOrder(secondOrder);

        System.out.println(customer);
    }
}
