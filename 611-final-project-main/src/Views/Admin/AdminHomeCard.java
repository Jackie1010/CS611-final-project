/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views.Admin;

import Controllers.AdminHomeController;
import Controllers.AllUserTradingHistoryController;
import Controllers.CustomerTradingHistoryController;
import Views.Admin.TableCellRenderers.CurrentCustomerTableCellRenderer;
import Views.Admin.TableCellRenderers.NewCustomerTableCellRenderer;
import Views.Customer.CustomerTradingAccountReport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zeqi
 */
public class AdminHomeCard extends javax.swing.JPanel {


    private AdminHomeController adminHomeController;
    private CustomerTradingHistoryController customerTradingHistoryController;
    private String[] columnNamesTableNewCustomers;
    private Object[][] dataTableNewCustomers;

    private String[] columnNamesTableCurrentCustomers;
    private Object[][] dataTableCurrentCustomers;
    private AllUserTradingHistoryController allUserTradingHistoryController;

    /**
     * Creates new form AdminHomeCard
     */
    public AdminHomeCard(AdminHomeController adminHomeController, CustomerTradingHistoryController customerTradingHistoryController, AllUserTradingHistoryController allUserTradingHistoryController) {
        this.allUserTradingHistoryController = allUserTradingHistoryController;
        this.adminHomeController = adminHomeController;
        adminHomeController.setAdminHomeCard(this);
        this.customerTradingHistoryController = customerTradingHistoryController;
        initComponents();
        //TODO Database update these code
        //First Table
        columnNamesTableNewCustomers = adminHomeController.getColumnNamesTableNewCustomers();
        dataTableNewCustomers = adminHomeController.getDataTableNewCustomers();

        //Second Table
        columnNamesTableCurrentCustomers = adminHomeController.getColumnNamesTableCurrentCustomers();
        dataTableCurrentCustomers = adminHomeController.getDataTableCurrentCustomers();

        updateContent();

    }

    private void updateContent() {
        /**
         * Try not to modify these code, or if you feel it's necessarily to do so
         */
        //Default settings for every table
        DefaultTableModel tableModel = new DefaultTableModel(dataTableNewCustomers, columnNamesTableNewCustomers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
        // Set the table model for the JTable
        TBLNewCustomers.setModel(tableModel);
        TBLNewCustomers.getTableHeader().setReorderingAllowed(false);

        tableModel = new DefaultTableModel(dataTableCurrentCustomers, columnNamesTableCurrentCustomers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
        // Set the table model for the JTable
        TBLCurrentCustomers.setModel(tableModel);
        TBLCurrentCustomers.getTableHeader().setReorderingAllowed(false);
        /****************/

        /**
         * Defining New Customer Tables
         */
        //Special Customization for UIs
        TableColumn toApprove = TBLNewCustomers.getColumnModel().getColumn(2);
        toApprove.setCellRenderer(new NewCustomerTableCellRenderer());

        TableColumn toDeny = TBLNewCustomers.getColumnModel().getColumn(3);
        toDeny.setCellRenderer(new NewCustomerTableCellRenderer());

        // Initialize mouse listeners in the new customer table
        MouseListener[] mouseListeners = TBLNewCustomers.getMouseListeners();
        for (MouseListener mouseListener : mouseListeners) {
            TBLNewCustomers.removeMouseListener(mouseListener);
        }
        //Adding mouse pressed listeners to the specific areas of the table
        TBLNewCustomers.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int column = TBLNewCustomers.columnAtPoint(e.getPoint());
                int row = TBLNewCustomers.rowAtPoint(e.getPoint());

                if (row >= 0 && row < TBLNewCustomers.getRowCount()) {
                    // Unselect all rows except the clicked one
                    TBLNewCustomers.clearSelection();
                    TBLNewCustomers.addRowSelectionInterval(row, row);
//                    int customerID = Integer.parseInt(TBLCurrentCustomers.getValueAt(row, 0).toString());
                    if (column == 2) {
                        //implement Buy functionalities in this method
                        //TODO update database in the following method
                        adminHomeController.toApprove(row, TBLNewCustomers);
                    } else if (column == 3) {
                        //implement Buy functionalities in this method
                        //TODO update database in the following method
                        adminHomeController.toDeny(row, TBLNewCustomers);
                    }
                }
            }
        });

        /**
         * Defining Current Customer Tables
         */
        //Special Customization for UIs
        TableColumn accountReport = TBLCurrentCustomers.getColumnModel().getColumn(5);
        accountReport.setCellRenderer(new CurrentCustomerTableCellRenderer());

        TableColumn sendNote = TBLCurrentCustomers.getColumnModel().getColumn(6);
        sendNote.setCellRenderer(new CurrentCustomerTableCellRenderer());

        // Initialize mouse listeners in the new customer table
        mouseListeners = TBLCurrentCustomers.getMouseListeners();
        for (MouseListener mouseListener : mouseListeners) {
            TBLCurrentCustomers.removeMouseListener(mouseListener);
        }
        //Adding mouse pressed listeners to the specific areas of the table
        TBLCurrentCustomers.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int column = TBLCurrentCustomers.columnAtPoint(e.getPoint());
                int row = TBLCurrentCustomers.rowAtPoint(e.getPoint());

                if (row >= 0 && row < TBLCurrentCustomers.getRowCount()) {
                    // Unselect all rows except the clicked one
                    TBLCurrentCustomers.clearSelection();
                    TBLCurrentCustomers.addRowSelectionInterval(row, row);
                    String customerID = TBLCurrentCustomers.getValueAt(row, 0).toString();
                    String customerName = TBLCurrentCustomers.getValueAt(row, 1).toString();
                    if (column == 5) {
                        //implement Buy functionalities in this method
                        //TODO update database in the following method
                        int customer_id = Integer.parseInt(TBLCurrentCustomers.getValueAt(row, 0).toString());
                        adminHomeController.getReport(customer_id, customerTradingHistoryController);
                    } else if (column == 6) {
                        //implement Buy functionalities in this method
                        //TODO update database in the following method
                        int customerId = Integer.parseInt(TBLCurrentCustomers.getValueAt(row, 0).toString());
                        sendNotificationIndividually(customerId);
                    }else if (column == 1) {
                        showCurrentCustomerMenu(customerID, customerName, customerTradingHistoryController);

                    }
                }
            }
        });
    }

    private void showCurrentCustomerMenu(String customerID, String customerName, CustomerTradingHistoryController customerTradingHistoryController){
        System.out.println("Menu for current customer");
        // convert customerID to int
        int customerId = Integer.parseInt(customerID);
        new CurrentCustomerMenu(adminHomeController, customerId, customerName, customerTradingHistoryController).setVisible(true);
    }

    private void sendNotificationIndividually(int customerId) {
        //TODO: Update Database
        String message = JOptionPane.showInputDialog(this, "Enter your message:");
        if (message != null) {
            // user clicked "Send"
            System.out.println("Message: " + message);
            adminHomeController.sendNotification(customerId, message);
        } else {
            // user clicked "Cancel" or closed the dialog box
            System.out.println("Dialog closed without sending a message.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LBLTitleNewCustomers = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBLNewCustomers = new javax.swing.JTable();
        LBLTitleCurrentCustomers = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TBLCurrentCustomers = new javax.swing.JTable();
        BTNNotifyAll = new javax.swing.JButton();
        BTNGetAllReport = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));

        LBLTitleNewCustomers.setBackground(new java.awt.Color(204, 204, 204));
        LBLTitleNewCustomers.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 17)); // NOI18N
        LBLTitleNewCustomers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBLTitleNewCustomers.setText("New Customers Trading Account Application Requests");
        LBLTitleNewCustomers.setOpaque(true);

        TBLNewCustomers.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] {
                        "Customer ID", "Customer Name", "To Approve", "To Deny"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TBLNewCustomers);

        LBLTitleCurrentCustomers.setBackground(new java.awt.Color(204, 204, 204));
        LBLTitleCurrentCustomers.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 17)); // NOI18N
        LBLTitleCurrentCustomers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBLTitleCurrentCustomers.setText("                                                   Current Customer Trading Accounts");
        LBLTitleCurrentCustomers.setOpaque(true);

        TBLCurrentCustomers.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null}
                },
                new String [] {
                        "Customer ID", "Customer Name", "Trading Account ID", "Realized Profit", "Unrealized Profit", "Account Report", "Send Notification"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TBLCurrentCustomers);

        BTNNotifyAll.setText("Notify Options Account");
        BTNNotifyAll.addActionListener(adminHomeController);

        BTNGetAllReport.setText("Get All");
        BTNGetAllReport.addActionListener(adminHomeController);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(LBLTitleNewCustomers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(LBLTitleCurrentCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BTNGetAllReport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BTNNotifyAll)
                                .addGap(24, 24, 24))
                        .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(LBLTitleNewCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LBLTitleCurrentCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(BTNNotifyAll)
                                        .addComponent(BTNGetAllReport))
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BTNNotifyAllActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("notify all clicked");
        //TODO: Update database
        List<String> customers = new ArrayList<>();
        for (int i = 0; i < TBLCurrentCustomers.getRowCount(); i++) {
            int profit = Integer.parseInt(TBLCurrentCustomers.getValueAt(i, 3).toString());
            if (profit >= 10000) {
                int customerId = Integer.parseInt(TBLCurrentCustomers.getValueAt(i, 0).toString());
                customers.add("Customer ID: " + customerId);
            }
        }

        if (customers.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(customers.size()).append(" customers have been sent a notification to open a derivative account:\n");
            for (String customer : customers) {
                sb.append(customer).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNGetAllReport;
    private javax.swing.JButton BTNNotifyAll;
    private javax.swing.JLabel LBLTitleCurrentCustomers;
    private javax.swing.JLabel LBLTitleNewCustomers;
    private javax.swing.JTable TBLCurrentCustomers;
    private javax.swing.JTable TBLNewCustomers;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    /**
     * Getter for BTNGetAllReport
     */
    public javax.swing.JButton getBTNGetAllReport() {
        return BTNGetAllReport;
    }

    /**
     * Getter for BTNNotifyAll
     */
    public javax.swing.JButton getBTNNotifyAll() {
        return BTNNotifyAll;
    }
}
