package main;
import service.WebCrawlerService;

public class WebCrawlerApp {
	public static void main(String args[]){
		System.out.println("Main App Intialized.");
		WebCrawlerService webCrawlerService = new WebCrawlerService();
		webCrawlerService.startCrawling();
	}
}
