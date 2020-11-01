package com.scapper.scrapper2.repo;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;


import java.io.IOException;

@Repository
public class ScrapperRespository {

    private Document document;
    private String url;
    ScrapperRespository(){
        System.out.println("repositor constructed");
    }
    public Document getConnection(String url){

        try {
            document = Jsoup.connect(url).get();
            this.url = url;
            System.out.println("connection successfull");
        } catch (Exception e) {
            System.out.println("connection not successfull");
        }
        return document;
    }

    public String getUrl() {
        return url;
    }

}
