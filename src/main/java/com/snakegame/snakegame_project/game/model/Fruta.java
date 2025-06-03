package com.snakegame.snakegame_project.game.model;
import com.snakegame.snakegame_project.game.ui.TelaJogo;

import java.awt.*;
import java.util.Random;

public class Fruta {
    private Point fruta;
    private final Random aleatorio;


    public Fruta() {
        this.aleatorio = new Random();
        gerarNovaFruta();
    }


    public void gerarNovaFruta(){
        int x = aleatorio.nextInt(TelaJogo.NUM_BLOCOS);
        int y = aleatorio.nextInt(TelaJogo.NUM_BLOCOS);

        fruta = new Point(x,y);
    }

    public Point getFruta() {
        return fruta;
    }
    public void desenharFruta(Graphics gr){
        gr.setColor(Color.RED);
        gr.fillOval(fruta.x *TelaJogo.TAMANHO_BLOCO, fruta.y*TelaJogo.TAMANHO_BLOCO, TelaJogo.TAMANHO_BLOCO, TelaJogo.TAMANHO_BLOCO);
    }

    public Point getPosicao() {
        return fruta;
    }
}
