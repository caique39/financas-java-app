package com.uesb.financas.product;

import com.uesb.financas.db.*;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Database access for products.
 */
public class ProductDaoImpl implements ProductDao {
  private Connection conn;

  public ProductDaoImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public long insert(Product product) {
    PreparedStatement preSqlStatement = null;

    try {
      preSqlStatement = conn.prepareStatement("INSERT INTO products (name, categoryID, price, stock) VALUES (?, ?, ?, ?)",
          Statement.RETURN_GENERATED_KEYS);

      preSqlStatement.setString(1, product.getName());
      preSqlStatement.setLong(2, product.getCategoryID());
      preSqlStatement.setDouble(3, product.getPrice());
      preSqlStatement.setLong(4, product.getStock());

      preSqlStatement.executeUpdate();

      ResultSet resultSet = preSqlStatement.getGeneratedKeys();

      if (resultSet.next()) {
        long id = resultSet.getLong(1);

        return id;
      }

      throw new DbException("Não foi possível adicionar o cliente.");
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
    }
  }

  @Override
  public void update(Product product) {
    PreparedStatement preSqlStatement = null;

    try {
      preSqlStatement = conn.prepareStatement("UPDATE products SET name=?, categoryID=?, price=?, stock=? WHERE id=?");

      preSqlStatement.setString(1, product.getName());
      preSqlStatement.setLong(2, product.getCategoryID());
      preSqlStatement.setDouble(3, product.getPrice());
      preSqlStatement.setLong(4, product.getStock());
      preSqlStatement.setLong(5, product.getId());

      int updatedRows = preSqlStatement.executeUpdate();

      if (updatedRows == 0) {
        throw new DbException("Não foi possível atualizar o cliente.");
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
      preSqlStatement = conn.prepareStatement("UPDATE products SET active = 0 WHERE id=?");

      preSqlStatement.setLong(1, id);

      int updatedRows = preSqlStatement.executeUpdate();

      if (updatedRows == 0) {
        throw new DbException("Não foi possível deletar o cliente.");
      }
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
    }
  }

  
  public Product findById(long id) {
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM products where id = ?");
      preSqlStatement.setLong(1, id);

      resultSet = preSqlStatement.executeQuery();

      if (resultSet.next()) {
        Product product = new Product(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getLong("categoryID"), resultSet.getDouble("price"), resultSet.getLong("stock"));

        return product;
      }

      throw new DbException("Não foi possível encontrar o cliente informado.");
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }


  public List<Product> list(long start, long max) {
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM products where active = 1 limit ? offset ?");
      preSqlStatement.setLong(1, max);
      preSqlStatement.setLong(2, start);

      resultSet = preSqlStatement.executeQuery();

      List<Product> products = new ArrayList<>();

      while (resultSet.next()) {
        Product product = new Product(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getLong("categoryID"), resultSet.getDouble("price"), resultSet.getLong("stock"));
        products.add(product);
      }

      return products;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }

  public List<Product> listProductsInStock() {
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM products WHERE stock >= 1");

      resultSet = preSqlStatement.executeQuery();

      List<Product> products = new ArrayList<>();

      while (resultSet.next()) {
        Product product = new Product(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getLong("categoryID"), resultSet.getDouble("price"), resultSet.getLong("stock"));
        products.add(product);
      }

      return products;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }

  public List<Product> findByCategoryID(long categoryID){
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM products where categoryID=?");
      preSqlStatement.setLong(1, categoryID);

      resultSet = preSqlStatement.executeQuery();

      List<Product> products = new ArrayList<>();

      while (resultSet.next()) {
        Product product = new Product(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getLong("categoryID"), resultSet.getDouble("price"), resultSet.getLong("stock"));
        products.add(product);
      }

      return products;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }
}