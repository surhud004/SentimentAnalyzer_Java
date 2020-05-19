package stanfordcorenlp_opinionmining;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ReviewClassifier extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int reviewcount[] = new int[5];	static int count = 0;
	
	static SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
		JFrame jf=new JFrame("Sentiment Analyzer Stanford CoreNLP Java");
	JPanel jp=new JPanel();
	JButton b1=new JButton("NEXT");	JLabel title=new JLabel("Sentiment Analyzer Stanford CoreNLP Java");
	JLabel l1=new JLabel("<html>Classifying all the reviews...<br/>Please Wait...</html>", JLabel.CENTER);	Font f = new Font("Calibri", Font.BOLD, 18);	Font titlef = new Font("Calibri", Font.BOLD, 44);
	
	public ReviewClassifier() throws IOException
	{
		readFromFile();
		sentimentAnalyzer.initialize();
		jp.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
				l1.setFont(f);		b1.setFont(f);		title.setFont(titlef);		
		jp.add(title);		jp.add(l1);		jp.add(b1);
		b1.addActionListener(this);
		jf.add(jp);
		jf.setVisible(true);		jf.pack();
		jf.setSize(800, 600);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jp.setBackground(Color.decode("#262626"));		title.setForeground(Color.decode("#6be007"));
		l1.setForeground(Color.decode("#6be007"));
		b1.setBackground(Color.decode("#6be007"));
		b1.setForeground(Color.decode("#262626"));
		
		Timer t = new Timer(5000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                l1.setText("<html>Classified all the reviews...Done.<br/>Click on Next button to continue.</html>");
            }
        });
        t.setRepeats(false);
        t.start();
	}
	
	
	public static void main(String[] args) throws IOException {		
		// TODO Auto-generated method stub
	}
	
	public static void readFromFile() throws IOException
	{
		try (BufferedReader br = new BufferedReader(new FileReader("reviews.txt"));) {
		
		String line;
		while((line = br.readLine()) != null) {
			
			String eachreview = classifyreview(line);
			
			switch(eachreview) 
			{
			case "Very positive":
				reviewcount[0]++;				count++;
				break;
			case "Positive":
				reviewcount[1]++;				count++;
			  	break;
			case "Negative":
				reviewcount[2]++;				count++;
			  	break;
			case "Very negative":
				reviewcount[3]++;				count++;
			  	break;
			case "Neutral":
				reviewcount[4]++;				count++;
				break;
			default: System.out.println("Error");
				break;
			}
	      }
		} catch (FileNotFoundException fe) {			fe.printStackTrace();		}
		System.out.println("reviewcount Array");
		for(int j=0;j<=4;j++)
		{
			System.out.print(j+":"+reviewcount[j]+" ");
		}
		System.out.println("Total Count: "+count);
	}
	
	public static String classifyreview(String getreview){
		
		System.out.println("Review: "+getreview);
		SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(getreview);
		System.out.println("Sentiment: "+sentimentResult.sentimentType);
		return sentimentResult.sentimentType;
	}		public static int[] reviewCountArray()    {        return reviewcount;    }
	public static int getCount()	{		return count;
	}	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		String btname = ae.getActionCommand();
		if(btname.equals("NEXT"))
		{
			try {
				new PieChartCreator();
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
