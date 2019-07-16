package com.uesb.financas.sale.item;

import com.uesb.financas.db.*;

import java.sql.*;

/**
 * Database access for sale items.
 */
public class SaleItemDaoImpl implements SaleItemDao {
    private Connection conn;

    public SaleItemDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public long insert(long saleId, SaleItem product) {
        PreparedStatement preSqlStatement = null;

        try {
            preSqlStatement = conn.prepareStatement(
                    "INSERT INTO sales_items (sale_id, product_id, quantity) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preSqlStatement.setLong(1, saleId);
            preSqlStatement.setLong(2, product.getProductId());
            preSqlStatement.setInt(3, product.getQuantity());

            preSqlStatement.executeUpdate();

            ResultSet resultSet = preSqlStatement.getGeneratedKeys();

            if (resultSet.next()) {
                long id = resultSet.getLong(1);

                return id;
            }

            throw new DbException("Não foi possível adicionar o item.");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preSqlStatement);
        }
    }

}