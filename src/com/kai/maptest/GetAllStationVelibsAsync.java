package com.kai.maptest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

public class GetAllStationVelibsAsync extends AsyncTask<String, ArrayList<Element>, ArrayList<StationVelibs>> {

	ArrayList<StationVelibs> velibs;

	@Override
	protected ArrayList<StationVelibs> doInBackground(String... arg) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(arg[0]);

		velibs = new ArrayList<StationVelibs>();
		try {
			HttpResponse response = client.execute(get);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				InputStream reponse = response.getEntity().getContent();
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(reponse);
				doc.getDocumentElement().normalize();
				// Récupération des noeuds dans un tableau
				NodeList markersNode = doc.getElementsByTagName("marker");

				for (int i = 0; i < markersNode.getLength(); i++) {
					Element eElement = (Element) markersNode.item(i);
					StationVelibs station = new StationVelibs(eElement);
					velibs.add(station);
				}
			} else {
				Log.v("APPLIANDROID", "PAS OK");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return velibs;
	}

	@Override
	protected void onPostExecute(ArrayList<StationVelibs> result) {
		retourVersLeFutur(velibs);
	}

	private ArrayList<StationVelibs> retourVersLeFutur(ArrayList<StationVelibs> velibs2) {
		return velibs2;
	}

}