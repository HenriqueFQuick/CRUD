import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Arquivo<G extends Entidade>{

    protected RandomAccessFile raf;
    protected RandomAccessFile indice;
    protected String nomeArquivo;
    protected Constructor<G> construtor;

    public Arquivo(Constructor<G> c, String nomeArquivo)throws Exception{
        this.nomeArquivo = nomeArquivo;
        this.construtor   = c;
        this.raf          = new RandomAccessFile(nomeArquivo + ".db", "rw");
        this.indice       = new RandomAccessFile(nomeArquivo + ".idx", "rw");
        if(raf.length() < 4){
            raf.writeInt(0);
            //apagar indice
        }
    }

    public void close() throws Exception {
        raf.close();
        indice.close();
    }

    public int ultimoID() throws Exception {
        raf.seek(0);
        return raf.readInt();
    }

    public int inserir(G objeto)throws Exception{
        int ultimoID;
        ultimoID = this.ultimoID();
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
        return ultimoID;
    } 

    public G pesquisar(int idqr)throws Exception{
        raf.seek(0);
        G objeto = null;
        int i = raf.readInt();
        if(i >= idqr){
            long pos = buscaI(idqr, 0, i);
            if (pos != -1){
                raf.seek(pos);
                byte lapide = raf.readByte();
                int tamanho = raf.readShort();
                byte[] b = new byte[tamanho];
                if(lapide == ' '){
                    objeto = construtor.newInstance();
                    raf.read(b);
                    objeto.fromByteArray(b);
                }  
            }
        }
        return objeto;
    }

    public long buscaI(int idqr, int esq, int dir) throws Exception{
        long addr = 0;
        if (dir >= esq){
            long meio = ((esq + dir)/2) * 12;
            indice.seek(meio);
            int id = indice.readInt();
            if(id == idqr){ 
                addr = indice.readLong();
            }
            else{   
                if (id < idqr) addr = buscaI(idqr, ((esq + dir)/2) + 1, dir);
                else addr = buscaI(idqr, esq, ((esq + dir)/2) - 1 );
            }
        }
        return addr;
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
        boolean result = false;
        if(i >= idqr){
            long pos = buscaI(idqr, 0, i);
            if (pos != -1){
                raf.seek(pos);
                byte lapide = raf.readByte();
                if(lapide == ' '){
                    raf.seek(pos);
                    raf.writeByte('*');
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean alterar(int idqr, G objeto)throws Exception{
        boolean removeu;
        boolean result = false;
        raf.seek(0);
        int i = raf.readInt();
        if(i >= idqr){
            removeu = this.remover(idqr);
            if(removeu){
                result = this.inserirAlterado(objeto, idqr);
            }
        }
        return result;
    }

    public boolean inserirAlterado(G objeto, int idqr) throws Exception{
        objeto.setID(idqr);
        indice.seek(0);
        for(int i = 0; i < indice.length() - 12; i = i + 8){
            int idIndice = indice.readInt();
            if(idIndice == idqr){
                indice.writeLong(raf.length());
            }
        }
        raf.seek(raf.length());
        byte[] b = objeto.toByteArray();
        raf.writeByte(' ');
        raf.writeShort(b.length);
        raf.write(b);
        return true;
    }

}
