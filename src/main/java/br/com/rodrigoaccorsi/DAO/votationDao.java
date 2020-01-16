package br.com.rodrigoaccorsi.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import br.com.rodrigoaccorsi.jdbc.ConnectionFactory;
import br.com.rodrigoaccorsi.model.Agenda;
import br.com.rodrigoaccorsi.model.Votation;
import br.com.rodrigoaccorsi.patterns.DAOPattern;
import br.com.rodrigoaccorsi.routes.response.Response;
import br.com.rodrigoaccorsi.routes.response.ResponseBuilder;
import br.com.rodrigoaccorsi.routes.util.RoutesUtils;

@Component
public class VotationDAO implements DAOPattern {

	@Autowired
	private VoteDAO voteDao;
	@Autowired
	private AgendaDAO agendaDao;

	private MongoCollection<Document> collection = 
			ConnectionFactory.getInstance()
					.getCollection(ConnectionFactory.collections.votacao.name());
	
	public Response createEntry(String json) {
		try{
			Document doc = new Document().parse(json);
			collection.insertOne(doc);
			return new ResponseBuilder()
					.withMessage("Success")
					.withObject(json)
					.withResponseCode(HttpStatus.SC_OK)
					.build();
		} catch (Exception e) {
			return new ResponseBuilder()
					.withMessage("Erro ao inserir")
					.withObject(e.getMessage())
					.withResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.build();
		}
	}

	public Response getAll() {
		List<Document> docList = new ArrayList();
		for(Document doc : collection.find()) {
			docList.add(doc);
		}
		return new ResponseBuilder()
				.withMessage("Success")
				.withObject(docList)
				.build();
	}

	@Override
	public Votation getDocumentByObjectId(String objectId) {
		Document docVotation = collection.find(Filters.eq("objectId", objectId)).first();

		return RoutesUtils.castStrJsonToObject(docVotation.toJson(), Votation.class);
	}

	public Response getVotationResult(String objectId) {
		FindIterable<Document> docVotation = voteDao.getAllByVotationId(objectId);
		VotationResults results = new VotationResults();
		for(Document d : docVotation) {
			if(Boolean.parseBoolean(d.get("vote").toString())) {
				results.setVoteTrue(results.getVoteTrue()+1);
			} else {
				results.setVoteFalse(results.getVoteFalse()+1);
			}
			results.setVotes(results.getVotes()+1);
		}
		results.setVotationId(objectId);
		
		//Agenda agenda = RoutesUtils.castStrJsonToObject(agendaDao.getDocumentByObjectId(objectId).getObject().toString(), Agenda.class);
		//results.setAgendaName(agenda.getName());
		return new ResponseBuilder()
				.withMessage("Success")
				.withObject(results)
				.withResponseCode(HttpStatus.SC_OK)
				.build();
	}

	private class VotationResults {
		String votationId;
		String agendaName;
		int voteTrue = 0;
		int voteFalse = 0;
		int votes = 0;

		public int getVoteTrue() {
			return voteTrue;
		}
		public void setVoteTrue(int voteTrue) {
			this.voteTrue = voteTrue;
		}
		public int getVoteFalse() {
			return voteFalse;
		}
		public void setVoteFalse(int voteFalse) {
			this.voteFalse = voteFalse;
		}
		public int getVotes() {
			return votes;
		}
		public void setVotes(int votes) {
			this.votes = votes;
		}
		public String getVotationId() {
			return votationId;
		}
		public void setVotationId(String votationId) {
			this.votationId = votationId;
		}
		public String getAgendaName() {
			return agendaName;
		}
		public void setAgendaName(String agendaName) {
			this.agendaName = agendaName;
		}
	}
}
