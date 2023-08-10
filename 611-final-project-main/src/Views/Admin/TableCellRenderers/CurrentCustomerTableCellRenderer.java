package Views.Admin.TableCellRenderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CurrentCustomerTableCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        // Call super method to set up cell properties
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Set the background color for specific cell(s)
        if (column == 5) {
            setForeground(Color.BLUE);
            setText("Get report");
            setHorizontalAlignment(CENTER);
        } else if (column == 6) {
            setForeground(Color.BLUE);
            setText("Send");
            setHorizontalAlignment(CENTER);
        }
        return this;
    }
}