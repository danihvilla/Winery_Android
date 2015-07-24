package biz.agbo.baccus.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import biz.agbo.baccus.R;
import biz.agbo.baccus.model.Wine;

public class WebActivity extends AppCompatActivity {

    private static final String STATE_URL = "url";

    // Modelo
    private Wine mWine = null;

    // Vistas
    private WebView mBrowser = null;
    private ProgressBar mLoading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);

        // Creamos el modelo
        mWine = new Wine(
                "Bembibre",
                "Tinto",
                R.drawable.vegaval,
                "Dominio de Tares",
                "http://www.dominiodetares.com/portfolio/bembibre/",
                "Este vino muestra toda la complejidad y la elegancia de la variedad Mencía. En fase visual luce un color rojo picota muy cubierto con tonalidades violáceas en el menisco. En nariz aparecen recuerdos frutales muy intensos de frutas rojas (frambuesa, cereza) y una potente ciruela negra, así como tonos florales de la gama de las rosas y violetas, vegetales muy elegantes y complementarios, hojarasca verde, tabaco y maderas aromáticas (sándalo) que le brindan un toque ciertamente perfumado.",
                "El Bierzo",
                5);
        mWine.addGrape("Mencía");

        // Asocio vista y controlador
        mBrowser = (WebView) findViewById(R.id.browser);
        mLoading = (ProgressBar) findViewById(R.id.loading);

        // Configuro vistas
        mBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoading.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mLoading.setVisibility(View.GONE);
            }
        });

        mBrowser.getSettings().setJavaScriptEnabled(true);
        mBrowser.getSettings().setBuiltInZoomControls(true);

        // Cargo la página web

        if (savedInstanceState == null || !savedInstanceState.containsKey(STATE_URL)) {
            mBrowser.loadUrl(mWine.getCompanyWeb());
        }
        else {
            mBrowser.loadUrl(savedInstanceState.getString(STATE_URL));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_URL, mBrowser.getUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_web, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_reload){
            mBrowser.reload();

            return true;
        }
        return false;
    }
}
