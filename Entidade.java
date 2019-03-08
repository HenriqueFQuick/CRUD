import java.io.IOException;

public interface Entidade{
    public int getID();
    public void setID(int id);
    public byte[] toByteArray() throws Exception;
    public void fromByteArray(byte[] b) throws Exception;   
}
