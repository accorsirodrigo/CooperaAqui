package br.com.rodrigoaccorsi.patterns;

import java.util.List;

import org.bson.Document;

import br.com.rodrigoaccorsi.routes.response.Response;

public interface DAOPattern {

	public Response createEntry(String json);
	public Response getAll();
	public Object getDocumentByObjectId(String objectId);
}
