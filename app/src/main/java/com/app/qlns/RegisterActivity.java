package com.app.qlns;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.qlns.databinding.ActivityRegisterBinding;
import com.app.qlns.db.UserDAO;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding viewBinding;
    UserDAO userDAO ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        viewBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userDAO = new UserDAO(this);
        viewBinding.tvLogin.setOnClickListener(v->{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        viewBinding.btnRegister.setOnClickListener(v->{
            String username = viewBinding.edtUsername.getText().toString();
            String phone = viewBinding.edtPhoneNumber.getText().toString();
            String password = viewBinding.edtPassword.getText().toString();
            if(validateInfo(username, phone, password)){
                boolean result = userDAO.addUser(username, phone, password, "user");
                if(result){
                    Toast.makeText(this, "Đăng kí thành công vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                }
             }
        });
    }
    boolean validateInfo(String username, String phone, String password){
        boolean isAccept = viewBinding.cbPolicy.isChecked();
        if (username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            viewBinding.tvMessage.setText("Vui lòng nhập đầy đủ thông tin");
            return false;
        }
        if(!isAccept){
            viewBinding.tvMessage.setText("Vui lòng chấp nhận điều khoản");
            return false;
        }
        return true;
    }
}