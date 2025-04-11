package com.xpronto.webgestao.domain.model.valueobject;

import java.util.InputMismatchException;

public class Document {
    private final String number;
    private final DocumentType type;

    private Document(String number, DocumentType type) {
        if (type.equals(DocumentType.CPF) && !isValidCPF(number)) {
            throw new IllegalArgumentException("Invalid CPF");
        }

        if (type.equals(DocumentType.CNPJ) && !isValidCNPJ(number)) {
            throw new IllegalArgumentException("Invalid CNPJ");
        }

        this.number = number;
        this.type = type;
    }

    public static Document create(String number, DocumentType type) {
        return new Document(number, type);
    }

    private static boolean isValidCPF(String cpf) {
        if (cpf.chars().distinct().count() == 1)
            return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere "0" no inteiro 0
                // (48 eh a posicao de "0" na tabela ASCII)
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                return true;
            else
                return false;
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    private static boolean isValidCNPJ(String cnpj) {
        if (cnpj.chars().distinct().count() == 1)
            return false;

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                // converte o i-ésimo caractere do CNPJ em um número:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posição de '0' na tabela ASCII)
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else
                dig13 = (char) ((11 - r) + 48);

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else
                dig14 = (char) ((11 - r) + 48);

            // Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
                return (true);
            else
                return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static DocumentType verifyType(String document) {
        String digits = document.replaceAll("[^\\d]", "");

        switch (digits.length()) {
            case 11:
                return DocumentType.CPF;

            case 14:
                return DocumentType.CNPJ;

            default:
                return null;
        }
    }

    public static enum DocumentType {
        CPF, CNPJ
    }

    public String getNumber() {
        return number;
    }

    public DocumentType getType() {
        return type;
    }
}
