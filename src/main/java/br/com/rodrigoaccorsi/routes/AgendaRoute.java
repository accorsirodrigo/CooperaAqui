package br.com.rodrigoaccorsi.routes;

import java.io.IOException;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigoaccorsi.DAO.VotationDAO;

@RestController
@RequestMapping("agenda")
public class AgendaRoute {

	@Autowired
	private VotationDAO votacaoDao;

	@RequestMapping(
			value = "/create", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody String handleFileUpload(@RequestBody String json) throws IOException {
		votacaoDao.createEntry(json);
		return "";
	}

	@RequestMapping(
			value = "/all", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody List<Document> handleGetAll() throws IOException {
		return votacaoDao.getAll();
	}

}
