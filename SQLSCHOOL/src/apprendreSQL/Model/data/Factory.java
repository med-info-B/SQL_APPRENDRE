package apprendreSQL.Model.data;

import static java.util.stream.Collectors.toList;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import apprendreSQL.Controller.Controller;
import apprendreSQL.Model.analysisType.syntax.ParserSQL;
import apprendreSQL.View.Gui;

public class Factory  {
	private static JSONParser parser;
	private static BankQuestion bankQuestion;
	
	
	/**
	 *   Methode qui constuite une liste d'objet Quesiton
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static BankQuestion makeListQuestion(String path) throws FileNotFoundException, IOException, ParseException{
	
		return 		loadQuestionFromJsonFile(path);
	}
	
	/**
	 * lecture d'un fichier json avec streaming et de créer fur et à mesure un objet question et de l'ajouter dans 
	 *  l objet banqueQuesiton qui gère ce dernier
	 * @param s
	 * @return  
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */   
	@SuppressWarnings("unchecked")
	private static BankQuestion loadQuestionFromJsonFile(String path) throws FileNotFoundException, IOException, ParseException {
		init();	
		try(Reader is = new FileReader(path))
		{
			JSONArray jsonArray = (JSONArray) parser.parse(is); 
			jsonArray.stream()
			         .forEach( e -> 
			         {	
			        	 Question qu = new Question();
			        	 JSONObject jsonObject = (JSONObject) e;
			        	 jsonObject.forEach( (key, value) ->
			        			 {
			        				 if(key.equals("id")){
			        					 qu.setId(String.valueOf(value));
			        				 } 
			        				 else if(key.equals("question")) {
			        					 qu.setQuestion(String.valueOf(value));
			        				 }
			        				 else if(key.equals("reponses")) {
			        					 JSONArray l = (JSONArray) value;
			        					 List<String> reponses = (List<String>) l.stream().collect(toList());
			        					 qu.setReponses(reponses);
			        				 }
			        			 }
			             );
			        		bankQuestion.addQuestion(qu);	 
			         }  
			);
		}catch(IOException | ParseException e) {
			System.out.println(e.getMessage());
		}
		return bankQuestion;
	}
	
	/*
	 *  initialisation des variables 
	 */
	private static void init() throws FileNotFoundException, IOException, ParseException {
		 parser = new JSONParser();
		 bankQuestion = new BankQuestion();
	}
	
	
	
	
	
	
	/**
	 *    
	 * @return 
	 */
	public static ParserSQL makeParserSQL() {
		return  new ParserSQL(new ByteArrayInputStream("".getBytes()));
	}
	
	public static InputStream translateToStream(String str) {
		return  new ByteArrayInputStream(str.getBytes());
	}
	
	public static Gui makeView(Controller controller)
	{
		return new Gui(controller);
	}
	
	public static <T> List<T> makeListQuestions(){
		return new ArrayList<T>();
	}
	
	public static JLabel makeJLabel(String title){
		return new JLabel(title);
	}
	public static Button makeButton(String title) {
		return new Button(title);
	}
	public static  JPanel makePanel() {
		return new JPanel();
	}
	
	public static Dimension makeDimension(int x, int y) {
		return new Dimension(x, y);
	}
	
	public static JTextPane makeJTextPane() {
		return new JTextPane();
	}
	
	
	public static Color makeColor(int r, int b, int v ) {
		return new Color(r,b,v);
	}
	
	public static FlowLayout makeFlowLayout() {
		return new FlowLayout();
	}
	
	public static BorderLayout makeBorderLayout() {
		return new BorderLayout();
	}
}
