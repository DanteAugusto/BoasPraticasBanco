package com.boaspraticas.banco.service;

import java.util.ArrayList;
import java.util.List;

import com.boaspraticas.banco.model.Cliente;
import com.boaspraticas.banco.util.Cliente.CpfUtils;


public class ClienteService {
    private List<Cliente> clientes;

    public ClienteService() {
        this.clientes = new ArrayList<>();
    }

    public Cliente cadastrarCliente(String nome, String cpf) {
        if (clienteExiste(cpf)) {
            throw new IllegalArgumentException("Cliente com CPF " + cpf + " já está cadastrado");
        }

        Cliente novoCliente = new Cliente(nome, cpf);
        clientes.add(novoCliente);
        return novoCliente;
    }

    public boolean clienteExiste(String cpf) {
        if (cpf == null) {
            return false;
        }
        String cpfLimpo = CpfUtils.removeNonNumericCharacters(cpf);
        return clientes.stream().anyMatch(cliente -> cliente.getCpf().equals(cpfLimpo));
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        String cpfLimpo = CpfUtils.removeNonNumericCharacters(cpf);
        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpfLimpo))
                .findFirst()
                .orElse(null);
    }

}