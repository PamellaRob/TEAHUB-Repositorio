/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;


public class Paciente {
    private int idPaciente;
    private int idClinica;
    private String nomePaciente;
    private LocalDate dataNascimento;
    private String responsavel;
    private String telefoneResponsavel;
    private int nivelTea;
    private boolean statusAtivo;

    // Construtor vazio
    public Paciente() {}

    // Construtor completo
    public Paciente(int idPaciente, int idClinica, String nomePaciente,
                    LocalDate dataNascimento, String responsavel,
                    String telefoneResponsavel, int nivelTea, boolean statusAtivo) {
        this.idPaciente = idPaciente;
        this.idClinica = idClinica;
        this.nomePaciente = nomePaciente;
        this.dataNascimento = dataNascimento;
        this.responsavel = responsavel;
        this.telefoneResponsavel = telefoneResponsavel;
        this.nivelTea = nivelTea;
        this.statusAtivo = statusAtivo;
    }

    // Getters e Setters
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public int getIdClinica() { return idClinica; }
    public void setIdClinica(int idClinica) { this.idClinica = idClinica; }

    public String getNomePaciente() { return nomePaciente; }
    public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public String getTelefoneResponsavel() { return telefoneResponsavel; }
    public void setTelefoneResponsavel(String telefoneResponsavel) { this.telefoneResponsavel = telefoneResponsavel; }

    public int getNivelTea() { return nivelTea; }
    public void setNivelTea(int nivelTea) { this.nivelTea = nivelTea; }

    public boolean isStatusAtivo() { return statusAtivo; }
    public void setStatusAtivo(boolean statusAtivo) { this.statusAtivo = statusAtivo; }

    @Override
    public String toString() { return nomePaciente; }
}
