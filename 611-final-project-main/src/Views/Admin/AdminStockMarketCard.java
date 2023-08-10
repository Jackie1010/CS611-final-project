/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views.Admin;

import Controllers.AdminMarketController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author zeqi
 */
public class AdminStockMarketCard extends javax.swing.JPanel {

    private AdminMarketController adminMarketController;
    private String[] columnNames;
    private Object[][] data;

    /**
     * Creates new form AdminStockMarketCard
     */
    public AdminStockMarketCard(AdminMarketController adminMarketController) {
        this.adminMarketController = adminMarketController;
        adminMarketController.setAdminStockMarketCard(this);
        initComponents();
        //TODO Database update these code
        adminMarketController.setData();
        updateContent();
    }

    /**
     * Update Table with search
     */
    public void updateTable() {
        updateContent();
    }

    private void updateContent() {
        /**
         * Try not to modify these code, or if you feel it's necessarily to do so
         */
        columnNames = adminMarketController.getColumnNames();
        data = adminMarketController.getData();
        //Default settings for every table
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
                    String symbol = (String) TBLStockMarket.getValueAt(row, 2);
                    String stockName = (String) TBLStockMarket.getValueAt(row, 0);
                    if (column == 3) {
                        modifyPrice(row, symbol);
                    } else if (column == 4) {
                        modifyTradability(row, symbol);
                    } else if (column == 0) {
                        showStockMarketMenu(row, symbol, stockName);
                    }
                }
            }
        });
    }

    private void showStockMarketMenu(int row, String symbol, String stockName) {
        new AdminStockMarketMenu(row, symbol, stockName, adminMarketController).setVisible(true);
    }

    private void modifyTradability(int row, String symbol) {
        //TODO: Update Database

        // Show a dialog with "T" and "F" options
        int option = JOptionPane.showOptionDialog(null, "Select tradability option", "Modify Tradability",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                new Object[]{"T", "F"}, null);

        // Check which option was selected and update the table
        // get which option was selected
        String tradability = (option == 0) ? "T" : "F";
        System.out.println("AdminStockMarketCard: tradability = " + tradability);
        DefaultTableModel model = (DefaultTableModel) TBLStockMarket.getModel();
        model.setValueAt(tradability, row, 4);
        adminMarketController.updateTradeability(tradability, symbol);
    }


    private void modifyPrice(int row, String symbol) {
        //TODO: Update Database

        String input = JOptionPane.showInputDialog(this, "Enter new price:");
        if (input != null) {
            try {
                double newPrice = Double.parseDouble(input);
                newPrice = Math.round(newPrice * 100000.0) / 100000.0; // round to 5 decimals
                TBLStockMarket.setValueAt(newPrice, row, 3);
                adminMarketController.updatePrice(newPrice, symbol);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.");
            }
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

        PNLTop = new javax.swing.JPanel();
        PNLTitle = new javax.swing.JPanel();
        LBLTitleTXT = new javax.swing.JLabel();
        LBLNote = new javax.swing.JLabel();
        LBLSearch = new javax.swing.JLabel();
        TXTSearchSymbol = new javax.swing.JTextField();
        BTNSearch = new javax.swing.JButton();
        jScrollPaneTable = new javax.swing.JScrollPane();
        TBLStockMarket = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        PNLTop.setPreferredSize(new java.awt.Dimension(900, 226));

        LBLTitleTXT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        LBLTitleTXT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBLTitleTXT.setText("Stock Market (Boss Version)");
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

        LBLNote.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        LBLNote.setForeground(new java.awt.Color(0, 102, 255));
        LBLNote.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBLNote.setText("Note: Click the price cell to modify price. Click the Tradable to modify tradability of the stock.");

        LBLSearch.setText("Search By Symbol");

        TXTSearchSymbol.setToolTipText("Type Symbol to search");
        TXTSearchSymbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXTSearchSymbolActionPerformed(evt);
            }
        });

        BTNSearch.setText("Search");
        BTNSearch.addActionListener(adminMarketController);

        javax.swing.GroupLayout PNLTopLayout = new javax.swing.GroupLayout(PNLTop);
        PNLTop.setLayout(PNLTopLayout);
        PNLTopLayout.setHorizontalGroup(
                PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PNLTopLayout.createSequentialGroup()
                                .addGroup(PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(LBLNote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(PNLTopLayout.createSequentialGroup()
                                                .addGroup(PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(PNLTopLayout.createSequentialGroup()
                                                                .addGap(13, 13, 13)
                                                                .addComponent(PNLTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(PNLTopLayout.createSequentialGroup()
                                                                .addGap(279, 279, 279)
                                                                .addGroup(PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(PNLTopLayout.createSequentialGroup()
                                                                                .addGap(113, 113, 113)
                                                                                .addComponent(TXTSearchSymbol, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(LBLSearch))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(BTNSearch)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        PNLTopLayout.setVerticalGroup(
                PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PNLTopLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(PNLTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LBLNote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PNLTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(TXTSearchSymbol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(BTNSearch)
                                        .addComponent(LBLSearch))
                                .addGap(11, 11, 11))
        );

        jScrollPaneTable.setPreferredSize(new java.awt.Dimension(900, 439));

        TBLStockMarket.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}
                },
                new String[]{
                        "Stock Name", "Stock ID", "Symbol", "Current Price", "Tradable"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false
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
        System.out.println("Search Button clicked");
    }//GEN-LAST:event_BTNSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNSearch;
    private javax.swing.JLabel LBLNote;
    private javax.swing.JLabel LBLSearch;
    private javax.swing.JLabel LBLTitleTXT;
    private javax.swing.JPanel PNLTitle;
    private javax.swing.JPanel PNLTop;
    private javax.swing.JTable TBLStockMarket;
    private javax.swing.JTextField TXTSearchSymbol;
    private javax.swing.JScrollPane jScrollPaneTable;
    // End of variables declaration//GEN-END:variables

    /**
     * Getter for BTNSearch
     */
    public javax.swing.JButton getBTNSearch() {
        return BTNSearch;
    }

    /**
     * Getter for TXTSearchSymbol
     */
    public javax.swing.JTextField getTXTSearchSymbol() {
        return TXTSearchSymbol;
    }
}
