import java.io.*;
class Produto implements Entidade{

    protected int    idProduto;
    protected String nome_Produto;
    protected String descricao;
    protected float  preco;
    protected String marca;
    protected String origem; 

    //Construtor vazio
    public Produto(){
        this.idProduto    = -1;
        this.nome_Produto = "";
        this.descricao    = "";
        this.preco        = 0;
        this.marca        = "";
        this.origem       = "";
    }//end Produto 

    public void setID(int id){
        this.idProduto = id;
    }//end setId

    public int getID(){
        return this.idProduto;
    }//end getId


    //construtor com parametros
    public Produto(String nome_Produto, String descricao, float preco, String marca, String origem){
        this.nome_Produto = nome_Produto;
        this.descricao    = descricao;
        this.preco        = preco;
        this.marca        = marca;
        this.origem       = origem;
    }//end Produto

    //Retorna um array de bytes com os bytes para escrever no arquivo
    public byte[] toByteArray() throws Exception{
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        DataOutputStream saida = new DataOutputStream(dados);

        saida.writeInt(this.idProduto);
        saida.writeUTF(this.nome_Produto);
        saida.writeUTF(this.descricao);
        saida.writeFloat(this.preco);
        saida.writeUTF(this.marca);
        saida.writeUTF(this.origem);

        return dados.toByteArray();
    }//end toByteArray

    //Coloca nos atributos os bytes lidos doa arquivo
    public void fromByteArray(byte[] b) throws Exception {
        ByteArrayInputStream dados   = new ByteArrayInputStream(b);
        DataInputStream      entrada = new DataInputStream(dados);

        this.idProduto    = entrada.readInt();
        this.nome_Produto = entrada.readUTF();
        this.descricao    = entrada.readUTF();
        this.preco        = entrada.readFloat();
        this.marca        = entrada.readUTF();
        this.origem       = entrada.readUTF();

        entrada.close();
    }//end fromByteArray

    public String toString(){
        return "Id: "           + this.idProduto    + 
                "\nNome: "      + this.nome_Produto + 
                "\nDescricao: " + this.descricao    + 
                "\nPreco: "     + this.preco        + 
                "\nMarca: "     + this.marca        + 
                "\nOrigem: "    + this.origem;
    }//end toString
}//end Produto
