package com.androidclientsocket;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity {
    Socket socket;


    EditText message;

    Button btnSendMessage;

    TextView receivedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            socket = IO.socket("http://192.168.1.35:3030");
            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        message = findViewById(R.id.message);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        receivedTextView = findViewById(R.id.received);

        btnSendMessage.setOnClickListener(v -> {
            socket.emit("FROM_ANDROID_EMIT", message.getText().toString());
        });


        socket.on("FROM_WEB_EMIT", args -> runOnUiThread(() -> {
            String receivedMessage = (String) args[0];
            receivedTextView.setText(receivedMessage);
        }));
    }


}