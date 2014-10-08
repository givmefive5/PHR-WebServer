package phr.web.models;

public class Activity {
	
	Integer entryID;
	String name;
	double MET;
	int countUsed;
	
	public Activity(Integer entryID){
		super();
		this.entryID = entryID;
	}
	
	public Activity(Integer entryID, String name, double mET, int countUsed) {
		super();
		this.entryID = entryID;
		this.name = name;
		this.MET = mET;
		this.countUsed = countUsed;
	}
	
	public Activity(String name, double mET, int countUsed) {
		super();
		this.name = name;
		this.MET = mET;
		this.countUsed = countUsed;
	}


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

	public double getMET() {
		return MET;
	}

	public void setMET(double mET) {
		MET = mET;
	}

	public int getCountUsed() {
		return countUsed;
	}

	public void setCountUsed(int countUsed) {
		this.countUsed = countUsed;
	}
	
}
