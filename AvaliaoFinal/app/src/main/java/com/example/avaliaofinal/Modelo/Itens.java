package com.example.avaliaofinal.Modelo;

public class Itens {
    private String uid;
    private String nome;
    private String cor;
    private String quantidade;

    public Itens(){

    }

    public Itens(String uid, String nome, String cor, String quantidade) {
        this.uid = uid;
        this.nome = nome;
        this.cor = cor;
        this.quantidade = quantidade;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return nome +
                ", " + cor +
                ", " + quantidade;
    }
}
