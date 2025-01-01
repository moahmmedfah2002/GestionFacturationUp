package ma.ensa.project.repo;

import ma.ensa.project.entity.Client;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClientRepo {
    public Client getClient(int id) throws SQLException;
    public ArrayList<Client> getClients() throws SQLException;
    public boolean addClient(Client client,int userId) throws SQLException;
    public boolean updateClient(Client client) throws SQLException;
    public boolean deleteClient(int id) throws SQLException;

    Client getClientByEmail(String email) throws SQLException;

}
