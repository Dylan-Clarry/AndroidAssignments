package com.example.androidassignments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindow";
    ArrayList<String> chatMsgs;

    // Inner chat adapter class
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        // returns number of rows in listView(number of strings in arraylist obj)
        public int getCount() {
            return chatMsgs.size();
        }

        // returns the item to show in the list at specified position
        public String getItem(int position) {
            return chatMsgs.get(position);
        }

        // returns the layout that will be positioned at the specified row in the list
        public View getView(int position, View convertView, ViewGroup parent) {

            // layout inflater
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null ;
            if(position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME,"on create");

        // Chat stuff
        final ListView chatListView = (ListView) findViewById(R.id.chatListView);
        final EditText chatEditText = (EditText) findViewById(R.id.chatEditText);
        final Button chatSendBtn = (Button) findViewById(R.id.chatSendBtn);
        chatMsgs = new ArrayList<String>();

        //in this case, “this” is the ChatWindow, which is-A Context object
        final ChatAdapter messageAdapter = new ChatAdapter( this );
        chatListView.setAdapter(messageAdapter);

        // db stuff
        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(getApplicationContext());

        //SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();

        // create cursor
        Cursor c = dbWrite.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME, null);
        c.moveToFirst();

        Log.i(ACTIVITY_NAME, "number of messages: " + String.valueOf(c.getCount()));

        while(!c.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL Message" + c.getString( c.getColumnIndex( ChatDatabaseHelper.COLUMN_ITEM) ) );
            String getStr = c.getString(c.getColumnIndex(ChatDatabaseHelper.COLUMN_ITEM));
            chatMsgs.add(getStr);
            c.moveToNext();
        }

        messageAdapter.notifyDataSetChanged();

        Log.i(ACTIVITY_NAME, "Cursor's column count = " + c.getColumnCount() );

        for(int i = 0; i < c.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "Column Name: " + c.getColumnName(i) );
        }

        // chat send button event listener
        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // get message from edit text and append to chat messages
                chatMsgs.add(chatEditText.getText().toString());

                ContentValues vals = new ContentValues();
                vals.put(dbHelper.KEY_MESSAGE, chatEditText.getText().toString());
                db.insert("Messages", "Message", vals);
                db.close();

                //this restarts the process of getCount()/getView()
                messageAdapter.notifyDataSetChanged();

                // reset text field
                chatEditText.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
