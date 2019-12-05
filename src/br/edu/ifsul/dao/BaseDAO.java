package br.edu.ifsul.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAO {

    protected Connection getConnection(){
        String url = "jdbc:mariadb://localhost:3306/vendas";
        String user = "root";
        String pass = "senha5";
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
