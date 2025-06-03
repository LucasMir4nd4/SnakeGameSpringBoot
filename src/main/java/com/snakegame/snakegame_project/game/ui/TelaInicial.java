package com.snakegame.snakegame_project.game.ui;

import com.snakegame.snakegame_project.game.model.Cobra;
import com.snakegame.snakegame_project.game.model.Direcao;
import com.snakegame.snakegame_project.game.model.Fruta;
import com.snakegame.snakegame_project.game.model.Jogador;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class TelaInicial extends JFrame {
    public static final int LARGURA_TELA = 600;
    public static final int ALTURA_TELA = 600;

    public TelaInicial() {

        setTitle("Snake Game");
        setSize(LARGURA_TELA, ALTURA_TELA);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela

        // Fundo
        Image fundo = new ImageIcon(getClass().getResource("/images/fundo.png")).getImage();
        fundo = fundo.getScaledInstance(LARGURA_TELA, ALTURA_TELA, Image.SCALE_DEFAULT);
        JLabel imagemFundo = new JLabel(new ImageIcon(fundo));
        imagemFundo.setLayout(null); // necessário para posicionar os botões
        setContentPane(imagemFundo);

        // Ícone da janela
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon.png"));
        setIconImage(icon.getImage());

        JLabel labelTitulo = new JLabel("Snake Game");
        labelTitulo.setFont(carregarFont());
        labelTitulo.setForeground(Color.WHITE);

        // Botões
        JButton botaoJogar = new JButton("Jogar");
        JButton botaoLogin = new JButton("Login");
        JButton botaoCadastro = new JButton("Cadastro");

        configurarBotao(botaoJogar);
        configurarBotao(botaoLogin);
        configurarBotao(botaoCadastro);

        // Define posição manual dos botões (layout null)
        int botaoLargura = 200;
        int botaoAltura = 50;
        int x = (LARGURA_TELA - botaoLargura) / 2;
        int y = 220;

        botaoJogar.setBounds(x, y, botaoLargura, botaoAltura);
        botaoLogin.setBounds(x, y + 80, botaoLargura, botaoAltura);
        botaoCadastro.setBounds(x-25, y + 160, botaoLargura+50, botaoAltura);
        labelTitulo.setBounds(x -25,y-200, LARGURA_TELA, botaoAltura);



        imagemFundo.add(botaoJogar);
        imagemFundo.add(botaoLogin);
        imagemFundo.add(botaoCadastro);
        imagemFundo.add(labelTitulo);


        // Ações dos botões
        botaoJogar.addActionListener(e -> {
            new TelaJogo(new Cobra(Direcao.DIREITA), new Fruta(), new Jogador());
            this.dispose(); // Fecha tela inicial
        });

        botaoLogin.addActionListener(e -> {
            new Login();
            Login.botaoCadastro.setVisible(false);
            Login.botaoEntrar.setVisible(true);
            this.dispose();
        });

        botaoCadastro.addActionListener(e -> {

            new Login();
            Login.botaoCadastro.setVisible(true);
            Login.botaoEntrar.setVisible(false);
            this.dispose();
        });

        setVisible(true);

    }

    private Font carregarFont() {
        try (InputStream fontStream = getClass().getResourceAsStream("/font/PressStart2P-Regular.ttf")) {
            if (fontStream != null) {
                return Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 24f);
            } else {
                System.out.println("Fonte não encontrada.");
            }
        } catch (FontFormatException | IOException e) {
            System.out.println("Erro ao carregar a fonte: " + e.getMessage());
        }
        return new Font("SansSerif", Font.PLAIN, 24);
    }

    private void configurarBotao(JButton botao) {
        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/ImagemBotao1.png"));
        botao.setIcon(imagemBotao);
        botao.setHorizontalTextPosition(SwingConstants.CENTER);// texto centralizado na horizontal
        botao.setVerticalTextPosition(SwingConstants.CENTER);// texto centralizado na vertical
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setForeground(Color.WHITE);
        botao.setFont(carregarFont());
    }
}
