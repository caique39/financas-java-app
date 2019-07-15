package com.uesb.financas.product;

import java.util.List;

public interface ProductDao {
    long insert(Product product);

    void update(Product product);

    void delete(long id);

    Product findById(long id);

    List<Product> list(long start, long max);

    List<Product> listProductsInStock();

    List<Product> findByCategoryID(long categoryID);
}
