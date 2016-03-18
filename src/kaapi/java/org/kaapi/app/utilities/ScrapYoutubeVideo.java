package org.kaapi.app.utilities;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapYoutubeVideo {

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("https://www.youtube.com/playlist?list=PLWznN7ZS7n8XiYJ53d4xqiIfATFPgoAeb").get();
		Elements elementTitles = doc.select(".pl-video-title a.pl-video-title-link");
		StringBuilder data = new StringBuilder();
		for(Element elementTitle : elementTitles){
			System.out.println(elementTitle.text());
			System.out.println(elementTitle.attr("href"));
			System.out.println(elementTitle.attr("href").substring(9, elementTitle.attr("href").indexOf("&")));
			data.append(elementTitle.text()+"\t"+ elementTitle.attr("href").substring(9, elementTitle.attr("href").indexOf("&"))+"\r\n");
		}
		System.out.println(data.toString());
		
	}
}
