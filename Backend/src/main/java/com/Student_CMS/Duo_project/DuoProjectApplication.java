
package com.Student_CMS.Duo_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DuoProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(DuoProjectApplication.class, args);
	}
}
