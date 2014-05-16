package sciatis.player.bounded;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
 *
 *דוגמא להפעלת שירות 
 *אשר מפעילה נגן מוזיקה מנגנת שיר 
 *הדוגמא לא נכונה כי אנו מקבלים אלינו את כל הפונקציות של השירות
 *ויתכן שמי שמחובר יפסיק למשל את השירות לכולם 
 *לכן צריך לעשות אינטרפיס ומי שיהיה מחובר יראה רק את הפונקציות שיוכל להפעיל
 *סדר פעולות
 *שליחה בקשה לקבלת שירות 
 *נשלח תיקשורת למערכת ההפעלה במקרה שיש תקשורת נקבל בינדר דרכו אפשר לגשת לשירות
 *
 *הערה
 *כל זמן שמישהו בינאר לשירות אז הוא יפעל ברקע
 *כאשר אף אחד לא מחובר אליו יותר אז הוא ימות
 *לכן חשוב לשחרר תמיד בסוף התוכנית
 *
 */
//שולחים תקשורת פעם ראשונה שקוראים לשירות
	//למערכת ההפעלה כך היא תדע למי לקשר את השירות מי ביקש ממנה
	//במקרה שהתקשורת הצליחה נקבל בינאר איתו 
	//או דרכו אפשר להפעיל את הפונקציות של השירות
public class PlayerActivity extends Activity
{
	//משתנים
	private boolean isBounded = false;//האם מחובר
	private ServicePlayer playerService = null;//אוביקט השירות דרכו נפעיל פונקציות בשירות
	private PlayerServiceConnection playerServiceConnection = null;//תקשורת
	
	
	//2. תקשורת נשלח תקשורת למערכת הפעלה
	private class PlayerServiceConnection implements ServiceConnection
	{
		//במקרה שהתקשורת הצליחה
		//נקבל אלינו אוביקט בינאר למשתנה שלנו דרכו נפעיל פונקציות בשירות
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			playerService = ((ServicePlayer.LocalBinder)binder).getPlayerService();
			isBounded = true;//מחובר
		}
      //במקרה שהתקשורת נכשלה
		@Override
		public void onServiceDisconnected(ComponentName name) {
			isBounded = false; //לא מחובר
		}
	}
	
	@Override
	protected void onStart() 
	{
		//1
        //כאשר התוכנית מתחילה נשלח בקשת התקשרות וחיבור לשירות		
		super.onStart();
		Intent playerServiceIntent = new Intent(this, ServicePlayer.class);
		//bindService(service, conn, flags)
		bindService(playerServiceIntent, playerServiceConnection, Context.BIND_AUTO_CREATE);
	}
	
	//ביצאה מהתוכנית נשחרר את השירות
	//בגלל שאנו משתמשים יחדים פעולה זו גם תסגור את השירות
	@Override
	protected void onStop() 
	{
		super.onStop();
		if(isBounded)
		{
			unbindService(playerServiceConnection);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		playerServiceConnection = new PlayerServiceConnection();
		
		Button playerStartButton = (Button) findViewById(R.id.serviceStartButton);
		playerStartButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
               // אם אנחנו מקושרים ויש לנו אוביקט
				if(isBounded && playerService != null)
				{
					//הפעל פונקציה בשירות
					playerService.startPlay();
				}
			}
		});
		
		Button playerStopButton = (Button) findViewById(R.id.serviceStopButton);
		playerStopButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{

				if(isBounded && playerService != null)
				{    //הפעל פונקציה בשירות
					playerService.stopPlay();
				}
			}
		});
		
		
	}

	
	
	
	
	

}
