package com.uesb.financas.sale;

import java.util.List;

public interface SaleDao {
    long insert(Sale sale);

    void update(Sale sale);

    void delete(long id);

    Sale findById(long id);

    List<Sale> list(long start, long max);

}
