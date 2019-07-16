package com.uesb.financas.sale;

import com.uesb.financas.db.DB;
import com.uesb.financas.sale.SaleDaoImpl;

public class SaleFactory {
    public static SaleDao createSaleDao() {
        return new SaleDaoImpl(DB.getConnection());
    }
}
