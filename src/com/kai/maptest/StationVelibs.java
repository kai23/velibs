package com.kai.maptest;

import org.w3c.dom.Element;

import android.util.Log;

public class StationVelibs {

	private String name;
	private String number;
	private String address;
	private String fullAddress;
	private String lat;
	private String lng;
	private String open;
	private String bonus;

	private String available;
	private String free;
	private String total;
	private String ticket;
	private String updated;
	private String connected;

	
	public StationVelibs(Element e1, Element e2) {
		super();
		this.name = e1.getAttribute("name");
		this.number =  e1.getAttribute("number");
		this.address =  e1.getAttribute("address");
		this.fullAddress =  e1.getAttribute("fullAddress");
		this.lat =  e1.getAttribute("lat");
		this.lng =  e1.getAttribute("lng");
		this.open =  e1.getAttribute("open");
		this.bonus =  e1.getAttribute("bonus");
		
		
		this.available =  e2.getElementsByTagName("available").item(0).getTextContent();
		this.free =  e2.getElementsByTagName("free").item(0).getTextContent();
		this.total = e2.getElementsByTagName("total").item(0).getTextContent();
		this.ticket = e2.getElementsByTagName("ticket").item(0).getTextContent();
		this.updated =  e2.getElementsByTagName("updated").item(0).getTextContent();
		this.connected =  e2.getElementsByTagName("connected").item(0).getTextContent();
	}


	public StationVelibs() {

	}

	
	public StationVelibs(Element e1) {
		super();
		this.name = e1.getAttribute("name");
		this.number =  e1.getAttribute("number");
		this.address =  e1.getAttribute("address");
		this.fullAddress =  e1.getAttribute("fullAddress");
		this.lat =  e1.getAttribute("lat");
		this.lng =  e1.getAttribute("lng");
		this.open =  e1.getAttribute("open");
		this.bonus =  e1.getAttribute("bonus");
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	
	
	
	public Double getLat() {
		return Double.parseDouble(lat);
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return Double.parseDouble(lng);
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getConnected() {
		return connected;
	}

	public void setConnected(String connected) {
		this.connected = connected;
	}
}
