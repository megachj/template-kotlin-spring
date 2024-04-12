# template-kotlin-spring: 코틀린 스프링 프로젝트 템플릿

## 환경
kotlin 버전 및 spring boot 버전에 따라 호환되는 gradle 버전을 선택해야 한다.
* kotlin 버전과 gradle 버전: https://kotlinlang.org/docs/gradle-configure-project.html#apply-the-plugin
* spring boot 버전과 gradle 버전 https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#introduction

### gradle wrapper 버전 변경  
#### 방법1: gradle-wrapper.properties 파일에서 버전 변경
/gradle/wrapper/gradle-wrapper.properties 에서 버전을 수정한다.

#### 방법2: gradle 명령어 실행
`./gradlew wrapper --gradle-version 8.1.1`

## 메뉴얼
### gradle 명령어
* build some module: `./gradlew build -p {module_name}`
* build frontent (npm): `./gradlew copyWebApp -p {module_name}`
* generate openapi3 spec file: `./gradlew -p {module_name} copyOpenApiSpec`
* generate Dockerfile: `./gradlew docker -p {module_name} -x test`

### openapi3 테스트케이스 작성 방법
MockMvcRestDocumentation 을 감싼 MockMvcRestDocumentationWrapper 클래스 이용

## 코드 컨벤션 모음
* [모듈간 의존성](./README-모듈간%20의존성.md)
* [패키지 구조](./README-패키지%20구조.md)
