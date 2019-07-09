package com.uesb.financas.sale;

import org.jooby.Err;
import org.jooby.Results;
import org.jooby.Status;
import org.jooby.Jooby;

import java.util.List;

import com.uesb.financas.sale.SalesController;
import com.uesb.financas.sale.item.SaleItem;
import com.uesb.financas.sale.item.SaleItemRepository;

public class Sales extends Jooby {
  {
    path("api/v1/sales", () -> {
      // get((req) -> {
      // CategoryRepository db = require(CategoryRepository.class);

      // int start = req.param("start").intValue(0);
      // int max = req.param("max").intValue(20);

      // return db.list(start, max);
      // });

      get("/:id", (req) -> {
        SaleRepository db = require(SaleRepository.class);

        long id = req.param("id").longValue();
        Sale sale = db.findById(id);

        if (sale == null) {
          throw new Err(Status.NOT_FOUND);
        }

        return sale;
      });

      post("/", (req) -> {
        Sale sale = req.body(Sale.class);
        SaleRepository db = require(SaleRepository.class);
        SalesController saleController = new SalesController(db);

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