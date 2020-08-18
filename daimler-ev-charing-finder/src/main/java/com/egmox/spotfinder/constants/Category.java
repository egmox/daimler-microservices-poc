package com.egmox.spotfinder.constants;

public enum Category {
	PARKING_FACILITY("parking-facility"), EV_CHARGING_STATION("ev-charging-station"), RESTAURANT("restaurant");

	String name;

	Category(String name) {
		this.name = name;
	}

	public String getName(Category cat) {
		return this.name;
	}

}
