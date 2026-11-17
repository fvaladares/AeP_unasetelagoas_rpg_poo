package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Personagem;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {
    private static final String URL = "jdbc:sqlite:rpg_save.db";
    private final Gson gson = new Gson();

    public PersonagemDAO() {
        criarTabelaSeNaoExistir();
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void criarTabelaSeNaoExistir() {
        String sql = """
            CREATE TABLE IF NOT EXISTS personagens (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                hp INTEGER NOT NULL,
                mana INTEGER NOT NULL,
                ataque_base INTEGER NOT NULL,
                inventario_json TEXT
            );
        """;
        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    // CREATE
    public void salvar(Personagem p) {
        String sql = "INSERT INTO personagens (nome, hp, mana, ataque_base, inventario_json) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, p.getNome());
            pstmt.setInt(2, p.getHp());
            pstmt.setInt(3, p.getMana());
            pstmt.setInt(4, p.getAtaqueBase());
            pstmt.setString(5, gson.toJson(p.getInventario())); // Serializa lista para JSON
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar personagem: " + e.getMessage());
        }
    }

    // READ (Todos)
    public List<Personagem> listarTodos() {
        List<Personagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM personagens";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar personagens: " + e.getMessage());
        }
        return lista;
    }

    // READ (Por ID)
    public Personagem buscarPorId(int id) {
        String sql = "SELECT * FROM personagens WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar personagem: " + e.getMessage());
        }
        return null;
    }

    // UPDATE (Salvar progresso pós-batalha)
    public void atualizar(Personagem p) {
        String sql = "UPDATE personagens SET nome = ?, hp = ?, mana = ?, ataque_base = ?, inventario_json = ? WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNome());
            pstmt.setInt(2, p.getHp());
            pstmt.setInt(3, p.getMana());
            pstmt.setInt(4, p.getAtaqueBase());
            pstmt.setString(5, gson.toJson(p.getInventario()));
            pstmt.setInt(6, p.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar personagem: " + e.getMessage());
        }
    }

    // DELETE
    public void deletar(int id) {
        String sql = "DELETE FROM personagens WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar personagem: " + e.getMessage());
        }
    }

    // Método auxiliar privado para desserializar o ResultSet + JSON
    private Personagem mapearResultSet(ResultSet rs) throws SQLException {
        Type tipoLista = new TypeToken<ArrayList<String>>() {}.getType();
        List<String> inventario = gson.fromJson(rs.getString("inventario_json"), tipoLista);

        return new Personagem(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getInt("hp"),
                rs.getInt("mana"),
                rs.getInt("ataque_base"),
                inventario
        );
    }
}
