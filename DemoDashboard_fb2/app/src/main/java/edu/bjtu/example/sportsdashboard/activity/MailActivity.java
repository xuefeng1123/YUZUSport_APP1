package edu.bjtu.example.sportsdashboard.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.bjtu.example.sportsdashboard.R;
import edu.bjtu.example.sportsdashboard.mail_model.MailInfo;
import edu.bjtu.example.sportsdashboard.mail_model.SendMailUtil;

public class MailActivity extends AppCompatActivity {

    TextView send;
    TextView recive;
    TextView theme;
    TextView context;
    TextView password;
    Button send_button;

    private  String send_mail;
    private String recive_mail;
    private String theme_mail;
    private String context_mail;
    private String password_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmail);

        send =(EditText)findViewById(R.id.send);
        recive =(EditText)findViewById(R.id.recive);
        theme =(EditText)findViewById(R.id.theme);
        context =(EditText)findViewById(R.id.context);
        password = (EditText)findViewById(R.id.password);

        send_button = (Button)findViewById(R.id.send_button);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MailActivity.this.sendMail())
                    Toast.makeText(MailActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MailActivity.this,"发送失败",Toast.LENGTH_SHORT).show();

                finish();
            }
        });


    }

    protected boolean sendMail(){

        send_mail = send.getText().toString();
        recive_mail = recive.getText().toString();
        theme_mail = theme.getText().toString();
        context_mail = context.getText().toString();
        password_mail = password.getText().toString();

        SendMailUtil sendmailutil = new SendMailUtil(send_mail,password_mail);
        MailInfo mailInfo = sendmailutil.creatMail(send_mail,password_mail,recive_mail,theme_mail,context_mail);
        sendmailutil.send(mailInfo);
        return true;
    }


}
