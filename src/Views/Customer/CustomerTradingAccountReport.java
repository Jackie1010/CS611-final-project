/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.Customer;

import Controllers.CustomerTradingHistoryController;
import Views.Customer.TableCellRenderers.CustomerReportCellRenderer;
import Views.Customer.TableCellRenderers.StockMarketTableCellRenderer;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author zeqi
 */
public class CustomerTradingAccountReport extends javax.swing.JFrame {
    int customerID;
    private String[] columnNames;
    private Object[][] data;

    private CustomerTradingHistoryController customerTradingHistoryController;

    /**
     * Creates new form CustomerTradingAccountReport
     */
    public CustomerTradingAccountReport(int customerId, CustomerTradingHistoryController customerTradingHistoryController) {
        this.customerID = customerId;
        this.customerTradingHistoryController = customerTradingHistoryController;
        //TODO Database update these code
        columnNames = customerTradingHistoryController.getTradingHistoryColumns();
//        columnNames = new String [] {
//                "Time Stamp", "Stock Name", "Stock ID", "At Price ($)", "Shares", "Trade Type", "Trade Cost/Profit ($)"
//        };
        data = customerTradingHistoryController.getTradingHistoryData();
//        data = new Object [][] {
//                {"2020", "Tesla", "8848", "10", "10", "Sell", "Get Realized Profit"},
//                {"2021", "Tesla", "8848", "5", "10", "Buy", "5*10 = 50"},
//                {"2022", "Apple", "1145", "20", "10", "Sell", "Get Realized Profit"},
//                {"2023", "Apple", "1145", "30", "10", "Buy", "30*10=300"}
//        };

        initComponents();

        updateContent();
    }

    private void updateContent() {

        /**
         * Try not to modify these code, or if you feel it's necessarily to do so
         */
        //Default settings for every table
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
        // Set the table model for the JTable
        TBLReport.setModel(tableModel);
        TBLReport.getTableHeader().setReorderingAllowed(false);
        /****************/
        //Special Customization for UIs

        TableColumn tradeType = TBLReport.getColumnModel().getColumn(5);
        tradeType.setCellRenderer(new CustomerReportCellRenderer());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LBLTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBLReport = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        LBLTitle.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        LBLTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBLTitle.setText("Customer Trading Account Report");

        TBLReport.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null}
                },
                new String [] {
                        "Time Stamp", "Stock Name", "Stock ID", "At Price ($)", "Shares", "Trade Type", "Trade Cost/Profit ($)"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TBLReport);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
                                        .addComponent(LBLTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(LBLTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
                                .addContainerGap())
        );

        setSize(new java.awt.Dimension(900, 728));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerTradingAccountReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerTradingAccountReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerTradingAccountReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerTradingAccountReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new CustomerTradingAccountReport(1).setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LBLTitle;
    private javax.swing.JTable TBLReport;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
