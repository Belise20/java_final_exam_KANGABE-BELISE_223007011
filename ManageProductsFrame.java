package ui.admin;

import model.Product;
import Service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageProductsFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private ProductService productService = new ProductService();

    public ManageProductsFrame(){
        setTitle("Manage Products");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID","Name","Price","Quantity","Category"},0){
            public boolean isCellEditable(int row, int column){ return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add Product");
        JButton editBtn = new JButton("Edit Product");
        JButton deleteBtn = new JButton("Delete Product");

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        add(btnPanel, BorderLayout.SOUTH);

        // Load data
        loadProducts();

        // Actions
        addBtn.addActionListener(e -> addProduct());
        editBtn.addActionListener(e -> editProduct());
        deleteBtn.addActionListener(e -> deleteProduct());
    }

    private void loadProducts(){
        tableModel.setRowCount(0);
        List<Product> products = productService.getAllProducts();
        for(Product p : products){
            tableModel.addRow(new Object[]{p.getProductId(), p.getProductName(), p.getPrice(), p.getQuantity(), p.getCategory()});
        }
    }

    private void addProduct(){
        ProductFormDialog dialog = new ProductFormDialog(this,"Add Product", null);
        dialog.setVisible(true);
        if(dialog.isSaved()) loadProducts();
    }

    private void editProduct(){
        int row = table.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"Please select a product to edit!");
            return;
        }
        int id = (int) tableModel.getValueAt(row,0);
        Product p = productService.getAllProducts().stream().filter(prod -> prod.getProductId() == id).findFirst().orElse(null);
        if(p == null) return;

        ProductFormDialog dialog = new ProductFormDialog(this,"Edit Product", p);
        dialog.setVisible(true);
        if(dialog.isSaved()) loadProducts();
    }

    private void deleteProduct(){
        int row = table.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"Please select a product to delete!");
            return;
        }
        int id = (int) tableModel.getValueAt(row,0);
        int confirm = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this product?","Confirm",JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            if(productService.deleteProduct(id)){
                JOptionPane.showMessageDialog(this,"Product deleted successfully!");
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this,"Failed to delete product!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
