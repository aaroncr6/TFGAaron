package es.tfg.gestorrestaurante;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutTfgActivity extends AppCompatActivity {

    WebView videoViewAbout;
    TextView txtAbout;

    Button btnGitHubAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_tfg);

        videoViewAbout = findViewById(R.id.videoView_AboutTfg);
        txtAbout = findViewById(R.id.txt_AboutTfg);
        btnGitHubAbout = findViewById(R.id.btnGitHub_AboutTfg);

        WebSettings webSettings = videoViewAbout.getSettings();
        webSettings.setJavaScriptEnabled(true);

        videoViewAbout.setWebViewClient(new WebViewClient());

        String videoUrl = "https://youtube.com/shorts/Yo8X5vrP9AQ?si=oeMa_hB-JZw4Myz8";
        videoViewAbout.loadUrl(videoUrl);


        btnGitHubAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://github.com/aaroncr6/TFGAaron/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}