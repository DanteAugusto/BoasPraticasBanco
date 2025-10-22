# Sistema Bancário Simples

Este é um projeto da disciplina de Boas Práticas de Programação que simula um sistema bancário básico para controle de contas e operações financeiras.

## Funcionalidades Implementadas

### 1. Cadastro de Cliente
- Criar um novo cliente com nome e CPF

### Funcionalidades Futuras
- Cadastro de contas
- Depósito
- Saque
- Transferência entre contas
- Consulta de saldo
- Aplicar rendimento de poupanças
- Listagem de contas
- Relatório de consolidação

## Estrutura do Projeto

```
src/
├── main/
│   └── java/
│       └── com/
│           └── boaspraticas/
│               └── banco/
│                   ├── Main.java
│                   ├── model/
│                   │   └── Cliente.java
│                   └── service/
│                       └── ClienteService.java
```

## Como Executar

### Pré-requisitos
- Java 8 ou superior

### Compilação e Execução

1. Compile o projeto:
```bash
javac -d out src/main/java/com/boaspraticas/banco/*.java src/main/java/com/boaspraticas/banco/model/*.java src/main/java/com/boaspraticas/banco/service/*.java
```

2. Execute o programa:
```bash
java -cp out com.boaspraticas.banco.Main
```