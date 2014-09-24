package phr.fatsecret;

public class FatSecretFood {

	String brand_name;
	String food_description;
	String food_id;
	String food_name;
	String food_type;
	String food_url;

	public FatSecretFood(String brand_name, String food_description,
			String food_id, String food_name, String food_type, String food_url) {
		super();
		this.brand_name = brand_name;
		this.food_description = food_description;
		this.food_id = food_id;
		this.food_name = food_name;
		this.food_type = food_type;
		this.food_url = food_url;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getFood_description() {
		return food_description;
	}

	public void setFood_description(String food_description) {
		this.food_description = food_description;
	}

	public String getFood_id() {
		return food_id;
	}

	public void setFood_id(String food_id) {
		this.food_id = food_id;
	}

	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public String getFood_type() {
		return food_type;
	}

	public void setFood_type(String food_type) {
		this.food_type = food_type;
	}

	public String getFood_url() {
		return food_url;
	}

	public void setFood_url(String food_url) {
		this.food_url = food_url;
	}
}
