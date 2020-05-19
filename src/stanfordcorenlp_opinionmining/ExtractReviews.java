package stanfordcorenlp_opinionmining;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractReviews extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame jf = new JFrame("Sentiment Analyzer Stanford CoreNLP Java");
	JPanel jp=new JPanel();
	JButton b1=new JButton("EXTRACT");
	JButton b2=new JButton("NEXT");
	JLabel title=new JLabel("Sentiment Analyzer Stanford CoreNLP Java");
	JLabel l1=new JLabel("Enter URL: ");
	JLabel l2=new JLabel("", JLabel.CENTER);
	JLabel l3=new JLabel("", JLabel.CENTER);
	JTextField jt1=new JTextField(20);
	Font f = new Font("Calibri", Font.BOLD, 18);
	Font titlef = new Font("Calibri", Font.BOLD, 44);
	
	public ExtractReviews()
	{
		jp.setLayout(new FlowLayout(FlowLayout.CENTER, 4, 100));
		
		l1.setFont(f);
		l2.setFont(f);
		l3.setFont(f);
		b1.setFont(f);
		b2.setFont(f);
		jt1.setFont(f);
		title.setFont(titlef);
		
		jp.add(title);
		jp.add(l1);
		jp.add(jt1);
		jp.add(b1);
		jp.add(b2);
		jp.add(l2);
		jp.add(l3);
		b1.addActionListener(this);
		b2.addActionListener(this);
		jf.add(jp);
		jf.setVisible(true);
		jf.pack();
		jf.setSize(800, 600);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jp.setBackground(Color.decode("#262626"));
		title.setForeground(Color.decode("#6be007"));
		l1.setForeground(Color.decode("#6be007"));
		l2.setForeground(Color.decode("#6be007"));
		l3.setForeground(Color.decode("#6be007"));
		b1.setBackground(Color.decode("#6be007"));
		b1.setForeground(Color.decode("#262626"));
		b2.setBackground(Color.decode("#6be007"));
		b2.setForeground(Color.decode("#262626"));

	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		@SuppressWarnings("unused")
		ExtractReviews er=new ExtractReviews();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String btname = e.getActionCommand();
		if(btname.equals("EXTRACT"))
		{
			String geturl = jt1.getText().toString();
			l2.setText("<html>Connecting to: "+geturl+"<br/>Please Wait...</html>");
			try {
				
				URL url = new URL(geturl);
				url.toURI();
				extractDataFromUrl(geturl);
				
			} catch (MalformedURLException ae) {
				ae.printStackTrace();
			} catch (URISyntaxException ue) {
				ue.printStackTrace();
			}
			
			Timer t1 = new Timer(1000, new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                l3.setText("Extracting Data from: "+geturl);
	            }
	        });
			t1.setRepeats(false);
			t1.start();
			
			Timer t2 = new Timer(5000, new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                l3.setText("Extracted Data...Done. Click on Next button to continue.");
	            }
	        });
			t2.setRepeats(false);
			t2.start();
		}
		else if(btname.equals("NEXT"))
		{
			try {
				new ReviewClassifier();
			} catch (IOException re) {
				// TODO Auto-generated catch block
				re.printStackTrace();
			}
		}
		
	}

	private void extractDataFromUrl(String geturl) {
		// TODO Auto-generated method stub
		Document[] doc = new Document[20];
		Elements getreviews = new Elements();
		if(geturl.contains("bitcoin")) {
			try {
	
				for(int i = 1; i <= 2; i++)
				{
					doc[i] = Jsoup.connect(geturl).get();
					System.out.println("Extracting Data from: "+doc[i].title());
					getreviews.addAll(doc[i].select("div[class=post]"));
					geturl += ".20";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if(geturl.contains("rottentomatoes")) {
			try {
				
				for(int i = 2; i <= 14; i++)
				{
					doc[i] = Jsoup.connect(geturl).get();
					System.out.println("Extracting Data from: "+doc[i].title());
					getreviews.addAll(doc[i].select("div[class=the_review]"));
					geturl += "?page="+i;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if(geturl.contains("anandtech")){
			try {
				
				doc[1] = Jsoup.connect(geturl).get();
				System.out.println("Extracting Data from: "+doc[1].title());
				getreviews.addAll(doc[1].select("div[class=bbWrapper]"));
				
				for(int i = 2; i <= 5; i++)
				{
					doc[i] = Jsoup.connect(geturl+"/page-"+i).get();
					System.out.println("Extracting Data from: "+doc[i].title());
					getreviews.addAll(doc[i].select("div[class=bbWrapper]"));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			//WRITE WEB SCRAPPING FOR OTHER WEBSITES.
		}

		try(FileWriter fw = new FileWriter("reviews.txt", false);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)) {
			    
			for (Element element : getreviews) {
			    out.println(element.text());
			}
			
			} catch (IOException e) {
			    e.printStackTrace();
			}
	}
	
	
}
