package com.noname.vanishingviews;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout root;
    int childCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.cl_root);
        childCount = root.getChildCount();
        new VanishingAsyncTask().execute();
    }

    private class VanishingAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
        }

        private void toggleVisible(final int index, final boolean visible) {
            new Handler(MainActivity.this.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (!visible)
                        root.getChildAt(index).setVisibility(View.INVISIBLE);
                    else
                        root.getChildAt(index).setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int count = 0;
            do {
                for (int i = 0; i < childCount; i++) {
                    runVisibility(i);
                }
                count++;
            } while (count < 30);


            return null;
        }

        private void runVisibility(int index) {
            toggleVisible(index, false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            toggleVisible(index, true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
        }
    }


}
