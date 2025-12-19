package ui.admin;

import model.Product;
import Service.ProductService;

import javax.swing.*;
import java.awt.*;

public class ProductFormDialog extends JDialog {

    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField categoryField;
    private JButton saveButton, cancelButton;

    private ProductService productService = new ProductService();
    private Product product; // null for add, non-null for edit
    private boolean saved = false;

    public ProductFormDialog(JFrame parent, String title, Product product){
        super(parent, title, true);
        this.product = product;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx=0; gbc.gridy=0;
        add(new JLabel("Product Name:"), gbc);
        gbc.gridx=1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Price
        gbc.gridx=0; gbc.gridy=1;
        add(new JLabel("Price:"), gbc);
        gbc.gridx=1;
        priceField = new JTextField(20);
        add(priceField, gbc);

        // Quantity
        gbc.gridx=0; gbc.gridy=2;
        add(new JLabel("Quantity:"), gbc);
        gbc.gridx=1;
        quantityField = new JTextField(20);
        add(quantityField, gbc);

        // Category
        gbc.gridx=0; gbc.gridy=3;
        add(new JLabel("Category:"), gbc);
        gbc.gridx=1;
        categoryField = new JTextField(20);
        add(categoryField, gbc);

        // Buttons
        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2;
        JPanel btnPanel = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        btnPanel.add(saveButton);
        btnPanel.add(cancelButton);
        add(btnPanel, gbc);

        // Populate fields if editing
        if(product != null){
            nameField.setText(product.getProductName());
            priceField.setText(String.valueOf(product.getPrice()));
            quantityField.setText(String.valueOf(product.getQuantity()));
            categoryField.setText(product.getCategory());
        }

        // Actions
        saveButton.addActionListener(e -> saveProduct());
        cancelButton.addActionListener(e -> dispose());
    }

    private void saveProduct(){
        String name = nameField.getText().trim();
        String priceStr = priceField.getText().trim();
        String quantityStr = quantityField.getText().trim();
        String category = categoryField.getText().trim();

        if(name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()){
            JOptionPane.showMessageDialog(this,"Name, Price and Quantity are required!","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        int quantity;
        try {
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Invalid number format!","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(product == null){ // Add
            Product p = new Product();
            p.setProductName(name);
            p.setPrice(price);
            p.setQuantity(quantity);
            p.setCategory(category);

            if(productService.addProduct(p)){
                JOptionPane.showMessageDialog(this,"Product added successfully!");
                saved = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Failed to add product!","Error",JOptionPane.ERROR_MESSAGE);
            }
        } else { // Edit
            product.setProductName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setCategory(category);

            if(productService.updateProduct(product)){
                JOptionPane.showMessageDialog(this,"Product updated successfully!");
                saved = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Failed to update product!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean isSaved(){ return saved; }
}
