package com.boaspraticas.banco.model;

import com.boaspraticas.banco.util.conta.TipoConta;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(includeFieldNames = true)
public class ContaCorrente extends Conta {

  public ContaCorrente(int numeroUnico, double saldo, Cliente cliente) {
    super(numeroUnico, saldo, cliente);
  }

  public TipoConta getTipo() {
    return TipoConta.CORRENTE;
  }
}