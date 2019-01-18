package com.mozlab.application.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mozlab.application.dto.CaptainTeamDto;

@Document
public class CaptainTeam {

	@Id
	private Integer teamId;

	private Integer participantId;

	private String participantIdentifier;

	private Long date;

	public Integer getTeamId() {
		return teamId;
	}

	public Integer getParticipantId() {
		return participantId;
	}

	public String getParticipantIdentifier() {
		return participantIdentifier;
	}

	public Long getDate() {
		return date;
	}

	public CaptainTeam(Integer teamId, Integer participantId, String participantIdentifier) {
		super();
		this.teamId = teamId;
		this.participantId = participantId;
		this.participantIdentifier = participantIdentifier;
		this.date = Instant.now().toEpochMilli();
	}

	public CaptainTeamDto dto() {
		return new CaptainTeamDto(teamId, participantId, participantIdentifier);
	}

	@Override
	public String toString() {
		return "Captain [date=" + date + ", teamId=" + teamId + ", participantId=" + participantId
				+ ", participantIdentifier=" + participantIdentifier + "]";
	}

}
