package com.snakegame.snakegame_project.game.model;
public enum Direcao {
    CIMA,BAIXO,ESQUERDA,DIREITA;

    public Direcao oposta(){
        return switch (this) {
            case CIMA -> BAIXO;
            case BAIXO -> CIMA;
            case ESQUERDA -> DIREITA;
            case DIREITA -> ESQUERDA;
        };
    }
}
