package ui.customer;

import dao.DBUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewProductsPanel extends JPanel {

    private JTable productsTable;

    public ViewProductsPanel() {
        setLayout(new BorderLayout());

        // Table model with correct column names
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Product ID", "Name", "Price", "Quantity", "Category"});
        productsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        add(scrollPane, BorderLayout.CENTER);

        loadProducts(model);
    }

    private void loadProducts(DefaultTableModel model) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT product_id, product_name, price, quantity, category FROM products";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            model.setRowCount(0); // clear existing rows
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");

                model.addRow(new Object[]{productId, productName, price, quantity, category});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
