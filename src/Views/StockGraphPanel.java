package Views;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StockGraphPanel extends JPanel {

    private List<Object[]> dataPoints;
    private int padding = 50;
    private int labelPadding = 10;
    private Color lineColor = Color.blue;
    private Color gridColor = Color.lightGray;
    private Color axisColor = Color.black;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public StockGraphPanel(List<Object[]> dataPoints) {
        this.dataPoints = dataPoints;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minPrice = dataPoints.stream().mapToDouble(point -> (double) point[0]).min().orElse(0.0);
        double maxPrice = dataPoints.stream().mapToDouble(point -> (double) point[0]).max().orElse(1.0);

        // Draw the grid lines
        g2.setColor(gridColor);
        for (int i = 0; i < dataPoints.size(); i++) {
            int x = padding + labelPadding + i * (getWidth() - 2 * padding - labelPadding) / (dataPoints.size() - 1);
            g2.drawLine(x, padding, x, getHeight() - padding - labelPadding);
        }

        // Draw the stock price line
        g2.setColor(lineColor);
        g2.setStroke(new BasicStroke(2f));
        for (int i = 0; i < dataPoints.size() - 1; i++) {
            int x1 = padding + labelPadding + i * (getWidth() - 2 * padding - labelPadding) / (dataPoints.size() - 1);
            int y1 = (int) ((getHeight() - 2 * padding - labelPadding) * (1 - (((double) dataPoints.get(i)[0] - minPrice) / (maxPrice - minPrice))) + padding);
            int x2 = padding + labelPadding + (i + 1) * (getWidth() - 2 * padding - labelPadding) / (dataPoints.size() - 1);
            int y2 = (int) ((getHeight() - 2 * padding - labelPadding) * (1 - (((double) dataPoints.get(i + 1)[0] - minPrice) / (maxPrice - minPrice))) + padding);
            g2.drawLine(x1, y1, x2, y2);
        }

        // Draw the axes
        g2.setColor(axisColor);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        // Draw the X-axis labels
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int i = 0; i < dataPoints.size(); i++) {
            int x = padding + labelPadding + i * (getWidth() - 2 * padding - labelPadding) / (dataPoints.size() - 1);
            String timeStamp = dateFormat.format((Date) dataPoints.get(i)[1]);
            int stringWidth = g2.getFontMetrics().stringWidth(timeStamp);
            g2.drawString(timeStamp, x - stringWidth / 2, getHeight() - padding + labelPadding / 2);
        }
        // Draw the Y-axis labels
        int numberOfYLabels = 10;
        for (int i = 0; i <= numberOfYLabels; i++) {
            double priceLabel = minPrice + (maxPrice - minPrice) * i / numberOfYLabels;
            String priceText = String.format("%.2f", priceLabel);
            int stringWidth = g2.getFontMetrics().stringWidth(priceText);
            int y = (int) ((getHeight() - 2 * padding - labelPadding) * (1 - i / (double) numberOfYLabels) + padding);
            g2.drawString(priceText, padding - stringWidth - labelPadding / 2, y + g2.getFontMetrics().getHeight() / 2);
        }
    }
}




