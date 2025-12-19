package ui.admin;

import model.User;
import Service.AuthService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageUsersFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private AuthService authService = new AuthService();

    public ManageUsersFrame() {
        setTitle("Admin - Manage Users");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");
        topPanel.add(addBtn); topPanel.add(editBtn); topPanel.add(deleteBtn); topPanel.add(refreshBtn);
        add(topPanel,BorderLayout.NORTH);

        String[] cols = {"ID","Username","Email","Phone","Role"};
        tableModel = new DefaultTableModel(cols,0);
        table = new JTable(tableModel);
        add(new JScrollPane(table),BorderLayout.CENTER);

        loadUsers();

        refreshBtn.addActionListener(e -> loadUsers());
        deleteBtn.addActionListener(e -> deleteUser());
        addBtn.addActionListener(e -> addUser());
        editBtn.addActionListener(e -> editUser());
    }

    private void loadUsers(){
        tableModel.setRowCount(0);
        List<User> users = authService.getAllUsers();
        for(User u: users){
            tableModel.addRow(new Object[]{u.getUserId(),u.getUsername(),u.getEmail(),u.getPhone(),u.getRole()});
        }
    }

    private void deleteUser(){
        int row = table.getSelectedRow();
        if(row==-1){ JOptionPane.showMessageDialog(this,"Select a user!"); return; }
        int id = (int)tableModel.getValueAt(row,0);
        if(authService.deleteUser(id)){
            JOptionPane.showMessageDialog(this,"Deleted!");
            loadUsers();
        }else JOptionPane.showMessageDialog(this,"Failed to delete!");
    }

    private void addUser(){
        UserFormDialog dialog = new UserFormDialog(this,"Add User",null);
        dialog.setVisible(true);
        if(dialog.isSaved()) loadUsers();
    }

    private void editUser(){
        int row = table.getSelectedRow();
        if(row==-1){ JOptionPane.showMessageDialog(this,"Select a user!"); return; }
        int id = (int)tableModel.getValueAt(row,0);
        User u = authService.getAllUsers().stream().filter(x->x.getUserId()==id).findFirst().orElse(null);
        if(u!=null){
            UserFormDialog dialog = new UserFormDialog(this,"Edit User",u);
            dialog.setVisible(true);
            if(dialog.isSaved()) loadUsers();
        }
    }
}
