package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null;

	// método para conectar com o banco de dados
	public static Connection getConnection() {
		// se estiver nulo conecto com o banco
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");

				// passo a url do banco e as propriedades de conexão
				/*
				 * Conectar com o banco é instanciar um objeto connection, é o que fizemos com o
				 * DriverManager
				 */
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}

	// método para fechar a conexão
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	// método auxiliar para carregar as propriedades do db.properties
	private static Properties loadProperties() {

		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			// load = faz a leitura do arquivo apontado no stream e guarda no props
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	// método auxiliar para fechar os objeto statement
	// e evitar ficar enchendo o programa de try catch
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
