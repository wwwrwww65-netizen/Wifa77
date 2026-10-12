package com.wi_fa.wifag;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseIDService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseIDService";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);
    }

    private void sendRegistrationToServer(String token) {

    }
}

