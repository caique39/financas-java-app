package com.uesb.financas.sale.item;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;

import java.util.List;

/**
 * Database access for categories.
 */
@RegisterRowMapper(SaleItem.Mapper.class)
public interface SaleItemRepository {
  /**
   * Insert a list of products.
   *
   * @param saleId long to insert.
   * @param cart   List<SaleItem> to insert.
   */
  @SqlBatch("insert into sales_items(sale_id, product_id, quantity) values(:saleId, :productId, :quantity)")
  void insert(long saleId, @BindBean List<SaleItem> cart);
}