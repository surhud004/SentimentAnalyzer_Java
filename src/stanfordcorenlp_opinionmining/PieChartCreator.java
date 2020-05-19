package stanfordcorenlp_opinionmining;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


public class PieChartCreator extends JFrame {


	private static final long serialVersionUID = 1L;

	JFrame jf=new JFrame("Sentiment Analyzer Stanford CoreNLP Java");
	JPanel jp=new JPanel();
	static int[] reviewarray = ReviewClassifier.reviewCountArray();
	static int count = ReviewClassifier.getCount();
	
	public PieChartCreator() throws NumberFormatException, IOException {
		
        final DefaultPieDataset piechart = new DefaultPieDataset();
        
        piechart.setValue("Very Positive", new Double(new DecimalFormat("##.##").format(((double)reviewarray[0]/(double)count)*100)));
        piechart.setValue("Positive", new Double(new DecimalFormat("##.##").format(((double)reviewarray[1]/(double)count)*100)));
        piechart.setValue("Neutral", new Double(new DecimalFormat("##.##").format(((double)reviewarray[4]/(double)count)*100)));
        piechart.setValue("Negative", new Double(new DecimalFormat("##.##").format(((double)reviewarray[2]/(double)count)*100)));
        piechart.setValue("Very Negative", new Double(new DecimalFormat("##.##").format(((double)reviewarray[3]/(double)count)*100)));
        
        final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1}%");

        final JFreeChart chart = ChartFactory.createPieChart("Overall Review", piechart, true, true, false);
        final PiePlot plot1 = (PiePlot) chart.getPlot();
        plot1.setLabelGenerator(labelGenerator);
        plot1.setSectionPaint(0, Color.decode("#00FF00"));
        plot1.setSectionPaint(1, Color.decode("#A3FF00"));
        plot1.setSectionPaint(2, Color.decode("#FFFF00"));
        plot1.setSectionPaint(3, Color.decode("#FFA700"));
        plot1.setSectionPaint(4, Color.decode("#FF0000"));
        
        jp.setLayout(new GridLayout());
        final ChartPanel panel = new ChartPanel(chart);
        jp.add(panel);
        jf.add(jp);
		jf.setVisible(true);
		jf.pack();
		jf.setSize(800, 600);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
    }

    public static void main(final String[] args) {
    	// TODO Auto-generated method stub
    }
}