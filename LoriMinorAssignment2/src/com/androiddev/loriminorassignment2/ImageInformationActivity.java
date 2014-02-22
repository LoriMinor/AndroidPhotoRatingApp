package com.androiddev.loriminorassignment2;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * This activity will receive a photo that the user selects from the gallery in the Image Capture activity.  
 * The user will be provided with a rating bar in order to give the photo a rating of zero through five.
 * Also, the details of the image file will be displayed to the user.
 * 
 * @author Lori Minor
 *
 */
public class ImageInformationActivity extends Activity {

	static ImageView imgDisplayImage;
	RatingBar rbRateImage;
	Button btnSubmit;
	private Bitmap selectedImage;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_information);

		//this is for the image view that will display photo thumbnail
		imgDisplayImage = (ImageView) findViewById(R.id.imgDisplayImage);

		//this will get image Uri from gallery photo and display it in the image view
		Uri imageUri = getIntent().getData();

		try {
			//stream of data from file
			InputStream openInputStream = getContentResolver().openInputStream(imageUri);

			//converts the stream of data to a bitmap
			selectedImage = BitmapFactory.decodeStream(openInputStream);

			//sets the image view to the selected image
			imgDisplayImage.setImageBitmap(selectedImage);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*The text views below will display the image file details to the user. */

		//display file path
		TextView txtFilePath = (TextView) findViewById(R.id.txtFilePath);
		String imagePath = imageUri.getPath();
		txtFilePath.setText("" + imagePath);

		//display file width
		TextView txtFileWidth = (TextView) findViewById(R.id.txtFileWidth);  
		int imageWidth = selectedImage.getWidth();
		txtFileWidth.setText("" + imageWidth);

		//display file height
		TextView txtFileHeight = (TextView) findViewById(R.id.txtFileHeight);
		int imageHeight = selectedImage.getHeight();
		txtFileHeight.setText("" + imageHeight);

		//display file size
		TextView txtImageFileSize = (TextView) findViewById(R.id.txtImageFileSize);
		int imageFileSize = selectedImage.getByteCount();
		txtImageFileSize.setText("" + imageFileSize);


		/* The rating bar will allow users to rate photos */
		final RatingBar rbRateImage = (RatingBar) findViewById(R.id.rbRatePhoto);

		//initialize the submit button
		Button btnSubmit = (Button) findViewById(R.id.btnSubmit);

		//setup listener for the submit button	
		btnSubmit.setOnClickListener(new OnClickListener() {

			/**
			 * Method will be invoked when Submit button is clicked
			 */
			@Override
			public void onClick(View v) {

				//get rating from the rating bar
				getIntent().putExtra("rating", rbRateImage.getRating());

				setResult(RESULT_OK, getIntent());

				//finish activity
				finish();
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_information, menu);
		return true;
	}


}
