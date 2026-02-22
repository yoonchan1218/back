document.addEventListener("DOMContentLoaded", () => {
    // 1. 지원취소 팝업
    const applyCancelButtons = document.querySelectorAll(".btn.btnGyBd.devBtnCancel.devBtnOddInfo");
    const buttonClose = document.querySelector(".butClose.mtuSpImg.devLyBtnClose");
    const dimmedDiv = document.querySelector(".dimmed");
    const popupApplyCancel = document.querySelector(".mtuPopup.popupApplyCancel");
    const buttonCancel = document.querySelector(".button.button-cancel");
    const applySubmitButton = document.querySelector(".button.button-ok.devBtnSubmitCancel");

    let currentApplyId = null;
    let currentCancelBtn = null;

    if (applyCancelButtons.length > 0 && popupApplyCancel) {
        applyCancelButtons.forEach((btn) => {
            btn.addEventListener("click", () => {
                currentApplyId = btn.dataset.idx;
                currentCancelBtn = btn;
                experienceLayout.openCancelPopup(dimmedDiv, popupApplyCancel);
            });
        });

        const close = () => experienceLayout.closeCancelPopup(dimmedDiv, popupApplyCancel);
        if (buttonClose) buttonClose.addEventListener("click", close);
        if (buttonCancel) buttonCancel.addEventListener("click", close);
        if (applySubmitButton) {
            applySubmitButton.addEventListener("click", () => {
                if (!currentApplyId || applySubmitButton.disabled) return;
                applySubmitButton.disabled = true;
                experienceService.cancelApply(currentApplyId, (success) => {
                    applySubmitButton.disabled = false;
                    if (success) {
                        close();
                        experienceLayout.showCancelled(currentCancelBtn);
                        currentApplyId = null;
                        currentCancelBtn = null;
                    }
                });
            });
        }
    }

    // 2. 취소사유 드롭다운
    const applyListButton = document.querySelector(".btnChoose");
    if (applyListButton) {
        const applyListDrop = applyListButton.nextElementSibling;
        const applyLists = applyListDrop.querySelectorAll("li");
        const applyListButtonSpan = applyListButton.firstElementChild;
        const applyCancelReason = document.getElementById("applyCancelreason");
        const applyCancelReasonInput = document.getElementById("Apply_Cncl_Rsn_Etc");

        applyListButton.addEventListener("click", () => experienceLayout.toggleReasonDrop(applyListDrop));

        applyLists.forEach((li, i) => {
            li.addEventListener("click", () => {
                const text = li.querySelector("button").textContent;
                experienceLayout.selectReason(i, text, applyListButtonSpan, applyCancelReason, applyListDrop);
            });
        });

        if (applyCancelReasonInput) {
            applyCancelReasonInput.addEventListener("input", () => {
                if (applyCancelReasonInput.value.length > 30) {
                    alert("최대 30자만 입력 가능합니다");
                    applyCancelReasonInput.value = applyCancelReasonInput.value.substring(0, 30);
                }
            });
        }
    }

    // 3. 조회기간 버튼
    const selectDueButtons = document.querySelectorAll(".formBx.clear button");
    selectDueButtons.forEach((btn) => {
        btn.addEventListener("click", () => experienceLayout.updatePeriodActive(btn, selectDueButtons));
    });

    // 4. 공통 드롭다운
    const dropDownButtons = document.querySelectorAll(".btnMtcLySel.mtcBtnB");
    dropDownButtons.forEach((btn) => {
        const dropDownDiv = btn.nextElementSibling;
        const dropDownItems = dropDownDiv.querySelectorAll("li a");

        btn.addEventListener("click", (e) => {
            e.stopPropagation();
            experienceLayout.toggleCommonDrop(dropDownDiv);
        });

        dropDownItems.forEach((item) => {
            item.addEventListener("click", (e) => {
                e.stopPropagation();
                experienceLayout.selectCommonDropItem(btn, dropDownDiv, item.textContent);
            });
        });
    });

    document.addEventListener("click", () => experienceLayout.closeAllDrops());

    // 5. 지원이력서 모달
    const resumeButtons = document.querySelectorAll(".button.button-resume.resumeTile");
    const resumeModalOverlay = document.getElementById("resumeModalOverlay");
    const resumeModalClose = document.getElementById("resumeModalClose");

    if (resumeModalOverlay) {
        resumeButtons.forEach((btn) => {
            btn.addEventListener("click", (e) => {
                e.preventDefault();
                experienceLayout.openResumeModal(resumeModalOverlay);
            });
        });

        const close = () => experienceLayout.closeResumeModal(resumeModalOverlay);
        if (resumeModalClose) resumeModalClose.addEventListener("click", close);
        resumeModalOverlay.addEventListener("click", (e) => {
            if (e.target === resumeModalOverlay) close();
        });

        document.addEventListener("keydown", (e) => {
            if (e.key === "Escape" && resumeModalOverlay.classList.contains("active")) close();
        });
    }
});
