package br.univel;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class CriaTabela{	
	
	
	
private Connection con;
	
	public CriaTabela(){
	String strCreateTable = getCreateTable(Carro.class); 
	System.out.println(strCreateTable);
	 conectar();     
 
}
public void conectar() {
 	String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "sa";
    
    try {
        con = DriverManager.getConnection(url, user, pass);
    } catch (SQLException ex) {
        Logger.getLogger(CriaTabela.class.getName()).log(Level.SEVERE, null, ex);
    }
    criarTabela();
}

public void criarTabela() {
	List<Carro> lista = new ArrayList<>();
    
    PreparedStatement ps;
    try {
        
        ps = con.prepareStatement(getCreateTable(Carro.class));
        ResultSet rs = ps.executeQuery();
       
        
    } catch (SQLException ex) {
        Logger.getLogger(CriaTabela.class.getName()).log(Level.SEVERE, null, ex);
    }
    	
}
private String getCreateTable(Class<Carro> cl) {

	try {

		StringBuilder sb = new StringBuilder();

		
		{
			String nomeTabela;
			if (cl.isAnnotationPresent(Tabela.class)) {

				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();

			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();

			}
			sb.append("CREATE TABLE ").append(nomeTabela).append(" (");
		}

		Field[] atributos = cl.getDeclaredFields();

		
		{
			for (int i = 0; i < atributos.length; i++) {

				Field field = atributos[i];

				String nomeColuna;
				String tipoColuna;

				if (field.isAnnotationPresent(Coluna.class)) {
					Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

					if (anotacaoColuna.nome().isEmpty()) {
						nomeColuna = field.getName().toUpperCase();
					} else {
						nomeColuna = anotacaoColuna.nome();
					}

				} else {
					nomeColuna = field.getName().toUpperCase();
				}

				Class<?> tipoParametro = field.getType();

				if (tipoParametro.equals(String.class)) {
					tipoColuna = "VARCHAR(100)";

				} else if (tipoParametro.equals(int.class)) {
					tipoColuna = "INT";
					
				} else if (tipoParametro.equals(BigDecimal.class)){				
					tipoColuna = "VARCHAR(1000000)";
					
				} else {
					tipoColuna = "DESCONHECIDO";
				}

				if (i > 0) {
					sb.append(",");
				}

				sb.append("\n\t").append(nomeColuna).append(' ').append(tipoColuna);
			}
		}

		
		{

			sb.append(",\n\tPRIMARY KEY( ");

			for (int i = 0, achou = 0; i < atributos.length; i++) {

				Field field = atributos[i];

				if (field.isAnnotationPresent(Coluna.class)) {

					Coluna coluna = field.getAnnotation(Coluna.class);

					if (coluna.pk()) {

						if (achou > 0) {
							sb.append(", ");
						}

						if (coluna.nome().isEmpty()) {
							sb.append(field.getName().toUpperCase());
						} else {
							sb.append(coluna.nome());
						}

						achou++;
					}

				}
			}

			sb.append(" )");
		}

		sb.append("\n);");

		return sb.toString();
	} catch (SecurityException e) {
		throw new RuntimeException(e);
	}
}

}

