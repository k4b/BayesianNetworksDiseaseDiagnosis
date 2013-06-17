package bayesianNetwork;

import java.util.List;
import java.util.Map;

import smile.Network;
import smile.SMILEException;
import datastructures.Disease;
import datastructures.DiseaseSymptom;

/**
 * 
 * @author maria
 * 
 */
public class NetworkStructure {

	public static String[] nodeDesc = { "disease", "symptom", "test" };
	public static final String YES = "Yes";
	public static final String NO = "No";

	private Network network;
	
	public static double[] concat(double[] first, double[] second) {
		double[] result = new double[first.length + second.length];
		System.arraycopy(first, 0, result, 0, first.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public NetworkStructure() {
//		this.network = CreateNetwork();
//		Thread th=new Thread(new Runnable() {
//			  @Override
//			  public void run() {
//			    // This implements Runnable.run
//			  }
//		});
	}

	public /*synchronized*/ Network CreateNetwork() {
		Network net = new Network();

		try {

			net.addNode(Network.NodeType.Cpt, "Flu");
			net.setOutcomeId("Flu", 0, YES);
			net.setOutcomeId("Flu", 1, NO);

			double[] aDiseaseChance = { 0.5, 0.5 };
			net.setNodeDefinition("Flu", aDiseaseChance);

			net.addNode(Network.NodeType.Cpt, "Clap");
			net.setOutcomeId("Clap", 0, YES);
			net.setOutcomeId("Clap", 1, NO);

			double[] bDiseaseChance = { 0.5, 0.5 };
			net.setNodeDefinition("Clap", bDiseaseChance);

			net.addNode(Network.NodeType.Cpt, "Fever");
			net.addOutcome("Fever", YES);
			net.addOutcome("Fever", NO);
			net.deleteOutcome("Fever", 0);
			net.deleteOutcome("Fever", 0);
			net.addArc("Flu", "Fever");
			net.addArc("Clap", "Fever");

			double[] aForecastDef = { 0.98, 0.02, 0.99, 0.01, 0.2, 0.8, 0.01,
					0.99 };
			net.setNodeDefinition("Fever", aForecastDef);

			net.addNode(Network.NodeType.Cpt, "BackPain");
			net.addOutcome("BackPain", YES);
			net.addOutcome("BackPain", NO);
			net.deleteOutcome("BackPain", 0);
			net.deleteOutcome("BackPain", 0);

			net.addArc("Flu", "BackPain");

			double[] bForecastDef = { 0.99, 0.01, 0.01, 0.99 };
			net.setNodeDefinition("BackPain", bForecastDef);

			net.addNode(Network.NodeType.Cpt, "Sneezing");
			net.addOutcome("Sneezing", YES);
			net.addOutcome("Sneezing", NO);
			net.deleteOutcome("Sneezing", 0);
			net.deleteOutcome("Sneezing", 0);
		//	net.get
			net.addArc("Flu", "Sneezing");
			
			double[] cForecastDef = { 0.99, 0.01, 0.01, 0.99 };
			net.setNodeDefinition("Sneezing", cForecastDef);

			net.writeFile("tutorial_a.xdsl");
		} catch (SMILEException e) {
			System.out.println(e.getMessage());
		}
		return network;
	}

	public void CreateNetwork(List<Disease> diseases) {
		try {
			net = new Network();

			for (Disease disease : diseases) {
				
				int position = setNodeProperties(0);
				net.setNodeName(position, disease.getName());

				double[] aDiseaseChance = { disease.getDiseaseProbability(),
						1 - disease.getDiseaseProbability() };
				net.setNodeDefinition(disease.getName(), aDiseaseChance);

				// this section sets symptoms parameters
				Map<DiseaseClue, DiseaseProbabilityBean> symptoms = disease
						.getSymptoms();
				Iterator<Entry<DiseaseClue, DiseaseProbabilityBean>> entries = symptoms
						.entrySet().iterator();

				while (entries.hasNext()) {
					Entry<DiseaseClue, DiseaseProbabilityBean> entry = entries
							.next();
					
					position = setNodeProperties(1);
					registerSymptomNodes(entry.getKey().getName(), position);
					net.addArc(net.getNode(disease.getName()), position);

					// TODO set proabilities
				}

				// this section sets tests parameters
				Map<DiseaseClue, DiseaseProbabilityBean> tests = disease
						.getTests();
				entries = tests.entrySet().iterator();

				while (entries.hasNext()) {
					Entry<DiseaseClue, DiseaseProbabilityBean> entry = entries
							.next();
								
					position = setNodeProperties(2);
					net.setNodeName(position, entry.getKey().getName());

					net.addArc(disease.getName(), entry.getKey().getName());

					// TODO set proabilities
				}
			}

		} catch (SMILEException e) {
			System.out.println(e.getMessage());
		}
	}

	public Network CreateNetwork(Map<String, Disease> diseases,
			Map<String, DiseaseSymptom> symptoms) {

		Network net = new Network();

		
		net.writeFile("inference.xdsl");

		return network;

	}
	
	
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
			List<Integer> temp = new ArrayList<Integer>();
			temp.add(position);
			symptomNodes.put(name, temp);
		}
	}

}
