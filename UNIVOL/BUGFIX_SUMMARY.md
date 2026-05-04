# 댓글 NULL userId 버그 수정 완료

## 문제점
- 댓글 등록 시 `ORA-01400: NULL값 입력할 수 없습니다` 에러 발생
- userId가 NULL로 데이터베이스에 전송되고 있었음

## 원인
- loginUser가 모델에 제대로 추가되지 않음
- 클라이언트에서 userId 검증 부족

## 해결 방법

### 1. PostController 수정 (`/src/main/java/com/univol/post/controller/PostController.java`)
```java
// 변경 전
Object loginUser = session.getAttribute("loginUser");
if(loginUser != null) {
    model.addAttribute("loginUser", loginUser);
}

// 변경 후
Object loginUser = session.getAttribute("loginUser");
model.addAttribute("loginUser", loginUser);  // 항상 추가 (null이어도)
```

### 2. ReplyController 수정 (`/src/main/java/com/univol/post/controller/ReplyController.java`)
userId 검증 추가:
```java
if(reply.getUserId() == null || reply.getUserId().trim().isEmpty()) {
    response.put("success", false);
    response.put("message", "로그인이 필요합니다.");
    return ResponseEntity.badRequest().body(response);
}
```

### 3. detail.html 수정 (`/src/main/resources/templates/post/detail.html`)
- userId 추출 및 검증 로직 추가
- 콘솔 디버그 로그 추가
- 클라이언트에서 빈 userId 거절

## 테스트 방법

### STS에서 프로젝트 재시작
1. **Project → Clean...** 실행
2. STS 재시작
3. 서버 재부팅

### 로그인 확인
1. **중요**: 반드시 로그인한 상태로 게시글 상세 페이지 접속
2. 브라우저 개발자 도구(F12) → Console 탭 열기
3. 다음이 보여야 함:
   ```
   userId from page: [YOUR_USER_ID]
   Is logged in? true
   ```

### 댓글 등록 테스트
1. 댓글 입력 후 "댓글 등록" 버튼 클릭
2. 콘솔에서 다음 확인:
   ```
   Sending userId: [YOUR_USER_ID]
   ```
3. 댓글이 성공적으로 등록되면 리스트에 표시됨

## 만약 여전히 에러가 나면
- **"로그인이 필요합니다" 에러**: 로그인되지 않은 상태 → 로그인 후 재시도
- **콘솔에서 userId가 빈 값**: 로그인 정보가 세션에 제대로 저장되지 않음 → 로그인 컨트롤러 확인 필요
- **데이터베이스 에러**: 콘솔 로그 확인하고 에러 메시지 공유

## 클린업 필요
- `bin/main` 폴더의 오래된 컴파일 파일 삭제 (Clean 실행으로 자동 처리)
