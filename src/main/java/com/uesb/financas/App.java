package com.uesb.financas;

import com.uesb.financas.category.Categories;
import com.uesb.financas.customer.Customers;
import com.uesb.financas.product.Products;
import com.uesb.financas.sale.Sales;

import org.jooby.Jooby;
import org.jooby.jdbc.Jdbc;

import org.jooby.json.Jackson;

public class App extends Jooby {

  {
    use(new Jackson());
    use(new Jdbc("db"));

    get(() -> "Seja bem vindo à API!");

    use(new Categories());
    use(new Customers());
    use(new Sales());
    use(new Products());
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
