package bayesianNetwork;

import java.util.List;

import smile.Network;
import smile.SMILEException;
import datastructures.Disease;

/**
 * 
 * @author maria
 * 
 */
public class NetworkStructure {

	public static String[] nodeDesc = { "disease", "symptom", "test" };
	public static final String YES = "Yes";
	public static final String NO = "No";

	public static double[] concat(double[] first, double[] second) {
		double[] result = new double[first.length + second.length];
		System.arraycopy(first, 0, result, 0, first.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public NetworkStructure() {
		CreateNetwork();
	}

	public void CreateNetwork() {
		try {
			Network net = new Network();

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

			net.addArc("Flu", "Sneezing");

			double[] cForecastDef = { 0.99, 0.01, 0.01, 0.99 };
			net.setNodeDefinition("Sneezing", cForecastDef);

			// Writting the network to a file:
			net.writeFile("tutorial_a.xdsl");
		} catch (SMILEException e) {
			System.out.println(e.getMessage());
		}
	}

	public void CreateNetwork(List<Disease> diseases) {
		try {
			Network net = new Network();

			for (Disease disease : diseases) {

				net.addNode(Network.NodeType.Cpt, disease.getName());
				net.setNodeDescription(disease.getName(), nodeDesc[0]);

				net.setOutcomeId(disease.getName(), 0, YES);
				net.setOutcomeId(disease.getName(), 1, NO);

				double[] aDiseaseChance = { disease.getDiseaseProbability(),
						1 - disease.getDiseaseProbability() };
				net.setNodeDefinition(disease.getName(), aDiseaseChance);

				// List<Value> list = new ArrayList<Value>(map.values());

			}

		} catch (SMILEException e) {
			System.out.println(e.getMessage());
		}
	}

}
