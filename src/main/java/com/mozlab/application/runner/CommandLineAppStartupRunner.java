package com.mozlab.application.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.mozlab.application.model.CaptainTeam;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);
	@Autowired
	MongoRepository<CaptainTeam, Integer> repo;

	@Override
	public void run(String... args) throws Exception {

		if (!repo.existsById(1000)) {

			CaptainTeam entity = repo.save(new CaptainTeam(1000, 1111, "Alex"));

			System.out.println("Create for test :CaptainTeam id:" + entity);

		}

		if (!repo.existsById(1001)) {

			CaptainTeam entity = repo.save(new CaptainTeam(1001, 1111, "Alex"));

			System.out.println("Create for test :CaptainTeam id:" + entity);

		}

		logger.info(
				"Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.");
	}
}