package com.example.edunomics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.isExternalStorageLegacy;
import static com.example.edunomics.Open.CurrEmail;
import static com.example.edunomics.Open.CurrRoom;

public class ChatActivity extends AppCompatActivity {
    MessageAdapter mAdapter;
    EditText msg;
    ArrayList<Messsage>l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        TextView textView = findViewById(R.id.CurrCourse);
        textView.setText(CurrRoom);
        msg = findViewById(R.id.msg);
        l = new ArrayList<>();
        mAdapter = new MessageAdapter(l,this);
        receieve();
    }

    private void receieve() {
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child(CurrRoom);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                l.clear();
                for(DataSnapshot dsnp : dataSnapshot.getChildren()){
                    l.add(dsnp.getValue(Messsage.class));
                }
                RecyclerView r = findViewById(R.id.recycerMsg);
                LinearLayoutManager llm = new LinearLayoutManager(ChatActivity.this);
                llm.setStackFromEnd(true);
                r.setLayoutManager(llm);
                mAdapter.notifyDataSetChanged();
                mAdapter = new MessageAdapter(l,ChatActivity.this);
                r.setAdapter(mAdapter);
                r.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void send(View view) {
        String MSG = msg.getText().toString();
        if(MSG == "")return;
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child(CurrRoom);
        String s = dref.push().getKey();
        Messsage m = new Messsage();
        m.setEmail(CurrEmail);
        m.setMsg(MSG);
        dref.child(s).setValue(m);
        msg.setText("");
        receieve();
    }

    class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
        ArrayList<Messsage> list;
        Context ctx;

        public MessageAdapter(){
        }

        public MessageAdapter(ArrayList<Messsage> list, Context ctx) {
            this.list = list;
            this.ctx = ctx;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType == 0) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sample_msg_r,parent,false);
                return  new ViewHolder(v);
            }
            else{
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sample_msg_s,parent,false);
                return  new ViewHolder(v);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(list.get(position).getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                return 0;
            }
            else
            {
                return 1;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            String msg = list.get(position).getMsg();
            TextView txt = holder.itemView.findViewById(R.id.MSG);
            txt.setText(msg);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

}
