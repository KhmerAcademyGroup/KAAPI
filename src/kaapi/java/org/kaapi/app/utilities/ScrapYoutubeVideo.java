package org.kaapi.app.utilities;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapYoutubeVideo {

   public static void main(String[] args) throws IOException {
		try{
			
			Document doc = Jsoup.connect("https://www.youtube.com/playlist?list=PLWznN7ZS7n8U-tlROS8R5sYnXOSZZUrIx").get();
			Elements elementTitles = doc.select(".pl-video");
			/*System.out.println(elementTitles);*/
			StringBuilder data = new StringBuilder();
			for(Element elementTitle : elementTitles){
				
				/*System.out.println(elementTitle.select("a.pl-video-title-link").text());
				System.out.println(elementTitle.select("a.pl-video-title-link").attr("href"));
				System.out.println(elementTitle.select(".timestamp span").text());*/
				
//				System.out.println(elementTitle.select("a.pl-video-title-link").attr("href"));
				
				System.out.println(elementTitle.select("a.pl-video-title-link").text());
				String fullUrl = elementTitle.select("a.pl-video-title-link").attr("href");
				System.out.println(fullUrl.substring(fullUrl.indexOf("=")+1 ,fullUrl.indexOf("&") ));
				System.out.println(elementTitle.select(".timestamp span").text()  );
				
				
				/*System.out.println(elementTitle.text());
				System.out.println(elementTitle.attr("href"));
				System.out.println(elementTitle.text());
				System.out.println(elementTitle.attr("href").substring(9, elementTitle.attr("href").indexOf("&")));
				data.append(elementTitle.text()+"\t"+ elementTitle.attr("href").substring(9, elementTitle.attr("href").indexOf("&"))+"\r\n");*/
			}
			/*System.out.println(data.toString());
			System.out.println(data.length());*/
		}catch(Exception exception){
			exception.printStackTrace();	
		}
		
	}
}
