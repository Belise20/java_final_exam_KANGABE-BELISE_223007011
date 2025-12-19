package ui.customer;

import dao.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MakeOrderPanel extends JPanel {
    private JComboBox<String> productDropdown;
    private JTextField quantityField;
    private JButton orderButton;
    private int customerId;  // <--- added field

    public MakeOrderPanel(int customerId) {   // <--- constructor added
        this.customerId = customerId;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel productLabel = new JLabel("Select Product:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(productLabel, gbc);

        productDropdown = new JComboBox<>();
        loadProducts();
        gbc.gridx = 1; gbc.gridy = 0;
        add(productDropdown, gbc);

        JLabel quantityLabel = new JLabel("Quantity:");
        gbc.gridx = 0; gbc.gridy = 1;
        add(quantityLabel, gbc);

        quantityField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1;
        add(quantityField, gbc);

        orderButton = new JButton("Place Order");
        gbc.gridx = 1; gbc.gridy = 2;
        add(orderButton, gbc);

        orderButton.addActionListener(e -> placeOrder());
    }

    private void loadProducts() {
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT product_name FROM products";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productDropdown.addItem(rs.getString("product_name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }

    private void placeOrder() {
        String selectedProduct = (String) productDropdown.getSelectedItem();
        String quantityText = quantityField.getText();

        if (selectedProduct == null || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a product and enter quantity.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            String productQuery = "SELECT product_id, price, quantity FROM products WHERE product_name = ?";
            PreparedStatement ps1 = conn.prepareStatement(productQuery);
            ps1.setString(1, selectedProduct);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                int productId = rs.getInt("product_id");
                double price = rs.getDouble("price");
                int stock = rs.getInt("quantity");
                int orderQuantity = Integer.parseInt(quantityText);

                if (orderQuantity > stock) {
                    JOptionPane.showMessageDialog(this, "Not enough stock available!");
                    return;
                }

                double total = price * orderQuantity;

                String orderQuery = "INSERT INTO orders (user_id, order_date, total_amount) VALUES (?, NOW(), ?)";
                PreparedStatement ps2 = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
                ps2.setInt(1, customerId); // link to logged-in user
                ps2.setDouble(2, total);
                ps2.executeUpdate();

                ResultSet keys = ps2.getGeneratedKeys();
                int orderId = 0;
                if (keys.next()) {
                    orderId = keys.getInt(1);
                }

                String itemQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                PreparedStatement ps3 = conn.prepareStatement(itemQuery);
                ps3.setInt(1, orderId);
                ps3.setInt(2, productId);
                ps3.setInt(3, orderQuantity);
                ps3.setDouble(4, price);
                ps3.executeUpdate();

                String updateQuery = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
                PreparedStatement ps4 = conn.prepareStatement(updateQuery);
                ps4.setInt(1, orderQuantity);
                ps4.setInt(2, productId);
                ps4.executeUpdate();

                conn.commit();
                JOptionPane.showMessageDialog(this, "Order placed successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error placing order: " + e.getMessage());
        }
    }
}
