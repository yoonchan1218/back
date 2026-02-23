# Three-Tier Architecture Code Summary
> 기준: `spring_day10/workspace` (threetier_v4)
> 코드 작성 시 이 파일의 패턴을 따를 것

---

## 계층 구조
```
Controller → Service → DAO → Mapper(interface) ← XML
```

---

## 1. Controller

```java
@Controller
@RequestMapping("/post/**")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final HttpSession session;

    @GetMapping("write")
    public String goToWriteForm() {
        return "post/write";
    }

    @PostMapping("write")
    public RedirectView write(PostDTO postDTO,
                              @RequestParam("file") List<MultipartFile> files) {
        postService.write(postDTO, files);
        return new RedirectView("/post/list");
    }

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("posts", postService.list());
        return "post/list";
    }

    @GetMapping("detail")
    public String detail(Long id, Model model) {
        model.addAttribute("post", postService.detail(id));
        return "post/detail";
    }

    @GetMapping("delete")
    public RedirectView delete(Long id) {
        postService.delete(id);
        return new RedirectView("/post/list");
    }

    // AJAX 전용은 @ResponseBody 또는 @RestController
    @GetMapping("check")
    @ResponseBody
    public boolean check(String value) {
        return postService.check(value);
    }
}
```

**규칙:**
- 클래스: `@Controller` + `@RequestMapping` + `@RequiredArgsConstructor`
- 뷰 이동 메서드: `goTo{Form}Form()` 패턴
- 뷰 반환: `String` (템플릿 경로)
- 리다이렉트: `RedirectView`
- AJAX: `@ResponseBody` + 반환값 그대로
- REST API: `@RestController` + `/api/` 접두어 별도 클래스
- 파라미터: DTO 직접 바인딩, `@RequestParam`, `@PathVariable`

---

## 2. Service

```java
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PostService {
    private final PostDAO postDAO;
    private final FileDAO fileDAO;

    public void write(PostDTO postDTO, List<MultipartFile> files) {
        postDAO.save(postDTO);
        // 비즈니스 로직...
    }

    @Transactional(readOnly = true)
    public PostDTO detail(Long id) {
        return postDAO.findById(id).orElseThrow(PostNotFoundException::new);
    }

    public void delete(Long id) {
        postDAO.delete(id);
    }
}
```

**규칙:**
- 클래스: `@Service` + `@RequiredArgsConstructor` + `@Transactional(rollbackFor = Exception.class)`
- 조회 전용: `@Transactional(readOnly = true)` 메서드 레벨에 추가
- 메서드명: `write()`, `list()`, `detail()`, `update()`, `delete()` 등 행위 동사
- VO↔DTO 변환은 Service에서 처리
- 예외: `orElseThrow(CustomException::new)`

---

## 3. DAO (Repository)

```java
@Repository
@RequiredArgsConstructor
public class PostDAO {
    private final PostMapper postMapper;

    public void save(PostDTO postDTO) {
        postMapper.insert(postDTO);
    }

    public List<PostDTO> findAll() {
        return postMapper.selectAll();
    }

    public Optional<PostDTO> findById(Long id) {
        return postMapper.selectById(id);
    }

    public void setPost(PostVO postVO) {
        postMapper.update(postVO);
    }

    public void delete(Long id) {
        postMapper.delete(id);
    }
}
```

**규칙:**
- 클래스: `@Repository` + `@RequiredArgsConstructor`
- SQL 없음, Mapper 위임만
- 메서드명: `save()`, `find*()`, `findAll*()`, `set*()`, `delete*()`
- 단건 조회: `Optional<T>` 반환
- 다건 조회: `List<T>` 반환

---

## 4. Mapper Interface

```java
@Mapper
public interface PostMapper {
    void insert(PostDTO postDTO);
    List<PostDTO> selectAll();
    Optional<PostDTO> selectById(Long id);
    void update(PostVO postVO);
    void delete(Long id);
    int selectTotal();

    // 다중 파라미터 시 @Param
    List<PostDTO> selectAllWithFilter(
            @Param("memberId") Long memberId,
            @Param("fromDt") String fromDt,
            @Param("toDt") String toDt);
}
```

**규칙:**
- 인터페이스: `@Mapper`
- 메서드명: `select*`, `insert*`, `update*`, `delete*`
- 다중 파라미터: `@Param("name")` 필수
- 단건 조회: `Optional<T>`, 다건: `List<T>`, DML: `void` 또는 `int`(영향 행 수)

---

## 5. VO (Domain Object)

```java
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class PostVO extends Period {
    private Long id;
    private String postTitle;
    private String postContent;
    private Long memberId;
}
```

**규칙:**
- `@Getter`만 (setter 없음, 불변)
- `@NoArgsConstructor(access = AccessLevel.PROTECTED)`
- `@SuperBuilder` (Period 상속 시) 또는 `@Builder` (단독)
- `@EqualsAndHashCode(of = "id")`
- Period 상속: `createdDatetime`, `updatedDatetime` 포함

---

## 6. DTO (Data Transfer Object)

```java
@Getter @Setter
@ToString
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String postTitle;
    private String postContent;
    private Long memberId;
    private String memberName;        // JOIN 결과용 추가 필드
    private String createdDatetime;
    private String updatedDatetime;

    private List<TagDTO> tags = new ArrayList<>();   // 연관 컬렉션

    public PostVO toVO() {
        return PostVO.builder()
                .id(id)
                .postTitle(postTitle)
                .postContent(postContent)
                .memberId(memberId)
                .build();
    }
}
```

**규칙:**
- `@Getter @Setter @ToString @NoArgsConstructor` (public)
- `toVO()` 변환 메서드 포함
- 연관 컬렉션 초기화: `= new ArrayList<>()`
- JOIN 결과 필드, UI 전용 필드 포함 가능
- `@JsonIgnore`: 민감 필드(비밀번호 등)

---

## 7. XML Mapper

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.app.trycatch.mapper.post.PostMapper">

    <!-- INSERT: useGeneratedKeys + keyProperty -->
    <insert id="insert" useGeneratedKeys="true" keyProperty="id">
        insert into tbl_post (post_title, post_content, member_id)
        values (#{postTitle}, #{postContent}, #{memberId})
    </insert>

    <!-- 재사용 SQL 조각 -->
    <sql id="filterCondition">
        <if test="fromDt != null and fromDt != ''">
            and created_datetime >= #{fromDt}
        </if>
        <if test="toDt != null and toDt != ''">
            and created_datetime &lt; date_add(#{toDt}, interval 1 day)
        </if>
    </sql>

    <!-- SELECT: WHERE 자동 처리 -->
    <select id="selectAll" resultType="PostDTO">
        select p.id, p.post_title, m.member_name
        from tbl_post p
        join tbl_member m on p.member_id = m.id
        <where>
            <include refid="filterCondition"/>
        </where>
        order by p.id desc
    </select>

    <!-- 동적 조건 -->
    <select id="selectAllWithFilter" resultType="PostDTO">
        select * from tbl_post
        where member_id = #{memberId}
        <if test="fromDt != null and fromDt != ''">
            and created_datetime >= #{fromDt}
        </if>
        <if test="toDt != null and toDt != ''">
            and created_datetime &lt; date_add(#{toDt}, interval 1 day)
        </if>
        order by created_datetime desc
    </select>

    <update id="update">
        update tbl_post
        set post_title = #{postTitle},
            updated_datetime = current_timestamp
        where id = #{id}
    </update>

    <delete id="delete">
        delete from tbl_post where id = #{id}
    </delete>

</mapper>
```

**규칙:**
- namespace = Mapper 인터페이스 풀 경로
- INSERT: `useGeneratedKeys="true" keyProperty="id"`
- 동적 조건: `<if test="">`, `<where>`, `<choose><when><otherwise>`
- 재사용: `<sql id>` + `<include refid>`
- 다중 컬렉션: `<foreach>`
- XML 특수문자: `<` → `&lt;`
- 소문자 SQL 키워드 사용

---

## 8. 네이밍 규칙 요약

| 계층 | 클래스명 | 메서드 패턴 |
|------|---------|------------|
| Controller | `{Domain}Controller` | `goTo{X}Form()`, `list()`, `detail()`, `write()`, `update()`, `delete()` |
| Service | `{Domain}Service` | 행위 동사 그대로 |
| DAO | `{Domain}DAO` | `save()`, `find*()`, `findAll*()`, `set*()`, `delete*()` |
| Mapper | `{Domain}Mapper` | `insert*()`, `select*()`, `update*()`, `delete*()` |
| VO | `{Domain}VO` | - |
| DTO | `{Domain}DTO` | `toVO()`, `toXVO()` |

---

## 9. Enum 패턴

```java
public enum Status {
    ACTIVE("active"), INACTIVE("inactive");

    private String value;
    private static final Map<String, Status> MAP =
            Arrays.stream(Status.values())
                    .collect(Collectors.toMap(Status::getValue, Function.identity()));

    Status(String value) { this.value = value; }
    public String getValue() { return value; }
    public static Status getStatus(String value) { return MAP.get(value); }
}
```

---

## 10. 예외 처리 패턴

```java
// 커스텀 예외
@NoArgsConstructor
public class PostNotFoundException extends RuntimeException { }

// 핸들러
@ControllerAdvice(basePackages = "com.app.trycatch.controller.post")
public class PostExceptionHandler {
    @ExceptionHandler(PostNotFoundException.class)
    protected RedirectView notFound(PostNotFoundException e, RedirectAttributes ra) {
        ra.addFlashAttribute("error", "not-found");
        return new RedirectView("/post/list");
    }
}
```

---

# threetier_v5 추가 패턴
> 기준: `spring_day12/threetier_v5` (이메일_SMS_KakaoOAuth)

---

## 11. Mail Service 패턴

```java
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendMail(String mail, HttpServletResponse response) {
        String code = createCode();
        Cookie cookie = new Cookie("code", code);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 3); // 3분 만료
        response.addCookie(cookie);

        StringBuilder body = new StringBuilder();
        body.append("<html><body>");
        body.append("<a href=\"http://localhost:10000/mail/confirm?code=").append(code).append("\">인증하기</a>");
        body.append("<img src=\"cid:icon\">");
        body.append("</body></html>");

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("sender@gmail.com");
            helper.setTo(mail);
            helper.setSubject("인증");
            helper.setText(body.toString(), true); // true = HTML

            // 인라인 이미지
            helper.addInline("icon", new FileSystemResource(new File("path/to/icon.png")));
            // 첨부파일
            helper.addAttachment("file.txt", new FileSystemResource(new File("path/to/file.txt")));

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String createCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }
}
```

**Controller에서 @CookieValue 사용:**
```java
@GetMapping("confirm")
public RedirectView confirm(
        @CookieValue(value = "code", required = false) String cookieCode,
        String code) {
    if (cookieCode == null || cookieCode.isEmpty()) {
        return new RedirectView("/mail/fail");
    }
    if (cookieCode.equals(code)) {
        Cookie cookie = new Cookie("code", null);
        cookie.setMaxAge(0); // 쿠키 삭제
        cookie.setPath("/");
        response.addCookie(cookie);
        return new RedirectView("/mail/success");
    }
    return new RedirectView("/mail/fail");
}
```

**application.yml Gmail SMTP 설정:**
```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email
    password: your-app-password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

**규칙:**
- `MimeMessageHelper(mimeMessage, true, "UTF-8")` — multipart + 인코딩
- `setText(body, true)` — 두 번째 인수 `true` = HTML
- 인라인 이미지: `addInline("cid명", FileSystemResource)`, HTML에서 `cid:cid명`으로 참조
- 첨부파일: `addAttachment("파일명", FileSystemResource)`
- 쿠키 삭제: `setMaxAge(0)`

---

## 12. SMS Service 패턴 (@PostConstruct)

```java
@Service
public class SmsService {
    private DefaultMessageService messageService;

    @PostConstruct
    private void init() {
        this.messageService = SolapiClient.INSTANCE
                .createInstance("API_KEY", "API_SECRET");
    }

    public void sendSms(String phone) {
        Message message = new Message();
        message.setFrom("01000000000");
        message.setTo(phone);
        message.setText("인증번호: 123456");
        try {
            messageService.send(message);
        } catch (SolapiMessageNotReceivedException e) {
            System.out.println(e.getFailedMessageList());
        }
    }
}
```

**규칙:**
- `@PostConstruct` — DI 완료 후 초기화 메서드 (필드에 DI 의존성 없으면 생성자 대신 사용)
- Solapi SDK: `SolapiClient.INSTANCE.createInstance(key, secret)`

---

## 13. Kakao OAuth 2.0 패턴

```java
@Service
@RequiredArgsConstructor
public class KaKaoService {
    // 1단계: 인가코드 → 액세스 토큰
    public String getAccessToken(String code) {
        URL url = new URL("https://kauth.kakao.com/oauth/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String params = "grant_type=authorization_code"
                + "&client_id=YOUR_CLIENT_ID"
                + "&redirect_uri=http://localhost:10000/kakao/login"
                + "&code=" + code;

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
            bw.write(params);
        }

        if (conn.getResponseCode() == 200) {
            StringBuilder result = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) result.append(line);
            }
            JsonElement json = JsonParser.parseString(result.toString());
            return json.getAsJsonObject().get("access_token").getAsString();
        }
        return null;
    }

    // 2단계: 액세스 토큰 → 사용자 정보
    public MemberDTO getKakaoInfo(String token) {
        URL url = new URL("https://kapi.kakao.com/v2/user/me");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        if (conn.getResponseCode() == 200) {
            // ... 응답 읽기 동일 ...
            JsonObject kakaoAccount = JsonParser.parseString(result)
                    .getAsJsonObject().get("kakao_account").getAsJsonObject();
            String email = kakaoAccount.get("email").getAsString();
            String name = kakaoAccount.get("profile").getAsJsonObject()
                    .get("nickname").getAsString();

            MemberDTO dto = new MemberDTO();
            dto.setMemberEmail(email);
            dto.setMemberName(name);
            return dto;
        }
        return null;
    }
}
```

**Controller에서 flash attribute:**
```java
@GetMapping("/kakao/login")
public RedirectView kakaoLogin(String code, RedirectAttributes ra) {
    MemberDTO dto = kaKaoService.kakaoLogin(code);
    if (dto.getId() == null) {
        ra.addFlashAttribute("kakao", dto); // 리다이렉트 후 Model에 접근 가능
        return new RedirectView("/member/kakao/join");
    }
    session.setAttribute("member", dto);
    return new RedirectView("/post/list");
}
```

**규칙:**
- `conn.setDoOutput(true)` — POST body 전송 시 필수
- `Authorization: Bearer {token}` — OAuth 토큰 헤더
- GSON: `JsonParser.parseString()` → `getAsJsonObject()` → `get("key").getAsString()`
- `addFlashAttribute()` — 리다이렉트 후 1회 접근 가능한 Model 속성

---

## 14. MyBatis TypeHandler 패턴

```java
@MappedTypes(Status.class)
public class StatusHandler implements TypeHandler<Status> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Status parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter.getValue()); // Java → SQL
    }

    @Override
    public Status getResult(ResultSet rs, String columnName) throws SQLException {
        return Status.getStatus(rs.getString(columnName)); // SQL → Java
    }

    @Override
    public Status getResult(ResultSet rs, int columnIndex) throws SQLException {
        return Status.getStatus(rs.getString(columnIndex));
    }

    @Override
    public Status getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Status.getStatus(cs.getString(columnIndex));
    }
}
```

**규칙:**
- `@MappedTypes(MyEnum.class)` — 대상 타입 지정
- `setParameter()` — Java 타입 → SQL 값 변환
- `getResult()` — SQL 값 → Java 타입 변환 (3가지 오버로드 모두 구현)

---

## 15. Spring Converter 패턴

```java
@Component
public class StringToStatusConverter implements Converter<String, Status> {
    @Override
    public Status convert(String source) {
        return Status.getStatus(source); // Enum의 static 조회 메서드 사용
    }
}
```

**규칙:**
- `@Component` — Spring이 자동으로 ConversionService에 등록
- `Converter<String, TargetType>` — 폼 파라미터 → Enum 자동 변환
- TypeHandler는 MyBatis용, Converter는 Spring MVC 폼 바인딩용

---

## 16. 페이지네이션 (hasMore 패턴)

```java
@Setter @Getter @ToString
public class Criteria {
    private int page;
    private int rowCount = 10;
    private int count;     // rowCount + 1 (다음 페이지 존재 여부 확인용)
    private int offset;
    private boolean hasMore;

    public Criteria(int page, int total) {
        this.count = rowCount + 1; // +1개 더 조회해서 hasMore 판단
        this.page = Math.max(1, page);
        this.offset = (page - 1) * rowCount;
        // pageCount, startPage, endPage 계산...
    }
}
```

**XML에서 페이지네이션:**
```xml
<select id="selectAll">
    select * from tbl_post
    order by id desc
    limit #{criteria.count} offset #{criteria.offset}
</select>
```

**Service에서 hasMore 설정:**
```java
List<PostDTO> list = postDAO.findAll(criteria);
criteria.setHasMore(list.size() == criteria.getCount());
if (criteria.isHasMore()) list.remove(list.size() - 1); // 마지막 1개 제거
```

**규칙:**
- DB에서 `rowCount + 1`개 조회
- 결과 size == count 이면 다음 페이지 존재 (`hasMore = true`)
- hasMore 판단 후 마지막 항목 제거 (클라이언트에는 rowCount개만 전달)

---

# threetier_v6 추가 패턴
> 기준: `spring_day12/threetier_v6_REST_인터셉터`

---

## 17. Interceptor 패턴

```java
// 1. Interceptor 구현
@RequiredArgsConstructor
public class TestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 컨트롤러 실행 전
        request.setAttribute("test", "값");
        return true; // false 반환 시 요청 중단
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러 실행 후, 뷰 렌더링 전
    }
}

// 2. 설정 클래스에 등록
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TestInterceptor())
                .addPathPatterns("/test/**")
                // .excludePathPatterns("/test/skip")
                ;
    }
}
```

**규칙:**
- `HandlerInterceptor` 구현
- `preHandle()` — `true` 반환 시 계속 진행, `false` 반환 시 중단
- `postHandle()` — 뷰 렌더링 전 처리
- `@Configuration` + `WebMvcConfigurer` 클래스에 등록
- `addPathPatterns()` — 적용 경로, `excludePathPatterns()` — 제외 경로

---

## 18. REST Controller 완전 CRUD 패턴

```java
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replies/**")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("write")
    public void write(@RequestBody ReplyDTO replyDTO) {
        replyService.write(replyDTO);
    }

    @GetMapping("list/{page}")
    public ReplyWithPagingDTO list(@PathVariable int page, Long postId) {
        return replyService.getListInPost(page, postId);
    }

    @PutMapping("{id}")
    public void update(@RequestBody ReplyDTO replyDTO) {
        replyService.update(replyDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        replyService.delete(id);
    }
}
```

**응답 DTO 래퍼 (페이지네이션 포함):**
```java
@Getter @Setter @ToString @NoArgsConstructor
public class ReplyWithPagingDTO {
    private List<ReplyDTO> replies;
    private Criteria criteria;
}
```

**규칙:**
- `@RestController` — `@Controller` + `@ResponseBody` 결합, JSON 자동 직렬화
- `/api/` 접두어 — REST 엔드포인트 구분
- `@PostMapping` + `@RequestBody` — JSON body → DTO 자동 역직렬화
- `@PutMapping("{id}")` — 전체 수정
- `@DeleteMapping("{id}")` + `@PathVariable` — 삭제
- `@ResponseBody` 불필요 (`@RestController`가 포함)

---

## 19. XML Mapper 페이지네이션 쿼리

```xml
<select id="selectAllByPostId" resultType="ReplyDTO">
    select
        m.member_name,
        r.id, r.reply_content, r.member_id, r.post_id,
        r.created_datetime, r.updated_datetime
    from tbl_member m join tbl_reply r on m.id = r.member_id
    where post_id = #{id}
    order by r.id desc
    limit #{criteria.rowCount} offset #{criteria.offset}
</select>

<select id="selectCountAllByPostId" resultType="int">
    select count(*) from tbl_reply
    where post_id = #{id}
</select>
```

**규칙:**
- `limit #{criteria.rowCount} offset #{criteria.offset}` — 페이지네이션
- `#{criteria.필드명}` — 중첩 객체 필드 접근
- 전체 카운트 쿼리 별도 작성 (`selectCount*`)

---

## 20. 네이밍 규칙 보완 (v5/v6 추가분)

| 항목 | 패턴 | 예시 |
|------|------|------|
| REST Controller | `{Domain}Controller` + `@RestController` + `/api/` prefix | `ReplyController` |
| Interceptor | `{Name}Interceptor` + `HandlerInterceptor` | `TestInterceptor` |
| WebMvc 설정 | `WebMvcConfig` + `@Configuration` + `WebMvcConfigurer` | `WebMvcConfig` |
| TypeHandler | `{Enum}Handler` + `@MappedTypes` + `TypeHandler<T>` | `StatusHandler` |
| Converter | `StringTo{Enum}Converter` + `@Component` + `Converter<String,T>` | `StringToStatusConverter` |
| 페이지네이션 DTO | `{Domain}WithPagingDTO` | `ReplyWithPagingDTO` |
| 조회 기준 | `Criteria` | `Criteria` |
