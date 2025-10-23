package com.boaspraticas.banco.model;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import com.boaspraticas.banco.util.CpfUtils;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames = true)
public class Cliente {
    private String nome;
    
    @EqualsAndHashCode.Include
    private String cpf;

    public Cliente(String nome, String cpf) {
        if (isNullOrEmpty(nome)) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (isNullOrEmpty(cpf)) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        if (!CpfUtils.isValidCpfFormat(cpf)) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos");
        }
        
        this.nome = nome.trim();
        this.cpf = CpfUtils.removeNonNumericCharacters(cpf);
    }

    private boolean isNullOrEmpty(String input) {
        return (input == null || input.trim().isEmpty());
    }

    public String getCpfFormatado() {
        return CpfUtils.formatCpf(cpf);
    }
}