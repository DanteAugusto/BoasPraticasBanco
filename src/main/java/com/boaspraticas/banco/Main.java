package com.boaspraticas.banco;

import java.util.List;
import java.util.Scanner;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.service.ClienteService;
import com.boaspraticas.banco.service.ContaService;


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
}