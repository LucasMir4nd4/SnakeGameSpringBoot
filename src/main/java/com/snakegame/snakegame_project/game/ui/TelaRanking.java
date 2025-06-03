package com.snakegame.snakegame_project.game.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class TelaRanking extends JFrame {

    public static final int LARGURA_TELA = 600;
    public static final int ALTURA_TELA = 600;

    public TelaRanking() {

        setSize(LARGURA_TELA, ALTURA_TELA);

        Image imagem = new ImageIcon(getClass().getResource("/images/fundo.png")).getImage();
        imagem = imagem.getScaledInstance(LARGURA_TELA, ALTURA_TELA, Image.SCALE_SMOOTH);

        JLabel fundo = new JLabel(new ImageIcon(imagem));
        fundo.setLayout(new BorderLayout());

        setContentPane(fundo);

        setTitle("Snake Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        setVisible(true);
    }

}
