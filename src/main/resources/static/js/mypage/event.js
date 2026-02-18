document.addEventListener("DOMContentLoaded", () => {
    /* mypage.html logic */
    // 팁 닫기
    const tipCloseButton = document.querySelector(".my-status-top .badge .tip .tip-close");
    const tipDiv = document.querySelector(".my-status-top .badge .tip");
    if (tipCloseButton && tipDiv) {
        tipCloseButton.addEventListener("click", () => {
            tipDiv.classList.add("close");
        });
    }

    // 스크랩 버튼 (이벤트 위임)
    document.addEventListener("click", (e) => {
        const scrapbutton = e.target.closest(".tplBtnTy.js-scrBtn");
        if (scrapbutton) {
            e.preventDefault();
            e.stopPropagation();
            scrapbutton.classList.toggle("on");
            scrapbutton.classList.toggle("tplBtnScrOff");
            scrapbutton.classList.toggle("tplBtnScrOn");
        }
    });

    // 원픽 공고 슬라이더
    const wrapper = document.querySelector(".celebrate_my .swiper-wrapper");
    const prevBtn = document.querySelector(".celebrate-btn-prev");
    const nextBtn = document.querySelector(".celebrate-btn-next");
    const slides = document.querySelectorAll(".celebrate_my .swiper-slide");

    if (wrapper && prevBtn && nextBtn) {
        const slidesPerView = 3;
        const slideWidth = 304;
        const slideGap = 12;
        const moveDistance = (slideWidth + slideGap) * slidesPerView;
        const totalPages = Math.ceil(slides.length / slidesPerView);
        let currentPage = 0;

        const updateSlider = () => {
            const translateX = -currentPage * moveDistance;
            wrapper.style.transform = `translate3d(${translateX}px, 0px, 0px)`;
            wrapper.style.transitionDuration = "300ms";
            prevBtn.classList.toggle("swiper-button-disabled", currentPage === 0);
            nextBtn.classList.toggle("swiper-button-disabled", currentPage === totalPages - 1);
        };

        nextBtn.addEventListener("click", () => {
            if (currentPage < totalPages - 1) {
                currentPage++;
                updateSlider();
            }
        });

        prevBtn.addEventListener("click", () => {
            if (currentPage > 0) {
                currentPage--;
                updateSlider();
            }
        });

        updateSlider();
    }

    // 스크랩, 최근 본 공고 탭 전환
    const tabLists = document.querySelectorAll(".mtuTab.devFixedTab ul li");
    const recentContent = document.getElementById("recentContent");
    const scrapContent = document.getElementById("scrapContent");
    const moreLink = document.getElementById("moreLink");

    if (tabLists.length > 0) {
        tabLists.forEach((tabList) => {
            tabList.addEventListener("click", () => {
                tabLists.forEach((tab) => tab.classList.remove("on"));
                tabList.classList.add("on");

                const flag = tabList.getAttribute("data-flag");
                if (flag === "SC") {
                    if (recentContent) recentContent.style.display = "none";
                    if (scrapContent) scrapContent.style.display = "block";
                    if (moreLink) moreLink.textContent = "스크랩 공고 더보기";
                } else {
                    if (scrapContent) scrapContent.style.display = "none";
                    if (recentContent) recentContent.style.display = "block";
                    if (moreLink) moreLink.textContent = "최근 본 공고 더보기";
                }
            });
        });
    }

    // 프로필 사진 편집 모달
    const profileModal = document.getElementById("profileModal");
    const profileBtn = document.querySelector(".profile-btn.btnRegist");
    const modalClose = document.getElementById("modalClose");
    const modalCancel = document.getElementById("modalCancel");
    const modalConfirm = document.querySelector(".btn-confirm");
    const profileInput = document.getElementById("profileInput");
    const previewImg = document.getElementById("previewImg");
    const currentProfileImg = document.querySelector(".profile-img .img img");

    if (profileModal && profileBtn) {
        profileBtn.addEventListener("click", () => {
            if (previewImg && currentProfileImg) {
                previewImg.src = currentProfileImg.src || "/images/default_photo.png";
            }
            profileModal.classList.add("show");
        });

        const closeModal = () => profileModal.classList.remove("show");
        if (modalClose) modalClose.addEventListener("click", closeModal);
        if (modalCancel) modalCancel.addEventListener("click", closeModal);
        if (modalConfirm) {
            modalConfirm.addEventListener("click", () => {
                if (previewImg && currentProfileImg) {
                    currentProfileImg.src = previewImg.src;
                }
                closeModal();
            });
        }
        profileModal.addEventListener("click", (e) => {
            if (e.target === profileModal) closeModal();
        });
    }

    /* change-my-information.html logic */
    const profileBirth = document.getElementById("profileBirth");
    if (profileBirth) {
        // 포커스 시 에러 메시지 숨기기
        const helpWraps = document.querySelectorAll(".mbrHelpWrap");
        helpWraps.forEach((wrap) => {
            const inputs = wrap.querySelectorAll("input, select, textarea");
            inputs.forEach((input) => {
                input.addEventListener("focus", () => {
                    const helpLayer = wrap.querySelector(".mbrLayerHelp");
                    if (helpLayer) helpLayer.style.display = "none";
                });
            });
        });

        // 필드 초기화
        const birth = profileBirth.value;
        const gender = document.getElementById("profileGender")?.value;
        const phone = document.getElementById("profilePhone")?.value;

        if (birth && birth.includes("-")) {
            const [y, m, d] = birth.split("-");
            const yEl = document.getElementById("M_Year");
            const mEl = document.getElementById("M_Month");
            const dEl = document.getElementById("M_Day");
            if (yEl) yEl.value = y;
            if (mEl) mEl.value = String(parseInt(m, 10));
            if (dEl) dEl.value = String(parseInt(d, 10));
        }

        if (gender) {
            const man = document.getElementById("M_Gender0");
            const women = document.getElementById("M_Gender1");
            if (gender === "man" && man) man.checked = true;
            if (gender === "women" && women) women.checked = true;
        }

        if (phone) {
            const parts = phone.split("-");
            if (parts.length >= 3) {
                const p1 = document.getElementById("M_Hand_Phone1");
                const p2 = document.getElementById("M_Hand_Phone2");
                const p3 = document.getElementById("M_Hand_Phone3");
                if (p1) p1.value = parts[0];
                if (p2) p2.value = parts[1];
                if (p3) p3.value = parts[2];
            }
        }

        // 주소 검색
        const btnSearchAddr = document.getElementById("btnSearchAddr");
        const postcheck = document.getElementById("postcheck");
        const execDaumPostcode = () => {
            new daum.Postcode({
                oncomplete: (data) => {
                    const zonecode = data.zonecode;
                    let addr = data.userSelectedType === "R" ? data.roadAddress : data.jibunAddress;
                    let extraAddr = "";
                    if (data.userSelectedType === "R") {
                        if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) extraAddr += data.bname;
                        if (data.buildingName !== "" && data.apartment === "Y") extraAddr += (extraAddr !== "" ? ", " + data.buildingName : data.buildingName);
                        if (extraAddr !== "") extraAddr = " (" + extraAddr + ")";
                    }
                    document.getElementById("postcheck").value = zonecode;
                    document.getElementById("M_ZipCode").value = zonecode;
                    document.getElementById("M_Addr_Text").value = addr + extraAddr;
                    const addrDetail = document.getElementById("M_Addr_Text1");
                    addrDetail.value = "";
                    addrDetail.focus();
                },
            }).open();
        };
        if (btnSearchAddr) btnSearchAddr.addEventListener("click", execDaumPostcode);
        if (postcheck) postcheck.addEventListener("click", execDaumPostcode);

        // 이메일 선택
        const emailSelect = document.getElementById("Email_Addr");
        const emailInput = document.getElementById("Email_Addr_Text");
        if (emailSelect && emailInput) {
            emailSelect.addEventListener("change", () => {
                if (emailSelect.value !== "etc" && emailSelect.value !== "") {
                    emailInput.value = emailSelect.value;
                    emailInput.readOnly = true;
                } else {
                    emailInput.value = "";
                    emailInput.readOnly = false;
                    emailInput.focus();
                }
            });
        }

        // 수정 버튼
        const modifyBtn = document.querySelector(".mbrBtnModify_1 a");
        if (modifyBtn) {
            modifyBtn.addEventListener("click", (e) => {
                e.preventDefault();
                const form = document.forms["form"];
                const birth = `${form.M_Year.value}-${String(form.M_Month.value).padStart(2, "0")}-${String(form.M_Day.value).padStart(2, "0")}`;
                const gender = form.querySelector("input[name='M_Gender']:checked")?.value === "1" ? "women" : "man";
                const phone = [form.M_Hand_Phone1?.value, form.M_Hand_Phone2.value, form.M_Hand_Phone3.value].filter(Boolean).join("-");
                const email = `${form.Email_ID.value.trim()}@${form.Email_Addr_Text.value.trim()}`;

                const upsertHidden = (name, value) => {
                    let input = form.querySelector(`input[name="${name}"]`);
                    if (!input) {
                        input = document.createElement("input");
                        input.type = "hidden";
                        input.name = name;
                        form.appendChild(input);
                    }
                    input.value = value || "";
                };

                upsertHidden("memberName", form.M_Name.value.trim());
                upsertHidden("memberEmail", email);
                upsertHidden("memberPhone", phone);
                upsertHidden("individualMemberBirth", birth);
                upsertHidden("individualMemberGender", gender);
                form.submit();
            });
        }

        // 취소 버튼
        const cancelButton = document.querySelector(".mbrBtn.mbrBtnCancel_1 button");
        if (cancelButton) {
            cancelButton.addEventListener("click", () => {
                location.href = "/mypage/mypage";
            });
        }
    }
});
