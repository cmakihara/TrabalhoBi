
package br.univel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CarroTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Carro> itens = new ArrayList<>();
	private List<String> lista;
	int col = 0;
	Carro c =new Carro();
	
	public CarroTableModel(List<String> lista, Class<?> classe) {
		this.lista = lista;
		for(Field f : classe.getDeclaredFields()){
			if(f.isAnnotationPresent(Coluna.class)){
				col++;
			}
		}
	}
	public String getColumnName(int column){
		
		switch(column){// falta dinamico
		
		case 0 : return "ID";
		case 1 : return "Nome do carro";
		case 2 : return "Ano";
		case 3 : return "Valor";
		
		
		}
		return null;	
}
	
	@Override
	public int getColumnCount() {
		Class<?> clazz = c.getClass();		
		return clazz.getDeclaredFields().length ;
	}

	public int getRowCount() {
		return lista.size()/col;	      
	}
	int i = 0;
	public Object getValueAt(int rowIndex, int column) {
		//System.out.println(rowIndex+" "+ i);
		
		return lista.get(i++);
	}
	
}