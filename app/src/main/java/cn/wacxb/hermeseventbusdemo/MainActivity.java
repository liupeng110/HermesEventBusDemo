package cn.wacxb.hermeseventbusdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.wacxb.ipc.entity.MessageEvent;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView ;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // register
        HermesEventBus.getDefault().register(this);
        mTextView = (TextView) this.findViewById(R.id.textView);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getNextActivityMessage(NextMessage nextMessage){
        mTextView.setText( mTextView.getText() + "\n" + nextMessage.getMessage() );
    }

    public void goToNext(View view) {
        startActivity( new Intent(this, NextActivity.class) );
    }

    //向子activity发送数据
    public void sendEventToChildApp(View view) {
        HermesEventBus.getDefault().post( new MessageEvent("A message from main app !") );
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recive_plugin(MessageEvent messageEvent){
        mTextView.setText( mTextView.getText() + "\n" + messageEvent.getMessage() );
    }


    @Override protected void onDestroy() {
        // unregister
        HermesEventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}


