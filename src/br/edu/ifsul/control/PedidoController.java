package br.edu.ifsul.control;

import br.edu.ifsul.dao.ClienteDAO;
import br.edu.ifsul.dao.ItemDAO;
import br.edu.ifsul.dao.PedidoDAO;
import br.edu.ifsul.model.Cliente;
import br.edu.ifsul.model.Item;
import br.edu.ifsul.model.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PedidoController {


    private static Scanner s = new Scanner(System.in);
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private static ItemDAO itemDAO = new ItemDAO();
    private static ClienteDAO clienteDAO = new ClienteDAO();

    public static void main(String[] args) {
        PedidoController telaPedido = new PedidoController();
        int opcao = 0;
        do{
            System.out.println("\n\n******** Pedidos ********");
            System.out.print(
                              "1. Check-out do pedido" +
                            "\n2. Listar itens de um pedido" +
                            "\n3. Excluir item de um pedido" +
                            "\n4. Lista todos os pedidos (inativos)" +
                            "\n5. Lista todos os pedidos (ativos)" +
                            "\n6. Lista todos os pedidos por período" +
                            "\n7. Listar todos os pedidos por cliente" +
                            "\nDigite a opção (0 para sair): "
            );
            Scanner s = new Scanner(System.in);
            opcao = s.nextInt();
            switch (opcao){
                case 1:
                    telaPedido.checkout();
                    break;
                case 2:
                    telaPedido.listarItensPorPedido();
                    break;
                case 3:
                    telaPedido.removerItem();
                    break;
                case 4:
                    System.out.println("em desenvolvimento " + opcao);
                    break;
                case 5:
                    System.out.println("em desenvolvimento " + opcao);
                    break;
                case 6:
                    System.out.println("em desenvolvimento " + opcao);
                    break;
                case 7:
                    System.out.println("em desenvolvimento " + opcao);
                    break;
                default:
                    if(opcao != 0) System.out.println("Opção inválida.");
            }
        }while (opcao != 0);
    }

    private void listarItensPorPedido() {
        System.out.println("Digite o id do pedido: ");

        int id_pedido = s.nextInt();

        while(id_pedido == 0) {
            System.out.println("Código inválido.");
            System.out.println("Digite o código do pedido: ");
            id_pedido = s.nextInt();
        }

        Pedido pedido = pedidoDAO.getPedidoById(id_pedido);

        if(pedido != null) {
            List<Item> itens = itemDAO.getItensByPedido(pedido.getId());
            System.out.println(itens);
            System.out.println(pedido.getCliente());
        }
        else {
            System.out.println("Código não localizado.");
            listarItensPorPedido();
        }
    }

    private void checkout() {
        System.out.println("Digite o código do pedido: ");

        int id_pedido = s.nextInt();

        while(id_pedido == 0) {
            System.out.println("Código inválido.");
            System.out.println("Digite o código do pedido: ");
            id_pedido = s.nextInt();
        }

        Pedido pedido = pedidoDAO.getPedidoById(id_pedido);

        if(pedido != null){
            System.out.println(pedido);
            System.out.println("Confirmar a operação? (0-sim/1-não)? ");
            if(s.nextInt() == 0) {
                pedidoDAO.checkout(pedido);
                System.out.println("Check-out realizado.");
            }
            else {
                System.out.println("Operação cancelada.");
            }
        }
        else {
            System.out.println("Código não localizado.");
            checkout();
        }
    }

    private void removerItem() {
        System.out.println("Digite o código do pedido: ");
        Pedido pedido = pedidoDAO.getPedidoById(s.nextInt());

        if(pedido != null) {
            System.out.println(pedido);
            List<Item> itens = itemDAO.getItensByPedido(pedido.getId());

            System.out.println(itens);
            System.out.println("\nDigite o código do item que deseja remover: ");
            Item item = itemDAO.getItemById(s.nextInt());

            if(item != null) {
                System.out.println(item);
                System.out.println("Confirmar a operação? (0-sim/1-não)");
                if(s.nextInt() == 0) {
                    itemDAO.softDelete(item);
                    System.out.println("Item removido.");
                    System.out.println(itens);
                } else {
                    System.out.println("Operação cancelada.");
                }
            } else{
                System.out.println("Código não localizado.");
            }
        } else {
            System.out.println("Código não localizado.");
        }
    }
}
