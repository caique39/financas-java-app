package com.uesb.financas.product;

import org.jooby.Err;
import org.jooby.Results;
import org.jooby.Status;
import org.jooby.Jooby;

public class Products extends Jooby {
  {
    path("api/v1/products", () -> {
      get((req) -> {
        ProductRepository db = require(ProductRepository.class);

        int start = req.param("start").intValue(0);
        int max = req.param("max").intValue(20);

        return db.list(start, max);
      });

      get("/inStock", (req) ->  {
        ProductRepository db = require(ProductRepository.class);

        return db.listProductsInStock();
      });

      get("/:id", (req) ->  {
        ProductRepository db = require(ProductRepository.class);

        long id = req.param("id").longValue();
        Product product = db.findById(id);

        if (product == null) {
          throw new Err(Status.NOT_FOUND);
        }

        return product;
      });

      get("/byCategory/:categoryID", (req) ->  {
        ProductRepository db = require(ProductRepository.class);

        long categoryID = req.param("categoryID").longValue();
        
        return db.findByCategoryID(categoryID);
      });

      post((req) -> {
        ProductRepository db = require(ProductRepository.class);
        Product product = req.body(Product.class);

        if (product.getName() == null) {
          throw new Err(Status.NOT_FOUND);
        }

        long id = db.insert(product);

        return new Product(id, product.getName(), product.getCategoryID(), product.getPrice(), product.getStock());
      });

      put((req) -> {
        ProductRepository db = require(ProductRepository.class);
        Product product = req.body(Product.class);

        if (!db.update(product)) {
          throw new Err(Status.NOT_FOUND);
        }

        return product;
      });

      delete("/:id", (req) -> {
        ProductRepository db = require(ProductRepository.class);

        long id = req.param("id").longValue();

        if (!db.delete(id)) {
          throw new Err(Status.NOT_FOUND);
        }

        return Results.noContent();
      });
    });
  }
}