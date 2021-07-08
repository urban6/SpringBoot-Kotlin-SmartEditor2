# SpringBoot-Kotlin-SmartEditor2

<br>

## 시작
Spring Boot와 Kotlin을 이용해서 Naver의 SmartEditor2를 적용하는 프로젝트입니다.

https://github.com/naver/smarteditor2

> 이미지 업로드를 사용하고 싶은 경우, v2.8.2.3 이하 버전을 이용해야 합니다. 
> 이후에 나온 버전에는 Sample 폴더가 없습니다.

## 이미지 업로드 관련
`HomeControll`파일의 `multiImageUpload`메소드를 확인하면 쉽게 따라할 수 있습니다.
추가적으로 리소스 폴더의 `/static/sample/photo_uploader/attch_photo.js`에서 php파일로 되어있는 것을 위의 경로로 수정을 해줘야합니다
> multiImageUpload을 전체 검색하면 쉽게 내용을 파악할 수 있습니다.

이미지 저장 경로는 `application.properties`에서 'image.upload.path'을 알맞게 수정하면 됩니다.
