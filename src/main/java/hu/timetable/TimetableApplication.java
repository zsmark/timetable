package hu.timetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class TimetableApplication {
	public static void main(String[] args) {
		SpringApplication.run(TimetableApplication.class, args);
	}
}
