package com.boaspraticas.banco;

import java.util.Scanner;

import com.boaspraticas.banco.controller.SistemaBancarioController;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static SistemaBancarioController sistemaBancarioController = new SistemaBancarioController();

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("     SISTEMA BANCÁRIO - BOAS PRÁTICAS      ");
        System.out.println("===========================================");
        
        boolean continuar = true;
        
        while (continuar) {
            exibirMenu();
            int opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    sistemaBancarioController.cadastrarCliente();
                    break;
                case 2:
                    sistemaBancarioController.listarClientes();
                    break;
                case 3:
                    sistemaBancarioController.cadastrarConta();
                    break;
                case 4:
                    sistemaBancarioController.listarContas();
                    break;
                case 5:
                    sistemaBancarioController.depositarEmConta();
                    break;
                case 6:
                    sistemaBancarioController.realizarSaque();
                    break;
                case 7:
                    sistemaBancarioController.consultarSaldo();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Obrigado por usar o Sistema Bancário!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            
            if (continuar) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===========================================");
        System.out.println("              MENU PRINCIPAL");
        System.out.println("===========================================");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Listar Clientes");
        System.out.println("3. Cadastrar Conta");
        System.out.println("4. Listar Contas");
        System.out.println("5. Realizar Depósito");
        System.out.println("6. Realizar Saque");
        System.out.println("7. Consultar Saldo");
        System.out.println("0. Sair");
        System.out.println("===========================================");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    
}