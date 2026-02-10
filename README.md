# SnakeGameSpringBoot

Um projeto em **Java com Spring Boot** que implementa um jogo da cobrinha (Snake) com um sistema de persistência de pontuações dos jogadores usando **SQLite**.

Esse repositório combina a lógica do jogo com uma API backend que faz um **CRUD de jogadores** (criar, listar, atualizar e remover jogadores e seus scores), armazenando tudo em um banco de dados leve (`.db`). Isso demonstra habilidades tanto em lógica de jogo quanto em desenvolvimento backend com Spring Boot.

---

## 🧠 Funcionalidades

### ✔️ Jogo Snake
- Jogo clássico da cobrinha com lógica de movimentação e colisões;
- Sistema de controle e placar pelo backend.

### 📊 CRUD de Jogadores
- Cadastro de novos jogadores;
- Listagem de jogadores e seus melhores scores;
- Atualização de dados de jogador;
- Remoção de jogadores;
- As operações são feitas através de endpoints REST no Spring Boot.

---

## 🚀 Tecnologias utilizadas

O projeto foi desenvolvido com as seguintes ferramentas e bibliotecas:

| Tecnologia | Finalidade |
|------------|------------|
| Java       | Linguagem principal |
| Spring Boot| Framework para a API REST |
| Spring Data JPA | Mapeamento objeto-relacional |
| SQLite     | Banco de dados leve (arquivo `.db`) |
| Maven      | Gerenciamento de dependências e build |

---

## 🛠️ Como rodar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/LucasMir4nd4/SnakeGameSpringBoot.git
