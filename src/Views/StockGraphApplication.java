package Views;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockGraphApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Stock Price Graph");
            List<Object[]> dataPoints = new ArrayList<>(Arrays.asList(
                    new Object[]{111.0, Timestamp.valueOf("2023-05-07 04:47:03")},
                    new Object[]{115.0, Timestamp.valueOf("2023-05-07 05:10:30")},
                    new Object[]{116.0, Timestamp.valueOf("2023-05-07 05:10:46")}));
            frame.add(new StockGraphPanel(dataPoints));
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
