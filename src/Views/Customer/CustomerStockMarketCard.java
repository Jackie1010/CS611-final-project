/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views.Customer;

import Controllers.CustomerStockMarketActionMenuController;
import Controllers.CustomerStockMarketController;
import Views.Customer.TableCellRenderers.StockMarketTableCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author zeqi
 */
public class CustomerStockMarketCard extends javax.swing.JPanel {
    private int customerID;
    private String[] columnNames;
    private Object[][] data;
    private CustomerStockMarketController customerStockMarketController;
    private CustomerStockMarketActionMenuController customerStockMarketActionMenuController;


    /**
     * Creates new form CustomerStockMarketCard
     */
    public CustomerStockMarketCard(int CustomerID, CustomerStockMarketController customerStockMarketController, CustomerStockMarketActionMenuController customerStockMarketActionMenuController) {
        this.customerStockMarketActionMenuController = customerStockMarketActionMenuController;
        customerStockMarketActionMenuController.setCustomerId(CustomerID);
        this.customerStockMarketController = customerStockMarketController;
        customerStockMarketController.setCustomerStockMarketCard(this);
        this.customerID = CustomerID;
        initComponents();

        customerStockMarketController.setData();
        System.out.println("Data fetched from DB");
        LBLCashAvailable.setText(String.valueOf(customerStockMarketController.getCashAvailable(customerID)));
        updateContent();
    }

    private void updateContent() {
        // re-call the constructor to update the data
        /**
         * Try not to modify these code, or if you feel it's necessarily to do so
         */
        //Default settings for every table
        this.columnNames = customerStockMarketController.getStockMarketColumnNames();
        data = customerStockMarketController.getCustomerMarketData();
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
        // Set the table model for the JTable
        TBLStockMarket.setModel(tableModel);
        TBLStockMarket.getTableHeader().setReorderingAllowed(false);
        /****************/
        // Initialize mouse listeners in the table
        MouseListener[] mouseListeners = TBLStockMarket.getMouseListeners();
        for (MouseListener mouseListener : mouseListeners) {
            TBLStockMarket.removeMouseListener(mouseListener);
        }
        //Adding mouse pressed listeners to the specific areas of the table
        TBLStockMarket.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int column = TBLStockMarket.columnAtPoint(e.getPoint());
                int row = TBLStockMarket.rowAtPoint(e.getPoint());

                if (row >= 0 && row < TBLStockMarket.getRowCount()) {
                    // Unselect all rows except the clicked one
                    TBLStockMarket.clearSelection();
                    TBLStockMarket.addRowSelectionInterval(row, row);
                    String stockName = (String) TBLStockMarket.getValueAt(row, 0);
                    String stockSymbol = (String) TBLStockMarket.getValueAt(row, 1);
                    if (column == 3) {
                        //implement Buy functionalities in this method
                        //TODO: update database in the following toBuyDialog method
                        customerStockMarketController.toBuyDialog(stockName, stockSymbol, LBLCashAvailable, customerID);
                    } else if (column == 0) {
                        //TODO: move this to your controller based on your need
                        // get name of the stock I clicked
                        showMarketMenu(stockName, stockSymbol);
                    }
                }
            }
        });

        //Special Customization for UIs
        TableColumn toBuy = TBLStockMarket.getColumnModel().getColumn(3);
        toBuy.setPreferredWidth(30);
        toBuy.setCellRenderer(new StockMarketTableCellRenderer());
    }

    public void updateTable(){
        updateContent();
    }

    private void showMarketMenu(String stockName, String stockSymbol){
        System.out.println("Show Stock Action clicked");
        new StockMarketActionMenu(customerStockMarketActionMenuController, stockName, stockSymbol, customerID).setVisible(true);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PNLTop = new javax.swing.JPanel();
        PNLTitle = new javax.swing.JPanel();
        LBLTitleTXT = new javax.swing.JLabel();
        LBLCashAvailable = new javax.swing.JLabel();
        LBLWalletTXT = new javax.swing.JLabel();
        LBLSearch = new javax.swing.JLabel();
        TXTSearchSymbol = new javax.swing.JTextField();
        BTNSearch = new javax.swing.JButton();
        jScrollPaneTable = new javax.swing.JScrollPane();
        TBLStockMarket = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        PNLTop.setPreferredSize(new java.awt.Dimension(900, 226));

        LBLTitleTXT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        LBLTitleTXT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBLTitleTXT.setText("Stock Market");
        LBLTitleTXT.setPreferredSize(new java.awt.Dimension(92, 21));

        javax.swing.GroupLayout PNLTitleLayout = new javax.swing.GroupLayout(PNLTitle);
        PNLTitle.setLayout(PNLTitleLayout);
        PNLTitleLayout.setHorizontalGroup(
                PNLTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PNLTitleLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(LBLTitleTXT, javax.swing.GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE)
                                .addContainerGap())
        );
        PNLTitleLayout.setVerticalGroup(
                PNLTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PNLTitleLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(LBLTitleTXT, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                                .addContainerGap())
        );

        LBLCashAvailable.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        LBLCashAvailable.setText("0");

        LBLWalletTXT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        LBLWalletTXT.setText("Cash Available: $");

        LBLSearch.setText("Search By Symbol");

        TXTSearchSymbol.setToolTipText("Type Symbol to search");
        TXTSearchSymbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXTSearchSymbolActionPerformed(evt);
            }
        });

        BTNSearch.setText("Search");
        BTNSearch.addActionListener(customerStockMarketController);

        javax.swing.GroupLayout PNLTopLayout = new javax.swing.GroupLayout(PNLTop);
        PNLTop.setLayout(PNLTopLayout);
        PNLTopLayout.setHorizontalGroup(
                PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PNLTopLayout.createSequentialGroup()
                                .addGroup(PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(PNLTopLayout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addComponent(PNLTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(PNLTopLayout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addComponent(LBLWalletTXT)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(LBLCashAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(PNLTopLayout.createSequentialGroup()
                                                .addGap(261, 261, 261)
                                                .addGroup(PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(PNLTopLayout.createSequentialGroup()
                                                                .addGap(113, 113, 113)
                                                                .addComponent(TXTSearchSymbol, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(LBLSearch))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(BTNSearch)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PNLTopLayout.setVerticalGroup(
                PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PNLTopLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(PNLTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LBLCashAvailable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(LBLWalletTXT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(TXTSearchSymbol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(BTNSearch)
                                        .addComponent(LBLSearch))
                                .addGap(11, 11, 11))
        );

        jScrollPaneTable.setPreferredSize(new java.awt.Dimension(900, 439));

        TBLStockMarket.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null}
                },
                new String[]{
                        "Stock Name", "Stock ID", "Symbol", "Current Price", "ToBuy", "Tradable"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TBLStockMarket.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        TBLStockMarket.setPreferredSize(new java.awt.Dimension(900, 700));
        jScrollPaneTable.setViewportView(TBLStockMarket);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPaneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(PNLTop, javax.swing.GroupLayout.PREFERRED_SIZE, 888, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(PNLTop, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TXTSearchSymbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXTSearchSymbolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TXTSearchSymbolActionPerformed

    private void BTNSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNSearchActionPerformed
        // TODO add your handling code here:
        System.out.println("Search Button Clicked");
    }//GEN-LAST:event_BTNSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNSearch;
    private javax.swing.JLabel LBLCashAvailable;
    private javax.swing.JLabel LBLSearch;
    private javax.swing.JLabel LBLTitleTXT;
    private javax.swing.JLabel LBLWalletTXT;
    private javax.swing.JPanel PNLTitle;
    private javax.swing.JPanel PNLTop;
    private javax.swing.JTable TBLStockMarket;
    private javax.swing.JTextField TXTSearchSymbol;
    private javax.swing.JScrollPane jScrollPaneTable;
    // End of variables declaration//GEN-END:variables

    /**
     * Getter for the search button
     */
    public JButton getBTNSearch() {
        return BTNSearch;
    }

    public JTextField getTXTSearchSymbol() {
        return TXTSearchSymbol;
    }
}
