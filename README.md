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
**1** API를 통해 캠페인, 광고 세트(잠재 고객 세팅 되어있는) 생성  
---
**2** 비즈니스 관리자에서 개제할 광고 세트 선택 후 광고 탭 클릭  
<img src="/img/1.PNG" width="400" height="200">  
---
**3** 광고 만들기 클릭  
<img src="/img/2.PNG" width="300" height="200">  
---
**4** 광고 이름 입력 후 임시저장  
<img src="/img/3.PNG" width="300" height="200">  
---
**5** 생성된 광고 수정 클릭  
---
**6** 광고를 개제할 페이스북 페이지가 개설되어 있어야 한다  
<img src="/img/7.PNG" width="300" height="200">  
---
**7** 아래와 같이 '**광고 만들기**' 와 '**기존 게시물 사용**' 중 택한다  
<img src="/img/4.PNG" width="300" height="200">  
---
**8** 위에서 택한 페이지에 기존에 만들어 놓은 게시물이 없어**광고 만들기**를 택할 경우 웹 사이트 링크를 필수로 넣어야한다(나머지는 선택 사항)  
<img src="/img/5.PNG" width="300" height="200"><img src="/img/6.PNG" width="300" height="200">  
---  
**9** 위에서 택한 페이지에 기존에 만들어 놓은 게시물이 있다면 **기존 게시물 사용**을 택한 후 게시물 변경에서 게시물 선택  
<img src="/img/9.PNG" width="300" height="200"><img src="/img/8.PNG" width="300" height="200">  
---  

## Details
* Swagger를 통해 Request와 Response 확인 가능  
http://localhost:8080/swagger-ui.html

## Reference
* [페이스북 마케팅 API 사용 참고1](https://developers.facebook.com/docs/marketing-api/buying-api)  
* [페이스북 마케팅 API 사용 참고2](https://github.com/facebook/facebook-java-business-sdk) -> 예전에 나온 버전에 대한 내용이라 일부 내용만 참고  
* [맞춤타겟에 핸드폰 번호 저장 참고](https://github.com/15520528/fbapi/blob/455d5f1bc078a7911a76b4de91bebf89031c4750/src/main/java/facebook/FacebookCustomAudience.java)  
