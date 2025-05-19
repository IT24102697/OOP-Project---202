package org.example.custamizingcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustamizingCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustamizingCrudApplication.class, args);
        System.out.println("\n🔗 Application is running at: http://localhost:8080\n");
    }
}
