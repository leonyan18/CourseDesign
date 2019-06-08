package com.example.yan.coursedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.adapter.MsgAdapter;
import com.example.yan.coursedesign.bean.Msg;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<Msg>();

    private EditText inputText;

    private Button send;
    private int id;
    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;
    private ImageButton backbtn;
    private Intent resultIntent;
    private String name;
    private String headPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        headPic=intent.getStringExtra("headPic");
        id=intent.getIntExtra("id",1);
        initMsgs(); // 初始化消息数据
        backbtn=findViewById(R.id.backbtn);
        inputText =  findViewById(R.id.input_text);
        send =  findViewById(R.id.send);
        TextView textView=findViewById(R.id.chatWith);
        textView.setText(name);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList,headPic);
        msgRecyclerView.setAdapter(adapter);
        ImageButton backbtn=findViewById(R.id.backbtn);
        resultIntent=new Intent();
        resultIntent.putExtra("name",name);
        backbtn.setOnClickListener(view -> onBackPressed());
        send.setOnClickListener(v -> {
            String content = inputText.getText().toString();
            if (!"".equals(content)) {
                resultIntent.putExtra("content",content);
                Msg msg = new Msg(content, Msg.TYPE_SENT);
                msg.setTo(id);
                msg.setFrom(1);
                msg.save();
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
                msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行
                inputText.setText(""); // 清空输入框中的内容
            }
        });
        setResult(RESULT_OK,resultIntent);
    }
    private void initMsgs() {
        List<Msg> msgs= DataSupport.where("from = ? or to = ?", String.valueOf(id),String.valueOf(id)).find(Msg.class);
        for (Msg m:msgs) {
            if(m.getFrom()==1){
                m.setType(Msg.TYPE_SENT);
            }else{
                m.setType(Msg.TYPE_RECEIVED);
            }
        }
        msgList=msgs;
    }
}
