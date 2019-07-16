package com.uesb.financas.sale;

import org.jooby.Err;
import org.jooby.Status;
import org.jooby.Jooby;

import com.uesb.financas.db.DbException;
import com.uesb.financas.sale.SalesController;


public class Sales extends Jooby {
  {
    path("api/v1/sales", () -> {
      get((req) -> {
        SaleDao dao = SaleFactory.createSaleDao();

        int start = req.param("start").intValue(0);
        int max = req.param("max").intValue(20);

        return dao.list(start, max);
      });

      get("/:id", (req) ->  {
        SaleDao dao = SaleFactory.createSaleDao();

        long id = req.param("id").longValue();

        try {
          Sale sale = dao.findById(id);

          return sale;
        } catch (DbException e) {
          throw new Err(Status.NOT_FOUND);
        }
      });

      post("/", (req) -> {
        Sale sale = req.body(Sale.class);
        SaleDao dao = SaleFactory.createSaleDao();
        SalesController saleController = new SalesController(dao);

        return saleController.create(sale);
      });

      // put((req) -> {
      // CategoryRepository db = require(CategoryRepository.class);
      // Category category = req.body(Category.class);

      // if (!db.update(category)) {
      // throw new Err(Status.NOT_FOUND);
      // }

      // return Results.noContent();
      // });

      // delete("/:id", (req) -> {
      // CategoryRepository db = require(CategoryRepository.class);

      // long id = req.param("id").longValue();

      // if (!db.delete(id)) {
      // throw new Err(Status.NOT_FOUND);
      // }

      // return Results.noContent();
      // });
    });
  }
}