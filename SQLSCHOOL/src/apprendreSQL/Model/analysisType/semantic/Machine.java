package apprendreSQL.Model.analysisType.semantic;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import apprendreSQL.Controller.Protocol;
import apprendreSQL.Model.analysisType.syntax.ParseException;
import apprendreSQL.Model.analysisType.syntax.ParserSQL;

public class Machine implements Observers{
	
	private String requeste;
	private List<String> requesteELeve;
	private List<String> requesteProf;
	private String erreur;
	private Boolean isErreur;
	private ParserSQL parser;
	InputStream stream;
	private Protocol p;
	public Machine(String s) throws ParseException {
		requeste = s;
		requesteELeve = new ArrayList<>();
		requesteProf = new ArrayList<>();
		stream = new ByteArrayInputStream(requeste.getBytes());
		this.parser = new ParserSQL(stream);
	//	parser.register(this);
	//	parser.setPersonne("prof");
  	    parser.sqlStmtList();
		erreur = "";
		isErreur = false;
	}
	
	
	public void register(Protocol p) {
		this.p = p;
	}
	
	private void notifyEvenErreur(String erreur) {
		p.sendMessage(erreur);
	}
	
	public void reset() {
		for(int i = 0; i < requesteELeve.size() ; i ++)
			 requesteELeve.remove(i);
		for(int i = 0; i < requesteProf.size() ; i ++)
			 requesteProf.remove(i);
		erreur = "";
	}
	
	
	public String erreur() {
		System.out.println(erreur);
		return erreur;
	}
	
	
	
	public Boolean execute() {
		erreur = "Erreur :: \n il vous manque ces token dans votre requete : ";
		for(String token : requesteProf) {
			System.out.println("for"+ token);
			if(!requesteELeve.contains(token)) {
				erreur += token;
				erreur += "\n";
				isErreur = true;
			}
		}
        return isErreur;
	}
	
	@Override
	public void notifyEventEleve(String token) {
		System.out.println("test eleve   "+ token);
		requesteELeve.add(token);
	}
	@Override
	public void notifyEventProf(String token) {
		System.out.println("test prof   "+ token);
		requesteProf.add(token);
	}

}
