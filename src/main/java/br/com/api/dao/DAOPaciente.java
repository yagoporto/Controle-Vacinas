package br.com.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.api.model.PacienteModel.Paciente;

public class DAOPaciente {

    public static Connection conexao = null;

     public static int inserir(Paciente paciente) throws SQLException{
        //Define a consulta sql 
        String sql = "INSERT INTO paciente (nome, cpf, sexo,data_nascimento ) VALUES (?, ?, ?,?)";

        //Statement.RETURN_GENERATED_KEYS parametro que diz que o banco de dados deve retornar o id da chave primaria criada
        try(PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //adiciona os valores de nome e mail no lugar das ? da string sql
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getSexo().name());
            comando.setDate(4, new java.sql.Date(paciente.getData_nascimento().getTime()));

            //envia o sql para o banco de dados
            comando.executeUpdate();

            //obtem o resultado retornado do banco de dados
            //getGenerateKeys ira retornar o id da chave primaria que o banco de dados criou
            try (ResultSet idGerado = comando.getGeneratedKeys()) {
                //verifica se o banco de dados retornou um id
                if (idGerado.next()) { 
                    //retorna o id gerado no banco de dados
                    return idGerado.getInt(1);
                }
            }
        }
        
        //Caso o fluxo de execucao chegue ate este ponto é porque ocorreu algum erro
        //Gera uma excecao de negocio dizendo que nenhum id foi gerado
        throw new SQLException("Erro ao inserir paciente: nenhum ID gerado.");
    }

     public static ArrayList<Paciente> consultarTodos() throws SQLException{
        //cria o array list pra receber os dados dos usuarios que retornarao do banco de dados
        ArrayList<Paciente> lista = new ArrayList<Paciente>();

        //define o sql de consulta
        String sql = "SELECT * FROM paciente";

        try (   Statement comando = conexao.createStatement(); //cria o comando
                ResultSet resultado = comando.executeQuery(sql); //executa a consulta
            ) {

            //para cada registro retornado do banco de dados
            while(resultado.next()){
                
                //cria um novo objeto usuario
                Paciente novoPaciente = new Paciente(
                    resultado.getInt("id"),
                    resultado.getString("nome"), 
                    resultado.getString("cpf"),
                    Paciente.Sexo.valueOf(resultado.getString("sexo")),
                    resultado.getDate("data_nascimento")
                );

                //adiciona o objeto usuario no array list
                lista.add(novoPaciente);
            }
        } 
        
        //retorna o array list de objetos usuarios
        return lista;
    }


    public static Paciente consultarPorID(int id) throws SQLException{
        //inicia o objeto pessoa como null
        Paciente paciente = null;

        //define o sql da consulta
        String sql = "SELECT * FROM paciente WHERE id = ?";

        try (
            PreparedStatement comando = conexao.prepareStatement(sql) //cria o comando para receber sql dinamico
        ) {
            //substitui a ? pelo codigo do usuario
            comando.setInt(1, id);

            //executa o comando sql
            ResultSet resultado = comando.executeQuery();

            //verifica se tem algum resultado retornado pelo banco
            if (resultado.next()) {
                //cria o objeto pessoa com os dados retornados do banco
                paciente = new Paciente(
                    resultado.getInt("id"),
                    resultado.getString("nome"), 
                    resultado.getString("cpf"),
                    Paciente.Sexo.valueOf(resultado.getString("sexo")),
                    resultado.getDate("data_nascimento")
                );                 
            } 
                
            //retorna o objeto pessoa
            return paciente;
        }
    }

    public static int excluirPorID(int id) throws SQLException{
        //define o sql de exclusao
        String sql = "DELETE FROM paciente WHERE id = ?";

        try (
            PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao com o sql a ser preparado
        ) {
            //substitui a ? pelo id do usuario
            comando.setInt(1, id);

            //executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
            int qtdeLinhasExcluidas = comando.executeUpdate();

            return qtdeLinhasExcluidas;
        } catch (Exception e) {
            throw e;
        } 
    }

    public static int atualizar(Paciente paciente) throws SQLException{
        //define o sql 
        String sql = "UPDATE paciente SET nome = ?, cpf = ?, sexo = ?, data_nascimento = ? WHERE id = ?";

        try (
            PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao para receber o sql dinamico
        ) {
            //substitui as ? pelos valores de nome, email e id
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getSexo().name());
            comando.setDate(4, new java.sql.Date(paciente.getData_nascimento().getTime()));
            comando.setLong(5, paciente.getId());

            //executa a atualizacao e armazena o retorno do banco com a quantidade de linhas atualizadas
            int qtdeLinhasAlteradas = comando.executeUpdate();

            //retorna a quantidade de linhas atualizadas
            return qtdeLinhasAlteradas;
        }
    }
  
}
