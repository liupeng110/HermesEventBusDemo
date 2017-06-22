package cn.wacxb.plugin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.wacxb.ipc.entity.MessageEvent;
import xiaofei.library.hermes.Hermes;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HermesEventBus.getDefault().register(this);
        mTextView = (TextView) this.findViewById(R.id.textView);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMainAppEvent(MessageEvent messageEvent) {
        Log.i("tag","接收到："+messageEvent.getMessage());
        Toast.makeText( MainActivity.this, "Plugin APP 中 接收到消息", Toast.LENGTH_SHORT ).show();
        mTextView.setText( mTextView.getText() + "\n" + messageEvent.getMessage() );
    }

    public void send(View view){
        HermesEventBus.getDefault().post( new MessageEvent("plugin里发送过来的消息！") );
    }


    @Override protected void onDestroy() {
        HermesEventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
