/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatBot;

import diseasediagnosis.DataModel;
import diseasediagnosis.DiagnosisApp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.queryparser.classic.ParseException;

import bayesianNetwork.InferenceEngine;
import datastructures.*;
import java.util.ArrayList;
import sun.security.util.DisabledAlgorithmConstraints;

/**
 *
 * @author Karol Abramczyk
 */
public class ChatBot implements ActionListener {
    
    private DiagnosisApp view;
    private DataModel data;
    private AnswerProcessor aProcessor;
    private State state = State.Idle;
    private SymptomsOccurence totalSymptomsOccurence;
    private ArrayList symptomsToAsk;
    private Disease lastDisease;
    
  //  private InferenceEngine engine;
    
    public enum State { AskedGeneralQuestion, AskedSpecificQuestion, Testing, Idle };
    
    public ChatBot(DiagnosisApp view, DataModel data /*, InferenceEngine engine*/) {
        this.view = view;
        this.data = data;
        aProcessor = new AnswerProcessor(data);
    //    this.engine = engine;
    }
    
    public void invitation() {
        view.logln(">> Hello! Tell me what ails you?");
        state = State.AskedGeneralQuestion;
    }
    
    public SymptomsOccurence analyzeInput(String text) {
        SymptomsOccurence foundSymptoms = null;
        try {
            foundSymptoms = aProcessor.searchSymptoms(text);
        } catch (IOException ex) {
            Logger.getLogger(DiagnosisApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DiagnosisApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return foundSymptoms;
        }
    }
    
    private void processConversation(SymptomsOccurence symptomsOccurence) {
        switch(state) {
            case AskedGeneralQuestion :
                processGeneralAnswer(symptomsOccurence);
                break;
            case AskedSpecificQuestion :
            	
                processSpecificAnswer(symptomsOccurence);
                break;
            case Idle :
                processIdleAnswer(symptomsOccurence);
                break;
        }
    }
    
    private void processGeneralAnswer(SymptomsOccurence symptomsOccurence) {
        totalSymptomsOccurence = symptomsOccurence;
        if(data.getSymptoms().size() > symptomsOccurence.size()) {
            Disease disease = generateSampleDisease();  //should be most probable disease found by bayesian network
            lastDisease = disease;
            System.out.println(disease.toString());
            symptomsToAsk = disease.getDiffSymptomNames(new ArrayList<>(totalSymptomsOccurence.keySet()));
            System.out.println("Need to ask: " + symptomsToAsk.toString());
            if(symptomsToAsk != null && symptomsToAsk.size() > 0) {
                askForLackingSymptom(disease, true);
            } 
            else{
            	//ORDER TEST OR finish
            }
        }
    }
    
    private void askForLackingSymptom(Disease disease, boolean isFirstCall) {
        String word = "";
        if(isFirstCall == false && disease != null && disease.getName().equals(lastDisease.getName())) {
            word = "still ";
        }
        view.logln(">> It " + word + "looks that you have " + disease.getName() + ". But tell me how about " + symptomsToAsk.get(0) + "?");
        state = State.AskedSpecificQuestion;
    }
    
    private void processSpecificAnswer(SymptomsOccurence symptomsOccurence) {
        if(symptomsOccurence.size() == 0){
            view.logln(">> Please answer in complete sentence.");
            System.out.println("Need to ask: " + symptomsToAsk.toString());
            askForLackingSymptom(lastDisease, false);
        } else {
            symptomsToAsk.removeAll(totalSymptomsOccurence.keySet());
            totalSymptomsOccurence.putAll(symptomsOccurence);
            Disease disease = generateSampleDisease();  //should be most probable disease found by bayesian network
            lastDisease = disease;
            symptomsToAsk = disease.getDiffSymptomNames(new ArrayList<>(totalSymptomsOccurence.keySet()));
            System.out.println("Need to ask: " + symptomsToAsk.toString());
            if(symptomsToAsk.size() > 0) {
                askForLackingSymptom(disease, false);
            } else {
                state = State.Idle;
                view.logln(">> Now I'm sure - you have " + disease.getName() + "!");
                System.out.println("All symptoms: " + totalSymptomsOccurence.toString());
            }
        }
    }
    
    private void processIdleAnswer(SymptomsOccurence symptomsOccurence) {
        view.logln(">> As I told you, most probably you have " + lastDisease.getName() + ". Now let me have a brake.");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = view.getTextField().getText();
        view.logln(text);
//        logger.setForeground(Color.BLUE);
        view.getTextField().selectAll();
        SymptomsOccurence symptoms = analyzeInput(text);
        processConversation(symptoms);        
    }
    
    private Disease generateSampleDisease() {
        Disease disease = new Disease("cold");
        disease.setDiseaseProbability(0.9);
        DiseaseSymptom s1 = new DiseaseSymptom("cough");
        DiseaseSymptom s2 = new DiseaseSymptom("sneezing");
        DiseaseSymptom s3 = new DiseaseSymptom("vomiting");
        DiseaseProbabilityBean bean1 = new DiseaseProbabilityBean(disease, s1, 0.6, 0.3);
        DiseaseProbabilityBean bean2 = new DiseaseProbabilityBean(disease, s2, 0.8, 0.4);
        DiseaseProbabilityBean bean3 = new DiseaseProbabilityBean(disease, s3, 0.2, 0.3);
        disease.getSymptoms().put(s1, bean1);
        disease.getSymptoms().put(s2, bean2);    
        disease.getSymptoms().put(s3, bean3);
        return disease;
    }
    
    
    
}
