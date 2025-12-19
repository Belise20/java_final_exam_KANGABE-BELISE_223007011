package ui.customer;

import model.CustomerProduct;
import model.User;
import Service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MakeOrderFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User loggedInUser;
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private OrderService orderService = new OrderService();

    public MakeOrderFrame(User user) {
        this.loggedInUser = user;

        setTitle("Make Order - E-Commerce Management System");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- Top Panel ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton placeOrderBtn = new JButton("Place Order");
        topPanel.add(placeOrderBtn);
        add(topPanel, BorderLayout.NORTH);

        // --- Table for Products ---
        String[] columns = {"ID", "Name", "Category", "Price", "Available Quantity", "Order Quantity"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the last column (Order Quantity) is editable
                return column == 5;
            }
        };
        productsTable = new JTable(tableModel);
        add(new JScrollPane(productsTable), BorderLayout.CENTER);

        // --- Load Products ---
        loadProducts();

        // --- Place Order Action ---
        placeOrderBtn.addActionListener(e -> placeOrders());
    }

    private void loadProducts() {
        tableModel.setRowCount(0); // Clear table
        List<CustomerProduct> products = orderService.getAllProducts();

        if (products != null) {
            for (CustomerProduct p : products) {
                tableModel.addRow(new Object[]{
                        p.getProductId(),
                        p.getProductName(),
                        p.getCategory(),
                        p.getPrice(),
                        p.getQuantity(),
                        0 // default order quantity
                });
            }
        }
    }

    private void placeOrders() {
        boolean anyOrder = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int orderQty = 0;
            try {
                orderQty = Integer.parseInt(tableModel.getValueAt(i, 5).toString());
            } catch (NumberFormatException ex) {
                // skip invalid input
            }
            if (orderQty > 0) {
                int productId = (int) tableModel.getValueAt(i, 0);
                orderService.placeOrder(loggedInUser, productId, orderQty);
                anyOrder = true;
            }
        }

        if (anyOrder) {
            JOptionPane.showMessageDialog(this, "Order placed successfully!");
            loadProducts(); // refresh table
        } else {
            JOptionPane.showMessageDialog(this, "No order quantity selected.");
        }
    }

    // --- Optional main for testing ---
    public static void main(String[] args) {
        User testUser = new User();
        testUser.setUsername("Belise");
        SwingUtilities.invokeLater(() -> new MakeOrderFrame(testUser).setVisible(true));
    }
}
