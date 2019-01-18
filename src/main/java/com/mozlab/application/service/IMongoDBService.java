package com.mozlab.application.service;

import com.mozlab.application.model.CaptainTeam;

public interface IMongoDBService {

	CaptainTeam create(Integer teamId, Integer participantId, String participantIdentifier);

	CaptainTeam get(Integer teamId);

	void removeCaptainTeam(Integer teamId);

	boolean isExistCaptainTeam(Integer teamId);

}
