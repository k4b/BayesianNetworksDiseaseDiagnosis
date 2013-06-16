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
    private Map<Disease, DiseaseProbabilityBean> probabilities = new HashMap<Disease, DiseaseProbabilityBean>(); 

    public DiseaseClue(String name) {
           this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public Map<Disease, DiseaseProbabilityBean> getProbabilities() {
        return probabilities;
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
		DiseaseClue other = (DiseaseClue) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	 
	public void addDisease(Disease disease, DiseaseProbabilityBean probability){
		probabilities.put(disease, probability);
	}
}
