import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Arquivo<G extends Entidade>{

    protected RandomAccessFile raf;
    protected RandomAccessFile indice;
    protected String nome_Arquivo;
    protected Constructor<G> construtor;

    public Arquivo(Constructor<G> c, String nome_Arquivo)throws Exception{
        this.nome_Arquivo = nome_Arquivo;
        this.construtor = c;
        this.raf          = new RandomAccessFile(nome_Arquivo, "rw");
        this.indice       = new RandomAccessFile("indice.db",  "rw");
        if(raf.length() < 4){
            raf.writeInt(0);
            //apagar indice
        }
    }

    public void close() throws Exception {
        raf.close();
        indice.close();
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

    public G pesquisar(int idqr)throws Exception{
        raf.seek(0);
        G objeto = null;
        int i = raf.readInt();
        //System.out.println(i);
        if(i >= idqr){
            long pos = buscaI(idqr, 0, i);
            if (pos != -1){
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
        }
        return objeto;
    }

    public long buscaI(int idqr, int esq, int dir) throws Exception{
        if (dir >= esq){
            long meio = ((esq + dir)/2) * 12;
            indice.seek(meio);
            int id = indice.readInt();
            if(id == idqr) return indice.readLong();
            else if (id < idqr) buscaI(idqr, ((esq + dir)/2) + 1, dir);
            else buscaI(idqr, esq, ((esq + dir)/2) - 1 );
        }
        return (long) -1;
    }

    public ArrayList<G> toList()throws Exception{
        G objeto;
        ArrayList<G> lista = new ArrayList<G>();
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
            if (lapide == ' ') lista.add(objeto);
        }
        return lista;
    }

    public boolean remover(int idqr)throws Exception{
        raf.seek(0);
        int i = raf.readInt();
        if(i >= idqr){
            long pos = buscaI(idqr, 0, i);
            raf.seek(pos);
            byte lapide = raf.readByte();
            if(lapide == ' '){
                raf.seek(pos);
                raf.writeByte('*');
                System.out.println("Removido com sucesso");
                return true;
            }else{ 
                System.out.println("Produto foi removido anteriormente");
                return false;
            }
        }
        else {
            System.out.println("Produto inexistente");
            return false;
        }
    }

    public void alterar(int idqr, G objeto)throws Exception{
        boolean removeu;
        raf.seek(0);
        int i = raf.readInt();
        if(i >= idqr){
            removeu = this.remover(idqr);
            if(removeu){
                this.inserir(objeto);
                System.out.println("Novo ID: " + i);
            }
        }
        else System.out.println("Produto inexistente");
    }

}
