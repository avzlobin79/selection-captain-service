package com.mozlab.application.dto;

public class CaptainTeamDto {

	private Integer teamId;

	private Integer participantId;

	private String participantIdentifier;

	public CaptainTeamDto() {
		super();
	}

	public CaptainTeamDto(Integer teamId, Integer participantId, String participantIdentifier) {
		super();
		this.teamId = teamId;
		this.participantId = participantId;
		this.participantIdentifier = participantIdentifier;
	}

	public Integer getParticipantId() {
		return participantId;
	}

	public String getParticipantIdentifier() {
		return participantIdentifier;
	}

	public Integer getTeamId() {
		return teamId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaptainTeamDto other = (CaptainTeamDto) obj;
		if (participantId == null) {
			if (other.participantId != null)
				return false;
		} else if (!participantId.equals(other.participantId))
			return false;
		if (participantIdentifier == null) {
			if (other.participantIdentifier != null)
				return false;
		} else if (!participantIdentifier.equals(other.participantIdentifier))
			return false;
		if (teamId == null) {
			if (other.teamId != null)
				return false;
		} else if (!teamId.equals(other.teamId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[teamId=" + teamId + ", participantId=" + participantId + ", participantIdentifier="
				+ participantIdentifier + "]";
	}

}
