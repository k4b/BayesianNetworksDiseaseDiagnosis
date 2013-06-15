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

    public DiseaseProbabilityBean(Disease disease, DiseaseClue symptom) {
        this.disease = disease;
        this.symptom = symptom;
    }

    public DiseaseProbabilityBean(Disease disease, DiseaseClue symptom, double pSgivenD, double pSgivenNotD) {
        this.disease = disease;
        this.symptom = symptom;
        this.pSgivenD = pSgivenD;
        this.pSgivenNotD = pSgivenNotD;
    }

    public Disease getDisease() {
        return disease;
    }

    public DiseaseClue getSymptom() {
        return symptom;
    }

    public double getpSgivenD() {
        return pSgivenD;
    }

    public double getpSgivenNotD() {
        return pSgivenNotD;
    }
        
        
}
