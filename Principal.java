import java.util.Scanner;
public class Principal{

    public static void main(String[] args){
        Scanner read = new Scanner(System.in);
        String nome, descricao, marca, origem;
        float preco;
        int id;
        Arquivo<Produto> arq;
        try{
            arq = new Arquivo<>(Produto.class.getConstructor(), "jonathan.db");
            boolean fecharMenu = false;
            do{
                //menu
                System.out.println("\n\t*** MENU ***");
                System.out.println("0 - Adicionar produto\n1 - Remover produto\n2 - Alterar produto\n3 - Consultar produto\n4 - Sair");
                System.out.print("Digite a opção: ");
                int opcao = read.nextInt();
                System.out.println();
                switch (opcao){
                    case 0:
                        System.out.println("\t** Adicionar produto **\n");
                        System.out.print("Nome do produto: ");
                        nome = read.nextLine();
                        nome = read.nextLine();
                        System.out.print("Descricao do produto: ");
                        descricao = read.nextLine();
                        System.out.print("Preco do produto: ");
                        preco = read.nextFloat();
                        System.out.print("Marca do produto: ");
                        marca = read.nextLine();
                        marca = read.nextLine();
                        System.out.print("Origem do produto: ");
                        origem = read.nextLine();
                        arq.inserir(new Produto(nome,descricao,preco,marca,origem));
                        break;

                    case 1:
                        System.out.println("\t** Remover produto **\n");
                        System.out.print("ID do produto a ser removido: ");
                        id = read.nextInt();
                        arq.remover(id);
                        break;

                    case 2:
                        System.out.println("\t** Alterar produto **\n");
                        System.out.print("ID do produto a ser alterado: ");
                        id = read.nextInt();
                        System.out.print("Nome do produto: ");
                        nome = read.nextLine();
                        nome = read.nextLine();
                        System.out.print("Descricao do produto: ");
                        descricao = read.nextLine();
                        System.out.print("Preco do produto: ");
                        preco = read.nextFloat();
                        System.out.print("Marca do produto: ");
                        marca = read.nextLine();
                        marca = read.nextLine();
                        System.out.print("Origem do produto: ");
                        origem = read.nextLine();
                        arq.alterar(id, new Produto(nome,descricao,preco,marca,origem));
                        break;

                    case 3:
                        System.out.println("\t** Consultar produto **\n");
                        System.out.print("ID do produto a ser consultado: ");
                        id = read.nextInt();
                        System.out.println();
                        Produto p = arq.pesquisarI(id);
                        if (p != null){
                            System.out.println(p);
                        }
                        else System.out.println("Produto não encontrado!");
                        break;
                    case 4:
                        fecharMenu = true;
                        break;
                    default:                    
                        System.out.println("Opcao invalida!\n");
                        Thread.sleep(1000);
                        break;
                }
            }while(!fecharMenu);
        } 
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
