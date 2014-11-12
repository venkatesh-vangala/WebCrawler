package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProcessWeblinks {
	public static String getHtmlBody(String urlPath) {
		StringBuffer htmlBody = new StringBuffer();
		try {
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream inputStream = connection.getInputStream();
			BufferedReader bufferStream = new BufferedReader(new InputStreamReader(inputStream));
			String text;
			while ((text = bufferStream.readLine()) != null) {
				htmlBody.append(text + "\n");
			}
			inputStream.close();
			bufferStream.close();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlBody.toString();
	}
}
