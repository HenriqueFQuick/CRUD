import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Arquivo<G extends Entidade>{

    protected RandomAccessFile raf;
    protected RandomAccessFile indice;
    protected String nome_Arquivo;
    protected Constructor<G> construtor;

    public Arquivo( Constructor<G> c, String nome_Arquivo)throws Exception{
        this.nome_Arquivo = nome_Arquivo;
        this.construtor = c;
        this.raf          = new RandomAccessFile(nome_Arquivo, "rw");
        this.indice       = new RandomAccessFile("indice.db",  "rw");
        if(raf.length() < 4){
            raf.writeInt(0);
            //apagar indice
        }
    } 

    public void inserir(G objeto)throws Exception{
        int ultimoID;
        raf.seek(0);
        ultimoID = raf.readInt();
        raf.seek(0);
        objeto.setID(ultimoID+1);      
        
        

        indice.seek(indice.length());
        indice.writeInt(objeto.getID());
        indice.writeLong(raf.length());
        
        raf.seek(raf.length());
        byte[] b = objeto.toByteArray();
        raf.writeByte(' ');
        raf.writeShort(b.length);
        raf.write(b);

        raf.seek(0);
        raf.writeInt(objeto.getID());
        raf.seek(0);
        ultimoID = raf.readInt();
        System.out.println(ultimoID);


    }

    public G pesquisarI(int idqr)throws Exception{
        raf.seek(0);
        G objeto = null;
        int i = raf.readInt();
        //System.out.println(i);
        if(i >= idqr){
            long pos = buscaI(i, 1, idqr);
            raf.seek(pos);
            byte lapide = raf.readByte();
            int tamanho = raf.readShort();
            byte[] b = new byte[tamanho];
            objeto = construtor.newInstance();
            if(lapide == ' '){
                raf.read(b);
                objeto.fromByteArray(b);
            }
        }
        raf.close();
        return objeto;
    }

    public long buscaI(int dir, int esq, int idqr)throws Exception{
        long ultimo   = (dir/2)*12;
        long primeiro = (esq/2)*12;
        long metade   = ((esq/2)+(dir/2))*12;
        /*System.out.println(esq);
        System.out.println(dir);
        System.out.println(primeiro);
        System.out.println(ultimo);
        System.out.println(metade);*/
        indice.seek(metade);
       // System.out.println("AKI 2");
        int a = indice.readInt();
       // System.out.println("Aki 3");
        if(a == idqr){
        //    System.out.println("Aki4");
            return indice.readLong();
        }else if( a < idqr){ buscaI(dir, ((esq+dir)/2) + 1, idqr);
              }else buscaI(((esq+dir)/2) -1, esq, idqr);
        return 0;
    }

    public ArrayList<G> toList()throws Exception{
        G objeto;
        ArrayList<G> lista = new ArrayList();
        short tamanho;
        byte[] b;
        byte lapide;

        raf.seek(4);
        while(raf.getFilePointer() < raf.length()) {
            lapide = raf.readByte();
            tamanho = raf.readShort();
            b = new byte[tamanho];
            raf.read(b);
            objeto = construtor.newInstance();
            objeto.fromByteArray(b);
            if(lapide == ' '){
                lista.add(objeto);
            }
            
        }
        return lista;
    }

    public void remover(int idqr)throws Exception{
        raf.seek(0);
        int i = raf.readInt();
        if(i >= idqr){
            long pos = buscaI(i, 0, idqr);
            raf.seek(pos);
            byte lapide = raf.readByte();
            if(lapide == ' '){
                raf.seek(pos);
                raf.writeByte('*');
                System.out.println("Removido com sucesso");
            }else System.out.println("Produto foi removido anteriormente");
        }else System.out.println("Produto inexistente");
    }

    public void alterar(int idqr, G objeto)throws Exception{
        raf.seek(0);
        int i = raf.readInt();
        if(i >= idqr){
            this.remover(idqr);
            this.inserir(objeto);
            System.out.println("Novo ID: " + i);
        }else System.out.println("Produto inexistente");
    }

}
