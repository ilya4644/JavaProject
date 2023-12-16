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

public class CountryCountBar  extends JFrame {
    public CountryCountBar(ArrayList<GetCounter> countryCountList) {
        super("Country Count Bar");

        DefaultCategoryDataset dataset = createDataset(countryCountList);

        JFreeChart chart = ChartFactory.createBarChart(
                "Country count",
                "Country",
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

    private DefaultCategoryDataset createDataset(ArrayList<GetCounter> countryCountList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (GetCounter countryCount: countryCountList) {
            String country = countryCount.getValue();
            if (country.equals("Россия")) {
                continue;
            }
            int count = countryCount.getCount();

            dataset.addValue(count, "Country name count", country);
        }

        return dataset;
    }
}
