package br.com.rodrigoaccorsi.jdbc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Component
public class ConnectionFactory {

	public final String DATABASE_URI = "mongodb://localhost:27017";
	public final String DATABASE_DB = "teste_sicredi";
	private Map<String, MongoCollection<Document>> collectionsMap;
	private static ConnectionFactory instance;

	public enum collections {
		votacao,
		pauta,
		votos,
		associados
	};

	public MongoCollection<Document> getCollection(String collectionName) {
		if(collectionsMap == null) {
			collectionsMap = new HashMap<>();
		} 
		if(collectionsMap.get(collectionName) == null) {
			collectionsMap.put(collectionName, new MongoClient(new MongoClientURI(DATABASE_URI))
					.getDatabase(DATABASE_DB)
					.getCollection(collectionName));
		}
		return collectionsMap.get(collectionName);
	}

	public static void main(String[] args) {
		MongoCollection<Document> collection = 
				ConnectionFactory.getInstance().getCollection(collections.pauta.name());

		System.out.println(collection.count());
		
		Document doc = new Document("name", "MongoDB")
				.append("type", "database")
				.append("count", 1)
				.append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
				.append("info", new Document("x", 203)
						.append("y", 102)
						.append("versions", Arrays.asList("v3.2", "v3.0", "v2.6")));
		
		//collection.insertOne(doc);

		System.out.println(collection.count());

		Document myDoc = collection.find().first();
		System.out.println(myDoc.toJson());

		Document myDoc2 = collection.find(Filters.eq("info.x", 206)).first();
		System.out.println(myDoc2.toJson());
	}

	public static ConnectionFactory getInstance() {
		if(instance == null) {
			instance = new ConnectionFactory();
		}
		return instance;
	}
}
