package model;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.conexaoDAO;

public class RelatorioMovimentacao extends JFrame {

    private static final long serialVersionUID = 1L;
    private conexaoDAO dao = new conexaoDAO();
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private JPanel contentPane;
    private JLabel lblData;
    private JTextArea txtRelatorio;
    private JButton btnSalvarTxt;
    private JButton btnRelatorioGeral;
    private JButton btnRelatorioAlunos;
    private JButton btnRelatorioLivros;
    private JButton btnRelatorioEmprestimos;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RelatorioMovimentacao frame = new RelatorioMovimentacao();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public RelatorioMovimentacao() {
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                setarData();
            }
        });
        setTitle("RELATÓRIOS DE MOVIMENTAÇÃO");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(RelatorioMovimentacao.class.getResource("/img/icone.png")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(153, 153, 153));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(64, 128, 128));
        panel.setBounds(0, 0, 784, 41);
        contentPane.add(panel);
        panel.setLayout(null);

        lblData = new JLabel("");
        lblData.setBounds(299, 0, 390, 38);
        panel.add(lblData);
        lblData.setForeground(SystemColor.text);
        lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel lblNewLabel = new JLabel("PAINEL DE RELATÓRIOS");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(250, 50, 300, 30);
        contentPane.add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 90, 764, 300);
        contentPane.add(scrollPane);

        txtRelatorio = new JTextArea();
        txtRelatorio.setEditable(false);
        txtRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollPane.setViewportView(txtRelatorio);

        btnRelatorioGeral = new JButton("Relatório Geral");
        btnRelatorioGeral.addActionListener(e -> gerarRelatorioGeral());
        btnRelatorioGeral.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRelatorioGeral.setBounds(40, 401, 140, 30);
        contentPane.add(btnRelatorioGeral);

        btnRelatorioAlunos = new JButton("Alunos");
        btnRelatorioAlunos.addActionListener(e -> gerarRelatorioAlunos());
        btnRelatorioAlunos.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRelatorioAlunos.setBounds(190, 401, 100, 30);
        contentPane.add(btnRelatorioAlunos);
        
        btnRelatorioLivros = new JButton("Livros");
        btnRelatorioLivros.addActionListener(e -> gerarRelatorioLivros());
        btnRelatorioLivros.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRelatorioLivros.setBounds(300, 401, 100, 30);
        contentPane.add(btnRelatorioLivros);
        
        btnRelatorioEmprestimos = new JButton("Empréstimos");
        btnRelatorioEmprestimos.addActionListener(e -> gerarRelatorioEmprestimos());
        btnRelatorioEmprestimos.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRelatorioEmprestimos.setBounds(410, 401, 130, 30);
        contentPane.add(btnRelatorioEmprestimos);

        btnSalvarTxt = new JButton("Salvar como TXT");
        btnSalvarTxt.addActionListener(e -> salvarRelatorioComoTxt());
        btnSalvarTxt.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSalvarTxt.setBounds(594, 401, 160, 30);
        contentPane.add(btnSalvarTxt);
    }

    private void setarData() {
        Date data = new Date();
        SimpleDateFormat datafoma = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lblData.setText("Data/Hora: " + datafoma.format(data));
    }

    private void prepararAreaRelatorio(String titulo) {
        txtRelatorio.setText("");
        txtRelatorio.append("=========================================================================================================================\n");
        txtRelatorio.append(String.format("%70s\n", titulo));
        txtRelatorio.append("=========================================================================================================================\n");
        SimpleDateFormat sdfDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        txtRelatorio.append("Gerado em: " + sdfDataHora.format(new Date()) + "\n");
    }
    

    private void gerarRelatorioGeral() {
        prepararAreaRelatorio("RELATÓRIO DE MOVIMENTAÇÃO GERAL");
        gerarConteudoAlunos();
        gerarConteudoLivros();
        gerarConteudoEmprestimos();
    }

    private void gerarRelatorioAlunos() {
        prepararAreaRelatorio("RELATÓRIO DE ALUNOS");
        gerarConteudoAlunos();
    }

    private void gerarRelatorioLivros() {
        prepararAreaRelatorio("RELATÓRIO DE LIVROS");
        gerarConteudoLivros();
    }

    private void gerarRelatorioEmprestimos() {
        prepararAreaRelatorio("RELATÓRIO DE EMPRÉSTIMOS");
        gerarConteudoEmprestimos();
    }
    

    private void gerarConteudoAlunos() {
        txtRelatorio.append("=========================================================================================================================\n");
        txtRelatorio.append("\n----- SEÇÃO: ALUNOS ----------------------------------------------------------------------------------------------------\n");
        txtRelatorio.append(String.format("%-10s %-15s %-30s %-20s\n", "ID ALUNO", "MATRICULA", "NOME COMPLETO", "DATA NASCIMENTO"));
        txtRelatorio.append("-------------------------------------------------------------------------------------------------------------------------\n");
        try {
            con = dao.conectar();
            String sql = "SELECT id_aluno, matricula, nome_aluno, data_nascimento FROM Alunos";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                txtRelatorio.append(String.format("%-10s %-15s %-30s %-20s\n",
                        rs.getInt("id_aluno"),
                        rs.getString("matricula"),
                        rs.getString("nome_aluno"),
                        rs.getString("data_nascimento") != null ? new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("data_nascimento")) : ""));
            }
        } catch (SQLException e) {
            System.out.println(e);
            txtRelatorio.append("Erro ao gerar relatório de alunos: " + e.getMessage() + "\n");
        } finally {
            closeResources();
        }
    }

    private void gerarConteudoLivros() {
        txtRelatorio.append("=========================================================================================================================\n");
        txtRelatorio.append("\n----- SEÇÃO: LIVROS ----------------------------------------------------------------------------------------------------\n");
        txtRelatorio.append(String.format("%-10s %-40s %-30s %-15s %-10s\n", "ID LIVRO", "TÍTULO", "AUTOR", "ANO", "ESTOQUE"));
        txtRelatorio.append("-------------------------------------------------------------------------------------------------------------------------\n");
        try {
            con = dao.conectar();
            String sql = "SELECT id_livro, titulo, autor, ano_publicacao, quantidade_estoque FROM Livros";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                txtRelatorio.append(String.format("%-10s %-40s %-30s %-15s %-10s\n",
                        rs.getInt("id_livro"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("ano_publicacao"),
                        rs.getInt("quantidade_estoque")));
            }
        } catch (SQLException e) {
            System.out.println(e);
            txtRelatorio.append("Erro ao gerar relatório de livros: " + e.getMessage() + "\n");
        } finally {
            closeResources();
        }
    }

    private void gerarConteudoEmprestimos() {
        txtRelatorio.append("=========================================================================================================================\n");
        txtRelatorio.append("\n----- SEÇÃO: EMPRÉSTIMOS -------------------------------------------------------------------------------------------------\n");
        txtRelatorio.append(String.format("%-15s %-15s %-30s %-10s %-30s %-15s %-15s\n",
                "ID EMPRÉSTIMO", "ID ALUNO", "NOME ALUNO", "ID LIVRO", "TÍTULO LIVRO", "DATA EMPRÉSTIMO", "DATA DEVOLUÇÃO"));
        txtRelatorio.append("-------------------------------------------------------------------------------------------------------------------------\n");
        try {
            con = dao.conectar();
            String sql = "SELECT e.id_emprestimo, e.id_aluno, a.nome_aluno, e.id_livro, l.titulo, e.data_emprestimo, e.data_devolucao " +
                         "FROM Emprestimos e " +
                         "INNER JOIN Alunos a ON e.id_aluno = a.id_aluno " +
                         "INNER JOIN Livros l ON e.id_livro = l.id_livro ORDER BY e.id_emprestimo";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                txtRelatorio.append(String.format("%-15s %-15s %-30s %-10s %-30s %-15s %-15s\n",
                        rs.getInt("id_emprestimo"),
                        rs.getInt("id_aluno"),
                        rs.getString("nome_aluno"),
                        rs.getInt("id_livro"),
                        rs.getString("titulo"),
                        rs.getString("data_emprestimo") != null ? new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("data_emprestimo")) : "",
                        rs.getDate("data_devolucao") != null ? new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("data_devolucao")) : "ATIVO"
                        	
                    ));
            }
        } catch (SQLException e) {
            System.out.println(e);
            txtRelatorio.append("Erro ao gerar relatório de empréstimos: " + e.getMessage() + "\n");
        } finally {
            closeResources();
        }
    }

    private void salvarRelatorioComoTxt() {
        if (txtRelatorio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Gere um relatório antes de salvar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório como .TXT");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto (*.txt)", "txt"));
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String nomeArquivoSugerido = "RelatorioAlunosDeIA_" + sdf.format(new Date()) + ".txt";
        fileChooser.setSelectedFile(new File(nomeArquivoSugerido));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(txtRelatorio.getText());
                JOptionPane.showMessageDialog(this, "Relatório salvo como .TXT com sucesso!");
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(new File(filePath));
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o relatório como TXT: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar recursos do banco: " + ex);
        }
    }
}