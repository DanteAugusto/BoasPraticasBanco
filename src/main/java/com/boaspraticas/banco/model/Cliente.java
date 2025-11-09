package com.boaspraticas.banco.model;

import com.boaspraticas.banco.util.cliente.CpfUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames = true)
public class Cliente {
  private String nome;

  @EqualsAndHashCode.Include
  private String cpf;

  public Cliente(String nome, String cpf) {
    if (ehNuloOuVazio(nome)) {
      throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
    }
    if (ehNuloOuVazio(cpf)) {
      throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
    }
    if (!CpfUtils.ehValidoCpf(cpf)) {
      throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos");
    }

    this.nome = nome.trim();
    this.cpf = CpfUtils.removerCaracteresNaoNumericos(cpf);
  }

  private boolean ehNuloOuVazio(String input) {
    return (input == null || input.trim().isEmpty());
  }

  public String getCpfFormatado() {
    return CpfUtils.formatarCpf(cpf);
  }
}