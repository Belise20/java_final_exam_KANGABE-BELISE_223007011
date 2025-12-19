package ui.admin;

import model.Order;
import model.OrderItem;
import Service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewOrdersFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable orderTable;
    private DefaultTableModel tableModel;
    private OrderService orderService = new OrderService();

    public ViewOrdersFrame(){
        setTitle("View Orders");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Order ID","User ID","Order Date","Total Amount","Status"},0){
            public boolean isCellEditable(int row, int col){ return false; }
        };
        orderTable = new JTable(tableModel);
        add(new JScrollPane(orderTable), BorderLayout.CENTER);

        // Buttons panel
        JPanel btnPanel = new JPanel();
        JButton viewItemsBtn = new JButton("View Items");
        JButton updateStatusBtn = new JButton("Update Status");
        btnPanel.add(viewItemsBtn);
        btnPanel.add(updateStatusBtn);
        add(btnPanel, BorderLayout.SOUTH);

        loadOrders();

        // Actions
        viewItemsBtn.addActionListener(e -> viewItems());
        updateStatusBtn.addActionListener(e -> updateStatus());
    }

    private void loadOrders(){
        tableModel.setRowCount(0);
        List<Order> orders = orderService.getAllOrders();
        for(Order o : orders){
            tableModel.addRow(new Object[]{
                    o.getOrderId(), o.getUserId(), o.getOrderDate(), o.getTotalAmount(), o.getStatus()
            });
        }
    }

    private void viewItems(){
        int row = orderTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select an order first!");
            return;
        }
        int orderId = (int) tableModel.getValueAt(row,0);
        List<OrderItem> items = orderService.getOrderItems(orderId);

        StringBuilder sb = new StringBuilder();
        for(OrderItem i : items){
            sb.append("Product ID: ").append(i.getProductId())
              .append(", Quantity: ").append(i.getQuantity())
              .append(", Price: ").append(i.getPrice()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.length()==0?"No items found":sb.toString(),"Order Items",JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStatus(){
        int row = orderTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select an order first!");
            return;
        }
        int orderId = (int) tableModel.getValueAt(row,0);
        String currentStatus = (String) tableModel.getValueAt(row,4);

        String[] options = {"pending","processing","shipped","delivered","cancelled"};
        String newStatus = (String) JOptionPane.showInputDialog(this,
                "Select new status:", "Update Order Status",
                JOptionPane.QUESTION_MESSAGE, null, options, currentStatus);

        if(newStatus != null && !newStatus.equals(currentStatus)){
            if(orderService.updateOrderStatus(orderId, newStatus)){
                JOptionPane.showMessageDialog(this,"Order status updated!");
                loadOrders();
            } else {
                JOptionPane.showMessageDialog(this,"Failed to update status!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
