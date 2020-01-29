package br.com.rodrigoaccorsi.model;

import br.com.rodrigoaccorsi.patterns.ModelDefaults;

public class Vote extends ModelDefaults{

	private String votationId;
	private String peopleId;
	private Boolean vote;

	public String getVotationId() {
		return votationId;
	}
	public void setVotationId(String votationId) {
		this.votationId = votationId;
	}
	public Boolean getVote() {
		return vote;
	}
	public void setVote(Boolean vote) {
		this.vote = vote;
	}
	public String getPeopleId() {
		return peopleId;
	}
	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}

}
