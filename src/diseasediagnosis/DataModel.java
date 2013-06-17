/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diseasediagnosis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import datastructures.DiseaseSymptom;


/**
 * 
 * @author Karol Abramczyk
 */
public class DataModel {
    private Map<String, DiseaseSymptom> symptoms;
    private String[] symptomNames = {"sneezing", "cough", "fever", "skin lesions",
        "diarrhea", "hair loss", "muscle pain", "itch", "vomiting", "weakness"};
    
    public DataModel() {
//        createData();
    }
    
//    private void createData() {
//        symptoms = new HashMap<>();
//        for(int i = 0; i<symptomNames.length; i++) {
//            symptoms.put(symptomNames[i], new DiseaseSymptom(symptomNames[i]));            
//        }
////        System.out.println(symptoms.toString());
//    }
//
    public Map<String, DiseaseSymptom> getSymptoms() {
        return symptoms;
    }

	public String[] getSymptomNames() {
		return symptomNames;
	}

	public void setSymptomNames(String[] symptomNames) {
		this.symptomNames = symptomNames;
	}

	public void setSymptoms(Map<String, DiseaseSymptom> symptoms) {
		this.symptoms = symptoms;
	}

	public void setSymptomNames(Map<String, DiseaseSymptom> symptoms2) {
		Collection<DiseaseSymptom> collection = symptoms2.values();
		for (DiseaseSymptom diseaseSymptom : collection) {
			
		}
	}
    
    
}
