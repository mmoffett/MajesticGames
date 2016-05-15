import java.awt.Graphics;
import java.sql.*;


public class SQLiteJDBC
{
  /*public static void main( String args[] )
  {
	  Connection c = null;
      Statement stmt = null;
    
      try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:gameDB.db");
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
      
    try {
      stmt = c.createStatement();
      String sql = "CREATE TABLE HighScore " +
                   "(ID INTEGER PRIMARY KEY," +
                   " ScorerName VARCHAR(50), " + 
                   " Score INT     , " + 
                   " LevelTo INT) " ; 
      stmt.executeUpdate(sql);
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }  */  
    
    
    
    /*try {
        stmt = c.createStatement();
        String sql = "INSERT INTO HighScores (ID,ScorerName,Score,LevelTo,GameLength) " +
                     "VALUES (5, 'Jay', 47, 1, '2:09:01' );"; 
        stmt.executeUpdate(sql);

        sql = "INSERT INTO HighScores (ID,ScorerName,Score,LevelTo,GameLength) " +
              "VALUES (6, 'Al', 225, 9, '12:00:00' );"; 
        stmt.executeUpdate(sql);

        sql = "INSERT INTO HighScores (ID,ScorerName,Score,LevelTo,GameLength) " +
              "VALUES (7, 'Taylor', 23, 1, '0:50:00' );"; 
        stmt.executeUpdate(sql);

        sql = "INSERT INTO HighScores (ID,ScorerName,Score,LevelTo,GameLength) " +
              "VALUES (8, 'May', 97, 2, '4:37:00' );"; 
        stmt.executeUpdate(sql);

        stmt.close();
        c.commit();
        c.close();
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }
      System.out.println("Records created successfully");*/
    
      //System.out.println("Operation done successfully");
    //}
    public void output(Graphics g)
    {
    	Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:gameDB.db");
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
    	try {
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM HighScore ORDER BY score DESC" );
            int i=1;
            for (int y=50; rs.next(); y=y+20 ) 
            {
               int id = rs.getInt("id");
               String  ScorerName = rs.getString("ScorerName");
               int level  = rs.getInt("levelTo");
               int  score = rs.getInt("score");
               g.drawString("Rank= "+ i,20,y);
               g.drawString( "ID = " + id , 100, y);
               g.drawString("NAME = " + ScorerName, 180, y );
               g.drawString( "Level Reached = " + level, 330, y);
               g.drawString( "SCORE = " + score, 480, y);
               i++;
            }
            rs.close();
            stmt.close();
            c.close();
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
    }
    @SuppressWarnings("unused")
	public void addHighScore(String name, int score, int level)
    {
    	
    	Connection c = null;
        Statement stmt = null;
        
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:gameDB.db");
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
        
    	try {
        stmt = c.createStatement();
        c.setAutoCommit(false);
        String sql = "INSERT INTO HighScore (ScorerName,Score,LevelTo) " +
                     "VALUES ('"+name+"', "+score+", "+level+");"; 
        stmt.executeUpdate(sql);
        stmt.close();
        c.commit();
        c.close();
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }
      System.out.println("Records created successfully");
    }
  }
