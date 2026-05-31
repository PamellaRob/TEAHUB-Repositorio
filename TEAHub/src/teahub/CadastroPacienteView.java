/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teahub;

import dao.PacienteDAO;
import model.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CadastroPacienteView extends JPanel {

    // Campos do formulário
    private JTextField txtNome, txtDataNascimento, txtResponsavel, txtTelefone;
    private JSpinner spnNivelTea;
    private JCheckBox chkAtivo;

    // Pesquisa e tabela
    private JTextField txtPesquisa;
    private JTable tabelaPacientes;
    private DefaultTableModel modeloTabela;

    // Controle
    private PacienteDAO dao = new PacienteDAO();
    private int idClinica = 1; // substituir pelo id da sessão
    private int idPacienteSelecionado = -1;

    // Cores do TEAHub
    private final Color COR_PRIMARIA  = new Color(0, 128, 128);   // teal
    private final Color COR_DESTAQUE  = new Color(88, 44, 131);   // roxo
    private final Color COR_FUNDO     = new Color(245, 245, 250);
    private final Color COR_BRANCO    = Color.WHITE;

    public CadastroPacienteView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(COR_FUNDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(criarPainelTitulo(),     BorderLayout.NORTH);
        add(criarPainelCentral(),    BorderLayout.CENTER);
        add(criarPainelBotoes(),     BorderLayout.SOUTH);

        carregarTabela("");
    }

    // Título
    private JPanel criarPainelTitulo() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painel.setBackground(COR_FUNDO);

        try {
        ImageIcon logoOriginal = new ImageIcon(getClass().getResource("/imagens/logo.png"));
        Image logoRedimensionado = logoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoRedimensionado));
        painel.add(logoLabel);
    } catch (Exception e) {
        System.out.println("Logo não encontrada: " + e.getMessage());
    }

    JLabel titulo = new JLabel("Cadastro de Pacientes");
    titulo.setFont(new Font("Segoe UI", Font.BOLD, 25));
    titulo.setForeground(COR_PRIMARIA);
    painel.add(titulo);

    return painel;
    }

    // Painel central (formulário + tabela) 
    private JSplitPane criarPainelCentral() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                criarPainelFormulario(), criarPainelTabela());
        split.setDividerLocation(370);
        split.setBackground(COR_FUNDO);
        return split;
    }

    // Formulário 
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_BRANCO);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 5, 5, 5);

        // Nome
        adicionarLabel(painel, g, "Nome do Paciente:", 0);
        txtNome = criarTextField();
        adicionarCampo(painel, g, txtNome, 1);

        // Data de nascimento
        adicionarLabel(painel, g, "Data de Nascimento (dd/MM/yyyy):", 2);
        txtDataNascimento = criarTextField();
        adicionarCampo(painel, g, txtDataNascimento, 3);

        // Responsável
        adicionarLabel(painel, g, "Responsável:", 4);
        txtResponsavel = criarTextField();
        adicionarCampo(painel, g, txtResponsavel, 5);

        // Telefone
        adicionarLabel(painel, g, "Telefone do Responsável:", 6);
        txtTelefone = criarTextField();
        adicionarCampo(painel, g, txtTelefone, 7);

        // Nível TEA
        adicionarLabel(painel, g, "Nível TEA (1 a 3):", 8);
        spnNivelTea = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));
        spnNivelTea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        g.gridx = 0; g.gridy = 9; g.gridwidth = 2;
        painel.add(spnNivelTea, g);

        // Status ativo
        chkAtivo = new JCheckBox("Paciente Ativo");
        chkAtivo.setSelected(true);
        chkAtivo.setBackground(COR_BRANCO);
        chkAtivo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        g.gridy = 10;
        painel.add(chkAtivo, g);

        return painel;
    }

    private void adicionarLabel(JPanel p, GridBagConstraints g, String texto, int linha) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(60, 60, 80));
        g.gridx = 0; g.gridy = linha; g.gridwidth = 2;
        p.add(label, g);
    }

    private void adicionarCampo(JPanel p, GridBagConstraints g, JComponent campo, int linha) {
        g.gridx = 0; g.gridy = linha; g.gridwidth = 2;
        p.add(campo, g);
    }

    private JTextField criarTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 200)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        return tf;
    }

    // Painel da tabela com pesquisa
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBackground(COR_FUNDO);

        // Barra de pesquisa
        JPanel painelPesquisa = new JPanel(new BorderLayout(5, 0));
        painelPesquisa.setBackground(COR_FUNDO);
        JLabel lblBusca = new JLabel("Buscar:");
        lblBusca.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPesquisa = criarTextField();
        txtPesquisa.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { carregarTabela(txtPesquisa.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { carregarTabela(txtPesquisa.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { carregarTabela(txtPesquisa.getText()); }
        });
        painelPesquisa.add(lblBusca, BorderLayout.WEST);
        painelPesquisa.add(txtPesquisa, BorderLayout.CENTER);

        // Tabela
        String[] colunas = {"ID", "Nome", "Responsável", "Telefone", "Nível TEA", "Ativo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaPacientes = new JTable(modeloTabela);
        tabelaPacientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaPacientes.setRowHeight(26);
        tabelaPacientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelaPacientes.getTableHeader().setBackground(COR_PRIMARIA);
        tabelaPacientes.getTableHeader().setForeground(Color.WHITE);
        tabelaPacientes.setSelectionBackground(new Color(200, 230, 230));

        // Duplo clique abre detalhes
        tabelaPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) abrirDetalhes();
                else if (e.getClickCount() == 1) preencherFormulario();
            }
        });

        // Esconder coluna ID
        tabelaPacientes.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaPacientes.getColumnModel().getColumn(0).setMaxWidth(0);

        painel.add(painelPesquisa, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaPacientes), BorderLayout.CENTER);
        return painel;
    }

    // Botões 
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painel.setBackground(COR_FUNDO);

        JButton btnNovo     = criarBotao("Novo",     COR_PRIMARIA);
        JButton btnSalvar   = criarBotao("Salvar",   new Color(34, 150, 80));
        JButton btnEditar   = criarBotao("Editar",   new Color(200, 130, 0));
        JButton btnDesativar = criarBotao("Desativar", new Color(180, 50, 50));
        JButton btnDetalhes = criarBotao("Detalhes", COR_DESTAQUE);

        btnNovo.addActionListener(e -> limparFormulario());
        btnSalvar.addActionListener(e -> salvar());
        btnEditar.addActionListener(e -> editar());
        btnDesativar.addActionListener(e -> desativar());
        btnDetalhes.addActionListener(e -> abrirDetalhes());

        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnEditar);
        painel.add(btnDesativar);
        painel.add(btnDetalhes);
        return painel;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return btn;
    }

    // Lógica 
    private void carregarTabela(String filtro) {
        modeloTabela.setRowCount(0);
        List<Paciente> lista = filtro.isBlank()
                ? dao.listarPorClinica(idClinica)
                : dao.buscarPorNome(filtro, idClinica);
        for (Paciente p : lista) {
            modeloTabela.addRow(new Object[]{
                p.getIdPaciente(),
                p.getNomePaciente(),
                p.getResponsavel(),
                p.getTelefoneResponsavel(),
                "Nível " + p.getNivelTea(),
                p.isStatusAtivo() ? "Ativo" : "Inativo"
            });
        }
    }

    private void preencherFormulario() {
        int linha = tabelaPacientes.getSelectedRow();
        if (linha < 0) return;
        idPacienteSelecionado = (int) modeloTabela.getValueAt(linha, 0);
        // Busca dados completos pelo DAO para preencher todos os campos
        List<Paciente> todos = dao.listarPorClinica(idClinica);
        for (Paciente p : todos) {
            if (p.getIdPaciente() == idPacienteSelecionado) {
                txtNome.setText(p.getNomePaciente());
                txtDataNascimento.setText(p.getDataNascimento()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                txtResponsavel.setText(p.getResponsavel());
                txtTelefone.setText(p.getTelefoneResponsavel());
                spnNivelTea.setValue(p.getNivelTea());
                chkAtivo.setSelected(p.isStatusAtivo());
                break;
            }
        }
    }

    private void salvar() {
        if (!validarCampos()) return;
        Paciente p = montarPaciente();
        if (dao.cadastrar(p)) {
            JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso! ✅");
            limparFormulario();
            carregarTabela("");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        if (idPacienteSelecionado < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela.");
            return;
        }
        if (!validarCampos()) return;
        Paciente p = montarPaciente();
        p.setIdPaciente(idPacienteSelecionado);
        if (dao.atualizar(p)) {
            JOptionPane.showMessageDialog(this, "Paciente atualizado! ✅");
            limparFormulario();
            carregarTabela("");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void desativar() {
        if (idPacienteSelecionado < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela.");
            return;
        }
        int confirma = JOptionPane.showConfirmDialog(this,
                "Deseja desativar este paciente?", "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            if (dao.desativar(idPacienteSelecionado)) {
                JOptionPane.showMessageDialog(this, "Paciente desativado.");
                limparFormulario();
                carregarTabela("");
            }
        }
    }

    private void abrirDetalhes() {
        int linha = tabelaPacientes.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela.");
            return;
        }
        int id = (int) modeloTabela.getValueAt(linha, 0);
        List<Paciente> todos = dao.listarPorClinica(idClinica);
        for (Paciente p : todos) {
            if (p.getIdPaciente() == id) {
                new DetalhesPacienteDialog((JFrame) SwingUtilities.getWindowAncestor(this), p).setVisible(true);
                break;
            }
        }
    }

    private Paciente montarPaciente() {
        Paciente p = new Paciente();
        p.setIdClinica(idClinica);
        p.setNomePaciente(txtNome.getText().trim());
        p.setDataNascimento(LocalDate.parse(txtDataNascimento.getText().trim(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        p.setResponsavel(txtResponsavel.getText().trim());
        p.setTelefoneResponsavel(txtTelefone.getText().trim());
        p.setNivelTea((int) spnNivelTea.getValue());
        p.setStatusAtivo(chkAtivo.isSelected());
        return p;
    }

    private boolean validarCampos() {
        if (txtNome.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o nome do paciente.");
            return false;
        }
        try {
            LocalDate.parse(txtDataNascimento.getText().trim(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.");
            return false;
        }
        if (txtResponsavel.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o responsável.");
            return false;
        }
        if (txtTelefone.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o telefone do responsável.");
            return false;
        }
        return true;
    }

    private void limparFormulario() {
        txtNome.setText("");
        txtDataNascimento.setText("");
        txtResponsavel.setText("");
        txtTelefone.setText("");
        spnNivelTea.setValue(1);
        chkAtivo.setSelected(true);
        idPacienteSelecionado = -1;
        tabelaPacientes.clearSelection();
    }
}