package com.example.mobile_adproject.LoginAndRegisterFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobile_adproject.R;


public class LoginFragment extends Fragment {
    private EditText username;
    private EditText password;
    private Button login;
/*    private static final int GET=1;
    private OkHttpClient client = new OkHttpClient();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){

                case GET:
                    //获取数据
                    username.setText((String) msg.obj);
            }
        }
    };*/
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login,container,false);
        username=root.findViewById(R.id.username_login);
        password=root.findViewById(R.id.password_login);
        login=root.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("abc")&&password.getText().toString().equals("123456")) {
                    Toast.makeText(getContext(), "Login Success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_LONG).show();

                    /*getDataFromGet();//点击事件*/

                }
            }
        });

        return root;

    }

/*    //子线程,运行get请求，但是不能直接显示文本,携带数据用的是message
    private void getDataFromGet(){
        new Thread(){
            @Override
            public void run(){
                super.run();
                try {
                    String result = get("https://adt8api.azurewebsites.net/swagger-ui/index.html#/staff-controller/getAll_1");
                    Log.e("TAG",result);
                    Message msg=Message.obtain();
                    msg.what=GET;
                    msg.obj=result;
                    handler.sendMessage(msg);//返回message之后发到handler这边来
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }*/

/*//get请求
    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();//构建url

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();//返回
        }
    }*/

}
