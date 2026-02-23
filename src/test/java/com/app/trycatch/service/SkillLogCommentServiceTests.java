import com.app.trycatch.common.enumeration.file.FileContentType;
import com.app.trycatch.dto.file.FileDTO;
import com.app.trycatch.dto.skilllog.SkillLogCommentDTO;
import com.app.trycatch.dto.skilllog.SkillLogCommentFileDTO;
import com.app.trycatch.dto.skilllog.SkillLogCommentWithPagingDTO;
import com.app.trycatch.dto.skilllog.SkillLogNestedCommentWithPagingDTO;
import com.app.trycatch.mapper.file.FileMapper;
import com.app.trycatch.mapper.skilllog.SkillLogCommentFileMapper;
import com.app.trycatch.mapper.skilllog.SkillLogCommentMapper;
import com.app.trycatch.service.skilllog.SkillLogCommentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SkillLogCommentServiceTests {
    @Autowired
    private SkillLogCommentService skillLogCommentService;
    @Autowired
    private SkillLogCommentMapper skillLogCommentMapper;
    @Autowired
    private SkillLogCommentFileMapper skillLogCommentFileMapper;
    @Autowired
    private FileMapper fileMapper;

    //    댓글 작성 (파일 없이)
    @Test
    public void testWriteWithoutFile() {
        SkillLogCommentDTO dto = new SkillLogCommentDTO();
        dto.setMemberId(9L);
        dto.setSkillLogId(30L);
        dto.setSkillLogCommentContent("서비스 테스트 댓글 (파일 없음)");

        skillLogCommentService.write(dto, null);

        log.info("작성된 댓글 ID: {}", dto.getId());
    }

    //    답글 작성 (파일 없이)
    @Test
    public void testWriteReplyWithoutFile() {
        SkillLogCommentDTO dto = new SkillLogCommentDTO();
        dto.setMemberId(9L);
        dto.setSkillLogId(30L);
        dto.setSkillLogCommentParentId(1L);
        dto.setSkillLogCommentContent("서비스 테스트 답글 (파일 없음)");

        skillLogCommentService.write(dto, null);

        log.info("작성된 답글 ID: {}", dto.getId());
    }

    //    댓글 목록
    @Test
    public void testGetListInSkillLog() {
        SkillLogCommentWithPagingDTO result = skillLogCommentService.getListInSkillLog(1, 30L);

        log.info("댓글 수: {}", result.getCriteria().getTotal());
        result.getSkillLogComments().forEach(comment ->
                log.info("댓글: id={}, 작성자={}, 내용={}, 시간={}",
                        comment.getId(), comment.getMemberName(),
                        comment.getSkillLogCommentContent(), comment.getCreatedDatetime())
        );
    }

    //    대댓글 목록
    @Test
    public void testGetListInSkillLogAndParentComment() {
        SkillLogNestedCommentWithPagingDTO result =
                skillLogCommentService.getListInSkillLogAndParentComment(1, 30L, 1L);

        log.info("답글 수: {}", result.getCriteria().getTotal());
        result.getSkillLogNestedComments().forEach(comment ->
                log.info("답글: id={}, 작성자={}, 내용={}, 시간={}",
                        comment.getId(), comment.getMemberName(),
                        comment.getSkillLogCommentContent(), comment.getCreatedDatetime())
        );
    }

    //    댓글 수정 (파일 없이)
    @Test
    public void testUpdateWithoutFile() {
        SkillLogCommentDTO dto = new SkillLogCommentDTO();
        dto.setId(1L);
        dto.setSkillLogCommentContent("수정된 댓글 내용");

        skillLogCommentService.update(dto, null);

        log.info("댓글 수정 완료: id={}", dto.getId());
    }

    //    댓글 삭제 (파일 없는 댓글, 답글도 없음)
    @Test
    public void testDeleteWithoutFile() {
        // 먼저 테스트용 댓글 생성
        SkillLogCommentDTO dto = new SkillLogCommentDTO();
        dto.setMemberId(9L);
        dto.setSkillLogId(30L);
        dto.setSkillLogCommentContent("삭제 테스트용 댓글");
        skillLogCommentMapper.insert(dto);

        log.info("삭제 대상 댓글 ID: {}", dto.getId());

        // 삭제
        skillLogCommentService.delete(dto.getId(), null);

        log.info("댓글 삭제 완료");
    }

    //    댓글 삭제 (파일 있는 댓글, 답글도 없음)
    @Test
    public void testDeleteWithFile() {
        // 1. 테스트용 댓글 생성
        SkillLogCommentDTO commentDTO = new SkillLogCommentDTO();
        commentDTO.setMemberId(9L);
        commentDTO.setSkillLogId(30L);
        commentDTO.setSkillLogCommentContent("파일 있는 삭제 테스트용 댓글");
        skillLogCommentMapper.insert(commentDTO);

        // 2. 테스트용 파일 생성
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFilePath("test");
        fileDTO.setFileName("test_delete.png");
        fileDTO.setFileOriginalName("delete.png");
        fileDTO.setFileSize("1024");
        fileDTO.setFileContentType(FileContentType.IMAGE);
        fileMapper.insert(fileDTO);

        // 3. 중간 테이블 연결
        SkillLogCommentFileDTO commentFileDTO = new SkillLogCommentFileDTO();
        commentFileDTO.setId(fileDTO.getId());
        commentFileDTO.setSkillLogCommentId(commentDTO.getId());
        skillLogCommentFileMapper.insert(commentFileDTO.toSkillLogCommentFileVO());

        log.info("삭제 대상 - 댓글 ID: {}, 파일 ID: {}", commentDTO.getId(), fileDTO.getId());

        // 4. 삭제
        skillLogCommentService.delete(commentDTO.getId(), fileDTO.getId());

        log.info("댓글+파일 삭제 완료");
    }

    //    댓글 삭제 (답글이 있는 부모 댓글)
    @Test
    public void testDeleteParentWithReplies() {
        // 1. 부모 댓글 생성
        SkillLogCommentDTO parentDTO = new SkillLogCommentDTO();
        parentDTO.setMemberId(9L);
        parentDTO.setSkillLogId(30L);
        parentDTO.setSkillLogCommentContent("부모 댓글 (답글 포함 삭제 테스트)");
        skillLogCommentMapper.insert(parentDTO);

        // 2. 답글 생성
        SkillLogCommentDTO replyDTO = new SkillLogCommentDTO();
        replyDTO.setMemberId(9L);
        replyDTO.setSkillLogId(30L);
        replyDTO.setSkillLogCommentParentId(parentDTO.getId());
        replyDTO.setSkillLogCommentContent("답글 (삭제 테스트)");
        skillLogCommentMapper.insert(replyDTO);

        log.info("삭제 대상 - 부모 ID: {}, 답글 ID: {}", parentDTO.getId(), replyDTO.getId());

        // 3. 부모 댓글 삭제 (답글도 같이 삭제되어야 함)
        skillLogCommentService.delete(parentDTO.getId(), null);

        log.info("부모 댓글 + 답글 삭제 완료");
    }

    //    게시글 삭제 시 댓글 전체 삭제
    @Test
    public void testDeleteAllBySkillLogId() {
        // 주의: 실제 게시글의 모든 댓글이 삭제됩니다.
        // 테스트용 게시글 ID를 사용하세요.
        Long testSkillLogId = 30L;

        log.info("게시글 {} 댓글 전체 삭제 시작", testSkillLogId);

        int beforeCount = skillLogCommentMapper.selectCountAllBySkillLogId(testSkillLogId);
        log.info("삭제 전 댓글 수: {}", beforeCount);

//        skillLogCommentService.deleteAllBySkillLogId(testSkillLogId);

        int afterCount = skillLogCommentMapper.selectCountAllBySkillLogId(testSkillLogId);
        log.info("삭제 후 댓글 수: {}", afterCount);
    }
}



