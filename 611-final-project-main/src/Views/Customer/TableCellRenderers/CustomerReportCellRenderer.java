package Views.Customer.TableCellRenderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CustomerReportCellRenderer extends DefaultTableCellRenderer {
    private Color darkGreen = new Color(0,153,51);
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        // Call super method to set up cell properties
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Set the background color for specific cell(s)
        if (column == 5) {
            String cellValue = (String) value;
            if (cellValue.equalsIgnoreCase("buy")) {
                setForeground(Color.RED);
            } else if (cellValue.equalsIgnoreCase("sell")) {
                setForeground(darkGreen);
            } else {
                // Set default background color if neither "buy" nor "sell"
                setForeground(Color.BLACK);
            }
        }
        return this;
    }
}
