package ui.customer;

import model.User;

import javax.swing.*;
import java.awt.*;

public class MakePaymentFrame extends JFrame {

    private User loggedInUser;
    private JTextField amountField;
    private JComboBox<String> paymentMethodBox;
    private JTextField referenceField;

    public MakePaymentFrame(User user) {
        this.loggedInUser = user;

        setTitle("Make Payment - E-Commerce Management System");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Title Label ---
        JLabel titleLabel = new JLabel("💳 Make a Payment");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // --- User Info ---
        JLabel userLabel = new JLabel("Customer: " + loggedInUser.getUsername());
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // --- Payment Method ---
        JLabel methodLabel = new JLabel("Select Payment Method:");
        paymentMethodBox = new JComboBox<>(new String[]{"Mobile Money", "Credit Card", "Bank Transfer"});
        paymentMethodBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // --- Amount Field ---
        JLabel amountLabel = new JLabel("Enter Amount (RWF):");
        amountField = new JTextField();
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // --- Reference Field ---
        JLabel referenceLabel = new JLabel("Transaction Reference / Phone:");
        referenceField = new JTextField();
        referenceField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // --- Buttons ---
        JButton payBtn = new JButton("Confirm Payment");
        JButton backBtn = new JButton("Back to Dashboard");

        JButton[] buttons = {payBtn, backBtn};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(0, 102, 204));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
        }

        // --- Layout ---
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(userLabel);
        formPanel.add(new JLabel(""));
        formPanel.add(methodLabel);
        formPanel.add(paymentMethodBox);
        formPanel.add(amountLabel);
        formPanel.add(amountField);
        formPanel.add(referenceLabel);
        formPanel.add(referenceField);
        formPanel.add(new JLabel("")); // filler
        formPanel.add(new JLabel("")); // filler
        formPanel.add(payBtn);
        formPanel.add(backBtn);

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // --- ACTIONS ---
        payBtn.addActionListener(e -> processPayment());
        backBtn.addActionListener(e -> {
            new CustomerDashboardFrame(loggedInUser).setVisible(true);
            dispose();
        });
    }

    private void processPayment() {
        String method = (String) paymentMethodBox.getSelectedItem();
        String amountText = amountField.getText().trim();
        String reference = referenceField.getText().trim();

        if (amountText.isEmpty() || reference.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all payment details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- Simulate Payment Success ---
            JOptionPane.showMessageDialog(this,
                    "Payment successful!\n\n" +
                    "Method: " + method +
                    "\nAmount: RWF " + amount +
                    "\nReference: " + reference,
                    "Payment Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);

            // Redirect to dashboard
            new CustomerDashboardFrame(loggedInUser).setVisible(true);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Amount must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Optional: testing main ---
    public static void main(String[] args) {
        User testUser = new User();
        testUser.setUsername("Belise");
        new MakePaymentFrame(testUser).setVisible(true);
    }
}
