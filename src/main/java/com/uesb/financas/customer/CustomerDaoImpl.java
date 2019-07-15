package com.uesb.financas.customer;

import com.uesb.financas.db.*;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Database access for customers.
 */
public class CustomerDaoImpl implements CustomerDao {
  private Connection conn;

  public CustomerDaoImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public long insert(Customer customer) {
    PreparedStatement preSqlStatement = null;

    try {
      preSqlStatement = conn.prepareStatement("INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)",
          Statement.RETURN_GENERATED_KEYS);

      preSqlStatement.setString(1, customer.getName());
      preSqlStatement.setString(2, customer.getEmail());
      preSqlStatement.setString(3, customer.getPhone());

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
  public void update(Customer customer) {
    PreparedStatement preSqlStatement = null;

    try {
      preSqlStatement = conn.prepareStatement("UPDATE customers SET name=? WHERE id=?");

      preSqlStatement.setString(1, customer.getName());
      preSqlStatement.setLong(2, customer.getId());

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
      preSqlStatement = conn.prepareStatement("UPDATE customers SET active = 0 WHERE id=?");

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

  public Customer findById(long id) {
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM customers where id = ?");
      preSqlStatement.setLong(1, id);

      resultSet = preSqlStatement.executeQuery();

      if (resultSet.next()) {
        Customer customer = new Customer(resultSet.getLong("id"), resultSet.getString("name"),
            resultSet.getString("email"), resultSet.getString("phone"));

        return customer;
      }

      throw new DbException("Não foi possível encontrar o cliente informado.");
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }

  @Override
  public Customer findByEmail(String email) {
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM customers WHERE email = ?");
      preSqlStatement.setString(1, email);

      resultSet = preSqlStatement.executeQuery();

      if (resultSet.next()) {
        Customer customer = new Customer(resultSet.getLong("id"), resultSet.getString("name"),
            resultSet.getString("email"), resultSet.getString("phone"));

        return customer;
      }

      throw new DbException("Não foi possível encontrar o cliente informado.");
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }

  public List<Customer> list(long start, long max) {
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM customers where active = 1 limit ? offset ?");
      preSqlStatement.setLong(1, max);
      preSqlStatement.setLong(2, start);

      resultSet = preSqlStatement.executeQuery();

      List<Customer> customers = new ArrayList<>();

      while (resultSet.next()) {
        Customer customer = new Customer(resultSet.getLong("id"), resultSet.getString("name"),
            resultSet.getString("email"), resultSet.getString("phone"));
        customers.add(customer);
      }

      return customers;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }
}