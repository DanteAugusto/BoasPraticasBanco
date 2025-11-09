package com.boaspraticas.banco.util.Conta;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EstatisticasTipoConta {
  private TipoConta tipoConta;
  private int quantidadeContas;
  private double saldoTotal;

  @Override
  public String toString() {
    return String.format("Tipo: %-10s | Quantidade: %3d | Saldo Total: R$ %,.2f",
        tipoConta.getDescricao(), quantidadeContas, saldoTotal);
  }
}