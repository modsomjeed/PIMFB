package th.ac.pim.pimfacebook;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView userName;
	private ProfilePictureView pictureProfile;
	private Button postMessage;
	private Button postPicture;
	private EditText message;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		userName = (TextView) findViewById(R.id.userName);
		pictureProfile = (ProfilePictureView) findViewById(R.id.picture_profile);
		postMessage = (Button) findViewById(R.id.post_message);
		postPicture = (Button) findViewById(R.id.post_picture);
		message = (EditText) findViewById(R.id.message);
		
		postMessage.setOnClickListener(this);
		postPicture.setOnClickListener(this);
		
		Session session = Session.getActiveSession();
		
		if (session.isOpened()) {

			// make request to the /me API
			Request.executeMeRequestAsync(session,
					new Request.GraphUserCallback() {

						// callback after Graph API response with user
						@Override
						public void onCompleted(GraphUser user,Response response) {
							if (user != null) {
								userName.setText(user.getName());
								pictureProfile.setProfileId(user.getId());
							}
						}
					});
		}
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,resultCode, data);
	}

	@Override
	public void onClick(View v) {
		Session session = Session.getActiveSession();
		switch(v.getId()){
		case R.id.post_message:
			//Toast.makeText(this, "post_message", Toast.LENGTH_LONG).show();
			Request.executeStatusUpdateRequestAsync(session, message.getText().toString(), new Request.Callback() {
				@Override
				public void onCompleted(Response response) {
					if(response.getError() == null){
						Toast.makeText(MainActivity.this, "Post your message success", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(MainActivity.this, "Post your message success", Toast.LENGTH_SHORT).show();
					}
				}
			});
			break;
		case R.id.post_picture:
			//Toast.makeText(this, "post_picture", Toast.LENGTH_LONG).show();
			Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.sp);
            Request.executeUploadPhotoRequestAsync(session, image, new Request.Callback() {
				
				@Override
				public void onCompleted(Response response) {
					if(response.getError() == null){
						Toast.makeText(MainActivity.this, "Post your picture success", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(MainActivity.this, "Post your picture success", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
			break;
		}
	}

}
