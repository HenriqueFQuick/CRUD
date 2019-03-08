import java.io.*;
class Arquivo{

    protected RandomAccessFile raf;
    protected RandomAccessFile raf2;
    protected String nome_Arquivo;

    public Arquivo(String nome_Arquivo)throws Exception{
        this.nome_Arquivo = nome_Arquivo;
        this.raf          = new RandomAccessFile(nome_Arquivo, "rw");
        this.raf2         = new RandomAccessFile("indice.db",  "rw");
        if(raf.length() < 4){
            raf.writeInt(0);
        }
    } 

    public void escrever(Produto produto)throws Exception{
        int ultimoID = 0;
        raf.seek(0);
        ultimoID = raf.readInt();
        produto.setID(ultimoID+1);      

        raf2.seek(raf2.length());
        raf2.writeInt(produto.getID());
        raf2.writeLong(raf.length());
    
        raf.seek(raf.length());
        byte[] b = produto.toByteArray();
        raf.writeByte(' ');
        raf.writeShort(b.length);
        raf.write(b);

        raf.seek(0);
        raf.write(produto.getID());


    }

    public void ler()throws Exception{
        Produto p = new Produto();
        short tamanho;
        byte[] b;
        byte lapide;

        raf.seek(4);
        while(raf.getFilePointer() < raf.length()) {
            lapide = raf.readByte();
            tamanho = raf.readShort();
            b = new byte[tamanho];
            raf.read(b);
            p.fromByteArray(b);
            System.out.println(p);
        }
    }

}
