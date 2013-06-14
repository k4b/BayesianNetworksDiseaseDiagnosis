package datastructures;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author czarek
 *		common class for symptoms and tests
 */
public class DiseaseClue {
	 private String name;
	 private Map<Disease, DiseaseProbabilityBean> probabilities; 

	 public DiseaseClue(String name) {
	        this.name = name;
	    }
	 
	 
}
