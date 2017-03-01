package th.ac.pim.pimfacebook;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Session.NewPermissionsRequest;

public class LoginActivity extends Activity implements OnClickListener {

	private ImageButton fbLoginButton;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private Intent intent;
	
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		fbLoginButton = (ImageButton) findViewById(R.id.fbLoginButton);
		fbLoginButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Session session = Session.getActiveSession();
		if (session != null) {
			if (!session.isOpened() && !session.isClosed()) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			} else {
				Session.openActiveSession(this, true, statusCallback);
			}
		} else {
			Session.openActiveSession(this, true, statusCallback);
		}

	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (session.isOpened()) {
				session.requestNewPublishPermissions(new NewPermissionsRequest(LoginActivity.this, Arrays.asList("publish_actions","publish_stream")));
//				Session.OpenRequest request = new Session.OpenRequest(LoginActivity.this);
//                request.setPermissions(Arrays.asList("publish_actions","publish_stream"));
            
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	@Override
	public void onStart() {
		super.onStart();
		if(Session.getActiveSession() != null){
			Session.getActiveSession().addCallback(statusCallback);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if(Session.getActiveSession() != null){
			Session.getActiveSession().removeCallback(statusCallback);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
