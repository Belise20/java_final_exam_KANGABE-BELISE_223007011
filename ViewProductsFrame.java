package ui.customer;

import model.CustomerProduct;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewProductsFrame extends JFrame {

    public ViewProductsFrame(List<CustomerProduct> products) {
        setTitle("All Products");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] cols = {"Product ID", "Name", "Category", "Price"};
        Object[][] data = new Object[products.size()][4];
        for (int i = 0; i < products.size(); i++) {
            CustomerProduct p = products.get(i);
            data[i][0] = p.getProductId();
            data[i][1] = p.getProductName();
            data[i][2] = p.getCategory();
            data[i][3] = p.getPrice();
        }

        JTable table = new JTable(data, cols);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
