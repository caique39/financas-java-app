package com.uesb.financas.customer;

import com.uesb.financas.db.DB;

public class CustomerFactory {
    public static CustomerDao createCustomerDao() {
        return new CustomerDaoImpl(DB.getConnection());
    }
}
