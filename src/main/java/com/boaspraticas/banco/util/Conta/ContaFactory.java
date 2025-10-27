package com.boaspraticas.banco.util.Conta;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.model.Conta;

public class ContaFactory {
    public static Conta criarConta(TipoConta tipo, int numero, double saldo, Cliente cliente) {
        if(tipo == TipoConta.CORRENTE) {
            return new com.boaspraticas.banco.model.ContaCorrente(numero, saldo, cliente);
        } else if(tipo == TipoConta.POUPANCA) {
            return new com.boaspraticas.banco.model.ContaPoupanca(numero, saldo, cliente);
        } else {
            throw new IllegalArgumentException("Tipo de conta inválido: " + tipo);
        }
    }
}
