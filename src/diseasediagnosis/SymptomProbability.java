package diseasediagnosis;

import diseasediagnosis.DiseaseSymptom;
import java.util.ArrayList;


public class SymptomProbability {
	
	private DiseaseSymptom symptom;
	private float[] probability = new float[4];
	//private bool isDiscovered = false;
	
	public SymptomProbability(String name, float p0, float p1, float p2, float p3)
	{
		symptom = new DiseaseSymptom(name);
		probability[0] = p0;
		probability[1] = p1;
		probability[2] = p2;
		probability[3] = p3;
	}
	

}
