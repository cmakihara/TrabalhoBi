package br.univel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TelaGen {
	
	private JTable table;
	private Class<?> clazz;
	
	
	public JPanel tela(Object o){
		JPanel contentPane = new JPanel();
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);		
		
		int i = 1;
		BancoSql banco = new BancoSql();		
		clazz = o.getClass();		
		Carro car = new Carro();		
				
		List<JTextField> textFields = new ArrayList<JTextField>();
		for(Field f : clazz.getDeclaredFields()){
			JLabel lblNewLabel = new JLabel(f.getAnnotation(Coluna.class).nome().toUpperCase());
			contentPane.add(lblNewLabel, createConstraints(0, i));
			
			JTextField textField = new JTextField();
			textFields.add(textField);
			textField.setName(f.getName());
			contentPane.add(textField, createConstraints(3, i));
			textField.setColumns(50);
			i++;
		}
		
		JButton btnNewButton = new JButton("Criar Tabela");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriaTabela cria = new CriaTabela();
				
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Adcionar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> lista = new ArrayList<String>();
				for(int i = 0; i < textFields.size(); i++){
					lista.add(textFields.get(i).getText());
				}
				if(!lista.get(1).equals("")){
					banco.inserir(o, lista);
					
				}				
				CarroTableModel tb = new CarroTableModel(lista, clazz);
				table.setModel(tb);				
			}
		});	
		
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 0;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("Exclui Tabela");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExcluiTabela banco = new ExcluiTabela();
			}
		});
		
		JButton btnNewButton_2 = new JButton("Apagar campos");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JTextField jTextField : textFields) {
					jTextField.setText("");
				}				
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 2;
		gbc_btnNewButton_2.gridy = 1;
		contentPane.add(btnNewButton_2, gbc_btnNewButton_2);
		
		
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_3.gridx = 3;
		gbc_btnNewButton_3.gridy = 0;
		contentPane.add(btnNewButton_3, gbc_btnNewButton_3);		
	
		JButton btnNewButton_4 = new JButton("Busca");
		btnNewButton_4.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				if(textFields.get(0).getText().equals("")){
					banco.buscarTodos(car);
					String nome = o.getClass().getSimpleName();
					String sql = "SELECT * FROM "+nome;
					List lista = banco.tabela(sql, o);
					
					CarroTableModel tb = new CarroTableModel(lista, clazz);
					table.setModel(tb);
				} 						
			}			
		});
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_4.gridx = 2;
		gbc_btnNewButton_4.gridy = 0;
		contentPane.add(btnNewButton_4, gbc_btnNewButton_4);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 600;
		gbc_scrollPane.gridwidth = 600;
		gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 10;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable(){
		
			
		};
		scrollPane.setViewportView(table);
		
		return contentPane;		
	}

	private GridBagConstraints createConstraints(int x, int y){
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = x;
		gbc_textField.gridy = y;
		gbc_textField.insets = new Insets(3, 3, 3, 3);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		return gbc_textField;
	}	
}