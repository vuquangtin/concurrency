package com.hystrix.async.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Let's examine this tiny REST controller in detail:
 * <ul>
 * <li>The @RestController annotation indicates that we don't want to render
 * views, but write the results straight into the response body instead.</li>
 * <li>@GetMapping is Spring's shorthand annotation for @RequestMapping(method =
 * RequestMethod.GET) . In this case, it defaults the route to / .</li>
 * <li>Our greeting() method has one argument-- @RequestParam(required=false,
 * defaultValue="") String name . It indicates that this value can be requested
 * via an HTTP query ( ?name=Greg )--the query isn't required, and in case it's
 * missing, it will supply an empty string.</li>
 * <li>Finally, we return one of two messages depending on whether or not the
 * name is an empty string, using Java's ternary operator.</li>
 * </ul>
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

@RestController
public class HomeController {
	@GetMapping
	public String greeting(
			@RequestParam(required = false, defaultValue = "") String name) {
		return name.equals("") ? "Hey!" : "Hey, " + name + "!";
	}
}