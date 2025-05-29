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

public class CadastroLivros extends JFrame {

    conexaoDAO dao = new conexaoDAO();
    private static final long serialVersionUID = 1L;
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private JPanel contentPane;
    private JLabel lblData;
    private JTextField txtIdLivro;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAnoPublicacao;
    private JTextField txtQuantidadeEstoque;
    private JButton btnAdicionar;
    private JButton btnExcluir;
    private JButton btnBuscar;
    private JButton btnBuscarTitulo;
    private JButton btnEditar;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CadastroLivros frame = new CadastroLivros();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CadastroLivros() {
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                setarData();
            }
        });
        setTitle("CADASTRO DE LIVROS - CONSULTA");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(CadastroLivros.class.getResource("/img/icone.png")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 454, 450);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(153, 153, 153));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(64, 128, 128));
        panel.setBounds(0, 0, 734, 41);
        contentPane.add(panel);
        panel.setLayout(null);

        lblData = new JLabel("");
        lblData.setBounds(10, 11, 380, 27);
        panel.add(lblData);
        lblData.setForeground(SystemColor.text);
        lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel lblNewLabel_1 = new JLabel("CADASTRAMENTO DE LIVROS / CONSULTA");
        lblNewLabel_1.setBounds(130, 52, 250, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblIdLivro = new JLabel("ID LIVRO");
        lblIdLivro.setBounds(10, 86, 76, 14);
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
        txtIdLivro.setBounds(81, 83, 92, 20);
        contentPane.add(txtIdLivro);
        txtIdLivro.setColumns(10);
        txtIdLivro.setDocument(new Validador(10));

        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarPorId();
            }
        });
        
        btnBuscar.setForeground(new Color(64, 128, 128));
        btnBuscar.setBounds(183, 81, 83, 20);
        contentPane.add(btnBuscar);

        JLabel lblTitulo = new JLabel("TÍTULO");
        lblTitulo.setBounds(10, 124, 75, 20);
        contentPane.add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtTitulo.setBounds(81, 124, 250, 20);
        contentPane.add(txtTitulo);
        txtTitulo.setColumns(10);
        txtTitulo.setDocument(new Validador(150));

        btnBuscarTitulo = new JButton("Buscar");
        btnBuscarTitulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnBuscarTitulo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarPorTitulo();
            }
        });
        
        btnBuscarTitulo.setForeground(new Color(64, 128, 128));
        btnBuscarTitulo.setBounds(341, 124, 83, 20);
        contentPane.add(btnBuscarTitulo);

        JLabel lblAutor = new JLabel("AUTOR");
        lblAutor.setBounds(10, 164, 75, 20);
        contentPane.add(lblAutor);

        txtAutor = new JTextField();
        txtAutor.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtAutor.setBounds(81, 164, 250, 20);
        contentPane.add(txtAutor);
        txtAutor.setColumns(10);
        txtAutor.setDocument(new Validador(100));

        JLabel lblAnoPublicacao = new JLabel("ANO PUBLICAÇÃO");
        lblAnoPublicacao.setBounds(10, 204, 120, 20);
        contentPane.add(lblAnoPublicacao);

        txtAnoPublicacao = new JTextField();
        txtAnoPublicacao.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtAnoPublicacao.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                String caracteres = "0123456789";
                if (!caracteres.contains(e.getKeyChar() + "")) {
                    e.consume();
                }
            }
        });
        
        txtAnoPublicacao.setBounds(130, 204, 100, 20);
        contentPane.add(txtAnoPublicacao);
        txtAnoPublicacao.setColumns(10);
        txtAnoPublicacao.setDocument(new Validador(4));

        JLabel lblQuantidadeEstoque = new JLabel("QUANTIDADE ESTOQUE");
        lblQuantidadeEstoque.setBounds(10, 244, 140, 20);
        contentPane.add(lblQuantidadeEstoque);

        txtQuantidadeEstoque = new JTextField();
        txtQuantidadeEstoque.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtQuantidadeEstoque.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                String caracteres = "0123456789";
                if (!caracteres.contains(e.getKeyChar() + "")) {
                    e.consume();
                }
            }
        });
        
        txtQuantidadeEstoque.setBounds(150, 244, 80, 20);
        contentPane.add(txtQuantidadeEstoque);
        txtQuantidadeEstoque.setColumns(10);
        txtQuantidadeEstoque.setDocument(new Validador(6));

        btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnAdicionar.setEnabled(false);
        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionar();
            }
        });
        
        btnAdicionar.setToolTipText("Adicionar livro");
        btnAdicionar.setBounds(10, 367, 92, 33);
        contentPane.add(btnAdicionar);

        btnEditar = new JButton("EDITAR");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editar();
            }
        });
        
        btnEditar.setToolTipText("Editar livro");
        btnEditar.setBounds(112, 367, 83, 33);
        contentPane.add(btnEditar);

        btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });
        
        btnExcluir.setToolTipText("Excluir livro");
        btnExcluir.setBounds(205, 367, 83, 33);
        contentPane.add(btnExcluir);

        JButton btnReset = new JButton("LIMPAR");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        
        btnReset.setToolTipText("Limpar campos");
        btnReset.setBounds(302, 367, 92, 33);
        contentPane.add(btnReset);

        JButton btnNovoLivro = new JButton("NOVO LIVRO");
        btnNovoLivro.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNovoLivro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                novoLivro();
            }
        });
        
        btnNovoLivro.setToolTipText("Iniciar cadastro de novo livro");
        btnNovoLivro.setBounds(10, 323, 146, 33);
        contentPane.add(btnNovoLivro);

        this.setLocationRelativeTo(null);
    }

    private void setarData() {
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
        lblData.setText(formatador.format(data));
    }

    private void novoLivro() {
        reset();
        txtIdLivro.setEditable(false);
        btnBuscar.setEnabled(false);
        btnAdicionar.setEnabled(true);
        txtTitulo.requestFocus();
    }

    private void buscarPorId() {
        if (txtIdLivro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite o ID do livro");
            txtIdLivro.requestFocus();
        } else {
            String readId = "select id_livro, titulo, autor, ano_publicacao, quantidade_estoque from livros where id_livro = ?";
            try {
                con = dao.conectar();
                pst = con.prepareStatement(readId);
                pst.setString(1, txtIdLivro.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    txtTitulo.setText(rs.getString("titulo"));
                    txtAutor.setText(rs.getString("autor"));
                    String ano = rs.getString("ano_publicacao");
                    txtAnoPublicacao.setText(ano != null ? ano : "");
                    txtQuantidadeEstoque.setText(rs.getString("quantidade_estoque"));

                    txtIdLivro.setEditable(false);
                    btnBuscar.setEnabled(false);
                    btnEditar.setEnabled(true);
                    btnExcluir.setEnabled(true);
                } else {
                    int confirma = JOptionPane.showConfirmDialog(null,
                            "ID não cadastrado.\nDeseja iniciar um novo cadastro?", "Aviso", JOptionPane.YES_NO_OPTION);
                    if (confirma == JOptionPane.YES_OPTION) {
                        novoLivro();
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

    private void buscarPorTitulo() {
        if (txtTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite o título do livro");
            txtTitulo.requestFocus();
        } else {
            String readTitulo = "select id_livro, titulo, autor, ano_publicacao, quantidade_estoque from livros where titulo like ?";
            try {
                con = dao.conectar();
                pst = con.prepareStatement(readTitulo,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                pst.setString(1, txtTitulo.getText() + "%");
                rs = pst.executeQuery();

                int rowCount = 0;
                String[] livros = new String[100];
                String primeiroId = "";
                String primeiroTitulo = "";
                String primeiroAutor = "";
                String primeiroAno = "";
                String primeiraQuantidade = "";

                while (rs.next() && rowCount < 100) {
                    String id = rs.getString("id_livro");
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");

                    livros[rowCount] = id + " - " + titulo + " (" + autor + ")";

                    if (rowCount == 0) {
                        primeiroId = id;
                        primeiroTitulo = titulo;
                        primeiroAutor = autor;
                        primeiroAno = rs.getString("ano_publicacao");
                        primeiraQuantidade = rs.getString("quantidade_estoque");
                    }

                    rowCount++;
                }

                if (rowCount > 0) {
                    if (rowCount == 1) {
                        txtIdLivro.setText(primeiroId);
                        txtTitulo.setText(primeiroTitulo);
                        txtAutor.setText(primeiroAutor);
                        txtAnoPublicacao.setText(primeiroAno != null ? primeiroAno : "");
                        txtQuantidadeEstoque.setText(primeiraQuantidade);

                        txtIdLivro.setEditable(false);
                        btnBuscar.setEnabled(false);
                        btnEditar.setEnabled(true);
                        btnExcluir.setEnabled(true);
                    } else {
                        String selecionado = (String) JOptionPane.showInputDialog(null,
                                "Foram encontrados " + rowCount + " livros. Selecione:", "Resultado da Busca",
                                JOptionPane.QUESTION_MESSAGE, null, livros, livros[0]);

                        if (selecionado != null) {
                            String id = selecionado.split(" - ")[0];
                            buscarLivroPorId(id);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Livro não encontrado");
                    int confirma = JOptionPane.showConfirmDialog(null,
                            "Deseja cadastrar um novo livro com este título?", "Aviso", JOptionPane.YES_NO_OPTION);
                    if (confirma == JOptionPane.YES_OPTION) {
                        novoLivro();
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

    private void buscarLivroPorId(String id) {
        String readId = "select id_livro, titulo, autor, ano_publicacao, quantidade_estoque from livros where id_livro = ?";
        try {
            con = dao.conectar();
            pst = con.prepareStatement(readId);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtIdLivro.setText(rs.getString("id_livro"));
                txtTitulo.setText(rs.getString("titulo"));
                txtAutor.setText(rs.getString("autor"));
                String ano = rs.getString("ano_publicacao");
                txtAnoPublicacao.setText(ano != null ? ano : "");
                txtQuantidadeEstoque.setText(rs.getString("quantidade_estoque"));

                txtIdLivro.setEditable(false);
                btnBuscar.setEnabled(false);
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Erro ao buscar livro: " + e.getMessage());
        }
    }

    private void adicionar() {
        if (txtTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha o título do livro");
            txtTitulo.requestFocus();
        } else if (txtQuantidadeEstoque.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha a quantidade em estoque");
            txtQuantidadeEstoque.requestFocus();
        } else {
            String insert = "insert into livros(titulo, autor, ano_publicacao, quantidade_estoque) values(?, ?, ?, ?)";
            try {
                con = dao.conectar();
                pst = con.prepareStatement(insert);
                pst.setString(1, txtTitulo.getText());
                pst.setString(2, txtAutor.getText().isEmpty() ? null : txtAutor.getText());
                
                if (txtAnoPublicacao.getText().isEmpty()) {
                    pst.setNull(3, java.sql.Types.INTEGER);
                } else {
                    pst.setInt(3, Integer.parseInt(txtAnoPublicacao.getText()));
                      }
                pst.setInt(4, Integer.parseInt(txtQuantidadeEstoque.getText()));

                int confirma = pst.executeUpdate();

                if (confirma == 1) {
                    String buscarId = "select id_livro from livros where titulo = ? order by id_livro desc limit 1";
                    PreparedStatement pstBusca = con.prepareStatement(buscarId);
                    pstBusca.setString(1, txtTitulo.getText());
                    ResultSet rsBusca = pstBusca.executeQuery();

                    if (rsBusca.next()) {
                        String idGerado = rsBusca.getString("id_livro");
                        txtIdLivro.setText(idGerado);

                        JOptionPane.showMessageDialog(null,
                                "Livro cadastrado com sucesso!\n" +
                                        "ID gerado: " + idGerado);

                        txtIdLivro.setEditable(false);
                        btnBuscar.setEnabled(false);
                        btnAdicionar.setEnabled(false);
                        btnEditar.setEnabled(true);
                        btnExcluir.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
                        reset();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Erro! Livro não cadastrado.");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar livro: " + e.getMessage());
            }
        }
    }

    private void editar() {
        if (txtTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha o título do livro");
            txtTitulo.requestFocus();
        } else if (txtQuantidadeEstoque.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha a quantidade em estoque");
            txtQuantidadeEstoque.requestFocus();
        } else {
            String update = "update livros set titulo=?, autor=?, ano_publicacao=?, quantidade_estoque=? where id_livro=?";
            try {
                con = dao.conectar();
                pst = con.prepareStatement(update);
                pst.setString(1, txtTitulo.getText());
                pst.setString(2, txtAutor.getText().isEmpty() ? null : txtAutor.getText());
                
                if (txtAnoPublicacao.getText().isEmpty()) {
                    pst.setNull(3, java.sql.Types.INTEGER);
                } else {
                    pst.setInt(3, Integer.parseInt(txtAnoPublicacao.getText()));
                }
                
                pst.setInt(4, Integer.parseInt(txtQuantidadeEstoque.getText()));
                pst.setString(5, txtIdLivro.getText());

                int confirma = pst.executeUpdate();
                if (confirma == 1) {
                    JOptionPane.showMessageDialog(null, "Dados do livro alterados.");
                    reset();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro! Dados do livro não alterados.");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Erro ao editar livro: " + e.getMessage());
            }
        }
    }

    private void excluir() {
        int confirmaExcluir = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste livro?", "Atenção!",
                JOptionPane.YES_NO_OPTION);
        if (confirmaExcluir == JOptionPane.YES_OPTION) {
            String delete = "delete from livros where id_livro=?";
            try {
                con = dao.conectar();
                pst = con.prepareStatement(delete);
                pst.setString(1, txtIdLivro.getText());
                int confirma = pst.executeUpdate();
                if (confirma == 1) {
                    reset();
                    JOptionPane.showMessageDialog(null, "Livro excluído");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Erro ao excluir livro: " + e.getMessage());
            }
        }
    }

    private void reset() {
        txtIdLivro.setText(null);
        txtTitulo.setText(null);
        txtAutor.setText(null);
        txtAnoPublicacao.setText(null);
        txtQuantidadeEstoque.setText(null);
        txtIdLivro.requestFocus();
        txtIdLivro.setEditable(true);
        btnBuscar.setEnabled(true);
        btnAdicionar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }
}