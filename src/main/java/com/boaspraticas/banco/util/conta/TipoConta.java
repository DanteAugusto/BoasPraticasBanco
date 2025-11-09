package com.boaspraticas.banco.util.conta;

public enum TipoConta {
  CORRENTE("Corrente"),
  POUPANCA("Poupança");

  private final String descricao;

  TipoConta(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}