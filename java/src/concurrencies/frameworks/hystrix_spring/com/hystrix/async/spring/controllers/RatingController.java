package com.hystrix.async.spring.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hystrix.async.spring.models.Rating;

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
public class RatingController {

	private Logger log = LoggerFactory.getLogger(RatingController.class);

	@RequestMapping(path = "rating/{id}", method = RequestMethod.GET)
	public String index(@PathVariable("id") Integer id) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(this.getClass().getClassLoader()
				.getResourceAsStream("rating.json"));
		ArrayNode ratings = (ArrayNode) root.path("ratings");
		TypeReference<List<Rating>> typeRef = new TypeReference<List<Rating>>() {
		};
		List<Rating> ratingList = mapper.readValue(ratings.traverse(), typeRef);

		Rating theRating = null;
		for (Rating rating : ratingList) {
			if (id.equals(rating.getId())) {
				log.debug("Found rating with id: " + rating.getId());
				theRating = rating;
			}
		}

		return mapper.writeValueAsString(theRating);
	}
}