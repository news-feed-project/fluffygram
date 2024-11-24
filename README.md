
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


## 📅 와이어 프레임

## 📑 API 명세서

<details>
<summary>API 명세서</summary>
<br/>

#### user
|    기능    | method |URL|
|:--------:|:------:|:---:|
|  사용자 생성   | POST  |/users/signup|
| 사용자 전체 조회 | GET  |/users|
| 사용자 단건 조회 | GET  |/users/mypage/{Id}|
|  다른 사용자 프로필 조회   | GET  |/users/others{Id}|
|  사용자 정보 수정   | PATCH  |/users/{Id}|
| 사용자 삭제 | DELETE  |/users/{Id}|
|  로그인   | POST  |/users/login|
|  로그아웃   | POST  |/users/logout|

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


## ⚙️ ERD

<details>
  
<summary>ERD</summary>

</details>


