
 //-------------- @autor: Thamiris da Silva Souza --------------
 
//---------------------------- PROJETO -------------------------------

package br.com.senac.cadastro.GUI;

// Importação de pacotes e recursos
import br.com.senac.cadastro.CadastroCliente;
import java.sql.*;
import javax.swing.JOptionPane;

// Herdando as caracteristicas do JFrame
public class TelaCadastro extends javax.swing.JFrame {

    // Iniciando variáveis e objetos para trabalhar com o banco de dados
    Connection conexao = null;          //conexão com o banco
    PreparedStatement pst = null;       //executar comandos sql
    ResultSet rs = null;                //retorno (banco de dados)
    
    
    public TelaCadastro() {
        initComponents();
        // Iniciar a conexão (Módulo de Conexão)
        conexao = CadastroCliente.conector();
       
        // A estrutura abaixo troca a imagem de conexão com banco de dados.
        // (String -> conectado | Null -> não conectado).
        // Se a conexão(ok) for diferente de Null(error) colocar a imagem dbok.png.
        if (conexao != null) {
            lbldb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/senac/cadastro/icones/dbok.png")));
        } else {
            lbldb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/senac/cadastro/icones/dberror.png")));
      }
      
    }
    
    //****************************** MÉTODOS ************************************//
    
    
    //-------------------- Método CONSULTAR -----------------
        private void pesquisar() {
        String consulta = "select * from tb_thamiris where id=?";

        //Usando uma estrutura do tipo 'try catch' para tratamento de exceções
        try {
            // Lógica principal
            pst = conexao.prepareStatement(consulta);

            // A linha abaixo captura o conteúdo da caixa de texto txtId e ...
            //substitui pelo parâmetro '?'
            pst.setString(1, txtid.getText());

            // A linha abaixo executa a query(que é o comando do banco de dados) 
            //e recupera os dados do banco.
            rs = pst.executeQuery();

            // Se existir um registro(contato) correspondente ao ID, preencher as...
            //caixas de texto.
            // Usando as variáveis 'conexão','pst','rs' para pesquisar e...
            // recuperar o conteúdo do banco de dados.
            if (rs.next()) {
                txtnome.setText(rs.getString(2));  // Preencher o campo txtNome do...
                //fomulário com o conteúdo do campo '2' da tabela tb_contatos do banco.
                txttelefone.setText(rs.getString(3));
                txtemail.setText(rs.getString(4));
                txtcep.setText(rs.getString(5));
                txttipolog.setText(rs.getString(6));
                txtlogradouro.setText(rs.getString(7));
                txtbairro.setText(rs.getString(8));
                txtcidade.setText(rs.getString(9));
                txtuf.setText(rs.getString(10));
                
            } else {
                // Caso não tenha nenhum registro de contato
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado");
                // Depois da mensage, limpar os campos:
                limpar();
          }
        } 
        
        catch (Exception e) {
            //Caso ocorra uma exceção, exibir uma mensagem de erro
            JOptionPane.showMessageDialog(null, e);
      }
    }

    //-------------------- Método ADICIONAR ------------------
    private void adicionar() {
        String inserir = "insert into tb_thamiris(id,nome,telefone,email,cep,tipologradouro,logradouro,bairro,cidade,uf)values(?,?,?,?,?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(inserir);

            // A linha abaixo captura o conteúdo da caixa de texto txtId e ...
            //substitui pelo parâmetro '?'
            pst.setString(1, txtid.getText());
            pst.setString(2, txtnome.getText());
            pst.setString(3, txttelefone.getText());
            pst.setString(4, txtemail.getText());
            pst.setString(5, txtcep.getText());
            pst.setString(6, txttipolog.getText());
            pst.setString(7, txtlogradouro.getText());
            pst.setString(8, txtbairro.getText());
            pst.setString(9, txtcidade.getText());
            pst.setString(10, txtuf.getText());
            
            //Validação dos campos do formulário:
            // OBS: Caixa vazia no java retorna como se fosse um caracter em branco...
            //assim é possível que o banco de dados aceite valores vazios acredi-
            //tando ter valor, porém em branco, e não nulo.
            //º .isEmpty -> Verifica se o campo foi preenchido.
            
              if ((txtid.getText().isEmpty()) || (txtnome.getText().isEmpty())
                    || (txttelefone.getText().isEmpty()) || (txtcep.getText().isEmpty()) 
                    || (txttipolog.getText().isEmpty()) || (txtlogradouro.getText().isEmpty())
                    || (txtbairro.getText().isEmpty()) || (txtcidade.getText().isEmpty())
                    || (txtuf.getText().isEmpty())  ) {
          
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
              } 
              else {
                //A lógica abaixo é usada para identificar que a tabela atualizou:
                //º rowsInserted -> Linhas Inseridas.
                //º pst.executeUpdate -> Comando para atualizar a tabela.
       
                int confirma = pst.executeUpdate();

                //A linha abaixo é usada para entendimento da lógica
                //System.out.println(confirma);
                if (confirma == 1) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                }
                // Depois das mensagens, limpar os campos:
                limpar();
          }
        } 
        
        catch (Exception e) {
            // Mensagem amigável após validação dos campos
            JOptionPane.showMessageDialog(null, "O campo ID não permite valores duplicados");
      }
    }

    //--------------------- Método ALTERAR -------------------
    private void modificar() {
        String atualizar = "update tb_thamiris set nome=?,telefone=?,email=?,cep=?,tipologradouro=?,logradouro=?,bairro=?,cidade=?,uf=? where id=?";
        try {
            pst = conexao.prepareStatement(atualizar);
            pst.setString(1, txtnome.getText());   //Atenção !!! Neste caso o nome é o 1º parametro.
            pst.setString(2, txttelefone.getText());
            pst.setString(3, txtemail.getText());
            pst.setString(4, txtcep.getText());  
            pst.setString(5, txttipolog.getText());  
            pst.setString(6, txtlogradouro.getText());  
            pst.setString(7, txtbairro.getText());  
            pst.setString(8, txtcidade.getText());  
            pst.setString(9, txtuf.getText());  
            pst.setString(10,txtid.getText());     //Atenção !!! Neste caso o ID é o último parametro.

            
            
            if ((txtid.getText().isEmpty()) || (txtnome.getText().isEmpty())
                    || (txttelefone.getText().isEmpty()) || (txtcep.getText().isEmpty()) 
                    || (txttipolog.getText().isEmpty()) || (txtlogradouro.getText().isEmpty())
                    || (txtbairro.getText().isEmpty()) || (txtcidade.getText().isEmpty())
                    || (txtuf.getText().isEmpty())  ) {
          
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            }
            
            
            else {
            //Identificando se a tabela atualizou:
                    int confirmar = pst.executeUpdate();
            
                    if (confirmar == 1) {
                    JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");
            }
                        // Depois das mensagens, limpar os campos:
                            limpar();
          }
        } 
        
            catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
      }
    }
    
    //--------------------- Método EXCLUIR ----------------------
    private void excluir(){
        
        //Deletando usuário cadastrado por meio do ID:
        String deletar = "delete from tb_thamiris where id=?";
        try {
            pst=conexao.prepareStatement(deletar);
            pst.setString(1,txtid.getText());
            
            // Atenção !!! Sempre confirmar uma exclusão!
            int apagar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar este cliente?","Atenção", JOptionPane.YES_NO_OPTION);
            if (apagar == JOptionPane.YES_OPTION){
                int confirma = pst.executeUpdate(); 
                if (confirma == 1) {
                 JOptionPane.showMessageDialog(null,"Cliente deletado com sucesso! ");
                 
                 //Limpar os campos depois da mensagem
                 limpar();     
          }
        }                
      }

        catch (Exception e) {
            //Caso ocorra um outro erro
            JOptionPane.showMessageDialog(null, e);
      }
    }
    
    
    //--------------------- Método LIMPAR -----------------------  
    private void limpar(){
        
        txtid.setText(null);
        txtnome.setText(null);
        txttelefone.setText(null);
        txtemail.setText(null);
        txtcep.setText(null);
        txttipolog.setText(null);
        txtlogradouro.setText(null);
        txtbairro.setText(null);
        txtcidade.setText(null);
        txtuf.setText(null);
           
    }

//----------------------------------------------------------------------------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbldb = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtnome = new javax.swing.JTextField();
        nome = new javax.swing.JLabel();
        telefone = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        txttelefone = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        id = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cep = new javax.swing.JLabel();
        tipologradouro = new javax.swing.JLabel();
        logradouro = new javax.swing.JLabel();
        bairro = new javax.swing.JLabel();
        cidade = new javax.swing.JLabel();
        uf = new javax.swing.JLabel();
        txtcep = new javax.swing.JTextField();
        txttipolog = new javax.swing.JTextField();
        txtlogradouro = new javax.swing.JTextField();
        txtbairro = new javax.swing.JTextField();
        txtcidade = new javax.swing.JTextField();
        txtuf = new javax.swing.JTextField();
        btnadicionar = new javax.swing.JButton();
        btnpesquisar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        meunome = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro de Clientes");
        setBackground(new java.awt.Color(0, 102, 102));
        setName("CadastroDeClientes"); // NOI18N
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CONEXÃO DB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N

        lbldb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/senac/cadastro/icones/dberror.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lbldb)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbldb, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(142, 221, 221));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados Pessoais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 14))); // NOI18N

        nome.setText("*NOME:");

        telefone.setText("*TELEFONE:");

        email.setText("E-MAIL:");

        txttelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttelefoneActionPerformed(evt);
            }
        });

        id.setText("*ID:");

        jLabel2.setText("* Campos obrigatórios");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(id)
                    .addComponent(email)
                    .addComponent(telefone)
                    .addComponent(nome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtnome)
                            .addComponent(txtemail, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                            .addComponent(txttelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(id)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telefone)
                    .addComponent(txttelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(142, 219, 219));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 14))); // NOI18N

        cep.setText("*CEP:");

        tipologradouro.setText("*TIPO DO LOGRADOURO:");

        logradouro.setText("*LOGRADOURO:");

        bairro.setText("*BAIRRO:");

        cidade.setText("*CIDADE:");

        uf.setText("*UF:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cep)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcep, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(tipologradouro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttipolog, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(cidade))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(uf)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtuf, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcidade, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(logradouro)
                            .addComponent(bairro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtbairro, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtlogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cep)
                    .addComponent(cidade)
                    .addComponent(txtcep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipologradouro)
                    .addComponent(uf)
                    .addComponent(txttipolog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtuf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logradouro)
                    .addComponent(txtlogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bairro)
                    .addComponent(txtbairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnadicionar.setBackground(new java.awt.Color(0, 153, 153));
        btnadicionar.setText("ADICIONAR");
        btnadicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnadicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnadicionarActionPerformed(evt);
            }
        });

        btnpesquisar.setBackground(new java.awt.Color(0, 153, 153));
        btnpesquisar.setText("PESQUISAR");
        btnpesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnpesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpesquisarActionPerformed(evt);
            }
        });

        btndeletar.setBackground(new java.awt.Color(0, 153, 153));
        btndeletar.setText("DELETAR");
        btndeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btndeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeletarActionPerformed(evt);
            }
        });

        btnmodificar.setBackground(new java.awt.Color(0, 153, 153));
        btnmodificar.setText("MODIFICAR");
        btnmodificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CADASTRO DE CLIENTES");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Projeto criado por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 12))); // NOI18N

        meunome.setText("Thamiris da Silva Souza");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(meunome)
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(meunome, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(31, 31, 31))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(187, 187, 187)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnpesquisar)
                                .addComponent(btnadicionar)
                                .addComponent(btndeletar)
                                .addComponent(btnmodificar))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnadicionar, btndeletar, btnmodificar, btnpesquisar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnadicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnpesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnadicionar, btndeletar, btnmodificar, btnpesquisar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnadicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnadicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_btnadicionarActionPerformed

    private void txttelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttelefoneActionPerformed
      
    }//GEN-LAST:event_txttelefoneActionPerformed

    private void btnpesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpesquisarActionPerformed
        pesquisar();
    }//GEN-LAST:event_btnpesquisarActionPerformed

    private void btndeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeletarActionPerformed
        excluir();
    }//GEN-LAST:event_btndeletarActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        modificar();
    }//GEN-LAST:event_btnmodificarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bairro;
    private javax.swing.JButton btnadicionar;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton btnpesquisar;
    private javax.swing.JLabel cep;
    private javax.swing.JLabel cidade;
    private javax.swing.JLabel email;
    private javax.swing.JLabel id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbldb;
    private javax.swing.JLabel logradouro;
    private javax.swing.JLabel meunome;
    private javax.swing.JLabel nome;
    private javax.swing.JLabel telefone;
    private javax.swing.JLabel tipologradouro;
    private javax.swing.JTextField txtbairro;
    private javax.swing.JTextField txtcep;
    private javax.swing.JTextField txtcidade;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtlogradouro;
    private javax.swing.JTextField txtnome;
    private javax.swing.JTextField txttelefone;
    private javax.swing.JTextField txttipolog;
    private javax.swing.JTextField txtuf;
    private javax.swing.JLabel uf;
    // End of variables declaration//GEN-END:variables
}

