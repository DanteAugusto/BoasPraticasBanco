package com.boaspraticas.banco.model;

import com.boaspraticas.banco.util.Conta.TipoConta;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames = true)
public abstract class Conta {
    
    @EqualsAndHashCode.Include
    private int numeroUnico;

    private double saldo;

    private Cliente cliente;

    public Conta(int numeroUnico, double saldo, Cliente cliente) {        
        this.numeroUnico = numeroUnico;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    public abstract TipoConta getTipo();
}