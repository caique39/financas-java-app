package com.uesb.financas.customer;

import java.util.List;

public interface CustomerDao {
    long insert(Customer customer);

    void update(Customer customer);

    void delete(long id);

    Customer findById(long id);

    Customer findByEmail(String email);

    List<Customer> list(long start, long max);
}
