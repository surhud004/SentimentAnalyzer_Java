package stanfordcorenlp_opinionmining;

public class SentimentResult {

	double sentimentScore;
	String sentimentType;
	SentimentClassifier sentimentClass;

	public double getSentiment() {
		return sentimentScore;
	}

	public double getSentimentScore() {
		return sentimentScore;
	}

	public void setSentimentScore(double sentimentScore) {
		this.sentimentScore = sentimentScore;
	}

	public String getSentimentType() {
		return sentimentType;
	}

	public void setSentimentType(String sentimentType) {
		this.sentimentType = sentimentType;
	}

	public SentimentClassifier getSentimentClass() {
		return sentimentClass;
	}

	public void setSentimentClass(SentimentClassifier sentimentClass) {
		this.sentimentClass = sentimentClass;
	}

	

}