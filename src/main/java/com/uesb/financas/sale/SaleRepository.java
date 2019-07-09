package com.uesb.financas.sale;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * Database access for categories.
 */
@RegisterRowMapper(Sale.Mapper.class)
public interface SaleRepository {
  /**
   * List sales using start/max limits.
   *
   * @param start Start offset.
   * @param max   Max number of rows.
   * @return Available sales.
   */
  @SqlQuery("select * from sales limit :max offset :start")
  List<Sale> list(int start, int max);

  /**
   * Find a category by ID.
   *
   * @param id Category ID.
   * @return Category or null.
   */
  @SqlQuery("select * from sales where id=:id")
  Sale findById(long id);

  /**
   * Insert a sale and returns the generated PK.
   *
   * @param sale Sale to insert.
   * @return Primary key.
   */
  @SqlUpdate("insert into sales(customer_id, payment_method_id, gross_amount, net_amount, amount_paid) values(:customerId, :paymentMethodId, :grossAmount, :netAmount, :amountPaid)")
  @GetGeneratedKeys
  long insert(@BindBean Sale sale);

  /**
   * Update a category and returns true if the categories was updated.
   *
   * @param category Category to update.
   * @return True if the category was updated.
   */
  @SqlUpdate("update categories set name=:name where id=:id")
  boolean update(@BindBean Sale sale);

  /**
   * Delete a category by ID.
   *
   * @param id Category ID.
   * @return True if the category was deleted.
   */
  @SqlUpdate("update categories set active = 0 where id=:id")
  boolean delete(long id);
}