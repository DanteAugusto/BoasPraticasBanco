package com.boaspraticas.banco;

import java.util.List;
import java.util.Scanner;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.model.Conta;
import com.boaspraticas.banco.service.ClienteService;
import com.boaspraticas.banco.service.ContaService;
import com.boaspraticas.banco.util.Conta.TipoConta;


public class Main {
    private static ClienteService clienteService = new ClienteService();
    private static ContaService contaService = new ContaService(clienteService);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("     SISTEMA BANCÁRIO - BOAS PRÁTICAS      ");
        System.out.println("===========================================");
        
        boolean continuar = true;
        
        while (continuar) {
            exibirMenu();
            int opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    cadastrarConta();
                    break;
                case 4:
                    listarContas();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Obrigado por usar o Sistema Bancário!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            
            if (continuar) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===========================================");
        System.out.println("              MENU PRINCIPAL");
        System.out.println("===========================================");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Listar Clientes");
        System.out.println("3. Cadastrar Conta");
        System.out.println("4. Listar Contas");
        System.out.println("0. Sair");
        System.out.println("===========================================");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n--- CADASTRO DE CLIENTE ---");
        
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        
        System.out.print("Digite o CPF do cliente (apenas números ou com pontuação): ");
        String cpf = scanner.nextLine();
        
        try {
            Cliente cliente = clienteService.cadastrarCliente(nome, cpf);
            System.out.println("Cliente cadastrado com sucesso!");
            System.out.println("   Nome: " + cliente.getNome());
            System.out.println("   CPF: " + cliente.getCpfFormatado());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private static void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES CADASTRADOS ---");
        
        List<Cliente> clientes = clienteService.listarClientes();
        
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no sistema.");
        } else {
            System.out.println("Total de clientes: " + clientes.size());
            System.out.println();
            
            for (int i = 0; i < clientes.size(); i++) {
                Cliente cliente = clientes.get(i);
                System.out.printf("%d. Nome: %-30s CPF: %s%n", 
                    (i + 1), 
                    cliente.getNome(), 
                    cliente.getCpfFormatado()
                );
            }
        }
    }

    private static void cadastrarConta() {
        System.out.println("\n--- CADASTRO DE CONTA ---");
        
        System.out.print("Digite o número único da conta: ");
        int numUnico = scanner.nextInt();
        
        
        System.out.print("Digite o saldo inicial da conta: ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine(); 

        System.out.print("Digite o CPF do cliente dono da conta (apenas números ou com pontuação): ");
        String cpf = scanner.nextLine();
        
        TipoConta tipo = lerTipoConta();
        if(tipo == null){
            System.out.println("Tipo de conta inválido. Operação cancelada.");
            return;
        }
        
        try {
            Conta conta = contaService.cadastrarConta(numUnico, saldoInicial, cpf, tipo);
            System.out.println("Conta cadastrada com sucesso!");
            System.out.println("   Número Único: " + conta.getNumeroUnico());
            System.out.println("   Saldo Inicial: " + conta.getSaldo());
            System.out.println("   Tipo de conta: " + conta.getTipo().getDescricao());
            System.out.println("   CPF do cliente: " + conta.getCliente().getCpfFormatado());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar conta: " + e.getMessage());
        }
    }

    private static void listarContas() {
        System.out.println("\n--- LISTA DE CONTAS CADASTRADAS ---");
        
        List<Conta> contas = contaService.listarContas();
        
        if (contas.isEmpty()) {
            System.out.println("Nenhum conta cadastrado no sistema.");
        } else {
            System.out.println("Total de contas: " + contas.size());
            System.out.println();
            
            for (int i = 0; i < contas.size(); i++) {
                Conta conta = contas.get(i);
                System.out.printf("%d. Tipo: %-15s Nome do titular: %-15s CPF do titular: %s%n   Número único: %-15d Saldo: %.2f%n",
                    (i + 1), 
                    conta.getTipo().getDescricao(), 
                    conta.getCliente().getNome(),
                    conta.getCliente().getCpfFormatado(),
                    conta.getNumeroUnico(),
                    conta.getSaldo()
                );
            }
        }
    }
    
    private static TipoConta lerTipoConta(){
        System.out.println("Digite o número do tipo de conta que deseja criar:");
        int idx = 1;
        List<TipoConta> tiposDeConta = contaService.listarTiposDeConta();
        for (TipoConta tipoConta : tiposDeConta) {
            System.out.printf("%d. %s%n", idx, tipoConta.name());
            idx++;
        }
        int tipoIdx = scanner.nextInt();
        scanner.nextLine();
        if(tipoIdx < 1 || tipoIdx > tiposDeConta.size()){            
            return null;
        }
        return tiposDeConta.get(tipoIdx - 1);
    }
}