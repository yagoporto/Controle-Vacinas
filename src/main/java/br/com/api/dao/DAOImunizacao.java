package br.com.api.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.api.model.Imunizacao;

public class DAOImunizacao {
    public static Connection conexao = null;

        public static int adicionarImunizacao(Imunizacao imunizacao) throws SQLException{

            //Query de inserçao do banco
            String sql = "INSERT INTO imunizacao (idPaciente, idDose, dataAplicacao, fabricante, lote, localAplicacao, profissionalAplicador) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try(PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                //adicionar os respectivos valores no lugar das ? na Query
                comando.setLong(1, imunizacao.getIdPaciente());
                comando.setLong(2, imunizacao.getIdDose());
                comando.setDate(3, new java.sql.Date(imunizacao.getDataAplicacao().getTime()));
                comando.setString(4, imunizacao.getFabricante());
                comando.setString(5, imunizacao.getLote());
                comando.setString(6, imunizacao.getLocalAplicacao());
                comando.setString(7, imunizacao.getProfissionalAplicador());

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
            String sql = "UPDATE imunizacao SET idPaciente = ?, idDose = ?, dataAplicacao = ?, fabricante = ?, lote = ?, localAplicacao = ?, profissionalAplicador = ? WHERE id = ?";

            try(PreparedStatement comando = conexao.prepareStatement(sql)){

                //adicionar os respectivos valores no lugar das ? na Query
                comando.setLong(1, imunizacao.getIdPaciente());
                comando.setLong(2, imunizacao.getIdDose());
                comando.setDate(3, new java.sql.Date(imunizacao.getDataAplicacao().getTime()));
                comando.setString(4, imunizacao.getFabricante());
                comando.setString(5, imunizacao.getLote());
                comando.setString(6, imunizacao.getLocalAplicacao());
                comando.setString(7, imunizacao.getProfissionalAplicador());
                comando.setLong(8, imunizacao.getId());

                //enviar o sql para o banco de dados
                int linhasAlteradas = comando.executeUpdate();

                //Retorna a quantidade de linhas alteradas
                return linhasAlteradas;
            }
        }


        public static int excluirImunizacao(Long id) throws SQLException{
            //define o sql de exclusao
            String sql = "DELETE FROM imunizacoes WHERE id = ?";

            try (
                PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao com o sql a ser preparado
            ) {
                //substitui a ? pelo id do usuario
                comando.setLong(1, id);

                //executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
                int qtdeLinhasExcluidas = comando.executeUpdate();

                return qtdeLinhasExcluidas;
            } catch (Exception e) {
                throw e;
            } 
        }

        
        public static int excluirTodos(Long idPaciente) throws SQLException {
            // define o sql de exclusao
            String sql = "DELETE FROM imunizacoes WHERE idPaciente = ?";

            try (
                PreparedStatement comando = conexao.prepareStatement(sql) // cria a conexao com o sql a ser preparado
            ) {
                // substitui a ? pelo id do paciente
                comando.setLong(1, idPaciente);

                // executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
                int qtdeLinhasExcluidas = comando.executeUpdate();

                return qtdeLinhasExcluidas;
            } catch (Exception e) {
                throw e;
            } 
        }

        public static Imunizacao consultarImunizacaoPorId(Long id) throws SQLException{
            //define o sql de consulta
            String sql = "SELECT * FROM imunizacoes WHERE id = ?";

            try (
                PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao com o sql a ser preparado
            ) {
                //substitui a ? pelo id do usuario
                comando.setLong(1, id);

                //executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
                ResultSet resultado = comando.executeQuery();

                //retorna o objeto pessoa
                if (resultado.next()) {
                    //cria o objeto pessoa com os dados retornados do banco
                    Imunizacao imunizacao = new Imunizacao(
                        resultado.getLong("id"),
                        resultado.getLong("idPaciente"),
                        resultado.getLong("idDose"),
                        resultado.getDate("dataAplicacao"),
                        resultado.getString("fabricante"),
                        resultado.getString("lote"),
                        resultado.getString("localAplicacao"),
                        resultado.getString("profissionalAplicador")
                    );                 
                    return imunizacao;
                } else {
                    return null;
                }
            }
        }

        public static Imunizacao consultarTodasImunizacaoPorId(Long id) throws SQLException {
            // define o sql de consulta
            String sql = "SELECT * FROM imunizacoes WHERE id = ?";
        
            try (
                PreparedStatement comando = conexao.prepareStatement(sql) // cria a conexao com o sql a ser preparado
            ) {
                // substitui a ? pelo id do usuario
                comando.setLong(1, id);
        
                // executa a consulta e armazena o resultado na variavel 
                ResultSet resultado = comando.executeQuery();
        
                // retorna o objeto imunizacao
                if (resultado.next()) {
                    // cria o objeto imunizacao com os dados retornados do banco
                    Imunizacao imunizacao = new Imunizacao(
                        resultado.getLong("id"),
                        resultado.getLong("idPaciente"),
                        resultado.getLong("idDose"),
                        resultado.getDate("dataAplicacao"),
                        resultado.getString("fabricante"),
                        resultado.getString("lote"),
                        resultado.getString("localAplicacao"),
                        resultado.getString("profissionalAplicador")
                    );                 
                    return imunizacao;
                } else {
                    return null;
                }
            }
        }

        public static Imunizacao consultarImunizacaoPorPaciente(Long id) throws SQLException{
            //define o sql de consulta
            String sql = "SELECT * FROM imunizacoes WHERE idPaciente = ?";

            try (
                PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao com o sql a ser preparado
            ) {
                //substitui a ? pelo id do usuario
                comando.setLong(1, id);

                //executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
                ResultSet resultado = comando.executeQuery();

                //retorna o objeto pessoa
                if (resultado.next()) {
                    //cria o objeto pessoa com os dados retornados do banco
                    Imunizacao imunizacao = new Imunizacao(
                        resultado.getLong("id"),
                        resultado.getLong("idPaciente"),
                        resultado.getLong("idDose"),
                        resultado.getDate("dataAplicacao"),
                        resultado.getString("fabricante"),
                        resultado.getString("lote"),
                        resultado.getString("localAplicacao"),
                        resultado.getString("profissionalAplicador")
                    );                 
                    return imunizacao;
                } else {
                    return null;
                }
            }
        }

        public static Imunizacao  consultarImunizacoesPorIntervalo(Long id, Date dataini, Date datafim) throws SQLException{
            //define o sql de consulta
            String sql = "SELECT * FROM imunizacoes WHERE idPaciente = ? AND dataAplicacao BETWEEN ? AND ?";

            try (
                PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao com o sql a ser preparado
            ) {
                //substitui a ? pelo id do usuario
                comando.setLong(1, id);
                comando.setDate(2, dataini);
                comando.setDate(3, datafim);

                //executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
                ResultSet resultado = comando.executeQuery();

                //retorna o objeto pessoa
                if (resultado.next()) {
                    //cria o objeto pessoa com os dados retornados do banco
                    Imunizacao imunizacao = new Imunizacao(
                        resultado.getLong("id"),
                        resultado.getLong("idPaciente"),
                        resultado.getLong("idDose"),
                        resultado.getDate("dataAplicacao"),
                        resultado.getString("fabricante"),
                        resultado.getString("lote"),
                        resultado.getString("localAplicacao"),
                        resultado.getString("profissionalAplicador")
                    );                 
                    return imunizacao;
                } else {
                    return null;
                }
            }
        }
}
