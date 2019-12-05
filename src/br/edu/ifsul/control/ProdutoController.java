package br.edu.ifsul.control;

import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.model.Produto;

import java.util.List;
import java.util.Scanner;

public class ProdutoController {

    private static Scanner s = new Scanner(System.in);
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public static void main(String[] args) {
        ProdutoController telaProduto = new ProdutoController();
        int opcao = 0;
        do{
            System.out.println("\n\n******** Produto ********");
            System.out.print(
                    "1. Inserir" +
                    "\n2. Alterar" +
                    "\n3. Excluir (tornar inativo)" +
                    "\n4. Lista todos os produtos (ativos e inativos)" +
                    "\n5. Lista todos os produtos (ativos)" +
                    "\n6. Listar produtos pelo nome" +
                    "\n7. Listar produto pelo código" +
                    "\nDigite a opção (0 para sair): "
            );

            opcao = s.nextInt();
            switch (opcao){
                case 1:
                    telaProduto.insert();
                    break;
                case 2:
                    telaProduto.update();
                    break;
                case 3:
                    telaProduto.tornarInativo();
                    break;
                case 4:
                    telaProduto.listarAtivosInativos();
                    break;
                case 5:
                    telaProduto.listarPorSituacao(telaProduto);
                    break;
                case 6:
                    telaProduto.localizarPorNome();
                    break;
                case 7:
                    telaProduto.localizarPeloCodigo();
                    break;
                default:
                    if(opcao != 0) System.out.println("Opção inválida.");
            }
        }while (opcao != 0);
    }

    private void insert() {
        System.out.println("Digite os dados do produto\nNome:");
        Produto produto = new Produto();
        produto.setNome(s.next());

        System.out.println("Valor:");
        produto.setValor(s.nextDouble());

        System.out.println("Descricao: ");
        produto.setDescricao(s.next());

        System.out.println("Estoque: ");
        produto.setEstoque(s.nextInt());

        produto.setSituacao(true);

        if(produtoDAO.insert(produto)){
            System.out.println("Produto Salvo");
        }else{
            System.out.println("Erro ao tentar salvar o produto. Por favor, contate o adminstrador.");
        }
    }

    private void update() {
        Produto produto;
        int opcao;

        do {
            opcao = 1;
            System.out.print("Digite o código do produto (sair-0): ");
            produto = produtoDAO.getProdutoById(s.nextInt());

            if(produto == null){
                System.out.println("Código inválido.");
            }else{
                System.out.println("Nome: " + produto.getNome());
                System.out.print("Alterar? (0-sim/1-não) ");
                if(s.nextInt() == 0) {
                    System.out.println("Digite o novo nome: ");
                    produto.setNome(s.next());
                }

                System.out.println("Valor: " + produto.getValor());
                System.out.print("Alterar? (0-sim/1-não) ");
                if(s.nextInt() == 0){
                    System.out.print("Digite o novo valor: ");
                    produto.setValor(s.nextDouble());
                }

                System.out.println("Descricao: " + produto.getDescricao());
                System.out.print("Alterar? (0-sim/1-não) ");
                if(s.nextInt() == 0){
                    System.out.print("Digite a nova descricao: ");
                    produto.setDescricao(s.next());
                }

                System.out.println("Quantidade: " + produto.getEstoque());
                System.out.print("Alterar? (0-sim/1-não) ");
                if(s.nextInt() == 0){
                    System.out.print("Digite a nova quantidade: ");
                    produto.setEstoque(s.nextInt());
                }

                produto.setSituacao(true);
                if(produtoDAO.update(produto)){
                    System.out.println("Produto salvo:" + produtoDAO.getProdutoById(produto.getId()));
                }else{
                    System.out.println("Erro ao tentar salvar o produto. Por favor, contate o adminstrador.");
                }
                opcao = 0;
            }
        } while (produto == null || opcao != 0);
    }

    private void tornarInativo() {
        System.out.println("Digite o código do produto: ");
        int codigo = s.nextInt();
        Produto produto = produtoDAO.getProdutoById(codigo);

        if(produto != null) {
            System.out.println(produto);
            System.out.println("Confirmar a operação? (0-sim/1-não)");
            if(s.nextInt() == 0) {
                produtoDAO.softDelete(produto);
                System.out.println("Produto excluido.");
            } else {
                System.out.println("Operação cancelada.");
            }
        } else {
            System.out.println("Código não encontrado.");
        }
    }

    private void listarAtivosInativos() {
        System.out.println("\n**** Lista de produtos ativos e inativos: ****\n" + produtoDAO.getProdutos());
    }

    private static void listarPorSituacao(ProdutoController telaProduto) {
        System.out.print("Deseja listar os ativos ou os inativos (ativos-0/inativos-1)? " );
        if(s.nextInt() == 0){
            System.out.println("\n****Lista de Produtos Ativos: " + telaProduto.produtoDAO.getProdutosBySituacao(true));
        }else{
            System.out.println("\n****Lista de Produtos Inativos: " + telaProduto.produtoDAO.getProdutosBySituacao(false));
        }
    }

    private void localizarPorNome() {
        System.out.println("Digite o nome do produto: ");
        String nome = s.next();
        System.out.println("Chave de pesquisa" + nome);

        List<Produto> produtos = produtoDAO.getProdutosByName(nome);

        if(produtos.isEmpty()) {
            System.out.println("Não há registros correspondentes para: " + nome);
        } else {
            System.out.println(produtos);
        }
    }

    private void localizarPeloCodigo() {
        System.out.print("Digite o código do produto: ");
        Produto produto = produtoDAO.getProdutoById(s.nextInt());
        if(produto != null){
            System.out.print(produto);
        }else{
            System.out.println("Código não localizado.");
        }
    }

    private void ativarProdutoPeloCodigo() {
        System.out.print("Digite o código do produto: ");
        int codigo = s.nextInt();
        Produto produto = produtoDAO.getProdutoById(codigo);
        if(produto != null){
            System.out.println(produto);
            System.out.println("Confirmar a operação? (0-sim/1-não)");
            if(s.nextInt() == 0){
                if(produtoDAO.softDelete(produto)) System.out.println(produtoDAO.getProdutoById(codigo));
            }else{
                System.out.println("Operação cancelada.");
            }
        }else{
            System.out.println("Código não localizado.");
        }
    }

}
