package com.uesb.financas.product;

public class Product {

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