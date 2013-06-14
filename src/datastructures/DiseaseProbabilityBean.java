package datastructures;

/**
 * 
 * @author czarek
 *
 */
public class DiseaseProbabilityBean {
	private Disease disease;
	private DiseaseClue symptom;
	
	//true positive P(S|D):
	private double pSgivenD;
	//P(~S|C) = 1 - pSgivenD
	
	
	//false positive P(S|~D):
	private double pSgivenNotD;
	//P(~S|~C) = 1 - pSgivenNotD;
}
