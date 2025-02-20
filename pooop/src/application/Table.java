package application;

import java.util.ArrayList;
import java.util.List;



public class Table {
	public Table(String tableName) {
		Tname = tableName;
		
		columns = new ArrayList<Column>();
		
	}
	public String Tname;
	public List<Column> columns;
	
	
	public void addColumn(Column newColumn) {
		// TODO Auto-generated method stub
		if( checkcol(newColumn.getColumnName())) {
			// GRESKA
			return;
		}else {
			columns.add(newColumn);
		}
	}
	public List<Column> getColumns() {
		return columns;
	}
	public String getTableName() {
		return Tname;
	}
	public int getColumnssize() {
		return columns.size();
	}
	public boolean checkcol(String name) {
		for (int i = 0; i < columns.size(); i++) {
			if (columns.get(i).getColumnName().equals( name ) ) return true;
		}
		return false;
	}
	public void addcolumn(Column c) {
		String name = c.getColumnName();
		
		if (checkcol(name)) {
			// GRESKA
			return;
			
		}
		columns.add(c);
	}
}
