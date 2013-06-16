/**
 * 
 */
package bayesianNetwork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import chatBot.SymptomsOccurence;

import smile.Network;
import smile.SMILEException;
import datastructures.Disease;
import datastructures.DiseaseSymptom;
import datastructures.DiseaseTest;

/**
 * @author maria & czarek ;)
 * 
 */
public class InferenceEngine {

	/**
	 * @param args
	 */

	private HashSet<Pair<String, String>> observed; // a list of clues observed for
													// particular case
	private final String YES = NetworkStructure.YES;
	private final String NO = NetworkStructure.NO;
	private Network net;
	private List<String> diseases; // alternatively - list of Diseases
//	private final String networkFileName = "tutorial_a.xdsl";
	private String mostProbableDisease;

//	public static void main(String[] args) {
//
//		// for test/presentation purposes
//		new NetworkStructure();
//		InfereceWithBayesianNetwork();
//	}
//
//	public InferenceEngine() {
//		loadNetworkFromFile();
//		observed = new ArrayList<Pair<String, String>>();
//		mostProbableDisease = null;
//
//	}
//
//	private void loadNetworkFromFile() {
//		try {
//			net = new Network();
////			net.readFile(networkFileName);
//		} catch (SMILEException e) {
//			System.out.println(e.getMessage());
//		}
//	}
	
	public InferenceEngine(Map <String, Disease> diseases ,  Map <String, DiseaseSymptom> symptoms) {
		NetworkStructure networkStructure = new NetworkStructure();
		net = networkStructure.CreateNetwork(diseases ,  symptoms);
		observed = new HashSet<Pair<String, String>>();
		mostProbableDisease = null;
	}
	
	public void invokeInference(SymptomsOccurence symptoms){
		Iterator<Entry<String, Boolean>>  it = symptoms.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Boolean>  pair = it.next();
			addEvidence(pair.getKey(), pair.getValue());
		}
		runInference();
	}

	public void addEvidence(String clueName, boolean isPositive // TRUE if clue occurs
																// FALSE if doesn't
	) {
		this.observed.add(new Pair<String, String>(clueName, isPositive ? YES : NO));

	}

	public String findMostLikelyDisease() {
		return mostProbableDisease;
	}

	public DiseaseTest findMostSuitableTest() {
		// TODO VoI implementation
		return null;
	}

	private void runInference() {
		net.clearAllEvidence();
		double maxProbability = 0;
		try {
			for (Pair<String, String> observation : observed) {
				net.setEvidence(observation.getLeft(), observation.getRight());
			}

			net.updateBeliefs();

			for (String disease : diseases) {
				net.getNode(disease);
				double probab = net.getNodeValue(disease)[0];
				if (probab > maxProbability) {
					maxProbability = probab;
					this.mostProbableDisease = disease;
				}
			}

		} catch (SMILEException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void InfereceWithBayesianNetwork() { // TEST METHOD
		try {
			Network net_test = new Network();
			net_test.readFile("tutorial_a.xdsl");

			double[] aValues;

			net_test.setEvidence("Fever", "Yes");

			net_test.updateBeliefs();

			net_test.getNode("Flu");
			aValues = net_test.getNodeValue("Flu");

			System.out.println("Fever yes");
			System.out.println("P(Influenza=T|evidence)= " + aValues[0]);
			System.out.println("P(Influenza=F|evidence)= " + aValues[1]);

			net_test.clearAllEvidence();

			net_test.setEvidence("Fever", "Yes");
			// net.setEvidence("BackPain", "Yes");
			// Updating the network:
			net_test.updateBeliefs();

			// Getting the handle of the node "Success":
			net_test.getNode("Clap");
			aValues = net_test.getNodeValue("Clap");

			System.out.println("Fever yes");
			System.out.println("P(Clap=T|evidence)= " + aValues[0]);
			System.out.println("P(Clap=F|evidence)= " + aValues[1]);

			net_test.clearAllEvidence();

			net_test.setEvidence("Fever", "No");
			// net.setEvidence("BackPain", "Yes");
			// Updating the network:
			net_test.updateBeliefs();

			// Getting the handle of the node "Success":
			net_test.getNode("Flu");
			aValues = net_test.getNodeValue("Flu");

			System.out.println("Fever no");
			System.out.println("P(Influenza=T|evidence)= " + aValues[0]);
			System.out.println("P(Influenza=F|evidence)= " + aValues[1]);

			net_test.clearAllEvidence();

			net_test.setEvidence("Fever", "No");
			// net.setEvidence("BackPain", "Yes");
			// Updating the network:
			net_test.updateBeliefs();

			// Getting the handle of the node "Success":
			net_test.getNode("Clap");
			aValues = net_test.getNodeValue("Clap");

			System.out.println("Fever no");
			System.out.println("P(Clap=T|evidence)= " + aValues[0]);
			System.out.println("P(Clap=F|evidence)= " + aValues[1]);

			net_test.clearAllEvidence();

			/*
			 * net.setEvidence("BackPain", "No"); //net.setEvidence("BackPain",
			 * "Yes"); // Updating the network: net.updateBeliefs();
			 * 
			 * net.getNode("Flu"); aValues = net.getNodeValue("Flu");
			 * System.out.println("BackPain no");
			 * System.out.println("P(Influenza=T|evidence)= " + aValues[0]);
			 * System.out.println("P(Influenza=F|evidence)= " + aValues[1]);
			 * 
			 * net.clearAllEvidence();
			 * 
			 * net.setEvidence("Sneezing", "Yes"); //net.setEvidence("BackPain",
			 * "Yes"); // Updating the network: net.updateBeliefs();
			 * 
			 * net.getNode("Flu"); aValues = net.getNodeValue("Flu");
			 * System.out.println("Sneezing yes");
			 * System.out.println("P(Influenza=T|evidence)= " + aValues[0]);
			 * System.out.println("P(Influenza=F|evidence)= " + aValues[1]);
			 * 
			 * net.clearAllEvidence();
			 * 
			 * net.setEvidence("Fever", "Yes"); net.setEvidence("BackPain",
			 * "Yes"); net.setEvidence("Sneezing", "Yes"); // Updating the
			 * network: net.updateBeliefs();
			 * 
			 * net.getNode("Flu"); aValues = net.getNodeValue("Flu");
			 * 
			 * System.out.println("fever yes back pain no sneezing Yes");
			 * System.out.println("P(Influenza=T|evidence)= " + aValues[0]);
			 * System.out.println("P(Influenza=F|evidence)= " + aValues[1]);
			 */

		} catch (SMILEException e) {
			System.out.println(e.getMessage());
		}
	}

	public class Pair<L, R> {

		private final L left;
		private final R right;

		public Pair(L left, R right) {
			this.left = left;
			this.right = right;
		}

		public L getLeft() {
			return left;
		}

		public R getRight() {
			return right;
		}
	}
}
