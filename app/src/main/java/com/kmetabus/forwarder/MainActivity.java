package com.kmetabus.forwarder;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class MainActivity extends AppCompatActivity  {

    private static final long DOUBLE_BACK_PRESS_INTERVAL = 2000; // 2초 간격으로 뒤로 가기 버튼을 눌렀을 때 종료
    private long lastBackPressTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackPressTime > DOUBLE_BACK_PRESS_INTERVAL) {
            // 두 번째 뒤로 가기 버튼 클릭이 설정된 간격 내에 발생하지 않으면 토스트 메시지를 표시
            Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            lastBackPressTime = currentTime;
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(R.id.mainFragment);
        } else {
            // 두 번째 뒤로 가기 버튼 클릭이 설정된 간격 내에 발생하면 앱을 종료
            super.onBackPressed();
            finishAffinity();
        }
    }
}
