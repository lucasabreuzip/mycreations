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
import javax.swing.DefaultListModel;
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



public class CadatroAluno extends JFrame {

	conexaoDAO dao = new conexaoDAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblData;
	private JTextField txtRA;
	private JTextField txtNome;
	private JTextField txtDataNascimento;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JButton btnBuscar;
	private JButton btnBuscarNome;
	private JButton btnEditar;
	private JLabel lblNewLabel_1_2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadatroAluno frame = new CadatroAluno();
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

	public CadatroAluno() {
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				setarData();
			}
		});
		setTitle("CADASTRO ALUNO - CONSULTA");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(CadatroAluno.class.getResource("/img/icone.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 419, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 153, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(64, 128, 128));
		panel.setBounds(0, 0, 684, 41);
		contentPane.add(panel);
		panel.setLayout(null);
		
				lblData = new JLabel("");
				lblData.setBounds(10, 11, 234, 19);
				panel.add(lblData);
				lblData.setForeground(SystemColor.text);
				lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblNewLabel = new JLabel("MATRÍCULA");
		lblNewLabel.setBounds(10, 86, 76, 14);
		contentPane.add(lblNewLabel);

		txtRA = new JTextField();
		txtRA.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtRA.setBounds(81, 83, 92, 20);
		contentPane.add(txtRA);
		txtRA.setColumns(10);
		txtRA.setDocument(new Validador(6));
		

		JLabel lblNewLabel_1 = new JLabel("DADOS DO ALUNO/CADASTRO");
		lblNewLabel_1.setBounds(109, 52, 250, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblDataNascimento = new JLabel("DATA NASCIMENTO");
		lblDataNascimento.setBounds(10, 158, 120, 14);
		contentPane.add(lblDataNascimento);

		txtDataNascimento = new JTextField();
		txtDataNascimento.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtDataNascimento.setBounds(130, 155, 136, 20);
		contentPane.add(txtDataNascimento);
		txtDataNascimento.setColumns(10);

		txtNome = new JTextField();
		txtNome.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				listarNomes();
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					int confirma = JOptionPane.showConfirmDialog(null,
							"Aluno não cadastrado.\nDeseja cadastrar este aluno?", "Aviso", JOptionPane.YES_OPTION);
					if (confirma == JOptionPane.YES_OPTION) {
						txtRA.setEditable(false);
						btnBuscar.setEnabled(false);
						btnAdicionar.setEnabled(true);
					} else {
						reset();
					}
				}
			}
		});
		txtNome.setBounds(55, 124, 211, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(30));

		btnAdicionar = new JButton("ADICIONAR");
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(null);
		btnAdicionar.setBounds(10, 317, 92, 33);
		contentPane.add(btnAdicionar);

		JButton btnReset = new JButton("LIMPAR");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnReset.setIcon(null);
		btnReset.setToolTipText("Limpar campos");
		btnReset.setBounds(298, 317, 92, 33);
		contentPane.add(btnReset);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarRA();
			}
		});
		btnBuscar.setForeground(new Color(64, 128, 128));
		btnBuscar.setBounds(183, 81, 83, 20);
		contentPane.add(btnBuscar);
		
		btnBuscarNome = new JButton("Buscar");
		btnBuscarNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBuscarNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPorNome();
			}
		});
		btnBuscarNome.setForeground(new Color(64, 128, 128));
		btnBuscarNome.setBounds(276, 124, 83, 20);
		contentPane.add(btnBuscarNome);

		btnEditar = new JButton("EDITAR");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setIcon(null);
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(112, 317, 83, 33);
		contentPane.add(btnEditar);

		btnExcluir = new JButton("EXCLUIR");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setIcon(null);
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(205, 317, 83, 33);
		contentPane.add(btnExcluir);
		
		lblNewLabel_1_2 = new JLabel("NOME");
		lblNewLabel_1_2.setBounds(10, 124, 75, 20);
		contentPane.add(lblNewLabel_1_2);
		
		this.setLocationRelativeTo(null);
	}

	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
	}

	
	
	
	private void buscarPorNome() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do aluno");
			txtNome.requestFocus();
		} else {
			String readNome = "select matricula, nome_aluno, data_nascimento from alunos where nome_aluno like ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readNome, 
						ResultSet.TYPE_SCROLL_INSENSITIVE, 
						ResultSet.CONCUR_READ_ONLY);
				pst.setString(1, txtNome.getText() + "%");
				rs = pst.executeQuery();
				
				int rowCount = 0;
				String[] alunos = new String[100];
				String primeiroRA = "";
				String primeiroNome = "";
				String primeiraDataNasc = "";
				
				while (rs.next() && rowCount < 100) {
					String matricula = rs.getString("matricula");
					String nome = rs.getString("nome_aluno");
					String dataNasc = rs.getString("data_nascimento");
					alunos[rowCount] = matricula + " - " + nome;
					
					if (rowCount == 0) {
						primeiroRA = matricula;
						primeiroNome = nome;
						primeiraDataNasc = dataNasc;
					}
					
					rowCount++;
				}
				
				if (rowCount > 0) {
					if (rowCount == 1) {
						txtRA.setText(primeiroRA);
						txtNome.setText(primeiroNome);
						txtDataNascimento.setText(formatarDataParaTela(primeiraDataNasc));
						txtRA.setEditable(false);
						btnBuscar.setEnabled(false);
						btnEditar.setEnabled(true);
						btnExcluir.setEnabled(true);
					} else {
						
						String selecionado = (String) JOptionPane.showInputDialog(null, 
							"Foram encontrados " + rowCount + " alunos. Selecione:", "Resultado da Busca",
							JOptionPane.QUESTION_MESSAGE, null, alunos, alunos[0]);
						
						if (selecionado != null) {
							String matricula = selecionado.split(" - ")[0];
							buscarAlunoMatricula(matricula);
						}
					}
				} else {
					
					JOptionPane.showMessageDialog(null, "Aluno não encontrado");
					int confirma = JOptionPane.showConfirmDialog(null,
							"Deseja cadastrar um novo aluno com este nome?", "Aviso", JOptionPane.YES_NO_OPTION);
					if (confirma == JOptionPane.YES_OPTION) {
						txtRA.setEditable(false);
						btnBuscar.setEnabled(false);
						btnAdicionar.setEnabled(true);
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
	
	private void buscarAlunoMatricula(String matricula) {
		String readRA = "select matricula, nome_aluno, data_nascimento from alunos where matricula = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readRA);
			pst.setString(1, matricula);
			rs = pst.executeQuery();
			if (rs.next()) {
				txtRA.setText(rs.getString("matricula"));
				txtNome.setText(rs.getString("nome_aluno"));
				String dataBanco = rs.getString("data_nascimento");
				txtDataNascimento.setText(formatarDataParaTela(dataBanco));
				
				txtRA.setEditable(false);
				btnBuscar.setEnabled(false);
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Erro ao buscar aluno: " + e.getMessage());
		}
	}

	private void adicionar() {
	    if (txtNome.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Preencha o nome");
	        txtNome.requestFocus();
	    } else if (txtDataNascimento.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Preencha a data de nascimento");
	        txtDataNascimento.requestFocus();
	    } else {
	        String dataFormatada = formatarDataParaBanco(txtDataNascimento.getText());
	        if (dataFormatada == null) {
	            JOptionPane.showMessageDialog(null, "Data inválida! Use o formato dd/mm/aaaa ou ddmmaaaa");
	            txtDataNascimento.requestFocus();
	            return;
	        }
	        
	        String insert = "insert into alunos(nome_aluno, data_nascimento) values(?, ?)";
	        try {
	            con = dao.conectar();
	            pst = con.prepareStatement(insert);
	            pst.setString(1, txtNome.getText());
	            pst.setString(2, dataFormatada);
	            
	            int confirma = pst.executeUpdate();
	            
	            if (confirma == 1) {
	                String buscarMatricula = "select matricula from alunos where nome_aluno = ? and data_nascimento = ? order by matricula desc limit 1";
	                PreparedStatement pstBusca = con.prepareStatement(buscarMatricula);
	                pstBusca.setString(1, txtNome.getText());
	                pstBusca.setString(2, dataFormatada);
	                ResultSet rsBusca = pstBusca.executeQuery();
	                
	                if (rsBusca.next()) {
	                    String matriculaGerada = rsBusca.getString("matricula");
	                    txtRA.setText(matriculaGerada);
	                    
	                    JOptionPane.showMessageDialog(null,"Aluno cadastrado com sucesso!\n" + "Matrícula gerada: " + matriculaGerada);
	                    
	                    txtRA.setEditable(false);
	                    btnBuscar.setEnabled(false);
	                    btnAdicionar.setEnabled(false);
	                    btnEditar.setEnabled(true);
	                    btnExcluir.setEnabled(true);
	                } else {
	                    JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!");
	                    reset();
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "Erro! Aluno não cadastrado.");
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	            JOptionPane.showMessageDialog(null, "Erro ao cadastrar aluno: " + e.getMessage());
	        }
	    }
	}

	private void buscarRA() {
	    if (txtRA.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Digite a Matrícula");
	        txtRA.requestFocus();
	    } else {
	        String readRA = "select matricula, nome_aluno, data_nascimento from alunos where matricula = ?";
	        try {
	            con = dao.conectar();
	            pst = con.prepareStatement(readRA);
	            pst.setString(1, txtRA.getText());
	            rs = pst.executeQuery();
	            if (rs.next()) {
	                txtNome.setText(rs.getString("nome_aluno"));
	                String dataBanco = rs.getString("data_nascimento");
	                txtDataNascimento.setText(formatarDataParaTela(dataBanco));
	                
	                txtRA.setEditable(false);
	                btnBuscar.setEnabled(false);
	                btnEditar.setEnabled(true);
	                btnExcluir.setEnabled(true);
	            } else {
	                int confirma = JOptionPane.showConfirmDialog(null,
	                        "Matricula não cadastrada.\nDeseja iniciar um novo cadastro?", "Aviso", JOptionPane.YES_OPTION);
	                if (confirma == JOptionPane.YES_OPTION) {
	                    txtRA.setEditable(false);
	                    txtRA.setText(null);
	                    btnBuscar.setEnabled(false);
	                    txtNome.setText(null);
	                    txtDataNascimento.setText(null);
	                    txtNome.requestFocus();
	                    btnAdicionar.setEnabled(true);
	                } else {
	                    reset();
	                }
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	}
	

	private void listarNomes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		String readLista = "select * from alunos where nome_aluno like '" + txtNome.getText() + "%'" + "order by nome_aluno";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void editar() {
	    if (txtNome.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Preencha o nome");
	        txtNome.requestFocus();
	    } else if (txtDataNascimento.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Preencha a data de nascimento");
	        txtDataNascimento.requestFocus();
	    } else {
	    	
	        String dataFormatada = formatarDataParaBanco(txtDataNascimento.getText());
	        if (dataFormatada == null) {
	            JOptionPane.showMessageDialog(null, "Data inválida! Use o formato dd/mm/aaaa ou ddmmaaaa");
	            txtDataNascimento.requestFocus();
	            return;
	        }
	        
	        String update = "update alunos set nome_aluno=?, data_nascimento=? where matricula=?";
	        try {
	            con = dao.conectar();
	            pst = con.prepareStatement(update);
	            pst.setString(1, txtNome.getText());
	            pst.setString(2, dataFormatada);
	            pst.setString(3, txtRA.getText());
	            
	            int confirma = pst.executeUpdate();
	            if (confirma == 1) {
	                JOptionPane.showMessageDialog(null, "Dados do aluno alterados.");
	                reset();
	                
	            } else {
	                JOptionPane.showMessageDialog(null, "Erro! Dados do aluno não alterados.");
	            }
	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	}

	private void excluir() {
		int confirmaExcluir = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste aluno ?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirmaExcluir == JOptionPane.YES_OPTION) {
			String delete = "delete from alunos where matricula=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtRA.getText());
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					reset();
					JOptionPane.showMessageDialog(null, "Aluno excluído");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}


	private void reset() {
	    txtRA.setText(null);
	    txtNome.setText(null);
	    txtDataNascimento.setText(null);
	    txtNome.requestFocus();
	    txtRA.setEditable(true);
	    btnBuscar.setEnabled(true);
	    btnAdicionar.setEnabled(false);
	    btnEditar.setEnabled(false);
	    btnExcluir.setEnabled(false);
	}
}