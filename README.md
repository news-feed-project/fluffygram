# 💻 프로젝트 소개
#### **플러피그램(Fluffygram)**
#### 자신의 애완동물을 자랑하고 서로 정보를 공유하는 사이트

## 🚀 개발 기간
> 2024.11.20 - 2024.11.24


## 🌱 개발 환경
- `IDE : IntelliJ`
- `Java Development Kit : openjdk version '17.0.2'`
- `Framework : springframework.boot version '3.3.5', Spring Data JPA`
- `Database : MySQL version '8.0.40'`
- `tool : figma, `

## 🪧 커밋 컨벤션
🎉 Begin: 프로젝트 시작 <br>
✨ Feat : 새로운 기능 추가, 구현<br>
📝 Docs : 문서 파일 추가 및 수정<br>
🔧 Add :  파일 추가 & 코드 수정<br>
✏️ Typos : 단순 오타 수정<br>
🐛 Fix : 버그 수정<br>
🚚 Rename : 파일, 경로를 옮기거나 이름 변경<br>
🎨 Rename : 코드의 구조, 형태 개선<br>
♻️ Refactor : 코드 리팩토링<br>
💡 Comment : 주석 추가, 변경<br>
🔥 Remove : 파일, 코드 삭제<br>
🔀 Branch : 브랜치 추가, 병합 등<br>
➕ Dependency : 의존성 추가<br>
🏗️ Chore : 빌드 업무 수정, 패키지 매니저 수정, 패키지 관리자 구성 등

## 🪐 주요 기능
<details>
<summary>주요 기능</summary>

#### **✨ Lv1 : 일정 CRUD**
* 일정을 저장, 조회, 수정, 삭제할 수 있다. []

#### **✨ Lv2 : 유저 CRUD**
* 유저를 저장, 조회, 삭제할 수 있다. []

#### **✨ Lv3 : 회원가입**
* 유저는 유저명과 비밀번호, 이메일을 입력해 회원가입을 할 수 있다. []

#### **✨ Lv4 : 로그인(인증)**
* 유저는 이메일과 비밀번호를 입력해 로그인을 할 수 있다. []
* Cookie/Session을 활용해 로그인 기능을 구현 []
* 필터를 활용해 인증 처리를 할 수 있다. []
* 회원가입, 로그인 요청은 인증 처리에서 제외 []
* 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 401을 반환한다. []

#### **✨ Lv5 : 예외 처리**
* Validation을 활용해 다양한 예외처리를 적용 []
* @Pattern을 사용해서 회원 가입 Email 데이터 검증 []

#### **✨ Lv6 : 비밀번호 암호화**
* 비밀번호 필드에 들어가는 비밀번호를 암호화 할 수 있다. []

#### **✨ Lv7 : 댓글 CRUD**
* 생성한 일정에 댓글을 남길 수 있다. []
* 댓글을 저장, 조회, 수정, 삭제할 수 있다. []

#### **✨ Lv8 : 영속성 전이를 활용한 삭제**
* 유저를 삭제할 때 해당 유저가 생성한 일정과 댓글도 삭제된다. []

#### **✨ Lv9 : 일정 페이징 조회**
* 일정을 Spring Data JPA의 Pageable과 Page 인터페이스를 활용하여 페이지네이션을 구현한다. []
* 일정 제목, 일정 내용, 댓글 개수, 일정 작성일, 일정 수정일, 일정 작성 유저명 필드를 조회 할 수 있다. []
* 디폴트 페이지 크기는 10으로 적용한다. []
* 일정의 수정일을 기준으로 내림차순 정렬이 가능하다. []

</details>

## 📅 와이어 프레임

## ⚙️ ERD
<details>
<summary>ERD</summary>

![fluffygram ERD](https://github.com/user-attachments/assets/17514ff3-d6d7-4f5a-82ac-96392b07cb04)
</details>
  

## 📑 API 명세서
<details>
<summary>API 명세서</summary>
<br/>

### user

<table>
    <tr>
      <th scope="col">기능</td>
      <th scope="col">Method</td>
      <th scope="col">URL</th>
      <th scope="col">Request</td>
      <th scope="col">Response</td>
      <th scope="col">요쳥 변수</td>
      <th scope="col">request</td>
      <th scope="col">응답 변수</td>
      <th scope="col">response</td>
      <th scope="col">상태 코드</td>
    </tr>
    <tr>
      <td>사용자 생성<br>(회원가입)</td>
      <td>POST</td>
      <td>/users/signup</td>
      <td></td>
      <td></td>
      <td>String email : 필수 0<br>
        String password : 필수 0<br>
       String userNicname : 필수 0<br>
      String phoneNumber : 필수 0<br>
      String profileImage : 필수 x</td>
      <td>requestBody(JSON) :
{
      "email " : "abcde@gmail.com",<br>
      "password" : "12345",<br>
      "userNicname " : "닉네임",<br>
     "phoneNumber " : "01012345678",<br>
     "profileImage" : "fdkjf39"<br>
 }</td>
 
      <td>requestBody(JSON) :
{<br>
      "email " : "abcde@gmail.com",<br>
      "password" : "12345",<br>
      "userNicname " : "닉네임",<br>
     "phoneNumber " : "01012345678",<br>
     "profileImage" : "fdkjf39"<br>
 }</td>
      <td>Long id : 필수 0<br>
String email : 필수 0<br>
String userNicname : 필수 0<br>
String phoneNumber : 필수 0<br>
String profileImage : 필수 x<br>
LocalDatetime createAt : 필수 0<br>
LocalDatetime modifyAt : 필수 0"</td>
      <td>
        {
      "id" : "1",<br>
      "email " : "abcde@gmail.com",<br>
      "userNicname " : "닉네임",<br>
     "phoneNumber " : "01012345678",<br>
     "profileImage" : "fdkjf39",<br>
    "create_at" : "2024-11-19 18:00:00",<br>
     "modify_at" : "2024-11-19 18:00:00"<br>
 }
      </td>
      <td>
        201: 생성 성공,<br> 
400: 잘못된 값 입력
      </td>
    </tr>
    <tr>
      <td>사용자 전체 조회</td>
      <td>GET</td>
      <td>/users</td>
      <td>Cookie :<br>
JSESSIONID= ${sessionId}</td>
<td></td>
<td>없음</td>
<td>없음</td>
      <td>list:<br>
Long id : 필수 0<br>
String email : 필수 0<br>
String userNicname : 필수 0<br>
String phoneNumber : 필수 0<br>
String profileImage : 필수 x<br>
LocalDatetime createAt : 필수 0<br>
LocalDatetime modifyAt : 필수 0</td>
      <td>[
{
      "id" : "1",<br>
      "email " : "abcde@gmail.com",<br>
      "userNicname " : "닉네임",<br>
     "phoneNumber " : "01012345678",<br>
     "profileImage" : "fdkjf39",<br>
    "create_at" : "2024-11-19 18:00:00",<br>
     "modifyAt " : "2024-11-19 18:00:00"<br>
 },<br>
{<br>
      "id" : "1",<br>
      "email " : "efgh@gmail.com",<br>
      "userNicname " : "닉네임2",<br>
     "phoneNumber " : "01012349876",<br>
     "profileImage" : "glwjfq",<br>
    "create_at" : "2024-11-19 18:00:00",<br>
     "modify_at" : "2024-11-19"<br>
 }<br>
]</td>
<td>200 : 정상<br>
400 : 잘못된 값 입력<br>
401 : 권한 없음 (로그인 인증 안됨)<br>
404 : 해당 데이터 없음</td>
    </tr>
    <tr>
      <td>사용자 단건 조회</td> 
      <td>GET</td>
      <td>/users/mypage/{id}</td>
      <td>Cookie :<br>
      JESSIONID = ${sessionId}</td>
      <td></td>
      <td>Long id : 필수 0</td>
      <td>PathVariable(param)<br>
{<br>
"id" : 1<br>
}</td>
      <td>Long id : 필수 0<br>
String email : 필수 0<br>
String userNicname : 필수 0<br>
String phoneNumber : 필수 0<br>
String profileImage : 필수 x<br>
LocalDatetime createAt : 필수 0<br>
LocalDatetime modifyAt : 필수 0</td>
      <td>
        {<br>
      "id" : "1",<br>
      "email " : "abcde@gmail.com",<br>
      "userNicname " : "닉네임",<br>
     "phoneNumber " : "01012345678",<br>
     "profileImage" : "fdkjf39",<br>
    "create_at" : "2024-11-19 18:00:00",<br>
     "modify_at" : "2024-11-19 18:00:00"<br>
 }
      </td>
      <td>
        200 : 정상<br>
400 : 잘못된 값 입력<br>
401 : 권한 없음 (로그인 인증 안됨)<br>
404 : 해당 데이터 없음<br>
      </td>
    </tr>
    <tr>
      <td>다른 사용자 프로필 조회</td>
      <td>GET</td>
      <td>/users/others/{id}</td>
      <td>Cookie :<br>
      JSESSIONID=${sessionId}</td>
      <td></td>
      <td>Long id : 필수 0</td>
      <td>
        PathVariable(param<br>
{<br>
"id" : 1<br>
}
      </td>
      <td>
      Long id : 필수 0<br>
String userNicname : 필수 0<br>
String profileImage : 필수 x<br>
LocalDatetime createAt : 필수 0<br>
LocalDatetime modifyAt : 필수 0<br>
      </td>
      <td>{<br>
      "id" : "1",<br>
      "userNicname " : "닉네임수정",<br>
     "profileImage" : "fdkjf39",<br>
    "create_at" : "2024-11-19 18:00:00",<br>
     "modify_at" : "2024-11-19 18:00:00"<br>
 }</td>
      <td>
        200 : 정상<br>
400 : 잘못된 값 입력<br>
404 : 해당 데이터 없음<br>
      </td>
    </tr>
    <tr>
      <td>사용자 삭제</td>
      <td>Delete</td>
      <td>/users/{id}</td>
      <td>Cookie :<br>
      JSESSIONID = ${sessionId}</td>
      <td></td>
      <td>Long id : 필수 0<br>
String password : 필수 0</td>
      
      <td>PathVariable(param)<br>
{<br>
"id" : 1<br>
},<br>
requestBody(JSON) :<br>
{<br>
     "password" : "!a123456"<br>
 }</td>
 <td></td>
 <td></td>
 <td>204 : 내용없음<br>
400 : 잘못된 값 입력<br>
401 : 권한 없음 (로그인 인증 안됨)<br>
404 : 해당 데이터 없음</td>
    </tr>
  </table>

<details>
<summary> 사용자 생성</summary>

  
|  기능  | method |URL|
|:----:|:------:|:---:|
| 사용자 생성 | POST  |/users/signup|

#### Request Eelements
|    파라미터    |   타입    | 필수 여부 |           설명           |
|:----------:|:-------:|:-----:|:----------------------:|
|   email    | String  |   Y   |         이메일          |
|  password  | String  |   Y   |         비밀번호          |
|  userNickname   | String  |   Y   |         유저 닉네임         |
|  phoneNumber  | String  |   Y   |         전화번호          |
|  profileImage   | String  |   N   |         사진첨부         |

#### Respons Eelements
| 파라미터  |   타입    | 필수 여부 |     설명 |
|:-----:|:-------:|:-----:|:------:|
| id | Integer |   Y   |         ID          |
|   email    | String  |   Y   |         이메일          |
|  userNickname  | String  |   Y   |         유저 닉네임          |
| profileImage | String |   N   |   사진 첨부   |
| create_at |  String   |   Y   | 일정 작성 일자 (datetime) |
| modify_at |  String   |   Y   | 일정 최종 수정 일자 (datetime) |




#### Schedule
|    기능    | method |URL|
|:--------:|:------:|:---:|
|  일정 생성   | POST  |/schedules|
| 일정 목록 조회 | GET  |/schedules|
| 일정 상세 조회 | GET  |/schedules/{Id}|
|  일정 수정   | PUT  |/schedules/{Id}|
|  일정 삭제   | DELETE  |/schedules/{Id}|

<details>
<summary> 일정 생성</summary>

|  기능  | method |URL|
|:----:|:------:|:---:|
| 일정 생성 | POST  |/schedules|

#### Request Eelements
|    파라미터    |   타입    | 필수 여부 |           설명           |
|:----------:|:-------:|:-----:|:----------------------:|
|   title    | String  |   Y   |         일정 제목          |
|  contents  | String  |   Y   |         일정 내용          |
|  user_id   | String  |   Y   |         사용자 ID         |

#### Respons Eelements
| 파라미터  |   타입    | 필수 여부 |     설명 |
|:-----:|:-------:|:-----:|:------:|
| id | Integer |   Y   |         일정 ID          |
|   title    | String  |   Y   |         일정 제목          |
|  contents  | String  |   Y   |         일정 내용          |
| created_at | String |   Y   |   일정 작성 일자(datetime)   |
| updated_at |  String   |   Y   | 일정 최종 수정 일자 (datetime) |


#### 요청 예시

```json
  {
      "title" : "제목입니다.",
      "contents" : "내용입니다.",
      "username" : "유저이름"
  }
```
#### 응답 예시
- Statue Code 201 Created [생성 성공]
```json
  {
      "id": 1,
      "title": "제목입니다.",
      "contents": "내용입니다."
  }
```
- Statue Code 400 Bad Request [잘못된 요청]
```json
  {
    "error": "일정 생성에 실패했습니다."
  }
```
</details>

</details>



