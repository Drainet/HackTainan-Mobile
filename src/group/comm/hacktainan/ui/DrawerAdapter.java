package group.comm.hacktainan.ui;





import group.comm.hacktainan.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class DrawerAdapter extends BaseAdapter{
	
	private LayoutInflater myInflater;
	int length;
    //CharSequence[] list = null;
    
    
    public DrawerAdapter(Context ctxt){
        myInflater = LayoutInflater.from(ctxt);
        
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		convertView = myInflater.inflate(R.layout.drawer_ui, null);
		
		ImageView image = (ImageView) convertView.findViewById(R.id.ImageView_icon);
		
		switch(position){
        case 0:
        	image.setImageResource(R.drawable.ic_launcher);
            break;
        case 1:
        	image.setImageResource(R.drawable.ic_launcher);
            break;
        case 2:
        	image.setImageResource(R.drawable.ic_launcher);
            break;
        case 3:
        	image.setImageResource(R.drawable.ic_launcher);
            break;
    }
		return convertView;
	}

}