package com.uesb.financas.category;

import com.uesb.financas.db.DbException;

import org.jooby.Err;
import org.jooby.Results;
import org.jooby.Status;
import org.jooby.Jooby;

public class Categories extends Jooby {

  {
    path("api/v1/categories", () -> {
      get((req) -> {
        CategoryDao dao = CategoryFactory.createCategoryDao();

        long start = req.param("start").longValue(0);
        long max = req.param("max").longValue(20);

        return dao.list(start, max);
      });

      get("/:id", (req) -> {
        CategoryDao dao = CategoryFactory.createCategoryDao();

        long id = req.param("id").longValue();

        try {
          Category category = dao.findById(id);

          return category;
        } catch (DbException e) {
          throw new Err(Status.NOT_FOUND);
        }
      });

      post((req) -> {
        CategoryDao dao = CategoryFactory.createCategoryDao();
        Category category = req.body(Category.class);

        if (category.getName() == null) {
          throw new Err(Status.NOT_FOUND);
        }

        try {
          long id = dao.insert(category);

          return new Category(id, category.getName());
        } catch (DbException e) {
          throw new Err(Status.EXPECTATION_FAILED);
        }
      });

      put((req) -> {
        CategoryDao dao = CategoryFactory.createCategoryDao();
        Category category = req.body(Category.class);

        try {
          dao.update(category);

          return Results.noContent();
        } catch (DbException e) {
          throw new Err(Status.NOT_FOUND);
        }
      });

      delete("/:id", (req) -> {
        CategoryDao dao = CategoryFactory.createCategoryDao();

        long id = req.param("id").longValue();

        try {
          dao.delete(id);

          return Results.noContent();
        } catch (DbException e) {
          throw new Err(Status.NOT_FOUND);
        }
      });
    });
  }
}