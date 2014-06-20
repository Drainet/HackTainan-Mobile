package group.comm.hacktainan.data;

public final class Status {
	public final String NAME;
	public final String DATE;
	public final String IMAGE_URL;
	public final Integer PROGRESS;
	public final Integer GOOD_NUMBER;
	public Status(String name,String date,String imageURL,Integer progress,Integer goodNumber){
		NAME = name;
		DATE = date;
		IMAGE_URL = imageURL;
		PROGRESS = progress;
		GOOD_NUMBER = goodNumber;
	}
}
