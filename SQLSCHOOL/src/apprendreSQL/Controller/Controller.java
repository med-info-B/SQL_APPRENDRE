package apprendreSQL.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import apprendreSQL.Model.analysisType.semantic.Machine;
import apprendreSQL.Model.analysisType.syntax.ParseException;
import apprendreSQL.Model.analysisType.syntax.ParserSQL;
import apprendreSQL.Model.data.BankQuestion;
import apprendreSQL.Model.data.Factory;
import apprendreSQL.Model.data.Question;
import apprendreSQL.View.Gui;
/**
 * 
 * @author equipe de dev
 * @version 2.0
 * cette class une unité intermidiare entre la vue et le model  
 * qui comme responsabilité de gérer le traitement logique
 *  
 *
 */
/**
 * 
 *
 */
public class Controller {

	/*
	 *  représente la vue de l'application
	 */
	private Gui view;
	private Integer index;
	/**
	 *  represente le parseur de l'application
	 */
	private ParserSQL parser;
	private InputStream stream;
	private Machine machine;
	private BankQuestion questions;
	/**
	 *   représente la partie semantique de l'application
	 * @throws org.json.simple.parser.ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
//	private Semantique semantique;
	
	public Controller() 
	{
		index = 0;
		try {
			init();
			view.sendMessage("*************************!!!!!!!   Hello !!!!! ***********************");
		} catch (IOException | org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 *   methode qui gère les évènements lors du clic sur les trois boutons (next,precedent, exécuter)
	 */
	private void eventManager() {
		/**
		 *   gestion d'evenement pour le bouton next 
		 */
		view.getButtonNext().addActionListener( e ->  nextQuestion());
		
		/**
		 *  gestion d'evenement pour le bouton précédent
		 */
		view.getButtonPrecedent().addActionListener(e ->  precedentQuestion() );
		
		/**
		 *  gestion d'evenement pour le bouton executer
		 */
		view.getButtonExu().addActionListener( e -> 
					{
						if(view.getText().length() != 0)
							start_analysis();
					}
			);
	}
	
	
	/**
	 *  methode lance l'analyse lexical, syntaxique et semantique repectivement l'ordre 
	 */
	private void start_analysis() {
		
		parser.ReInit(Factory.translateToStream(view.getText()));
    	  try {
			parser.sqlStmtList();
		    view.sendMessage("OK. Correcte syntaxique oui oui \n ");
		} catch (ParseException e1) {
			view.sendMessage(" Error : "+ e1.getMessage());
		}
	}
	
	/*
	 * Methode mis à jour l'attribut Question dans la class Gui lors du clic sur boutton Precedent
	 */
	private void precedentQuestion() {
		if(index >= 1){
			 index--;
			 view.setQuestion(questions.findQuestionByID(index));
		 }
	}
	/**
	 * Methode mis à jour l'attribut Question dans la class Gui lors du clic sur boutton Precedent
	 */
	private void nextQuestion() {
		 if(index < questions.size() - 1){
			 index++;
			 view.setQuestion(questions.findQuestionByID(index));
		 }
	}
	
	
	
	
	/**
	 *   Cette méthode appelle 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void DisplayView() {	
		view.display();
	}
	
	/**
	 * cette methode initialise toutes les attributs 
	 * @throws org.json.simple.parser.ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 * methode initialise tout les attributs de la class 
	 */
	private void init() throws FileNotFoundException, IOException, org.json.simple.parser.ParseException  {
		String path  = "/home/mohammed/Vidéos/SQLSCHOOL/src/apprendreSQL/Model/data/question.json";
		questions   = Factory.makeListQuestion(path);
		view        = Factory.makeView(this);
		this.parser = Factory.makeParserSQL();
		
		parser.registerObserver(machine);
		parser.setIndice("eleve");
		
		DisplayView();
		eventManager();
	}
	

	/**
	 * cette methode renvoie l'attribut view 
	 * @return renvoie l'attribut view 
	 */
	public Gui getView() {
		return view;
	}
	//******************************* developement ces fonctionnalité dans les prochaines version  ///////////////
   
	
	public void sendIsKeyWord(String word) {
		
	}




















	/**
	 * 
	 * @return question indéxé par id
	 */
	public Question getQuestions() {
		return questions.findQuestionByID(index);
	}

	
}
