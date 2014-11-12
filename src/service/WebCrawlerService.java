package service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.validator.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.ProcessWeblinks;
import constants.WebCrawlerConstants;

public class WebCrawlerService {
	public void startCrawling() {
		System.out.println("Start Crawling the given URL" + WebCrawlerConstants.WEB_URL_PATH);
		String mainHtmlBody = ProcessWeblinks.getHtmlBody(WebCrawlerConstants.WEB_URL_PATH);
		Matcher mainUrlMatcher = WebCrawlerConstants.Main_URL_PATTERN.matcher(mainHtmlBody);
		List<String> links = new ArrayList<String>();
		while (mainUrlMatcher.find()) {
			String link = WebCrawlerConstants.WEB_URL_PATH + mainUrlMatcher.group(1).replace("date", "");
			System.out.println(link);
			links.add(link);
		}

		for (String link : links) {
			try {
				URL u = new URL(link);
				HttpURLConnection connection = (HttpURLConnection) u.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				if (connection.getResponseCode() == 200) {
					Document doc = Jsoup.connect(link).get();
					Elements elements = doc.select("a[href]");
					Matcher digit = WebCrawlerConstants.DIGIT_PATTERN.matcher(link);
					int month = 0;
					if (digit.find()) {
						month = Integer.parseInt(digit.group().replace(WebCrawlerConstants.YEAR, ""));
					}
					String MM = WebCrawlerConstants.Month.values()[month - 1].toString();
					int counter = 1;
					for (Element element : elements) {
						writeContentToFile(link + element.attr("href"), MM, counter);
						counter++;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeContentToFile(String link, String Month, int counter) {
		System.out.println(link);
		File mkDir = new File("MailArchives");
		if (!mkDir.exists()) {
			try {
				mkDir.mkdir();
			} catch (SecurityException se) {
				se.printStackTrace();
			}
		}
		String filepath = "MailArchives/" + Month + "_" + counter + ".txt";
		UrlValidator urlValidator = new UrlValidator();
		if (urlValidator.isValid(link)) {
			try {
				Document htmlBody = Jsoup.connect(link).get();
				String textContent = Jsoup.parse(htmlBody.toString()).text();
				File file = new File(filepath);
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fileWriter = new FileWriter(file);
				BufferedWriter bufferReader = new BufferedWriter(fileWriter);
				bufferReader.write(textContent.replaceAll(">", ""));
				bufferReader.flush();
				bufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
