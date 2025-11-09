package com.boaspraticas.banco.service;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.model.Conta;
import com.boaspraticas.banco.model.ContaPoupanca;
import com.boaspraticas.banco.util.cliente.CpfUtils;
import com.boaspraticas.banco.util.conta.ContaFactory;
import com.boaspraticas.banco.util.conta.EstatisticasTipoConta;
import com.boaspraticas.banco.util.conta.RelatorioConsolidacao;
import com.boaspraticas.banco.util.conta.TipoConta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContaService {
  private List<Conta> contas;
  private ClienteService clienteService;

  public ContaService(ClienteService clienteService) {
    this.contas = new ArrayList<>();
    this.clienteService = clienteService;
  }

  public Conta cadastrarConta(int numeroUnico, double saldo, String clienteCpf, TipoConta tipo) {
    if (contaExiste(numeroUnico)) {
      throw new IllegalArgumentException("Conta com número único " + numeroUnico + " já está cadastrada");
    }

    if (!CpfUtils.isValidCpfFormat(clienteCpf)) {
      throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos");
    }

    Cliente cliente = clienteService.buscarClientePorCpf(clienteCpf);
    if (cliente == null) {
      throw new IllegalArgumentException("Cliente com CPF " + clienteCpf + " não encontrado");
    }

    Conta novaConta = ContaFactory.criarConta(tipo, numeroUnico, saldo, cliente);
    contas.add(novaConta);

    return novaConta;
  }

  public List<Conta> listarContas() {
    List<Conta> contasOrdendas = ordenaContasPorSaldoDescendente();
    return contasOrdendas;
  }

  public boolean contaExiste(int numeroUnico) {
    return contas.stream().anyMatch(conta -> conta.getNumeroUnico() == numeroUnico);
  }

  public List<TipoConta> listarTiposDeConta() {
    return Arrays.asList(TipoConta.values());
  }

  public Conta buscarContaPorNumero(int numeroUnico) {
    for (Conta conta : contas) {
      if (conta.getNumeroUnico() == numeroUnico) {
        return conta;
      }
    }
    return null;
  }

  public void depositar(int numeroUnico, double valor) {
    Conta conta = buscarContaPorNumero(numeroUnico);
    if (conta == null) {
      throw new IllegalArgumentException("Conta com número " + numeroUnico + " não encontrada.");
    }
    if (valor <= 0) {
      throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
    }

    conta.setSaldo(conta.getSaldo() + valor);
  }

  public void sacarValorDeConta(int numeroUnico, double valor) {
    if (valor <= 0) {
      throw new IllegalArgumentException("O valor do saque deve ser positivo.");
    }

    Conta conta = buscarContaPorNumero(numeroUnico);
    if (conta == null) {
      throw new IllegalArgumentException("Conta com número " + numeroUnico + " não encontrada.");
    }

    if (conta.getSaldo() < valor) {
      throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
    }

    conta.setSaldo(conta.getSaldo() - valor);
  }

  public void transferenciaEntreContas(
      int numeroUnicoPagante, int numeroUnicoRecebedor, double valor) {
    if (valor <= 0) {
      throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
    }

    Conta contaPagante = buscarContaPorNumero(numeroUnicoPagante);
    if (contaPagante == null) {
      throw new IllegalArgumentException(
          "Conta pagante com número " + numeroUnicoPagante + " não encontrada.");
    }

    if (contaPagante.getSaldo() < valor) {
      throw new IllegalArgumentException(
          "Saldo insuficiente na conta pagante para realizar transferência.");
    }

    Conta contaRecebedora = buscarContaPorNumero(numeroUnicoRecebedor);
    if (contaRecebedora == null) {
      throw new IllegalArgumentException(
          "Conta recebedora com número " + numeroUnicoRecebedor + " não encontrada.");
    }

    sacarValorDeConta(numeroUnicoPagante, valor);
    depositar(numeroUnicoRecebedor, valor);
  }

  public double consultarSaldo(int numeroUnico) {
    Conta conta = buscarContaPorNumero(numeroUnico);
    if (conta == null) {
      throw new IllegalArgumentException("Conta com número " + numeroUnico + " não encontrada.");
    }
    return conta.getSaldo();
  }

  public void aplicarRendimentoContasPoupanca(double taxaRendimento) {
    if (taxaRendimento <= 0) {
      throw new IllegalArgumentException("A taxa de rendimento deve ser maior que zero");
    }

    List<ContaPoupanca> contasPoupanca = filtraContasPoupanca();
    if (contasPoupanca.isEmpty()) {
      throw new IllegalArgumentException(
          "Não há contas poupança cadastradas para aplicar o rendimento");
    }

    for (ContaPoupanca contaPoupanca : contasPoupanca) {
      aplicarRendimento(contaPoupanca, taxaRendimento);
    }
  }

  public RelatorioConsolidacao gerarRelatorioConsolidacao() {
    List<EstatisticasTipoConta> estatisticasPorTipo = new ArrayList<>();

    for (TipoConta tipo : TipoConta.values()) {
      long quantidade = contarContasPorTipo(tipo);
      double saldoTotal = calcularSaldoTotalPorTipo(tipo);

      EstatisticasTipoConta estatistica = new EstatisticasTipoConta(tipo, (int) quantidade, saldoTotal);
      estatisticasPorTipo.add(estatistica);
    }

    int totalContasGeral = contas.size();
    double saldoTotalGeral = calcularSaldoTotalBanco();

    return new RelatorioConsolidacao(estatisticasPorTipo, totalContasGeral, saldoTotalGeral);
  }

  private long contarContasPorTipo(TipoConta tipo) {
    return contas.stream().filter(conta -> conta.getTipo() == tipo).count();
  }

  private double calcularSaldoTotalPorTipo(TipoConta tipo) {
    return contas.stream()
        .filter(conta -> conta.getTipo() == tipo)
        .mapToDouble(Conta::getSaldo)
        .sum();
  }

  private double calcularSaldoTotalBanco() {
    return contas.stream().mapToDouble(Conta::getSaldo).sum();
  }

  private List<ContaPoupanca> filtraContasPoupanca() {
    List<ContaPoupanca> contasPoupanca = new ArrayList<>();
    for (Conta conta : contas) {
      if (conta.getTipo() == TipoConta.POUPANCA) {
        contasPoupanca.add((ContaPoupanca) conta);
      }
    }
    return contasPoupanca;
  }

  private void aplicarRendimento(ContaPoupanca conta, double taxaRendimento) {
    double saldo = conta.getSaldo();
    double rendimento = calcularRendimento(taxaRendimento, saldo);

    depositar(conta.getNumeroUnico(), rendimento);
  }

  private double calcularRendimento(double taxaRendimento, double saldo) {
    return saldo * (taxaRendimento / 100.0);
  }

  private List<Conta> ordenaContasPorSaldoDescendente() {
    List<Conta> contasOrdenadas = new ArrayList<>(contas);
    contasOrdenadas.sort((c1, c2) -> Double.compare(c2.getSaldo(), c1.getSaldo()));
    return contasOrdenadas;
  }
}
