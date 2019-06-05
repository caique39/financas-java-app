package com.uesb.financas;

import com.uesb.financas.category.Categories;
import com.uesb.financas.category.CategoryRepository;
import com.uesb.financas.customer.Customers;
import com.uesb.financas.product.ProductRepository;
import com.uesb.financas.product.Products;
import com.uesb.financas.customer.CustomerRepository;

import org.jooby.Jooby;
import org.jooby.jdbc.Jdbc;
import org.jooby.jdbi.Jdbi3;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jooby.jdbi.TransactionalRequest;

import org.jooby.json.Jackson;

public class App extends Jooby {

  {
    use(new Jackson());
    use(new Jdbc("db"));
    use(new Jdbi3()
      .doWith(jdbi -> {
        jdbi.installPlugin(new SqlObjectPlugin());
      })
      .transactionPerRequest(
        new TransactionalRequest()
            .attach(CategoryRepository.class).attach(CustomerRepository.class).attach(ProductRepository.class)
      )
  
    );

    get(() -> "Seja bem vindo à API!");

    use(new Categories());
    use(new Customers());
    // use(new Orders());
    use(new Products());
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
