package com.uesb.financas.category;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Category {
  public static class Mapper implements RowMapper<Category> {
    @Override public Category map(final ResultSet rs, final StatementContext ctx) throws SQLException {
      return new Category(rs.getLong("id"), rs.getString("name"));
    }
  }

  private Long id;
  private String name;

  public Category(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}