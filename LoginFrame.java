package ui;

import model.User;
import ui.admin.AdminDashboardFrame;
import ui.customer.CustomerDashboardFrame;

import javax.swing.*;

import Service.AuthService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField usernameOrEmailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private AuthService authService;

    public LoginFrame() {
        authService = new AuthService();

        setTitle("E-Commerce Management System - Login");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setResizable(true);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.VERTICAL;

        JLabel titleLabel = new JLabel("Login to your account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        panel.add(titleLabel, gbc);

        // Username or Email
        gbc.gridwidth=1; gbc.gridx=0; gbc.gridy=1;
        panel.add(new JLabel("Username or Email:"), gbc);
        gbc.gridx=1;
        usernameOrEmailField = new JTextField(20);
        panel.add(usernameOrEmailField, gbc);

        // Password
        gbc.gridx=0; gbc.gridy=2;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx=1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Login Button
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0,120,215));
        loginButton.setForeground(Color.BLACK);
        panel.add(loginButton, gbc);

        // Register Button
        gbc.gridy=4;
        registerButton = new JButton("Register as New User");
        registerButton.setForeground(Color.WHITE);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        panel.add(registerButton, gbc);

        add(panel);

        // Actions
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void handleLogin() {
        String usernameOrEmail = usernameOrEmailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if(usernameOrEmail.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please fill all fields!","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = authService.login(usernameOrEmail,password);
        if(user!=null){
            JOptionPane.showMessageDialog(this,"Welcome "+user.getUsername());
            if(user.getRole().equalsIgnoreCase("admin")){
                new AdminDashboardFrame(user).setVisible(true);
            }else{
                new CustomerDashboardFrame(user).setVisible(true);
            }
            dispose();
        }else{
            JOptionPane.showMessageDialog(this,"Invalid username/email or password!","Login Failed",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
