package br.com.api.estatisticas;

import static spark.Spark.*;
import com.google.gson.Gson;
import br.com.api.config.Conexao;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EstatisticasController {

    public static void init() {
        // Rota para quantidade de vacinas aplicadas por paciente
        get("/estatisticas/imunizacoes/paciente/:id", (req, res) -> {
            int idPaciente = Integer.parseInt(req.params(":id"));
            int quantidade = 0;

            try (Connection conn = Conexao.getConexao()) {
                System.out.println("Conexão com o banco de dados estabelecida.");

                String sql = "SELECT COUNT(*) AS total FROM imunizacoes WHERE id_paciente = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idPaciente);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    quantidade = rs.getInt("total");
                    System.out.println("Quantidade de imunizações encontradas: " + quantidade);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return "Erro ao consultar o banco de dados.";
            }

            res.type("application/json");
            return new Gson().toJson(new QuantidadeResponse(quantidade));
        });

        // Rota para quantidade de vacinas aplicáveis no próximo mês por paciente
        get("/estatisticas/proximas_imunizacoes/paciente/:id", (req, res) -> {
            int idPaciente = Integer.parseInt(req.params(":id"));
            int quantidade = 0;

            try (Connection conn = Conexao.getConexao()) {
                System.out.println("Conexão com o banco de dados estabelecida.");

                // Consulta a data de nascimento do paciente
                String sqlPaciente = "SELECT data_nascimento FROM paciente WHERE id = ?";
                PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente);
                stmtPaciente.setInt(1, idPaciente);
                ResultSet rsPaciente = stmtPaciente.executeQuery();

                if (rsPaciente.next()) {
                    Date dataNascimento = rsPaciente.getDate("data_nascimento");

                    // Converter java.sql.Date para java.time.LocalDate
                    LocalDate dataNasc = dataNascimento.toLocalDate();
                    LocalDate hoje = LocalDate.now();

                    // Calcular a idade em meses
                    long idadeMeses = ChronoUnit.MONTHS.between(dataNasc, hoje);
                    System.out.println("Idade do paciente em meses: " + idadeMeses);

                    // Consulta as vacinas recomendadas para o próximo mês
                    String sqlVacinas = "SELECT COUNT(*) AS total FROM dose WHERE idade_recomendada_aplicacao BETWEEN ? AND ?";
                    PreparedStatement stmtVacinas = conn.prepareStatement(sqlVacinas);
                    stmtVacinas.setLong(1, idadeMeses);
                    stmtVacinas.setLong(2, idadeMeses + 1);
                    ResultSet rsVacinas = stmtVacinas.executeQuery();

                    if (rsVacinas.next()) {
                        quantidade = rsVacinas.getInt("total");
                        System.out.println("Quantidade de vacinas aplicáveis no próximo mês: " + quantidade);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return "Erro ao consultar o banco de dados.";
            }

            res.type("application/json");
            return new Gson().toJson(new QuantidadeResponse(quantidade));
        });

        // Rota para quantidade de vacinas atrasadas
        get("/estatisticas/imunizacoes_atrasadas/paciente/:id", (req, res) -> {
            int idPaciente = Integer.parseInt(req.params(":id"));
            int quantidade = 0;

            try (Connection conn = Conexao.getConexao()) {
                System.out.println("Conexão com o banco de dados estabelecida.");

                // Consulta a data de nascimento do paciente
                String sqlPaciente = "SELECT data_nascimento FROM paciente WHERE id = ?";
                PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente);
                stmtPaciente.setInt(1, idPaciente);
                ResultSet rsPaciente = stmtPaciente.executeQuery();

                if (rsPaciente.next()) {
                    Date dataNascimento = rsPaciente.getDate("data_nascimento");

                    // Converter java.sql.Date para java.time.LocalDate
                    LocalDate dataNasc = dataNascimento.toLocalDate();
                    LocalDate hoje = LocalDate.now();

                    // Calcular a idade em meses
                    long idadeMeses = ChronoUnit.MONTHS.between(dataNasc, hoje);
                    System.out.println("Idade do paciente em meses: " + idadeMeses);

                    // Consulta as vacinas recomendadas para idades anteriores à idade atual
                    String sqlVacinas = """
                        SELECT COUNT(*) AS total 
                        FROM dose 
                        WHERE idade_recomendada_aplicacao < ? 
                        AND id NOT IN (
                            SELECT id_dose 
                            FROM imunizacoes 
                            WHERE id_paciente = ?
                        )
                    """;
                    PreparedStatement stmtVacinas = conn.prepareStatement(sqlVacinas);
                    stmtVacinas.setLong(1, idadeMeses);
                    stmtVacinas.setInt(2, idPaciente);
                    ResultSet rsVacinas = stmtVacinas.executeQuery();

                    if (rsVacinas.next()) {
                        quantidade = rsVacinas.getInt("total");
                        System.out.println("Quantidade de vacinas atrasadas: " + quantidade);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return "Erro ao consultar o banco de dados.";
            }

            res.type("application/json");
            return new Gson().toJson(new QuantidadeResponse(quantidade));
        });

        // Rota para quantidade de vacinas acima de uma determinada idade
        get("/estatisticas/vacinas_acima_idade/paciente/:id/:idade", (req, res) -> {
            int idPaciente = Integer.parseInt(req.params(":id"));
            int idadeLimite = Integer.parseInt(req.params(":idade"));
            int quantidade = 0;

            try (Connection conn = Conexao.getConexao()) {
                System.out.println("Conexão com o banco de dados estabelecida.");

                // Consulta as vacinas recomendadas para idades acima da idade limite
                String sqlVacinas = """
                    SELECT COUNT(*) AS total 
                    FROM dose 
                    WHERE idade_recomendada_aplicacao > ? 
                    AND id NOT IN (
                        SELECT id_dose 
                        FROM imunizacoes 
                        WHERE id_paciente = ?
                    )
                """;
                PreparedStatement stmtVacinas = conn.prepareStatement(sqlVacinas);
                stmtVacinas.setInt(1, idadeLimite);
                stmtVacinas.setInt(2, idPaciente);
                ResultSet rsVacinas = stmtVacinas.executeQuery();

                if (rsVacinas.next()) {
                    quantidade = rsVacinas.getInt("total");
                    System.out.println("Quantidade de vacinas acima da idade " + idadeLimite + " meses: " + quantidade);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return "Erro ao consultar o banco de dados.";
            }

            res.type("application/json");
            return new Gson().toJson(new QuantidadeResponse(quantidade));
        });

        // Rota para quantidade de vacinas não aplicáveis
        get("/estatisticas/vacinas_nao_aplicaveis/paciente/:id", (req, res) -> {
            int idPaciente = Integer.parseInt(req.params(":id"));
            int quantidade = 0;

            try (Connection conn = Conexao.getConexao()) {
                System.out.println("Conexão com o banco de dados estabelecida.");

                // Consulta a data de nascimento do paciente
                String sqlPaciente = "SELECT data_nascimento FROM paciente WHERE id = ?";
                PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente);
                stmtPaciente.setInt(1, idPaciente);
                ResultSet rsPaciente = stmtPaciente.executeQuery();

                if (rsPaciente.next()) {
                    Date dataNascimento = rsPaciente.getDate("data_nascimento");

                    // Converter java.sql.Date para java.time.LocalDate
                    LocalDate dataNasc = dataNascimento.toLocalDate();
                    LocalDate hoje = LocalDate.now();

                    // Calcular a idade em meses
                    long idadeMeses = ChronoUnit.MONTHS.between(dataNasc, hoje);
                    System.out.println("Idade do paciente em meses: " + idadeMeses);

                    // Consulta as vacinas que não são aplicáveis (idade recomendada já passou e não foram aplicadas)
                    String sqlVacinas = """
                        SELECT COUNT(*) AS total 
                        FROM dose 
                        WHERE idade_recomendada_aplicacao < ? 
                        AND id NOT IN (
                            SELECT id_dose 
                            FROM imunizacoes 
                            WHERE id_paciente = ?
                        )
                    """;
                    PreparedStatement stmtVacinas = conn.prepareStatement(sqlVacinas);
                    stmtVacinas.setLong(1, idadeMeses);
                    stmtVacinas.setInt(2, idPaciente);
                    ResultSet rsVacinas = stmtVacinas.executeQuery();

                    if (rsVacinas.next()) {
                        quantidade = rsVacinas.getInt("total");
                        System.out.println("Quantidade de vacinas não aplicáveis: " + quantidade);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                res.status(500);
                return "Erro ao consultar o banco de dados.";
            }

            res.type("application/json");
            return new Gson().toJson(new QuantidadeResponse(quantidade));
        });
    }
}

// Classe para representar a resposta
class QuantidadeResponse {
    private int quantidade;

    public QuantidadeResponse(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }
}