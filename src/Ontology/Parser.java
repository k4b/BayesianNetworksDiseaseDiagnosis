package Ontology;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import datastructures.Disease;
import datastructures.DiseaseClue;
import datastructures.DiseaseProbabilityBean;
import datastructures.DiseaseSymptom;
import datastructures.DiseaseTest;

public class Parser {

	private BufferedReader reader;
	
	private static final String TERM_PREFIX = "[Term]";

	private static final String NAME = "name: ";

	private static final String DEFINITION = "def: ";

	private static final String EMPTY_LINE = "";
	
	private static final String DEFINITION_REGEX = "(and )?has_symptom";

	private static final int DISEASES_INITIAL_CAPACITY = 2000;;
	
	private static final int SYMPTOMS_INITIAL_CAPACITY = 5000;;
	
	private Map <String, Disease> diseases;
	private Map <String, DiseaseSymptom> symptoms;
//	private Map <String, DiseaseTest> tests;
	
	public void parseOntology(String file) throws IOException{
		String line;
		reader = new BufferedReader(new FileReader(file));
		diseases = new HashMap<String, Disease>(DISEASES_INITIAL_CAPACITY);
		symptoms = new HashMap<String, DiseaseSymptom>(SYMPTOMS_INITIAL_CAPACITY);
		while((line = reader.readLine())!=null){
			if(TERM_PREFIX.equals(line)){
				parseTerm();
			}
		}
	}

	private void parseTerm() throws IOException {
		String line;
		Disease disiease = new Disease();
		disiease.setDiseaseProbability(RandGenerator.getDiseaseP());
		
		while((line = reader.readLine())!=null){
			if(line.startsWith(NAME)){
				disiease.setName(line.substring(NAME.length()));
			}
			else if(line.startsWith(DEFINITION)){
				parseDefinition(disiease, line);
			}
			else if(line.equals(EMPTY_LINE)){
				//finishTerm();
//				diseases.put(key, value)
				return;
			}
		}		
	}

	private void parseDefinition(Disease disease, String line) {
		String[] strings = line.split("\"");
		if(strings.length > 1){
			String[] symptomNames = strings[1].split(DEFINITION_REGEX);
			for (String name : symptomNames) {
				DiseaseSymptom symptom = symptoms.get(name);
				if(symptom == null){
					symptom = new DiseaseSymptom(name);
				}
				DiseaseProbabilityBean probability =new DiseaseProbabilityBean(disease, symptom, 
						RandGenerator.getPSgivenD(), RandGenerator.getPSgivenNotD());

				symptom.addDisease(disease, probability);
				disease.addSymptom(symptom, probability);
				symptoms.put(name, symptom);
			}
			
		}
	
	}
}
