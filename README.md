Android Programing
----------------------------------------------------
### 2017.11.16 34일차

#### 예제
____________________________________________________

- [RxJava 와 Retrofit2 를 사용하여 서울 기상 관측 정보 가져오기](https://github.com/Hooooong/DAY41_SeoulWeather)

#### 공부정리
____________________________________________________

##### __RxBinding__

- RxBinding 이란?

  > JakeWharton 이 만든 RxJava 의 Binding Library 이다. View에 대한 반응형 Library로 RxJava 와 함께 사용한다.

- RxBinding 설정  

  ```xml
  dependencies {
      implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
      implementation 'com.jakewharton.rxbinding2:rxbinding-appcompat-v7:2.0.0'
  }
  ```

- RxBinding 사용법

  - View에 대해 반응형 Event 메소드를 적용하여, 반응할 때 RxJava 의 `subscribe()` 메소드를 사용하여 값을 활용한다.

  ```java
  // 0. 로그인 체크하기
  // RxTextView 의 textChangeEvents 를 사용하여 변화에 반응하는 Observale 을 생성
  Observable<TextViewTextChangeEvent> idEmitter = RxTextView.textChangeEvents(editId);
  Observable<TextViewTextChangeEvent> pwEmitter = RxTextView.textChangeEvents(editPassword);
  // 조건 ID 가 5자 이상이고, PW 가 8자 이상이면 btnSignIn 의 enable 이 true 로 변경

  // Observale.combineLatest()
  Observable.combineLatest(
          idEmitter,
          pwEmitter,
          (id, pw) -> id.text().length() >= 5 && pw.text().length() >= 8)
          .subscribe(flag -> findViewById(R.id.btnSignIn).setEnabled(flag));


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
              editText.setText(object);
          });
  ```
