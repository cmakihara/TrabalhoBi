package br.univel;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class  BancoSql  {

	public Connection conexao() {
		Connection con = null;
		String url = "jdbc:postgresql://localhost:5432/postgres";
	    String user = "postgres";
	    String pass = "sa";
	    
	    try {
	        con = DriverManager.getConnection(url, user, pass);
	    } catch (SQLException ex) {
	     Logger.getLogger(BancoSql.class.getName()).log(Level.SEVERE, null, ex); 		
	}
		return con;
	}
	
	public String inserir(Object obj, List<?> list) {
		Connection con = conexao();		
		sqlInserir(con, obj, list);
		return null;
	}
	
	public String buscarTodos(Object t) {
		Connection con = conexao();		
		sqlBusca(con, t);
		return null;
	}
	public List<Object> tabela(String sql, Object o) {
		Connection con = conexao();
		List<Object> lista = new ArrayList<Object>();
		PreparedStatement ps;
		Class<?> clazz = o.getClass();
		Field[] f = clazz.getDeclaredFields();
		int i = 0;
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				i = 1;
				while(i != f.length+1){
					lista.add(rs.getObject(i));
					i++;
				}
			}
		} catch (SQLException e) {
			
		}
		return lista;
	}

	
	String sqlInserir(Connection con, Object obj, List lista) {

		Class<? extends Object> cl = obj.getClass();

		StringBuilder sb = new StringBuilder();
		Carro c =new Carro();
		Class<?> clazz = c.getClass();
		
		String nome = clazz.getSimpleName();
		
		sb.append("INSERT INTO "+ nome +"(");
		int x = 0;
		for(Field f : cl.getDeclaredFields()){
			Coluna coluna = f.getAnnotation(Coluna.class);
			if(x > 0){
				sb.append(", ");
			}
			f.setAccessible(true);
			
			if(coluna.pk() == false){
				sb.append(nomeCol(f, coluna));
			}	
			if(coluna.pk() == true){
				sb.append(nomeCol(f, coluna));
			}
			x++;
		}
		sb.append(")\nVALUES (");		
		
		int i = 0, count = 0;
		for (Field f : cl.getDeclaredFields()) {
			count++;
			if (count == cl.getDeclaredFields().length){
				if(f.getType().getSimpleName().equals("String")){
					sb.append("'");
					sb.append(lista.get(i));
					sb.append("'");
					i++;
				}else{
					sb.append(lista.get(i));
					i++;
				}
			}else{
				if(f.getType().getSimpleName().equals("String")){
					sb.append("'");
					sb.append(lista.get(i));
					sb.append("'");
					i++;
				}else{
					sb.append(lista.get(i));
					i++;
				}
				sb.append(", ");
			}
		}
		
		sb.append(");");
		
		try {
			con.prepareStatement(sb.toString()).execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	String sqlBusca(Connection con, Object obj) {
		
		String nome = obj.getClass().getSimpleName();
		String sql = "SELECT * FROM "+nome;
		System.out.println(sql);
		try {
			con.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sql;
	}
		
	private String nomeCol(Field field, Coluna coluna) {
		String nomeCol;
		if(field.isAnnotationPresent(Coluna.class)){
			if(coluna.nome().isEmpty()){
				nomeCol = field.getName();
			}else{
				nomeCol = coluna.nome();
			}
		}else{
			nomeCol = field.getName();
		}		
		return nomeCol;
		
	}
	

}