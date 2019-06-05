package com.uesb.financas.product;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * Database access for products.
 */
@RegisterRowMapper(Product.Mapper.class)
public interface ProductRepository {
  /**
   * List products using start/max limits.
   *
   * @param start Start offset.
   * @param max   Max number of rows.
   * @return all products.
   */
  @SqlQuery("select * from products where active = 1 limit :max offset :start")
  List<Product> list(int start, int max);

  /**
   * List products in stock.
   *
   * @param stock number of products in stock.
   * @return Available products.
   */
  @SqlQuery("select * from products where stock >= 1")
  List<Product> listProductsInStock();

  /**
   * Find a product by ID.
   *
   * @param id Product ID.
   * @return Product or null.
   */
  @SqlQuery("select * from products where id=:id")
  Product findById(long id);

  /**
   * Find a product by categoryID.
   *
   * @param categoryID CategoryID.
   * @return Product or null.
   */
  @SqlQuery("select * from products where categoryID=:categoryID")
  List<Product> findByCategoryID(long categoryID);

  /**
   * Insert a product and returns the generated PK.
   *
   * @param product Product to insert.
   * @return Primary key.
   */
  @SqlUpdate("insert into products(name, categoryID, price, stock) values(:name, :categoryID, :price, :stock)")
  @GetGeneratedKeys
  long insert(@BindBean Product product);

  /**
   * Update a product and returns true if the products was updated.
   *
   * @param product Product to update.
   * @return True if the Product was updated.
   */
  @SqlUpdate("update products set name=:name, categoryID=:categoryID, price=:price, stock=:stock where id=:id")
  boolean update(@BindBean Product product);

//   /**
//    * Withdraws products from stock when sold.
//    *
//    * @param category Product to update.
//    * @return True if the Product was updated.
//    */
//   @SqlUpdate("update products set stock = :stock - 1 where id=:id")
//   boolean withdrawSoldProduct(@BindBean Product product);

  /**
   * Delete a Product by ID.
   *
   * @param id Product ID.
   * @return True if the Product was deleted.
   */
  @SqlUpdate("update products set active = 0 where id=:id")
  boolean delete(long id);
}