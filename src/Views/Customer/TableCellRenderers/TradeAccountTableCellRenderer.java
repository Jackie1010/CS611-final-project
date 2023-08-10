package Views.Customer.TableCellRenderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TradeAccountTableCellRenderer extends DefaultTableCellRenderer {
    private Color darkGreen = new Color(0,153,51);
    private Color darkRed = new Color(255,0,0);
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        // Call super method to set up cell properties
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Set the background color for specific cell(s)
        if (column == 6) {
            setForeground(darkRed);
            setText("Buy");
            setHorizontalAlignment(CENTER);
        } else if (column == 7){
            setForeground(darkGreen);
            setText("Sell");
            setHorizontalAlignment(CENTER);
        }
        return this;
    }
}

