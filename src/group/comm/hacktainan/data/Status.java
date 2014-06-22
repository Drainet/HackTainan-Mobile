package group.comm.hacktainan.data;

public final class Status {
	public final String NAME;
	public final String TITLE;
	public final String DATE;
	public final String DESCRIPTION;
	public final String IMAGE_URL;
	public final Integer PROGRESS;
	public final Integer GOOD_NUMBER;
	public Status(String name,String title,String date,String description,String imageURL,Integer progress,Integer goodNumber){
		NAME = name;
		TITLE = title;
		DATE = date;
		DESCRIPTION = description;
		IMAGE_URL = imageURL;
		PROGRESS = progress;
		GOOD_NUMBER = goodNumber;
	}
}
