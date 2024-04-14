# 패키지 구조
## 기본 패키지 틀
전체 프로젝트 구조인 group, project, modules 를 그대로 패키지로 사용하는 것을 권장한다. 
````
{group}.{project}.{modules}.{packages}

예시)
sunset.templatekotlinspring.app.examplewebapi.model
````

## 모듈별 패키지 아키텍처
Package by Feature 아키텍처로 패키지를 구조화하는 것을 권장한다.

### Package by Layer: Class 계층에 따라 패키지를 나누는 방식
간단히 보면 'controller, service, model' 등 계층으로 나누는 방식이다.  

프로젝트 규모가 조금이라도 커지면 혼란스러워지는게 가장 큰 단점이다.
규모가 작을때 이 방식을 쓰고 커지면 feature 로 하는 방법으로 사용해도 괜찮다는 말들이 있다.
하지만 현업에서 일하게 되면 한 프로젝트 개발 인원들이 여러명이라 일정 이상됐을 때 바꾸기가 쉽지 않다. 

### Package by Feature: Class 기능(도메인)에 따라 패키지를 나누는 방식
간단히 보면 'user, bank, bankaccount' 등 기능(도메인)별로 나누는 방식이다.  

프로젝트 규모가 작을땐 비효율적으로 보일 수도 있지만 조금이라도 커지면 잘 정돈되어 훨씬 보기가 좋다.
그리고 특정 기능만 변경사항이 생길때 해당 패키지만 보면 되어서 많이 편하다.
* 프로젝트내 다른 모듈로 분리할때 용이
* 프로젝트내(외) 다른 애플리케이션 서버로 분리할때 용이
* 해당 기능 fadeout 할때도 용이 
