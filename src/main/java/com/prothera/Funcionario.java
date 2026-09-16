package com.prothera;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario extends Pessoa {

    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void aplicarAumentoPercentual(BigDecimal percentual) {
        BigDecimal fator = BigDecimal.ONE.add(percentual.divide(new BigDecimal("100")));
        salario = salario.multiply(fator);
    }
}
