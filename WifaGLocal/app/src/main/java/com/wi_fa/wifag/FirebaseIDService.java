package com.wi_fa.wifag;

import android.app.Service;
import android.util.Log;


import com.google.firebase.iid.FirebaseInstanceId;


/**
 * Created by ahmedalalimi on 07/10/2017.
 */


public class FirebaseIDService extends com.google.firebase.iid.FirebaseInstanceIdService {

    private static final String TAG = "FirebaseIDService";

    @Override

    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }

    private void sendRegistrationToServer(String token) {

    }

}

