package com.kai.maptest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	/**
	 * ATTRIBUTS.
	 */
	private LocationManager locationManager;
	private GoogleMap gMap;
	private Marker marker;
	private ArrayList<StationVelibs> mesStations;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_main);
		initialize();
	}

	@SuppressLint("NewApi")
	protected void initialize() {
		// Création de la map
		gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

		getActionBar().setSplitBackgroundDrawable(new ColorDrawable(Color.argb(128, 0, 0, 0)));
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(128, 0, 0, 0)));

		// Récupération des stations en async
		try {
			Log.v("AAPLI", "OK");
			mesStations = new GetAllStationVelibsAsync().execute("http://www.velo.toulouse.fr/service/carto").get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		// Obtention de la référence du service
		locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 10, this);
		}

		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
		}
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
		}
		// mMap is an instance of GoogleMap that has already been initialized
		// else where
		
		Toast.makeText(this, "Récupération de votre position...", Toast.LENGTH_LONG).show();
		
		gMap.setOnCameraChangeListener(getCameraChangeListener());
	}

	public OnCameraChangeListener getCameraChangeListener() {
		return new OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition position) {
				addItemsToMap();
			}
		};
	}

	private void addItemsToMap()
	{ 
	    if(this.gMap != null)
	    {
	        // Récupération de la zone visible
	        LatLngBounds bounds = this.gMap.getProjection().getVisibleRegion().latLngBounds;
	        
	        // On vide la map
	        gMap.clear();
	        
	        // Affichage des items qui sont visibles
	        for(StationVelibs item : mesStations) 
	        {
	        	LatLng latLong = new LatLng(item.getLat(), item.getLng());
	            //If the item is within the the bounds of the screen
	            if(bounds.contains(latLong))
	            {
	            	new GetStationDetail().execute(item);
	            	try {
						marker = gMap.addMarker(new GetStationDetail().execute(item).get());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
	            }
	        }
	    }
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onLocationChanged(final Location location) {
		LatLng latlong = new LatLng(location.getLatitude(), location.getLongitude());
		gMap.moveCamera(CameraUpdateFactory.newLatLng(latlong));

		// Ajout d'un marker
		marker = gMap.addMarker(new MarkerOptions().title("Vous êtes ici").position(latlong));

		Toast.makeText(this, "Location trouvée !", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(final String provider) {
		String msg = "Connexion perdue !";
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(final String provider) {
		String msg = "Recherche en cours";
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(final String provider, final int status, final Bundle extras) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			// Comportement du bouton "A Propos"
			return true;
		case R.id.menu_help:
			// Comportement du bouton "Aide"
			return true;
		case R.id.menu_settings:
			// Comportement du bouton "Paramètres"
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
