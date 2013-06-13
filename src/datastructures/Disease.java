/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datastructures;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Disease {

	 private String name;
	 private double diseaseProbability;
	 
	 private Map<DiseaseClue, DiseaseProbabilityBean> symtpoms; 
	 private Map<DiseaseClue, DiseaseProbabilityBean> tests;
	
	 
	 
	 @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(diseaseProbability);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((symtpoms == null) ? 0 : symtpoms.hashCode());
		result = prime * result + ((tests == null) ? 0 : tests.hashCode());
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
		if (Double.doubleToLongBits(diseaseProbability) != Double
				.doubleToLongBits(other.diseaseProbability))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (symtpoms == null) {
			if (other.symtpoms != null)
				return false;
		} else if (!symtpoms.equals(other.symtpoms))
			return false;
		if (tests == null) {
			if (other.tests != null)
				return false;
		} else if (!tests.equals(other.tests))
			return false;
		return true;
	}

	 
}
