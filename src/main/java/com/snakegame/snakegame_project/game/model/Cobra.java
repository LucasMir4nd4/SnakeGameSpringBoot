package com.snakegame.snakegame_project.game.model;

import com.snakegame.snakegame_project.game.ui.TelaJogo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cobra extends JPanel {

    private final ArrayList<Point> corpo;
    private Direcao direcao = Direcao.DIREITA;


    public Cobra(Direcao direcao){
        this.direcao = Direcao.DIREITA;
        corpo = new ArrayList<>();
        corpo.add(new Point(5, 9));  // cabeça
        corpo.add(new Point(4, 9));  // corpo
        corpo.add(new Point(3, 9)); // cauda

        setFocusable(true); // Permite que o painel receba foco


    }

    public void resetarCobra(){
        corpo.clear();
        corpo.add(new Point(5, 9));  // cabeça
        corpo.add(new Point(4, 9));  // corpo
        corpo.add(new Point(3, 9));  // cauda

        direcao = Direcao.DIREITA;
    }

    public void mover(){
        Point cabeca = corpo.getFirst();
        Point nova = new Point(cabeca);

        switch (direcao){
            case CIMA: nova.y -= 1;break;
            case BAIXO: nova.y += 1;break;
            case DIREITA: nova.x += 1;break;
            case ESQUERDA: nova.x -= 1;break;
        }


        corpo.addFirst(nova);   // adiciona nova cabeça
        corpo.removeLast(); // remove a cauda
    }

    public ArrayList<Point> getCorpo() {
        return corpo;
    }
    public Point getCabeca(){
        return corpo.getFirst();
    }

    public void crescer(){
        Point cauda = corpo.get(corpo.size() - 1);
        corpo.add(new Point(cauda));
    }
    public void setDirecao(Direcao direcao) {
        this.direcao = direcao;
    }

    public Direcao getDirecao() {
        return direcao != null? direcao : Direcao.DIREITA;
    }

    public boolean verificarColisao(){

        Point cabeca = getCabeca();
        ArrayList<Point> corpo = getCorpo();

        // Verifica colisões
        for (int i = 1; i < corpo.size(); i++) {
            if (cabeca.x < 0 || cabeca.y < 0 ||
                    cabeca.x >= TelaJogo.NUM_BLOCOS || cabeca.y >= TelaJogo.NUM_BLOCOS) {
                return true;
            }
            if (cabeca.equals(corpo.get(i))) {
                return true;
            }
        }
        return false;
    }
    public boolean verificarColisaoFruta(Fruta fruta) {
        Point cabeca = getCabeca();

        // Verifica se pegou a fruta
        if (cabeca.equals(fruta.getPosicao())) {
            crescer();
            fruta.gerarNovaFruta();
            return true; // Cobra comeu a fruta
        }
        return false;

    }
}
