package com.boaspraticas.banco.service;

import java.util.ArrayList;
import java.util.List;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.model.Conta;
import com.boaspraticas.banco.util.ContaFactory;
import com.boaspraticas.banco.util.CpfUtils;

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
        
        if(!CpfUtils.isValidCpfFormat(clienteCpf)){
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos");
        }

        Cliente cliente = clienteService.buscarClientePorCpf(clienteCpf);
        if(cliente == null) {
            throw new IllegalArgumentException("Cliente com CPF " + clienteCpf + " não encontrado");
        }
        
        Conta novaConta = ContaFactory.criarConta(tipo, numeroUnico, saldo, cliente);
        contas.add(novaConta);

        return novaConta;
    }

    public List<Conta> listarContas() {
        ordenaContasPorSaldoDesc(); 
        return new ArrayList<>(contas);
    }

    public boolean contaExiste(int numeroUnico) {
        return contas.stream().anyMatch(conta -> conta.getNumeroUnico() == numeroUnico);
    }

    public String listarTiposDeConta() {
        return "corrente, poupança";
    }

    private void ordenaContasPorSaldoDesc() {
        contas.sort((c1, c2) -> Double.compare(c2.getSaldo(), c1.getSaldo()));
    }
}
