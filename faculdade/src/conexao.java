public class conexao {
    public static void main (String[] args) {
        try{
            Class.forName("com.mysql.jdc.Driver") ;
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver do banco de dados não localizado");
        }
    }
}
