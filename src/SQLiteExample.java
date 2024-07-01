import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLiteExample extends Exception {

   static Connection connection = null;
   static Statement statement = null;

    // Create a database connection
    public static Connection getConnection(String url) throws SQLException
    { 
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Connected!!");
            return connection;   
    }
    //create a statement
    public static Statement getStatement(Connection connection) throws SQLException
    {
        Statement statement = connection.createStatement();
        return statement;
    }
    // Create a new table
    public static void createTableSQLite(String sqlStmt) throws SQLException
    {   
         statement.executeUpdate(sqlStmt);
         System.out.println("Table created");

    }
    // Insert data into the table
    public static void insertDataSQLite(String sqlStmt) throws SQLException
    {       
            statement.executeUpdate(sqlStmt);
            System.out.println("Data inserted");

    }
    public static void retrieveDataSQLite(String table) throws SQLException
    {
           // Query data from the table
           String queryDataSQL = "SELECT * FROM "+table;
           ResultSet resultSet = statement.executeQuery(queryDataSQL);

           // Print the results
           while (resultSet.next()) {
               int id = resultSet.getInt("id");
               String name = resultSet.getString("name");
               int age = resultSet.getInt("age");
               System.out.println(id+" "+name+" "+age);
           }

           // Close the resultSet, statement, and connection
           resultSet.close();
    }
    public static void main(String[] args ){
        try {
            // Load the SQLite JDBC driver (you must have the sqlite-jdbc.jar in your classpath)
            Class.forName("org.sqlite.JDBC");

             // Create a database connection
            String urlString="jdbc:sqlite:C://Users//PTPL-589//Desktop//javadb.db";
            connection=getConnection(urlString);

            //Create a statement using connection object
            statement=getStatement(connection);
             try
             {  //Create a table
                String sqlString="CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER)";
                createTableSQLite(sqlString);
            }
            catch(SQLException e)
            {
                System.out.println("Table already exists in the database");
            }
            

            //Insert into table
            String insertDataSQL = "INSERT INTO users (name, age) VALUES ('Alice', 30), ('Bob', 25)";
            insertDataSQLite(insertDataSQL);

            //Retrieve values
            String table = "users";
            retrieveDataSQLite(table);

            
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        catch(ClassNotFoundException e)
        {
             System.out.println(e.getMessage()); 
        }
        catch(Exception e)
        {
             System.out.println(e);
        }

        finally {
            try {
                if (statement != null) {
                    statement.close();
                    
                }
                if (connection != null) {
                    connection.close();
                    System.out.println("Connection Closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

