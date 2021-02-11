package dwight.global.wiser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WiserOnlineApplication {
    public static void main(String[] args) {
        SpringApplication.run(WiserOnlineApplication.class, args);
    }
}
/*
Properties:
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://aa19k7bjjgn704q.csdomcskus2z.us-east-2.rds.amazonaws.com:3306/ebdb
spring.datasource.username=harambee
spring.datasource.password=dwightglobal
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
 */