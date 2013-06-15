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
import datastructures.*;
import java.util.ArrayList;

/**
 *
 * @author Karol Abramczyk
 */
public class ChatBot implements ActionListener {
    
    private DiagnosisApp view;
    private DataModel data;
    private AnswerProcessor aProcessor;
    private State state = State.Idle;
    private SymptomsOccurence currentSymptomsOccurence;
    private ArrayList symptomsToAsk;
    
    public enum State { AskedGeneralQuestion, AskedSpecificQuestion, Testing, Idle };
    
    public ChatBot(DiagnosisApp view, DataModel data) {
        this.view = view;
        this.data = data;
        aProcessor = new AnswerProcessor(data);
    }
    
    public void invitation() {
        view.logln(">> Hello!. Tell me how do you feel?");
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
        }
    }
    
    private void processGeneralAnswer(SymptomsOccurence symptomsOccurence) {
        if(data.getSymptoms().size() > symptomsOccurence.size()) {
            Disease disease = generateSampleDisease();  //should be most probable disease found by bayesian network
            System.out.println(disease.tosString());
            symptomsToAsk = disease.getDiffSymptomNames(new ArrayList<>(symptomsOccurence.keySet()));
            System.out.println("Need to ask: " + symptomsToAsk.toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = view.getTextField().getText();
        view.logln(text);
//        logger.setForeground(Color.BLUE);
        view.getTextField().selectAll();
        currentSymptomsOccurence = analyzeInput(text);
        processConversation(currentSymptomsOccurence);        
    }
    
    private Disease generateSampleDisease() {
        Disease disease = new Disease("cold");
        disease.setDiseaseProbability(0.9);
        DiseaseSymptom s1 = new DiseaseSymptom("cough");
        DiseaseSymptom s2 = new DiseaseSymptom("sneezing");
        DiseaseProbabilityBean bean1 = new DiseaseProbabilityBean(disease, s1, 0.6, 0.3);
        DiseaseProbabilityBean bean2 = new DiseaseProbabilityBean(disease, s2, 0.8, 0.4);
        disease.getSymptoms().put(s1, bean1);
        disease.getSymptoms().put(s2, bean2);        
        return disease;
    }
    
    
    
}
