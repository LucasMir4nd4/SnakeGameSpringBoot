package com.snakegame.snakegame_project.game.ui;

import com.snakegame.snakegame_project.db.BancoDeDados;
import com.snakegame.snakegame_project.game.model.Cobra;
import com.snakegame.snakegame_project.game.model.Direcao;
import com.snakegame.snakegame_project.game.model.Fruta;
import com.snakegame.snakegame_project.game.model.Jogador;

import javax.swing.*;
import java.awt.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
public class Login extends JFrame{
    public static final int LARGURA_TELA = 600;
    public static final int ALTURA_TELA = 600;

    public ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon.png"));
    public static JButton botaoEntrar = new JButton("Entrar");
    public static JButton botaoCadastro = new JButton("Cadastro");

    public Login(){
        setTitle("Snake Game");
        setSize(LARGURA_TELA, ALTURA_TELA);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
        setResizable(false);
        requestFocusInWindow();

        // Fundo
        Image fundo = new ImageIcon(getClass().getResource("/images/fundo.png")).getImage();
        fundo = fundo.getScaledInstance(LARGURA_TELA, ALTURA_TELA, Image.SCALE_DEFAULT);
        JLabel imagemFundo = new JLabel(new ImageIcon(fundo));
        imagemFundo.setLayout(null); // necessário para posicionar os botões
        setContentPane(imagemFundo);

        // Ícone da janela
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon.png"));
        setIconImage(icon.getImage());

        // Botões
        configurarBotao(botaoEntrar);
        configurarBotao(botaoCadastro);


        // Define posição manual dos botões (layout null)
        int largura = 200;
        int altura = 50;
        int x = (LARGURA_TELA - largura) / 2-6;
        int y =300;

        botaoEntrar.setBounds(x, y+100, largura, altura);
        botaoCadastro.setBounds(x-25, y+100, largura+50, altura);


        imagemFundo.add(botaoEntrar);
        imagemFundo.add(botaoCadastro);

        JLabel labelUsuario = new JLabel("Usuário:");
        configuraLabel(labelUsuario);
        labelUsuario.setBounds(x,y-200, largura, altura);
        imagemFundo.add(labelUsuario);

        JTextField campoUsuario = new JTextField(20);
        campoUsuario.setBounds(x, y - 150, largura, altura);
        imagemFundo.add(campoUsuario);


        JLabel labelSenha = new JLabel("Senha:");
        configuraLabel(labelSenha);
        labelSenha.setBounds(x, y-40 , largura, altura);
        imagemFundo.add(labelSenha);

        JPasswordField campoSenha = new JPasswordField(20);
        campoSenha.setBounds(x, y, largura, altura);
        imagemFundo.add(campoSenha);

        campoSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    botaoEntrar.doClick();
                }
            }
        });

        campoSenha.requestFocusInWindow();

        BancoDeDados.inicializar();
        // Ações dos botões
        // Cadastro
        botaoCadastro.addActionListener(e -> {
            // Lógica de cadastro
            String usuario = campoUsuario.getText();
            String senha = new String(campoSenha.getPassword());

            if (usuario.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BancoDeDados.cadastro(usuario, senha);
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

            Jogador jogador = new Jogador(usuario, senha);

            // Inicia o jogo após o cadastro
            new TelaJogo(new Cobra(Direcao.DIREITA), new Fruta(),jogador);
        });

        configurarBotao(botaoEntrar);

        // Botão Entrar
        botaoEntrar.addActionListener(e -> {
            // Lógica de autenticação aqui
            String usuario = campoUsuario.getText();
            String senha = new String(campoSenha.getPassword());

            // Verificar usuário e senha
            System.out.println("Usuário: " + usuario + ", Senha: " + senha);

            Optional<Jogador> jogadorOpt = BancoDeDados.getJogador(usuario);

            if (jogadorOpt.isPresent()) {
                Jogador jogador = jogadorOpt.get();
                if (jogador.getSenha().equals(senha)) {
                    JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose(); // Fecha tela de login
                    new TelaJogo(new Cobra(Direcao.DIREITA), new Fruta(), jogador);
                } else {
                    JOptionPane.showMessageDialog(this, "Senha incorreta. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            else {
                botaoCadastro.setBounds(x-25, y+170, largura+50, altura);
                botaoCadastro.setVisible(true);
                JOptionPane.showMessageDialog(this, "Usuário não encontrado. Por favor, cadastre-se.", "Erro", JOptionPane.ERROR_MESSAGE);
                this.repaint();

            }

        });

        setVisible(true);
    }
    private void configurarBotao(JButton botao) {
        ImageIcon imagemBotao = new ImageIcon(getClass().getResource("/images/ImagemBotao1.png"));
        botao.setIcon(imagemBotao);
        botao.setHorizontalTextPosition(SwingConstants.CENTER);
        botao.setVerticalTextPosition(SwingConstants.CENTER);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setForeground(Color.WHITE);
        botao.setFont(carregarFont());
    }
    private void configuraLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(carregarFont());
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
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

}
