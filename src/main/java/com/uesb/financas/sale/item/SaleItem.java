package com.uesb.financas.sale.item;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleItem {
  public static class Mapper implements RowMapper<SaleItem> {
    @Override
    public SaleItem map(final ResultSet rs, final StatementContext ctx) throws SQLException {
      return new SaleItem(rs.getLong("id"), rs.getInt("sale_id"), rs.getInt("product_id"), rs.getInt("quantity"));
    }
  }

  private long id;
  private int saleId;
  private int productId;
  private int quantity;

  public SaleItem(long id, int saleId, int productId, int quantity) {
    this.id = id;
    this.saleId = saleId;
    this.productId = productId;
    this.quantity = quantity;
  }

  public Long getId() {
    return id;
  }

  public int getSaleId() {
    return saleId;
  }

  public int getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }
}