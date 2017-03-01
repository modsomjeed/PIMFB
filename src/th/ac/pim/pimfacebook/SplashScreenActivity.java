package th.ac.pim.pimfacebook;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;

import com.facebook.Session;

public class SplashScreenActivity extends Activity {

	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		intent = new Intent();
		
		Session session = Session.getActiveSession();
        
        if (session != null) {
            if(session.isOpened()){
            	intent.setClass(this, MainActivity.class);
            }else{
            	intent.setClass(this, LoginActivity.class);
            }
        }else{
        	intent.setClass(this, LoginActivity.class);
        }

        Handler handler = new Handler();
		 
        // run a thread after 3 seconds to start the home screen
        handler.postDelayed(new Runnable() {
 
            @Override
            public void run() {
 
                // make sure we close the splash screen so the user won't come back when it presses back key
                finish();
                startActivity(intent);

            }
 
        }, 3000);
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}
	
//	public void logKeyHash() {
//		try {
//			PackageInfo info = getPackageManager().getPackageInfo(
//					getPackageName(), PackageManager.GET_SIGNATURES);
//			for (Signature signature : info.signatures) {
//				MessageDigest md = MessageDigest.getInstance("SHA");
//				md.update(signature.toByteArray());
//				Log.d("KeyHash",
//						Base64.encodeToString(md.digest(), Base64.DEFAULT));
//			}
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//	}

}
