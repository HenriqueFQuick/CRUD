import java.util.Scanner;
public class Principal{

    public static void main(String[] args){
        Scanner read = new Scanner(System.in);
        Arquivo<Produto> arq;
        try{
            arq = new Arquivo<>(Produto.class.getConstructor(), "jonathan.db");
            //menu
            System.out.println("Adicionar produto: 0\nRemover   produto: 1\nAlterar   produto: 2\nConsutar  produto: 3");

            int opcao = read.nextInt();

            switch (opcao){
                case 0:
                    System.out.println("Adicionar produto");
                    System.out.println("Nome do produto:");
                    String nome = read.nextLine();
                    nome = read.nextLine();
                    System.out.println("Descricao do produto");
                    String descricao = read.nextLine();
                    System.out.println("Preco do produto");
                    float preco = read.nextFloat();
                    System.out.println("Marca do produto");
                    String marca = read.nextLine();
                    marca = read.nextLine();
                    System.out.println("Origem do produto");
                    String origem = read.nextLine();

                    arq.inserir(new Produto(nome,descricao,preco,marca,origem));
                break;
                case 1:
                    System.out.println("Remover produto");
                    System.out.println("ID do produto a ser removido: ");
                    int id = read.nextInt();
                    arq.remover(id);
                break;
                case 2:
                    System.out.println("Alterar produto");
                    System.out.println("ID do produto a ser alterado: ");
                    id = read.nextInt();
                    System.out.println("Nome do produto:");
                    nome = read.nextLine();
                    nome = read.nextLine();
                    System.out.println("Descricao do produto");
                    descricao = read.nextLine();
                    System.out.println("Preco do produto");
                    preco = read.nextFloat();
                    System.out.println("Marca do produto");
                    marca = read.nextLine();
                    marca = read.nextLine();
                    System.out.println("Origem do produto");
                    origem = read.nextLine();

                    arq.alterar(id, new Produto(nome,descricao,preco,marca,origem));

                break;
                case 3:
                    System.out.println("Consultar produto");
                    System.out.println("ID do produto a ser consultado: ");
                    id = read.nextInt();
                    Produto p = arq.pesquisarI(id);
                    System.out.println(p);
                break;
                default:
                    System.out.println("Opcao invalida");
                    
                break;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
