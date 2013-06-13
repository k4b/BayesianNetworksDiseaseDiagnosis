/**
 * 
 */
package bayesianNetwork;


import bayesianNetwork.NetworkStructure;

import smile.*;

/**
 * @author Marysia
 *
 */
public class Inference {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		NetworkStructure ns = new NetworkStructure(); 
		InfereceWithBayesianNetwork();
	}
	
	
	public static void InfereceWithBayesianNetwork() {
		 try {
		   Network net = new Network();
		   net.readFile("tutorial_a.xdsl"); 
		   int outcomeIndex;
		   double[] aValues;
		       
		   net.setEvidence("Fever", "Yes");
		   
		   // Updating the network:
		   net.updateBeliefs();
		   
		   // Getting the handle of the node "Success":
		   net.getNode("Flu");
		   
		   // Getting the index of the "Failure" outcome:
		   String[] aSuccessOutcomeIds = net.getOutcomeIds("Flu");
		   for (outcomeIndex = 0; outcomeIndex < aSuccessOutcomeIds.length; outcomeIndex++)
		     if ("Yes".equals(aSuccessOutcomeIds[outcomeIndex]))
		       break;
		   
		   // Getting the value of the probability:
		   aValues = net.getNodeValue("Flu");
		   double P_FluYesGivenFeverYes = aValues[outcomeIndex];
		   
		   System.out.println("P(\"Flu\" = Yes | \"Fever\" = Yes) = " + P_FluYesGivenFeverYes);
		   
		   net.setEvidence("BackPain", "Yes");
		   
		   // Updating the network:
		   net.updateBeliefs();
		   
		   // Getting the handle of the node "Success":
		   net.getNode("Flu");
		   
		   // Getting the index of the "Failure" outcome:
		   aSuccessOutcomeIds = net.getOutcomeIds("Flu");
		   for (outcomeIndex = 0; outcomeIndex < aSuccessOutcomeIds.length; outcomeIndex++)
		     if ("Yes".equals(aSuccessOutcomeIds[outcomeIndex]))
		       break;
		   
		   // Getting the value of the probability:
		   aValues = net.getNodeValue("Flu");
		   double P_FluYesGivenBackPainYes = aValues[outcomeIndex];
		   
		   System.out.println("P(\"Flu\" = Yes | \"BackPain\" = Yes) = " + P_FluYesGivenBackPainYes);
		   
		  
		   
		   
		 }
		 catch (SMILEException e) {
		   System.out.println(e.getMessage());
		 }
		}

}
