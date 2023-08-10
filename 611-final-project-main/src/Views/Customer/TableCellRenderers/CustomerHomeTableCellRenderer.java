package Views.Customer.TableCellRenderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CustomerHomeTableCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        // Call super method to set up cell properties
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Set the background color for specific cell(s)
        if (column == 1) {
            setForeground(Color.BLUE);
            setText("Get Report");
            setHorizontalAlignment(CENTER);
        }
        return this;
    }
}
