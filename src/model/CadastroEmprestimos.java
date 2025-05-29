package model;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import dao.conexaoDAO;
import utils.Validador;

public class CadastroEmprestimos extends JFrame {

    conexaoDAO dao = new conexaoDAO();
    private static final long serialVersionUID = 1L;
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private JPanel contentPane;
    private JLabel lblData;
    private JTextField txtIdEmprestimo;
    private JTextField txtMatriculaAluno;
    private JTextField txtNomeAluno;
    private JTextField txtIdLivro;
    private JTextField txtTituloLivro;
    private JTextField txtDataEmprestimo;
    private JTextField txtDataDevolucao;
    private JTextField txtEstoqueDisponivel;
    private JButton btnAdicionar;
    private JButton btnExcluir;
    private JButton btnBuscar;
    private JButton btnBuscarAluno;
    private JButton btnBuscarLivro;
    private JButton btnEditar;
    private JButton btnDevolver;

    public static void main(String[] args) {
         EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CadastroEmprestimos frame = new CadastroEmprestimos();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private String formatarDataParaBanco(String dataDigitada) {
        try {
            String dataLimpa = dataDigitada.replaceAll("[^0-9]", "");
            
            if (dataLimpa.length() == 8) {
                String dia = dataLimpa.substring(0, 2);
                String mes = dataLimpa.substring(2, 4);
                String ano = dataLimpa.substring(4, 8);
                return ano + "-" + mes + "-" + dia;
            } else if (dataDigitada.contains("/")) {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
                return formatoBanco.format(formatoEntrada.parse(dataDigitada));
            } else if (dataDigitada.contains("-")) {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
                return formatoBanco.format(formatoEntrada.parse(dataDigitada));
            }
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    private String formatarDataParaTela(String dataBanco) {
        try {
            if (dataBanco != null && !dataBanco.isEmpty()) {
                SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatoTela = new SimpleDateFormat("dd/MM/yyyy");
                return formatoTela.format(formatoBanco.parse(dataBanco));
            }
            
            return "";
            
        } catch (Exception e) {
            return dataBanco;
        }
    }

    public CadastroEmprestimos() {
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                setarData();
            }
        });
        setTitle("CADASTRO EMPRÉSTIMOS - CONSULTA");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(CadastroEmprestimos.class.getResource("/img/icone.png")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 543, 550);
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
        lblData.setBounds(10, 11, 390, 19);
        panel.add(lblData);
        lblData.setForeground(SystemColor.text);
        lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel lblNewLabel_1 = new JLabel("DADOS DO EMPRÉSTIMO");
        lblNewLabel_1.setBounds(71, 52, 250, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblIdEmprestimo = new JLabel("ID EMPRÉSTIMO");
        lblIdEmprestimo.setBounds(10, 86, 120, 14);
        contentPane.add(lblIdEmprestimo);

        txtIdEmprestimo = new JTextField();
        txtIdEmprestimo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                String caracteres = "0123456789";
                if (!caracteres.contains(e.getKeyChar() + "")) {
                    e.consume();
                }
            }
        });
        txtIdEmprestimo.setBounds(130, 83, 92, 20);
        contentPane.add(txtIdEmprestimo);
        txtIdEmprestimo.setColumns(10);
        txtIdEmprestimo.setDocument(new Validador(10));

        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarPorId();
            }
        });
        btnBuscar.setForeground(new Color(64, 128, 128));
        btnBuscar.setBounds(232, 81, 83, 20);
        contentPane.add(btnBuscar);
        
        JLabel lblSeparadorAluno = new JLabel("--- DADOS DO ALUNO ---");
        lblSeparadorAluno.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSeparadorAluno.setBounds(10, 120, 200, 14);
        contentPane.add(lblSeparadorAluno);

        JLabel lblMatriculaAluno = new JLabel("MATRÍCULA");
        lblMatriculaAluno.setBounds(10, 145, 80, 14);
        contentPane.add(lblMatriculaAluno);

        txtMatriculaAluno = new JTextField();
        txtMatriculaAluno.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                String caracteres = "0123456789";
                if (!caracteres.contains(e.getKeyChar() + "")) {
                    e.consume();
                }
            }
        });
        
        txtMatriculaAluno.setBounds(90, 142, 92, 20);
        contentPane.add(txtMatriculaAluno);
        txtMatriculaAluno.setColumns(10);
        txtMatriculaAluno.setDocument(new Validador(20));

        btnBuscarAluno = new JButton("Buscar");
        btnBuscarAluno.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnBuscarAluno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarAluno();
            }
        });
        
        btnBuscarAluno.setForeground(new Color(64, 128, 128));
        btnBuscarAluno.setBounds(192, 142, 70, 20);
        contentPane.add(btnBuscarAluno);

        JLabel lblNomeAluno = new JLabel("NOME ALUNO");
        lblNomeAluno.setBounds(10, 175, 80, 14);
        contentPane.add(lblNomeAluno);

        txtNomeAluno = new JTextField();
        txtNomeAluno.setEditable(false);
        txtNomeAluno.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtNomeAluno.setBounds(90, 172, 250, 20);
        contentPane.add(txtNomeAluno);
        txtNomeAluno.setColumns(10);

        JLabel lblSeparadorLivro = new JLabel("--- DADOS DO LIVRO ---");
        lblSeparadorLivro.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSeparadorLivro.setBounds(10, 210, 200, 14);
        contentPane.add(lblSeparadorLivro);

        JLabel lblIdLivro = new JLabel("ID LIVRO");
        lblIdLivro.setBounds(10, 235, 80, 14);
        contentPane.add(lblIdLivro);

        txtIdLivro = new JTextField();
        txtIdLivro.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                String caracteres = "0123456789";
                if (!caracteres.contains(e.getKeyChar() + "")) {
                    e.consume();
                }
            }
        });
        
        txtIdLivro.setBounds(90, 232, 92, 20);
        contentPane.add(txtIdLivro);
        txtIdLivro.setColumns(10);
        txtIdLivro.setDocument(new Validador(10));

        btnBuscarLivro = new JButton("Buscar");
        btnBuscarLivro.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnBuscarLivro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarLivro();
            }
        });
        
        btnBuscarLivro.setForeground(new Color(64, 128, 128));
        btnBuscarLivro.setBounds(192, 232, 70, 20);
        contentPane.add(btnBuscarLivro);

        JLabel lblTituloLivro = new JLabel("TÍTULO");
        lblTituloLivro.setBounds(10, 265, 80, 14);
        contentPane.add(lblTituloLivro);

        txtTituloLivro = new JTextField();
        txtTituloLivro.setEditable(false);
        txtTituloLivro.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtTituloLivro.setBounds(90, 262, 250, 20);
        contentPane.add(txtTituloLivro);
        txtTituloLivro.setColumns(10);

        JLabel lblEstoque = new JLabel("ESTOQUE");
        lblEstoque.setBounds(323, 235, 80, 14);
        contentPane.add(lblEstoque);

        txtEstoqueDisponivel = new JTextField();
        txtEstoqueDisponivel.setEditable(false);
        txtEstoqueDisponivel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtEstoqueDisponivel.setBounds(381, 232, 60, 20);
        contentPane.add(txtEstoqueDisponivel);
        txtEstoqueDisponivel.setColumns(10);

        JLabel lblSeparadorDatas = new JLabel("--- DATAS ---");
        lblSeparadorDatas.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSeparadorDatas.setBounds(10, 300, 200, 14);
        contentPane.add(lblSeparadorDatas);

        JLabel lblDataEmprestimo = new JLabel("DATA EMPRÉSTIMO");
        lblDataEmprestimo.setBounds(10, 325, 112, 14);
        contentPane.add(lblDataEmprestimo);

        txtDataEmprestimo = new JTextField();
        txtDataEmprestimo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtDataEmprestimo.setBounds(130, 322, 120, 20);
        contentPane.add(txtDataEmprestimo);
        txtDataEmprestimo.setColumns(10);

        JLabel lblDataDevolucao = new JLabel("DATA DEVOLUÇÃO");
        lblDataDevolucao.setBounds(264, 325, 120, 14);
        contentPane.add(lblDataDevolucao);

        txtDataDevolucao = new JTextField();
        txtDataDevolucao.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtDataDevolucao.setBounds(371, 322, 120, 20);
        contentPane.add(txtDataDevolucao);
        txtDataDevolucao.setColumns(10);

        btnAdicionar = new JButton("EMPRESTAR");
        btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnAdicionar.setEnabled(false);
        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarEmprestimo();
            }
        });
        
        btnAdicionar.setToolTipText("Realizar empréstimo");
        btnAdicionar.setBounds(10, 467, 100, 33);
        contentPane.add(btnAdicionar);

        btnDevolver = new JButton("DEVOLVER");
        btnDevolver.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnDevolver.setEnabled(false);
        btnDevolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                devolverLivro();
            }
        });
        
        btnDevolver.setToolTipText("Devolver livro");
        btnDevolver.setBounds(120, 467, 100, 33);
        contentPane.add(btnDevolver);

        btnEditar = new JButton("EDITAR");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editar();
            }
        });
        
        btnEditar.setToolTipText("Editar empréstimo");
        btnEditar.setBounds(230, 467, 83, 33);
        contentPane.add(btnEditar);

        btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });
        
        btnExcluir.setToolTipText("Excluir empréstimo");
        btnExcluir.setBounds(323, 467, 83, 33);
        contentPane.add(btnExcluir);

        JButton btnReset = new JButton("LIMPAR");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        
        btnReset.setToolTipText("Limpar campos");
        btnReset.setBounds(420, 467, 92, 33);
        contentPane.add(btnReset);

        JButton btnNovoEmprestimo = new JButton("NOVO EMPRÉSTIMO");
        btnNovoEmprestimo.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNovoEmprestimo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                novoEmprestimo();
            }
        });
        
        btnNovoEmprestimo.setToolTipText("Iniciar novo empréstimo");
        btnNovoEmprestimo.setBounds(10, 423, 140, 33);
        contentPane.add(btnNovoEmprestimo);

        this.setLocationRelativeTo(null);
    }

    private void setarData() {
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
        lblData.setText(formatador.format(data));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDataEmprestimo.setText(sdf.format(data));
    }

    private void novoEmprestimo() {
        reset();
        txtIdEmprestimo.setEditable(false);
        btnBuscar.setEnabled(false);
        btnAdicionar.setEnabled(true);
        txtMatriculaAluno.requestFocus();
        
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDataEmprestimo.setText(sdf.format(data));
    }

    private void buscarPorId() {
        if (txtIdEmprestimo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite o ID do empréstimo");
            txtIdEmprestimo.requestFocus();
        } else {
            String readId = "SELECT e.id_emprestimo, e.id_aluno, e.id_livro, e.data_emprestimo, e.data_devolucao, " +
                          "a.matricula, a.nome_aluno, l.titulo, l.quantidade_estoque " +
                          "FROM emprestimos e " +
                          "INNER JOIN alunos a ON e.id_aluno = a.id_aluno " +
                          "INNER JOIN livros l ON e.id_livro = l.id_livro " +
                          "WHERE e.id_emprestimo = ?";
            try {
                con = dao.conectar();
                pst = con.prepareStatement(readId);
                pst.setString(1, txtIdEmprestimo.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    txtMatriculaAluno.setText(rs.getString("matricula"));
                    txtNomeAluno.setText(rs.getString("nome_aluno"));
                    txtIdLivro.setText(rs.getString("id_livro"));
                    txtTituloLivro.setText(rs.getString("titulo"));
                    txtEstoqueDisponivel.setText(rs.getString("quantidade_estoque"));
                    txtDataEmprestimo.setText(formatarDataParaTela(rs.getString("data_emprestimo")));
                    
                    String dataDevolucao = rs.getString("data_devolucao");
                    txtDataDevolucao.setText(dataDevolucao != null ? formatarDataParaTela(dataDevolucao) : "");

                    txtIdEmprestimo.setEditable(false);
                    btnBuscar.setEnabled(false);
                    btnEditar.setEnabled(true);
                    btnExcluir.setEnabled(true);
                    btnDevolver.setEnabled(dataDevolucao == null);
                } else {
                    JOptionPane.showMessageDialog(null, "Empréstimo não encontrado");
                    int confirma = JOptionPane.showConfirmDialog(null,
                            "Deseja iniciar um novo empréstimo?", "Aviso", JOptionPane.YES_NO_OPTION);
                    if (confirma == JOptionPane.YES_OPTION) {
                        novoEmprestimo();
                    } else {
                        reset();
                    }
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Erro na busca: " + e.getMessage());
            }
        }
    }

    private void buscarAluno() {
        if (txtMatriculaAluno.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite a matrícula do aluno");
            txtMatriculaAluno.requestFocus();
        } else {
            String readAluno = "SELECT id_aluno, nome_aluno FROM alunos WHERE matricula = ?";
            try {
                con = dao.conectar();
                pst = con.prepareStatement(readAluno);
                pst.setString(1, txtMatriculaAluno.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    txtNomeAluno.setText(rs.getString("nome_aluno"));
                } else {
                    JOptionPane.showMessageDialog(null, "Aluno não encontrado");
                    txtNomeAluno.setText("");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Erro ao buscar aluno: " + e.getMessage());
            }
        }
    }

    private void buscarLivro() {
        if (txtIdLivro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite o ID do livro");
            txtIdLivro.requestFocus();
        } else {
            String readLivro = "SELECT titulo, quantidade_estoque FROM livros WHERE id_livro = ?";
            try {
                con = dao.conectar();
                pst = con.prepareStatement(readLivro);
                pst.setString(1, txtIdLivro.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    txtTituloLivro.setText(rs.getString("titulo"));
                    txtEstoqueDisponivel.setText(rs.getString("quantidade_estoque"));
                } else {
                    JOptionPane.showMessageDialog(null, "Livro não encontrado");
                    txtTituloLivro.setText("");
                    txtEstoqueDisponivel.setText("");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Erro ao buscar livro: " + e.getMessage());
            }
        }
    }

    private void realizarEmprestimo() {
        if (txtMatriculaAluno.getText().isEmpty() || txtNomeAluno.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um aluno válido");
            txtMatriculaAluno.requestFocus();
        } else if (txtIdLivro.getText().isEmpty() || txtTituloLivro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um livro válido");
            txtIdLivro.requestFocus();
        } else if (txtDataEmprestimo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha a data do empréstimo");
            txtDataEmprestimo.requestFocus();
        } else {
            int estoque = Integer.parseInt(txtEstoqueDisponivel.getText());
            if (estoque <= 0) {
                JOptionPane.showMessageDialog(null, "Livro sem estoque disponível!");
                return;
            }

            String dataEmprestimoFormatada = formatarDataParaBanco(txtDataEmprestimo.getText());
            if (dataEmprestimoFormatada == null) {
                JOptionPane.showMessageDialog(null, "Data de empréstimo inválida! Use o formato dd/mm/aaaa");
                txtDataEmprestimo.requestFocus();
                return;
            }

            try {
                con = dao.conectar();
                con.setAutoCommit(false);

                String buscarAluno = "SELECT id_aluno FROM alunos WHERE matricula = ?";
                pst = con.prepareStatement(buscarAluno);
                pst.setString(1, txtMatriculaAluno.getText());
                rs = pst.executeQuery();
                
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Aluno não encontrado");
                    con.rollback();
                    con.close();
                    return;
                }
                int idAluno = rs.getInt("id_aluno");

                String insertEmprestimo = "INSERT INTO emprestimos(id_aluno, id_livro, data_emprestimo) VALUES(?, ?, ?)";
                pst = con.prepareStatement(insertEmprestimo);
                pst.setInt(1, idAluno);
                pst.setInt(2, Integer.parseInt(txtIdLivro.getText()));
                pst.setString(3, dataEmprestimoFormatada);

                int confirmaEmprestimo = pst.executeUpdate();

                if (confirmaEmprestimo == 1) {
                	
                    String updateEstoque = "UPDATE livros SET quantidade_estoque = quantidade_estoque - 1 WHERE id_livro = ?";
                    pst = con.prepareStatement(updateEstoque);
                    pst.setInt(1, Integer.parseInt(txtIdLivro.getText()));
                    
                    int confirmaEstoque = pst.executeUpdate();
                    
                    if (confirmaEstoque == 1) {
                        con.commit();
                        

                        String buscarId = "SELECT id_emprestimo FROM emprestimos WHERE id_aluno = ? AND id_livro = ? AND data_emprestimo = ? ORDER BY id_emprestimo DESC LIMIT 1";
                        pst = con.prepareStatement(buscarId);
                        pst.setInt(1, idAluno);
                        pst.setInt(2, Integer.parseInt(txtIdLivro.getText()));
                        pst.setString(3, dataEmprestimoFormatada);
                        rs = pst.executeQuery();
                        
                        if (rs.next()) {
                            String idGerado = rs.getString("id_emprestimo");
                            txtIdEmprestimo.setText(idGerado);
                            
                            JOptionPane.showMessageDialog(null,
                                "Empréstimo realizado com sucesso!\n" +
                                "ID do empréstimo: " + idGerado);
                            

                            int novoEstoque = estoque - 1;
                            txtEstoqueDisponivel.setText(String.valueOf(novoEstoque));
                            
                            txtIdEmprestimo.setEditable(false);
                            btnBuscar.setEnabled(false);
                            btnAdicionar.setEnabled(false);
                            btnEditar.setEnabled(true);
                            btnExcluir.setEnabled(true);
                            btnDevolver.setEnabled(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
                            reset();
                        }
                    } else {
                        con.rollback();
                        JOptionPane.showMessageDialog(null, "Erro ao atualizar estoque!");
                    }
                } else {
                    con.rollback();
                    JOptionPane.showMessageDialog(null, "Erro ao realizar empréstimo!");
                }
                
                con.close();
            } catch (Exception e) {
                try {
                    if (con != null) {
                        con.rollback();
                        con.close();
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Erro ao realizar empréstimo: " + e.getMessage());
            }
        }
    }

    private void devolverLivro() {
        if (txtDataDevolucao.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Preencha a data de devolução");
            txtDataDevolucao.requestFocus();
        } else {
            String dataDevolucaoFormatada = formatarDataParaBanco(txtDataDevolucao.getText());
            if (dataDevolucaoFormatada == null) {
                JOptionPane.showMessageDialog(null, "Data de devolução inválida! Use o formato dd/mm/aaaa");
                txtDataDevolucao.requestFocus();
                return;
            }

            try {
                con = dao.conectar();
                con.setAutoCommit(false);


                String updateDevolucao = "UPDATE emprestimos SET data_devolucao = ? WHERE id_emprestimo = ?";
                pst = con.prepareStatement(updateDevolucao);
                pst.setString(1, dataDevolucaoFormatada);
                pst.setInt(2, Integer.parseInt(txtIdEmprestimo.getText()));

                int confirmaDevolucao = pst.executeUpdate();

                if (confirmaDevolucao == 1) {

                    String updateEstoque = "UPDATE livros SET quantidade_estoque = quantidade_estoque + 1 WHERE id_livro = ?";
                    pst = con.prepareStatement(updateEstoque);
                    pst.setInt(1, Integer.parseInt(txtIdLivro.getText()));

                    int confirmaEstoque = pst.executeUpdate();

                    if (confirmaEstoque == 1) {
                        con.commit();
                        JOptionPane.showMessageDialog(null, "Devolução registrada com sucesso!");


                        int estoqueAtual = Integer.parseInt(txtEstoqueDisponivel.getText());
                        txtEstoqueDisponivel.setText(String.valueOf(estoqueAtual + 1));

                        reset();
                    } else {
                        con.rollback();
                        JOptionPane.showMessageDialog(null, "Erro ao atualizar o estoque!");
                    }
                } else {
                    con.rollback();
                    JOptionPane.showMessageDialog(null, "Erro ao registrar a devolução!");
                }

                con.close();
            } catch (Exception e) {
                try {
                    if (con != null) {
                        con.rollback();
                        con.close();
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Erro ao registrar devolução: " + e.getMessage());
            }
        }
    }

    private void editar() {
        if (txtIdEmprestimo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um empréstimo para editar");
            txtIdEmprestimo.requestFocus();
        } else if (txtDataDevolucao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha a data de devolução");
            txtDataDevolucao.requestFocus();
        } else {
            String dataDevolucaoFormatada = formatarDataParaBanco(txtDataDevolucao.getText());
            if (dataDevolucaoFormatada == null) {
                JOptionPane.showMessageDialog(null, "Data de devolução inválida! Use o formato dd/mm/aaaa");
                txtDataDevolucao.requestFocus();
                return;
            }

            String update = "UPDATE emprestimos SET data_devolucao = ? WHERE id_emprestimo = ?";
            try {
                con = dao.conectar();
                pst = con.prepareStatement(update);
                pst.setString(1, dataDevolucaoFormatada);
                pst.setInt(2, Integer.parseInt(txtIdEmprestimo.getText()));

                int confirma = pst.executeUpdate();
                if (confirma == 1) {
                    JOptionPane.showMessageDialog(null, "Empréstimo atualizado com sucesso");
                    reset();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar empréstimo");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Erro ao editar: " + e.getMessage());
            }
        }
    }

    private void excluir() {
        if (txtIdEmprestimo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um empréstimo para excluir");
            txtIdEmprestimo.requestFocus();
        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste empréstimo?", "Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                String delete = "DELETE FROM emprestimos WHERE id_emprestimo = ?";
                try {
                    con = dao.conectar();
                    pst = con.prepareStatement(delete);
                    pst.setInt(1, Integer.parseInt(txtIdEmprestimo.getText()));
                    int excluido = pst.executeUpdate();
                    if (excluido == 1) {
                        JOptionPane.showMessageDialog(null, "Empréstimo excluído com sucesso");
                        reset();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir empréstimo");
                    }
                    con.close();
                } catch (Exception e) {
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
                }
            }
        }
    }

    private void reset() {
        txtIdEmprestimo.setText(null);
        txtMatriculaAluno.setText(null);
        txtNomeAluno.setText(null);
        txtIdLivro.setText(null);
        txtTituloLivro.setText(null);
        txtEstoqueDisponivel.setText(null);
        txtDataEmprestimo.setText(null);
        txtDataDevolucao.setText(null);

        txtIdEmprestimo.setEditable(true);
        btnBuscar.setEnabled(true);
        btnAdicionar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnDevolver.setEnabled(false);
    }
}