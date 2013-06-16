/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datastructures;


/**
 * 
 * @author czarek zawadka 
 */
public class DiseaseSymptom extends DiseaseClue {

	private String nonStemmedName;

	public DiseaseSymptom(String name) {
		super(name);
	}

	public String toString() {
		return super.toString();
	}

	public String getNonStemmedName() {
		return nonStemmedName;
	}

	public void setNonStemmedName(String nonStemmedName) {
		this.nonStemmedName = nonStemmedName;
	}

	
}
