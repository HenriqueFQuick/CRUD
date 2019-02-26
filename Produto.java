import java.io.*;
class Produto{

        protected int    idProduto;
        protected String nome_Produto;
        protected String descricao;
        protected float  preco;
        protected String marca;
        protected String origem; 

        public Produto(){
            this.idProduto    = -1;
            this.nome_Produto = "";
            this.descricao    = "";
            this.preco        = 0;
            this.marca        = "";
            this.origem       = "";
        }

        public void setID(int id){
            this.idProduto = id;
        }

        public int getID(){
            return this.idProduto;
        }

        public Produto(String nome_Produto, String descricao, float preco, String marca, String origem){
            this.nome_Produto = nome_Produto;
            this.descricao    = descricao;
            this.preco        = preco;
            this.marca        = marca;
            this.origem       = origem;

        }

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
        }

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
    }

    public String toString(){
        return "Id: " + this.idProduto+" \nNome: " + this.nome_Produto+"\nDescricao:  " + this.descricao+"\nPreco: " + this.preco+ "\nMarca: " + this.marca+"\nOrigem:" + this.origem;
    }
}