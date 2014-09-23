package phr.web.models;

public class Food {
	
	Integer entryID;
	String name;
	double calorie;
	String servingUnit;
	double servingSize;
	Integer restaurantID;
	Boolean fromFatsecret;
	public Integer getEntryID() {
		return entryID;
	}
	public void setEntryID(Integer entryID) {
		this.entryID = entryID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCalorie() {
		return calorie;
	}
	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}
	public String getServingUnit() {
		return servingUnit;
	}
	public void setServingUnit(String servingUnit) {
		this.servingUnit = servingUnit;
	}
	public double getServingSize() {
		return servingSize;
	}
	public void setServingSize(double servingSize) {
		this.servingSize = servingSize;
	}
	public Integer getRestaurantID() {
		return restaurantID;
	}
	public void setRestaurantID(Integer restaurantID) {
		this.restaurantID = restaurantID;
	}
	public Boolean getFromFatsecret() {
		return fromFatsecret;
	}
	public void setFromFatsecret(Boolean fromFatsecret) {
		this.fromFatsecret = fromFatsecret;
	}
	
}
