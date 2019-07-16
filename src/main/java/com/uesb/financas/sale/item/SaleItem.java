package com.uesb.financas.sale.item;

public class SaleItem {

  private long id;
  private long saleId;
  private long productId;
  private int quantity;

  public SaleItem(long id, long saleId, long productId, int quantity) {
    this.id = id;
    this.saleId = saleId;
    this.productId = productId;
    this.quantity = quantity;
  }

  public Long getId() {
    return id;
  }

  public long getSaleId() {
    return saleId;
  }

  public long getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }
}