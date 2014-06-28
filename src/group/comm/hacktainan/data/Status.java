package group.comm.hacktainan.data;

public final class Status {
	public final Integer ID;
	public final String NAME;
	public final String DATE;
	public final String TITLE;
	public final String CONTENT;
	public final String IMAGE;
	public Integer LIKES;
	public final Integer GOAL;
	public Status(
			Integer id,
			String name,
			String date,
			String title,
			String content,
			String image,
			Integer likes,
			Integer goal){
		ID = id;
		NAME = name;
		DATE = date;
		TITLE = title;
		CONTENT = content;
		IMAGE = image;
		LIKES = likes;
		GOAL = goal;
		

	}
}
