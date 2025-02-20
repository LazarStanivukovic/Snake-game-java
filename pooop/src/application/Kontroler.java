package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Kontroler {
	public String formatPovratna(String povratna) {
        // Replace * with space and % with new line
        String formatted = povratna.replace("*", " ").replace("%", "\n");
        return formatted;
    }
	
	static {
	    System.loadLibrary("DLL1");
	}
	@FXML // menja SCENE 
	private void handleButtonAction(ActionEvent event) throws IOException
	{
		Stage stage;
		Parent root;
		if(event.getSource()==dalje) {
			stage = (Stage) dalje.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("drugi.fxml"));					
		}else if(event.getSource()==goback){
			stage = (Stage) goback.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
		}else if(event.getSource()==upitidugme){
			stage = (Stage) upitidugme.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("drugi.fxml"));
		}else if(event.getSource()==importdugme){
			stage = (Stage) importdugme.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
		}else if(event.getSource()==exportbaze){
			stage = (Stage) exportbaze.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("export.fxml"));
		}else {
			// ex dugme
			stage = (Stage) ex.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("export.fxml"));
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	private Button mojfiledugme;
	@FXML
	private Button importdugme;
	@FXML
	private Button upitidugme;
	@FXML
	private Button sqldugme;
	@FXML
	private Button exportbaze;
	@FXML
	private Button ex;	
	@FXML
	private Button uradiUpit;
	
	public static String selectupit;
	public static String order;
	@FXML 
	public void uradiupitfunk(ActionEvent event) throws IOException { 		
		System.out.println("--------------------------");
		System.out.println("Executing query: " + unosUpita.getText());	
		String unos = unosUpita.getText();
		System.out.println("UNOS JE: "+unos);
		if( unos.contains("ORDER")) {
			int n = unos.indexOf("ORDER");
			order = unos.substring(n);
			unos = unos.substring(0, n-1);
			System.out.println("OVO JE ORDER: "+order);
			System.out.println("OVO JE UNOS: " + unos);
		}
		
		String povratna = funkcija(unos);
		
		if(povratna.equals("-1")) {
			showErrorPopup("Error", "Failed to load the database.");
			return;
		}else if(povratna.equals("-2")) {
			showErrorPopup("Error", "POGRESAN UPIT.");
			return;
		}else if(povratna.equals("-3")) {
			showErrorPopup("Error", "Failed to export the database.");
			return;
		}else if(povratna.equals("1")) {
			showCheckPopup("BRAVO", "UPIT JE IZVRSEN.");
		}
		if (unos.startsWith("SELECT")) {
		    System.out.println(formatPovratna(povratna));
		    selectupit = formatPovratna(povratna);
		    Stage stage;
			Parent root;
			stage = (Stage) uradiUpit.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("tableview.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();									
		}
		if (unos.startsWith("SHOW")) {
			showCheckPopup(unos, povratna);
		}
		
		System.out.println("POVRATNA VREDNOST JE: "+povratna);
		
		
		// UCITAJ PONOVO BAZU
		
		MyFile.importfile(Main.datab, "MYfile.ls");
		
		
	}

	@FXML
	public TextField unosUpita;
	
	native static String funkcija(String s);
	
	@FXML
	private AnchorPane mainPane;
	//@FXML
	//public MenuItem ime;
	@FXML
	private Button dalje;
	@FXML
	private Button goback;
	
	@FXML
	protected void quit() {
		System.out.println("najace");
	}
	
	@FXML
	public void mojexportfunk(ActionEvent event) throws IOException {
		
		if( MyFile.exportFile(Main.datab, "proba.ls") == 1) {
			showCheckPopup("BRAVO", "Uspesno exportovana baza.");
		}else {
			showErrorPopup("Error", "Failed to export the database.");
		}	
	}
	
	@FXML
	public void sqlexportfunk(ActionEvent event) {
		try {
			SqlFile.exportfile(Main.datab);
			showCheckPopup("BRAVO", "Uspesno exportovana baza.");
		} catch (Exception e) {
			showErrorPopup("Error", "Failed to export the database.");
		}		
	}
	
	@FXML
    protected void napravinovubazu(ActionEvent event) throws IOException {
		Main.datab.getTables().clear();
		if( MyFile.exportFile(Main.datab, "MYfile.ls") == 1) {
			showCheckPopup("BRAVO", "Uspesno napravljena prazna baza.");
		}else {
			showErrorPopup("Error", "Failed to load the database.");
		}		
    }
	
	@FXML
	void ucitajbazu(ActionEvent event) throws IOException {
		try {
			MyFile.importfile(Main.datab, "MYfile.ls");
			showCheckPopup("BRAVO", "Uspesno ucitana baza.");
		} catch (Exception e) {
			showErrorPopup("Error", "Failed to load the database.");
		}		
    }
	
	private void showCheckPopup(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	private void showErrorPopup(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
}
