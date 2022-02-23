package com.example.jm9dvr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static Context context;

    private int moveNum=4; private int gameNum=87;
    private int gamepageNum=1;
    private int movepageNum=1;
    private int gamelieNum=1;
    private int movelieNum=1;

    private int kongbugamepageNum=1;
    private int hangtiangamepageNum=1;
    private int jixiangamepageNum=1;
    private int haiyanggamepageNum=1;
    private int kepugamepageNum=1;

    private String image="game";
    private RadioGroup who_group = null;
    private LinearLayout cheackboxip=null;
    private RadioButton IP1 = null;
    private RadioButton IP2 = null;
    private RadioButton IP3 = null;
    private RadioButton IP4 = null;
    private RadioButton IP5 = null;
    private RadioButton IP6 = null;
    private CheckBox CB1 = null;
    private CheckBox CB2 = null;
    private CheckBox CB3 = null;
    private CheckBox CB4 = null;
    private CheckBox CB5 = null;
    private CheckBox CB6 = null;


    private int Checkednum=1;
    private Button qidong;
    private  EditText password;
    private Button changeip;
    private  EditText edit_ip;
    private  EditText text_search;
    private TextView UDPbuffer;
    private Button bt_move; private Button bt_game;
    private Button bt_qianjin; private Button bt_houtui;
    private Button bt_start; private Button bt_close;Button bt_stop;
    private ImageView search;private ImageView setup;
    private ImageView shangyiye;private ImageView xiayiye;
    private ImageView selected_imgae;
    private ImageView close;
    private ImageView game1;private ImageView game2;private ImageView game3;
    private String gameid="game"+"01";private String moveid="move"+"01";
    private int pagenum=0;

    private String gameMulu="allgame";
    private Button allgame;private Button kongbu;
    private Button jixian;private Button haiyang;private Button kepu;
    List<Integer> kongbugame = new ArrayList<Integer>();
    List<Integer> hangtiangame = new ArrayList<Integer>();
    List<Integer> jixiangame = new ArrayList<Integer>();
    List<Integer> haiyanggame = new ArrayList<Integer>();
    List<Integer> kepugame = new ArrayList<Integer>();

    List<String> kongbugameName = new ArrayList<String>();
    List<String> hangtiangameName = new ArrayList<String>();
    List<String> jixiangameName = new ArrayList<String>();
    List<String> haiyanggameName = new ArrayList<String>();
    List<String> kepugameName = new ArrayList<String>();
//String []gmmulu=new String[]{"allgame","kongbu","hangtian","jixian","haiyang","kepu"};
    private static final int SEND_SMS = 100;
    public static String[] Serial=new String[]{"123456789","","","",""};
    String SERVERIP="192.168.1.146";//目标接收端的ip地址
    DatagramSocket Socket;
    int DuanKouShou = 60000;        //目标接收端口号,用来接收数据的端口，假设端口号为60000
    int DuanKouFa = 60001;            //本机端口号,用来发送的端口，假设端口号为66001

    int SERVERPORT=10009;//目标接收端口号
    int iport=30000;//本机端口号
    String UDPBuf="";
    String UDPBufA="";
    String UDPsendBuf="IPAD(00,game1,01)";
    private StringBuffer udpRcvStrBuf=new StringBuffer(),udpSendStrBuf=new StringBuffer();
    private boolean PCBclose=true;
    private static final int CONNECT_SUCCESS = 0x01;
    private static final int CONNECT_FAILURE = 0x02;
    private static final int DISCONNECT_SUCCESS = 0x03;
    private static final int SEND_SUCCESS = 0x04;
    private static final int SEND_FAILURE= 0x05;
    private static final int RECEIVE_SUCCESS= 0x06;
    private static final int RECEIVE_FAILURE =0x07;


    private Handler handlerTime=new Handler();

    private Runnable myRunnable=new Runnable() {
        @Override
        public void run() {
          //  Log.d("TAG","myRunnable");
            //间隔时间循环执行
            handlerTime.postDelayed(myRunnable,10000);
            closeApp();

        }
    };
    String recevieIP="";
    private Handler handler=new Handler(){
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            String receviemsg=(String) msg.obj;
            UDPBuf +="\n"+ (String) msg.obj;
            UDPbuffer.setText(UDPBuf);

            if (receviemsg.contains("name NULL")){
                bt_start.setEnabled(true);
                Toast.makeText(MainActivity.this,"播放器暂无此影片！", Toast.LENGTH_SHORT).show();
            }
           // Toast.makeText(MainActivity.this,"IPList:"+IP, Toast.LENGTH_LONG).show();
            Log.v("fist", "UDPBufA: "  + UDPBufA );
            if(!UDPBufA.equals("")) {
                for (int i=0;i<IP.size();i++){

                    if(!UDPBufA.contains(IP.get(i).toString()))
                    {
                        PCBclose=false;
                        recevieIP+=" 设备IP："+IP.get(i)+" ";
                    }
                }
            }else{
                Toast.makeText(MainActivity.this,"控制台IP:"+SERVERIP+",控制台播放器未打开！", Toast.LENGTH_LONG).show();
            }

            if (!PCBclose){
                Toast.makeText(MainActivity.this,recevieIP+"播放器未打开！", Toast.LENGTH_LONG).show();
                recevieIP="";
            }

        }
    };

    Thread TSend;  //发送线程，相当于建立一个Android端用于发送数据的平台
    Thread threadRece;	//接收线程，同上
    boolean recrState=true;
    List<String> game = new ArrayList<String>();
    List<String> move = new ArrayList<String>();
    List<String> IP = new ArrayList<String>();
    int gamenum=1; int movenum=1;
    int countCB=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//不熄屏
        super.onCreate(savedInstanceState);
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//始终竖屏
     //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
      /*   if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
     */
        initPermission();
        setContentView(R.layout.activity_main);
        context=this;
        initView();
        initData();
        bt_move.setEnabled(false);
        bt_close.setEnabled(false);
        bt_start.setEnabled(false);
        password.requestFocus();  //请求获取焦点

        //  limited();
       // .....//sss.avi 播放  name NULL

    }

    /**'
     * android 6.0 以上需要动态申请权限'
     */
    private void initPermission() {
        String permissions[] = {android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.CHANGE_NETWORK_STATE,
                android.Manifest.permission.INTERNET,
                Manifest.permission.CALL_PHONE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }
    private void initData(){
        IP.add(getString(R.string.ip1));
        IP.add(getString(R.string.ip2));
        IP.add(getString(R.string.ip3));
        IP.add(getString(R.string.ip4));
        IP.add(getString(R.string.ip5));
        IP.add(getString(R.string.ip6));
        game.add(getString(R.string.game1));
        game.add(getString(R.string.game2));
        game.add(getString(R.string.game3));
        game.add(getString(R.string.game4));
        game.add(getString(R.string.game5));
        game.add(getString(R.string.game6));
        game.add(getString(R.string.game7));
        game.add(getString(R.string.game8));
        game.add(getString(R.string.game9));
        game.add(getString(R.string.game10));
        game.add(getString(R.string.game11));
        game.add(getString(R.string.game12));
        game.add(getString(R.string.game13));
        game.add(getString(R.string.game14));
        game.add(getString(R.string.game15));
        game.add(getString(R.string.game16));
        game.add(getString(R.string.game17));
        game.add(getString(R.string.game18));
        game.add(getString(R.string.game19));
        game.add(getString(R.string.game20));
        game.add(getString(R.string.game21));
        game.add(getString(R.string.game22));
        game.add(getString(R.string.game23));
        game.add(getString(R.string.game24));
        game.add(getString(R.string.game25));
        game.add(getString(R.string.game26));
        game.add(getString(R.string.game27));
        game.add(getString(R.string.game28));
        game.add(getString(R.string.game29));
        game.add(getString(R.string.game30));
        game.add(getString(R.string.game31));
        game.add(getString(R.string.game32));
        game.add(getString(R.string.game33));
        game.add(getString(R.string.game34));
        game.add(getString(R.string.game35));
        game.add(getString(R.string.game36));
        game.add(getString(R.string.game37));
        game.add(getString(R.string.game38));
        game.add(getString(R.string.game39));
        game.add(getString(R.string.game40));
        game.add(getString(R.string.game41));
        game.add(getString(R.string.game42));
        game.add(getString(R.string.game43));
        game.add(getString(R.string.game44));
        game.add(getString(R.string.game45));
        game.add(getString(R.string.game46));
        game.add(getString(R.string.game47));
        game.add(getString(R.string.game48));
        game.add(getString(R.string.game49));
        game.add(getString(R.string.game50));
        game.add(getString(R.string.game51));
        game.add(getString(R.string.game52));
        game.add(getString(R.string.game53));
        game.add(getString(R.string.game54));
        game.add(getString(R.string.game55));
        game.add(getString(R.string.game56));
        game.add(getString(R.string.game57));
        game.add(getString(R.string.game58));
        game.add(getString(R.string.game59));
        game.add(getString(R.string.game60));
        game.add(getString(R.string.game61));
        game.add(getString(R.string.game62));
        game.add(getString(R.string.game63));
        game.add(getString(R.string.game64));
        game.add(getString(R.string.game65));
        game.add(getString(R.string.game66));
        game.add(getString(R.string.game67));
        game.add(getString(R.string.game68));
        game.add(getString(R.string.game69));
        game.add(getString(R.string.game70));
        game.add(getString(R.string.game71));
        game.add(getString(R.string.game72));
        game.add(getString(R.string.game73));
        game.add(getString(R.string.game74));
        game.add(getString(R.string.game75));
        game.add(getString(R.string.game76));
        game.add(getString(R.string.game77));
        game.add(getString(R.string.game78));
        game.add(getString(R.string.game79));
        game.add(getString(R.string.game80));
        game.add(getString(R.string.game81));
        game.add(getString(R.string.game82));
        game.add(getString(R.string.game83));
        game.add(getString(R.string.game84));
        game.add(getString(R.string.game85));
        game.add(getString(R.string.game86));
        game.add(getString(R.string.game87));
     //目录  恐怖，航天，极限，海洋，科普
         kongbugame.add(R.drawable.game22);
         kongbugame.add(R.drawable.game27);
         kongbugame.add(R.drawable.game33);
         kongbugame.add(R.drawable.game41);
         kongbugame.add(R.drawable.game50);

         //GameNmae
        kongbugameName.add(getString(R.string.game22));
        kongbugameName.add(getString(R.string.game27));
        kongbugameName.add(getString(R.string.game33));
        kongbugameName.add(getString(R.string.game41));
        kongbugameName.add(getString(R.string.game50));


         hangtiangame.add(R.drawable.game14);
         hangtiangame.add(R.drawable.game16);
         hangtiangame.add(R.drawable.game23);
         hangtiangame.add(R.drawable.game31);
         hangtiangame.add(R.drawable.game34);
         hangtiangame.add(R.drawable.game38);
         hangtiangame.add(R.drawable.game49);
         hangtiangame.add(R.drawable.game52);
        //GameNmae
        hangtiangameName.add(getString(R.string.game14));
        hangtiangameName.add(getString(R.string.game16));
        hangtiangameName.add(getString(R.string.game23));
        hangtiangameName.add(getString(R.string.game31));
        hangtiangameName.add(getString(R.string.game34));
        hangtiangameName.add(getString(R.string.game38));
        hangtiangameName.add(getString(R.string.game49));
        hangtiangameName.add(getString(R.string.game52));


         jixiangame.add(R.drawable.game1);
         jixiangame.add(R.drawable.game2);
         jixiangame.add(R.drawable.game3);
         jixiangame.add(R.drawable.game7);
         jixiangame.add(R.drawable.game9);
         jixiangame.add(R.drawable.game10);
         jixiangame.add(R.drawable.game11);
        jixiangame.add(R.drawable.game18);
        jixiangame.add(R.drawable.game19);
        jixiangame.add(R.drawable.game20);
        jixiangame.add(R.drawable.game22);
        jixiangame.add(R.drawable.game26);
        //GameNmae
        jixiangameName.add(getString(R.string.game1));
        jixiangameName.add(getString(R.string.game2));
        jixiangameName.add(getString(R.string.game3));
        jixiangameName.add(getString(R.string.game7));
        jixiangameName.add(getString(R.string.game9));
        jixiangameName.add(getString(R.string.game10));
        jixiangameName.add(getString(R.string.game11));
        jixiangameName.add(getString(R.string.game18));
        jixiangameName.add(getString(R.string.game19));
        jixiangameName.add(getString(R.string.game20));
        jixiangameName.add(getString(R.string.game22));
        jixiangameName.add(getString(R.string.game26));

        haiyanggame.add(R.drawable.game21);
         haiyanggame.add(R.drawable.game39);
         haiyanggame.add(R.drawable.game40);
         haiyanggame.add(R.drawable.game42);
        haiyanggame.add(R.drawable.game44);
        haiyanggame.add(R.drawable.game48);
        haiyanggame.add(R.drawable.game56);
        haiyanggame.add(R.drawable.game60);
        haiyanggame.add(R.drawable.game72);
        haiyanggame.add(R.drawable.game74);
        //GameNmae
        haiyanggameName.add(getString(R.string.game21));
        haiyanggameName.add(getString(R.string.game39));
        haiyanggameName.add(getString(R.string.game40));
        haiyanggameName.add(getString(R.string.game42));
        haiyanggameName.add(getString(R.string.game44));
        haiyanggameName.add(getString(R.string.game48));
        haiyanggameName.add(getString(R.string.game56));
        haiyanggameName.add(getString(R.string.game60));
        haiyanggameName.add(getString(R.string.game72));
        haiyanggameName.add(getString(R.string.game74));


         kepugame.add(R.drawable.game12);
         kepugame.add(R.drawable.game13);
         kepugame.add(R.drawable.game15);
        kepugame.add(R.drawable.game17);
        kepugame.add(R.drawable.game32);
        kepugame.add(R.drawable.game73);
        //GameNmae
        kepugameName.add(getString(R.string.game12));
        kepugameName.add(getString(R.string.game13));
        kepugameName.add(getString(R.string.game15));
        kepugameName.add(getString(R.string.game17));
        kepugameName.add(getString(R.string.game32));
        kepugameName.add(getString(R.string.game73));

    }

    private void initView(){
        bt_move=findViewById(R.id.bt_move);
        bt_game=findViewById(R.id.bt_game);
        bt_start=findViewById(R.id.start_game);
        bt_close=findViewById(R.id.close_game);
        search=findViewById(R.id.search);
        xiayiye =findViewById(R.id.control_left);
        shangyiye=findViewById(R.id.control_reigt);
        game1=findViewById(R.id.game1);
        game2=findViewById(R.id.game2);
        game3=findViewById(R.id.game3);
        selected_imgae=findViewById(R.id.selected_imgae);
        text_search=findViewById(R.id.text_search);
        UDPbuffer=findViewById(R.id.UDPbuffer);
        who_group=findViewById(R.id.who_group);
        cheackboxip=findViewById(R.id.cheackboxip);
        IP1=findViewById(R.id.ip1);
        IP2=findViewById(R.id.ip2);
        IP3=findViewById(R.id.ip3);
        IP4=findViewById(R.id.ip4);
        IP5=findViewById(R.id.ip5);
        IP6=findViewById(R.id.ip6);
        CB1=findViewById(R.id.CB1);
        CB2=findViewById(R.id.CB2);
        CB3=findViewById(R.id.CB3);
        CB4=findViewById(R.id.CB4);
        CB5=findViewById(R.id.CB5);
        CB6=findViewById(R.id.CB6);
        qidong=findViewById(R.id.qidong);
        password=findViewById(R.id.ec_edit_password);
        changeip=findViewById(R.id.change);
        edit_ip=findViewById(R.id.edit_ip);
        close=findViewById(R.id.closeapp);
        bt_stop=findViewById(R.id.stop);
        bt_qianjin=findViewById(R.id.bt_qianjin);
        bt_houtui=findViewById(R.id.bt_houtui);
        UDPbuffer.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动

        allgame=findViewById(R.id.allgame);
        kongbu=findViewById(R.id.kongbugame);
        Button hangtian = findViewById(R.id.hangtiangame);
        jixian=findViewById(R.id.jixiangame);
        haiyang=findViewById(R.id.haiyanggame);
        kepu=findViewById(R.id.kepugame);

        bt_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (gameMulu){
                    case "allgame":
                        image="move";
                        switch (movepageNum ){
                            case 1:
                                game1.setImageResource(R.drawable.move001);
                                game2.setImageResource(R.drawable.move002);
                                game3.setImageResource(R.drawable.move003);
                                break;
                            case 2:
                                game1.setImageResource(R.drawable.move004);
                                game2.setImageResource(R.drawable.move005);
                                game3.setImageResource(R.drawable.move006);
                                break;
                            case 3:
                                game1.setImageResource(R.drawable.move007);
                                game2.setImageResource(R.drawable.move008);
                                game3.setImageResource(R.drawable.move009);
                                break;
                            case 4:
                                game1.setImageResource(R.drawable.move010);
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                                break;
                            default:
                                game1.setImageResource(R.drawable.solarsystem);
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                                break;
                        }
                        break;
                    case "kongbugame":

                        break;
                    case "hangtiangame":

                        break;
                    case "jixiangame":

                        break;
                    case "haiyanggame":

                        break;
                    case "kepugame":

                        break;
                }

            }
        });
        bt_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (gameMulu){
                    case "allgame":
                        image="game";
                        switch (gamepageNum ){
                            case 1:
                                game1.setImageResource(R.drawable.game1);
                                game2.setImageResource(R.drawable.game2);
                                game3.setImageResource(R.drawable.game3);
                                break;
                            case 2:
                                game1.setImageResource(R.drawable.game4);
                                game2.setImageResource(R.drawable.game5);
                                game3.setImageResource(R.drawable.game6);
                                break;
                            case 3:
                                game1.setImageResource(R.drawable.game7);
                                game2.setImageResource(R.drawable.game8);
                                game3.setImageResource(R.drawable.game9);
                                break;
                            case 4:
                                game1.setImageResource(R.drawable.game10);
                                game2.setImageResource(R.drawable.game11);
                                game3.setImageResource(R.drawable.game12);
                                break;
                            case 5:
                                game1.setImageResource(R.drawable.game13);
                                game2.setImageResource(R.drawable.game14);
                                game3.setImageResource(R.drawable.game15);
                                break;
                            case 6:
                                game1.setImageResource(R.drawable.game16);
                                game2.setImageResource(R.drawable.game17);
                                game3.setImageResource(R.drawable.game18);
                                break;
                            case 7:
                                game1.setImageResource(R.drawable.game19);
                                game2.setImageResource(R.drawable.game20);
                                game3.setImageResource(R.drawable.game21);
                                break;
                            case 8:
                                game1.setImageResource(R.drawable.game22);
                                game2.setImageResource(R.drawable.game23);
                                game3.setImageResource(R.drawable.game24);
                                break;
                            case 9:
                                game1.setImageResource(R.drawable.game25);
                                game2.setImageResource(R.drawable.game26);
                                game3.setImageResource(R.drawable.game27);
                                break;
                            case 10:
                                game1.setImageResource(R.drawable.game28);
                                game2.setImageResource(R.drawable.game29);
                                game3.setImageResource(R.drawable.game30);
                                break;
                            case 11:
                                game1.setImageResource(R.drawable.game31);
                                game2.setImageResource(R.drawable.game32);
                                game3.setImageResource(R.drawable.game33);
                                break;
                            case 12:
                                game1.setImageResource(R.drawable.game34);
                                game2.setImageResource(R.drawable.game35);
                                game3.setImageResource(R.drawable.game36);
                                break;
                            case 13:
                                game1.setImageResource(R.drawable.game37);
                                game2.setImageResource(R.drawable.game38);
                                game3.setImageResource(R.drawable.game39);
                                break;
                            case 14:
                                game1.setImageResource(R.drawable.game40);
                                game2.setImageResource(R.drawable.game41);
                                game3.setImageResource(R.drawable.game42);
                                break;
                            case 15:
                                game1.setImageResource(R.drawable.game43);
                                game2.setImageResource(R.drawable.game44);
                                game3.setImageResource(R.drawable.game45);
                                break;
                            case 16:
                                game1.setImageResource(R.drawable.game46);
                                game2.setImageResource(R.drawable.game47);
                                game3.setImageResource(R.drawable.game48);
                                break;
                            case 17:
                                game1.setImageResource(R.drawable.game49);
                                game2.setImageResource(R.drawable.game50);
                                game3.setImageResource(R.drawable.game51);
                                break;
                            case 18:
                                game1.setImageResource(R.drawable.game52);
                                game2.setImageResource(R.drawable.game53);
                                game3.setImageResource(R.drawable.game54);
                                break;
                            case 19:
                                game1.setImageResource(R.drawable.game55);
                                game2.setImageResource(R.drawable.game56);
                                game3.setImageResource(R.drawable.game57);
                                break;
                            case 20:
                                game1.setImageResource(R.drawable.game58);
                                game2.setImageResource(R.drawable.game59);
                                game3.setImageResource(R.drawable.game60);
                                break;
                            case 21:
                                game1.setImageResource(R.drawable.game61);
                                game2.setImageResource(R.drawable.game62);
                                game3.setImageResource(R.drawable.game63);
                                break;
                            case 22:
                                game1.setImageResource(R.drawable.game64);
                                game2.setImageResource(R.drawable.game65);
                                game3.setImageResource(R.drawable.game66);
                                break;
                            case 23:
                                game1.setImageResource(R.drawable.game67);
                                game2.setImageResource(R.drawable.game68);
                                game3.setImageResource(R.drawable.game69);
                                break;
                            case 24:
                                game1.setImageResource(R.drawable.game70);
                                game2.setImageResource(R.drawable.game71);
                                game3.setImageResource(R.drawable.game72);
                                break;
                            case 25:
                                game1.setImageResource(R.drawable.game73);
                                game2.setImageResource(R.drawable.game74);
                                game3.setImageResource(R.drawable.game75);
                                break;
                            case 26:
                                game1.setImageResource(R.drawable.game76);
                                game2.setImageResource(R.drawable.game77);
                                game3.setImageResource(R.drawable.game78);
                                break;
                            case 27:
                                game1.setImageResource(R.drawable.game79);
                                game2.setImageResource(R.drawable.game80);
                                game3.setImageResource(R.drawable.game81);
                                break;
                            case 28:
                                game1.setImageResource(R.drawable.game82);
                                game2.setImageResource(R.drawable.game83);
                                game3.setImageResource(R.drawable.game84);
                                break;
                            case 29:
                                game1.setImageResource(R.drawable.game85);
                                game2.setImageResource(R.drawable.game86);
                                game3.setImageResource(R.drawable.game87);
                                break;
                            default:
                                game1.setImageResource(R.drawable.solarsystem);
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                                break;
                        }
                        break;
                    case "kongbugame":

                        break;
                    case "hangtiangame":

                        break;
                    case "jixiangame":

                        break;
                    case "haiyanggame":

                        break;
                    case "kepugame":

                        break;
                }

            }
        });
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setImageResource(R.drawable.search);
                recrState=true;
                bt_close.setEnabled(true);
                bt_start.setEnabled(false);
                shangyiye.setEnabled(false);
                xiayiye.setEnabled(false);
                String gamemoveName="";
                Log.i(" JM9DVR-Cinema",  "gamelieNum:"+gamelieNum);

                switch (gameMulu){
                    case "allgame":
                        if (image.equals("game")){
                            gamemoveName=game.get(gamenum-1);
                 /*   switch (gamelieNum){
                        case 1:
                            gamenum=gamepageNum*3-2;
                            if (gamepageNum==1){
                                gamemoveName=game.get(0);
                            }else if (gamepageNum==2){
                                gamemoveName=game.get(3);
                            }else if (gamepageNum==3){
                                gamemoveName=game.get(6);
                            }
                            else if (gamepageNum==4){
                                gamemoveName=game.get(9);
                            }
                            break;
                        case 2:
                            gamenum=gamepageNum*3-1;
                            if (gamepageNum==1){
                                gamemoveName=game.get(1);
                            }else if (gamepageNum==2){
                                gamemoveName=game.get(4);
                            }else if (gamepageNum==3){
                                gamemoveName=game.get(7);
                            }
                            else if (gamepageNum==4){
                                gamemoveName=game.get(10);
                            }
                            break;
                        case 3:
                            gamenum=gamepageNum*3;
                            if (gamepageNum==1){
                                gamemoveName=game.get(2);
                            }else if (gamepageNum==2){
                                gamemoveName=game.get(5);
                            }else if (gamepageNum==3){
                                gamemoveName=game.get(8);
                            }
                            else if (gamepageNum==4){
                                gamemoveName=game.get(11);
                            }
                            break;
                    }*/
                            UDPsendBuf="IPAD(00,"+gamemoveName+",01)";
                        }else if (image.equals("move")){
                            switch (movelieNum){
                                case 1:
                                    movenum=movepageNum*3-2;
                                    if (movepageNum==1){
                                        gamemoveName=move.get(0);
                                    }else if (movepageNum==2){
                                        gamemoveName=move.get(3);
                                    }else if (movepageNum==3){
                                        gamemoveName=move.get(6);
                                    }
                                    else if (movepageNum==4){
                                        gamemoveName=move.get(9);
                                    }
                                    break;
                                case 2:
                                    movenum=movepageNum*3-1;
                                    if (movepageNum==1){
                                        gamemoveName=move.get(1);
                                    }else if (movepageNum==2){
                                        gamemoveName=move.get(4);
                                    }else if (movepageNum==3){
                                        gamemoveName=move.get(7);
                                    }
                                    else if (movepageNum==4){
                                        gamemoveName=move.get(10);
                                    }
                                    break;
                                case 3:
                                    movenum=movepageNum*3;
                                    if (movepageNum==1){
                                        gamemoveName=move.get(2);
                                    }else if (movepageNum==2){
                                        gamemoveName=move.get(5);
                                    }else if (movepageNum==3){
                                        gamemoveName=move.get(8);
                                    }
                                    else if (movepageNum==4){
                                        gamemoveName=move.get(11);
                                    }
                                    break;

                            }
                            UDPsendBuf="IPAD(00,"+gamemoveName+",01)";
                        }
              /*  for (int i=0;i<IP.size();i++){
                  //  Toast.makeText(MainActivity.this,"IPList:"+IP.get(i), Toast.LENGTH_SHORT).show();
                    SERVERIP=IP.get(i).toString();
                    Log.v("fist", "IPList,IP" +i+":"+SERVERIP);
                    TSend = new Thread(){
                        public void run(){
                            String str = UDPsendBuf;//获取输入框的信息
                            Log.v("fist","可以发送："+ str);
                            SendMsg(str,SERVERIP);   //只要一按按钮，就发送数据
                            Log.v("fist","设备IP："+ SERVERIP);
                        }
                    };
                    TSend.start();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.v("fist",SERVERIP+"线程执行完啦！");
                }
              */

                        break;
                    case "kongbugame":
                        switch (gamelieNum){
                            case 1:
                                gamemoveName=kongbugameName.get(kongbugamepageNum*3-3);
                                break;
                            case 2:
                                gamemoveName=kongbugameName.get(kongbugamepageNum*3-2);
                                break;
                            case 3:
                                gamemoveName=kongbugameName.get(kongbugamepageNum*3-1);
                                break;
                        }

                        break;
                    case "hangtiangame":
                        switch (gamelieNum){
                            case 1:
                                gamemoveName=hangtiangameName.get(hangtiangamepageNum*3-3);
                                break;
                            case 2:
                                gamemoveName=hangtiangameName.get(hangtiangamepageNum*3-2);
                                break;
                            case 3:
                                gamemoveName=hangtiangameName.get(hangtiangamepageNum*3-1);
                                break;
                        }
                        break;
                    case "jixiangame":
                        switch (gamelieNum){
                            case 1:
                                gamemoveName=jixiangameName.get(jixiangamepageNum*3-3);
                                break;
                            case 2:
                                gamemoveName=jixiangameName.get(jixiangamepageNum*3-2);
                                break;
                            case 3:
                                gamemoveName=jixiangameName.get(jixiangamepageNum*3-1);
                                break;
                        }
                        break;
                    case "haiyanggame":
                        switch (gamelieNum){
                            case 1:
                                gamemoveName=haiyanggameName.get(haiyanggamepageNum*3-3);
                                break;
                            case 2:
                                gamemoveName=haiyanggameName.get(haiyanggamepageNum*3-2);
                                break;
                            case 3:
                                gamemoveName=haiyanggameName.get(haiyanggamepageNum*3-1);
                                break;
                        }
                        break;
                    case "kepugame":
                        switch (gamelieNum){
                            case 1:
                                gamemoveName=kepugameName.get(kepugamepageNum*3-3);
                                break;
                            case 2:
                                gamemoveName=kepugameName.get(kepugamepageNum*3-2);
                                break;
                            case 3:
                                gamemoveName=kepugameName.get(kepugamepageNum*3-1);
                                break;
                        }
                        break;
                }
                UDPsendBuf="IPAD(00,"+gamemoveName+",01)";
                Log.v("UDPsendBuf", "UDPsendBuf" +":"+UDPsendBuf);
                Log.v("fist", "SERVERIP" +":"+SERVERIP);
                TSend = new Thread(){
                    public void run(){
                        String str = UDPsendBuf;//获取输入框的信息
                        Log.v("fist","可以发送："+ str);
                        SendMsg(str,SERVERIP);   //只要一按按钮，就发送数据
                        Log.v("fist","设备IP："+ SERVERIP);
                    }
                };
                TSend.start();

                new Thread(){
                    public void run(){
                        try {
                            receiveMsg();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recrState=false;
                bt_close.setEnabled(false);
                bt_start.setEnabled(true);
                shangyiye.setEnabled(true);
                xiayiye.setEnabled(true);
                String gamemoveName="";
                Log.i(" JM9DVR-Cinema",  "gamelieNum:"+gamelieNum);

                switch (gameMulu){
                    case "allgame":
                        if (image.equals("game")){
                            gamemoveName=game.get(gamenum-1);
                  /*  switch (gamelieNum){
                        case 1:
                            gamenum=gamepageNum*3-2;
                            if (gamepageNum==1){
                                gamemoveName=game.get(0);
                            }else if (gamepageNum==2){
                                gamemoveName=game.get(3);
                            }else if (gamepageNum==3){
                                gamemoveName=game.get(6);
                            }
                            else if (gamepageNum==4){
                                gamemoveName=game.get(9);
                            }
                            break;
                        case 2:
                            gamenum=gamepageNum*3-1;
                            if (gamepageNum==1){
                                gamemoveName=game.get(1);
                            }else if (gamepageNum==2){
                                gamemoveName=game.get(4);
                            }else if (gamepageNum==3){
                                gamemoveName=game.get(7);
                            }
                            else if (gamepageNum==4){
                                gamemoveName=game.get(10);
                            }
                            break;
                        case 3:
                            gamenum=gamepageNum*3;
                            if (gamepageNum==1){
                                gamemoveName=game.get(2);
                            }else if (gamepageNum==2){
                                gamemoveName=game.get(5);
                            }else if (gamepageNum==3){
                                gamemoveName=game.get(8);
                            }
                            else if (gamepageNum==4){
                                gamemoveName=game.get(11);
                            }
                            break;
                    }*/
                            UDPsendBuf="IPAD(00,"+gamemoveName+",03)";
                        }else if (image.equals("move")){
                            switch (movelieNum){
                                case 1:
                                    movenum=movepageNum*3-2;
                                    if (movepageNum==1){
                                        gamemoveName=move.get(0);
                                    }else if (movepageNum==2){
                                        gamemoveName=move.get(3);
                                    }else if (movepageNum==3){
                                        gamemoveName=move.get(6);
                                    }
                                    else if (movepageNum==4){
                                        gamemoveName=move.get(9);
                                    }
                                    break;
                                case 2:
                                    movenum=movepageNum*3-1;
                                    if (movepageNum==1){
                                        gamemoveName=move.get(1);
                                    }else if (movepageNum==2){
                                        gamemoveName=move.get(4);
                                    }else if (movepageNum==3){
                                        gamemoveName=move.get(7);
                                    }
                                    else if (movepageNum==4){
                                        gamemoveName=move.get(10);
                                    }
                                    break;
                                case 3:
                                    movenum=movepageNum*3;
                                    if (movepageNum==1){
                                        gamemoveName=move.get(2);
                                    }else if (movepageNum==2){
                                        gamemoveName=move.get(5);
                                    }else if (movepageNum==3){
                                        gamemoveName=move.get(8);
                                    }
                                    else if (movepageNum==4){
                                        gamemoveName=move.get(11);
                                    }
                                    break;
                            }
                            UDPsendBuf="IPAD(00,"+gamemoveName+",03)";
                        }
                   /*   for (int i=0;i<IP.size();i++) {
                    //  Toast.makeText(MainActivity.this,"IPList:"+IP.get(i), Toast.LENGTH_SHORT).show();
                    SERVERIP = IP.get(i).toString();
                    Log.v("fist", "IPList,IP" +i+":"+SERVERIP);
                    TSend = new Thread() {
                        public void run() {
                            String str = UDPsendBuf;//获取输入框的信息
                            Log.v("fist", "可以发送：" + str);
                            SendMsg(str, SERVERIP);   //只要一按按钮，就发送数据
                            Log.v("fist","设备IP："+ SERVERIP);
                        }
                    };
                    TSend.start();
                    Log.v("fist", SERVERIP+"线程执行完啦！");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
             */
                        break;
                    case "kongbugame":
                        switch (gamelieNum){
                            case 1:
                                gamemoveName=kongbugameName.get(kongbugamepageNum*3-3);
                                break;
                            case 2:
                                gamemoveName=kongbugameName.get(kongbugamepageNum*3-2);
                                break;
                            case 3:
                                gamemoveName=kongbugameName.get(kongbugamepageNum*3-1);
                                break;
                        }

                        break;
                    case "hangtiangame":
                        switch (gamelieNum){
                            case 1:
                                gamemoveName=hangtiangameName.get(hangtiangamepageNum*3-3);
                                break;
                            case 2:
                                gamemoveName=hangtiangameName.get(hangtiangamepageNum*3-2);
                                break;
                            case 3:
                                gamemoveName=hangtiangameName.get(hangtiangamepageNum*3-1);
                                break;
                        }
                        break;
                    case "jixiangame":
                        switch (gamelieNum){
                            case 1:
                                gamemoveName=jixiangameName.get(jixiangamepageNum*3-3);
                                break;
                            case 2:
                                gamemoveName=jixiangameName.get(jixiangamepageNum*3-2);
                                break;
                            case 3:
                                gamemoveName=jixiangameName.get(jixiangamepageNum*3-1);
                                break;
                        }
                        break;
                    case "haiyanggame":
                        switch (gamelieNum){
                            case 1:
                                gamemoveName=haiyanggameName.get(haiyanggamepageNum*3-3);
                                break;
                            case 2:
                                gamemoveName=haiyanggameName.get(haiyanggamepageNum*3-2);
                                break;
                            case 3:
                                gamemoveName=haiyanggameName.get(haiyanggamepageNum*3-1);
                                break;
                        }
                        break;
                    case "kepugame":
                        switch (gamelieNum){
                            case 1:
                                gamemoveName=kepugameName.get(kepugamepageNum*3-3);
                                break;
                            case 2:
                                gamemoveName=kepugameName.get(kepugamepageNum*3-2);
                                break;
                            case 3:
                                gamemoveName=kepugameName.get(kepugamepageNum*3-1);
                                break;
                        }
                        break;
                }
                UDPsendBuf="IPAD(00,"+gamemoveName+",03)";
                Log.v("UDPsendBuf", "UDPsendBuf" +":"+UDPsendBuf);
                TSend = new Thread(){
                    public void run(){
                        String str = UDPsendBuf;//获取输入框的信息
                        Log.v("fist","可以发送："+ str);
                        SendMsg(str,SERVERIP);   //只要一按按钮，就发送数据
                        Log.v("fist","设备IP："+ SERVERIP);
                    }
                };
                TSend.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(){
                    public void run(){
                        try {
                            receiveMsg();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setImageResource(R.drawable.searching);
                boolean   Search=false;
                String searchString=text_search.getText().toString().trim() ;
                Log.i(" JM9DVR-Cinema",  "text_search:"+searchString);
                if (!Search){
                    for (int i = 0; i <game.size(); i++) {
                        if (searchString.equals(game.get(i))){
                            Search=true;
                            image="game";
                            switch (i+1){
                                case 1:
                                    gamepageNum=1;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game1);
                                    break;
                                case 2:
                                    gamepageNum=1;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game2);
                                    break;
                                case 3:
                                    gamepageNum=1;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game3);
                                    break;
                                case 4:
                                    gamepageNum=2;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game4);
                                    break;
                                case 5:
                                    gamepageNum=2;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game5);
                                    break;
                                case 6:
                                    gamepageNum=2;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game6);
                                    break;
                                case 7:
                                    gamepageNum=3;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game7);
                                    break;
                                case 8:
                                    gamepageNum=3;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game8);
                                    break;
                                case 9:
                                    gamepageNum=3;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game9);
                                    break;
                                case 10:
                                    gamepageNum=4;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game10);
                                    break;
                                case 11:
                                    gamepageNum=4;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game11);
                                    break;
                                case 12:
                                    gamepageNum=4;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game12);
                                    break;
                                case 13:
                                    gamepageNum=5;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game13);
                                    break;
                                case 14:
                                    gamepageNum=5;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game14);
                                    break;
                                case 15:
                                    gamepageNum=5;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game15);
                                    break;
                                case 16:
                                    gamepageNum=6;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game16);
                                    break;
                                case 17:
                                    gamepageNum=6;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game17);
                                    break;
                                case 18:
                                    gamepageNum=6;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game18);
                                    break;
                                case 19:
                                    gamepageNum=7;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game19);
                                    break;
                                case 20:
                                    gamepageNum=7;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game20);
                                    break;
                                case 21:
                                    gamepageNum=7;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game21);
                                    break;
                                case 22:
                                    gamepageNum=8;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game22);
                                    break;
                                case 23:
                                    gamepageNum=8;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game23);
                                    break;
                                case 24:
                                    gamepageNum=8;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game24);
                                    break;
                                case 25:
                                    gamepageNum=9;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game25);
                                    break;
                                case 26:
                                    gamepageNum=9;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game26);
                                    break;
                                case 27:
                                    gamepageNum=9;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game27);
                                    break;
                                case 28:
                                    gamepageNum=10;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game28);
                                    break;
                                case 29:
                                    gamepageNum=10;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game29);
                                    break;
                                case 30:
                                    gamepageNum=10;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game30);
                                    break;
                                case 31:
                                    gamepageNum=11;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game31);
                                    break;
                                case 32:
                                    gamepageNum=11;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game32);
                                    break;
                                case 33:
                                    gamepageNum=11;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game33);
                                    break;
                                case 34:
                                    gamepageNum=12;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game34);
                                    break;
                                case 35:
                                    gamepageNum=12;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game35);
                                    break;
                                case 36:
                                    gamepageNum=12;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game36);
                                    break;
                                case 37:
                                    gamepageNum=13;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game37);
                                    break;
                                case 38:
                                    gamepageNum=13;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game38);
                                    break;
                                case 39:
                                    gamepageNum=13;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game39);
                                    break;
                                case 40:
                                    gamepageNum=14;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game40);
                                    break;
                                case 41:
                                    gamepageNum=14;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game41);
                                    break;
                                case 42:
                                    gamepageNum=14;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game42);
                                    break;
                                case 43:
                                    gamepageNum=15;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game43);
                                    break;
                                case 44:
                                    gamepageNum=15;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game44);
                                    break;
                                case 45:
                                    gamepageNum=15;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game45);
                                    break;
                                case 46:
                                    gamepageNum=16;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game46);
                                    break;
                                case 47:
                                    gamepageNum=16;
                                    gamelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.game47);
                                    break;
                                case 48:
                                    gamepageNum=16;
                                    gamelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.game48);
                                    break;
                                case 49:
                                    gamepageNum=17;
                                    gamelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.game49);
                                    break;
                                default:
                                    selected_imgae.setImageResource(R.drawable.solarsystem);
                                    break;
                            }
                            break;
                        }else{
                            Search=false;
                        }
                    }
                }
                if (!Search){
                    for (int i = 0; i <move.size(); i++) {
                        if (searchString.equals(move.get(i))){
                            Search=true;
                            image="move";
                            switch (i+1){
                                case 1:
                                    movepageNum=1;
                                    movelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.move001);
                                    break;
                                case 2:
                                    movepageNum=1;
                                    movelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.move002);
                                    break;
                                case 3:
                                    movepageNum=1;
                                    movelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.move003);
                                    break;
                                case 4:
                                    movepageNum=2;
                                    movelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.move004);
                                    break;
                                case 5:
                                    movepageNum=2;
                                    movelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.move005);
                                    break;
                                case 6:
                                    movepageNum=2;
                                    movelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.move006);
                                    break;
                                case 7:
                                    movepageNum=3;
                                    movelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.move007);
                                    break;
                                case 8:
                                    movepageNum=3;
                                    movelieNum=2;
                                    selected_imgae.setImageResource(R.drawable.move008);
                                    break;
                                case 9:
                                    movepageNum=3;
                                    movelieNum=3;
                                    selected_imgae.setImageResource(R.drawable.move009);
                                    break;
                                case 10:
                                    movepageNum=4;
                                    movelieNum=1;
                                    selected_imgae.setImageResource(R.drawable.move010);
                                    break;
                                default:
                                    selected_imgae.setImageResource(R.drawable.solarsystem);
                                    break;
                            }
                            break;
                        }else{
                            Search=false;
                        }
                    }
                }
                if (!Search){
                    Toast.makeText(MainActivity.this, "没有搜索到"+searchString+"!请重新搜索！", Toast.LENGTH_SHORT).show();
                    search.setImageResource(R.drawable.search);
                }

            }
        });
        shangyiye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (gameMulu){
                    case "allgame":
                        if (image.equals("game")){
                            gamepageNum++;
                            if (gameNum%3==0){
                                pagenum=gameNum/3;
                            }else {
                                pagenum=gameNum/3+1;
                            }
                            if (gamepageNum > pagenum){
                                gamepageNum=1;
                            }
                            switch (gamepageNum ){
                                case 1:
                                    game1.setImageResource(R.drawable.game1);
                                    game2.setImageResource(R.drawable.game2);
                                    game3.setImageResource(R.drawable.game3);
                                    break;
                                case 2:
                                    game1.setImageResource(R.drawable.game4);
                                    game2.setImageResource(R.drawable.game5);
                                    game3.setImageResource(R.drawable.game6);
                                    break;
                                case 3:
                                    game1.setImageResource(R.drawable.game7);
                                    game2.setImageResource(R.drawable.game8);
                                    game3.setImageResource(R.drawable.game9);
                                    break;
                                case 4:
                                    game1.setImageResource(R.drawable.game10);
                                    game2.setImageResource(R.drawable.game11);
                                    game3.setImageResource(R.drawable.game12);
                                    break;
                                case 5:
                                    game1.setImageResource(R.drawable.game13);
                                    game2.setImageResource(R.drawable.game14);
                                    game3.setImageResource(R.drawable.game15);
                                    break;
                                case 6:
                                    game1.setImageResource(R.drawable.game16);
                                    game2.setImageResource(R.drawable.game17);
                                    game3.setImageResource(R.drawable.game18);
                                    break;
                                case 7:
                                    game1.setImageResource(R.drawable.game19);
                                    game2.setImageResource(R.drawable.game20);
                                    game3.setImageResource(R.drawable.game21);
                                    break;
                                case 8:
                                    game1.setImageResource(R.drawable.game22);
                                    game2.setImageResource(R.drawable.game23);
                                    game3.setImageResource(R.drawable.game24);
                                    break;
                                case 9:
                                    game1.setImageResource(R.drawable.game25);
                                    game2.setImageResource(R.drawable.game26);
                                    game3.setImageResource(R.drawable.game27);
                                    break;
                                case 10:
                                    game1.setImageResource(R.drawable.game28);
                                    game2.setImageResource(R.drawable.game29);
                                    game3.setImageResource(R.drawable.game30);
                                    break;
                                case 11:
                                    game1.setImageResource(R.drawable.game31);
                                    game2.setImageResource(R.drawable.game32);
                                    game3.setImageResource(R.drawable.game33);
                                    break;
                                case 12:
                                    game1.setImageResource(R.drawable.game34);
                                    game2.setImageResource(R.drawable.game35);
                                    game3.setImageResource(R.drawable.game36);
                                    break;
                                case 13:
                                    game1.setImageResource(R.drawable.game37);
                                    game2.setImageResource(R.drawable.game38);
                                    game3.setImageResource(R.drawable.game39);
                                    break;
                                case 14:
                                    game1.setImageResource(R.drawable.game40);
                                    game2.setImageResource(R.drawable.game41);
                                    game3.setImageResource(R.drawable.game42);
                                    break;
                                case 15:
                                    game1.setImageResource(R.drawable.game43);
                                    game2.setImageResource(R.drawable.game44);
                                    game3.setImageResource(R.drawable.game45);
                                    break;
                                case 16:
                                    game1.setImageResource(R.drawable.game46);
                                    game2.setImageResource(R.drawable.game47);
                                    game3.setImageResource(R.drawable.game48);
                                    break;
                                case 17:
                                    game1.setImageResource(R.drawable.game49);
                                    game2.setImageResource(R.drawable.game50);
                                    game3.setImageResource(R.drawable.game51);
                                    break;
                                case 18:
                                    game1.setImageResource(R.drawable.game52);
                                    game2.setImageResource(R.drawable.game53);
                                    game3.setImageResource(R.drawable.game54);
                                    break;
                                case 19:
                                    game1.setImageResource(R.drawable.game55);
                                    game2.setImageResource(R.drawable.game56);
                                    game3.setImageResource(R.drawable.game57);
                                    break;
                                case 20:
                                    game1.setImageResource(R.drawable.game58);
                                    game2.setImageResource(R.drawable.game59);
                                    game3.setImageResource(R.drawable.game60);
                                    break;
                                case 21:
                                    game1.setImageResource(R.drawable.game61);
                                    game2.setImageResource(R.drawable.game62);
                                    game3.setImageResource(R.drawable.game63);
                                    break;
                                case 22:
                                    game1.setImageResource(R.drawable.game64);
                                    game2.setImageResource(R.drawable.game65);
                                    game3.setImageResource(R.drawable.game66);
                                    break;
                                case 23:
                                    game1.setImageResource(R.drawable.game67);
                                    game2.setImageResource(R.drawable.game68);
                                    game3.setImageResource(R.drawable.game69);
                                    break;
                                case 24:
                                    game1.setImageResource(R.drawable.game70);
                                    game2.setImageResource(R.drawable.game71);
                                    game3.setImageResource(R.drawable.game72);
                                    break;
                                case 25:
                                    game1.setImageResource(R.drawable.game73);
                                    game2.setImageResource(R.drawable.game74);
                                    game3.setImageResource(R.drawable.game75);
                                    break;
                                case 26:
                                    game1.setImageResource(R.drawable.game76);
                                    game2.setImageResource(R.drawable.game77);
                                    game3.setImageResource(R.drawable.game78);
                                    break;
                                case 27:
                                    game1.setImageResource(R.drawable.game79);
                                    game2.setImageResource(R.drawable.game80);
                                    game3.setImageResource(R.drawable.game81);
                                    break;
                                case 28:
                                    game1.setImageResource(R.drawable.game82);
                                    game2.setImageResource(R.drawable.game83);
                                    game3.setImageResource(R.drawable.game84);
                                    break;
                                case 29:
                                    game1.setImageResource(R.drawable.game85);
                                    game2.setImageResource(R.drawable.game86);
                                    game3.setImageResource(R.drawable.game87);
                                    break;
                                default:
                                    game1.setImageResource(R.drawable.solarsystem);
                                    game2.setImageResource(R.drawable.solarsystem);
                                    game3.setImageResource(R.drawable.solarsystem);
                                    break;
                            }

                        }else if (image.equals("move")){
                            movepageNum++;
                            if (moveNum%3==0){
                                pagenum=moveNum/3;
                            }else {
                                pagenum=moveNum/3+1;
                            }
                            if (movepageNum > pagenum){
                                movepageNum=1;
                            }
                            switch (movepageNum ){
                                case 1:
                                    game1.setImageResource(R.drawable.move001);
                                    game2.setImageResource(R.drawable.move002);
                                    game3.setImageResource(R.drawable.move003);
                                    break;
                                case 2:
                                    game1.setImageResource(R.drawable.move004);
                                    game2.setImageResource(R.drawable.move005);
                                    game3.setImageResource(R.drawable.move006);
                                    break;
                                case 3:
                                    game1.setImageResource(R.drawable.move007);
                                    game2.setImageResource(R.drawable.move008);
                                    game3.setImageResource(R.drawable.move009);
                                    break;
                                case 4:
                                    game1.setImageResource(R.drawable.move010);
                                    game2.setImageResource(R.drawable.solarsystem);
                                    game3.setImageResource(R.drawable.solarsystem);
                                    break;
                                default:
                                    game1.setImageResource(R.drawable.solarsystem);
                                    game2.setImageResource(R.drawable.solarsystem);
                                    game3.setImageResource(R.drawable.solarsystem);
                                    break;
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "gamepageNum:"+gamepageNum);
                        Log.i(" JM9DVR-Cinema",  "movepageNum:"+movepageNum);
                        break;
                    case "kongbugame":
                        kongbugamepageNum++;
                        int pNum=kongbugame.size();
                        if (pNum>3&&pNum%3==0){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3;
                            }
                            game1.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                            game2.setImageResource( kongbugame.get(kongbugamepageNum*3-2));
                            game3.setImageResource( kongbugame.get(kongbugamepageNum*3-1));
                        }else if(pNum>3&&pNum%3==1){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3+1;
                                game1.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                                game2.setImageResource( kongbugame.get(kongbugamepageNum*3-2));
                                game3.setImageResource( kongbugame.get(kongbugamepageNum*3-1));
                            }

                        }
                        else if(pNum>3&&pNum%3==2){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3+1;
                                game1.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                                game2.setImageResource(kongbugame.get(kongbugamepageNum*3-2));
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                                game2.setImageResource( kongbugame.get(kongbugamepageNum*3-2));
                                game3.setImageResource( kongbugame.get(kongbugamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum:"+pNum);
                        Log.i(" JM9DVR-Cinema",  "kongbugamepageNum:"+kongbugamepageNum);

                        break;
                    case "hangtiangame":
                        hangtiangamepageNum++;
                       int pNum1=hangtiangame.size();
                        if (pNum1>3&&pNum1%3==0){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3;
                            }
                            game1.setImageResource( hangtiangame.get(hangtiangamepageNum*3-3));
                            game2.setImageResource( hangtiangame.get(hangtiangamepageNum*3-2));
                            game3.setImageResource( hangtiangame.get(hangtiangamepageNum*3-1));
                        }else if(pNum1>3&&pNum1%3==1){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3+1;
                                game1.setImageResource( hangtiangame.get(hangtiangamepageNum*3-3));
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( hangtiangame.get(hangtiangamepageNum*3-3));
                                game2.setImageResource( hangtiangame.get(hangtiangamepageNum*3-2));
                                game3.setImageResource( hangtiangame.get(hangtiangamepageNum*3-1));
                            }

                        }
                        else if(pNum1>3&&pNum1%3==2){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3+1;
                                game1.setImageResource( hangtiangame.get(hangtiangamepageNum*3-3));
                                game2.setImageResource(hangtiangame.get(hangtiangamepageNum*3-2));
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( hangtiangame.get(hangtiangamepageNum*3-3));
                                game2.setImageResource( hangtiangame.get(hangtiangamepageNum*3-2));
                                game3.setImageResource( hangtiangame.get(hangtiangamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum1:"+pNum1);
                        Log.i(" JM9DVR-Cinema",  "hangtiangamepageNum:"+hangtiangamepageNum);
                        break;
                    case "jixiangame":
                        jixiangamepageNum++;
                        int pNum2=jixiangame.size();

                        if (pNum2>3&&pNum2%3==0){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3;
                            }
                            game1.setImageResource( jixiangame.get(jixiangamepageNum*3-3));
                            game2.setImageResource( jixiangame.get(jixiangamepageNum*3-2));
                            game3.setImageResource( jixiangame.get(jixiangamepageNum*3-1));
                        }else if(pNum2>3&&pNum2%3==1){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3+1;
                                game1.setImageResource( jixiangame.get(jixiangamepageNum*3-3));
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( jixiangame.get(jixiangamepageNum*3-3));
                                game2.setImageResource( jixiangame.get(jixiangamepageNum*3-2));
                                game3.setImageResource( jixiangame.get(jixiangamepageNum*3-1));
                            }

                        }
                        else if(pNum2>3&&pNum2%3==2){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3+1;
                                game1.setImageResource( jixiangame.get(jixiangamepageNum*3-3));
                                game2.setImageResource(jixiangame.get(jixiangamepageNum*3-2));
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( jixiangame.get(jixiangamepageNum*3-3));
                                game2.setImageResource( jixiangame.get(jixiangamepageNum*3-2));
                                game3.setImageResource( jixiangame.get(jixiangamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum2:"+pNum2);
                        Log.i(" JM9DVR-Cinema",  "jixiangamepageNum:"+jixiangamepageNum);
                        break;
                    case "haiyanggame":
                        haiyanggamepageNum++;
                        int pNum3=haiyanggame.size();

                        if (pNum3>3&&pNum3%3==0){
                            if( haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3;
                            }
                            game1.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-3));
                            game2.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-2));
                            game3.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-1));
                        }else if(pNum3>3&&pNum3%3==1){
                            if( haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3+1;
                                game1.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-3));
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-3));
                                game2.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-2));
                                game3.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-1));
                            }

                        }
                        else if(pNum3>3&&pNum3%3==2){
                            if( haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3+1;
                                game1.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-3));
                                game2.setImageResource( haiyanggame.get( haiyanggamepageNum*3-2));
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-3));
                                game2.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-2));
                                game3.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum3:"+pNum3);
                        Log.i(" JM9DVR-Cinema",  "haiyanggamepageNum:"+ haiyanggamepageNum);
                        break;
                    case "kepugame":
                        kepugamepageNum++;
                        int pNum4=kepugame.size();

                        if (pNum4>3&&pNum4%3==0){
                            if( kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3;
                            }
                            game1.setImageResource(  kepugame.get( kepugamepageNum*3-3));
                            game2.setImageResource(  kepugame.get( kepugamepageNum*3-2));
                            game3.setImageResource( kepugame.get( kepugamepageNum*3-1));
                        }else if(pNum4>3&&pNum4%3==1){
                            if( kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3+1;
                                game1.setImageResource(  kepugame.get( kepugamepageNum*3-3));
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource(  kepugame.get( kepugamepageNum*3-3));
                                game2.setImageResource(  kepugame.get( kepugamepageNum*3-2));
                                game3.setImageResource(  kepugame.get( kepugamepageNum*3-1));
                            }

                        }
                        else if(pNum4>3&&pNum4%3==2){
                            if( kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3+1;
                                game1.setImageResource(  kepugame.get( kepugamepageNum*3-3));
                                game2.setImageResource( kepugame.get( kepugamepageNum*3-2));
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource(  kepugame.get( kepugamepageNum*3-3));
                                game2.setImageResource(  kepugame.get( kepugamepageNum*3-2));
                                game3.setImageResource(  kepugame.get( kepugamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum4:"+pNum4);
                        Log.i(" JM9DVR-Cinema",  "kepugamepageNum:"+ kepugamepageNum);
                        break;
                }


            }
        });
        xiayiye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (gameMulu){
                    case "allgame":
                        if (image.equals("game")){
                            gamepageNum--;
                            if (gamepageNum < 0||gamepageNum==0){
                                gamepageNum=1;
                            }
                            switch (gamepageNum ){
                                case 1:
                                    game1.setImageResource(R.drawable.game1);
                                    game2.setImageResource(R.drawable.game2);
                                    game3.setImageResource(R.drawable.game3);
                                    break;
                                case 2:
                                    game1.setImageResource(R.drawable.game4);
                                    game2.setImageResource(R.drawable.game5);
                                    game3.setImageResource(R.drawable.game6);
                                    break;
                                case 3:
                                    game1.setImageResource(R.drawable.game7);
                                    game2.setImageResource(R.drawable.game8);
                                    game3.setImageResource(R.drawable.game9);
                                    break;
                                case 4:
                                    game1.setImageResource(R.drawable.game10);
                                    game2.setImageResource(R.drawable.game11);
                                    game3.setImageResource(R.drawable.game12);
                                    break;
                                case 5:
                                    game1.setImageResource(R.drawable.game13);
                                    game2.setImageResource(R.drawable.game14);
                                    game3.setImageResource(R.drawable.game15);
                                    break;
                                case 6:
                                    game1.setImageResource(R.drawable.game16);
                                    game2.setImageResource(R.drawable.game17);
                                    game3.setImageResource(R.drawable.game18);
                                    break;
                                case 7:
                                    game1.setImageResource(R.drawable.game19);
                                    game2.setImageResource(R.drawable.game20);
                                    game3.setImageResource(R.drawable.game21);
                                    break;
                                case 8:
                                    game1.setImageResource(R.drawable.game22);
                                    game2.setImageResource(R.drawable.game23);
                                    game3.setImageResource(R.drawable.game24);
                                    break;
                                case 9:
                                    game1.setImageResource(R.drawable.game25);
                                    game2.setImageResource(R.drawable.game26);
                                    game3.setImageResource(R.drawable.game27);
                                    break;
                                case 10:
                                    game1.setImageResource(R.drawable.game28);
                                    game2.setImageResource(R.drawable.game29);
                                    game3.setImageResource(R.drawable.game30);
                                    break;
                                case 11:
                                    game1.setImageResource(R.drawable.game31);
                                    game2.setImageResource(R.drawable.game32);
                                    game3.setImageResource(R.drawable.game33);
                                    break;
                                case 12:
                                    game1.setImageResource(R.drawable.game34);
                                    game2.setImageResource(R.drawable.game35);
                                    game3.setImageResource(R.drawable.game36);
                                    break;
                                case 13:
                                    game1.setImageResource(R.drawable.game37);
                                    game2.setImageResource(R.drawable.game38);
                                    game3.setImageResource(R.drawable.game39);
                                    break;
                                case 14:
                                    game1.setImageResource(R.drawable.game40);
                                    game2.setImageResource(R.drawable.game41);
                                    game3.setImageResource(R.drawable.game42);
                                    break;
                                case 15:
                                    game1.setImageResource(R.drawable.game43);
                                    game2.setImageResource(R.drawable.game44);
                                    game3.setImageResource(R.drawable.game45);
                                    break;
                                case 16:
                                    game1.setImageResource(R.drawable.game46);
                                    game2.setImageResource(R.drawable.game47);
                                    game3.setImageResource(R.drawable.game48);
                                    break;
                                case 17:
                                    game1.setImageResource(R.drawable.game49);
                                    game2.setImageResource(R.drawable.game50);
                                    game3.setImageResource(R.drawable.game51);
                                    break;
                                case 18:
                                    game1.setImageResource(R.drawable.game52);
                                    game2.setImageResource(R.drawable.game53);
                                    game3.setImageResource(R.drawable.game54);
                                    break;
                                case 19:
                                    game1.setImageResource(R.drawable.game55);
                                    game2.setImageResource(R.drawable.game56);
                                    game3.setImageResource(R.drawable.game57);
                                    break;
                                case 20:
                                    game1.setImageResource(R.drawable.game58);
                                    game2.setImageResource(R.drawable.game59);
                                    game3.setImageResource(R.drawable.game60);
                                    break;
                                case 21:
                                    game1.setImageResource(R.drawable.game61);
                                    game2.setImageResource(R.drawable.game62);
                                    game3.setImageResource(R.drawable.game63);
                                    break;
                                case 22:
                                    game1.setImageResource(R.drawable.game64);
                                    game2.setImageResource(R.drawable.game65);
                                    game3.setImageResource(R.drawable.game66);
                                    break;
                                case 23:
                                    game1.setImageResource(R.drawable.game67);
                                    game2.setImageResource(R.drawable.game68);
                                    game3.setImageResource(R.drawable.game69);
                                    break;
                                case 24:
                                    game1.setImageResource(R.drawable.game70);
                                    game2.setImageResource(R.drawable.game71);
                                    game3.setImageResource(R.drawable.game72);
                                    break;
                                case 25:
                                    game1.setImageResource(R.drawable.game73);
                                    game2.setImageResource(R.drawable.game74);
                                    game3.setImageResource(R.drawable.game75);
                                    break;
                                case 26:
                                    game1.setImageResource(R.drawable.game76);
                                    game2.setImageResource(R.drawable.game77);
                                    game3.setImageResource(R.drawable.game78);
                                    break;
                                case 27:
                                    game1.setImageResource(R.drawable.game79);
                                    game2.setImageResource(R.drawable.game80);
                                    game3.setImageResource(R.drawable.game81);
                                    break;
                                case 28:
                                    game1.setImageResource(R.drawable.game82);
                                    game2.setImageResource(R.drawable.game83);
                                    game3.setImageResource(R.drawable.game84);
                                    break;
                                case 29:
                                    game1.setImageResource(R.drawable.game85);
                                    game2.setImageResource(R.drawable.game86);
                                    game3.setImageResource(R.drawable.game87);
                                    break;
                                default:
                                    game1.setImageResource(R.drawable.solarsystem);
                                    game2.setImageResource(R.drawable.solarsystem);
                                    game3.setImageResource(R.drawable.solarsystem);
                                    break;
                            }
                        }else if (image.equals("move")){
                            movepageNum--;
                            if (movepageNum <0||movepageNum==0){
                                movepageNum=1;
                            }
                            switch (movepageNum ){
                                case 1:
                                    game1.setImageResource(R.drawable.move001);
                                    game2.setImageResource(R.drawable.move002);
                                    game3.setImageResource(R.drawable.move003);
                                    break;
                                case 2:
                                    game1.setImageResource(R.drawable.move004);
                                    game2.setImageResource(R.drawable.move005);
                                    game3.setImageResource(R.drawable.move006);
                                    break;
                                case 3:
                                    game1.setImageResource(R.drawable.move007);
                                    game2.setImageResource(R.drawable.move008);
                                    game3.setImageResource(R.drawable.move009);
                                    break;
                                case 4:
                                    game1.setImageResource(R.drawable.move010);
                                    game2.setImageResource(R.drawable.solarsystem);
                                    game3.setImageResource(R.drawable.solarsystem);
                                    break;
                                default:
                                    game1.setImageResource(R.drawable.solarsystem);
                                    game2.setImageResource(R.drawable.solarsystem);
                                    game3.setImageResource(R.drawable.solarsystem);
                                    break;
                            }
                        }
                        Log.i(" JM9DVR-Cinema",  "gamepageNum:"+gamepageNum);
                        Log.i(" JM9DVR-Cinema",  "movepageNum:"+movepageNum);
                        break;
                    case "kongbugame":
                        kongbugamepageNum--;
                        if(kongbugamepageNum<1){
                            kongbugamepageNum=1;
                        }
                        int pNum=kongbugame.size();
                        //{1,2,3,4,5}
                        Log.i(" JM9DVR-Cinema",  "pNum:"+pNum);
                        Log.i(" JM9DVR-Cinema",  "kongbugamepageNum:"+kongbugamepageNum);
                        if (pNum>3&&pNum%3==0){
                            game1.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                            game2.setImageResource( kongbugame.get(kongbugamepageNum*3-2));
                            game3.setImageResource( kongbugame.get(kongbugamepageNum*3-1));
                        }else if(pNum>3&&pNum%3==1){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3+1;
                                game1.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                                game2.setImageResource( kongbugame.get(kongbugamepageNum*3-2));
                                game3.setImageResource( kongbugame.get(kongbugamepageNum*3-1));
                            }

                        }
                        else if(pNum>3&&pNum%3==2){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3+1;
                                game1.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                                game2.setImageResource(kongbugame.get(kongbugamepageNum*3-2));
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                                game2.setImageResource( kongbugame.get(kongbugamepageNum*3-2));
                                game3.setImageResource( kongbugame.get(kongbugamepageNum*3-1));
                            }

                        }
                        break;
                    case "hangtiangame":
                        hangtiangamepageNum--;
                        if(hangtiangamepageNum<1){
                            hangtiangamepageNum=1;
                        }
                       int  pNum1=hangtiangame.size();
                        if (pNum1>3&&pNum1%3==0){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3;
                            }
                            game1.setImageResource( hangtiangame.get(hangtiangamepageNum*3-3));
                            game2.setImageResource( hangtiangame.get(hangtiangamepageNum*3-2));
                            game3.setImageResource( hangtiangame.get(hangtiangamepageNum*3-1));
                        }else if(pNum1>3&&pNum1%3==1){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3+1;
                                game1.setImageResource( hangtiangame.get(hangtiangamepageNum*3-3));
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( hangtiangame.get(hangtiangamepageNum*3-3));
                                game2.setImageResource( hangtiangame.get(hangtiangamepageNum*3-2));
                                game3.setImageResource( hangtiangame.get(hangtiangamepageNum*3-1));
                            }

                        }
                        else if(pNum1>3&&pNum1%3==2){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3+1;
                                game1.setImageResource( hangtiangame.get(hangtiangamepageNum*3-3));
                                game2.setImageResource(hangtiangame.get(hangtiangamepageNum*3-2));
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( hangtiangame.get(hangtiangamepageNum*3-3));
                                game2.setImageResource( hangtiangame.get(hangtiangamepageNum*3-2));
                                game3.setImageResource( hangtiangame.get(hangtiangamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum:"+pNum1);
                        Log.i(" JM9DVR-Cinema",  "hangtiangamepageNum:"+hangtiangamepageNum);
                        break;
                    case "jixiangame":
                        jixiangamepageNum--;
                        if(jixiangamepageNum<1){
                            jixiangamepageNum=1;
                        }
                        int  pNum2=jixiangame.size();
                        if (pNum2>3&&pNum2%3==0){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3;
                            }
                            game1.setImageResource( jixiangame.get(jixiangamepageNum*3-3));
                            game2.setImageResource( jixiangame.get(jixiangamepageNum*3-2));
                            game3.setImageResource( jixiangame.get(jixiangamepageNum*3-1));
                        }else if(pNum2>3&&pNum2%3==1){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3+1;
                                game1.setImageResource( jixiangame.get(jixiangamepageNum*3-3));
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( jixiangame.get(jixiangamepageNum*3-3));
                                game2.setImageResource( jixiangame.get(jixiangamepageNum*3-2));
                                game3.setImageResource( jixiangame.get(jixiangamepageNum*3-1));
                            }

                        }
                        else if(pNum2>3&&pNum2%3==2){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3+1;
                                game1.setImageResource( jixiangame.get(jixiangamepageNum*3-3));
                                game2.setImageResource(jixiangame.get(jixiangamepageNum*3-2));
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( jixiangame.get(jixiangamepageNum*3-3));
                                game2.setImageResource( jixiangame.get(jixiangamepageNum*3-2));
                                game3.setImageResource( jixiangame.get(jixiangamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum2:"+pNum2);
                        Log.i(" JM9DVR-Cinema",  "jixiangamepageNum:"+jixiangamepageNum);
                        break;
                    case "haiyanggame":
                        haiyanggamepageNum--;
                        if(haiyanggamepageNum<1){
                            haiyanggamepageNum=1;
                        }
                        int  pNum3=haiyanggame.size();
                        if (pNum3>3&&pNum3%3==0){
                            if(haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3;
                            }
                            game1.setImageResource( haiyanggame.get(haiyanggamepageNum*3-3));
                            game2.setImageResource( haiyanggame.get(haiyanggamepageNum*3-2));
                            game3.setImageResource( haiyanggame.get(haiyanggamepageNum*3-1));
                        }else if(pNum3>3&&pNum3%3==1){
                            if(haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3+1;
                                game1.setImageResource( haiyanggame.get(haiyanggamepageNum*3-3));
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( haiyanggame.get(haiyanggamepageNum*3-3));
                                game2.setImageResource( haiyanggame.get(haiyanggamepageNum*3-2));
                                game3.setImageResource( haiyanggame.get(haiyanggamepageNum*3-1));
                            }

                        }
                        else if(pNum3>3&&pNum3%3==2){
                            if(haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3+1;
                                game1.setImageResource( haiyanggame.get(haiyanggamepageNum*3-3));
                                game2.setImageResource(haiyanggame.get(haiyanggamepageNum*3-2));
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( haiyanggame.get(haiyanggamepageNum*3-3));
                                game2.setImageResource( haiyanggame.get(haiyanggamepageNum*3-2));
                                game3.setImageResource( haiyanggame.get(haiyanggamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum3:"+pNum3);
                        Log.i(" JM9DVR-Cinema",  "haiyanggamepageNum:"+haiyanggamepageNum);
                        break;
                    case "kepugame":
                        kepugamepageNum--;
                        if(kepugamepageNum<1){
                            kepugamepageNum=1;
                        }
                        int  pNum4=kepugame.size();
                        if (pNum4>3&&pNum4%3==0){
                            if(kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3;
                            }
                            game1.setImageResource( kepugame.get(kepugamepageNum*3-3));
                            game2.setImageResource( kepugame.get(kepugamepageNum*3-2));
                            game3.setImageResource( kepugame.get(kepugamepageNum*3-1));
                        }else if(pNum4>3&&pNum4%3==1){
                            if(kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3+1;
                                game1.setImageResource( kepugame.get(kepugamepageNum*3-3));
                                game2.setImageResource(R.drawable.solarsystem);
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( kepugame.get(kepugamepageNum*3-3));
                                game2.setImageResource( kepugame.get(kepugamepageNum*3-2));
                                game3.setImageResource( kepugame.get(kepugamepageNum*3-1));
                            }

                        }
                        else if(pNum4>3&&pNum4%3==2){
                            if(kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3+1;
                                game1.setImageResource( kepugame.get(kepugamepageNum*3-3));
                                game2.setImageResource(kepugame.get(kepugamepageNum*3-2));
                                game3.setImageResource(R.drawable.solarsystem);
                            }else{
                                game1.setImageResource( kepugame.get(kepugamepageNum*3-3));
                                game2.setImageResource( kepugame.get(kepugamepageNum*3-2));
                                game3.setImageResource( kepugame.get(kepugamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum4:"+pNum4);
                        Log.i(" JM9DVR-Cinema",  "kepugamepageNum:"+kepugamepageNum);
                        break;
                }
            }
        });
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gamelieNum=1;
                movelieNum=1;
                switch (gameMulu){
                    case "allgame":
                        gamenum=gamepageNum*3-2;
                        if (image.equals("game")){
                            switch (gamepageNum ){
                                case 1:
                                    selected_imgae.setImageResource(R.drawable.game1);
                                    break;
                                case 2:
                                    selected_imgae.setImageResource(R.drawable.game4);
                                    break;
                                case 3:
                                    selected_imgae.setImageResource(R.drawable.game7);
                                    break;
                                case 4:
                                    selected_imgae.setImageResource(R.drawable.game10);
                                    break;
                                case 5:
                                    selected_imgae.setImageResource(R.drawable.game13);
                                    break;
                                case 6:
                                    selected_imgae.setImageResource(R.drawable.game16);
                                    break;
                                case 7:
                                    selected_imgae.setImageResource(R.drawable.game19);
                                    break;
                                case 8:
                                    selected_imgae.setImageResource(R.drawable.game22);
                                    break;
                                case 9:
                                    selected_imgae.setImageResource(R.drawable.game25);
                                    break;
                                case 10:
                                    selected_imgae.setImageResource(R.drawable.game28);
                                    break;
                                case 11:
                                    selected_imgae.setImageResource(R.drawable.game31);
                                    break;
                                case 12:
                                    selected_imgae.setImageResource(R.drawable.game34);
                                    break;
                                case 13:
                                    selected_imgae.setImageResource(R.drawable.game37);
                                    break;
                                case 14:
                                    selected_imgae.setImageResource(R.drawable.game40);
                                    break;
                                case 15:
                                    selected_imgae.setImageResource(R.drawable.game43);
                                    break;
                                case 16:
                                    selected_imgae.setImageResource(R.drawable.game46);
                                    break;
                                case 17:
                                    selected_imgae.setImageResource(R.drawable.game49);
                                    break;
                                case 18:
                                    selected_imgae.setImageResource(R.drawable.game52);
                                    break;
                                case 19:
                                    selected_imgae.setImageResource(R.drawable.game55);
                                    break;
                                case 20:
                                    selected_imgae.setImageResource(R.drawable.game58);
                                    break;
                                case 21:
                                    selected_imgae.setImageResource(R.drawable.game61);
                                    break;
                                case 22:
                                    selected_imgae.setImageResource(R.drawable.game64);
                                    break;
                                case 23:
                                    selected_imgae.setImageResource(R.drawable.game67);
                                    break;
                                case 24:
                                    selected_imgae.setImageResource(R.drawable.game70);
                                    break;
                                case 25:
                                    selected_imgae.setImageResource(R.drawable.game73);
                                    break;
                                case 26:
                                    selected_imgae.setImageResource(R.drawable.game76);
                                    break;
                                case 27:
                                    selected_imgae.setImageResource(R.drawable.game79);
                                    break;
                                case 28:
                                    selected_imgae.setImageResource(R.drawable.game82);
                                    break;
                                case 29:
                                    selected_imgae.setImageResource(R.drawable.game85);
                                    break;
                                default:
                                    selected_imgae.setImageResource(R.drawable.solarsystem);
                                    break;
                            }
                        }else if (image.equals("move")){
                            switch (movepageNum ){
                                case 1:
                                    selected_imgae.setImageResource(R.drawable.move001);
                                    break;
                                case 2:
                                    selected_imgae.setImageResource(R.drawable.move004);
                                    break;
                                case 3:
                                    selected_imgae.setImageResource(R.drawable.move007);
                                    break;
                                case 4:
                                    selected_imgae.setImageResource(R.drawable.move010);
                                    break;
                                default:
                                    selected_imgae.setImageResource(R.drawable.solarsystem);
                                    break;
                            }
                        }
                        Log.i(" JM9DVR-Cinema",  "gamepageNum:"+gamepageNum);
                        Log.i(" JM9DVR-Cinema",  "movepageNum:"+movepageNum);
                        break;
                    case "kongbugame":
                        selected_imgae.setImageResource( kongbugame.get(kongbugamepageNum*3-3));
                        Log.i(" JM9DVR-Cinema",  "kongbugamepageNum:"+kongbugamepageNum);
                        break;
                    case "hangtiangame":
                        selected_imgae.setImageResource(hangtiangame.get(hangtiangamepageNum*3-3));
                        Log.i(" JM9DVR-Cinema",  "hangtiangamepageNum:"+hangtiangamepageNum);
                        break;
                    case "jixiangame":
                        selected_imgae.setImageResource(jixiangame.get(jixiangamepageNum*3-3));
                        Log.i(" JM9DVR-Cinema",  "hangtiangamepageNum:"+jixiangamepageNum);
                        break;
                    case "haiyanggame":
                        selected_imgae.setImageResource(haiyanggame.get(haiyanggamepageNum*3-3));
                        Log.i(" JM9DVR-Cinema",  "haiyanggamepageNum:"+haiyanggamepageNum);
                        break;
                    case "kepugame":
                        selected_imgae.setImageResource(kepugame.get(kepugamepageNum*3-3));
                        Log.i(" JM9DVR-Cinema",  "kepugamepageNum:"+kepugamepageNum);
                        break;
                }

            }
        });
        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gamelieNum=2;
                movelieNum=2;
                switch (gameMulu){
                    case "allgame":
                        gamenum=gamepageNum*3-1;
                        if (image.equals("game")){
                            switch (gamepageNum ){
                                case 1:
                                    selected_imgae.setImageResource(R.drawable.game2);
                                    break;
                                case 2:
                                    selected_imgae.setImageResource(R.drawable.game5);
                                    break;
                                case 3:
                                    selected_imgae.setImageResource(R.drawable.game8);
                                    break;
                                case 4:
                                    selected_imgae.setImageResource(R.drawable.game11);
                                    break;
                                case 5:
                                    selected_imgae.setImageResource(R.drawable.game14);
                                    break;
                                case 6:
                                    selected_imgae.setImageResource(R.drawable.game17);
                                    break;
                                case 7:
                                    selected_imgae.setImageResource(R.drawable.game20);
                                    break;
                                case 8:
                                    selected_imgae.setImageResource(R.drawable.game23);
                                    break;
                                case 9:
                                    selected_imgae.setImageResource(R.drawable.game26);
                                    break;
                                case 10:
                                    selected_imgae.setImageResource(R.drawable.game29);
                                    break;
                                case 11:
                                    selected_imgae.setImageResource(R.drawable.game32);
                                    break;
                                case 12:
                                    selected_imgae.setImageResource(R.drawable.game35);
                                    break;
                                case 13:
                                    selected_imgae.setImageResource(R.drawable.game38);
                                    break;
                                case 14:
                                    selected_imgae.setImageResource(R.drawable.game41);
                                    break;
                                case 15:
                                    selected_imgae.setImageResource(R.drawable.game44);
                                    break;
                                case 16:
                                    selected_imgae.setImageResource(R.drawable.game47);
                                    break;
                                case 17:
                                    selected_imgae.setImageResource(R.drawable.game50);
                                    break;
                                case 18:
                                    selected_imgae.setImageResource(R.drawable.game53);
                                    break;
                                case 19:
                                    selected_imgae.setImageResource(R.drawable.game56);
                                    break;
                                case 20:
                                    selected_imgae.setImageResource(R.drawable.game59);
                                    break;
                                case 21:
                                    selected_imgae.setImageResource(R.drawable.game62);
                                    break;
                                case 22:
                                    selected_imgae.setImageResource(R.drawable.game65);
                                    break;
                                case 23:
                                    selected_imgae.setImageResource(R.drawable.game68);
                                    break;
                                case 24:
                                    selected_imgae.setImageResource(R.drawable.game71);
                                    break;
                                case 25:
                                    selected_imgae.setImageResource(R.drawable.game74);
                                    break;
                                case 26:
                                    selected_imgae.setImageResource(R.drawable.game77);
                                    break;
                                case 27:
                                    selected_imgae.setImageResource(R.drawable.game80);
                                    break;
                                case 28:
                                    selected_imgae.setImageResource(R.drawable.game83);
                                    break;
                                case 29:
                                    selected_imgae.setImageResource(R.drawable.game86);
                                    break;
                                default:
                                    selected_imgae.setImageResource(R.drawable.solarsystem);
                                    break;
                            }
                        }else if (image.equals("move")){
                            switch (movepageNum ){
                                case 1:
                                    selected_imgae.setImageResource(R.drawable.move002);
                                    break;
                                case 2:
                                    selected_imgae.setImageResource(R.drawable.move005);
                                    break;
                                case 3:
                                    selected_imgae.setImageResource(R.drawable.move008);
                                    break;
                                default:
                                    selected_imgae.setImageResource(R.drawable.solarsystem);
                                    break;
                            }
                        }
                        Log.i(" JM9DVR-Cinema",  "gamepageNum:"+gamepageNum);
                        Log.i(" JM9DVR-Cinema",  "movepageNum:"+movepageNum);
                        break;
                    case "kongbugame":
                        int pNum=kongbugame.size();
                        if (pNum>3&&pNum%3==0){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3;
                            }
                            selected_imgae.setImageResource( kongbugame.get(kongbugamepageNum*3-2));
                        }else if(pNum>3&&pNum%3==1){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource( kongbugame.get(kongbugamepageNum*3-2));
                            }
                        }
                        else if(pNum>3&&pNum%3==2){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3+1;
                                selected_imgae.setImageResource(kongbugame.get(kongbugamepageNum*3-2));
                            }else{
                                selected_imgae.setImageResource( kongbugame.get(kongbugamepageNum*3-2));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum:"+pNum);
                        Log.i(" JM9DVR-Cinema",  "kongbugamepageNum:"+kongbugamepageNum);
                        break;
                    case "hangtiangame":
                        int pNum1=hangtiangame.size();
                        if (pNum1>3&&pNum1%3==0){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3;
                            }

                            selected_imgae.setImageResource( hangtiangame.get(hangtiangamepageNum*3-2));

                        }else if(pNum1>3&&pNum1%3==1){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{

                                selected_imgae.setImageResource( hangtiangame.get(hangtiangamepageNum*3-2));

                            }

                        }
                        else if(pNum1>3&&pNum1%3==2){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3+1;
                                selected_imgae.setImageResource(hangtiangame.get(hangtiangamepageNum*3-2));
                            }else{
                                selected_imgae.setImageResource( hangtiangame.get(hangtiangamepageNum*3-2));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum1:"+pNum1);
                        Log.i(" JM9DVR-Cinema",  "hangtiangamepageNum:"+hangtiangamepageNum);
                        break;
                    case "jixiangame":

                        int pNum2=jixiangame.size();

                        if (pNum2>3&&pNum2%3==0){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3;
                            }
                            selected_imgae.setImageResource( jixiangame.get(jixiangamepageNum*3-2));
                        }else if(pNum2>3&&pNum2%3==1){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource( jixiangame.get(jixiangamepageNum*3-2));
                            }

                        }
                        else if(pNum2>3&&pNum2%3==2){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3+1;
                                selected_imgae.setImageResource(jixiangame.get(jixiangamepageNum*3-2));
                            }else{

                                selected_imgae.setImageResource( jixiangame.get(jixiangamepageNum*3-2));

                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum2:"+pNum2);
                        Log.i(" JM9DVR-Cinema",  "jixiangamepageNum:"+jixiangamepageNum);
                        break;
                    case "haiyanggame":

                        int pNum3=haiyanggame.size();

                        if (pNum3>3&&pNum3%3==0){
                            if( haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3;
                            }

                            selected_imgae.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-2));

                        }else if(pNum3>3&&pNum3%3==1){
                            if( haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-2));
                            }

                        }
                        else if(pNum3>3&&pNum3%3==2){
                            if( haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3+1;
                                selected_imgae.setImageResource( haiyanggame.get( haiyanggamepageNum*3-2));
                            }else{
                                selected_imgae.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-2));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum3:"+pNum3);
                        Log.i(" JM9DVR-Cinema",  "haiyanggamepageNum:"+ haiyanggamepageNum);
                        break;
                    case "kepugame":

                        int pNum4=kepugame.size();

                        if (pNum4>3&&pNum4%3==0){
                            if( kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3;
                            }
                            selected_imgae.setImageResource(  kepugame.get( kepugamepageNum*3-2));
                        }else if(pNum4>3&&pNum4%3==1){
                            if( kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource(  kepugame.get( kepugamepageNum*3-2));
                            }

                        }
                        else if(pNum4>3&&pNum4%3==2){
                            if( kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3+1;
                                selected_imgae.setImageResource( kepugame.get( kepugamepageNum*3-2));
                            }else{
                                selected_imgae.setImageResource(  kepugame.get( kepugamepageNum*3-2));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum4:"+pNum4);
                        Log.i(" JM9DVR-Cinema",  "kepugamepageNum:"+ kepugamepageNum);
                        break;
                }

            }
        });
        game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gamelieNum=3;
                movelieNum=3;
                switch (gameMulu){
                    case "allgame":
                        gamenum=gamepageNum*3;
                        if (image.equals("game")){
                            switch (gamepageNum ){
                                case 1:
                                    selected_imgae.setImageResource(R.drawable.game3);
                                    break;
                                case 2:
                                    selected_imgae.setImageResource(R.drawable.game6);
                                    break;
                                case 3:
                                    selected_imgae.setImageResource(R.drawable.game9);
                                    break;
                                case 4:
                                    selected_imgae.setImageResource(R.drawable.game12);
                                    break;
                                case 5:
                                    selected_imgae.setImageResource(R.drawable.game15);
                                    break;
                                case 6:
                                    selected_imgae.setImageResource(R.drawable.game18);
                                    break;
                                case 7:
                                    selected_imgae.setImageResource(R.drawable.game21);
                                    break;
                                case 8:
                                    selected_imgae.setImageResource(R.drawable.game24);
                                    break;
                                case 9:
                                    selected_imgae.setImageResource(R.drawable.game27);
                                    break;
                                case 10:
                                    selected_imgae.setImageResource(R.drawable.game30);
                                    break;
                                case 11:
                                    selected_imgae.setImageResource(R.drawable.game33);
                                    break;
                                case 12:
                                    selected_imgae.setImageResource(R.drawable.game36);
                                    break;
                                case 13:
                                    selected_imgae.setImageResource(R.drawable.game39);
                                    break;
                                case 14:
                                    selected_imgae.setImageResource(R.drawable.game42);
                                    break;
                                case 15:
                                    selected_imgae.setImageResource(R.drawable.game45);
                                    break;
                                case 16:
                                    selected_imgae.setImageResource(R.drawable.game48);
                                    break;
                                case 17:
                                    selected_imgae.setImageResource(R.drawable.game51);
                                    break;
                                case 18:
                                    selected_imgae.setImageResource(R.drawable.game54);
                                    break;
                                case 19:
                                    selected_imgae.setImageResource(R.drawable.game57);
                                    break;
                                case 20:
                                    selected_imgae.setImageResource(R.drawable.game60);
                                    break;
                                case 21:
                                    selected_imgae.setImageResource(R.drawable.game63);
                                    break;
                                case 22:
                                    selected_imgae.setImageResource(R.drawable.game66);
                                    break;
                                case 23:
                                    selected_imgae.setImageResource(R.drawable.game69);
                                    break;
                                case 24:
                                    selected_imgae.setImageResource(R.drawable.game72);
                                    break;
                                case 25:
                                    selected_imgae.setImageResource(R.drawable.game75);
                                    break;
                                case 26:
                                    selected_imgae.setImageResource(R.drawable.game78);
                                    break;
                                case 27:
                                    selected_imgae.setImageResource(R.drawable.game81);
                                    break;
                                case 28:
                                    selected_imgae.setImageResource(R.drawable.game84);
                                    break;
                                case 29:
                                    selected_imgae.setImageResource(R.drawable.game87);
                                    break;
                                default:
                                    selected_imgae.setImageResource(R.drawable.solarsystem);
                                    break;
                            }
                        }else if (image.equals("move")){
                            switch (movepageNum ){
                                case 1:
                                    selected_imgae.setImageResource(R.drawable.move003);
                                    break;
                                case 2:
                                    selected_imgae.setImageResource(R.drawable.move006);
                                    break;
                                case 3:
                                    selected_imgae.setImageResource(R.drawable.move009);
                                    break;
                                default:
                                    selected_imgae.setImageResource(R.drawable.solarsystem);
                                    break;
                            }
                        }
                        Log.i(" JM9DVR-Cinema",  "gamepageNum:"+gamepageNum);
                        Log.i(" JM9DVR-Cinema",  "movepageNum:"+movepageNum);
                        break;
                    case "kongbugame":
                        int pNum=kongbugame.size();
                        if (pNum>3&&pNum%3==0){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3;
                            }
                            selected_imgae.setImageResource( kongbugame.get(kongbugamepageNum*3-1));
                        }else if(pNum>3&&pNum%3==1){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource( kongbugame.get(kongbugamepageNum*3-1));
                            }

                        }
                        else if(pNum>3&&pNum%3==2){
                            if(kongbugamepageNum>pNum/3){
                                kongbugamepageNum=pNum/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource( kongbugame.get(kongbugamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum:"+pNum);
                        Log.i(" JM9DVR-Cinema",  "kongbugamepageNum:"+kongbugamepageNum);
                        break;
                    case "hangtiangame":
                        int pNum1=hangtiangame.size();
                        if (pNum1>3&&pNum1%3==0){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3;
                            }
                            selected_imgae.setImageResource( hangtiangame.get(hangtiangamepageNum*3-1));
                        }else if(pNum1>3&&pNum1%3==1){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource( hangtiangame.get(hangtiangamepageNum*3-1));
                            }

                        }
                        else if(pNum1>3&&pNum1%3==2){
                            if(hangtiangamepageNum>pNum1/3){
                                hangtiangamepageNum=pNum1/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource( hangtiangame.get(hangtiangamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum1:"+pNum1);
                        Log.i(" JM9DVR-Cinema",  "hangtiangamepageNum:"+hangtiangamepageNum);
                        break;
                    case "jixiangame":

                        int pNum2=jixiangame.size();

                        if (pNum2>3&&pNum2%3==0){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3;
                            }
                            selected_imgae.setImageResource( jixiangame.get(jixiangamepageNum*3-1));
                        }else if(pNum2>3&&pNum2%3==1){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource( jixiangame.get(jixiangamepageNum*3-1));
                            }

                        }
                        else if(pNum2>3&&pNum2%3==2){
                            if(jixiangamepageNum>pNum2/3){
                                jixiangamepageNum=pNum2/3+1;

                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource( jixiangame.get(jixiangamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum2:"+pNum2);
                        Log.i(" JM9DVR-Cinema",  "jixiangamepageNum:"+jixiangamepageNum);
                        break;
                    case "haiyanggame":

                        int pNum3=haiyanggame.size();

                        if (pNum3>3&&pNum3%3==0){
                            if( haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3;
                            }
                            selected_imgae.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-1));
                        }else if(pNum3>3&&pNum3%3==1){
                            if( haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-1));
                            }

                        }
                        else if(pNum3>3&&pNum3%3==2){
                            if( haiyanggamepageNum>pNum3/3){
                                haiyanggamepageNum=pNum3/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource(  haiyanggame.get( haiyanggamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum3:"+pNum3);
                        Log.i(" JM9DVR-Cinema",  "haiyanggamepageNum:"+ haiyanggamepageNum);
                        break;
                    case "kepugame":

                        int pNum4=kepugame.size();

                        if (pNum4>3&&pNum4%3==0){
                            if( kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3;
                            }
                            selected_imgae.setImageResource( kepugame.get( kepugamepageNum*3-1));
                        }else if(pNum4>3&&pNum4%3==1){
                            if( kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource(  kepugame.get( kepugamepageNum*3-1));
                            }

                        }
                        else if(pNum4>3&&pNum4%3==2){
                            if( kepugamepageNum>pNum4/3){
                                kepugamepageNum=pNum4/3+1;
                                selected_imgae.setImageResource(R.drawable.solarsystem);
                            }else{
                                selected_imgae.setImageResource(  kepugame.get( kepugamepageNum*3-1));
                            }

                        }
                        Log.i(" JM9DVR-Cinema",  "pNum4:"+pNum4);
                        Log.i(" JM9DVR-Cinema",  "kepugamepageNum:"+ kepugamepageNum);
                        break;
                }

            }
        });
        who_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if(checkedId == IP1.getId()){
                    Checkednum=1;
                    SERVERIP=IP1.getText().toString();
                }
                else if(checkedId == IP2.getId()){
                    Checkednum=2;
                    SERVERIP=IP2.getText().toString();
                }
                else if(checkedId == IP3.getId()){
                    Checkednum=3;
                    SERVERIP=IP3.getText().toString();
                }
                else if(checkedId == IP4.getId()){
                    Checkednum=4;
                    SERVERIP=IP4.getText().toString();
                }
                else if(checkedId == IP5.getId()){
                    Checkednum=5;
                    SERVERIP=IP5.getText().toString();
                }
                else if(checkedId == IP6.getId()){
                    Checkednum=6;
                    SERVERIP=IP6.getText().toString();
                }
            }
        });
        qidong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IP=scanip();
                String IPbuffer="";
                for (int i=0;i<IP.size();i++){
                    IPbuffer+="\n"+IP.get(i);
                    if (IP.get(i).equals(getString(R.string.ip1))){
                        CB1.setVisibility(View.VISIBLE);
                        Log.i("List","ip1");
                    }else if (IP.get(i).equals(getString(R.string.ip2))){
                        CB2.setVisibility(View.VISIBLE);
                        Log.i("List","ip2");
                    }
                    else if (IP.get(i).equals(getString(R.string.ip3))){
                        CB3.setVisibility(View.VISIBLE);
                        Log.i("List","ip3");
                    }
                    else if (IP.get(i).equals(getString(R.string.ip4))){
                        CB4.setVisibility(View.VISIBLE);
                        Log.i("List","ip3");
                    }else if (IP.get(i).equals(getString(R.string.ip5))){
                        CB5.setVisibility(View.VISIBLE);
                        Log.i("List","ip5");
                    }else if (IP.get(i).equals(getString(R.string.ip6))){
                        CB6.setVisibility(View.VISIBLE);
                        Log.i("List","ip6");
                    }else if (IP.get(i).equals(getString(R.string.ip7))){
                        CB6.setVisibility(View.VISIBLE);
                        Log.i("List","ip6");
                    }

                }
                // Toast.makeText(MainActivity.this,"IPList:\n"+IPbuffer, Toast.LENGTH_SHORT).show();
                Log.i("IPList","IPList:"+IPbuffer);
               // IP.clear();

                String Password=password.getText().toString();
                Log.i("Password", "PassWord: "  + Password);
                if (Password.equals(getString(R.string.password))) {
                    bt_close.setEnabled(false);
                    bt_start.setEnabled(true);
                }else{
                    Toast.makeText(MainActivity.this,"密码错误请重新输入密码！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        changeip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changeIP=edit_ip.getText().toString();
                Log.i("changeIP", "IP: "  + changeIP);
               if (changeIP!=null){
                   switch ( Checkednum){
                       case 1:
                           IP1.setText(changeIP);
                           break;
                       case 2:
                           IP2.setText(changeIP);
                           break;
                       case 3:
                           IP3.setText(changeIP);
                           break;

                       case 4:
                           IP4.setText(changeIP);
                           break;
                       case 5:
                           IP5.setText(changeIP);
                           break;
                       case 6:
                           IP6.setText(changeIP);
                           break;
                   }
               }
                who_group.clearCheck();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(Socket!=null)
               {Socket.close();}
               destory();
               System.exit(0);
           }
       });
        bt_qianjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UDPsendBuf="";
                TSend = new Thread(){
                    public void run(){
                        String str = UDPsendBuf;//获取输入框的信息
                        Log.v("fist","可以发送："+ str);
                        SendMsg(str,SERVERIP);   //只要一按按钮，就发送数据
                        Log.v("fist","设备IP："+ SERVERIP);
                    }
                };
                TSend.start();
            }
        });
        bt_houtui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UDPsendBuf="";
                TSend = new Thread(){
                    public void run(){
                        String str = UDPsendBuf;//获取输入框的信息
                        Log.v("fist","可以发送："+ str);
                        SendMsg(str,SERVERIP);   //只要一按按钮，就发送数据
                        Log.v("fist","设备IP："+ SERVERIP);
                    }
                };
                TSend.start();
            }
        });
        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // UDPsendBuf="";
                TSend = new Thread(){
                    public void run(){
                       // String str = UDPsendBuf;//获取输入框的信息
                        Log.v("fist","可以发送：STOP");
                        SendMsg("STOP",SERVERIP);   //只要一按按钮，就发送数据
                        Log.v("fist","设备IP："+ SERVERIP);
                    }
                };
                TSend.start();
            }
        });

        allgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMulu="allgame";

            }
        });
        kongbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMulu="kongbugame";

                game1.setImageResource( kongbugame.get(0));
                game2.setImageResource( kongbugame.get(1));
                game3.setImageResource( kongbugame.get(2));
            }
        });
        hangtian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMulu="hangtiangame";

                game1.setImageResource( hangtiangame.get(0));
                game2.setImageResource( hangtiangame.get(1));
                game3.setImageResource( hangtiangame.get(2));
            }
        });
        jixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMulu="jixiangame";
                game1.setImageResource( jixiangame.get(0));
                game2.setImageResource( jixiangame.get(1));
                game3.setImageResource( jixiangame.get(2));
            }
        });
        haiyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMulu="haiyanggame";
                game1.setImageResource( haiyanggame.get(0));
                game2.setImageResource( haiyanggame.get(1));
                game3.setImageResource( haiyanggame.get(2));
            }
        });
        kepu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMulu="kepugame";
                game1.setImageResource( kepugame.get(0));
                game2.setImageResource( kepugame.get(1));
                game3.setImageResource( kepugame.get(2));
            }
        });
        countCB=cheackboxip.getChildCount();
        for (int i=0;i<countCB;i++){
           CheckBox box=(CheckBox) cheackboxip.getChildAt(i);
           box.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isCheckede){
                 //  Toast.makeText(MainActivity.this,"CheckIP:"+box.getText().toString()+"isCheckede:"+isCheckede, Toast.LENGTH_SHORT).show();
                   if (isCheckede){
                      // IP.add(box.getText().toString());
                       SERVERIP=box.getText().toString();
                   }
                   else{
                     //IP.remove(box.getText().toString());
                   }
               }
           });
       }
    }
    private void receiveMsg() throws Exception{
        //Android端的接收方法
        try {
            while(recrState){
                Log.v("fist", "手机数据接收开始");
                try {
                    //   Socket = new DatagramSocket(DuanKouFa);
                    if(Socket==null){
                        Socket = new DatagramSocket(null);
                        Socket.setReuseAddress(true);
                        Socket.bind(new InetSocketAddress(DuanKouFa));
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                byte[]  data=new byte[1024];
                DatagramPacket pack = new DatagramPacket(data, data.length);
                Socket.receive(pack);			//关键问题： 在这里卡住了！收不到消息
                int len = pack.getLength();
                byte[] tong = new byte[len];
                for(int i = 0 ; i < len ; i++ )
                {  tong[i] = data[i];}
                String str=new String(tong,"GBK");
                UDPBufA=str;
                UDPBuf+="\n接收成功，消息为:"+str;
              //UDPbuffer.setText(UDPBuf);
                Log.v("fist", "接收成功，消息为: "  + str );
                String s = new String(tong,"GBK");
                Message message=handler.obtainMessage();
                message.obj=s;
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            UDPBuf+="\n接收异常 ，异常为 ： "  + e +"\n"+e.getLocalizedMessage();
           // UDPbuffer.setText(UDPBuf);
            Log.v("fist", " 接收异常 ，异常为 ：   "  + e +"\n"+e.getLocalizedMessage());
//			Toast.makeText(this, "接收异常" + e, Toast.LENGTH_LONG).show();
        }
    }
    private void SendMsg(String msg,String IP){
        //Android端的发送方法
        byte[]  data=new byte[1024];
        Log.v("fist", "开始发送！");
        try {
            if(Socket==null){
                Socket = new DatagramSocket(null);
                Socket.setReuseAddress(true);
                Socket.bind(new InetSocketAddress(DuanKouFa));
            }
            Log.v("SERVERIP", "SERVERIP:"+ IP);
            InetAddress serverAddress = InetAddress.getByName(IP);
            URLEncoder.encode(msg , "GBK");
            data = msg.getBytes("GBK");//把要发送的数据转化为Byte形式
            DatagramPacket package1 = new DatagramPacket(data,msg.getBytes("GBK").length,
                    serverAddress,DuanKouShou);//设置一个发送包（相当于快递，写了快递的地址和编号）
            Socket.send(package1);//发送快递
            UDPBuf+="\n发送成功，消息为:"+msg;
            //UDPbuffer.setText(UDPBuf);
            Log.v("fist", "发送成功！");
            String s = new String(data,"GBK");
            Message message=handler.obtainMessage();
            message.obj=s;
            handler.sendMessage(message);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            UDPBuf+="\n发送异常"+ e;
           // UDPbuffer.setText(UDPBuf);
            Log.v("fist", "发送异常"+ e);
            e.printStackTrace();
        }
    }

    private void showImage(String image) {

        if (image.equals("game")){
            game1.setImageResource(R.drawable.game1);
            game2.setImageResource(R.drawable.game2);
            game3.setImageResource(R.drawable.game3);
        }else if (image.equals("move")){
            game1.setImageResource(R.drawable.move001);
            game2.setImageResource(R.drawable.move002);
            game3.setImageResource(R.drawable.move003);
        }
    }
    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onStop() {
        if(Socket!=null)
            System.exit(0);
        super.onStop();
    }
    /** 核心池大小 **/
    private static final int CORE_POOL_SIZE = 1;
    /** 线程池最大线程数 **/
    private static final int MAX_IMUM_POOL_SIZE = 255;
    private String mDevAddress;// 本机IP地址-完整
    private String mLocAddress;// 局域网IP地址头,如：192.168.1.
    private Runtime mRun = Runtime.getRuntime();// 获取当前运行环境，来执行ping，相当于windows的cmd
    private Process mProcess = null;// 进程
    private String mPing = "ping -c 1 -w 3 ";// 其中 -c 1为发送的次数，-w 表示发送后等待响应的时间
    private List<String> mIpList = new ArrayList<String>();// ping成功的IP地址
    private ThreadPoolExecutor mExecutor;//线程池对象
    private List<String>  scanip(){
            /**
             * TODO<扫描局域网内ip，找到对应服务器>
             *
             * @return void
             */

                mDevAddress = getHostIP();// 获取本机IP地址
                mLocAddress = getLocAddrIndex(mDevAddress);// 获取本地ip前缀

                if (TextUtils.isEmpty(mLocAddress)) {
                    Log.i("ScanIP","扫描失败，请检查wifi网络");
                    return null;
                }

                /**
                 * 1.核心池大小 2.线程池最大线程数 3.表示线程没有任务执行时最多保持多久时间会终止
                 * 4.参数keepAliveTime的时间单位，有7种取值,当前为毫秒
                 * 5.一个阻塞队列，用来存储等待执行的任务，这个参数的选择也很重要，会对线程池的运行过程产生重大影响
                 * ，一般来说，这里的阻塞队列有以下几种选择：
                 */
                mExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_IMUM_POOL_SIZE,
                        50,TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(
                        CORE_POOL_SIZE));
                // 新建线程池
                for (int i = 1; i < 255; i++) {// 创建256个线程分别去ping
                    final int lastAddress = i;// 存放ip最后一位地址 1-100
                    Runnable run = new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            String ping = mPing + mLocAddress
                                    + lastAddress;
                            String currnetIp = mLocAddress + lastAddress;
                            if (mDevAddress.equals(currnetIp)) // 如果与本机IP地址相同,跳过
                                return;
                            try {
                                mProcess = mRun.exec(ping);
                                int result = mProcess.waitFor();
//                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         ALog.e(TAG, "正在扫描的IP地址为：" + currnetIp + "返回值为：" + result);
                                if (result == 0) {
//                            ALog.e(TAG, "扫描成功,Ip地址为：" + currnetIp);
                                    mIpList.add(currnetIp);
                                } else {
                                    // 扫描失败
//                            ALog.e(TAG, "扫描失败");
                                }
                            } catch (Exception e) {
                                Log.i("ScanIP", "扫描异常" + e.toString());
                            } finally {
                                if (mProcess != null)
                                    mProcess.destroy();
                            }
                        }
                    };
                    mExecutor.execute(run);
                }
                mExecutor.shutdown();
                while (true) {
                    try {
                        if (mExecutor.isTerminated()) {// 扫描结束,开始验证
                            Log.i("ScanIP", "扫描结束,总共成功扫描到" + mIpList.size() + "个设备.");
                          //  Log.i("ScanIP","设备列表："+new Gson().toJson(mIpList));
                            return mIpList;
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }
            }

    /**
     * TODO<销毁正在执行的线程池>
     *
     * @return void
     */
    public void destory() {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
        }
    }

    /**
     * TODO<获取本地ip地址>
     *
     * @return String
     */
    private String getLocAddress() {
        String ipaddress = "";

        try {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements()) {
                NetworkInterface networks = en.nextElement();
                // 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> address = networks.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (address.hasMoreElements()) {
                    InetAddress ip = address.nextElement();
                    if (!ip.isLoopbackAddress()
                            && (ip instanceof Inet4Address)) {
                        ipaddress = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("ScanIP", "获取本地ip地址失败");
            e.printStackTrace();
        }
        Log.i("ScanIP", "本机IP:" + ipaddress);
        return ipaddress;
    }
    /**
     * 获取ip地址
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("ScanIP", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }
    /**
     * TODO<获取本机IP前缀>
     *
     * @param devAddress
     *            // 本机IP地址
     * @return String
     */
    private String getLocAddrIndex(String devAddress) {
        if (!devAddress.equals("")) {
            return devAddress.substring(0, devAddress.lastIndexOf(".") + 1);
        }
        return null;
    }

    /**
     * 时间判断
     */
    private void limited() {
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。

        String time = t.year + "年 " + (t.month + 1) + "月 " + t.monthDay + "日 "
                + t.hour + "h " + t.minute + "m " + t.second;

        Log.e("msg", time);

        int mYear=2022,myMonth=3,myMonthDay=22;
        if(t.year==mYear && (t.month+1)==myMonth && t.monthDay>=myMonthDay)
        {
            closeApp();
        }else if (t.year>mYear){
            closeApp();
        }else if (t.year==mYear && (t.month+1)>myMonth){
            closeApp();
        }
    }

    /**
     * 退出APP
     */
    private void closeApp(){
        Toast.makeText(MainActivity.this,"试用版时间到期自动退出，请联系软件作者微信:........" ,
                Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable(){   //延迟执行
            @Override
            public void run(){
                android.os.Process
                        .killProcess(android.os.Process
                                .myPid()); // 终止线程
            }
        }, 5000);
    }

}