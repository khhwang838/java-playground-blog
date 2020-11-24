package com.keichee.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class MongoConfig {

    private static final String host = "localhost";
    private static final int port = 27017;
    private static final String database = "blogapp";

    @Bean
    public MongoDatabase blogAppDatabase() {
         MongoClient client = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Collections.singletonList(new ServerAddress(host, port))))
                        .build());
         return client.getDatabase(database);
    }
}
