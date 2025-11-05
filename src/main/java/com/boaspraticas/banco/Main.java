package com.boaspraticas.banco;

import java.util.Scanner;

import com.boaspraticas.banco.controller.SistemaBancarioController;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static SistemaBancarioController sistemaBancarioController = new SistemaBancarioController();

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
                    sistemaBancarioController.cadastrarCliente();
                    break;
                case 2:
                    sistemaBancarioController.listarClientes();
                    break;
                case 3:
                    sistemaBancarioController.cadastrarConta();
                    break;
                case 4:
                    sistemaBancarioController.listarContas();
                    break;
                case 5:
                    sistemaBancarioController.depositarEmConta();
                    break;
                case 6:
                    sistemaBancarioController.realizarSaque();
                    break;
                case 7:
                    realizarTransferencia();
                    break;
                case 8:
                    consultarSaldo();
                    break;
                case 9:
                    aplicarRendimentoEmContasPoupanca();
                    break;
                case 10:
                    gerarRelatorioConsolidacao();
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
        System.out.println("5. Realizar Depósito");
        System.out.println("6. Realizar Saque");
        System.out.println("7. Realizar Transferência");
        System.out.println("8. Consultar Saldo");
        System.out.println("9. Aplicar Rendimento em Contas Poupança");
        System.out.println("10. Relatório de Consolidação");
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

    private static void cadastrarConta() {
        System.out.println("\n--- CADASTRO DE CONTA ---");
        
        System.out.print("Digite o número único da conta: ");
        int numUnico = Integer.parseInt(scanner.nextLine());
        
        
        System.out.print("Digite o saldo inicial da conta: ");
        double saldoInicial = Double.parseDouble(scanner.nextLine());

        System.out.print("Digite o CPF do cliente dono da conta (apenas números ou com pontuação): ");
        String cpf = scanner.nextLine();
        
        try {
            TipoConta tipo = lerTipoConta();        
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

    private static void depositarEmConta() {
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

    private static void realizarSaque() {
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
    
    private static void realizarTransferencia() {
        System.out.println("\n--- TRANSFERÊNCIA ENTRE CONTAS ---");
        
        try {
            System.out.print("Digite o número único da conta pagante: ");
            int numUnicoPagante = Integer.parseInt(scanner.nextLine());

            System.out.print("Digite o número único da conta recebedora: ");
            int numUnicoRecebedora = Integer.parseInt(scanner.nextLine());

            System.out.print("Digite o valor a ser transferido: ");
            double valor = Double.parseDouble(scanner.nextLine());
            
            contaService.transferenciaEntreContas(numUnicoPagante, numUnicoRecebedora, valor);
            System.out.println("Transferência realizada com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: número ou valor inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao realizar transferência: " + e.getMessage());
        }
    }

    private static void consultarSaldo() {
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

    private static void aplicarRendimentoEmContasPoupanca() {
        System.out.println("\n--- APLICAR RENDIMENTO EM CONTAS POUPANÇA ---");
        
        try {
            System.out.print("Digite a taxa de rendimento (em %): ");
            double taxaRendimento = Double.parseDouble(scanner.nextLine());

            contaService.aplicarRendimentoContasPoupanca(taxaRendimento);
            System.out.println("Rendimento aplicado com sucesso em todas as contas poupança!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: taxa de rendimento inválida.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao aplicar rendimento: " + e.getMessage());
        }
    }

    private static void gerarRelatorioConsolidacao() {
        System.out.println("\n--- RELATÓRIO DE CONSOLIDAÇÃO ---");
        
        try {
            RelatorioConsolidacao relatorio = contaService.gerarRelatorioConsolidacao();
            System.out.println("\n" + relatorio.toString());
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
    
    private static TipoConta lerTipoConta(){
        System.out.println("Digite o número do tipo de conta que deseja criar:");
        List<TipoConta> tiposDeConta = contaService.listarTiposDeConta();
        int tamanhoDeTiposDeCOnta = tiposDeConta.size();
        for (int idx = 0; idx < tamanhoDeTiposDeCOnta; idx++) {
            TipoConta tipoConta = tiposDeConta.get(idx);
            System.out.printf("%d. %s%n", 
                                idx + 1, 
                                tipoConta.name());
        }

        int tipoIdx = Integer.parseInt(scanner.nextLine());
        if(tipoIdx < 1 || tipoIdx > tiposDeConta.size()){            
            throw new IllegalArgumentException("Tipo de conta inválido. Operação cancelada.");
        }
        return tiposDeConta.get(tipoIdx - 1);
    }
}