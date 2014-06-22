package group.comm.hacktainan.data;

public class Quest {
	public final int QID ;
	public final String DESCRIPTION;
	public final double LATITUDE;
	public final double LONGITUDE;
	public final String CREATED_AT;
	public final Integer GOAL_LIKES;
	public final String TITLE;
	public final String UPDATE_AT;
	
	public Quest(String createAt,String des,Integer goalLikes,int qid, double latitude,double longtitude,String title,String updateAt){
		QID =qid;
		DESCRIPTION = des;
		LATITUDE = latitude;
		LONGITUDE = longtitude;
		CREATED_AT = createAt;
		GOAL_LIKES = goalLikes;
		TITLE = title;
		UPDATE_AT = updateAt;
	}
	

}
