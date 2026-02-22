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

// 팀원 초대
invitationTeamMemberButton.addEventListener("click", (e) => {
    if (!invitationInput.value) {
        // server: 이메일 유효성 검사 필요
        alert("초대할 팀원의 이메일을 입력해주세요.");
    } else {
        // server: 이메일을 보내고 초대가 정상적으로 되었는지 검사
        alert("정상적으로 초대되었습니다.");
    }
});

// 팀원 내보내기
teamMemberRows.forEach((row) => {
    const moreOptionLayer = row.querySelector(".more-option");

    row.addEventListener("click", (e) => {
        if (e.target.closest(".moreOptionButton")) {
            moreOptionLayer.classList.toggle("active");
        }

        if (e.target.closest(".memberDelBtn")) {
            confirm("정말로 팀원을 내보내시겠습니까?") &&
                alert("팀원이 내보내기가 완료되었습니다.");
        }
    });
});
document.addEventListener("click", (e) => {
    if (!e.target.closest(".moreOptionButton")) {
        teamMemberRows.forEach((row) => {
            row.querySelector(".more-option").classList.remove("active");
        });
    }
});
