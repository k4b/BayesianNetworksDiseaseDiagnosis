package Ontology;

import datastructures.DiseaseTest;

public class RandGenerator {
	private static double DISEASE_MIN_P = 0.1; //divided by disease name lenght ^2
	private static double DISEASE_MAX_P = 0.5;//divided by disease name lenght ^ 2
	
	private static double SYNDROM_GIVEN_D_MIN_P = 0.7;
	private static double SYNDROM_GIVEN_D_MAX_P = 0.99;

	
	private static double SYNDROM_GIVEN_NOT_D_MIN_P = 0.01;
	private static double SYNDROM_GIVEN_NOT_D_MAX_P = 0.3;
	
	private static double TEST_POSITIVE_GIVEN_D_MIN_P = 0.9;
	private static double TEST_POSITIVE_GIVEN_D_MAX_P = 0.9999;

	
	private static double TEST_POSITIVE_GIVEN_NOT_D_MIN_P = 0.01;
	private static double TEST_POSITIVE_GIVEN_NOT_D_MAX_P = 0.1;
	

	public static double getDiseaseP(String string){
		return (DISEASE_MIN_P + Math.random()* (DISEASE_MAX_P-DISEASE_MIN_P)) / (string.length()*string.length());
	}
	
	public static double getPSgivenD(){
		return SYNDROM_GIVEN_D_MIN_P + Math.random()* (SYNDROM_GIVEN_D_MAX_P-SYNDROM_GIVEN_D_MIN_P);
	}
	
	public static double getPSgivenNotD(){
		return SYNDROM_GIVEN_NOT_D_MIN_P + Math.random()* (SYNDROM_GIVEN_NOT_D_MAX_P-SYNDROM_GIVEN_NOT_D_MIN_P);
	}
	
	public static double getPTestGivenD(){
		return TEST_POSITIVE_GIVEN_D_MIN_P + Math.random()* (TEST_POSITIVE_GIVEN_D_MAX_P-TEST_POSITIVE_GIVEN_D_MIN_P);
	}
	
	public static double getPTestGivenNotD(){
		return TEST_POSITIVE_GIVEN_NOT_D_MIN_P + Math.random()* (TEST_POSITIVE_GIVEN_NOT_D_MAX_P-TEST_POSITIVE_GIVEN_NOT_D_MIN_P);
	}

}
