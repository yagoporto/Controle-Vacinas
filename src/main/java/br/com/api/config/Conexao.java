package br.com.api.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/vacinacao"; //Alterar para o nome do seu banco de dados
    private static final String USUARIO = "root"; // Altere para seu usuário
    private static final String SENHA = "123456";  // Altere para sua senha

    public static Connection getConexao() throws Exception {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

}