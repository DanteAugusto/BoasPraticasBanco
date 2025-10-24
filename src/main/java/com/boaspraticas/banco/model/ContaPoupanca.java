package com.boaspraticas.banco.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(includeFieldNames = true)
public class ContaPoupanca extends Conta{    

    public ContaPoupanca(int numeroUnico, double saldo, Cliente cliente) {        
        super(numeroUnico, saldo, cliente);
    }

}