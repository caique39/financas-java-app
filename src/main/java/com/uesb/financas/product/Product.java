package com.uesb.financas.product;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;;

public class Product {
  public static class Mapper implements RowMapper<Product> {
    @Override
    public Product map(final ResultSet rs, final StatementContext ctx) throws SQLException {
        return new Product(rs.getLong("id"), rs.getString("name"), rs.getLong("categoryID"), rs.getDouble("price"), rs.getLong("stock"));
    }
  }

  private Long id;
  private String name;
  private Long categoryID;
  private Double price;
  private Long stock;

  public Product (Long id, String name, Long categoryID, Double price, Long stock){
    this.id = id;
    this.name = name;
    this.categoryID = categoryID;
    this.price = price;
    this.stock = stock;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Long getCategoryID() {
    return categoryID;
  }

  public Double getPrice() {
    return price;
  }

  public Long getStock() {
    return stock;
  }

}