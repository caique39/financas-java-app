package com.uesb.financas.customer;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer{
  public static class Mapper implements RowMapper<Customer> {
    @Override public Customer map(final ResultSet rs, final StatementContext ctx) throws SQLException {
      return new Customer(rs.getLong("id"), rs.getString("name"), rs.getString("email"), rs.getString("phone"));
    }
  }
    
  private Long id;
  private String name;
  private String email;
  private String phone;

  public Customer(Long id, String name, String email, String phone) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }  

}

