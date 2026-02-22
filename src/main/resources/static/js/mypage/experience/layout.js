const experienceLayout = (() => {
    // 1. 지원취소 팝업
    const openCancelPopup = (dimmedDiv, popupApplyCancel) => {
        dimmedDiv.style.position = "fixed";
        popupApplyCancel.style.display = "block";
    };

    const closeCancelPopup = (dimmedDiv, popupApplyCancel) => {
        dimmedDiv.style.position = "relative";
        popupApplyCancel.style.display = "none";
    };

    // 2. 취소사유 드롭다운
    const toggleReasonDrop = (applyListDrop) => {
        applyListDrop.classList.toggle("visible");
    };

    const selectReason = (index, buttonText, applyListButtonSpan, applyCancelReason, applyListDrop) => {
        applyListButtonSpan.textContent = buttonText;
        applyListDrop.classList.remove("visible");
        if (index !== 3) {
            applyCancelReason.style.display = "none";
        } else {
            applyCancelReason.style.display = "block";
        }
    };

    // 3. 조회기간 버튼
    const updatePeriodActive = (selectDueButton, selectDueButtons) => {
        selectDueButtons.forEach((btn) => btn.classList.remove("on"));
        selectDueButton.classList.add("on");
    };

    // 4. 공통 드롭다운 (진행여부 등)
    const toggleCommonDrop = (dropDownDiv) => {
        // 다른 드롭다운 닫기
        document.querySelectorAll(".lyItems").forEach((div) => {
            if (div !== dropDownDiv) div.style.display = "none";
        });
        dropDownDiv.style.display = (dropDownDiv.style.display === "block" ? "none" : "block");
    };

    const selectCommonDropItem = (dropDownButton, dropDownDiv, itemText) => {
        dropDownButton.textContent = itemText;
        dropDownDiv.style.display = "none";
    };

    const closeAllDrops = () => {
        document.querySelectorAll(".lyItems").forEach((div) => {
            div.style.display = "none";
        });
    };

    // 5. 지원이력서 모달
    const openResumeModal = (resumeModalOverlay) => {
        resumeModalOverlay.classList.add("active");
        document.body.style.overflow = "hidden";
    };

    const closeResumeModal = (resumeModalOverlay) => {
        resumeModalOverlay.classList.remove("active");
        document.body.style.overflow = "";
    };

    // 6. 지원취소됨 표시
    const showCancelled = (cancelBtn) => {
        const td = cancelBtn.closest("td");
        if (td) {
            td.innerHTML = "<span>지원취소됨.</span>";
        }
    };

    return {
        openCancelPopup, closeCancelPopup, toggleReasonDrop, selectReason,
        updatePeriodActive, toggleCommonDrop, selectCommonDropItem, closeAllDrops,
        openResumeModal, closeResumeModal, showCancelled
    };
})();
