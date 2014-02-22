package com.androiddev.loriminorassignment2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

/**
 * @author Lori Minor
 * 
 *	This is the first activity. It will allow the user to either take a photo using the camera
 * or select an image from the gallery.  The image results will be sent to the next activity.
 */
public class ImageCaptureActivity extends Activity {

	private static final int RATING_BAR_RESULT = 6;
	private static final float DEFAULT_RATING = 0.0f;
	float MIN_RATING = 4;
	static final int IMAGE_GALLERY = 1;
	static final int CAMERA_RESULT = 2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_capture);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_capture, menu);
		return true;
	}

	/** This method will be invoked when user clicks the "Open Image Gallery" button.
	 * It will allow the user to select a photo from the image gallery.
	 * 	
	 * @param v
	 */
	public void onImageGalleryClick(View v){
		//allows user to select image from a gallery
		Intent selectFromGalleryIntent = new Intent(Intent.ACTION_PICK);

		//this path will point to the directory where images are stored
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();

		//converts to URI, gallery expects
		Uri imageDirectory = Uri.parse(path);

		//will set the data and type for this intent.  specifies where to look for files and which file types
		selectFromGalleryIntent.setDataAndType(imageDirectory, "image/*");

		//starts the activity and produces a result
		startActivityForResult(selectFromGalleryIntent,IMAGE_GALLERY);
	}

	/** This method will be invoked when user clicks the "Take a Photo" button.
	 * It will allow the user to take a photo, using the camera.
	 * 
	 * @param v
	 */
	public void onTakeAPhotoClicked(View v){

		//this implicit intent will invoke the camera
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

		startActivityForResult(cameraIntent, CAMERA_RESULT);
	}

	/**
	 * This method will be called when a result is received from the 
	 * image gallery.  It will also receive a result from the rating bar.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		//make sure that a good result was received
		if (resultCode == RESULT_OK){

			//if the result is received from selecting an image from the gallery
			if (requestCode == IMAGE_GALLERY ){

				//associating photoLocation with the Uri of the gallery image
				Uri photoLocation = data.getData();

				//Intent to transfer the photo to the next activity
				Intent transferPhoto = new Intent(ImageCaptureActivity.this, ImageInformationActivity.class);

				//transfers the data from the photoLocation Uri above
				transferPhoto.setData(photoLocation);

				startActivityForResult(transferPhoto, RATING_BAR_RESULT);

				//if a result is received from the rating bar
			}  else if (requestCode == RATING_BAR_RESULT){

				//get the user's rating from the rating bar
				float userRating = data.getFloatExtra("rating", DEFAULT_RATING);

				//if the user rates the photo as a 4 (MIN_RATING) or greater, then display Toast message
				if (userRating >= MIN_RATING){

					Toast.makeText(ImageCaptureActivity.this, "Glad you liked it!", Toast.LENGTH_LONG).show();

				}
			}
		}
	}

}
