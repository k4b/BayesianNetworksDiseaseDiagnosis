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
    private String definition;
    
	public void addSymptom(DiseaseSymptom symptom, DiseaseProbabilityBean probability){
		symptoms.put(symptom, probability);
	}
	
	public void addTest(DiseaseTest test, DiseaseProbabilityBean probability){
		tests.put(test, probability);
	}
	
	
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

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	public String toString() {
		return "Disease [name=" + name + ", diseaseProbability="
				+ diseaseProbability + ", symptoms=" + symptoms + ", tests="
				+ tests + ", definition=" + definition + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disease other = (Disease) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
    
    
    
}
