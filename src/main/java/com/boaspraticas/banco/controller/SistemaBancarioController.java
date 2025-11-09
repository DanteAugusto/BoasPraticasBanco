package com.boaspraticas.banco.controller;

import java.util.List;
import java.util.Scanner;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.model.Conta;
import com.boaspraticas.banco.service.ClienteService;
import com.boaspraticas.banco.service.ContaService;
import com.boaspraticas.banco.util.conta.RelatorioConsolidacao;
import com.boaspraticas.banco.util.conta.TipoConta;

public class SistemaBancarioController {
  private static ClienteService clienteService = new ClienteService();
  private static ContaService contaService = new ContaService(clienteService);
  private static Scanner scanner = new Scanner(System.in);

  public void iniciarSistema(){
    System.out.println("===========================================");
    System.out.println("     SISTEMA BANCÁRIO - BOAS PRÁTICAS      ");
    System.out.println("===========================================");
    
    boolean continuar = true;
    
    while (continuar) {
      exibirMenu();
      int opcaoEscolhida = lerOpcao();
      continuar = resolverOpcao(opcaoEscolhida);
      
      if (continuar) {
        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
      }
    }

    scanner.close();
  }

  public void exibirMenu() {
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

  public int lerOpcao() {
    try {
      int opcao = Integer.parseInt(scanner.nextLine());
      return opcao;
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  private boolean resolverOpcao(int opcaoEscolhida) {
    if (opcaoEscolhida == 0) {
      System.out.println("Obrigado por usar o Sistema Bancário!");
      return false;
    }

    switch (opcaoEscolhida) {
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
      case 5:
        depositarEmConta();
        break;
      case 6:
        realizarSaque();
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
      default:
        System.out.println("Opção inválida! Tente novamente.");
    }

    return true;
  }

  private void cadastrarCliente() {
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

  private void listarClientes() {
    System.out.println("\n--- LISTA DE CLIENTES CADASTRADOS ---");
    List<Cliente> clientes = clienteService.listarClientes();
    
    if (clientes.isEmpty()) {
      System.out.println("Nenhum cliente cadastrado no sistema.");
    } else {
      System.out.println("Total de clientes: " + clientes.size());
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

  private void cadastrarConta() {
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
      System.out.println("   Tipo de conta: " + conta.getDescricaoDoTipo());
      System.out.println("   CPF do cliente: " + conta.getCpfFormatadoDoCliente());
    } catch (IllegalArgumentException e) {
      System.out.println("Erro ao cadastrar conta: " + e.getMessage());
    }
  }

  private void listarContas() {
    System.out.println("\n--- LISTA DE CONTAS CADASTRADAS ---");
    List<Conta> contas = contaService.listarContas();
    
    if (contas.isEmpty()) {
      System.out.println("Nenhum conta cadastrado no sistema.");
    } else {
      System.out.println("Total de contas: " + contas.size());
      for (int idx = 0; idx < contas.size(); idx++) {
        Conta conta = contas.get(idx);
        System.out.printf("%d. Tipo: %-15s Nome do titular: %-15s CPF do titular: %s%n   Número único: %-15d Saldo: %.2f%n",
            (idx + 1), 
            conta.getDescricaoDoTipo(), 
            conta.getNomeDoCliente(),
            conta.getCpfFormatadoDoCliente(),
            conta.getNumeroUnico(),
            conta.getSaldo()
        );
      }
    }
  }

  private void depositarEmConta() {
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

  private void realizarSaque() {
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

  private void realizarTransferencia() {
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

  private void consultarSaldo() {
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

  private void aplicarRendimentoEmContasPoupanca() {
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

  private void gerarRelatorioConsolidacao() {
    System.out.println("\n--- RELATÓRIO DE CONSOLIDAÇÃO ---");
    try {
      RelatorioConsolidacao relatorio = contaService.gerarRelatorioConsolidacao();
      System.out.println("\n" + relatorio.toString());
    } catch (Exception e) {
      System.out.println("Erro ao gerar relatório: " + e.getMessage());
    }
  }

  private TipoConta lerTipoConta(){
    System.out.println("Digite o número do tipo de conta que deseja criar:");
    List<TipoConta> tiposDeConta = contaService.listarTiposDeConta();
    int tamanhoDeTiposDeConta = tiposDeConta.size();
    for (int idx = 0; idx < tamanhoDeTiposDeConta; idx++) {
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
