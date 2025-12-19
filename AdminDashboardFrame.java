package ui.admin;

import model.User;
import ui.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User admin;

    public AdminDashboardFrame(User admin) {
        this.admin = admin;

        setTitle("Admin Dashboard - " + admin.getUsername());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        add(panel);

        // Top label
        JLabel welcomeLabel = new JLabel("Welcome, " + admin.getUsername(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        panel.add(buttonsPanel, BorderLayout.CENTER);

        JButton manageUsersBtn = new JButton("Manage Users");
        JButton manageProductsBtn = new JButton("Manage Products");
        JButton viewOrdersBtn = new JButton("View Orders");
        JButton viewPaymentsBtn = new JButton("View Payments");
        JButton reportsBtn = new JButton("Reports");
        JButton logoutBtn = new JButton("Logout");

        // Add buttons to panel
        buttonsPanel.add(manageUsersBtn);
        buttonsPanel.add(manageProductsBtn);
        buttonsPanel.add(viewOrdersBtn);
        buttonsPanel.add(viewPaymentsBtn);
        buttonsPanel.add(reportsBtn);
        buttonsPanel.add(logoutBtn);

        // Button actions
        manageUsersBtn.addActionListener(e -> new ManageUsersFrame().setVisible(true));
        manageProductsBtn.addActionListener(e -> new ManageProductsFrame().setVisible(true));
        viewOrdersBtn.addActionListener(e -> new ViewOrdersFrame().setVisible(true));
        viewPaymentsBtn.addActionListener(e -> new ViewPaymentFrame().setVisible(true));
        reportsBtn.addActionListener(e -> new ReportsFrame().setVisible(true));

        // Logout action
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Dispose this frame
                dispose();
                // Show login frame
                new LoginFrame().setVisible(true);
            }
        });
    }
}
