package com.hooooong.rxbasic04;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.Random;

import io.reactivex.Observable;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);
        EditText editId = findViewById(R.id.editId);
        EditText editPassword = findViewById(R.id.editPassword);

        // 0. 로그인 체크하기
        // RxTextView 의 textChangeEvents 를 사용하여 변화에 반응하는 Observale 을 생성
        Observable<TextViewTextChangeEvent> idEmitter = RxTextView.textChangeEvents(editId);
        Observable<TextViewTextChangeEvent> pwEmitter = RxTextView.textChangeEvents(editPassword);
        // 조건 ID 가 5자 이상이고, PW 가 8자 이상이면 btnSignIn 의 enable 이 true 로 변경

        Observable.combineLatest(
                idEmitter,
                pwEmitter,
                (item1, item2) -> item1.text().length() >= 6 && item2.text().length() >= 8)
                .subscribe(flag -> findViewById(R.id.btnSignIn)
                        .setEnabled(flag));

        // 1. editText 에 입력되는 값을 체크해서 실시간으로 Log 를 뿌려준다.
        // Key 를 입력받으면 Subscribe 가 호출이 된다.
        RxTextView.textChangeEvents(editText)
                .subscribe(ch -> Log.e(TAG, "onCreate: " + ch.text()));

        // 2. 버튼을 클릭하면 editText 에 Random 숫자를 입력]
        RxView.clicks(findViewById(R.id.button))
                // button 에는
                // Object Type 의 Object 가 Return 되는데, 이를 문자 타입으로 변경
                .map(button -> new Random().nextInt(10))
                // Object 에는
                // Map 에서 변경된 타입을 받게된다.
                // (Lambda 식의 장점이자 단점인 Type 을 설정안해줘도 된다.)
                .subscribe(object -> {
                    /*
                    Random random = new Random();
                    String num = "Random : " + random.nextInt(10);
                    */
                    editText.setText(object);
                });
    }
}
