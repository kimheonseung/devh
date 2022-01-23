# DevH Common SpringBoot

개인 참고용으로 작성중인 공통 코드
  
  
[[Docker] MariaDB & Elasticsearch](https://github.com/kimheonseung/devh/tree/master/springboot/docker-mariadb-elasticsearch)  
[[SpringBoot] 프로젝트 설정파일 처리](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/api/configuration/EnvironmentPostProcessorImpl.java)  
[Elasticsearch 7.16.2](https://github.com/kimheonseung/devh/tree/master/springboot/src/main/java/com/devh/common/elasticsearch)  
[자료구조](https://github.com/kimheonseung/devh/tree/master/springboot/src/main/java/com/devh/common/datastructure)  
[Netty](https://github.com/kimheonseung/devh/tree/master/springboot/src/main/java/com/devh/common/netty)  
  
#### [SpringBoot] 요청/응답 공통화
1. [ApiResponse](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/api/response/ApiResponse.java)
2. [Exception Advice](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/api/advice/ApiAdvice.java)
3. [검색 공통 VO](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/api/search/vo/SearchParameterVO.java)
  
  
#### [SpringDataJPA & JWT & SpringSecurity] 회원 인증
1. [SpringSecurity 설정](https://github.com/kimheonseung/devh/tree/master/springboot/src/main/java/com/devh/common/api/configuration/SpringSecurityConfiguration.java)
2. [엔티티](https://github.com/kimheonseung/devh/tree/master/springboot/src/main/java/com/devh/common/api/entity)
3. [초기 데이터](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/api/runner/InitDataRunner.java)
4. [JWT Filter](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/api/filter/JwtAuthFilter.java)
5. [로그인](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/api/controller/WebUserController.java)
  
  
#### [SpringDataElasticsearch]
1. [설정](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/api/configuration/ElasticsearchConfiguration.java)
2. [템플릿](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/api/configuration/TemplateInitializer.java)
  
  
#### 크롤링
1. [Jsoup](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/util/component/JsoupUtils.java)
2. [MTU 조절](https://github.com/kimheonseung/devh/blob/master/springboot/src/main/java/com/devh/common/util/component/MTUUtils.java)
  
  
