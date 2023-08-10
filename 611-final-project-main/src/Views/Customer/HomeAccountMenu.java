/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.Customer;

import Controllers.CustomerHomeController;

/**
 *
 * @author zeqi
 */
public class HomeAccountMenu extends javax.swing.JFrame {

    private CustomerHomeController customerHomeController;
    private int customerID;
    private String name;

    /**
     * Creates new form StockMarketActionMenu
     */
    public HomeAccountMenu(CustomerHomeController customerHomeController, int customerID, String name) {
        this.customerHomeController = customerHomeController;
        customerHomeController.setHomeAccountMenu(this);
        this.customerID = customerID;
        this.name = name;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        LBLUPTXT = new javax.swing.JLabel();
        LBLUnrealizedProfit = new javax.swing.JLabel();
        LBLAIDTXT = new javax.swing.JLabel();
        LBLAccountID = new javax.swing.JLabel();
        LBLRPTXT = new javax.swing.JLabel();
        LBLRealizedProfit = new javax.swing.JLabel();
        LBLTCTXT = new javax.swing.JLabel();
        LBLTotalCost = new javax.swing.JLabel();
        LBLATXT = new javax.swing.JLabel();
        BTNGetReport = new javax.swing.JButton();
        LBLExtendiableNote = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(name);

        LBLUPTXT.setText("Unrealized Profit: $");

        LBLUnrealizedProfit.setText(String.format("%.2f", customerHomeController.getUnRealizedProfitByCustomerID(customerID)));

        LBLAIDTXT.setText("Account ID:");

        LBLAccountID.setText(String.valueOf(customerID));

        LBLRPTXT.setText("Realized Profit$");

        LBLRealizedProfit.setText(String.format("%.2f", customerHomeController.getRealizedProfitByCustomerID(customerID)));

        LBLTCTXT.setText("Total Cost:");

        LBLTotalCost.setText(String.format("%.2f", customerHomeController.getTotalInvested(customerID)));

        LBLATXT.setText("Actions: ");

        BTNGetReport.setForeground(new java.awt.Color(0, 102, 255));
        BTNGetReport.setText("Get Transaction History Report");
        BTNGetReport.addActionListener(customerHomeController);

        LBLExtendiableNote.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBLExtendiableNote.setText("Extendable functions for future");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LBLExtendiableNote, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(LBLUPTXT)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(LBLUnrealizedProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(LBLRPTXT)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(LBLRealizedProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(LBLTCTXT)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(LBLTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(LBLAIDTXT)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(LBLAccountID, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(LBLATXT)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(BTNGetReport)))
                                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(LBLUnrealizedProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LBLAccountID, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LBLAIDTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LBLUPTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(LBLRealizedProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LBLTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LBLTCTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LBLRPTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LBLATXT)
                                        .addComponent(BTNGetReport))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                                .addComponent(LBLExtendiableNote)
                                .addGap(141, 141, 141))
        );

        setSize(new java.awt.Dimension(500, 628));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BTNGetReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNGetReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BTNGetReportActionPerformed

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
            java.util.logging.Logger.getLogger(HomeAccountMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeAccountMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeAccountMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeAccountMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new HomeAccountMenu().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNGetReport;
    private javax.swing.JLabel LBLAIDTXT;
    private javax.swing.JLabel LBLATXT;
    private javax.swing.JLabel LBLAccountID;
    private javax.swing.JLabel LBLExtendiableNote;
    private javax.swing.JLabel LBLRPTXT;
    private javax.swing.JLabel LBLRealizedProfit;
    private javax.swing.JLabel LBLTCTXT;
    private javax.swing.JLabel LBLTotalCost;
    private javax.swing.JLabel LBLUPTXT;
    private javax.swing.JLabel LBLUnrealizedProfit;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    /**
     * Getter for the BTNGetReport
     */
    public javax.swing.JButton getBTNGetReport() {
        return BTNGetReport;
    }
}
