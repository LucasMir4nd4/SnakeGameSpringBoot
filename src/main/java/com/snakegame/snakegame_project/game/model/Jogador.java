package com.snakegame.snakegame_project.game.model;

public class Jogador {

    private String nome;
    private String senha;
    private int pontuacao;

    public Jogador(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public Jogador(String nome, String senha, int pontuacao) {
        this.nome = nome;
        this.senha = senha;
        this.pontuacao = pontuacao;
    }

    public Jogador() {
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
}
