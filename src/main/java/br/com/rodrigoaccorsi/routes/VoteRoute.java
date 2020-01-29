package br.com.rodrigoaccorsi.routes;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.rodrigoaccorsi.DAO.PeopleDAO;
import br.com.rodrigoaccorsi.DAO.VotationDAO;
import br.com.rodrigoaccorsi.DAO.VoteDAO;
import br.com.rodrigoaccorsi.model.Votation;
import br.com.rodrigoaccorsi.model.Vote;
import br.com.rodrigoaccorsi.patterns.RoutesPattern;
import br.com.rodrigoaccorsi.routes.response.Response;
import br.com.rodrigoaccorsi.routes.response.ResponseBuilder;
import br.com.rodrigoaccorsi.routes.util.RoutesUtils;

@RestController
@RequestMapping("vote")
public class VoteRoute extends RoutesResponse implements RoutesPattern {

	@Autowired
	private VoteDAO voteDao;

	@Autowired
	private VotationDAO votationDao;

	@Autowired
	private PeopleDAO peopleDao;

	@RequestMapping(
			value = "/create", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Response handleCreate(@RequestBody String json, HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
		Vote vote = RoutesUtils.castStrJsonToObject(json, Vote.class);
		Response response;
		if(vote.getVote() == null || StringUtils.isEmpty(vote.getVotationId()) 
				|| vote.getPeopleId() == null || StringUtils.isEmpty(vote.getPeopleId())) {
			response = new ResponseBuilder()
					.withMessage("Parameters 'vote', 'votationId' and 'peopleId' can't be null.")
					.withSuccess(false)
					.withResponseCode(HttpStatus.SC_BAD_REQUEST)
					.build();
		} else if(peopleDao.getDocumentByObjectId(vote.getPeopleId()) == null) { 
			response = new ResponseBuilder()
					.withMessage("People not found.")
					.withSuccess(false)
					.withResponseCode(HttpStatus.SC_NOT_FOUND)
					.build();
		} else {
			Votation votation = votationDao.getDocumentByObjectId(vote.getVotationId());
			if(votation == null ) {
				response = new ResponseBuilder()
						.withMessage("Votation not found.")
						.withSuccess(false)
						.withResponseCode(HttpStatus.SC_NOT_FOUND)
						.build();
			} else {
				Timestamp newTime = new Timestamp(votation.getCreateTime().getTime());
				newTime.setTime(newTime.getTime() + TimeUnit.MINUTES.toMillis(votation.getVotationTime()));

				if(votation.getCreateTime().before(newTime)) {
					response = voteDao.createEntry(new Gson().toJson(vote));
				} else {
					response = new ResponseBuilder()
							.withMessage("Votation closed")
							.withSuccess(false)
							.withResponseCode(HttpStatus.SC_OK)
							.build();
				}
			}
		}
		setResponse(response, httpResponse, httpRequest);
		return getResponse();
	}

	@RequestMapping(
			value = "/all", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Response handleGetAll(HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
		return voteDao.getAll();
	}
}
