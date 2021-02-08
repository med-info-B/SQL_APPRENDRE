package AppTest;


import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import apprendreSQL.Controller.Controller;

public class App {

	public static void main(String[] args) {
		
		Controller controller;
		controller = new Controller();
		controller.getView().sendMessage("Connexion \n reussi ...");


	}
	

}
