package ui.customer;

import dao.DBUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewOrdersPanel extends JPanel {

    private int customerId;
    private JTable table;
    private DefaultTableModel model;

    public ViewOrdersPanel(int customerId) {
        this.customerId = customerId;

        setLayout(new BorderLayout());
        JLabel title = new JLabel("My Orders", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // Create table
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "Order ID", "Order Date", "Product Name", "Quantity", "Total Price", "Payment Status"
        });
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadOrders();
    }

    private void loadOrders() {
        model.setRowCount(0); // clear existing rows
        String sql = """
            SELECT 
                o.order_id,
                o.order_date,
                p.product_name,
                oi.quantity,
                (oi.quantity * p.price) AS total_price,
                COALESCE(pay.status, 'Pending') AS payment_status
            FROM orders o
            JOIN order_items oi ON o.order_id = oi.order_id
            JOIN products p ON oi.product_id = p.product_id
            LEFT JOIN payments pay ON o.order_id = pay.order_id
            WHERE o.user_id = ?
            ORDER BY o.order_date DESC
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("order_id"),
                    rs.getTimestamp("order_date"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("total_price"),
                    rs.getString("payment_status")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
