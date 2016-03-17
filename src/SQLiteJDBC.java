import java.sql.*;

public class SQLiteJDBC
{
  public static void main( String args[] )
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
    try {
        c.setAutoCommit(false);

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM HighScores;" );
        while ( rs.next() ) {
           int id = rs.getInt("id");
           String  ScorerName = rs.getString("ScorerName");
           int level  = rs.getInt("levelTo");
           int  score = rs.getInt("score");
           double gameLength = rs.getDouble("GameLength");
           System.out.println( "ID = " + id );
           System.out.println( "NAME = " + ScorerName );
           System.out.println( "Level Reached = " + level );
           System.out.println( "SCORE = " + score );
           System.out.println( "Game Length = " + gameLength );
           System.out.println();
        }
        rs.close();
        stmt.close();
        c.close();
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }
      System.out.println("Operation done successfully");
    }
    
    
  }
