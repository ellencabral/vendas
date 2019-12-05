package br.edu.ifsul.control;

import br.edu.ifsul.dao.ClienteDAO;
import br.edu.ifsul.model.Cliente;

import java.util.List;
import java.util.Scanner;

public class ClienteController {

    private static Scanner s = new Scanner(System.in);
    private ClienteDAO  clienteDAO = new ClienteDAO();

    public static void main(String[] args) {
        ClienteController telaCliente = new ClienteController();
        int opcao = 0;
        do{
            System.out.println("\n\n******** Cliente ********");
            System.out.print(
                    "1. Inserir" +
                    "\n2. Alterar" +
                    "\n3. Excluir (tornar inativo)" +
                    "\n4. Lista todos os clientes (ativos e inativos)" +
                    "\n5. Lista todos os clientes pela situação (ativos ou inativos)" +
                    "\n6. Localizar clientes pelo nome" +
                    "\n7. Localizar cliente pelo código" +
                    "\n8. Ativar um cliente pelo código" +
                    "\nDigite a opção (0 para sair): "
            );
            opcao = s.nextInt();
            switch (opcao){
                case 1:
                    telaCliente.insert();
                    break;
                case 2:
                    telaCliente.update();
                    break;
                case 3:
                    telaCliente.tornarInativo();
                    break;
                case 4:
                    telaCliente.listarAtivosInativos();
                    break;
                case 5:
                    telaCliente.listarPorSituacao(telaCliente);
                    break;
                case 6:
                    telaCliente.localizarPorNome();
                    break;
                case 7:
                    telaCliente.localizarPeloCodigo();
                    break;
                case 8:
                    telaCliente.ativarClientePeloCodigo();
                    break;
                default:
                    if(opcao != 0) System.out.println("Opção inválida.");
            }
        }while (opcao != 0);
    }

    private void listarAtivosInativos() {
        System.out.println("\n**** Lista de clientes ativos e inativos:\n" + clienteDAO.getClientes());
    }


    /**********
     *  Métodos utiĺitários.
     */
    //case 1
    private void insert() {
        Cliente cliente = new Cliente();
        System.out.println("Digite os dados do cliente\nNome:");
        cliente.setNome(s.next());
        System.out.println("sobrenome:");
        cliente.setSobrenome(s.next());
        cliente.setSituacao(true);
        if(clienteDAO.insert(cliente)){
            System.out.println("Cliente Salvo");
        }else{
            System.out.println("Erro ao tentar salvar o cliente. Por favor, contate o adminstrador.");
        }
    }

    //case 2
    private void update() {
        Cliente cliente;
        int opcao;
        do{
            opcao = 1;
            System.out.print("Digite o código do cliente (sair-0): ");
            cliente = clienteDAO.getClienteById(s.nextInt());
            if(cliente == null){
                System.out.println("Código inválido.");
            }else{
                System.out.println("Nome: " + cliente.getNome());
                System.out.print("Alterar? (0-sim/1-não) ");
                if(s.nextInt() == 0){
                    System.out.println("Digite o novo nome: ");
                    cliente.setNome(s.next());
                }
                System.out.println("Sobrenome: " + cliente.getSobrenome());
                System.out.print("Alterar? (0-sim/1-não) ");
                if(s.nextInt() == 0){
                    System.out.print("Digite o novo sobrenome: ");
                    cliente.setSobrenome(s.next());
                }
                cliente.setSituacao(true);
                if(clienteDAO.update(cliente)){
                    System.out.println("Cliente salvo:" + clienteDAO.getClienteById(cliente.getId()));
                }else{
                    System.out.println("Erro ao tentar salvar o cliente. Por favor, contate o adminstrador.");
                }
                opcao = 0;
            }
        }while(cliente == null || opcao != 0);
    }

    //case 3
    private void tornarInativo() {
        System.out.print("Digite o código do cliente: ");
        int codigo = s.nextInt();
        Cliente cliente = clienteDAO.getClienteById(codigo);
        if(cliente != null){
            System.out.println(cliente);
            System.out.println("Confirmar a operação? (0-sim/1-não)");
            if(s.nextInt() == 0){
                if(clienteDAO.softDelete(cliente)) {
                    System.out.println(cliente);
                }
            }else{
                System.out.println("Operação cancelada.");
            }
        }else{
            System.out.println("Código não localizado.");
        }
    }

    //case 5
    private static void listarPorSituacao(ClienteController telaCliente) {
        System.out.print("Deseja listar os ativos ou os inativos (ativos-0/inativos-1)? " );
        if(s.nextInt() == 0){
            System.out.println("\n****Lista de Clientes Ativos: " + telaCliente.clienteDAO.getClientesBySituacao(true));
        }else{
            System.out.println("\n****Lista de Clientes Inativos: " + telaCliente.clienteDAO.getClientesBySituacao(false));
        }
    }

    //case 6
    private void localizarPorNome() {
        System.out.print("Digite o nome do cliente: ");
        String nome = s.next();
        System.out.println("Chave de pesquisa: " + nome);
        List<Cliente> clientes = clienteDAO.getClientesByName(nome);
        if(clientes.isEmpty()){
            System.out.println("Não há registros correspondentes para: " + nome);
        }else{
            System.out.print(clientes);
        }
    }

    //case 7
    private void localizarPeloCodigo() {
        System.out.print("Digite o código do cliente: ");
        Cliente cliente = clienteDAO.getClienteById(s.nextInt());
        if(cliente != null){
            System.out.print(cliente);
        }else{
            System.out.println("Código não localizado.");
        }
    }

    //case 8
    private void ativarClientePeloCodigo() {
        System.out.print("Digite o código do cliente: ");
        int codigo = s.nextInt();
        Cliente cliente = clienteDAO.getClienteById(codigo);
        if(cliente != null){
            System.out.println(cliente);
            System.out.println("Confirmar a operação? (0-sim/1-não)");
            if(s.nextInt() == 0){
                if(clienteDAO.softDelete(cliente)) System.out.println(clienteDAO.getClienteById(codigo));
            }else{
                System.out.println("Operação cancelada.");
            }
        }else{
            System.out.println("Código não localizado.");
        }
    }
}
