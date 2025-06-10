package com.snakegame.snakegame_project.db;

import com.snakegame.snakegame_project.game.model.Jogador;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BancoDeDados {

    private static final String URL = "jdbc:sqlite:data/pontuacoes.db";

    // Cria a tabela se não existir
    public static void inicializar() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String sql = """
            CREATE TABLE IF NOT EXISTS pontuacoes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL UNIQUE,
                senha TEXT NOT NULL,
                pontuacao INTEGER NOT NULL
            );
        """;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela verificada/criada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao inicializar o banco: " + e.getMessage());
        }
    }

    // Salva a pontuação
    public static void salvarPontuacao(String nome, int pontuacao) {
        String sqlInsert = "INSERT INTO pontuacoes (nome, pontuacao) VALUES (?, ?)";

        String sqlUpdate = "UPDATE pontuacoes SET pontuacao = ? WHERE nome = ? AND pontuacao < ?";

        try (Connection conn = DriverManager.getConnection(URL)) {
            // Tenta atualizar se a pontuação for maior
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                pstmtUpdate.setInt(1, pontuacao);
                pstmtUpdate.setString(2, nome);
                pstmtUpdate.setInt(3, pontuacao);
                int rowsAffected = pstmtUpdate.executeUpdate();

                if (rowsAffected == 0) {
                    // Se não atualizou (novo jogador ou pontuação menor), tenta inserir
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                        pstmtInsert.setString(1, nome);
                        pstmtInsert.setInt(2, pontuacao);
                        pstmtInsert.executeUpdate();
                        System.out.println("Pontuação salva com sucesso!");
                    } catch (SQLException e) {
                        // Ignora erro de chave duplicada (nome já existe)
                    }
                } else {
                    System.out.println("Pontuação atualizada com sucesso!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar pontuação: " + e.getMessage());
        }
    }

    public static void cadastro(String nome, String senha) {
        String sql = "INSERT INTO pontuacoes (nome, senha, pontuacao) VALUES (?, ?, 0)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, senha);
            pstmt.executeUpdate();
            System.out.println("Jogador castrado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela de jogadores: " + e.getMessage());
        }
    }

    // Lista ranking (opcional)
    public static List<Jogador> listarRanking() {
        String sql = "SELECT nome, pontuacao FROM pontuacoes";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            List<Jogador> ranking = new ArrayList<>();

            while (rs.next()) {
                System.out.println(rs.getString("nome") + " - " + rs.getInt("pontuacao") + " pontuacao");
                ranking.add(new Jogador(rs.getString("nome"), "", rs.getInt("pontuacao")));
            }
            return ranking;

        } catch (SQLException e) {
            System.out.println("Erro ao listar ranking: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public static Optional<Jogador> getJogador(String usuario) {
        String sql = "SELECT * FROM pontuacoes WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                int pontuacao = rs.getInt("pontuacao");
                return Optional.of(new Jogador(nome, senha, pontuacao));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar jogador: " + e.getMessage());
        }
        return Optional.empty();
    }
}