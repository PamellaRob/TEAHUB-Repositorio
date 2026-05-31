/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teahub;

import model.Paciente;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.time.LocalDate;

import javax.swing.JDialog;

public class DetalhesPacienteDialog extends JDialog {

    private final Color COR_PRIMARIA = new Color(0, 128, 128);
    private final Color COR_DESTAQUE = new Color(88, 44, 131);

    public DetalhesPacienteDialog(JFrame pai, Paciente paciente) {
        super(pai, "Detalhes do Paciente", true);
        setSize(420, 380);
        setLocationRelativeTo(pai);
        setResizable(false);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 5, 6, 5);

        // Título
        JLabel titulo = new JLabel(paciente.getNomePaciente());
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(COR_PRIMARIA);
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        painel.add(titulo, g);

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(COR_PRIMARIA);
        g.gridy = 1;
        painel.add(sep, g);

        // Dados
        String[] labels = {"Data de Nascimento:", "Responsável:", "Telefone:", "Nível TEA:", "Status:"};
        String[] valores = {
            paciente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
    + "  |  " + calcularIdade(paciente.getDataNascimento()) + " anos",
            paciente.getResponsavel(),
            paciente.getTelefoneResponsavel(),
            "Nível " + paciente.getNivelTea() + descricaoNivel(paciente.getNivelTea()),
            paciente.isStatusAtivo() ? "Ativo" : "Inativo"
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lbl.setForeground(new Color(80, 80, 100));
            g.gridx = 0; g.gridy = i + 2; g.gridwidth = 1;
            painel.add(lbl, g);

            JLabel val = new JLabel(valores[i]);
            val.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            val.setForeground(Color.DARK_GRAY);
            g.gridx = 1;
            painel.add(val, g);
        }

        // Botão fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnFechar.setBackground(COR_DESTAQUE);
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setBorderPainted(false);
        btnFechar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnFechar.addActionListener(e -> dispose());
        g.gridx = 0; g.gridy = labels.length + 2; g.gridwidth = 2;
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        g.insets = new Insets(20, 5, 5, 5);
        painel.add(btnFechar, g);

        setContentPane(painel);
    }

    private String descricaoNivel(int nivel) {
        return switch (nivel) {
            case 1 -> " — Suporte necessário";
            case 2 -> " — Suporte substancial";
            case 3 -> " — Suporte muito substancial";
            default -> "";
        };
    }

    private int calcularIdade(LocalDate dataNascimento) {
    return Period.between(dataNascimento, LocalDate.now()).getYears();
}
}
