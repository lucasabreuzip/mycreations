/**
 * ---------------------------------------------------------------------------
 * Sistema de Gerenciamento Bibliotecário
 * ---------------------------------------------------------------------------
 * @author  // LUCAS ROBERTO DE ABREU ROGENSKI - 2424096
 * @author  // DARLEISON DE MOURA RODRIGUES - 2424097
 * @author  // EDILSON ANTONIO BESSA BOTTO - 2424111
 * @author  // GLÓRIA MARIA DE SOUZA EVANGELISTA - 2424102
 * @author  // LUIS ARTHUR DA COSTA SILVA - 2424100
 * @author  // VIVIAN RÉGIA CUNHA DA SILVA - 2425202
 * @version 1.0
 * @since   2024-05-27
 *
 * © 2024 Alunos da turma de IA na UNIFOR. Todos os direitos reservados.
 *
 */

package model;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.conexaoDAO;

import javax.swing.ImageIcon;

public class Main extends JFrame {

	 conexaoDAO dao = new conexaoDAO();
	    private static final long serialVersionUID = 1L;
	    private Connection con;
	    private JPanel contentPane;
	    private JLabel lblStatus;
	    private JLabel lblData;
    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    private void status() {
        try {
            con = dao.conectar();
            if (con == null) {
                lblStatus.setIcon(new ImageIcon(CadastroEmprestimos.class.getResource("/img/offline.png")));
            } else {
                lblStatus.setIcon(new ImageIcon(CadastroEmprestimos.class.getResource("/img/online.png")));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Main() {
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                status();
                setarData();
            }
        });
        
        setTitle("SISTEMA DE GERENCIAMENTO BIBLIOTECÁRIO - MENU PRINCIPAL");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/img/icone.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(153, 153, 153));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(64, 128, 128));
        panel.setBounds(0, 311, 584, 50);
        contentPane.add(panel);
        panel.setLayout(null);
        
        lblStatus = new JLabel("");
        lblStatus.setIcon(new ImageIcon(Main.class.getResource("/img/offline.png")));
        lblStatus.setBounds(217, 9, 32, 32);
        panel.add(lblStatus);

        lblData = new JLabel("");
        lblData.setBounds(36, 9, 157, 14);
        panel.add(lblData);
        lblData.setForeground(SystemColor.text);
        lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
       
        
        JLabel lblNewLabel_1 = new JLabel("Status do Banco de dados:");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(10, 27, 201, 14);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel = new JLabel("MENU DE GERENCIAMENTO");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(112, 45, 376, 30);
        contentPane.add(lblNewLabel);

        JButton btnAlunos = new JButton("ALUNOS");
        btnAlunos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	CadatroAluno alunosFrame = new CadatroAluno();
                alunosFrame.setVisible(true);
            }
        });
        
        btnAlunos.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnAlunos.setBounds(112, 113, 165, 50);
        contentPane.add(btnAlunos);

        JButton btnLivros = new JButton("LIVROS");
        btnLivros.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroLivros livrosFrame = new CadastroLivros();
                livrosFrame.setVisible(true);
            }
        });
        
        btnLivros.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnLivros.setBounds(323, 113, 165, 50);
        contentPane.add(btnLivros);

        JButton btnEmprestimos = new JButton("EMPRÉSTIMOS");
        btnEmprestimos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroEmprestimos emprestimosFrame = new CadastroEmprestimos();
                emprestimosFrame.setVisible(true);
            }
        });
        
        btnEmprestimos.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnEmprestimos.setBounds(112, 194, 165, 50);
        contentPane.add(btnEmprestimos);

        JButton btnRelatorios = new JButton("RELATÓRIOS");
        btnRelatorios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RelatorioMovimentacao relatorioFrame = new RelatorioMovimentacao();
                relatorioFrame.setVisible(true);
            }
        });
        
        btnRelatorios.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnRelatorios.setBounds(323, 194, 165, 50);
        contentPane.add(btnRelatorios);
    }



    private void setarData() {
        Date data = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lblData.setText(dateFormat.format(data));
    }
}