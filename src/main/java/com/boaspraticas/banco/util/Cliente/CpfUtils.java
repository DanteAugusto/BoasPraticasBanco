package com.boaspraticas.banco.util.cliente;

public class CpfUtils {

  public static String removeNonNumericCharacters(String input) {
    if (input == null) {
      return "";
    }
    return input.replaceAll("[^0-9]", "");
  }

  public static boolean isValidCpfFormat(String cpf) {
    String cleanCpf = removeNonNumericCharacters(cpf);
    return cleanCpf.length() == 11;
  }

  public static String formatCpf(String cpf) {
    String cleanCpf = removeNonNumericCharacters(cpf);
    if (cleanCpf.length() != 11) {
      throw new IllegalArgumentException("CPF deve ter 11 dígitos");
    }
    return String.format("%s.%s.%s-%s",
        cleanCpf.substring(0, 3),
        cleanCpf.substring(3, 6),
        cleanCpf.substring(6, 9),
        cleanCpf.substring(9, 11)
    );
  }
}
