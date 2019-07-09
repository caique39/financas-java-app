package com.uesb.financas.category;

import com.uesb.financas.db.*;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Database access for categories.
 */
public class CategoryDaoImpl implements CategoryDao {
  private Connection conn;

  public CategoryDaoImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public long insert(Category category) {
    PreparedStatement preSqlStatement = null;

    try {
      preSqlStatement = conn.prepareStatement("INSERT INTO categories (name) VALUES (?)",
          Statement.RETURN_GENERATED_KEYS);

      preSqlStatement.setString(1, category.getName());

      preSqlStatement.executeUpdate();

      ResultSet resultSet = preSqlStatement.getGeneratedKeys();

      if (resultSet.next()) {
        long id = resultSet.getLong(1);

        return id;
      }

      throw new DbException("Não foi possível adicionar a categoria.");
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
    }
  }

  @Override
  public void update(Category category) {
    PreparedStatement preSqlStatement = null;

    try {
      preSqlStatement = conn.prepareStatement("UPDATE categories SET name=? WHERE id=?");

      preSqlStatement.setString(1, category.getName());
      preSqlStatement.setLong(2, category.getId());

      int updatedRows = preSqlStatement.executeUpdate();

      if (updatedRows == 0) {
        throw new DbException("Não foi possível atualizar a categoria.");
      }

    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
    }
  }

  @Override
  public void delete(long id) {
    PreparedStatement preSqlStatement = null;

    try {
      preSqlStatement = conn.prepareStatement("UPDATE categories SET active = 0 WHERE id=?");

      preSqlStatement.setLong(1, id);

      int updatedRows = preSqlStatement.executeUpdate();

      if (updatedRows == 0) {
        throw new DbException("Não foi possível deletar a categoria.");
      }
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
    }
  }

  public Category findById(long id) {
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM categories where id = ?");
      preSqlStatement.setLong(1, id);

      resultSet = preSqlStatement.executeQuery();

      if (resultSet.next()) {
        Category category = new Category(resultSet.getLong("id"), resultSet.getString("name"));

        return category;
      }

      throw new DbException("Não foi possível encontrar a categoria informada.");
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }

  public List<Category> list(long start, long max) {
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM categories where active = 1 limit ? offset ?");
      preSqlStatement.setLong(1, max);
      preSqlStatement.setLong(2, start);

      resultSet = preSqlStatement.executeQuery();

      List<Category> categories = new ArrayList<>();

      while (resultSet.next()) {
        Category category = new Category(resultSet.getLong("id"), resultSet.getString("name"));
        categories.add(category);
      }

      return categories;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }
}