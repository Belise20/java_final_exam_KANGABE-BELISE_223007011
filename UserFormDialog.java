package ui.admin;

import model.User;
import Service.AuthService;

import javax.swing.*;
import java.awt.*;

public class UserFormDialog extends JDialog {

    private JTextField usernameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private JButton saveButton;
    private JButton cancelButton;

    private AuthService authService = new AuthService();
    private User user; // null for add, non-null for edit
    private boolean saved = false;

    public UserFormDialog(JFrame parent, String title, User user) {
        super(parent, title, true);
        this.user = user;

        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        add(emailField, gbc);

        // Phone
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        add(phoneField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        roleCombo = new JComboBox<>(new String[]{"admin","customer"});
        add(roleCombo, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        btnPanel.add(saveButton);
        btnPanel.add(cancelButton);
        add(btnPanel, gbc);

        // If editing, populate fields
        if(user != null){
            usernameField.setText(user.getUsername());
            emailField.setText(user.getEmail());
            phoneField.setText(user.getPhone());
            passwordField.setText(user.getPassword());
            roleCombo.setSelectedItem(user.getRole());
        }

        // Button actions
        saveButton.addActionListener(e -> saveUser());
        cancelButton.addActionListener(e -> dispose());
    }

    private void saveUser(){
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = (String) roleCombo.getSelectedItem();

        if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Username, Email, and Password are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(user == null){ // Add new user
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setPassword(password);
            newUser.setRole(role);

            if(authService.register(newUser)){
                JOptionPane.showMessageDialog(this,"User added successfully!");
                saved = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Failed to add user!","Error",JOptionPane.ERROR_MESSAGE);
            }
        } else { // Edit existing user
            user.setUsername(username);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(password);
            user.setRole(role);

            if(authService.updateUser(user)){
                JOptionPane.showMessageDialog(this,"User updated successfully!");
                saved = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Failed to update user!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean isSaved(){
        return saved;
    }
}
