package com.prothera;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat FORMATO_VALOR = criarFormatoValor();
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 – Inserir funcionários (ordem da tabela)
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 9), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 1), new BigDecimal("2284.38"), "Técnico"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1961, 2, 28), new BigDecimal("2234.68"), "Operador"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1995, 3, 5), new BigDecimal("1606.85"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 2, 14), new BigDecimal("2234.68"), "Auxiliar"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 2, 4), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(2001, 7, 15), new BigDecimal("1816.19"), "Estagiário"));

        // 3.2 – Remover João
        funcionarios.removeIf(f -> "João".equals(f.getNome()));

        // 3.3 – Imprimir todos (sem João, salários originais)
        System.out.println("=== 3.3 – Funcionários ===");
        imprimirFuncionarios(funcionarios);

        // 3.4 – Aumento de 10%
        funcionarios.forEach(f -> f.aplicarAumentoPercentual(new BigDecimal("10")));

        // 3.5 – Agrupar por função
        Map<String, List<Funcionario>> porFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 – Imprimir agrupados por função
        System.out.println("\n=== 3.6 – Agrupados por função ===");
        porFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            imprimirFuncionarios(lista);
        });

        // 3.8 – Aniversário nos meses 10 e 12
        System.out.println("\n=== 3.8 – Aniversário em outubro (10) e dezembro (12) ===");
        funcionarios.stream()
                .filter(f -> {
                    int mes = f.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .forEach(f -> System.out.println(formatarFuncionario(f)));

        // 3.9 – Maior idade
        System.out.println("\n=== 3.9 – Funcionário mais velho ===");
        funcionarios.stream()
                .max(Comparator.comparingInt(Funcionario::getIdade))
                .ifPresent(f -> System.out.println("Nome: " + f.getNome() + ", Idade: " + f.getIdade()));

        // 3.10 – Ordem alfabética
        System.out.println("\n=== 3.10 – Ordem alfabética ===");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(formatarFuncionario(f)));

        // 3.11 – Total dos salários
        System.out.println("\n=== 3.11 – Total dos salários ===");
        BigDecimal total = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(FORMATO_VALOR.format(total));

        // 3.12 – Quantidade de salários mínimos
        System.out.println("\n=== 3.12 – Salários mínimos por funcionário ===");
        funcionarios.forEach(f -> {
            BigDecimal quantidade = f.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + ": " + FORMATO_VALOR.format(quantidade));
        });
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        funcionarios.forEach(f -> System.out.println(formatarFuncionario(f)));
    }

    private static String formatarFuncionario(Funcionario f) {
        return String.format(
                "Nome: %s, Data nascimento: %s, Salário: %s, Função: %s",
                f.getNome(),
                f.getDataNascimento().format(FORMATO_DATA),
                FORMATO_VALOR.format(f.getSalario()),
                f.getFuncao());
    }

    private static DecimalFormat criarFormatoValor() {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setGroupingSeparator('.');
        simbolos.setDecimalSeparator(',');
        return new DecimalFormat("#,##0.00", simbolos);
    }
}
