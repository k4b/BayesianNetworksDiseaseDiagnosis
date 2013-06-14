/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diseasediagnosis;

import java.util.HashMap;

import datastructures.DiseaseSymptom;


/**
 * 
 * @author Karol Abramczyk
 */
public class DataModel {
    private HashMap<String, DiseaseSymptom> symptoms;
    
    public DataModel() {
        createData();
    }
    
    private void createData() {
        symptoms = new HashMap<>();
        symptoms.put("sneezing", new DiseaseSymptom("sneezing"));
        symptoms.put("cough", new DiseaseSymptom("cough"));
        symptoms.put("fever", new DiseaseSymptom("fever"));
        symptoms.put("skin lesions", new DiseaseSymptom("skin lesions"));
        symptoms.put("diarrhea", new DiseaseSymptom("diarrhea"));
        symptoms.put("hair loss", new DiseaseSymptom("hair loss"));
        symptoms.put("muscle pain", new DiseaseSymptom("muscle pain"));
        symptoms.put("itch", new DiseaseSymptom("itch"));
        symptoms.put("vomiting", new DiseaseSymptom("vomiting"));
        symptoms.put("weakness", new DiseaseSymptom("weakness"));
    }

    public HashMap<String, DiseaseSymptom> getSymptoms() {
        return symptoms;
    }
}
