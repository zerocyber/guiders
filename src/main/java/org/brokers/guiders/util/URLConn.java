package org.brokers.guiders.util;

import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class URLConn {
	URLConnection conn;

	public URLConn(String urlPath, int port) {
		try {
			URL url = new URL(urlPath + ":" + port);
			conn = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void urlPost(JSONPObject jsonObject) {
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		try {
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(jsonObject.toString());
			wr.flush();

			InputStream is = conn.getInputStream();
			Scanner sc = new Scanner(is);
			int line = 1;
			while (sc.hasNext()) {
				String str = sc.nextLine();
				System.out.println((line++) + ":" + str);
			}
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
