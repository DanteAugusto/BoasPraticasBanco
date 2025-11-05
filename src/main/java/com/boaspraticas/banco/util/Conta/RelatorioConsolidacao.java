package com.boaspraticas.banco.util.Conta;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RelatorioConsolidacao {
    private List<EstatisticasTipoConta> estatisticasPorTipo;
    private int totalContasGeral;
    private double saldoTotalGeral;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== RELATÓRIO DE CONSOLIDAÇÃO ==========\n\n");
        
        sb.append("CONSOLIDAÇÃO POR TIPO DE CONTA:\n");
        sb.append("─".repeat(50)).append("\n");
        
        for (EstatisticasTipoConta estatistica : estatisticasPorTipo) {
            sb.append(estatistica.toString()).append("\n");
        }
        
        sb.append("\n");
        sb.append("─".repeat(50)).append("\n");
        sb.append("CONSOLIDAÇÃO GERAL DO BANCO:\n");
        sb.append("─".repeat(50)).append("\n");
        sb.append(String.format("Total de Contas Cadastradas: %d\n", totalContasGeral));
        sb.append(String.format("Saldo Total do Banco: R$ %,.2f\n", saldoTotalGeral));
        sb.append("=".repeat(50));
        
        return sb.toString();
    }
}