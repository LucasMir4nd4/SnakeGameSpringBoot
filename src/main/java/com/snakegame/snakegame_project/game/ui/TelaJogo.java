package com.snakegame.snakegame_project.game.ui;

import com.snakegame.snakegame_project.db.BancoDeDados;
import com.snakegame.snakegame_project.game.model.Cobra;
import com.snakegame.snakegame_project.game.model.Direcao;
import com.snakegame.snakegame_project.game.model.Fruta;
import com.snakegame.snakegame_project.game.model.Jogador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TelaJogo extends JFrame {

    public static final int LARGURA_TELA = 600;
    public static final int ALTURA_TELA = 600;
    public static final int NUM_BLOCOS = 20;
    public static final int TAMANHO_BLOCO = LARGURA_TELA / NUM_BLOCOS;

    public TelaJogo(Cobra cobra, Fruta fruta, Jogador jogador) {

        // CONFIGURAÇÕES DA TELA
        setTitle("Snake Game");
        PainelJogo painel = new PainelJogo(cobra, fruta, jogador);
        painel.iniciarJogo();
        setContentPane(painel);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Ícone da tela
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon.png"));
        setIconImage(icon.getImage());

        setVisible(true);

    }

    // Cria um JPanel
    private static class PainelJogo extends JPanel implements KeyListener {
        private final Cobra cobra;
        private final Fruta fruta;
        private final Image fundo;
        private final JButton botaoJogar;
        private final JButton botaoVoltar;
        private final JLabel pontos;
        private final JLabel label;
        private int pontuacao = 0;
        private boolean rodando = false;
        private boolean podeMudarDirecao = true;
        private final Timer timer;
        private final Jogador jogador;


        // CONSTRUTOR DA CLASSE
        public PainelJogo(Cobra cobra, Fruta fruta, Jogador jogador) {

            this.cobra = cobra;
            this.fruta = fruta;
            this.jogador = jogador;

            label = new JLabel();
            pontos = new JLabel("Pontos: " + pontuacao);

            setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA)); // Define o tamanho da tela

            // Define os botões
            ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/ImagemBotao1.png"));
            botaoJogar = new JButton("Jogar", imagemBotao);
            botaoVoltar = new JButton("Voltar", imagemBotao);

            // Define o que acontece durante o tempo de atualização da tela
            timer = new Timer(150, e -> {
                if (rodando) {
                    cobra.mover();
                    podeMudarDirecao = true;

                    if (cobra.verificarColisao()){
                        gameOver();
                        return; // Sai do loop se colidiu
                    }
                    // Verifica se pegou a fruta
                    if (cobra.verificarColisaoFruta(fruta)) {
                        pontuacao++;
                        pontos.setText("Pontos: " + pontuacao);
                    }
                    repaint();
                }
            });

            fundo = new ImageIcon(getClass().getResource("/images/fundo.png")).getImage();

            setLayout(null);

            pontos.setFont(carregarFont().deriveFont(Font.PLAIN, 16f));
            pontos.setForeground(Color.WHITE);
            pontos.setBounds(10, 10, 200, 30);
            add(pontos);

            label.setText("SNAKE GAME");
            label.setFont(carregarFont().deriveFont(Font.PLAIN, 20f));
            label.setForeground(Color.WHITE);
            label.setSize(200, 70);
            add(label);

            add(botaoVoltar);
            add(botaoJogar);

            // Configura botões
            configurarBotao(botaoJogar);
            botaoJogar.addActionListener(e -> iniciarJogo());

            configurarBotao(botaoVoltar);
            botaoVoltar.addActionListener(e -> {
                this.getTopLevelAncestor().setVisible(false); // Fecha a tela atual
                new TelaInicial();
            });

            SwingUtilities.invokeLater(() -> {
                int x = ((LARGURA_TELA - botaoJogar.getWidth()) / 2) - 6;
                int y = ((ALTURA_TELA - botaoJogar.getHeight()) / 2) + 12;
                botaoJogar.setBounds(x, y, botaoJogar.getWidth(), botaoJogar.getHeight());
                label.setBounds(x, y - 50, label.getWidth(), label.getHeight());
                botaoVoltar.setBounds(x, y + 100, botaoVoltar.getWidth(), botaoVoltar.getHeight());
            });

            requestFocus(); // Traz o foco para a tela atual
            addKeyListener(this); // Adiciona listener para o teclado

            setFocusable(true);
            SwingUtilities.invokeLater(this::requestFocusInWindow);
        }

        // Função que chama as principais funções para o início do jogo
        private void iniciarJogo() {

            botaoJogar.setVisible(false);
            botaoVoltar.setVisible(false);
            label.setVisible(false);
            rodando = true;
            timer.start();
            revalidate();
            repaint();

            requestFocusInWindow();
        }

        // Pinta os componentes na tela
        @Override
        public void paintComponent(Graphics gr) {
            super.paintComponent(gr);
            gr.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);

            if (rodando) {
                fruta.desenharFruta(gr);

                ArrayList<Point> corpo = cobra.getCorpo();

                for (int i = 0; i < corpo.size(); i++) {
                    Point p = corpo.get(i);
                    gr.setColor(Color.BLACK);
                    gr.fillRect(p.x * TAMANHO_BLOCO, p.y * TAMANHO_BLOCO, TAMANHO_BLOCO, TAMANHO_BLOCO);
                }
            }
        }

        public void gameOver() {


            rodando = false;

            if (jogador != null) {
                BancoDeDados.salvarPontuacao(jogador.getNome(), pontuacao);
            }



            pontuacao = 0;
            pontos.setText("Pontos: " + pontuacao);
            timer.stop();

            cobra.resetarCobra();
            label.setText("GAME OVER");
            label.setVisible(true);

            botaoJogar.setVisible(true);
            botaoVoltar.setVisible(true);

            revalidate();
            repaint();
        }

        // AÇÕES DO TECLADO
        @Override
        public void keyPressed(KeyEvent e) {
            Direcao direcaoAtual = cobra.getDirecao();
            Direcao novaDirecao = null;

            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_A, KeyEvent.VK_LEFT -> novaDirecao = Direcao.ESQUERDA;
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> novaDirecao = Direcao.DIREITA;
                case KeyEvent.VK_W, KeyEvent.VK_UP -> novaDirecao = Direcao.CIMA;
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> novaDirecao = Direcao.BAIXO;
            }
            if (podeMudarDirecao && novaDirecao != null && novaDirecao != direcaoAtual.oposta()) {
                cobra.setDirecao(novaDirecao);
                podeMudarDirecao = false;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        // CONFIGURAÇÕES DA FONTE
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
            botao.setHorizontalTextPosition(SwingConstants.CENTER); // texto centralizado na horizontal
            botao.setVerticalTextPosition(SwingConstants.CENTER);   // texto centralizado na vertical
            botao.setContentAreaFilled(false);  // remove fundo padrão do botão
            botao.setSize(200, 50);
            botao.setFont(carregarFont());
            botao.setBorderPainted(false);
            botao.setForeground(Color.WHITE);
        }
    }
}
