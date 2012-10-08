package terminal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaSQLiteJDBC {

	public static void main(String[] args) {

		try {
			// Carga del driver de SQLite
			Class.forName("org.sqlite.JDBC");
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			// Creo una conexion hacia la base de datos
			Connection conexion = DriverManager
					.getConnection("jdbc:sqlite:db/alumno.db");
			Statement consulta = conexion.createStatement();

			// Se ejecuta una consulta SQL, y me devuelve un conjunto de resultados
			//En este caso, me devuelve todas las tuplas de la tabla alumno
			ResultSet resultado = consulta.executeQuery("select * from alumno;");

			System.out.println("DNI\t\tAPELLIDO\tNOMBRE");

			// Recorro el conjunto de resultados y muestro tupla por tupla
			while (resultado.next()) {
				System.out.println(resultado.getInt(1) + "\t"
						+ resultado.getString("apellido") + "\t"
						+ resultado.getString("nombre") + "\t\t");
			}

			// Cerramos las conexiones
			consulta.close();
			resultado.close();
			conexion.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Estado:" + e.getSQLState());
		}
	}
}