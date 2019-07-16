package com.uesb.financas.sale.item;

import java.util.List;

public interface SaleItemDao {
    long insert(long saleId, SaleItem product);
    List<SaleItem> listBySaleId(long id);
}