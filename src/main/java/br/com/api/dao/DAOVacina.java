package br.com.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import br.com.api.config.Conexao;
import br.com.api.model.Vacinas;
import br.com.api.model.Vacinas.PublicoAlvo;

public class DAOVacina {

    public static List<Vacinas> consultarTodasVacinas() throws Exception {
        List<Vacinas> lista = new ArrayList<>();
        String sql = "SELECT * FROM vacina";

        try (Connection conexao = Conexao.getConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vacinas vacina = new Vacinas(
                    rs.getLong("id"),
                    rs.getString("vacina"),
                    rs.getString("descricao"),
                    rs.getInt("limite_aplicacao"), // Corrigido nome da coluna
                    PublicoAlvo.valueOf(rs.getString("publico_alvo")) // Corrigido nome da coluna
                );
                lista.add(vacina);
            }
        }
        return lista;
    }

    public static List<Vacinas> consultarVacinasPorFaixa(String faixa) throws Exception {
        List<Vacinas> lista = new ArrayList<>();
        String sql = "SELECT * FROM vacina WHERE publico_alvo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, faixa);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vacinas vacina = new Vacinas(
                    rs.getLong("id"),
                    rs.getString("vacina"),
                    rs.getString("descricao"),
                    rs.getInt("limite_aplicacao"), // Corrigido nome da coluna
                    PublicoAlvo.valueOf(rs.getString("publico_alvo")) // Corrigido nome da coluna
                );
                lista.add(vacina);
            }
        }
        return lista;
    }

    public static List<Vacinas> consultarVacinasPorIdade(int meses) throws Exception {
        List<Vacinas> lista = new ArrayList<>();
        String sql = "SELECT * FROM vacina WHERE limite_aplicacao <= ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, meses);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Vacinas(
                    rs.getLong("id"),
                    rs.getString("vacina"),
                    rs.getString("descricao"),
                    rs.getInt("limite_aplicacao"), // Corrigido nome da coluna
                    PublicoAlvo.valueOf(rs.getString("publico_alvo")) // Corrigido nome da coluna
                ));
            }
        }
        return lista;
    }

    public static List<Vacinas> consultarVacinasNaoAplicaveis(int idadeMeses) throws Exception {
        List<Vacinas> lista = new ArrayList<>();
        String sql = "SELECT * FROM vacina WHERE limite_aplicacao < ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idadeMeses);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Vacinas(
                    rs.getLong("id"),
                    rs.getString("vacina"),
                    rs.getString("descricao"),
                    rs.getInt("limite_aplicacao"), // Corrigido nome da coluna
                    PublicoAlvo.valueOf(rs.getString("publico_alvo")) // Corrigido nome da coluna
                ));
            }
        }
        return lista;
    }
}
