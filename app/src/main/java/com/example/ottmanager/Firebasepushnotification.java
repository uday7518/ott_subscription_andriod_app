package com.example.ottmanager;
import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
public class Firebasepushnotification extends FirebaseMessagingService {
    public Firebasepushnotification()
    {
        super();
    }@Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
    }
}
