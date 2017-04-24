package br.univel;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcluiTabela{

	private Connection con;

	public ExcluiTabela(){
	
	 conectar(); 
		
	}
	public void conectar() {
	 	String url = "jdbc:postgresql://localhost:5432/postgres";
	    String user = "postgres";
	    String pass = "sa";
	    
	    try {
	        con = DriverManager.getConnection(url, user, pass);
	    } catch (SQLException ex) {
	        Logger.getLogger(ExcluiTabela.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    excluiTabela();
	}
	
	private void excluiTabela() {
		List<Carro> lista = new ArrayList<>();
		Carro c =new Carro();
		Class<?> clazz = c.getClass();
		//System.out.println(clazz.getSimpleName());
		String nome = clazz.getSimpleName();
		 String sql = "DROP TABLE "+nome;          //// fazer drop
		 System.out.println(sql);
	    PreparedStatement ps;
	    try {
	        
	        ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	       
	        
	    } catch (SQLException ex) {
	        Logger.getLogger(ExcluiTabela.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
}
