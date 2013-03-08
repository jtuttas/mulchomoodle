import java.sql.*; 
import java.util.LinkedList;

public class mm {
	static LinkedList questions;

    static String hostname = "localhost"; 
    static String port = "3306"; 

    Connection conn = null; 

    public boolean connect(String dbname,String user,String password) {
        try { 
      	    Class.forName("org.gjt.mm.mysql.Driver").newInstance(); 
        } 
        catch (Exception e) { 
            System.err.println("Unable to load driver."); 
            e.printStackTrace();
            return false;
        } 
        try { 
        	String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname; 
        	conn = DriverManager.getConnection(url, user, password);
        	return true;
        } 
        catch (SQLException sqle) { 
            System.out.println("SQLException: " + sqle.getMessage()); 
            System.out.println("SQLState: " + sqle.getSQLState()); 
            System.out.println("VendorError: " + sqle.getErrorCode()); 
            sqle.printStackTrace();
            return false;
        } 
    }
    
    
    public LinkedList getThemes() {
    	LinkedList themes = new LinkedList();
        Statement stmt;
		try {
			stmt = conn.createStatement();
			String sqlCommand =	"SELECT * from thema"; 

			ResultSet rs = stmt.executeQuery(sqlCommand); 
    
			while (rs.next()) { 
				int id = rs.getInt(1);
				String t = rs.getString(2);
				//System.out.println ("Add String >"+t);
				themes.add(new Theme(t,id));
			}
			return themes;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

    }
    
    public LinkedList getQuestions(Theme theme) {
    	LinkedList quests = new LinkedList();
        Statement stmt;
		try {
			stmt = conn.createStatement();
			String sqlCommand =	"SELECT * from aufgabe WHERE thema='"+theme.getId()+"'"; 

			ResultSet rs = stmt.executeQuery(sqlCommand); 
    
			while (rs.next()) { 
				String imagefile = rs.getString(2);
				String qText = rs.getString(3);
				Question q = new Question(theme.getText(),qText,imagefile);
				Statement stmtAntworten = conn.createStatement();
                ResultSet rsa = stmtAntworten.executeQuery("SELECT * FROM antwort WHERE id_aufgabe='"+rs.getInt(1)+"'"); 
                while (rsa.next()) { 
                	String atext=rsa.getString(3);
                	int correct=rsa.getInt(4);
  //              	System.out.println ("Antwort:"+atext+" richtig="+correct);
                	Answer a;
                	if (correct==1)  {
                		a = new Answer(atext,true);
                	}
                	else {
                		a = new Answer(atext,false);                		
                	}
                	q.addAnswer(a);
                }
				quests.add(q);
			}
			return quests;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

    }
    
    
	
}
