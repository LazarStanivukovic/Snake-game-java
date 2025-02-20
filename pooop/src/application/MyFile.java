package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class MyFile {
	public static int importfile(Database d, String filename) throws IOException {
		BufferedReader file = new BufferedReader(new FileReader(filename));
        if (file == null) {
            throw new IllegalArgumentException("Unable to open file for reading.");
        	//return -1;
        }

        // Clear existing tables in the database
        d.getTables().clear();

        // Read each table with its columns and rows
        String line;
        while ((line = file.readLine()) != null) {
            Scanner scanner = new Scanner(line);
            String tableName = scanner.next();
            Table newTable = new Table(tableName);

            while (scanner.hasNext()) {
                String columnName = scanner.next();
                Column newColumn = new Column(columnName);
                newTable.addColumn(newColumn);
            }

            d.addTable(newTable);

            // Read rows for each column
            for (Table table : d.getTables()) {
            	if( !(table.getTableName().equals(newTable.getTableName())) ) continue;
                for (Column column : table.getColumns()) {
                    line = file.readLine();
                    if (line != null) {
                        scanner = new Scanner(line);
                        while (scanner.hasNext()) {
                            String cell = scanner.next();
                            column.addRow(cell);
                        }
                    }
                }
            }
        }

        file.close();
        System.out.println("File imported successfully.");
        return 1;
	}
		
	public static int exportFile(Database d, String filename) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(filename));
        if (file == null) {
            System.err.println("Error: Unable to open file for writing.");
            return -1;
        }

        // Write each table with its columns and rows
        for (Table table : d.getTables()) {
            file.write(table.getTableName());
            for (Column column : table.getColumns()) {
                file.write(" " + column.getColumnName());
            }
            file.newLine();

            // Write rows for each column
            for (Column column : table.getColumns()) {
                for (String row : column.getRows()) {
                    file.write(row + " ");
                }
                file.newLine();
            }
        }

        file.close();
        System.out.println("File created and text written successfully.");
        return 1;
    }
}
