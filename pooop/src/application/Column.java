package application;

import java.util.ArrayList;
import java.util.List;

public class Column {
	public Column(String columnName) {
		Cname = columnName;
		
		row = new ArrayList<String>();
		
	}
	public String Cname;
	public List<String> row;
	
	
	public void addRow(String cell) {
		// TODO Auto-generated method stub
		row.add(cell);
	}
	public String getColumnName() {
		return Cname;
	}
	public List<String> getRows() {
		return row;
	}
	public int getRowSize() {
		return row.size();
	}
	
	public void addrow(String s) {
		row.add(s);
	}
}
