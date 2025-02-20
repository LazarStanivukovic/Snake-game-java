package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SqlFile {
	public static int exportfile(Database d) throws IOException {
		BufferedWriter outFile = new BufferedWriter(new FileWriter("exbase.sql"));

        // Check if the file is opened successfully
        if (outFile == null) {
            throw new IllegalArgumentException("Error opening file!");
        }

        // Writing CREATE statements
        for (int i = 0; i < d.getTablessize(); i++) {
            StringBuilder createStatement = new StringBuilder("CREATE TABLE ");
            String tableName = d.getTables().get(i).getTableName();
            createStatement.append(tableName).append(" (");

            for (int j = 0; j < d.getTables().get(i).getColumns().size(); j++) {
                createStatement.append(d.getTables().get(i).getColumns().get(j).getColumnName());
                if (j != d.getTables().get(i).getColumns().size() - 1) {
                    createStatement.append(", ");
                }
            }
            createStatement.append(");");
            outFile.write(createStatement.toString());
            outFile.newLine();
        }

        // Writing INSERT statements
        for (int i = 0; i < d.getTablessize(); i++) {
            StringBuilder insertStatement = new StringBuilder("INSERT INTO ");
            insertStatement.append(d.getTables().get(i).getTableName()).append(" (");

            StringBuilder columnsString = new StringBuilder();
            int maxRow = 0;

            for (int j = 0; j < d.getTables().get(i).getColumns().size(); j++) {
                maxRow = Math.max(maxRow, d.getTables().get(i).getColumns().get(j).getRowSize());
                columnsString.append(d.getTables().get(i).getColumns().get(j).getColumnName());
                if (j != d.getTables().get(i).getColumns().size() - 1) {
                    columnsString.append(", ");
                }
            }

            insertStatement.append(columnsString).append(") VALUES (");

            for (int t = 0; t < maxRow; t++) {
                StringBuilder rowsString = new StringBuilder();

                for (int j = 0; j < d.getTables().get(i).getColumns().size(); j++) {
                    if (t < d.getTables().get(i).getColumns().get(j).getRowSize()) {
                        rowsString.append(d.getTables().get(i).getColumns().get(j).getRows().get(t));
                    }
                    if (j != d.getTables().get(i).getColumns().size() - 1) {
                        rowsString.append(", ");
                    }
                }

                String fullInsertStatement = insertStatement.toString() + rowsString.toString() + ");";
                outFile.write(fullInsertStatement);
                outFile.newLine();
            }
        }

        // Close the file
        outFile.close();

        System.out.println("File created and text written successfully.");
        return 0;
		
	}
}
