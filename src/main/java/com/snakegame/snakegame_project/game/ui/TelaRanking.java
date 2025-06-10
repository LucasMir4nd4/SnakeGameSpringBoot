package com.snakegame.snakegame_project.game.ui;

import com.snakegame.snakegame_project.db.BancoDeDados;
import com.snakegame.snakegame_project.game.model.Jogador;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TelaRanking extends JFrame {

    public static final int LARGURA_TELA = 600;
    public static final int ALTURA_TELA = 600;

    public TelaRanking() {
        setTitle("Snake Game - Ranking");
        setSize(LARGURA_TELA, ALTURA_TELA);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fundo
        Image fundo = new ImageIcon(getClass().getResource("/images/fundo.png")).getImage();
        fundo = fundo.getScaledInstance(LARGURA_TELA, ALTURA_TELA, Image.SCALE_DEFAULT);
        JLabel imagemFundo = new JLabel(new ImageIcon(fundo));
        imagemFundo.setLayout(new BorderLayout());
        setContentPane(imagemFundo);

        // Ícone da janela
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon.png"));
        setIconImage(icon.getImage());

        // Título
        JLabel titulo = new JLabel("Ranking", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(carregarFont());
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.add(titulo, BorderLayout.NORTH);

        // Painel da tabela
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // Construir tabela HTML
        StringBuilder html = new StringBuilder("<html><body>");
        html.append("<table width='100%' border='1' cellspacing='0' cellpadding='5'>");
        html.append("<tr><th align='center'>Posição</th><th align='center'>Jogador</th><th align='center'>Pontuação</th></tr>");

        List<Jogador> ranking = BancoDeDados.listarRanking();
        // Ordena o ranking por pontuação decrescente
        ranking.sort((j1, j2) -> Integer.compare(j2.getPontuacao(), j1.getPontuacao()));
        for (int i = 0; i < ranking.size(); i++) {
            Jogador j = ranking.get(i);
            html.append("<tr>")
                    .append("<td align='center'>").append(i + 1).append("º</td>")
                    .append("<td align='center'>").append(j.getNome()).append("</td>")
                    .append("<td align='center'>").append(j.getPontuacao()).append("</td>")
                    .append("</tr>");
        }
        html.append("</table></body></html>");

        JLabel tabelaLabel = new JLabel(html.toString());
        tabelaLabel.setFont(carregarFont().deriveFont(16f));
        tabelaLabel.setForeground(Color.WHITE);
        tablePanel.add(tabelaLabel);

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(tablePanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(500, 350));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Configurações do scroll
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);  // Ajuste este valor para mudar a velocidade
        verticalScrollBar.setBlockIncrement(64); // Ajuste este valor para mudar o salto maior

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Botão Voltar
        JButton botaoVoltar = new JButton("Voltar");
        configurarBotao(botaoVoltar);
        botaoVoltar.setPreferredSize(new Dimension(200, 50));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(botaoVoltar);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Configura ação do botão
        botaoVoltar.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            dispose();
        });

        add(mainPanel);
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
        botao.setHorizontalTextPosition(SwingConstants.CENTER);
        botao.setVerticalTextPosition(SwingConstants.CENTER);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setForeground(Color.WHITE);
        botao.setFont(carregarFont().deriveFont(16f));
    }
}