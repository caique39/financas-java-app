package com.uesb.financas.sale;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import com.uesb.financas.sale.item.SaleItem;;

public class Sale {
  public static class Mapper implements RowMapper<Sale> {
    @Override
    public Sale map(final ResultSet rs, final StatementContext ctx) throws SQLException {
      return new Sale(rs.getLong("id"), rs.getLong("customer_id"), rs.getLong("payment_method_id"),
          rs.getDouble("gross_value"), rs.getDouble("net_value"), rs.getDouble("amount_paid"), rs.getDouble("discount"),
          new ArrayList<SaleItem>());
    }
  }

  private long id;
  private long customerId;
  private long paymentMethodId;
  private double grossAmount;
  private double netAmount;
  private double amountPaid;
  private double discount;
  private List<SaleItem> cart;

  public Sale(long id, long customerId, long paymentMethodId, double grossAmount, double netAmount, double amountPaid,
      double discount, List<SaleItem> cart) {
    this.id = id;
    this.customerId = customerId;
    this.paymentMethodId = paymentMethodId;
    this.grossAmount = grossAmount;
    this.netAmount = netAmount;
    this.amountPaid = amountPaid;
    this.discount = discount;
    this.cart = cart;
  }

  public long getId() {
    return id;
  }

  public long getCustomerId() {
    return customerId;
  }

  public long getPaymentMethodId() {
    return paymentMethodId;
  }

  public double getGrossAmount() {
    return grossAmount;
  }

  public double getNetAmount() {
    return netAmount;
  }

  public double getAmountPaid() {
    return amountPaid;
  }

  public double getDiscount() {
    return discount;
  }

  public List<SaleItem> getCart() {
    return cart;
  }

  public void setGrossAmount(double grossAmount) {
    this.grossAmount = grossAmount;
  }

  public void setNetAmount(double netAmount) {
    this.netAmount = netAmount;
  }
}