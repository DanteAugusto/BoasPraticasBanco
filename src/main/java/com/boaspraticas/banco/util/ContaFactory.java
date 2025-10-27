package com.boaspraticas.banco.util;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.model.Conta;

public class ContaFactory {
    public static Conta criarConta(String tipo, int numero, double saldo, Cliente cliente) {
        String tipoMinusculo = tipo.toLowerCase();
        if(tipoMinusculo.equals("corrente")) {
            return new com.boaspraticas.banco.model.ContaCorrente(numero, saldo, cliente);
        } else if(tipoMinusculo.equals("poupanca") || tipoMinusculo.equals("poupança")) {
            return new com.boaspraticas.banco.model.ContaPoupanca(numero, saldo, cliente);
        } else {
            throw new IllegalArgumentException("Tipo de conta inválido: " + tipo);
        }
    }
}
