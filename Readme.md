# Triple Assignment 

> 서버개발자 지원자
> **송민석** 

## Specifications
리뷰 작성이 이뤄질때마다 리뷰 작성 이벤트가 발생하고, 아래 API로 이벤트를 전달합니다.

POST /events

```
{
  "type": "REVIEW",
  "action": "ADD", /* "MOD", "DELETE" */
  "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
  "content": "좋아요!",
  "attachedPhotoIds": 
  		[
		  	"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
		   	"afb0cef2-851d-4a50-bb07-9cc15cbdc332"
	 	],
  "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
  "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```
한 사용자는 장소마다 리뷰를 1개만 작성할 수 있고, 리뷰는 수정 또는 삭제할 수 있습니다. 리뷰 작성 보상 점수는 아래와 같습니다.

```
내용 점수
- 1자 이상 텍스트 작성: 1점
- 1장 이상 사진 첨부: 1점
보너스 점수
- 특정 장소에 첫 리뷰 작성: 1점
```


## Requirements

- 이 서비스를 위한 SQL(MySQL 5.7) 스키마를 설계해 주세요. 테이블과 인덱스에 대한 DDL이 필요합니다.  
- 아래에 대한 pseudo code를 작성해 주세요.
	- 포인트 적립 API
	- 포인트 조회 API

## Remarks
- 포인트 증감이 있을 때마다 이력이 남아야 합니다.
- 사용자마다 현재 시점의 포인트 총점을 조회하거나 계산할 수 있어야 합니다.
- 포인트 부여 API 구현에 필요한 SQL 수행 시, 전체 테이블 스캔이 일어나지 않는 인덱스가 필요합니다.
- 리뷰를 작성했다가 삭제하면 해당 리뷰로 부여한 내용 점수와 보너스 점수는 회수합니다.
- 리뷰를 수정하면 수정한 내용에 맞는 내용 점수를 계산하여 점수를 부여하거나 회수합니다.
- 글만 작성한 리뷰에 사진을 추가하면 1점을 부여합니다.
- 글과 사진이 있는 리뷰에서 사진을 모두 삭제하면 1점을 회수합니다.
- 사용자 입장에서 본 '첫 리뷰'일 때 보너스 점수를 부여합니다.
- 어떤 장소에 사용자 A가 리뷰를 남겼다가 삭제하고, 삭제된 이후 사용자 B가 리뷰를 남기면 사용자 B에게 보너스 점수를 부여합니다.
- 어떤 장소에 사용자 A가 리뷰를 남겼다가 삭제하는데, 삭제되기 이전 사용자 B가 리뷰를 남기면 사용자 B에게 보너스 점수를 부여하지 않습니다.

## 개발환경 

- Intellij  
- Spring Boot
- Gradle
- MySQL 5.7

## DDL(MySQL)

```
create table user (
	id varchar(255), 
	point int not null, 
	user_name varchar(255), 
	primary key (id)
);

create table place (
	id varchar(255),
	name varchar(255) not null, 
	primary key (id)
);

create table review (
	id varchar(255), 
	content varchar(255), 
	is_deleted tinyint(1) not null default '0', 
	point int not null, 
	place_id varchar(255), 
	user_id varchar(255), 
	primary key (id), 
	FOREIGN KEY (place_id) REFERENCES place (id), 
	FOREIGN KEY (user_id) REFERENCES user (id)
);

create table photo (
	id varchar(255), 
	review_id varchar(255), 
	primary key (id), 
	FOREIGN KEY (review_id) REFERENCES review (id)
);

create table point_history (
	id bigint(20) unsigned, 
	point_change int not null, 
	type varchar(255) not null, 
	review_id varchar(255), 
	user_id varchar(255), 
	primary key (id), 
	FOREIGN KEY (review_id) REFERENCES review (id), 
	FOREIGN KEY (user_id) REFERENCES user (id)
);

create index IDX_POINT_HISTORY ON point_history (user_id);
create index IDX_REVIEW_EXISTS ON review (place_id, is_deleted);

```

## API

```
POST /events : 리뷰 생성, 수정, 삭제
GET /points : 현재 로그인된 유저의 포인트 총계를 반환 
GET /points/details : 현재 로그인된 유저의 포인트 히스토리 리스트를 반환 
```
