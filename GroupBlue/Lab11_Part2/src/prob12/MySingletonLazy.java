package prob12;

import java.sql.Connection;
import java.util.Optional;

public class MySingletonLazy {

  private static MySingletonLazy instance = null;

  private MySingletonLazy() {
  }

  public static MySingletonLazy getInstance() {
    instance = Optional.ofNullable(instance).orElseGet(MySingletonLazy::new);
    return instance;
  }

  public static void main(String[] args) {
    MySingletonLazy singleton1 = MySingletonLazy.getInstance();
    MySingletonLazy singleton2 = MySingletonLazy.getInstance();

    System.out.println("Same instance? " + (singleton1 == singleton2));
  }
}
