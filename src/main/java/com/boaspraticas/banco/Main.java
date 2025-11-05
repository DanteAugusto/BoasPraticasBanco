package com.boaspraticas.banco;


import com.boaspraticas.banco.controller.SistemaBancarioController;


public class Main {
    private static SistemaBancarioController sistemaBancarioController = new SistemaBancarioController();

    public static void main(String[] args) {
        sistemaBancarioController.iniciarSistema();
    }    
}