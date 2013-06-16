/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datastructures;

import chatBot.SymptomsOccurence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Disease {

    private String name;
    //a-priori probability P(D)
    private double diseaseProbability;
    private Map<DiseaseClue, DiseaseProbabilityBean> symptoms; 
    private Map<DiseaseClue, DiseaseProbabilityBean> tests;

    public Disease() {
        symptoms = new HashMap<>();
        tests = new HashMap<>();
    }
    
    public Disease(String name) {
        symptoms = new HashMap<>();
        tests = new HashMap<>();
        this.name = name;
    }  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiseaseProbability() {
        return diseaseProbability;
    }

    public void setDiseaseProbability(double diseaseProbability) {
        this.diseaseProbability = diseaseProbability;
    }

    public Map<DiseaseClue, DiseaseProbabilityBean> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Map<DiseaseClue, DiseaseProbabilityBean> symtpoms) {
        this.symptoms = symtpoms;
    }

    public Map<DiseaseClue, DiseaseProbabilityBean> getTests() {
        return tests;
    }

    public void setTests(Map<DiseaseClue, DiseaseProbabilityBean> tests) {
        this.tests = tests;
    }
         
    public ArrayList<String> getSymptomNames() {
        ArrayList symptomNames = new ArrayList();
        for(DiseaseClue clue : symptoms.keySet()) {
            symptomNames.add(clue.getName());
        }
        return symptomNames;
    }
    
    public ArrayList<String> getDiffSymptomNames(ArrayList<String> currentSymptoms) {
        ArrayList<String> diseaseSymptoms = getSymptomNames();
        diseaseSymptoms.removeAll(currentSymptoms);
        return diseaseSymptoms;
    }
    
    @Override
    public String toString() {
        String output = "";
        output += name + ", ";
        for (Map.Entry<DiseaseClue, DiseaseProbabilityBean> entry : symptoms.entrySet()) {
            DiseaseClue diseaseClue = entry.getKey();
            DiseaseProbabilityBean diseaseProbabilityBean = entry.getValue();
            output += diseaseProbabilityBean.getSymptom().getName() + " " + diseaseProbabilityBean.getpSgivenD() + " " + diseaseProbabilityBean.getpSgivenNotD();
            output += ", ";
        }
        return output;
    }
}
