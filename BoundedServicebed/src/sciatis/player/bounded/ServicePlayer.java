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
	private MediaPlayer player = null;//������ ���
	
	public ServicePlayer() {
		super();
	}

	//���� ����
    //������ ����� ���� ����� ��� ������ ������
	public final class LocalBinder extends Binder
	{
		public ServicePlayer getPlayerService()
		{
			// �� ����� ������� ���� ���� ������
			return ServicePlayer.this;
		}
	}
	// ���� ������ �����
	private final LocalBinder binder = new LocalBinder();
	
	// ������ �����
	@Override
	public IBinder onBind(Intent intent) 
	{
		return binder;
	}
	
	@Override	
	public void onCreate() 
	{
		super.onCreate();
		initPlayer();//����� ������ ���
	}

	//����� ������ ���
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

	//����� ���� �� ���
	public void startPlay() 
	{
	    if(!player.isPlaying())
		{
			player.start();
		}
	}
	
	//����� ����
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
	
	//���� ���� ������ ����� ������ �����
	// ����� ��� ��� �� ����� ������ ����� ���
	@Override
	public void onDestroy() 
	{
		//����� ���� ������� ������ ����
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
	// ������� �� ��� ����� �� ����� �� ���� ����� ����
	//����� ���� ����� ���� ����
	@Override
	public void onCompletion(MediaPlayer mp) 
	{
		initPlayer();
	}

}





























		