package br.com.rodrigoaccorsi.routes;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.rodrigoaccorsi.DAO.AgendaDAO;
import br.com.rodrigoaccorsi.DAO.VotationDAO;
import br.com.rodrigoaccorsi.model.Votation;
import br.com.rodrigoaccorsi.patterns.RoutesPattern;
import br.com.rodrigoaccorsi.routes.response.Response;
import br.com.rodrigoaccorsi.routes.response.ResponseBuilder;
import br.com.rodrigoaccorsi.routes.util.RoutesUtils;

@RestController
@RequestMapping("votacao")
public class VotationRouter extends RoutesResponse implements RoutesPattern {

	@Autowired
	private VotationDAO votationDao;
	@Autowired
	private AgendaDAO agendaDao;

	@RequestMapping(
			value = "/create", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Response handleCreate(@RequestBody String json, HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
		Votation votation = RoutesUtils.castStrJsonToObject(json, Votation.class);
		Response response;
		if(!(agendaDao.getDocumentByObjectId(votation.getAgendaId()) == null)) {
			response = votationDao.createEntry(new Gson().toJson(votation));
		} else {
			response = new ResponseBuilder()
					.withMessage("Agenda not found")
					.withSuccess(false)
					.withResponseCode(HttpStatus.SC_NOT_FOUND)
					.build();
		}
		setResponse(response, httpResponse, httpRequest);
		return getResponse();
	}

	@RequestMapping(
			value = "/all", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Response handleGetAll(HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
		return votationDao.getAll();
	}

	@RequestMapping(
			value = "/result/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Response result(@PathVariable("id") String objectId, HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
		setResponse(votationDao.getVotationResult(objectId), httpResponse, httpRequest);
		return getResponse();
	}
}
