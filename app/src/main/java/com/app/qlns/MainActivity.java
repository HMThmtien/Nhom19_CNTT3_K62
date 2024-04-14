package com.app.qlns;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.qlns.databinding.ActivityMainBinding;
import com.app.qlns.db.UserDAO;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding viewBinding;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userNameLogin = MySharedPreferences.getUserName(this);
        if(userNameLogin != null){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        EdgeToEdge.enable(this);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userDAO = new UserDAO(this);
        viewBinding.btnLogin.setOnClickListener(v->{
            String username = viewBinding.edtUsername.getText().toString();
            String password = viewBinding.edtPassword.getText().toString();
            if (login(username, password)) {
                viewBinding.tvMessage.setText("Đăng nhập thành công");
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                viewBinding.tvMessage.setTextColor(getResources().getColor(R.color.red));
                viewBinding.tvMessage.setText("Tài khoản hoặc mật khẩu không chính xác");
            }
        });
        viewBinding.tvRegister.setOnClickListener(v->{
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }

    boolean login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            viewBinding.tvMessage.setText("Vui lòng nhập đầy đủ thông tin");
            return false;
        }
        return userDAO.loginUser(username, password);
    }
}