package group.comm.hacktainan.data;

public class Quest {
	public final int QID ;
	public final String NAME;
	public final String CONTENT;
	public final double LATITUDE;
	public final double LONGITUDE;
	
	public Quest(int qid,String name,String content, double latitude,double longtitude){
		QID =qid;
		NAME = name;
		CONTENT = content;
		LATITUDE = latitude;
		LONGITUDE = longtitude;

	}
	

}
