package com.rainng.coursesystem.manager;

import com.rainng.coursesystem.dao.redis.SdnuNewsDAO;
import com.rainng.coursesystem.model.bo.SdnuNewsBO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SdnuNewsManager extends BaseManager {
    private static final int CRAWL_INTERVAL = 60 * 60 * 1000;
    private static final int CRAWL_TIMEOUT = 30 * 1000;
    private static final String CRAWL_TARGET_URL = "https://www.cisau.com.cn/html/list_1469.html";
    private static final String BASE_URL = "https://www.cisau.com.cn/";

    private final SdnuNewsDAO sdnuNewsDAO;

    public SdnuNewsManager(SdnuNewsDAO sdnuNewsDAO) {
        this.sdnuNewsDAO = sdnuNewsDAO;
    }

    public List<SdnuNewsBO> getAllNews() {
        Map<String, String> map = sdnuNewsDAO.getAllNews();

        List<SdnuNewsBO> newsList = new ArrayList<>(map.size());
        for (String key : map.keySet()) {
            String value = map.get(key);
            String[] split = value.split("&&");
            String date = split[0];
            String url = split[1];
            newsList.add(new SdnuNewsBO(key, date, url));
        }

        return newsList;
    }

    @Scheduled(fixedDelay = CRAWL_INTERVAL)
    public void crawlNews() {
        Document pageDoc = fetchPage();
        if (pageDoc == null) {
            return;
        }

        List<SdnuNewsBO> newsList = parseNews(pageDoc);
        sdnuNewsDAO.clear();
        for (SdnuNewsBO news : newsList) {
            sdnuNewsDAO.addNews(news.getTitle(), news.getDate() + "&&" + news.getUrl());
        }
        List<SdnuNewsBO> newsLatestList = parseLatestNews(pageDoc);
        for (SdnuNewsBO news : newsLatestList) {
            sdnuNewsDAO.addLatestNews(news.getTitle(), news.getDate() + "&&" + news.getUrl());
        }
    }



    private Document fetchPage() {
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(CRAWL_TARGET_URL), CRAWL_TIMEOUT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return doc;
    }

    private List<SdnuNewsBO> parseNews(Document pageDoc) {
        Elements elements = pageDoc.body()
                .getElementsByClass("noborder");
        List<SdnuNewsBO> newsList = new ArrayList<>();
        for (Element noborderElement : elements) {
            Elements lis = noborderElement.getElementsByTag("li");
            for (Element element : lis) {
                Element aTag = element.getElementsByTag("a").get(0);
                Element dateTag = element.getElementsByTag("span").get(0);
                String url = BASE_URL + aTag.attr("href");
                String title = aTag.text();
                String date = dateTag.text();
                if (newsList.size() == 10) {
                    break;
                }
                newsList.add(new SdnuNewsBO(title, date, url));
            }
        }
        return newsList;
    }

    private List<SdnuNewsBO> parseLatestNews(Document pageDoc) {
        Elements elements = pageDoc.body()
                .getElementsByClass("right02box");
        List<SdnuNewsBO> newsList = new ArrayList<>();
        Element element = elements.get(0);
        Elements tags = element.getElementsByTag("li");
        for (Element li : tags) {
            Elements span = li.getElementsByTag("span");
            Element N = span.get(0);
            String data  =  span.get(0).attr("class").split("N")[1];
            Elements a = li.getElementsByTag("a");
            String title = a.attr("title");
            String url = BASE_URL + a.attr("href");
            newsList.add(new SdnuNewsBO(title, data, url));
        }
        return newsList;
    }

    public List<SdnuNewsBO> getLatestAllNews() {
        Map<String, String> map = sdnuNewsDAO.getLatestAllNews();
        List<SdnuNewsBO> newsList = new ArrayList<>(map.size());
        for (String key : map.keySet()) {
            String value = map.get(key);
            String[] split = value.split("&&");
            String date = split[0];
            String url = split[1];
            newsList.add(new SdnuNewsBO(key, date, url));
        }
        return newsList;
    }
}
