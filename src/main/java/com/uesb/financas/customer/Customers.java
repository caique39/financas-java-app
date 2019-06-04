package com.uesb.financas.customer;

import org.jooby.Err;
import org.jooby.Results;
import org.jooby.Status;
import org.jooby.Jooby;

public class Customers extends Jooby {

  {
    path("api/v1/customers", () -> {
      get((req) -> {
        CustomerRepository db = require(CustomerRepository.class);

        int start = req.param("start").intValue(0);
        int max = req.param("max").intValue(20);

        return db.list(start, max);
      });

      get("/:id", (req) ->  {
        CustomerRepository db = require(CustomerRepository.class);

        long id = req.param("id").longValue();
        Customer customer = db.findById(id);

        if (customer == null) {
          throw new Err(Status.NOT_FOUND);
        }

        return customer;
      });

      post((req) -> {
        CustomerRepository db = require(CustomerRepository.class);
        Customer customer = req.body(Customer.class);

        if (customer.getName() == null) {
          throw new Err(Status.NOT_FOUND);
        }

        long id = db.insert(customer);

        return new Customer(id, customer.getName(), customer.getEmail(), customer.getPhone());
      });

      put((req) -> {
        CustomerRepository db = require(CustomerRepository.class);
        Customer customer = req.body(Customer.class);

        if (!db.update(customer)) {
          throw new Err(Status.NOT_FOUND);
        }

        return customer;
      });

      delete("/:id", (req) -> {
        CustomerRepository db = require(CustomerRepository.class);

        long id = req.param("id").longValue();

        if (!db.delete(id)) {
          throw new Err(Status.NOT_FOUND);
        }

        return Results.noContent();
      });
    });
  }
}