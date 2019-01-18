package com.mozlab.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.mozlab.application.model.CaptainTeam;

@Service
public class MongoDBServiceImpl implements IMongoDBService {

	@Autowired
	MongoRepository<CaptainTeam, Integer> mongoRepo;

	@Override
	public CaptainTeam create(Integer teamId, Integer participantId, String participantIdentifier) {
		CaptainTeam captainTeam = new CaptainTeam(teamId, participantId, participantIdentifier);

		return mongoRepo.insert(captainTeam);
	}

	@Override
	public CaptainTeam get(Integer teamId) {

		return mongoRepo.findById(teamId).get();

	}

	@Override
	public void removeCaptainTeam(Integer teamId) {

		mongoRepo.deleteById(teamId);

	}

	@Override
	public boolean isExistCaptainTeam(Integer teamId) {
		return mongoRepo.existsById(teamId);
	}

}
