
package ma.ensa.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private Connection con;
    public Connexion() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con=  DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionfacture","root","");


    }

    public Connection getCon() {
        return con;
    }
}