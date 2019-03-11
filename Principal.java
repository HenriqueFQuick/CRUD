import java.util.Scanner;
public class Principal{

    public static void main(String[] args){
        Scanner read = new Scanner(System.in);
        String nome, descricao, marca, origem;
        float preco;
        Produto p;
        int id; 
        byte opcao;
        boolean erro, result;
        Arquivo<Produto> arq;
        try{
            arq = new Arquivo<>(Produto.class.getConstructor(), "Produto");
            boolean fecharMenu = false;
            do{
                //menu
                System.out.println("\n\t*** MENU ***");
                System.out.println("0 - Adicionar produto\n1 - Remover produto\n2 - Alterar produto\n3 - Consultar produto\n4 - Listar produtos cadastrados\n5 - Sair");
                System.out.print("Digite a opção: ");
                opcao = read.nextByte();
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
                        do{
                            erro = false;
                            System.out.println("\nAdicionar novo produto?");
                            System.out.print("1 - SIM\n2 - NÂO\nR: ");
                            switch (read.nextByte()){
                                case 1:
                                    id = arq.inserir(new Produto(nome,descricao,preco,marca,origem));
                                    System.out.println("\nProduto inserido com o ID: " + id);   
                                    break;
                                case 2:
                                    System.out.println("\nNovo produto não foi inserido!");
                                    break;
                                default:
                                    System.out.println("\nOpção Inválida!\n");
                                    erro = true;
                                    break;
                            }
                        } while (erro);
                        break;

                    case 1:
                        System.out.println("\t** Remover produto **\n");
                        System.out.print("ID do produto a ser removido: ");
                        id = read.nextInt();
                        do{
                            erro = false;
                            System.out.println("\nRemover produto?");
                            System.out.print("1 - SIM\n2 - NÂO\nR: ");
                            switch (read.nextByte()){
                                case 1:
                                    result = arq.remover(id); 
                                    if(result) System.out.println("Removido com sucesso!");
                                    else System.out.println("Produto não encontrado!"); 
                                    break;
                                case 2:
                                    System.out.println("\nOperação Cancelada!");
                                    break;
                                default:
                                    System.out.println("\nOpção Inválida!\n");
                                    erro = true;
                                    break;
                            }
                        } while (erro); 
                        break;

                    case 2:
                        System.out.println("\t** Alterar produto **\n");
                        do{
                            erro = false;
                            System.out.print("ID do produto a ser alterado: ");
                            id = read.nextInt();
                            if(id <= 0){
                                erro = true;
                                System.out.println("ID Inválida! ");
                            }
                            System.out.println();
                        } while(erro);   
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
                        do{
                            erro = false;
                            System.out.println("\nAlterar produto?");
                            System.out.print("1 - SIM\n2 - NÂO\nR: ");
                            switch (read.nextByte()){
                                case 1:
                                    result = arq.alterar(id, new Produto(nome,descricao,preco,marca,origem));
                                    if(result) System.out.println("Alterado com sucesso!");
                                    else System.out.println("Produto para alterar não encontrado!");  
                                    break;
                                case 2:
                                    System.out.println("\nOperação Cancelada!");
                                    break;
                                default:
                                    System.out.println("\nOpção Inválida!\n");
                                    erro = true;
                                    break;
                            }
                        } while (erro); 
                        break;

                    case 3:
                        System.out.println("\t** Consultar produto **\n");
                        do{
                            erro = false;
                            System.out.print("ID do produto a ser consultado: ");
                            id = read.nextInt();
                            if(id <= 0){
                                erro = true;
                                System.out.println("ID Inválida! ");
                            }
                            System.out.println();
                        } while(erro);
                        p = arq.pesquisar(id);
                        if (p != null && p.idProduto != -1 ){
                            System.out.println(p);
                        }
                        else System.out.println("Produto não encontrado!");
                        break;
                    case 4:
                        System.out.println("\t** Lista dos produtos cadastrados **\n");
                        for(int i = 1; i <= arq.ultimoID(); i++){
                            p = arq.pesquisar(i);
                            if (p != null && p.idProduto != -1 ){
                                System.out.println(p);
                                System.out.println();
                                Thread.sleep(500);
                            }  
                        }
                        break;
                    case 5:
                        fecharMenu = true;
                        break;
                    default:                    
                        System.out.println("Opcao invalida!\n");
                        Thread.sleep(1000);
                        break;
                }
            }while(!fecharMenu);
            arq.close();
        } 
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
