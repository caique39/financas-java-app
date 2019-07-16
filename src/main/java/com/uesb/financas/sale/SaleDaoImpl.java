package com.uesb.financas.sale;

import com.uesb.financas.db.*;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Database access for sales.
 */
public class SaleDaoImpl implements SaleDao {
  private Connection conn;

  public SaleDaoImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public long insert(Sale sale) {
    PreparedStatement preSqlStatement = null;

    try {
      preSqlStatement = conn.prepareStatement("INSERT INTO sales (customer_id, gross_amount, net_amount, amount_paid, discount) VALUES (?, ?, ?, ?, ?)",
          Statement.RETURN_GENERATED_KEYS);

      preSqlStatement.setLong(1, sale.getCustomerId());
      preSqlStatement.setDouble(2, sale.getGrossAmount());
      preSqlStatement.setDouble(3, sale.getNetAmount());
      preSqlStatement.setDouble(4, sale.getAmountPaid());
      preSqlStatement.setDouble(5, sale.getDiscount());

      preSqlStatement.executeUpdate();

      ResultSet resultSet = preSqlStatement.getGeneratedKeys();

      if (resultSet.next()) {
        long id = resultSet.getLong(1);

        return id;
      }

      throw new DbException("Não foi possível adicionar a venda.");
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
    }
  }

  @Override
  public void update(Sale sale) {
    PreparedStatement preSqlStatement = null;

    try {
      preSqlStatement = conn.prepareStatement("UPDATE sales SET customer_id=?, gross_amount=?, net_amount=?, amount_paid=?, discount=? WHERE id=?");

      preSqlStatement.setLong(1, sale.getCustomerId());
      preSqlStatement.setDouble(2, sale.getGrossAmount());
      preSqlStatement.setDouble(3, sale.getNetAmount());
      preSqlStatement.setDouble(4, sale.getAmountPaid());
      preSqlStatement.setDouble(5, sale.getDiscount());

      int updatedRows = preSqlStatement.executeUpdate();

      if (updatedRows == 0) {
        throw new DbException("Não foi possível atualizar a venda.");
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
      preSqlStatement = conn.prepareStatement("UPDATE sales SET active = 0 WHERE id=?");

      preSqlStatement.setLong(1, id);

      int updatedRows = preSqlStatement.executeUpdate();

      if (updatedRows == 0) {
        throw new DbException("Não foi possível deletar a venda.");
      }
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
    }
  }

  
  public Sale findById(long id) {
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM sales where id = ?");
      preSqlStatement.setLong(1, id);

      resultSet = preSqlStatement.executeQuery();

      if (resultSet.next()) {
        Sale sale = new Sale(resultSet.getLong("id"), resultSet.getLong("customer_id"), resultSet.getDouble("gross_amount"), resultSet.getDouble("net_amount"), resultSet.getDouble("amount_paid"), resultSet.getDouble("discount"), null);

        return sale;
      }

      throw new DbException("Não foi possível encontrar a venda informado.");
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }


  public List<Sale> list(long start, long max) {
    PreparedStatement preSqlStatement = null;
    ResultSet resultSet = null;
    try {
      preSqlStatement = conn.prepareStatement("SELECT * FROM sales limit ? offset ?");
      preSqlStatement.setLong(1, max);
      preSqlStatement.setLong(2, start);

      resultSet = preSqlStatement.executeQuery();

      List<Sale> sales = new ArrayList<>();

      while (resultSet.next()) {
        Sale sale = new Sale(resultSet.getLong("id"), resultSet.getLong("customer_id"), resultSet.getDouble("gross_amount"), resultSet.getDouble("net_amount"), resultSet.getDouble("amount_paid"), resultSet.getDouble("discount"), null);
        sales.add(sale);
      }

      return sales;
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preSqlStatement);
      DB.closeResultSet(resultSet);
    }
  }

  
}