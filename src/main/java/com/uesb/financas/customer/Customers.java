package com.uesb.financas.customer;

import com.uesb.financas.db.DbException;

import org.jooby.Err;
import org.jooby.Results;
import org.jooby.Status;
import org.jooby.Jooby;

public class Customers extends Jooby {

  {
    path("api/v1/customers", () -> {
      get((req) -> {
        CustomerDao dao = CustomerFactory.createCustomerDao();

        int start = req.param("start").intValue(0);
        int max = req.param("max").intValue(20);

        return dao.list(start, max);
      });

      get("/:id", (req) -> {
        CustomerDao dao = CustomerFactory.createCustomerDao();

        long id = req.param("id").longValue();

        try {
          Customer customer = dao.findById(id);

          return customer;
        } catch (DbException e) {
          throw new Err(Status.NOT_FOUND);
        }

      });

      get("/:email", (req) -> {
        CustomerDao dao = CustomerFactory.createCustomerDao();

        String email = req.param("email").value();

        try {
          Customer customer = dao.findByEmail(email);

          return customer;
        } catch (DbException e) {
          throw new Err(Status.NOT_FOUND);
        }

      });

      post((req) -> {
        CustomerDao dao = CustomerFactory.createCustomerDao();
        Customer customer = req.body(Customer.class);

        if (customer.getName() == null) {
          throw new Err(Status.NOT_FOUND);
        }

        try {
          long id = dao.insert(customer);

          return new Customer(id, customer.getName(), customer.getEmail(), customer.getPhone());
        } catch (DbException e) {
          throw new Err(Status.EXPECTATION_FAILED);
        }
        
      });

      put((req) -> {
        CustomerDao dao = CustomerFactory.createCustomerDao();
        Customer customer = req.body(Customer.class);

        try {
          dao.update(customer);

          return Results.noContent();
        } catch (DbException e) {
          throw new Err(Status.NOT_FOUND);
        }

      });

      delete("/:id", (req) -> {
        CustomerDao dao = CustomerFactory.createCustomerDao();

        long id = req.param("id").longValue();

        try {
          dao.delete(id);

          return Results.noContent();
        } catch (DbException e) {
          throw new Err(Status.NOT_FOUND);
        }
      });
    });
  }
}