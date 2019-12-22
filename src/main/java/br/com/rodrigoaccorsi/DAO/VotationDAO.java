package br.com.rodrigoaccorsi.DAO;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;

import br.com.rodrigoaccorsi.jdbc.ConnectionFactory;

@Component
public class VotationDAO{

	private MongoCollection<Document> collection = 
			ConnectionFactory.getInstance()
				.getCollection(ConnectionFactory.collections.pauta.name());
	
	public void createEntry(String json) {
		Document doc = new Document().parse(json);
		collection.insertOne(doc);
	}

	public List<Document> getAll() {
		List<Document> docList = new ArrayList();
		for(Document doc : collection.find()) {
			docList.add(doc);
		}
		return docList;
	}
}
