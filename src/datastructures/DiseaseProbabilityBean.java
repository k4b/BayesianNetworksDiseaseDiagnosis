package datastructures;

/**
 * 
 * @author czarek
 *
 */
public class DiseaseProbabilityBean {
	private Disease disease;
	private DiseaseClue symptom;
	
	//true positive P(S|D tak ):
	private double pSgivenD;
	//P(~S|D) = 1 - pSgivenD
	
	
	//false positive P(S|~D):
	private double pSgivenNotD;
	//P(~S|~D) = 1 - pSgivenNotD;
}
