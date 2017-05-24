package org.appspot.apprtc;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatFragment extends Fragment {
    private static final String TAG = "Chat";

    private TextView    chatMessages;
    private EditText    chatMessage;
    private EditText    fileNameText;
    private Button      chatButton;
    private Button      chatSendFileButton;

    private OnChatEvents chatEvents;

    public interface OnChatEvents {
        void sendMessage(String message);
        void sendFile(String path);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatMessages = (TextView) view.findViewById(R.id.chat_messages);
        chatMessage = (EditText) view.findViewById(R.id.chat_message_text);
        fileNameText = (EditText) view.findViewById(R.id.chat_filename_text);
        chatButton = (Button) view.findViewById(R.id.chat_send_button);
        chatSendFileButton = (Button) view.findViewById(R.id.chat_send_file_button);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatMessage.getText().toString();
                Log.d(TAG, "Send message clicked with message:" + message);
                addOutComingMessage(message);
            }
        });

        chatSendFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatEvents.sendFile(getFilePath());
            }
        });

        return view;
    }

    public void addIncomingMessage(String message) {
        addMessage("-> " + message + "\n");
    }

    private void addOutComingMessage(String message) {
        addMessage("<- " + message + "\n");
        chatEvents.sendMessage(message);
    }

    private void addMessage(String message) {
        chatMessages.append(message);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        chatEvents = (OnChatEvents) context;
    }

    private String getFilePath() {
        //String file = Environment.getExternalStorageDirectory() + "/test.txt";
        String file = Environment.getExternalStorageDirectory() + "/" + fileNameText.getText().toString();
        return file;
    }
}































