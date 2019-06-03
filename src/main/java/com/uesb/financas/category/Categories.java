package com.uesb.financas.category;

import org.jooby.Err;
import org.jooby.Results;
import org.jooby.Status;
import org.jooby.Jooby;

public class Categories extends Jooby {

  {
    path("api/v1/categories", () -> {
      get((req) -> {
        CategoryRepository db = require(CategoryRepository.class);

        int start = req.param("start").intValue(0);
        int max = req.param("max").intValue(20);

        return db.list(start, max);
      });

      get("/:id", (req) ->  {
        CategoryRepository db = require(CategoryRepository.class);

        long id = req.param("id").longValue();
        Category category = db.findById(id);

        if (category == null) {
          throw new Err(Status.NOT_FOUND);
        }

        return category;
      });

      post((req) -> {
        CategoryRepository db = require(CategoryRepository.class);
        Category category = req.body(Category.class);

        if (category.getName() == null) {
          throw new Err(Status.NOT_FOUND);
        }

        long id = db.insert(category);

        return new Category(id, category.getName());
      });

      put((req) -> {
        CategoryRepository db = require(CategoryRepository.class);
        Category category = req.body(Category.class);

        if (!db.update(category)) {
          throw new Err(Status.NOT_FOUND);
        }

        return category;
      });

      delete("/:id", (req) -> {
        CategoryRepository db = require(CategoryRepository.class);

        long id = req.param("id").longValue();

        if (!db.delete(id)) {
          throw new Err(Status.NOT_FOUND);
        }

        return Results.noContent();
      });
    });
  }
}