package bayesianNetwork;


import smile.Network;
import smile.SMILEException;

public class NetworkStructure {



	public NetworkStructure()
	{
		CreateNetwork();
	}
	
	public void CreateNetwork() {
		 try {
		   Network net = new Network();
		   
		   // Creating node "Success" and setting/adding outcomes:
		   net.addNode(Network.NodeType.Cpt, "Flu");
		   net.setOutcomeId("Flu", 0, "Yes");
		   net.setOutcomeId("Flu", 1, "No");
		   
		   // Creating Symptoms nodes and setting/adding outcomes:
		   net.addNode(Network.NodeType.Cpt, "Fever");  
		   net.addOutcome("Fever", "Yes");
		   net.addOutcome("Fever", "No");
		   net.deleteOutcome("Fever", 0);
		   net.deleteOutcome("Fever", 0);
		   
		   net.addNode(Network.NodeType.Cpt, "BackPain");  
		   net.addOutcome("BackPain", "Yes");
		   net.addOutcome("BackPain", "No");
		   net.deleteOutcome("BackPain", 0);
		   net.deleteOutcome("BackPain", 0);
		   
		   // Adding arcs from "Success" to "Symptoms":
		   net.addArc("Flu", "Fever");
		   net.addArc("Flu", "BackPain");
		   
		   // P("Flu" = Yes) = 0.2
		   // P("Flu" = No) = 0.8
		   double[] aDiseaseChance = {0.2, 0.8};
		   net.setNodeDefinition("Flu", aDiseaseChance);
		   
		   // Filling in the conditional distribution for node "Fever". The probabilities are:
		   // P("Fever" = Yes | "Flu" = Yes) = 0.2
		   // P("Fever" = No | "Flu" = Yes) = 0.8
		   // P("Fever" = Yes | "Flu" = No) = 0.2
		   // P("Fever" = No | "Flu" = No) = 0.8
		   double[] aForecastDef = {0.2, 0.8, 0.2, 0.8}; 
		   net.setNodeDefinition("Fever", aForecastDef);
		   
		   // Filling in the conditional distribution for node "BackPain". The probabilities are:
		   // P("BackPain" = Yes | "Flu" = Yes) = 0.2
		   // P("BackPain" = No | "Flu" = Yes) = 0.8
		   // P("BackPain" = Yes | "Flu" = No) = 0.2
		   // P("BackPain" = No | "Flu" = No) = 0.8
		   double[] bForecastDef = {0.2, 0.8, 0.2, 0.8}; 
		   net.setNodeDefinition("BackPain", bForecastDef);
		   
		   		   		   
		   // Writting the network to a file:
		   net.writeFile("tutorial_a.xdsl");
		 }
		 catch (SMILEException e) {
		   System.out.println(e.getMessage());
		 }
		}
	
	public void CreateNetwork(int i) {
		 try {
		   Network net = new Network();
		   
		   // Creating node "Success" and setting/adding outcomes:
		   net.addNode(Network.NodeType.Cpt, "Success");
		   net.setOutcomeId("Success", 0, "Success");
		   net.setOutcomeId("Success", 1, "Failure");
		   
		   // Creating node "Forecast" and setting/adding outcomes:
		   net.addNode(Network.NodeType.Cpt, "Forecast");  
		   net.addOutcome("Forecast", "Good");
		   net.addOutcome("Forecast", "Moderate");
		   net.addOutcome("Forecast", "Poor");
		   net.deleteOutcome("Forecast", 0);
		   net.deleteOutcome("Forecast", 0);
		   
		   // Adding an arc from "Success" to "Forecast":
		   net.addArc("Success", "Forecast");
		   
		   // Filling in the conditional distribution for node "Success". The 
		   // probabilities are:
		   // P("Success" = Success) = 0.2
		   // P("Success" = Failure) = 0.8
		   double[] aSuccessDef = {0.2, 0.8};
		   net.setNodeDefinition("Success", aSuccessDef);
		   
		   // Filling in the conditional distribution for node "Forecast". The 
		   // probabilities are:
		   // P("Forecast" = Good | "Success" = Success) = 0.4
		   // P("Forecast" = Moderate | "Success" = Success) = 0.4
		   // P("Forecast" = Poor | "Success" = Success) = 0.2
		   // P("Forecast" = Good | "Success" = Failure) = 0.1
		   // P("Forecast" = Moderate | "Success" = Failure) = 0.3
		   // P("Forecast" = Poor | "Success" = Failure) = 0.6
		   double[] aForecastDef = {0.4, 0.4, 0.2, 0.1, 0.3, 0.6}; 
		   net.setNodeDefinition("Forecast", aForecastDef);
		   
		  		   net.setNodePosition("Forecast", 30, 100, 60, 30);
		   
		   // Writting the network to a file:
		   net.writeFile("tutorial_a.xdsl");
		 }
		 catch (SMILEException e) {
		   System.out.println(e.getMessage());
		 }
		}

}
