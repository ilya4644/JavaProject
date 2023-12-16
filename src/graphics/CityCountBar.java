package graphics;

import database.GetCounter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CityCountBar extends JFrame {
    public CityCountBar(ArrayList<GetCounter> cityCountList) {
        super("City Count Bar");

        DefaultCategoryDataset dataset = createDataset(cityCountList);

        JFreeChart chart = ChartFactory.createBarChart(
                "City count",
                "City",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(ArrayList<GetCounter> cityCountList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (GetCounter cityCount: cityCountList) {
            String city = cityCount.getValue();
            if (city.equals("Екатеринбург")) {
                continue;
            }
            int count = cityCount.getCount();

            dataset.addValue(count, "City name count", city);
        }

        return dataset;
    }
}
