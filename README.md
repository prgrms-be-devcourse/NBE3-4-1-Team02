# NBE3-4-1-Team02
## [ERD Cloud](https://www.erdcloud.com/d/9Hj4Dzm6vxTZ2Wv4u)
### 협업 전략
## 커밋 메시지 및 PR 네이밍 규칙
각 키워드 다음에 - {내용} 하시면 됩니다
#### ex) feat- 상품 추가 기능 구현
* feat: 새로운 기능 추가
* fix: 버그 수정
* docs: 문서 변경 (예: README.md 수정)
* style: 코드 포맷팅, 세미콜론 누락 등 스타일 변경
* refactor: 코드 리팩토링 (기능 변경 없음)
* test: 테스트 코드 추가, 수정
* chore: 빌드 스크립트 수정, 패키지 매니저 설정 등의 기타 변경사항

## 1. 작업 시작 전 git pull --rebase origin main 
### (다른 사람의 pr 이 merge 된 내역 반영된 내용 가져오기. 안 하면 버전 차이 때문에 브랜치가 꼬입니다.)
## 2. 프로젝트를 별도로 열기 (NBE3-4-1-Team02/backend 경로를 통해)
## 3. 새 브랜치 판 후 작업
### 1. 기본적으로 prefix 로 feature- 를 붙입니다.
#### ex)feature-product 
### 2. 기능이 세부적으로 나뉠 경우에도 -로 구분합니다. (필수는 아닙니다. (그대로 feature-product 에서 작업해도 됩니다))
#### ex)feature-product-create
### 3. 3-2의 경우에는 4번에서, main 브랜치 대신 상위 브랜치에 pr을 날린 후 merge 합니다.
#### ex)feature-product-create -> feature-product 에 pr 후 merge

## 4. main 브랜치에 pr 날리기
### (터미널 명령어로 커밋 시, cd .. 로 NBE3-4-1-Team02/backend 말고 NBE3-4-1-Team02 경로로 하시는 걸 추천 합니다 (안하면 어떻게 되는 진 모르겠습니다.))
## 5. 코드 리뷰 후 merge 해주기


