/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatBot;

import diseasediagnosis.DataModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.PorterStemmer;
//import org.apache.lucene.analysis.synonym.* ;
/**
 *
 * @author Karol Abramczyk
 */
public class AnswerProcessor {
    
    public static final String SYMPTOM = "SYMPTOM";
    public static final String STEM = "STEM";
    StandardAnalyzer analyzer;
    DataModel datamodel;
    ArrayList<String> symptomNames;
    Directory index;
    
    public AnswerProcessor(){
    	
    }
    
    public AnswerProcessor(DataModel datamodel) {
        this.datamodel = datamodel;
        symptomNames = new ArrayList<>(datamodel.getSymptoms().keySet());
        try {
            prepareAnalyzer();
        } catch (IOException ex) {
            Logger.getLogger(AnswerProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void prepareAnalyzer() throws IOException {
        // 0. Specify the analyzer for tokenizing text.
        //    The same analyzer should be used for indexing and searching
        analyzer = new StandardAnalyzer(Version.LUCENE_40);

        // 1. create the index
        index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);

        IndexWriter w = new IndexWriter(index, config);
        
//        //indexing symptom names
//        for(int i=0; i<symptomNames.size(); i++) {
//            System.out.println(symptomNames.get(i));
//            addDoc(w, symptomNames.get(i));
//        }
        //indexing symptom names' stems
        for(int i=0; i<symptomNames.size(); i++) {
            String stem = stemTerm(symptomNames.get(i));
            addDoc(w, stem, symptomNames.get(i));
        }
        w.close();

    }
    
    public SymptomsOccurence searchSymptoms(String text) throws IOException, ParseException {
        text = text.toLowerCase();
        ArrayList stems = procesAnswer(text);

        // 2. query
        String querystr = "";
        for(int j=0;j<stems.size();j++) {
            querystr += stems.get(j) + " ";

        }
        querystr = querystr.trim();
        System.out.println("Query string: " + querystr);

        // the "SYMPTOM" arg specifies the default field to use
        // when no field is explicitly specified in the query.
        Query q = new QueryParser(Version.LUCENE_40, STEM, analyzer).parse(querystr);

        // 3. searchSymptoms
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        // 4. display results        
        SymptomsOccurence results = new SymptomsOccurence();
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            
            results.put(d.get(SYMPTOM), new Boolean(true));
        }
        
        System.out.println("Found " + hits.length + " symptoms:");
        System.out.println(results.toString());
        
        // reader can only be closed when there
        // is no need to access the documents any more.
//        reader.close();
        return results;
    }
    
    private static void addDoc(IndexWriter w, String symptomStem, String symptom) throws IOException {
        Document doc = new Document();
        doc.add(new TextField(STEM, symptomStem, Field.Store.YES));

        // use a string field for isbn because we don't want it tokenized
        doc.add(new StringField(SYMPTOM, symptom, Field.Store.YES));
        w.addDocument(doc);
    }
    
    public ArrayList<String> procesAnswer(String text) {
        text = text.replaceAll(",", "");
        text = text.replaceAll("\\.", "");
        
        ArrayList<String> stems = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(text, " ");
        while(tokenizer.hasMoreTokens()) {
            String term = tokenizer.nextToken();
            String stem = stemTerm(term);
            stems.add(stem);
        }
        return stems;
    }
    
    private String stemTerm(String term) {
        PorterStemmer stemmer = new PorterStemmer();
        stemmer.setCurrent(term);
        stemmer.stem();
        return stemmer.getCurrent();
    }
    
    public String stemSentence(String sentence){
    	ArrayList<String> stmmedArray = procesAnswer(sentence);
    	StringBuffer stringBuffer = new StringBuffer();
    	for (String string : stmmedArray) {
    		stringBuffer.append(string);
    		stringBuffer.append(" ");   		
		}
    	
    	String concatenatedSentence = stringBuffer.toString();
    	
    	if(concatenatedSentence.startsWith(" ")){
    		concatenatedSentence = concatenatedSentence.substring(1);
    	}

    	if(concatenatedSentence.endsWith(" ")){
    		concatenatedSentence = concatenatedSentence.substring(0, concatenatedSentence.length()-1);
    	}
    	
    	return concatenatedSentence.toLowerCase();
    }
}
