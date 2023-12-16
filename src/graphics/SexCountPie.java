package graphics;

import database.GetCounter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SexCountPie extends JFrame {
    public SexCountPie(ArrayList<GetCounter> sexCountList) {
        super("Sex count");

        var dataset = createDataset(sexCountList);

        JFreeChart chart = ChartFactory.createPieChart(
                null,
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
    private DefaultPieDataset createDataset(ArrayList<GetCounter> sexCountList) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (GetCounter sexCount: sexCountList) {
            dataset.setValue(sexCount.getValue(), sexCount.getCount());
        }
        return dataset;
    }
}
