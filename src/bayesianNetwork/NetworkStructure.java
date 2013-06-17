package bayesianNetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import smile.Network;
import smile.SMILEException;
import datastructures.Disease;
import datastructures.DiseaseClue;
import datastructures.DiseaseProbabilityBean;
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

	private Network net;
	private Map<String, List<Integer>> symptomNodes;
	
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

		Network network = new Network();

		
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
