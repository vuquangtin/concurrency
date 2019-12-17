package com.hystrix.async.spring.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rx.Observable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hystrix.async.spring.models.Movie;
import com.hystrix.async.spring.observables.RatingCommand;
import com.hystrix.async.spring.observables.SocialCommand;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

@RestController
public class MovieController {

	private Logger logger = LoggerFactory.getLogger(MovieController.class);

	@RequestMapping(path = "movie/{id}", method = RequestMethod.GET)
	public String index(@PathVariable("id") Integer id) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		JsonNode root = mapper.readTree(this.getClass().getClassLoader()
				.getResourceAsStream("data.json"));
		ArrayNode movies = (ArrayNode) root.path("movies");
		TypeReference<List<Movie>> typeRef = new TypeReference<List<Movie>>() {
		};
		List<Movie> moviesList = mapper.readValue(movies.traverse(), typeRef);

		Movie theMovie = null;
		for (Movie movie : moviesList) {
			if (id.equals(movie.getId())) {
				theMovie = movie;
				RatingCommand ratingCommand = new RatingCommand(id);
				Observable<String> ratingObservable = ratingCommand.observe();
				theMovie.setRating(mapper
						.readTree(ratingObservable.toBlocking().first())
						.get("rating").asInt());
				SocialCommand socialCommand = new SocialCommand(id);
				Observable<String> socialObservable = socialCommand.observe();
				theMovie.setFriend(mapper
						.readTree(socialObservable.toBlocking().first())
						.get("friend").asText());
			}
		}
		logger.debug("writeValueAsString");
		return mapper.writeValueAsString(theMovie);
	}
}
