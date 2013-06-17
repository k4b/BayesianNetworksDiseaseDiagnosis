package bayesianNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import smile.Network;
import smile.SMILEException;
import datastructures.Disease;
import datastructures.DiseaseClue;
import datastructures.DiseaseProbabilityBean;
import datastructures.DiseaseSymptom;
import datastructures.DiseaseTest;

/**
 * 
 * @author maria
 * 
 */
public class NetworkStructure {

	public static String[] nodeDesc = { "disease", "symptom", "test" };
	public static final String YES = "Yes";
	public static final String NO = "No";
	private HashMap<String, ArrayList<Integer>>symptomNodes;
	private HashMap<String, Integer>diseaseNodes;
	private HashMap<String, Integer>testNodes;

	private Network net;
	private final String NETWORK_FILENAME = "nference.xdsl";
	private static final int SYMPTOM_NOTES_INIT_SIZE = 800;
	private static final int DISIEASE_NOTES_INIT_SIZE = 400;
	private static final int TEST_NOTES_INIT_SIZE = 1200;
	
	public static double[] concat(double[] first, double[] second) {
		double[] result = new double[first.length + second.length];
		System.arraycopy(first, 0, result, 0, first.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public NetworkStructure() {
		super();
//		this.network = CreateNetwork();
//		Thread th=new Thread(new Runnable() {
//			  @Override
//			  public void run() {
//			    // This implements Runnable.run
//			  }
//		});
		
		symptomNodes = new  HashMap<String, ArrayList<Integer>>(SYMPTOM_NOTES_INIT_SIZE);
		diseaseNodes = new  HashMap<String, Integer>(DISIEASE_NOTES_INIT_SIZE);
		testNodes = new  HashMap<String, Integer>(TEST_NOTES_INIT_SIZE);
	}

	


	public Network CreateNetwork(Map <String, Disease> diseases) {
		net = new Network();
		try {

			Iterator<Entry<String, Disease>> mapEntries = diseases.entrySet().iterator();

			while (mapEntries.hasNext()) {
				Disease disease = mapEntries.next().getValue();


				//for (Disease disease : diseases) {

				int diseaseNodePosition = setNodeProperties(0);
				net.setNodeName(diseaseNodePosition, disease.getName());

				double[] aDiseaseChance = { disease.getDiseaseProbability(),
						1 - disease.getDiseaseProbability() };
				//net.setNodeDefinition(disease.getName(), aDiseaseChance);
				net.setNodeDefinition(diseaseNodePosition, aDiseaseChance);
				diseaseNodes.put( disease.getName(),diseaseNodePosition );

				//TODO extract common method for creating test and symptom nodes
				// this section sets symptoms parameters
				Map<DiseaseSymptom, DiseaseProbabilityBean> symptoms = disease
						.getSymptoms();
				Iterator<Entry<DiseaseSymptom, DiseaseProbabilityBean>> entries = symptoms
						.entrySet().iterator();

				while (entries.hasNext()) {
					Entry<DiseaseSymptom, DiseaseProbabilityBean> entry = entries
							.next();

					int symptomNodePosition = setNodeProperties(1);
					registerSymptomNodes(entry.getKey().getName(), symptomNodePosition);
				
					net.addArc(diseaseNodePosition, symptomNodePosition);
				
					DiseaseProbabilityBean symptomProbability =  entry.getValue();

					double[] symptomChance = {symptomProbability.getpSgivenD(), 1- symptomProbability.getpSgivenD(),
								symptomProbability.getpSgivenNotD(), 1- symptomProbability.getpSgivenNotD()};
					net.setNodeDefinition(symptomNodePosition, symptomChance);

				}

				// this section sets tests parameters
				Map<DiseaseTest, DiseaseProbabilityBean> tests = disease
						.getTests();
				Iterator<Entry<DiseaseTest, DiseaseProbabilityBean>> entries2 = tests.entrySet().iterator();

				while (entries.hasNext()) {
					Entry<DiseaseTest, DiseaseProbabilityBean> entry = entries2
							.next();

					int testNodePosition = setNodeProperties(2);
					net.setNodeName(testNodePosition, entry.getKey().getName());

					net.addArc(diseaseNodePosition, testNodePosition);

					DiseaseProbabilityBean testProbability =  entry.getValue();

					double[] testChance = {testProbability.getpSgivenD(), 1- testProbability.getpSgivenD(),
								testProbability.getpSgivenNotD(), 1- testProbability.getpSgivenNotD()};
					net.setNodeDefinition(testNodePosition, testChance);
					testNodes.put( entry.getKey().getName(),testNodePosition );
				}
			}

		} catch (SMILEException e) {
			System.out.println(e.getMessage());
		}
		net.writeFile(NETWORK_FILENAME);

		return net;
	}

//	public Network CreateNetwork(Map<String, Disease> diseases,
//			Map<String, DiseaseSymptom> symptoms) {
//
//		Network network = new Network();
//
//		
//		net.writeFile("inference.xdsl");
//
//		return net;
//		
//	}
	
	
	
	private int setNodeProperties(int description)
	{
		int position = net.addNode(Network.NodeType.Cpt);
		net.setOutcomeId(position, 0, YES);
		net.setOutcomeId(position, 1, NO);
		net.setNodeDescription(position, nodeDesc[description]);
		
		return position;
		
	}
	
	private void registerSymptomNodes(String name, int position) {

		if (null != symptomNodes.get(name)) {
			symptomNodes.get(name).add(position);
		} else {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(position);
			symptomNodes.put(name, temp);
		}
	}
	
	public ArrayList<Integer> resolveClueToNode(String clueName) {
		if (null != testNodes.get(clueName)) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(testNodes.get(clueName));
			return temp;
		}
		if (null != symptomNodes.get(clueName)) {
			return symptomNodes.get(clueName);
		}
		return null;
	}

	public Network getNet() {
		return net;
	}

	public void setNet(Network net) {
		this.net = net;
	}

	
}
