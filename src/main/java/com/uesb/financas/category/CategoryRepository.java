package com.uesb.financas.category;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * Database access for categories.
 */
@RegisterRowMapper(Category.Mapper.class)
public interface CategoryRepository {
  /**
   * List categories using start/max limits.
   *
   * @param start Start offset.
   * @param max   Max number of rows.
   * @return Available categories.
   */
  @SqlQuery("select * from categories where active = 1 limit :max offset :start")
  List<Category> list(int start, int max);

  /**
   * Find a category by ID.
   *
   * @param id Category ID.
   * @return Category or null.
   */
  @SqlQuery("select * from categories where id=:id")
  Category findById(long id);

  /**
   * Insert a category and returns the generated PK.
   *
   * @param category Category to insert.
   * @return Primary key.
   */
  @SqlUpdate("insert into categories(name) values(:name)")
  @GetGeneratedKeys
  long insert(@BindBean Category category);

  /**
   * Update a category and returns true if the categories was updated.
   *
   * @param category Category to update.
   * @return True if the category was updated.
   */
  @SqlUpdate("update categories set name=:name where id=:id")
  boolean update(@BindBean Category category);

  /**
   * Delete a category by ID.
   *
   * @param id Category ID.
   * @return True if the category was deleted.
   */
  @SqlUpdate("update categories set active = 0 where id=:id")
  boolean delete(long id);
}