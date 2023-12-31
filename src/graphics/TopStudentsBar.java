package graphics;

import database.TopStudent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TopStudentsBar extends JFrame {
    public TopStudentsBar(String title, ArrayList<TopStudent> students) {
        super(title);

        DefaultCategoryDataset dataset = createDataset(students);

        JFreeChart chart = ChartFactory.createBarChart(
                "Top student scores",
                "Student",
                "Total score",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setMaximumBarWidth(0.2);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(ArrayList<TopStudent> students) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (TopStudent student: students) {
            String fullname = student.getName() + " " + student.getSurname();
            int totalScore = student.getScoreEx() + student.getScorePr();

            dataset.addValue(totalScore, "Student score", fullname);
        }

        return dataset;
    }
}
