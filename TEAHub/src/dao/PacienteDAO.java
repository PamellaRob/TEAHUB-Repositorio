/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexao.ConexaoBanco;
import model.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // CREATE - Cadastrar paciente
    public boolean cadastrar(Paciente p) {
        String sql = "INSERT INTO paciente (id_clinica, nome_paciente, data_nascimento, " +
                     "responsavel, telefone_responsavel, nivel_tea, status_ativo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getIdClinica());
            ps.setString(2, p.getNomePaciente());
            ps.setDate(3, Date.valueOf(p.getDataNascimento()));
            ps.setString(4, p.getResponsavel());
            ps.setString(5, p.getTelefoneResponsavel());
            ps.setInt(6, p.getNivelTea());
            ps.setBoolean(7, p.isStatusAtivo());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - Listar todos os pacientes ativos de uma clínica
    public List<Paciente> listarPorClinica(int idClinica) {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente WHERE id_clinica = ? AND status_ativo = TRUE ORDER BY nome_paciente";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClinica);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Paciente p = new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getInt("id_clinica"),
                    rs.getString("nome_paciente"),
                    rs.getDate("data_nascimento").toLocalDate(),
                    rs.getString("responsavel"),
                    rs.getString("telefone_responsavel"),
                    rs.getInt("nivel_tea"),
                    rs.getBoolean("status_ativo")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // READ - Buscar por nome (barra de pesquisa)
    public List<Paciente> buscarPorNome(String nome, int idClinica) {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente WHERE id_clinica = ? " +
                     "AND LOWER(nome_paciente) LIKE LOWER(?) AND status_ativo = TRUE";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClinica);
            ps.setString(2, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Paciente p = new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getInt("id_clinica"),
                    rs.getString("nome_paciente"),
                    rs.getDate("data_nascimento").toLocalDate(),
                    rs.getString("responsavel"),
                    rs.getString("telefone_responsavel"),
                    rs.getInt("nivel_tea"),
                    rs.getBoolean("status_ativo")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // UPDATE - Atualizar paciente
    public boolean atualizar(Paciente p) {
        String sql = "UPDATE paciente SET nome_paciente=?, data_nascimento=?, responsavel=?, " +
                     "telefone_responsavel=?, nivel_tea=?, status_ativo=? WHERE id_paciente=?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNomePaciente());
            ps.setDate(2, Date.valueOf(p.getDataNascimento()));
            ps.setString(3, p.getResponsavel());
            ps.setString(4, p.getTelefoneResponsavel());
            ps.setInt(5, p.getNivelTea());
            ps.setBoolean(6, p.isStatusAtivo());
            ps.setInt(7, p.getIdPaciente());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE lógico - Desativar paciente (não apaga do banco)
    public boolean desativar(int idPaciente) {
        String sql = "UPDATE paciente SET status_ativo = FALSE WHERE id_paciente = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}