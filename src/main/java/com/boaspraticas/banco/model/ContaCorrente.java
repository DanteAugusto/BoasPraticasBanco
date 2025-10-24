package com.boaspraticas.banco.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(includeFieldNames = true)
public class ContaCorrente extends Conta{    

    public ContaCorrente(int numeroUnico, double saldo, Cliente cliente) {        
        super(numeroUnico, saldo, cliente);
    }

}