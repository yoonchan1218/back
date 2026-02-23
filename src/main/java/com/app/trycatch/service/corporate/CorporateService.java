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
import com.app.trycatch.repository.member.AddressDAO;
import com.app.trycatch.repository.member.CorpMemberDAO;
import com.app.trycatch.repository.member.MemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorporateService {

    private final CorpMemberDAO corpMemberDAO;
    private final MemberDAO memberDAO;
    private final AddressDAO addressDAO;
    private final ExperienceProgramDAO experienceProgramDAO;
    private final CorpTeamMemberDAO corpTeamMemberDAO;

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
            addressDAO.update(dto.toAddressVO());
        }
    }

    /** 회원 정보(tbl_member) 수정 — 비밀번호 빈값이면 UPDATE 제외 */
    public void updateMemberInfo(CorpMemberDTO dto) {
        memberDAO.update(dto.toMemberVO());
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
