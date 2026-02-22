// 팀원 초대
const invitationButton = document.getElementById("invitation_button");
const invitationModal = document.querySelector(".company-invitation-modal");
const invitationModalCloseButton = document.querySelector(
    ".company-invitation-modal .close-button",
);
const invitationTeamMemberButton = document.querySelector(
    ".company-invitation-modal .modal-btn",
);
const invitationInput = form.invitation_mail;

// 팀원 메뉴 버튼
const teamMemberRows = document.querySelectorAll(
    ".teamWrap .mtcSchListTb table tbody tr",
);

// 팀원 초대
invitationButton.addEventListener("click", (e) => {
    // 모달 열기
    invitationModal.classList.add("active");
});
invitationModalCloseButton.addEventListener("click", (e) => {
    // 모달 닫기
    invitationModal.classList.remove("active");
});

// 팀원 초대 — 이메일 유효성 검사 후 폼 submit
invitationTeamMemberButton.addEventListener("click", (e) => {
    if (!invitationInput.value) {
        alert("초대할 팀원의 이메일을 입력해주세요.");
    } else {
        document.getElementById("form").submit();
    }
});

// 팀원 내보내기
teamMemberRows.forEach((row) => {
    const moreOptionLayer = row.querySelector(".more-option");
    if (!moreOptionLayer) return; // no-team-member 행 등 .more-option 없는 행 무시

    row.addEventListener("click", (e) => {
        if (e.target.closest(".moreOptionButton")) {
            moreOptionLayer.classList.toggle("active");
        }

        if (e.target.closest(".memberDelBtn")) {
            e.preventDefault(); // 폼 즉시 submit 방지
            if (confirm("정말로 팀원을 내보내시겠습니까?")) {
                e.target.closest("form").submit();
            }
        }
    });
});
document.addEventListener("click", (e) => {
    if (!e.target.closest(".moreOptionButton")) {
        teamMemberRows.forEach((row) => {
            const moreOption = row.querySelector(".more-option");
            if (moreOption) moreOption.classList.remove("active");
        });
    }
});
