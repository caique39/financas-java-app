package com.uesb.financas.product;

import com.uesb.financas.db.DbException;

import org.jooby.Err;
import org.jooby.Results;
import org.jooby.Status;
import org.jooby.Jooby;

public class Products extends Jooby {
  {
    path("api/v1/products", () -> {
      get((req) -> {
        ProductDao dao = ProductFactory.createProductDao();

        int start = req.param("start").intValue(0);
        int max = req.param("max").intValue(20);

        return dao.list(start, max);
      });

      get("/inStock", (req) ->  {
        ProductDao dao = ProductFactory.createProductDao();

        return dao.listProductsInStock();
      });

      get("/:id", (req) ->  {
        ProductDao dao = ProductFactory.createProductDao();

        long id = req.param("id").longValue();

        try {
          Product product = dao.findById(id);

          return product;
        } catch (DbException e) {
          throw new Err(Status.NOT_FOUND);
        }
      });

      get("/byCategory/:categoryID", (req) ->  {
        ProductDao dao = ProductFactory.createProductDao();

        long categoryID = req.param("categoryID").longValue();
        
        return dao.findByCategoryID(categoryID);
      });

      post((req) -> {
        ProductDao dao = ProductFactory.createProductDao();
        Product product = req.body(Product.class);

        if (product.getName() == null) {
          throw new Err(Status.NOT_FOUND);
        }

        try {
          long id = dao.insert(product);

          return new Product(id, product.getName(), product.getCategoryID(), product.getPrice(), product.getStock());
        } catch (DbException e) {
          throw new Err(Status.EXPECTATION_FAILED);
        }
        
      });

      put((req) -> {
        ProductDao dao = ProductFactory.createProductDao();
        Product product = req.body(Product.class);

        try {
          dao.update(product);

          return Results.noContent();
        } catch (DbException e) {
          throw new Err(Status.NOT_FOUND);
        }

      });

      delete("/:id", (req) -> {
        ProductDao dao = ProductFactory.createProductDao();

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