
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AddressOptions {
    
    //variables to store connection, statement, and prepared statement
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedstatement = null;
    
    //create database connection
    public AddressOptions() {
        
         try {
            connection = DriverManager.getConnection(Database.DATABASE_URL,
                    Database.USERNAME, Database.PASSWORD);
            
            System.out.println("Connection is successful");
             } 
         catch (SQLException ex) {
            Logger.getLogger(AddressOptions.class.getName()).log(Level.SEVERE,
                    null, ex);
             }
         
    }
    
    //create array list to store each address
    public ArrayList <Address> viewAddresses () {
        
        try {
            ArrayList <Address> address = new ArrayList <Address> ();
            String query="select * from ADDRESSES";
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                
                int id = rs.getInt("ADDRESSID");
                String firstname = rs.getString("FIRSTNAME");
                String lastname = rs.getString("LASTNAME");
                String email = rs.getString("EMAIL");
                String phonenumber = rs.getString("PHONENUMBER");
                
                address.add(new Address(id, firstname, lastname, email, phonenumber));
            }
            
            return address;
            
        } catch (SQLException ex) {
            Logger.getLogger(AddressOptions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    //method for SQL add address
    public void addAddress (String firstname, String lastname, String email, 
            String phonenumber) {
        
        try {
            String query = "insert into ADDRESSES (FIRSTNAME, LASTNAME, "
                    + "EMAIL, PHONENUMBER) values (?, ?, ?, ?)";
            
            preparedstatement = connection.prepareStatement(query);
            preparedstatement.setString(1, firstname);
            preparedstatement.setString(2, lastname);
            preparedstatement.setString(3, email);
            preparedstatement.setString(4, phonenumber);
            preparedstatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AddressOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //method for SQL update address
    public void updateAddress (int id, String firstName, String lastName,
            String email, String phoneNumber) {
        
        try {
            String query = "update ADDRESSES set FIRSTNAME=?, LASTNAME=?, "
                    + "EMAIL=?, PHONENUMBER=? where ADDRESSID=?";
            preparedstatement = connection.prepareStatement(query);
            preparedstatement.setString(1,firstName);
            preparedstatement.setString(2, lastName);
            preparedstatement.setString(3,email);
            preparedstatement.setString(4, phoneNumber);
            preparedstatement.setInt(5,id);
            preparedstatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AddressOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //method for SQL delete address
    public void deleteAddress (int id) {
        
        try {
            String query = "delete from ADDRESSES where ADDRESSID=?";
            preparedstatement=connection.prepareStatement(query);
            preparedstatement.setInt(1, id);
            preparedstatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AddressOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //method for SQL delete all addresses
    public void deleteAll () {
        
        try {
            String query = "delete from ADDRESSES";
            preparedstatement = connection.prepareStatement(query);
            preparedstatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AddressOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
