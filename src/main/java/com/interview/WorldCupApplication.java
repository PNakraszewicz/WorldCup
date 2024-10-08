package com.interview;

import com.interview.worldcup.command.StartGameCommand;
import com.interview.worldcup.command.UpdateGameCommand;
import com.interview.worldcup.domain.Team;
import com.interview.worldcup.service.ScoreBoard;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class WorldCupApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldCupApplication.class, args);
	}
}
