#!/bin/sh
export SPRING_JPA_HIBERNATE_DDL_AUTO=update
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/wiser
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=castro03
export SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQL5Dialect


mvn spring-boot:run
