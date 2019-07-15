package com.uesb.financas.product;

import com.uesb.financas.db.DB;

public class ProductFactory {
    public static ProductDao createProductDao() {
        return new ProductDaoImpl(DB.getConnection());
    }
}
