package com.example.android.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.app.ShareCompat.IntentBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This metha map to the location represented by the variable addressString using implicit Intents.
     *
     * @param v Button that was clicked.
     */
    public void onClickOpenAddressButton(View v) {
        String addressString = "1600 Amphitheatre Parkway, CA";

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("geo")
                .path("0,0")
                .query(addressString);
        Uri addressUri = builder.build();

        showMap(addressUri);
    }

    /**
     * This method is called when the Share Text Content button is clicked. It will simply share
     * the text contained within the String textThatYouWantToShare.
     *
     * @param v Button that was clicked.
     */
    public void onClickShareTextButton(View v) {
   
        String textThatYouWantToShare =
                "Sharing the coolest thing I've learned so far. You should " +
                        "check out Udacity and Google's Android Nanodegree!";
        shareText(textThatYouWantToShare);
    }

    /**
     * This is where you will create and fire off your own implicit Intent. Yours will be very
     * similar to what I've done above. You can view a list of implicit Intents on the Common
     * Intents page from the developer documentation.
     *
     * @see <http://developer.android.com/guide/components/intents-common.html/>
     *
     * @param v Button that was clicked.
     */
    public void createYourOwn(View v) {
        Toast.makeText(this,
                "TODO: Create Your Own Implicit Intent",
                Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * This method fires off an implicit Intent to open a webpage.
     *
     * @param url Url of webpage to open. Should start with http:// or https:// as that is the
     *            scheme of the URI expected with this Intent according to the Common Intents page
     */
    private void openWebPage(String url) {
     
        Uri webpage = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method will fire off an implicit Intent to view a location on a map.
     *
     * When constructing implicit Intents, you can use either the setData method or specify the
     * URI as the second parameter of the Intent's constructor,
     * as I do in {@link #openWebPage(String)}
     *
     * @param geoLocation The Uri representing the location that will be opened in the map
     */
    private void showMap(Uri geoLocation) {
       
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method shares text and allows the user to select which app they would like to use to
     * share the text. Using ShareCompat's IntentBuilder, we get some really cool functionality for
     * free. The chooser that is started using the {@link IntentBuilder#startChooser()} method will
     * create a chooser when more than one app on the device can handle the Intent. This happens
     * when the user has, for example, both a texting app and an email app. If only one Activity
     * on the phone can handle the Intent, it will automatically be launched.
     *
     * @param textToShare Text that will be shared
     */
    private void shareText(String textToShare) {
     
        String mimeType = "text/plain";
        String title = "Learning How to Share";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }
}