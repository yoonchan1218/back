package com.app.trycatch.service.corporate;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.domain.corporate.CorpTeamMemberVO;
import com.app.trycatch.domain.member.MemberVO;
import com.app.trycatch.dto.corporate.CorpTeamMemberDTO;
import com.app.trycatch.dto.corporate.CorpTeamMemberWithPagingDTO;
import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import com.app.trycatch.dto.corporate.CorpProgramWithPagingDTO;
import com.app.trycatch.dto.member.CorpMemberDTO;
import com.app.trycatch.repository.corporate.CorpTeamMemberDAO;
import com.app.trycatch.repository.experience.ExperienceProgramDAO;
import com.app.trycatch.repository.experience.ExperienceProgramFileDAO;
import com.app.trycatch.domain.experience.ExperienceProgramFileVO;
import com.app.trycatch.common.enumeration.file.FileContentType;
import com.app.trycatch.dto.file.FileDTO;
import com.app.trycatch.repository.file.CorpLogoFileDAO;
import com.app.trycatch.repository.file.FileDAO;
import com.app.trycatch.dto.qna.QnaDTO;
import com.app.trycatch.repository.member.AddressDAO;
import com.app.trycatch.repository.member.CorpMemberDAO;
import com.app.trycatch.repository.member.MemberDAO;
import com.app.trycatch.repository.qna.QnaDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorporateService {

    private final CorpMemberDAO corpMemberDAO;
    private final MemberDAO memberDAO;
    private final AddressDAO addressDAO;
    private final ExperienceProgramDAO experienceProgramDAO;
    private final CorpTeamMemberDAO corpTeamMemberDAO;
    private final FileDAO fileDAO;
    private final CorpLogoFileDAO corpLogoFileDAO;
    private final ExperienceProgramFileDAO experienceProgramFileDAO;
    private final QnaDAO qnaDAO;

    // ── 기업회원 여부 확인 ──────────────────────────────────────────────

    /** 해당 memberId가 tbl_corp에 존재하는지 확인 */
    public boolean isCorpMember(Long memberId) {
        return corpMemberDAO.findById(memberId).isPresent();
    }

    // ── 기업 정보 ──────────────────────────────────────────────────────

    /** 기업 + 회원 통합 정보 조회 */
    public CorpMemberDTO getCorpInfo(Long corpId) {
        return corpMemberDAO.findById(corpId)
                .orElseThrow(() -> new IllegalArgumentException("기업 정보를 찾을 수 없습니다. id=" + corpId));
    }

    /** 기업 정보(tbl_corp) + 주소(tbl_address) 수정 */
    public void updateCorpInfo(CorpMemberDTO dto) {
        corpMemberDAO.update(dto.toCorpVO());
        if (dto.getAddressId() != null) {
            // 기존 주소가 있으면 UPDATE
            addressDAO.update(dto.toAddressVO());
        } else if (dto.getAddressZipcode() != null && !dto.getAddressZipcode().isEmpty()) {
            // 주소가 없는데 새로 입력했으면 INSERT + member 업데이트
            dto.setAddressId(dto.getId());
            addressDAO.save(dto.toAddressVO());
            memberDAO.updateAddressIdById(dto.getId());
        }
    }

    /** 회원 정보(tbl_member) 수정 — 비밀번호 빈값이면 UPDATE 제외 */
    public void updateMemberInfo(CorpMemberDTO dto) {
        memberDAO.update(dto.toMemberVO());
    }

    // ── 로고 업로드 ────────────────────────────────────────────────────

    /** 기업 로고 업로드 — 기존 로고가 있으면 교체 */
    public String uploadCorpLogo(Long corpId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        // 기존 로고 삭제
        corpLogoFileDAO.findByCorpId(corpId).ifPresent(existing -> {
            corpLogoFileDAO.deleteByCorpId(corpId);
            fileDAO.delete((Long) existing.get("id"));
        });

        // 파일 저장
        String todayPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String rootPath = "C:/file/";
        String path = rootPath + todayPath;

        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + file.getOriginalFilename();

        File directory = new File(path);
        if (!directory.exists()) directory.mkdirs();
        file.transferTo(new File(path, fileName));

        // tbl_file INSERT
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFilePath(todayPath);
        fileDTO.setFileName(fileName);
        fileDTO.setFileOriginalName(file.getOriginalFilename());
        fileDTO.setFileSize(String.valueOf(file.getSize()));
        fileDTO.setFileContentType(FileContentType.IMAGE);
        fileDAO.save(fileDTO);

        // tbl_corp_logo_file INSERT
        corpLogoFileDAO.save(fileDTO.getId(), corpId);

        return "/api/files/display?filePath=" + todayPath + "&fileName=" + fileName;
    }

    /** 기업 로고 삭제 */
    public void deleteCorpLogo(Long corpId) {
        corpLogoFileDAO.findByCorpId(corpId).ifPresent(existing -> {
            corpLogoFileDAO.deleteByCorpId(corpId);
            fileDAO.delete((Long) existing.get("id"));
        });
    }

    // ── 홈 대시보드 ────────────────────────────────────────────────────

    /** 상태별 프로그램 수 Map 반환 (draft/recruiting/closed/cancelled) */
    public Map<String, Long> getProgramStats(Long corpId) {
        return experienceProgramDAO.countByStatus(corpId);
    }

    /** 최신 프로그램 N개 */
    public List<ExperienceProgramDTO> getRecentPrograms(Long corpId, int limit) {
        return experienceProgramDAO.findLatestByCorpId(corpId, limit);
    }

    // ── 팀원 관리 ──────────────────────────────────────────────────────

    /** 팀원 목록 (5명씩 페이징) */
    public CorpTeamMemberWithPagingDTO getTeamMembers(Long corpId, int page) {
        final int rowCount = 5;
        int total = corpTeamMemberDAO.countByCorpId(corpId);

        Criteria criteria = new Criteria(page, total);
        recalcCriteria(criteria, rowCount);

        List<CorpTeamMemberDTO> list = corpTeamMemberDAO.findByCorpId(corpId, criteria);
        boolean hasMore = list.size() > rowCount;
        if (hasMore) list = list.subList(0, rowCount);

        return new CorpTeamMemberWithPagingDTO(list, criteria, hasMore);
    }

    /** 팀원 초대 — 이메일로 회원 조회 후 tbl_corp_team_member에 추가 */
    public void inviteTeamMember(Long corpId, String email) {
        MemberVO member = memberDAO.findByMemberEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 회원을 찾을 수 없습니다."));
        CorpTeamMemberVO vo = CorpTeamMemberVO.builder()
                .id(member.getId())
                .corpId(corpId)
                .corpTeamMemberStatus("wait")
                .build();
        corpTeamMemberDAO.save(vo);
    }

    /** 팀원 제거 */
    public void removeTeamMember(Long memberId, Long corpId) {
        corpTeamMemberDAO.delete(memberId, corpId);
    }

    // ── 프로그램 등록 ──────────────────────────────────────────────────

    /** 새 프로그램 등록 */
    public void createProgram(ExperienceProgramDTO dto, List<MultipartFile> files) {
        experienceProgramDAO.save(dto);
        Long programId = dto.getId();

        // 파일이 있으면 저장
        if (files != null && !files.isEmpty()) {
            String todayPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String rootPath = "C:/file/";
            String path = rootPath + todayPath;

            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                String uuid = UUID.randomUUID().toString();
                String fileName = uuid + "_" + file.getOriginalFilename();

                // tbl_file INSERT
                FileDTO fileDTO = new FileDTO();
                fileDTO.setFilePath(todayPath);
                fileDTO.setFileName(fileName);
                fileDTO.setFileOriginalName(file.getOriginalFilename());
                fileDTO.setFileSize(String.valueOf(file.getSize()));
                fileDTO.setFileContentType(
                        file.getContentType() != null && file.getContentType().contains("image")
                                ? FileContentType.IMAGE : FileContentType.OTHER);
                fileDAO.save(fileDTO);

                // tbl_experience_program_file INSERT
                ExperienceProgramFileVO epfVO = ExperienceProgramFileVO.builder()
                        .id(fileDTO.getId())
                        .experienceProgramId(programId)
                        .build();
                experienceProgramFileDAO.save(epfVO);

                // 디스크에 파일 저장
                File directory = new File(path);
                if (!directory.exists()) directory.mkdirs();
                try {
                    file.transferTo(new File(path, fileName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // ── 프로그램 관리 ──────────────────────────────────────────────────

    /** 프로그램 목록 (페이징 + 상태 필터 + 키워드 검색) */
    public CorpProgramWithPagingDTO getPrograms(Long corpId, int page, int rowCount,
                                                       String status, String keyword) {
        int total = experienceProgramDAO.countByCorpId(corpId, status, keyword);

        Criteria criteria = new Criteria(page, total);
        recalcCriteria(criteria, rowCount);

        List<ExperienceProgramDTO> list =
                experienceProgramDAO.findByCorpId(corpId, criteria, status, keyword);
        boolean hasMore = list.size() > rowCount;
        if (hasMore) list = list.subList(0, rowCount);

        return new CorpProgramWithPagingDTO(list, criteria, hasMore);
    }

    // ── 참여자 관리 (tbl_challenger 스키마 확인 후 구현 예정) ──────────

    public Object getParticipants(Long programId, Long corpId, String status, int page) {
        // tbl_challenger / tbl_apply 스키마 확인 후 별도 구현
        return Map.of("list", List.of(), "total", 0);
    }

    public void updateParticipantStatus(Long participantId, Long corpId, String newStatus) {
        // tbl_challenger 상태 변경 — 추후 구현
    }

    public void rejectParticipant(Long participantId, Long corpId, String feedback) {
        // tbl_challenger 상태 변경 + tbl_feedback 저장 — 추후 구현
    }

    // ── 홈 하단: QNA ──────────────────────────────────────────────────

    /** 최신 QNA N개 */
    public List<QnaDTO> getRecentQnas(int limit) {
        return qnaDAO.findLatest(limit);
    }

    // ── 내부 헬퍼 ────────────────────────────────────────────────────

    /**
     * Criteria 생성자는 rowCount=20 기본값으로 pagination을 계산하므로,
     * 커스텀 rowCount 사용 시 startPage/endPage/realEnd를 재계산해야 한다.
     */
    private void recalcCriteria(Criteria c, int rowCount) {
        c.setRowCount(rowCount);
        c.setCount(rowCount + 1);
        c.setOffset((c.getPage() - 1) * rowCount);
        int realEnd = (int) Math.ceil(c.getTotal() / (double) rowCount);
        realEnd = Math.max(1, realEnd);
        int endPage = (int) (Math.ceil(c.getPage() / (double) c.getPageCount()) * c.getPageCount());
        int startPage = endPage - c.getPageCount() + 1;
        endPage = Math.min(endPage, realEnd);
        startPage = Math.max(1, startPage);
        c.setRealEnd(realEnd);
        c.setEndPage(endPage);
        c.setStartPage(startPage);
    }
}
