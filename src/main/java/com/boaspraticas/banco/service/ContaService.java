package com.boaspraticas.banco.service;

import java.util.ArrayList;
import java.util.List;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.model.Conta;
import com.boaspraticas.banco.model.ContaCorrente;

public class ContaService {
    private List<Conta> contas;
    private ClienteService clienteService;

    public ContaService(ClienteService clienteService) {
        this.contas = new ArrayList<>();
        this.clienteService = clienteService;
    }

    public Conta cadastrarConta(int numeroUnico, double saldo, String clienteCpf, String tipo) {
        if (contaExiste(numeroUnico)) {
            throw new IllegalArgumentException("Conta com número único " + numeroUnico + " já está cadastrada");
        }

        Cliente cliente = clienteService.buscarClientePorCpf(clienteCpf);
        Conta novaConta = new ContaCorrente(numeroUnico, saldo, cliente);
        return novaConta;
    }

    public boolean contaExiste(int numeroUnico) {
        return contas.stream().anyMatch(conta -> conta.getNumeroUnico() == numeroUnico);
    }
}
