package com.boaspraticas.banco.controller;

import java.util.List;
import java.util.Scanner;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.model.Conta;
import com.boaspraticas.banco.service.ClienteService;
import com.boaspraticas.banco.service.ContaService;
import com.boaspraticas.banco.util.Conta.TipoConta;

public class SistemaBancarioController {
    private static ClienteService clienteService = new ClienteService();
    private static ContaService contaService = new ContaService(clienteService);
    private static Scanner scanner = new Scanner(System.in);

    public void cadastrarCliente() {
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

    public void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES CADASTRADOS ---");
        
        List<Cliente> clientes = clienteService.listarClientes();
        
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no sistema.");
        } else {
            System.out.println("Total de clientes: " + clientes.size());
            System.out.println();
            
            for (int idx = 0; idx < clientes.size(); idx++) {
                Cliente cliente = clientes.get(idx);
                System.out.printf("%d. Nome: %-30s CPF: %s%n", 
                    (idx + 1), 
                    cliente.getNome(), 
                    cliente.getCpfFormatado()
                );
            }
        }
    }

    public void cadastrarConta() {
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

    public void listarContas() {
        System.out.println("\n--- LISTA DE CONTAS CADASTRADAS ---");
        
        List<Conta> contas = contaService.listarContas();
        
        if (contas.isEmpty()) {
            System.out.println("Nenhum conta cadastrado no sistema.");
        } else {
            System.out.println("Total de contas: " + contas.size());
            System.out.println();
            
            for (int idx = 0; idx < contas.size(); idx++) {
                Conta conta = contas.get(idx);
                System.out.printf("%d. Tipo: %-15s Nome do titular: %-15s CPF do titular: %s%n   Número único: %-15d Saldo: %.2f%n",
                    (idx + 1), 
                    conta.getTipo().getDescricao(), 
                    conta.getCliente().getNome(),
                    conta.getCliente().getCpfFormatado(),
                    conta.getNumeroUnico(),
                    conta.getSaldo()
                );
            }
        }
    }

    public void depositarEmConta() {
        System.out.println("\n--- DEPÓSITO EM CONTA ---");
        
        try {
            System.out.print("Digite o número único da conta para depósito: ");
            int numUnico = Integer.parseInt(scanner.nextLine());

            System.out.print("Digite o valor a ser depositado: ");
            double valor = Double.parseDouble(scanner.nextLine());
            
            contaService.depositar(numUnico, valor);
            System.out.println("Depósito realizado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: número ou valor inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao realizar depósito: " + e.getMessage());
        }
    }

    public void realizarSaque() {
        System.out.println("\n--- SAQUE EM CONTA ---");
        
        try {
            System.out.print("Digite o número único da conta para saque: ");
            int numUnico = Integer.parseInt(scanner.nextLine());

            System.out.print("Digite o valor a ser sacado: ");
            double valor = Double.parseDouble(scanner.nextLine());
            
            contaService.sacarValorDeConta(numUnico, valor);
            System.out.println("Saque realizado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: número ou valor inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao realizar saque: " + e.getMessage());
        }
    }

    public void consultarSaldo() {
        System.out.println("\n--- CONSULTA DE SALDO ---");
        
        try {
            System.out.print("Digite o número único da conta: ");
            int numeroUnico = Integer.parseInt(scanner.nextLine());

            double saldoContaConsultada = contaService.consultarSaldo(numeroUnico);
        
            System.out.println("Saldo da conta encontrada:");
            System.out.printf("   Número Único: %d%n", numeroUnico);
            System.out.printf("   Saldo: R$ %.2f%n", saldoContaConsultada);

        } catch (NumberFormatException e) {
            System.out.println("Erro: número da conta inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao consultar saldo: " + e.getMessage());
        }
    }
    
    public TipoConta lerTipoConta(){
        System.out.println("Digite o número do tipo de conta que deseja criar:");
        List<TipoConta> tiposDeConta = contaService.listarTiposDeConta();
        int tamanhoDeTiposDeCOnta = tiposDeConta.size();
        for (int idx = 0; idx < tamanhoDeTiposDeCOnta; idx++) {
            TipoConta tipoConta = tiposDeConta.get(idx);
            System.out.printf("%d. %s%n", 
                                idx + 1, 
                                tipoConta.name());
        }

        int tipoIdx = scanner.nextInt();
        scanner.nextLine();
        if(tipoIdx < 1 || tipoIdx > tiposDeConta.size()){            
            return null;
        }
        return tiposDeConta.get(tipoIdx - 1);
    }
}
