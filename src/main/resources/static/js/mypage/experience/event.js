document.addEventListener("DOMContentLoaded", () => {
    const buttonClose = document.querySelector(".butClose.mtuSpImg.devLyBtnClose");
    const dimmedDiv = document.querySelector(".dimmed");
    const popupApplyCancel = document.querySelector(".mtuPopup.popupApplyCancel");
    const buttonCancel = document.querySelector(".button.button-cancel");
    const applySubmitButton = document.querySelector(".button.button-ok.devBtnSubmitCancel");

    let currentApplyId = null;
    let currentCancelBtn = null;
    let currentApplyStatus = null;

    // 현재 필터 파라미터 수집
    const getFilterParams = () => {
        const fromDt = document.getElementById("txtFromDt")?.value || "";
        const toDt = document.getElementById("txtToDt")?.value || "";
        const keyword = document.getElementById("txtSearchText")?.value || "";
        const programStatus = document.querySelector("[data-filter='programStatus'] .btnMtcLySel")?.dataset.selectedVal || "";
        const applyStatus = document.querySelector("[data-filter='applyStatus'] .btnMtcLySel")?.dataset.selectedVal || "";
        return { fromDt, toDt, keyword, programStatus, applyStatus };
    };

    const doFilter = () => {
        const params = getFilterParams();
        experienceService.filterApplyList(params, (applies) => {
            if (applies !== false) experienceLayout.renderApplyList(applies);
        });
    };

    // 1. 지원취소 팝업 - 개별 리스너 등록 함수
    const setupCancelListeners = () => {
        document.querySelectorAll(".devBtnCancel").forEach(btn => {
            if (btn.dataset.listenerAttached === "true") return;
            btn.dataset.listenerAttached = "true";
            btn.addEventListener("click", () => {
                currentApplyId = btn.dataset.idx;
                currentCancelBtn = btn;
                currentApplyStatus = btn.dataset.status;
                experienceLayout.openCancelPopup(dimmedDiv, popupApplyCancel);
            });
        });
    };

    if (popupApplyCancel) {
        setupCancelListeners();
        document.addEventListener("appliesRendered", setupCancelListeners);

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
                        experienceLayout.decrementStatusCount(currentApplyStatus);
                        currentApplyId = null;
                        currentCancelBtn = null;
                        currentApplyStatus = null;
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

    // 3. 조회기간 버튼 - 클릭 즉시 필터 적용
    const selectDueButtons = document.querySelectorAll(".dev-btn-period");
    selectDueButtons.forEach((btn) => {
        btn.addEventListener("click", () => {
            experienceLayout.updatePeriodActive(btn, selectDueButtons);

            const toStr = d => d.toISOString().split("T")[0];
            const today = new Date();
            const period = btn.dataset.period;
            let fromDt;

            if (period === "99") {
                const d = new Date(today);
                d.setDate(d.getDate() - 7);
                fromDt = toStr(d);
            } else {
                const d = new Date(today);
                d.setMonth(d.getMonth() - parseInt(period));
                fromDt = toStr(d);
            }
            const toDt = toStr(today);

            // 날짜 입력 필드도 동기화
            const fromInput = document.getElementById("txtFromDt");
            const toInput = document.getElementById("txtToDt");
            if (fromInput) fromInput.value = fromDt;
            if (toInput) toInput.value = toDt;

            const params = { ...getFilterParams(), fromDt, toDt };
            experienceService.filterApplyList(params, (applies) => {
                if (applies !== false) experienceLayout.renderApplyList(applies);
            });
        });
    });

    // 4. 공통 드롭다운 (진행여부, 열람여부, 지원상태)
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
                experienceLayout.selectCommonDropItem(btn, dropDownDiv, item.textContent.trim());
                btn.dataset.selectedVal = item.dataset.val || "";
            });
        });
    });

    document.addEventListener("click", () => experienceLayout.closeAllDrops());

    // 5. 검색 버튼
    const btnSubmit = document.getElementById("btnSubmit");
    if (btnSubmit) {
        btnSubmit.addEventListener("click", doFilter);
    }

    // 6. 키워드 입력 엔터키
    const txtSearchText = document.getElementById("txtSearchText");
    if (txtSearchText) {
        txtSearchText.addEventListener("keydown", (e) => {
            if (e.key === "Enter") doFilter();
        });
    }

    // 7. 지원이력서 모달
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
