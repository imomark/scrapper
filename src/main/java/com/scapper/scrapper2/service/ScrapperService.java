package com.scapper.scrapper2.service;

import com.scapper.scrapper2.repo.ScrapperRespository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScrapperService {

    private final ScrapperRespository scrapperRespository;
    private Document document;
    private List<String> urls = new ArrayList<>();
    private Map<String, Integer> wordMap = new HashMap<>();
    private Map<String, Integer> wordPairMap = new HashMap<>();
    @Value("${base.noToSearch}")
    private List<String> listToignore;
    public ScrapperService(ScrapperRespository scrapperRespository) {
        this.scrapperRespository = scrapperRespository;
    }

    public Map<String, Integer> formWordPair(String text){
        List<String> words = Arrays.asList(text.split(" "));
        List<String> wordpair = new ArrayList<>();
        for(int i =0; i <words.size()-1;i++){
            String word = words.get(i);
            wordpair.add(word+ " " + words.get(i+1));
            //break;


        }
        Map<String, Integer> wordMap = new HashMap<>();
        wordpair.stream().filter(e -> !e.equals(" "))
                .map(e->e.toUpperCase())
                .forEach(a->{
                    if(wordPairMap.containsKey(a))
                        wordPairMap.put(a, wordPairMap.get(a)+1);
                    else
                        wordPairMap.put(a,1);
                });
        return  wordPairMap;

    }
    public Map<String, Integer> getWordMap(String text){
        // Recording wordcount
        List<String> words = Arrays.asList(text.split(" "));
        words.stream().filter(e -> !e.equals(" "))
                .map(e->e.toUpperCase())
                .forEach(a->{
                    if(wordMap.containsKey(a))
                        wordMap.put(a, wordMap.get(a)+1);
                    else
                        wordMap.put(a,1);
                });
        return  wordMap;
    }
    public Map<String, Integer> sortMapOnValues(Map<String, Integer> map){
        //Sorting map based on values
        Map result = map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue, newValue)->newValue, LinkedHashMap::new));
        return result;
    }
    public List<String> getTopTenItems(Map<String,Integer> map){
        //creating final list
        List<String> finalList =  new ArrayList<>();
        map.entrySet().stream().filter(a->!((Map.Entry)a).getKey().equals(""))
                .filter(a->!listToignore.contains(((Map.Entry)a).getKey()))
                .limit(10)
                .forEach((a)->finalList.add(a.toString()));
        return finalList;
    }
    public void setWordMap(String url){
        String text = scrapperRespository.getConnection(url).body().text();
        getWordMap(text);
        formWordPair(text);
    }
    public String getWordOutput(){
        wordMap = sortMapOnValues(wordMap);
        return getTopTenItems(wordMap).toString();
    }
    public String getWordPairOutput(){
        wordPairMap = sortMapOnValues(wordPairMap);
        return getTopTenItems(wordPairMap).toString();
    }

}
