import java.util.Scanner;
public class Principal{

    public static void main(String[] args){
        Scanner read = new Scanner(System.in);
        Arquivo arq;
        try{
            arq = new Arquivo("jonathan.db");
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

                    arq.escrever(new Produto(nome,descricao,preco,marca,origem));
                break;
                case 1:
                    System.out.println("Remover produto");

                break;
                case 2:
                    System.out.println("Alterar produto");

                break;
                case 3:
                    System.out.println("Consultar produto");
                    arq.ler();
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
