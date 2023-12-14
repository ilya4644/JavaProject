package graphics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class MeanCourseDonePie extends JFrame {

    public MeanCourseDonePie(String title, float[] values, String[] names) {
        super(title);

        var dataset = createDataset(values, names);

        JFreeChart chart = ChartFactory.createPieChart(
                title,
                dataset,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));
        setContentPane(chartPanel);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private DefaultPieDataset createDataset(float[] values, String[] names) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        float total = 0;
        for (float value : values) {
            total += value;
        }

        for (int i = 0; i < values.length; i++) {
            double percentage = (values[i] / total) * 100;
            dataset.setValue(names[i], percentage);
        }

        return dataset;
    }
}
