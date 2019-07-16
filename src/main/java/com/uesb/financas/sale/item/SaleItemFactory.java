package com.uesb.financas.sale.item;

import com.uesb.financas.db.DB;

public class SaleItemFactory {
    public static SaleItemDao createSaleItemDao() {
        return new SaleItemDaoImpl(DB.getConnection());
    }
}
