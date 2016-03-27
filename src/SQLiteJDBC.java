import java.awt.Graphics;
import java.sql.*;


public class SQLiteJDBC
{
 // public static void main( String args[] )
 // {
    
    
    
   /* try {
      stmt = c.createStatement();
      String sql = "CREATE TABLE HighScores " +
                   "(ID INT PRIMARY KEY NOT NULL," +
                   " ScorerName VARCHAR(50), " + 
                   " Score INT     , " + 
                   " LevelTo INT, " + 
                   " GameLength time(7))"; 
      stmt.executeUpdate(sql);
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }    */
    
    
    
   /* try {
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
   // }
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
            ResultSet rs = stmt.executeQuery( "SELECT * FROM HighScores;" );
            for (int y=50; rs.next(); y=y+20 ) {
               int id = rs.getInt("id");
               String  ScorerName = rs.getString("ScorerName");
               int level  = rs.getInt("levelTo");
               int  score = rs.getInt("score");
               double gameLength = rs.getDouble("GameLength");
               g.drawString( "ID = " + id , 20, y);
               g.drawString("NAME = " + ScorerName, 100, y );
               g.drawString( "Level Reached = " + level, 190, y);
               g.drawString( "SCORE = " + score, 320, y);
               g.drawString( "Game Length = " + gameLength, 400, y);
               
            }
            rs.close();
            stmt.close();
            c.close();
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
    }
    
  }
