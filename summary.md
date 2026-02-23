# 프로젝트 수정/추가 사항 Summary

## 1. 테스트 데이터 삽입

- `tbl_individual_member` : member id 6, 7, 8 삽입
- `tbl_corp` id=2 : `corp_company_type = '공기업'` 업데이트
- `tbl_experience_program` : 5개 체험 프로그램 삽입
- `tbl_latest_watch_posting` : 7개 최근 본 공고 삽입
- `tbl_scrap_posting` : 5개 스크랩 공고 삽입
- `tbl_apply` : 7개 지원 내역 삽입 (member 6, 7, 8 대상)

---

## 2. 취업톡톡 더보기 버튼 → QnA 목록 이동

**파일:** `src/main/resources/templates/mypage/mypage.html`

- 취업톡톡 섹션의 더보기 `<a>` 태그 `href=""` → `href="/qna/list"` 로 변경

---

## 3. QnA 페이지 `individualMemberLevel` SpEL 오류 수정

**원인:** 일반 로그인 시 세션에 `MemberDTO`(개인 필드 없음)가 저장되어 `loginMember?.individualMemberLevel` 오류 발생

**수정 파일:**

### `src/main/resources/mapper/member/individualMember.xml`
- `selectById` 쿼리 추가 (`view_member_profile` 뷰 조회)

```xml
<select id="selectById" resultType="IndividualMemberDTO">
    select * from view_member_profile
    where id = #{id}
</select>
```

### `src/main/java/com/app/trycatch/mapper/member/IndividualMemberMapper.java`
- `Optional<IndividualMemberDTO> selectById(Long id);` 메서드 추가

### `src/main/java/com/app/trycatch/repository/member/IndividualMemberDAO.java`
- `findById(Long id)` 메서드 추가

### `src/main/java/com/app/trycatch/service/member/IndividualMemberService.java`
- `login()` 반환 타입 `MemberDTO` → `IndividualMemberDTO` 변경
- `view_member_profile`에서 개인 회원 정보 전체 로드

---

## 4. 스크랩 공고 비활성 항목 노출 버그 수정

**파일:** `src/main/resources/mapper/mypage/scrapPostingMapper.xml`

- `selectAllByMemberId` WHERE 절에 `and sp.scrap_status = 'active'` 조건 추가

---

## 5. 기업 회원이 개인 로그인 폼으로 로그인되는 버그 수정

**파일:** `src/main/resources/mapper/member/memberMapper.xml`

- `selectMemberForLogin`에 `join tbl_individual_member i on m.id = i.id` 추가
- 개인 회원 테이블과 JOIN하여 기업 회원은 로그인 불가 처리

---

## 6. 기업 회원 로그인 기능 구현

### `src/main/resources/mapper/member/corpMemberMapper.xml`
- `selectCorpMemberForLogin` 쿼리 추가

### `src/main/java/com/app/trycatch/mapper/member/CorpMemberMapper.java`
- `Optional<CorpMemberDTO> selectCorpMemberForLogin(MemberDTO memberDTO);` 추가

### `src/main/java/com/app/trycatch/repository/member/CorpMemberDAO.java`
- `findForLogin(MemberDTO memberDTO)` 메서드 추가

### `src/main/java/com/app/trycatch/service/member/CorpService.java`
- `login(MemberDTO memberDTO)` 메서드 추가

### `src/main/java/com/app/trycatch/controller/member/MemberController.java`
- `POST /main/corp-log-in` 엔드포인트 추가
- 로그인 성공 시 세션에 `CorpMemberDTO` 저장 후 `re_url` 리다이렉트

### `src/main/resources/static/js/main/log-in.js`
- 개인 탭 클릭 → `form.action = "/main/log-in"`
- 기업 탭 클릭 → `form.action = "/main/corp-log-in"`

---

## 7. 로그인 오류 처리 (기존 구조 활용)

- `MyPageExceptionHandler` (`@ControllerAdvice`)가 `MemberExceptionHandler`와 함께 로그인 실패 처리 담당
- 별도 추가 없이 기존 `LoginFailException` → `MemberExceptionHandler`에서 처리

---

## 8. SQL 키워드 소문자 통일

**파일:**
- `src/main/resources/mapper/member/memberMapper.xml`
- `src/main/resources/mapper/member/corpMemberMapper.xml`

- `SELECT`, `FROM`, `WHERE`, `INSERT INTO`, `VALUES`, `UPDATE`, `SET` 등 모두 소문자로 변경

---

## 9. `application.yaml` 미포함 문제 (DataSource 오류)

**원인:** `application.yaml`이 `.gitignore`에 포함되어 Gradle bootRun 시 `build/resources/main/`에 복사 안 됨

**해결:** IntelliJ IDEA 설정 변경
- `Settings → Build, Execution, Deployment → Build Tools → Gradle`
- `Build and run using` → **IntelliJ IDEA** 로 변경

---

## 10. mypage/experience 페이지 — 레벨 뱃지 및 지원자수 표시

### `src/main/java/com/app/trycatch/dto/mypage/ApplyListDTO.java`
- 필드 추가: `experienceProgramLevel`, `experienceProgramRecruitmentCount`, `applicantCount`
- 헬퍼 메서드 추가:
  - `getExperienceProgramLevelLabel()` → `"A유형"` ~ `"E유형"` 반환
  - `getApplyStatusLabel()` → 한글 상태명 반환
  - `getExperienceProgramStatusLabel()` → 공고 진행상태 한글 반환

### `src/main/resources/mapper/mypage/applyListMapper.xml`
- 조회 컬럼 추가: `experience_program_level`, `experience_program_recruitment_count`
- 서브쿼리 추가: `(select count(*) from tbl_apply where ... and apply_status != 'cancelled') as applicantCount`

### `src/main/resources/templates/mypage/experience.html`
- 기업명 옆 레벨 뱃지 추가:
  ```html
  <span class="level-badge" th:text="${apply.experienceProgramLevelLabel}">A유형</span>
  ```
- 지원자수 표시 추가:
  ```html
  <div class="item" th:text="'지원자수 : ' + ${apply.applicantCount} + '/' + ${apply.experienceProgramRecruitmentCount}">
  ```

---

## 11. mypage/experience 페이지 — 지원 취소 비동기 처리

### `src/main/resources/static/js/mypage/experience/service.js`
- `cancelApply(applyId, callback)` : `POST /mypage/experience/cancel` fetch 요청

### `src/main/resources/static/js/mypage/experience/layout.js`
- `showCancelled(cancelBtn)` : 취소 버튼 TD를 "지원취소됨."으로 교체 + 동일 행 지원자수 -1 감소
- `decrementStatusCount(applyStatus)` : 상단 상태별 카운트 (`심사중`/`참여중`/`심사완료`) -1 감소

### `src/main/resources/static/js/mypage/experience/event.js`
- 취소 버튼 클릭 시 `currentApplyStatus = btn.dataset.status` 저장
- 취소 성공 후 `showCancelled()` + `decrementStatusCount()` 순서로 호출

---

## 12. 지원 취소 후 새로고침 유지

### `src/main/resources/mapper/mypage/applyListMapper.xml`
- `and a.apply_status != 'cancelled'` 필터 제거 → 취소된 항목도 DB에서 조회

### `src/main/resources/templates/mypage/experience.html`
- 취소된 항목은 서버 렌더링 시 버튼 대신 "지원취소됨." 텍스트로 표시:
  ```html
  <span th:if="${apply.applyStatus == 'cancelled'}">지원취소됨.</span>
  <button th:unless="${apply.applyStatus == 'cancelled'}" ... th:data-status="${apply.applyStatus}">
  ```
- 상단 카운트 요소에 ID 추가: `id="count-applied"`, `id="count-document-pass"`, `id="count-document-fail"`

---

## 13. 미로그인 시 로그인 redirect URL 동적 처리

### `src/main/java/com/app/trycatch/common/exception/handler/MyPageExceptionHandler.java`
- `HttpServletRequest` 주입 추가
- `UnauthorizedMemberAccessException`, `MemberNotFoundException` 핸들러에서 `re_url` 하드코딩 제거
- `request.getRequestURI()` 로 실제 접근 URL을 `re_url`로 사용
  - 예: `/mypage/experience` 접근 시 → `/main/log-in?re_url=/mypage/experience` 로 이동

---

## 14. 사이드 네비게이션 — 체험 지원 현황 링크 수정

### `src/main/resources/templates/mypage/experience.html`
- `<a href="" onclick="" class="on">체험 지원 현황</a>` → `<a href="/mypage/experience" class="on">체험 지원 현황</a>`

### `src/main/resources/templates/mypage/mypage.html`
- 동일하게 체험 지원 현황 링크 `href="/mypage/experience"` 추가
- 취업톡톡 더보기 `href="/qna/list"` 적용

---

## 주요 DB / 아키텍처 참고

| 항목 | 내용 |
|------|------|
| DB | `try_catch`, user: `TRY-CATCH`, pw: `1234`, port: `3306` |
| 세션 타입 | 일반/카카오: `IndividualMemberDTO`, 기업: `CorpMemberDTO` |
| 뷰 | `view_member_profile` = `tbl_member` + `tbl_individual_member` + `tbl_file` JOIN |
| 서버 실행 | IntelliJ IDEA 빌드 사용 (Gradle bootRun 사용 시 `application.yaml` 미포함 오류) |
| tbl_member | 슈퍼타입: `tbl_individual_member`, `tbl_corp` 서브타입 |
