package com.uesb.financas.sale;

import org.jooby.Jooby;

import java.util.List;
import java.util.stream.Collectors;

import com.uesb.financas.product.ProductRepository;
import com.uesb.financas.sale.Sale;
import com.uesb.financas.sale.item.SaleItem;

import com.uesb.financas.sale.item.SaleItemRepository;
import com.uesb.financas.sale.SaleRepository;

public class SalesController extends Jooby {
  private SaleRepository saleRepository;

  public SalesController(SaleRepository saleRepository) {
    this.saleRepository = saleRepository;
  }

  public long create(Sale sale) {
    SaleItemRepository saleItemRepository = require(SaleItemRepository.class);
    List<SaleItem> cart = sale.getCart();

    double grossAmount = getGrossAmount(cart);
    double netAmount = getNetAmount(grossAmount, sale.getDiscount());

    // if (category.getName() == null) {
    // throw new Err(Status.NOT_FOUND);
    // }

    sale.setGrossAmount(grossAmount);
    sale.setNetAmount(netAmount);

    long id = saleRepository.insert(sale);
    saleItemRepository.insert(id, cart);

    return id;
  }

  public double getGrossAmount(List<SaleItem> cart) {
    ProductRepository productRepository = require(ProductRepository.class);

    List<Object> prices = cart.stream().map(saleItem -> productRepository.findById(saleItem.getId()))
        .collect(Collectors.toList());

    for (Object price : prices) {
      System.out.println(price);
    }

    return 10;
  }

  public double getNetAmount(double grossAmount, double discount) {
    return grossAmount - discount;
  }
}