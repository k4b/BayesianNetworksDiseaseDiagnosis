package Ontology;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import chatBot.AnswerProcessor;

import datastructures.Disease;
import datastructures.DiseaseClue;
import datastructures.DiseaseProbabilityBean;
import datastructures.DiseaseSymptom;
import datastructures.DiseaseTest;

public class Parser {

	private static final String TERM_PREFIX = "[Term]";

	private static final String NAME = "name: ";

	private static final String DEFINITION_PREFIX = "def: ";

	private static final String EMPTY_LINE = "";

	private static final String DEFINITION_REGEX = "(and )?(or )?has_symptom";

	private static final int DISEASES_INITIAL_CAPACITY = 500;

	private static final int SYMPTOMS_INITIAL_CAPACITY = 1000;

	private static final int NUMBER_OF_TESTS = 3;
	
	private AnswerProcessor wordProcessor  = new AnswerProcessor();
	
	private BufferedReader reader;
	
	private Map <String, Disease> diseases = new HashMap<String, Disease>(DISEASES_INITIAL_CAPACITY);
	
	private Map <String, DiseaseSymptom> symptoms = new HashMap<String, DiseaseSymptom>(SYMPTOMS_INITIAL_CAPACITY);
	//	private Map <String, DiseaseTest> tests;

	public void parseOntology(String file) throws IOException{
		String line;
		reader = new BufferedReader(new FileReader(file));
//		diseases = new HashMap<String, Disease>(DISEASES_INITIAL_CAPACITY);
//		symptoms = new HashMap<String, DiseaseSymptom>(SYMPTOMS_INITIAL_CAPACITY);

		while((line = reader.readLine())!=null){
			if(TERM_PREFIX.equals(line)){
				parseTerm();
			}
		}
	}

	private void parseTerm() throws IOException {
		String line;
		Disease disease = new Disease();
		disease.setDiseaseProbability(RandGenerator.getDiseaseP());
		boolean someSymptomsAdded = false;
		while((line = reader.readLine())!=null){
			if(line.startsWith(NAME)){
				disease.setName(line.substring(NAME.length()));
			}
			else if(line.startsWith(DEFINITION_PREFIX)){
				someSymptomsAdded = parseDefinition(disease, line);
			}
			else if(line.equals(EMPTY_LINE)){
				if(someSymptomsAdded && disease.getName() != null){
					generateTests(disease);
					diseases.put(disease.getName(), disease);
				}

				return;
			}
		}		
	}

	private void generateTests(Disease disease) {
		for(int i =0; i< NUMBER_OF_TESTS; i++){
			DiseaseTest test = new DiseaseTest("tets for " + disease.getName() + " " +i);
			DiseaseProbabilityBean probability =new DiseaseProbabilityBean(disease, test, 
					RandGenerator.getPTestGivenD(), RandGenerator.getPTestGivenNotD());
			test.addDisease(disease, probability);
			disease.addTest(test, probability);
		}

	}

	private boolean parseDefinition(Disease disease, String line) {
		String[] strings = line.split("\"");
		if(strings.length > 1){
			String[] symptomNames = strings[1].split(DEFINITION_REGEX);
			if(symptomNames.length > 1 ){
				for (int i =1; i< symptomNames.length; i++) {
					String name = symptomNames[i].replaceAll(",|\\.", "");
					String stemmedName = wordProcessor.stemSentence(name);
					DiseaseSymptom symptom = symptoms.get(stemmedName);
					if(symptom == null){
						symptom = new DiseaseSymptom(stemmedName);
						symptom.setNonStemmedName(name);
					}
					DiseaseProbabilityBean probability =new DiseaseProbabilityBean(disease, symptom, 
							RandGenerator.getPSgivenD(), RandGenerator.getPSgivenNotD());

					symptom.addDisease(disease, probability);
					disease.addSymptom(symptom, probability);
					disease.setDefinition(line);
					symptoms.put(stemmedName, symptom);
				}

				return true;
			}
		}
		return false;

	}

	public Map<String, Disease> getDiseases() {
		return diseases;
	}

	public void setDiseases(Map<String, Disease> diseases) {
		this.diseases = diseases;
	}

	public Map<String, DiseaseSymptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Map<String, DiseaseSymptom> symptoms) {
		this.symptoms = symptoms;
	}
	
	
}
