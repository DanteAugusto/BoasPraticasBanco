package com.boaspraticas.banco.model;

import java.util.Objects;


public class Cliente {
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        if (!isValidCpf(cpf)) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos");
        }
        
        this.nome = nome.trim();
        this.cpf = cpf.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos
    }

    private boolean isValidCpf(String cpf) {
        String cleanCpf = cpf.replaceAll("[^0-9]", "");
        return cleanCpf.length() == 11;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCpfFormatado() {
        return String.format("%s.%s.%s-%s", 
            cpf.substring(0, 3),
            cpf.substring(3, 6),
            cpf.substring(6, 9),
            cpf.substring(9, 11)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return String.format("Cliente{nome='%s', cpf='%s'}", nome, getCpfFormatado());
    }
}