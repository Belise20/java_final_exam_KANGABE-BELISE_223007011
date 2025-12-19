package ui.admin;

import Service.ReportService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class ReportsFrame extends JFrame {

    private JLabel totalRevenueLabel;
    private JLabel totalOrdersLabel;
    private JTable topProductsTable;
    private DefaultTableModel tableModel;
    private ReportService reportService = new ReportService();

    public ReportsFrame(){
        setTitle("Sales Reports");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel for summary
        JPanel topPanel = new JPanel(new GridLayout(1,2,20,20));
        totalRevenueLabel = new JLabel();
        totalRevenueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalOrdersLabel = new JLabel();
        totalOrdersLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        topPanel.add(totalRevenueLabel);
        topPanel.add(totalOrdersLabel);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(topPanel, BorderLayout.NORTH);

        // Table for top products
        tableModel = new DefaultTableModel(new String[]{"Product Name","Quantity Sold"},0){
            public boolean isCellEditable(int row, int column){ return false; }
        };
        topProductsTable = new JTable(tableModel);
        add(new JScrollPane(topProductsTable), BorderLayout.CENTER);

        loadReports();
    }

    private void loadReports(){
        double totalRevenue = reportService.getTotalRevenue();
        int totalOrders = reportService.getTotalOrders();
        Map<String,Integer> topProducts = reportService.getTopProducts(10);

        totalRevenueLabel.setText("Total Revenue: $" + totalRevenue);
        totalOrdersLabel.setText("Total Orders: " + totalOrders);

        tableModel.setRowCount(0);
        for(Map.Entry<String,Integer> entry : topProducts.entrySet()){
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }
}
