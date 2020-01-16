package br.com.rodrigoaccorsi.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import br.com.rodrigoaccorsi.jdbc.ConnectionFactory;
import br.com.rodrigoaccorsi.patterns.DAOPattern;
import br.com.rodrigoaccorsi.routes.response.Response;
import br.com.rodrigoaccorsi.routes.response.ResponseBuilder;
import ch.qos.logback.core.filter.Filter;

@Component
public class AgendaDAO implements DAOPattern {

	private MongoCollection<Document> collection = 
			ConnectionFactory.getInstance()
					.getCollection(ConnectionFactory.collections.pauta.name());
	
	public Response createEntry(String json) {
		try {
			final Document doc = new Document().parse(json);
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
				.withResponseCode(HttpStatus.SC_OK)
				.build();
	}

	@Override
	public Response getDocumentByObjectId(String objectId) {
		Document doc = collection.find(Filters.eq("objectId", objectId)).first();
		return new ResponseBuilder()
				.withObject(doc)
				.withResponseCode(HttpStatus.SC_OK)
				.withMessage("Found")
				.build();
	}
}
