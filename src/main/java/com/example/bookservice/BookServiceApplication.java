package com.example.bookservice;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequestMapping("/books")
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

	private List<Book> bookList = Arrays.asList(new Book(1L, "Baeldung goes to the market", "Tim Schimandle"),
			new Book(2L, "Baeldung goes to the park", "Slavisa"));

	/*
	@GetMapping()
	public List<Book> findAllBooks(@RequestHeader("Authorization") String authorization, @RequestHeader("Accept") String accept) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(authorization);
		System.out.println(accept);
		return bookList;
	cd gi	}
	*/
	
	@GetMapping()
	public List<Book> findAllBooks() {
		return bookList;
	}

	@GetMapping("/level1")
	@PreAuthorize("hasRole('APIVIEWER')")
	public Book helloLevel1() {
		return bookList.get(0);
	}

	// https://stackoverflow.com/questions/41538972/keycloak-spring-boot-configuration/41676556
	// Conforme report acima para mapear papeis entre keycloak e spring security deve-se utilizar hasAuthority
	@GetMapping("/level2")
	@PreAuthorize("hasAuthority('APIVIEWER')")
	public Book helloLevel2() {
		return bookList.get(0);
	}
	
	@GetMapping("/level3")
	@PreAuthorize("hasAuthority('APIVIEWERXXX')")
	public Book helloLevel3() {
		return bookList.get(0);
	}
	
	@GetMapping("/level4")
	@PreAuthorize("hasAuthority('BRG01')")
	public Book helloLevel4() {
		return bookList.get(0);
	}

	@GetMapping("/{bookId}")
	public Book findBook(@PathVariable Long bookId) {
		return bookList.stream().filter(b -> b.getId().equals(bookId)).findFirst().orElse(null);
	}

	@GetMapping("/slow/{milis}")
	public List<Book> findBooksSlow(@PathVariable Long milis) throws InterruptedException {
		Thread.sleep(milis);
		return bookList;
	}
}
