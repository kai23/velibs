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
import org.xml.sax.SAXException;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.AsyncTask;
import android.util.Log;

public class GetStationDetail extends AsyncTask<StationVelibs, ArrayList<Element>,MarkerOptions> {

	@Override
	protected MarkerOptions doInBackground(StationVelibs... arg) {
		HttpClient client = new DefaultHttpClient();
		String numStation = arg[0].getNumber();
		String url = "http://www.velo.toulouse.fr/service/stationdetails/toulouse/" + numStation;
		HttpGet get = new HttpGet(url);
		StationVelibs item = arg[0];
		HttpResponse response;
		try {
			response = client.execute(get);
			StatusLine statusLine1 = response.getStatusLine();
			int statusCode1 = statusLine1.getStatusCode();

			if (statusCode1 == 200) { // Ok
				InputStream reponse = response.getEntity().getContent();
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc1 = db.parse(reponse);
				doc1.getDocumentElement().normalize();
				Element e2 = (Element) doc1.getElementsByTagName("station").item(0);
				
				item.setAvailable(e2.getElementsByTagName("available").item(0).getTextContent());
				item.setFree(e2.getElementsByTagName("free").item(0).getTextContent());
				item.setTotal(e2.getElementsByTagName("total").item(0).getTextContent());
				item.setTicket(e2.getElementsByTagName("ticket").item(0).getTextContent());
				item.setUpdated(e2.getElementsByTagName("updated").item(0).getTextContent());
				item.setConnected(e2.getElementsByTagName("connected").item(0).getTextContent());				
			} else {
				Log.v("APPLIANDROID", "PAS OK");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new MarkerOptions().title(item.getFree()).position(new LatLng(item.getLat(), item.getLng()));
	}

	@Override
	protected void onPostExecute(MarkerOptions result) {
		retourVersLeFutur(result);
	}

	private MarkerOptions retourVersLeFutur(MarkerOptions velibs2) {
		return velibs2;
	}

}