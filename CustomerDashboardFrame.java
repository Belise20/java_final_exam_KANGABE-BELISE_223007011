package ui.customer;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDashboardFrame extends JFrame {

    private User customer;
    private JPanel mainPanel;

    public CustomerDashboardFrame(User customer) {
        this.customer = customer;
        setTitle("Customer Dashboard - " + customer.getUsername());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Left navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(5, 1, 5, 5));
        JButton btnMakeOrder = new JButton("Make Order");
        JButton btnViewCart = new JButton("View Cart");
        JButton btnMakePayment = new JButton("Make Payment");
        JButton btnViewOrders = new JButton("View Orders");
        JButton btnLogout = new JButton("Logout");

        navPanel.add(btnMakeOrder);
        navPanel.add(btnViewCart);
        navPanel.add(btnMakePayment);
        navPanel.add(btnViewOrders);
        navPanel.add(btnLogout);

        mainPanel.add(navPanel, BorderLayout.WEST);

        // Panel to display selected feature
        JPanel displayPanel = new JPanel(new BorderLayout());
        mainPanel.add(displayPanel, BorderLayout.CENTER);

        // Button actions
        btnMakeOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.removeAll();
                displayPanel.add(new MakeOrderPanel(customer.getUserId()), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
            }
        });

        btnViewCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.removeAll();
                displayPanel.add(new ViewCartPanel(customer.getUserId()), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
            }
        });

        btnMakePayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.removeAll();
                displayPanel.add(new MakePaymentPanel(customer.getUserId()), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
            }
        });

        btnViewOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.removeAll();
                displayPanel.add(new ViewOrdersPanel(customer.getUserId()), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // close dashboard
                new ui.LoginFrame().setVisible(true); // return to login
            }
        });

        setVisible(true);
    }

    // Optional main method for testing
    public static void main(String[] args) {
        // Dummy user for testing
        User testUser = new User();
        testUser.setUserId(1);
        testUser.setUsername("customer1");
        new CustomerDashboardFrame(testUser);
    }
}
