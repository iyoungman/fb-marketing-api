# Product Name
> Facebook Marketing Api

## Motivation
페이스북 마케팅 API 연동

## Require
* 광고 계정이 생성되어 있어야한다
* 모든 요청은 광고 계정의 Access Token이 필요하다

## Fuction
* 광고 계정내에 캠페인 저장, 수정, 삭제, 리스트 조회
* 캠페인내에 광고세트 저장, 수정, 삭제, 리스트 조회
* 잠재고객 생성, 수정, 삭제, 리스트 조회, 생성된 잠재고객에 자세한 유저정보 저장(핸드폰 번호)

## Result
* API를 통해 실제 페이스북에 생성된 캠페인, 광고 세트 결과는 페이스북 광고 관리자에서 확인할 수 있다
https://www.facebook.com/ads/manager  
* API를 통해 실제 페이스북에 생성된 잠재 고객(맞춤 타겟) 결과는 페이스북 자산 라이브러리-타겟메뉴에서 확인 할 수 있다  
https://business.facebook.com/asset-library/audience  

## 광고 개제 Flow
* API를 통해 캠페인, 광고 세트(잠재 고객 세팅 되어있는) 생성
* 비즈니스 관리자에서 개제할 광고 세트 선택 후 광고 탭 클릭
![image](https://user-images.githubusercontent.com/25604495/59740328-bc1ec080-92a2-11e9-8f54-24198fe63782.png)  
* 광고 만들기 클릭
![image](https://user-images.githubusercontent.com/25604495/59740350-cccf3680-92a2-11e9-9c30-9c74424608c8.png)  
* 광고 이름 입력 후 임시저장
![image](https://user-images.githubusercontent.com/25604495/59740399-f5573080-92a2-11e9-9d3c-9a0b000f007e.png)  
* 수정 클릭
![image](https://user-images.githubusercontent.com/25604495/59740483-32232780-92a3-11e9-9a37-c5a97f66e9f1.png) 
* 
![image](https://user-images.githubusercontent.com/25604495/59740600-87f7cf80-92a3-11e9-96c4-2ed5828dd1b9.png)
![image](https://user-images.githubusercontent.com/25604495/59740619-95ad5500-92a3-11e9-841e-40333c075efd.png)

## Details
* Swagger를 통해 Request와 Response 확인 가능  
http://localhost:8080/swagger-ui.html

## Reference
* [페이스북 마케팅 API 사용 참고1](https://developers.facebook.com/docs/marketing-api/buying-api)  
* [페이스북 마케팅 API 사용 참고2](https://github.com/facebook/facebook-java-business-sdk) -> 예전에 나온 버전에 대한 내용이라 일부 내용만 참고  
* [맞춤타겟에 핸드폰 번호 저장 참고](https://github.com/15520528/fbapi/blob/455d5f1bc078a7911a76b4de91bebf89031c4750/src/main/java/facebook/FacebookCustomAudience.java)  
