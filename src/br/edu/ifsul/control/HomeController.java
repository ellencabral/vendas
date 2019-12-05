package br.edu.ifsul.control;

import br.edu.ifsul.model.Cliente;
import br.edu.ifsul.model.Item;
import br.edu.ifsul.model.Pedido;
import br.edu.ifsul.model.Produto;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HomeController {

    private static double totalPedido;

    public static void main(String[] args) {

        int opcao = 0;
        do {
            System.out.println("\n******** Home ********");
            System.out.print(
                    "1. Vender" +
                            "\n2. Manter Cliente" +
                            "\n3. Manter Produto" +
                            "\n4. Manter Pedido" +
                            "\nDigite a opção (0 para sair): "
            );
            Scanner s = new Scanner(System.in);
            opcao = s.nextInt();
            switch (opcao) {
                case 1:
                    VendasController telaVendas = new VendasController();
                    telaVendas.main(null);
                    break;
                case 2:
                    ClienteController telaCliente = new ClienteController();
                    telaCliente.main(null);
                    break;
                case 3:
                    ProdutoController telaProduto = new ProdutoController();
                    telaProduto.main(null);
                    break;
                case 4:
                    PedidoController telaPedido = new PedidoController();
                    telaPedido.main(null);
                    break;
                default:
                    if(opcao != 0) System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
