package Views.Admin.TableCellRenderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class NewCustomerTableCellRenderer extends DefaultTableCellRenderer {
    private Color darkGreen = new Color(0,153,51);
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        // Call super method to set up cell properties
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Set the background color for specific cell(s)
        if (column == 2) {
            setForeground(darkGreen);
            setText("Approve");
            setHorizontalAlignment(CENTER);
        } else if (column == 3) {
            setForeground(Color.RED);
            setText("Deny");
            setHorizontalAlignment(CENTER);
        }
        return this;
    }
}
