package com.uesb.financas.sale;

import org.jooby.Jooby;

import java.util.List;
import java.util.stream.Collectors;

import com.uesb.financas.product.ProductDao;
import com.uesb.financas.product.ProductFactory;
import com.uesb.financas.sale.Sale;
import com.uesb.financas.sale.item.SaleItem;
import com.uesb.financas.sale.item.SaleItemDao;
import com.uesb.financas.sale.item.SaleItemFactory;

public class SalesController extends Jooby {
  private SaleDao saleDao;

  public SalesController(SaleDao saleDao) {
    this.saleDao = saleDao;
  }

  public long create(Sale sale) {
    SaleItemDao saleItemDao = SaleItemFactory.createSaleItemDao();
    List<SaleItem> cart = sale.getCart();

    double grossAmount = getGrossAmount(cart);
    double netAmount = getNetAmount(grossAmount, sale.getDiscount());

    sale.setGrossAmount(grossAmount);
    sale.setNetAmount(netAmount);

    long id = saleDao.insert(sale);

    for ( SaleItem product : cart) {
      saleItemDao.insert(id, product);
    }

    return id;
  }

  public double getGrossAmount(List<SaleItem> cart) {
    ProductDao productDao = ProductFactory.createProductDao();

    List<Object> prices = cart.stream().map(saleItem -> productDao.findById(saleItem.getId()))
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