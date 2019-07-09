package com.uesb.financas.category;

import java.util.List;

public interface CategoryDao {
    long insert(Category category);

    void update(Category category);

    void delete(long id);

    Category findById(long id);

    List<Category> list(long start, long max);
}
