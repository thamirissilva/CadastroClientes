package br.com.senac.cadastro;

/*


//-------  @author Thamiris da Silva Souza -------


----------------- PROJETO -----------------

Atividade individual de avaliação:
"Cadastro de clientes"

Nome do banco de dados:
seunomedb (thamirisdb)

Nome da tabela:
tb_seunome (tb_thamiris)
Chave primária sem numeração automática

- Formulário:
Livre design e escolha dos campos
Todos os botões com ícones e acessibilidade

*** campos obrigatórios:
cep
tipo de logradouro (rua, avenida, estrada, etc)
logradouro (nome da rua, avenida, estrada, etc)
Bairro
Cidade
UF

 */

//---------------------------------------------------------------------------//

//Importando bibliotecas e recursos para trabalhar com o sql.
import java.sql.*;
 
public class CadastroCliente {
    
    //Método de retorno
    public static Connection conector(){
        // java.sql.Connection -> classe usada para conexão com o banco.
        // conexão -> nome da variável.
        // atribuimos o valor nulo por questões de segurança.
        java.sql.Connection conexao = null; 
        
        // String tipo de variável.
        // driver -> nome de variável que recebe o driver.
        String driver = "com.mysql.jdbc.Driver";
        
        // caminho do banco de dados.
        // String tipo de variável.
        // url -> nome da variável que recebe a informação do caminho.
        String url = "jdbc:mysql://localhost:3306/thamirisdb";
        // Autenticação do banco de dados (usuário e senha com permissões de acesso).
        // Alterar o usuário e a senha de acordo com o servidor / banco.
        String user = "root";
        String password = "senac@tat";
        
        // Tratamento de exceções.
        try {
            Class.forName(driver);
            // atribuindo á variável conexão às informações necessárias para estabelecer a conexão com o banco.
            // DriverManager -> Gerenciamento do driver (MYSQL).
            // getConnection -> Obter a conexão e fazer a passagem dos parâmetros url, user, password.
            conexao = DriverManager.getConnection(url,user,password);
            return conexao;
        } 
        
        catch (Exception e) {
        // Exceção, para caso se o banco de dados estiver indisponível. 
        return null;
        
        }
    
    }
    
}
