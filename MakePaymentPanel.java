package ui.customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import dao.DBUtil;

public class MakePaymentPanel extends JPanel {
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> paymentMethodBox;
    private JButton payButton;
    private int userId;

    public MakePaymentPanel(int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Table to display user's orders
        tableModel = new DefaultTableModel(new Object[]{"Order ID", "Total Amount", "Status"}, 0);
        ordersTable = new JTable(tableModel);
        add(new JScrollPane(ordersTable), BorderLayout.CENTER);

        // Bottom panel with payment method and button
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(new JLabel("Payment Method:"));

        paymentMethodBox = new JComboBox<>(new String[]{"cash", "credit card", "mobile money"});
        bottomPanel.add(paymentMethodBox);

        payButton = new JButton("Make Payment");
        bottomPanel.add(payButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load pending orders for the user
        loadOrders();

        // Handle payment button click
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makePayment();
            }
        });
    }

    private void loadOrders() {
        tableModel.setRowCount(0);
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT order_id, total_amount, status FROM orders WHERE user_id = ? AND status = 'pending'")) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("status");
                tableModel.addRow(new Object[]{orderId, totalAmount, status});
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void makePayment() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to pay for.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int orderId = (int) tableModel.getValueAt(selectedRow, 0);
        double amount = (double) tableModel.getValueAt(selectedRow, 1);
        String method = (String) paymentMethodBox.getSelectedItem();

        try (Connection conn = DBUtil.getConnection()) {
            // Insert payment record
            String insertPayment = "INSERT INTO payments (order_id, amount, payment_method, status) VALUES (?, ?, ?, 'paid')";
            PreparedStatement paymentStmt = conn.prepareStatement(insertPayment);
            paymentStmt.setInt(1, orderId);
            paymentStmt.setDouble(2, amount);
            paymentStmt.setString(3, method);
            paymentStmt.executeUpdate();

            // Update order status
            String updateOrder = "UPDATE orders SET status = 'processing' WHERE order_id = ?";
            PreparedStatement orderStmt = conn.prepareStatement(updateOrder);
            orderStmt.setInt(1, orderId);
            orderStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Payment successful for Order ID: " + orderId);
            loadOrders(); // Refresh the orders list

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error processing payment: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
