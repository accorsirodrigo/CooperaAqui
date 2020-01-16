package br.com.rodrigoaccorsi.model;

import java.sql.Timestamp;
import java.util.Date;

import br.com.rodrigoaccorsi.patterns.ModelDefaults;

public class Votation extends ModelDefaults {

	private String agendaId;
	private Integer votationTime;
	private Boolean inVotation;
	private Timestamp createTime;

	public Votation() {
		votationTime = 1;
		inVotation = true;
		createTime = new Timestamp(new Date().getTime());
	}

	public String getAgendaId() {
		return agendaId;
	}

	public void setAgendaId(String agendaId) {
		this.agendaId = agendaId;
	}

	public Integer getVotationTime() {
		return votationTime;
	}

	public void setVotationTime(Integer votationTime) {
		this.votationTime = votationTime;
	}

	public Boolean getInVotation() {
		return inVotation;
	}

	public void setInVotation(Boolean inVotation) {
		this.inVotation = inVotation;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
