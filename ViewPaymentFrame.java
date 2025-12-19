package ui.admin;

import model.Payment;
import Service.PaymentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewPaymentFrame extends JFrame {

    private JTable paymentTable;
    private DefaultTableModel tableModel;
    private PaymentService paymentService = new PaymentService();

    public ViewPaymentFrame(){
        setTitle("View Payments");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table
        tableModel = new DefaultTableModel(
                new String[]{"Payment ID","Order ID","Date","Amount","Method","Status"},0){
            public boolean isCellEditable(int row, int col){ return false; }
        };
        paymentTable = new JTable(tableModel);
        add(new JScrollPane(paymentTable), BorderLayout.CENTER);

        loadPayments();
    }

    private void loadPayments(){
        tableModel.setRowCount(0);
        List<Payment> payments = paymentService.getAllPayments();
        for(Payment p : payments){
            tableModel.addRow(new Object[]{
                    p.getPaymentId(),
                    p.getOrderId(),
                    p.getPaymentDate(),
                    p.getAmount(),
                    p.getPaymentMethod(),
                    p.getStatus()
            });
        }
    }
}
