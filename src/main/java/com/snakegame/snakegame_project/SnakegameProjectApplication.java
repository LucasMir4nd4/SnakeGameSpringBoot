package com.snakegame.snakegame_project;

import com.snakegame.snakegame_project.db.BancoDeDados;
import com.snakegame.snakegame_project.game.ui.TelaInicial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnakegameProjectApplication{

	public static void main(String[] args) {

		System.out.println("Executando o método main");
		new TelaInicial();

		SpringApplication.run(SnakegameProjectApplication.class, args);

	}

}
