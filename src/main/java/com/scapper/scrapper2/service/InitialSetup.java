package com.scapper.scrapper2.service;

import com.scapper.scrapper2.repo.ScrapperRespository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitialSetup {
    private final ScrapperRespository scrapperRespository;
    private Document document;
    private List<String> links = new ArrayList<>();


    public InitialSetup(ScrapperRespository scrapperRespository) {

        this.scrapperRespository = scrapperRespository;
        System.out.println("contructor");
    }

    public void setLinks(String url){
        //System.out.println("set links called");
        document = scrapperRespository.getConnection(url);
        //System.out.println(url);
        List<String> list = getLinks(url);
        //System.out.println(list);
        if(list.size()==0)
            return;
        else{
            links.addAll(list);
            list.forEach(a->setLinks(a));
        }

    }
    public List<String> getLinks(String url){
        List<String> list = new ArrayList<>();
        String arr[] = url.split("/");
        String select = arr[0]+"//"+arr[2];
        Elements elements = document.select("a[href]");
        for(Element e : elements){
            String word = e.attr("href");
            if(word.contains(select) && !word.equals(url)
                    && !links.contains(url))
                list.add(e.attr("href"));
        }
        return  list;
    }
    public void printList(){
        links.forEach(a-> System.out.println(a));
    }
    public List<String> getAllLinks(){
        return links;
    }

}
