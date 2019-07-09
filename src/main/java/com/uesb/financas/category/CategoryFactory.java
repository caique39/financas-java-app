package com.uesb.financas.category;

import com.uesb.financas.db.DB;

public class CategoryFactory {
    public static CategoryDao createCategoryDao() {
        return new CategoryDaoImpl(DB.getConnection());
    }
}
