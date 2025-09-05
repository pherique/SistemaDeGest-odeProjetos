import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/sistemagestao";
        String usuario = "root";
        String senha = "Carpedcc123";

        try {
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão realizada com sucesso!");
            conexao.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }
}