package com.uesb.financas.sale;

import java.util.List;

import com.uesb.financas.sale.item.SaleItem;;

public class Sale {

  private long id;
  private long customerId;
  private double grossAmount;
  private double netAmount;
  private double amountPaid;
  private double discount;
  private List<SaleItem> cart;

  public Sale(long id, long customerId, double grossAmount, double netAmount, double amountPaid,
      double discount, List<SaleItem> cart) {
    this.id = id;
    this.customerId = customerId;
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