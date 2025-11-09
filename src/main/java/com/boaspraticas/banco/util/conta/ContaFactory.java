package com.boaspraticas.banco.util.conta;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.model.Conta;
import com.boaspraticas.banco.model.ContaCorrente;
import com.boaspraticas.banco.model.ContaPoupanca;

public class ContaFactory {
  public static Conta criarConta(TipoConta tipo, int numero, double saldo, Cliente cliente) {
    if (tipo == TipoConta.CORRENTE) {
      return new ContaCorrente(numero, saldo, cliente);
    } else if (tipo == TipoConta.POUPANCA) {
      return new ContaPoupanca(numero, saldo, cliente);
    } else {
      throw new IllegalArgumentException("Tipo de conta inválido: " + tipo);
    }
  }
}
