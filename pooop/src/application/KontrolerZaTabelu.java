package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class KontrolerZaTabelu implements Initializable{
	@FXML
	private TableView<RowData> tableView = new TableView<RowData>();
	@FXML
	private Button dugmeZaNazad;
	
	public static List<String> findFirstWords(String input) {
        List<String> firstWords = new ArrayList<>();
        
        // Split the input by new lines to get each row
        String[] rows = input.split("\\n");
        
        // Iterate through each row
        for (String row : rows) {
            // Split the row by spaces and get the first word
            String[] words = row.split("\\s+");
            if (words.length > 0) {
                firstWords.add(words[0]);
            }
        }
        
        return firstWords;
    }
	public static List<String> getRestOfLines(String input) {
        List<String> restOfLines = new ArrayList<>();
        
        // Split the input by new lines to get each row
        String[] rows = input.split("\\n");
        
        // Iterate through each row
        for (String row : rows) {
            // Split the row by spaces
            String[] words = row.split("\\s+");
            if (words.length > 1) {
                // Join the words from the second word to the end
                String restOfLine = String.join(" ", java.util.Arrays.copyOfRange(words, 1, words.length));
                restOfLines.add(restOfLine);
            } else {
                restOfLines.add("");  // In case there's only one word in the row, add an empty string
            }
        }
        
        return restOfLines;
    }
	
	
	@FXML // menja SCENE DA SE DODA ZA TABLEVIEW
	private void handleButtonAction(ActionEvent event) throws IOException
	{
		Stage stage;
		Parent root;
		
		stage = (Stage) dugmeZaNazad.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("drugi.fxml"));					
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public String[] splitorder() {
		String[] words = Kontroler.order.split("\\s+");
		System.out.println(words[2]);
		System.out.println(words[4]);
		System.out.println(words[6]);
		return words;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String temp = Kontroler.selectupit;
		List<String> firstWords = findFirstWords(temp);
		
		
		// NALAZI KOLONU ZA SORTIRANJE
		String[] w = splitorder();
		int kolona;
		for ( int i = 0 ; i<firstWords.size() ;i++ ) {
			if(firstWords.get(i).equals(w[2])) {
				kolona = i;
				break;
			}			
		}
		
		
		// Dynamically create table columns based on the first words
	    for (int i = 0; i < firstWords.size(); i++) {
	        final int colIndex = i;  // To use inside the lambda expression
	        TableColumn<RowData, String> tb = new TableColumn<>(firstWords.get(i));
	        
	        // Set up the cell value factory for each column
	        tb.setCellValueFactory(cellData -> {
	            if (colIndex < cellData.getValue().getColumnCount()) {
	                return cellData.getValue().columnProperty(colIndex);
	            } else {
	                return null;
	            }
	        });

	        tableView.getColumns().add(tb);
	    }

	    // Get the rest of the lines (row values)
	    List<String> restOfLines = getRestOfLines(temp);
	    
	    
	    // SORTIRA KOLONU I DRUGE PO NJOJ
	    for (int i = 0; i < restOfLines.size(); i++) {
	    	
	    }
	    
	    // Determine the number of rows needed by finding the longest column
	    int maxRows = 0;
	    List<List<String>> columns = new ArrayList<>();

	    for (String line : restOfLines) {
	        List<String> columnValues = Arrays.asList(line.split("\\s+"));
	        columns.add(columnValues);
	        maxRows = Math.max(maxRows, columnValues.size());
	    }

	    // Create data rows based on the number of rows needed (maxRows)
	    ObservableList<RowData> data = FXCollections.observableArrayList();

	    // Populate the rows with values from each column
	    for (int rowIndex = 0; rowIndex < maxRows; rowIndex++) {
	        List<String> rowValues = new ArrayList<>();

	        // Iterate over each column and get the value at the current rowIndex
	        for (List<String> column : columns) {
	            if (rowIndex < column.size()) {
	                rowValues.add(column.get(rowIndex));  // Add the value if it exists
	            } else {
	                rowValues.add("");  // Add an empty string if the column has no more values
	            }
	        }

	        data.add(new RowData(rowValues));  // Add the row to the data list
	    }

	    // Set the data into the table
	    tableView.setItems(data);
        
		
	}
}
