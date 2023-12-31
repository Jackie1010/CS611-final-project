/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views.Admin;

import Controllers.AdminMarketController;
import Views.StockGraphPanel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author zeqi
 */
public class AdminStockMarketMenu extends javax.swing.JFrame {

    private int row;
    private String symbol;
    private String stockName;
    private AdminMarketController adminMarketController;

//    private AdminStockMarketCard adminStockMarketCard;

    /**
     * Creates new form AdminStockMarketMenu
     */
    public AdminStockMarketMenu(int row, String symbol, String stockName, AdminMarketController adminMarketController) {
        this.row = row;
        this.symbol = symbol;
        this.stockName = stockName;
        this.adminMarketController = adminMarketController;
        adminMarketController.setAdminStockMarketMenu(this);
//        this.adminStockMarketCard = adminStockMarketCard;
        initComponents();

        //Implement listeners here, or make a new method to encapsulate these:
        // Remove all listeners, Initialize the button to empty listeners
//        for (ActionListener listener : BTNGetHistory.getActionListeners()) {
//            BTNGetHistory.removeActionListener(listener);
//        }

//        BTNGetHistory.addActionListener(addYourListenerControllerHere());

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
        LBLCPTXT = new javax.swing.JLabel();
        LBLTTXT = new javax.swing.JLabel();
        LBLATXT = new javax.swing.JLabel();
        LBLExtendiableNote = new javax.swing.JLabel();
        BTNSave = new javax.swing.JButton();
        CBBTradable = new javax.swing.JComboBox<>();
        TXTCurrentPrice = new javax.swing.JTextField();
        BTNGetHistory = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(stockName);

        LBLCPTXT.setText("Current Price: $");

        LBLTTXT.setText("Tradable:");

        LBLATXT.setText("Actions: ");

        LBLExtendiableNote.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBLExtendiableNote.setText("Extendable functions for future");

        BTNSave.setText("Save");
        BTNSave.addActionListener(adminMarketController);

        CBBTradable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Yes (T)", "No (F)"}));
        CBBTradable.addActionListener(adminMarketController);

        TXTCurrentPrice.setToolTipText("Modify the price here");


        BTNGetHistory.setText("Get Price History Graph");
        BTNGetHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNGetHistoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LBLExtendiableNote, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(LBLATXT)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(LBLCPTXT)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(TXTCurrentPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(27, 27, 27)
                                                                .addComponent(LBLTTXT)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(CBBTradable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(BTNGetHistory)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(213, 213, 213)
                                                .addComponent(BTNSave)))
                                .addContainerGap(98, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(LBLATXT)
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LBLTTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(CBBTradable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LBLCPTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(TXTCurrentPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addComponent(BTNGetHistory)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
                                .addComponent(BTNSave)
                                .addGap(17, 17, 17)
                                .addComponent(LBLExtendiableNote))
        );

        setSize(new java.awt.Dimension(500, 628));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BTNSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BTNSaveActionPerformed

    private void CBBTradableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBBTradableActionPerformed

        // TODO add your handling code here:
        String selectedItem = (String) CBBTradable.getSelectedItem();
        System.out.println("Selected item: " + selectedItem);
    }//GEN-LAST:event_CBBTradableActionPerformed

    private void BTNGetHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNGetHistoryActionPerformed
        // TODO add your handling code here:

        System.out.println("Admin get price history clicked");
        List<List<String>> stockUpdateData = adminMarketController.getStockUpdateData(symbol);
        if(stockUpdateData.size() <2 ){
                    JOptionPane.showMessageDialog(null, "Graph will only display after at least two price changes", "Warning", JOptionPane.WARNING_MESSAGE);
        }else {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Stock Price Graph");
                List<Object[]> dataPoints = new ArrayList<>();
                for (List<String> data : stockUpdateData) {
                    double price = Double.parseDouble(data.get(0));
                    Timestamp time = Timestamp.valueOf(data.get(1));
                    dataPoints.add(new Object[]{price, time});
                }
                frame.add(new StockGraphPanel(dataPoints));
                frame.setSize(800, 600);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            });
        }
    }//GEN-LAST:event_BTNGetHistoryActionPerformed
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
            java.util.logging.Logger.getLogger(AdminStockMarketMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminStockMarketMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminStockMarketMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminStockMarketMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new AdminStockMarketMenu().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNGetHistory;
    private javax.swing.JButton BTNSave;
    private javax.swing.JComboBox<String> CBBTradable;
    private javax.swing.JLabel LBLATXT;
    private javax.swing.JLabel LBLCPTXT;
    private javax.swing.JLabel LBLExtendiableNote;
    private javax.swing.JLabel LBLTTXT;
    private javax.swing.JTextField TXTCurrentPrice;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    /**
     * Getter from BTNSave
     */
    public javax.swing.JButton getBTNSave() {
        return BTNSave;
    }

    /**
     * Getter for CBBTradable
     */
    public javax.swing.JComboBox<String> getCBBTradable() {
        return CBBTradable;
    }

    /**
     * Getter for TXTCurrentPrice
     */
    public javax.swing.JTextField getTXTCurrentPrice() {
        return TXTCurrentPrice;
    }

    /**
     * Getter for symbol
     */
    public String getSymbol() {
        return symbol;
    }

}
