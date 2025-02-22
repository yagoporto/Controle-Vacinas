package br.com.api.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.api.model.Imunizacao;

public class DAOImunizacao {
    public static Connection conexao = null;

        public static int adicionarImunizacao(Imunizacao imunizacao) throws SQLException{

            //Query de inserçao do banco
            String sql = "INSERT INTO imunizacoes (id_paciente, id_dose, data_aplicacao, fabricante, lote, local_aplicacao, profissional_aplicador) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try(PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                //adicionar os respectivos valores no lugar das ? na Query
                comando.setInt(1, imunizacao.getid_paciente());
                comando.setInt(2, imunizacao.getid_dose());
                comando.setDate(3, new java.sql.Date(imunizacao.getdata_aplicacao().getTime()));
                comando.setString(4, imunizacao.getFabricante());
                comando.setString(5, imunizacao.getLote());
                comando.setString(6, imunizacao.getlocal_aplicacao());
                comando.setString(7, imunizacao.getProfissional_Aplicador());

                //enviar o sql para o banco de dados
                comando.executeUpdate();

                //Obter retorno no banco de dados
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
            throw new SQLException("Erro ao inserir usuário: nenhum ID gerado.");
        }


        //Atualizar Imunizacao por id
        public static int alterarImunizacao (Imunizacao imunizacao) throws SQLException{

            //Query de alteracao do banco
            String sql = "UPDATE imunizacoes SET id_paciente = ?, id_dose = ?, data_aplicacao = ?, fabricante = ?, lote = ?, local_aplicacao = ?, profissional_aplicador = ? WHERE id = ?";

            try(PreparedStatement comando = conexao.prepareStatement(sql)){

                //adicionar os respectivos valores no lugar das ? na Query
                comando.setInt(1, imunizacao.getid_paciente());
                comando.setInt(2, imunizacao.getid_dose());
                comando.setDate(3, new java.sql.Date(imunizacao.getdata_aplicacao().getTime()));
                comando.setString(4, imunizacao.getFabricante());
                comando.setString(5, imunizacao.getLote());
                comando.setString(6, imunizacao.getlocal_aplicacao());
                comando.setString(7, imunizacao.getProfissional_Aplicador());
                comando.setInt(8, imunizacao.getId());

                //enviar o sql para o banco de dados
                int linhasAlteradas = comando.executeUpdate();

                //Retorna a quantidade de linhas alteradas
                return linhasAlteradas;
            }
        }


        public static int excluirImunizacao(int id) throws SQLException{
            //define o sql de exclusao
            String sql = "DELETE FROM imunizacoes WHERE id = ?";

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

        
        public static int excluirTodos(int id_paciente) throws SQLException {
            // define o sql de exclusao
            String sql = "DELETE FROM imunizacoes WHERE id_paciente = ?";

            try (
                PreparedStatement comando = conexao.prepareStatement(sql) // cria a conexao com o sql a ser preparado
            ) {
                // substitui a ? pelo id do paciente
                comando.setInt(1, id_paciente);

                // executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
                int qtdeLinhasExcluidas = comando.executeUpdate();

                return qtdeLinhasExcluidas;
            } catch (Exception e) {
                throw e;
            } 
        }

        public static Imunizacao consultarImunizacaoPorId(int id) throws SQLException{
            //define o sql de consulta
            String sql = "SELECT * FROM imunizacoes WHERE id = ?";

            try (
                PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao com o sql a ser preparado
            ) {
                //substitui a ? pelo id do usuario
                comando.setInt(1, id);

                //executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
                ResultSet resultado = comando.executeQuery();

                //retorna o objeto pessoa
                if (resultado.next()) {
                    //cria o objeto pessoa com os dados retornados do banco
                    Imunizacao imunizacao = new Imunizacao(
                        resultado.getInt("id"),
                        resultado.getInt("id_paciente"),
                        resultado.getInt("id_dose"),
                        resultado.getDate("data_aplicacao"),
                        resultado.getString("fabricante"),
                        resultado.getString("lote"),
                        resultado.getString("local_aplicacao"),
                        resultado.getString("profissional_aplicador")
                    );                 
                    return imunizacao;
                } else {
                    return null;
                }
            }
        }

        public static Imunizacao consultarTodasImunizacao() throws SQLException {
            // define o sql de consulta
            String sql = "SELECT * FROM imunizacoes";
        
            try (
                PreparedStatement comando = conexao.prepareStatement(sql) // cria a conexao com o sql a ser preparado
            ) {
            
                // executa a consulta e armazena o resultado na variavel 
                ResultSet resultado = comando.executeQuery();
        
                // retorna o objeto imunizacao
                if (resultado.next()) {
                    // cria o objeto imunizacao com os dados retornados do banco
                    Imunizacao imunizacao = new Imunizacao(
                        resultado.getInt("id"),
                        resultado.getInt("id_paciente"),
                        resultado.getInt("id_dose"),
                        resultado.getDate("data_aplicacao"),
                        resultado.getString("fabricante"),
                        resultado.getString("lote"),
                        resultado.getString("local_aplicacao"),
                        resultado.getString("profissional_aplicador")
                    );                 
                    return imunizacao;
                } else {
                    return null;
                }
            }
        }

        public static Imunizacao consultarImunizacaoPorPaciente(int id_paciente)throws SQLException{
            //define o sql de consulta
            String sql = "SELECT * FROM imunizacoes JOIN paciente ON imunizacoes.id_paciente = paciente.id WHERE paciente.id = ?";

            try (
                PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao com o sql a ser preparado
            ) {
                //substitui a ? pelo id do usuario
                comando.setInt(1, id_paciente);

                //executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
                ResultSet resultado = comando.executeQuery();

                //retorna o objeto pessoa
                if (resultado.next()) {
                    //cria o objeto pessoa com os dados retornados do banco
                    Imunizacao imunizacao = new Imunizacao(
                        resultado.getInt("id"),
                        resultado.getInt("id_paciente"),
                        resultado.getInt("id_dose"),
                        resultado.getDate("data_aplicacao"),
                        resultado.getString("fabricante"),
                        resultado.getString("lote"),
                        resultado.getString("local_aplicacao"),
                        resultado.getString("profissional_aplicador")
                    );                 
                    return imunizacao;
                } else {
                    return null;
                }
            }
        }

        public static List<Imunizacao> consultarImunizacoesPorIntervalo(int id_paciente, Date dataini, Date datafim) throws SQLException{
            //define o sql de consulta
            String sql = "SELECT * FROM imunizacoes WHERE id_paciente = ? AND data_aplicacao BETWEEN ? AND ?";

            List<Imunizacao> imunizacoes = new ArrayList<>();

            try (PreparedStatement comando = conexao.prepareStatement(sql)) {
                //substitui a ? pelo id do usuario
                comando.setInt(1, id_paciente);
                comando.setDate(2, dataini);
                comando.setDate(3, datafim);

                ResultSet resultado = comando.executeQuery();

                while (resultado.next()) {
                    Imunizacao imunizacao = new Imunizacao(
                        resultado.getInt("id"),
                        resultado.getInt("id_paciente"),
                        resultado.getInt("id_dose"),
                        resultado.getDate("data_aplicacao"),
                        resultado.getString("fabricante"),
                        resultado.getString("lote"),
                        resultado.getString("local_aplicacao"),
                        resultado.getString("profissional_aplicador")
                    );
                    imunizacoes.add(imunizacao);
                }
                return imunizacoes;
            }
        }
}
