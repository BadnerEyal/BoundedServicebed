package sciatis.player.bounded;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;

/**
 * This  example was created by Sciatis Technologies and belongs to.
 * 
 * Using this samples for teaching/training or distribution requires written approval from Sciatis Technologies.
 * 
 * Sciatis Technologies will not allow the use of this examples besides than development. 
 * 
 * For any questions please contact Gabriel@proto-mech.com
 * 
 * @author Gabriel@proto-mech.com
 */
public class ServicePlayer extends Service implements OnCompletionListener
{
	private MediaPlayer player = null;//אוביקט נגן
	
	public ServicePlayer() {
		super();
	}

	//אינר קלאס
    //אוביקט בינאר אותו נעביר למי שיתחבר לשירות
	public final class LocalBinder extends Binder
	{
		public ServicePlayer getPlayerService()
		{
			// זה הטעות מעבירים גישה מלאה לשירות
			return ServicePlayer.this;
		}
	}
	// יצרת אוביקט בינאר
	private final LocalBinder binder = new LocalBinder();
	
	// מחזרים בינאר
	@Override
	public IBinder onBind(Intent intent) 
	{
		return binder;
	}
	
	@Override	
	public void onCreate() 
	{
		super.onCreate();
		initPlayer();//אתחול אוביקט נגן
	}

	//אתחול אוביקט נגן
	private void initPlayer() 
	{
		if(player != null)
		{
			if(player.isPlaying())
			{
				player.stop();
			}
			player.release();
			player = null;
		}
			
		player = MediaPlayer.create(this, R.raw.rihana_disturbia);
		player.setOnCompletionListener(this);
	}

	//הפעלת הנגן עם שיר
	public void startPlay() 
	{
	    if(!player.isPlaying())
		{
			player.start();
		}
	}
	
	//עצירת הנגן
	public void stopPlay() 
	{
		if(player != null)
		{
		   if (player.isPlaying()) 
		   {
			   player.stop();
		   }
			player.release();
		}
		
		player = null;
		initPlayer();
	}
	
	//בזמן סיום השירות דברים שאלינו לעשות
	// במקרה שאף אחד לא בינאר לשירות ניכנס לפה
	@Override
	public void onDestroy() 
	{
		//הפסקת הנגן ושיחורר אוביקט הנגן
		if(player != null)
		{
		   if (player.isPlaying()) 
		   {
			   player.stop();
		   }
		   player.release();
		   player = null;

		}
		super.onDestroy();
	}

	//implements OnCompletionListener
	// פונקציה זו היא מימוש של מאזין מה יהיה בסיום השיר
	//בסיום השיר נאתחל אותו מחדש
	@Override
	public void onCompletion(MediaPlayer mp) 
	{
		initPlayer();
	}

}





























		