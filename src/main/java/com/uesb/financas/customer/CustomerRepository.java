package com.uesb.financas.customer;

import com.uesb.financas.customer.Customer;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * Database access for customers.
 */
@RegisterRowMapper(Customer.Mapper.class)
public interface CustomerRepository {
  /**
   * List customers using start/max limits.
   *
   * @param start Start offset.
   * @param max   Max number of rows.
   * @return Available customers.
   */
  @SqlQuery("select * from customers where active = 1 limit :max offset :start")
  List<Customer> list(int start, int max);

  /**
   * Find a customer by ID.
   *
   * @param id Customer ID.
   * @return Customer or null.
   */
  @SqlQuery("select * from customers where id=:id")
  Customer findById(long id);

  /**
   * Find a customer by email.
   *
   * @param id Customer ID.
   * @return Customer or null.
   */
  @SqlQuery("select * from customers where email=:email")
  Customer findByEmail(String email);

  /**
   * Insert a customer and returns the generated PK.
   *
   * @param customer Customer to insert.
   * @return Primary key.
   */
  @SqlUpdate("insert into customers(name, email, phone) values(:name, :email, :phone)")
  @GetGeneratedKeys
  long insert(@BindBean Customer customer);

  /**
   * Update a customer and returns true if the customers was updated.
   *
   * @param customer Customer to update.
   * @return True if the customer was updated.
   */
  @SqlUpdate("update customers set name=:name, email=:email, phone=:phone where id=:id")
  boolean update(@BindBean Customer customer);

  /**
   * Delete a customer by ID.
   *
   * @param id Customer ID.
   * @return True if the customer was deleted.
   */
  @SqlUpdate("update customers set active = 0 where id=:id")
  boolean delete(long id);
}