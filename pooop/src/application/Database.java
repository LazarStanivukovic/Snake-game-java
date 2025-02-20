package application;
import java.util.ArrayList;
import java.util.List;

public class Database {
	
	public Database(String dname) {
		Dname = dname;
		
		tables = new ArrayList<Table>();
		
	}
	public String Dname;
	public String getDname() {
		return Dname;
	}
	public List<Table> tables;
	public List<Table> getTables() {
		return tables;
	}
	public void addTable(Table newTable) {
		if(containsTable(newTable.getTableName())) {
			// GRESKA
			return;
		}
		else {
			tables.add(newTable);
		}
	}
	public int getTablessize() {
		return tables.size(); 
	}
	public boolean containsTable(String name) {
		for (int i = 0; i < tables.size(); i++) {
			if (tables.get(i).getTableName().equals( name ) ) return true;
		}
		return false;
	}
	public void dropTable(Table newTable) {
		if (!containsTable(newTable.getTableName())) {
			// Greska
			return;
		}
		
		
		int size = tables.size();
		for (int i =0; i < size;i++) {
			if( tables.get(i).getTableName().equals( newTable.getTableName() ) ) {
				tables.remove(i);
				break;
			}
		}
	}
}
