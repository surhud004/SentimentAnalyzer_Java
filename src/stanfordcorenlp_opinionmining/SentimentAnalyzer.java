package stanfordcorenlp_opinionmining;

import java.util.Properties;

import org.ejml.simple.SimpleMatrix;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalyzer {
	
	static Properties props;
	static StanfordCoreNLP pipeline;

	public void initialize() {
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		pipeline = new StanfordCoreNLP(props);
		System.out.println("Stanford CoreNLP initialized.");
	}

	public SentimentResult getSentimentResult(String text) {

		SentimentResult sentimentResult = new SentimentResult();
		SentimentClassifier sentimentClass = new SentimentClassifier();

		if (text != null && text.length() > 0) {
			initialize();
			// run all Annotators on the text
			Annotation annotation = pipeline.process(text);

			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				// this is the parse tree of the current sentence
				Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
				String sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

				sentimentClass.setVeryPositive((double)Math.round(sm.get(4) * 100d));
				sentimentClass.setPositive((double)Math.round(sm.get(3) * 100d));
				sentimentClass.setNeutral((double)Math.round(sm.get(2) * 100d));
				sentimentClass.setNegative((double)Math.round(sm.get(1) * 100d));
				sentimentClass.setVeryNegative((double)Math.round(sm.get(0) * 100d));
				
				sentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));
				sentimentResult.setSentimentType(sentimentType);
				sentimentResult.setSentimentClass(sentimentClass);
			}

		}


		return sentimentResult;
	}
	
}