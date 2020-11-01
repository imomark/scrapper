package com.scapper.scrapper2;

import com.scapper.scrapper2.service.InitialSetup;
import com.scapper.scrapper2.service.ScrapperService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Scrapper2Application {

    @Value("${base.linkToSearch}")
    String linkToSearch;
	public static void main(String[] args) {


	    ApplicationContext ctx = SpringApplication.run(Scrapper2Application.class, args);
        InitialSetup initialSetup  = ctx.getBean(InitialSetup.class);
		System.out.println("started");
        String link = ctx.getBean(String.class);
        initialSetup.setLinks(link);
        List<String> lists = initialSetup.getAllLinks();
        ScrapperService scrapperService = ctx.getBean(ScrapperService.class);
        for(String list: lists)
            scrapperService.setWordMap(list);
        System.out.println(scrapperService.getWordOutput());
        System.out.println(scrapperService.getWordPairOutput());
		System.out.println("ended");

	}
	@Bean
	public String getUrl(){
	    return linkToSearch;
    }

}
