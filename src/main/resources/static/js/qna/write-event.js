// 질문·글쓰기 버튼 → create-menu 토글
const createMenu = document.querySelector(".navi-top-area .create-menu");
const btnQuestion = document.querySelector(".btn-question");

btnQuestion.addEventListener("click", (e) => {
    createMenu.classList.toggle("tooltip-open");
});

//체크박스-드롭다운 연동
//체크박스와 드롭다운 매칭 (체크 해제 시 드롭다운 비활성화)
const checkboxLis = document.querySelectorAll(
    ".checkListArea .checkboxCommWrap > li",
);
const selectListArea = document.querySelector(".selectListArea");
const dropdownLis = selectListArea.querySelectorAll(":scope > ul > li");

checkboxLis.forEach((checkboxLi, index) => {
    const checkbox = checkboxLi.querySelector('input[type="checkbox"]');
    const dropdownLi = dropdownLis?.[index];
    const dropdown = dropdownLi.querySelector(".btn-select");

    if (checkbox && dropdown) {
        // 체크박스 변경 이벤트
        checkbox.addEventListener("change", () => {
            dropdown.classList.toggle("on", checkbox.checked);
        });

        // 초기 상태 동기화
        dropdown.classList.toggle("on", checkbox.checked);
    }
});

// 직무선배 체크박스(로그인)
const firstCheckButton =
    document.getElementById("lb_targetCheck2").nextElementSibling;
const jobCheckDrop = document.querySelector(
    ".btn-select.qnaSpA.devQnaWriteBizJobtypeDropDownButton.devQnaWriteBizJobtypeLayer",
);
const jobCheckDropDown = document.querySelector(
    ".layer-box-wrap.job.innerScroll",
);
const jobCheckDropDownCancelButton = document.querySelector(
    ".btn-layer-close.qnaSpB.devQnaWriteBizJobtypeLayerClose",
);
const searchDetailButton = document.querySelector(
    ".btn-direct-search.qnaSpA.devQnaWriteBizJobtypeDirectSearchButton",
);
const searchDetailButtonResult = document.querySelector(
    ".layer-box-wrap.job-directInput",
);
const jobToggleSelected = document.querySelectorAll(".tab.qnaSpA");

const tabMenuClassOnDiv = document.querySelector(".category-group");

const tabMenuCancelButton = document.querySelector(
    ".btn-layer-close.qnaSpB.devQnaWriteBizJobtypeDirectSearchLayerClose",
);

const recentSearchTab = document.querySelector(
    ".layer-cont.devRecentSearchBizJobtypeEmpty",
);

const applyJobButton = document.querySelector(
    ".btn-apply.devQnaWriteBizJobtypeApplyButton",
);

// 직무선배 버튼 이벤트(로그인)
firstCheckButton.addEventListener("click", (e) => {
    jobCheckDrop.classList.toggle("on");
});

jobCheckDrop.addEventListener("click", (e) => {
    // 다른 드롭다운 닫기
    document
        .querySelector(".layer-box-wrap.corporation.innerScroll")
        .classList.remove("open");
    // 직무선배 드롭다운 열기/닫기
    jobCheckDropDown.classList.toggle("open");
});

jobCheckDropDownCancelButton.addEventListener("click", (e) => {
    jobCheckDropDown.classList.remove("open");
});

tabMenuCancelButton.addEventListener("click", (e) => {
    jobCheckDropDown.classList.remove("open");
    searchDetailButtonResult.classList.remove("open");
});

searchDetailButton.addEventListener("click", (e) => {
    searchDetailButtonResult.classList.toggle("open");
});

const myJobToggle = document.querySelector(
    ".subtitle-wrap.has-tooltip.devQnaWriteBizJobtypeLayerTooltip",
);
const myJobButton = myJobToggle.querySelector("button");

myJobButton.addEventListener("click", (e) => {
    myJobToggle.classList.toggle("tooltip-open");
});


const layerContJob = document.querySelector(
    ".layer-cont.devRecentSearchBizJobtype",
);

// 전체삭제 버튼 클릭 이벤트(서버쪽 해야할 코드)
// document.addEventListener("click", (e) => {
//     if (e.target.closest(".btnAllDel.devQnaWriteBizJobtypeAllDeleteButton")) {
//         const layerContJob = document.querySelector(
//             ".layer-cont.devRecentSearchBizJobtype",
//         );
//         const emptySearchResult = document.querySelector(
//             ".layer-cont.devRecentSearchBizJobtypeEmpty",
//         );

//         // ✅ searchList 안의 내용 전부 삭제!
//         const searchList = layerContJob.querySelector(".searchList");
//         searchList.innerHTML = "";

//         layerContJob.style.display = "none";
//         emptySearchResult.style.display = "block";
//     }
// });
// 적용 버튼
const jobApplyButton = document.querySelector(
    ".btn-apply.devQnaWriteBizJobtypeApplyButton",
);

// 변수에 담아놓고 사용(로그인)
let tempSelect = null;

jobToggleSelected.forEach((jobToggleSelected) => {
    jobToggleSelected.addEventListener("click", (e) => {
        if (tempSelect) {
            tempSelect.classList.remove("selected");
        }
        tempSelect = jobToggleSelected;
        jobToggleSelected.classList.add("selected");
    });
});



const corpSeniorDropdownDiv = document.querySelector(
    ".layer-box-wrap.corporation.innerScroll",
);
const corpSeniorDropdown = document.querySelector(
    ".btn-select.qnaSpA.devQnaWriteCompanyLayer.devQnaWriteCompanyDropDownButton ",
);

corpSeniorDropdown.addEventListener("click", (e) => {
    // 다른 드롭다운 닫기
    document
        .querySelector(".layer-box-wrap.job.innerScroll")
        .classList.remove("open");
    document
        .querySelector(".layer-box-wrap.job-directInput")
        .classList.remove("open");
    // 기업선배 드롭다운 열기
    corpSeniorDropdownDiv.classList.add("open");
});

const corpSeniorDropdownCancelButton = corpSeniorDropdownDiv.querySelector(
    ".btn-layer-close.qnaSpB.devQnaWriteCompanyLayerClose",
);
corpSeniorDropdownCancelButton.addEventListener("click", (e) => {
    corpSeniorDropdownDiv.classList.remove("open");
});

const corpInputDiv = document.querySelector(
    ".jkSchInput.keywordSearch.devQnaWriteCompanyLayerKeyworkSearchDiv ",
);
const corpInput = corpInputDiv.querySelector(
    ".schInp.devQnaWriteCompanyLayerSearchInput",
);
corpInput.addEventListener("input", (e) => {
    corpInputDiv.classList.add("focus");
    if (!corpInput.value) {
        corpInputDiv.classList.remove("focus");
    }
});
// X 버튼(공용)
const btnLayerCloses = document.querySelectorAll(".btn-layer-close.qnaSpB");
// 링크

// 링크 버튼
const iconBtnLayerOpen = document.querySelector(
    ".icon-link.qnaSpB.btn-layer-open",
);
// 링크 레이어
const linkLayer = document.querySelector(".layer-box-wrap.link-layer");

// 링크 레이어 X버튼
btnLayerCloses.forEach((btnLayerClose) => {
    btnLayerClose.addEventListener("click", (e) => {
        linkLayer.classList.remove("open");
        iconBtnLayerOpen.classList.remove("on");
    });
});

// 링크 클릭
iconBtnLayerOpen.addEventListener("click", (e) => {
    linkLayer.classList.toggle("open");
    iconBtnLayerOpen.classList.toggle("on");
});

const urlRegex = /^(http|https):\/\/[\w\-]+(\.[\w\-]+)+[/#?]?.*$/;

const inputText = document.querySelector(
    ".jkSchInput.keywordSearch.keywordSearchLink p input[type=text]",
);
const findButton = document.querySelector(".btnSch.qnaSpB.devSearchLink");

// 로딩
const loading = document.querySelector(".loading");

// url 검사 함수 (중복 코드 방지)
function checkUrl() {
    if (urlRegex.test(inputText.value)) {
        loading.style.display = "block";
        setTimeout(() => {
            loading.style.display = "none";
            const a = document.querySelector(".ApiOpenGraphResult");
            a.innerHTML = `
                <a href="#" class="attach-box type-link">
                    <div class="thumb-img-area">
                        <span>링크</span>
                    </div>
                    <div class="link-textarea">
                        <textarea class="corp-name" name="" id="devAttachLinkTitle" cols="20" rows="5" maxlength="51" placeholder="링크 제목을 입력하세요."></textarea>
                    </div>
                </a>
            `;
        }, 1800);
    } else {
        alert("URL을 입력해주세요");
    }
}

// 버튼 클릭
findButton.addEventListener("click", checkUrl);

// 엔터키 입력
inputText.addEventListener("keydown", (e) => {
    if (e.key === "Enter") {
        checkUrl();
    }
});

// 링크 첨부하기 버튼 활성화

// 링크 제목 입력창
const linkTitleInput = document.querySelector(".ApiOpenGraphResult");
// 첨부하기 버튼
const attachButton = document.querySelector(".apply.attachLinkBtn");

// 입력할 때마다 체크
linkTitleInput.addEventListener("input", (e) => {
    // 링크 제목에 들어갈 변수 담기

    if (e.target.value) {
        attachButton.classList.add("on");
    } else {
        attachButton.classList.remove("on");
    }
});

// 링크 추가
const textarea = document.querySelector(".addFileAndLink");
attachButton.addEventListener("click", (e) => {
    const getText = document.getElementById("devAttachLinkTitle");
    const getURL = document.querySelector(
        ".jkSchInput.keywordSearch.keywordSearchLink p.inpWrap input.schInp",
    );
    if (attachButton.classList.contains("on")) {
        linkLayer.classList.remove("open");
        iconBtnLayerOpen.classList.remove("on");

        textarea.innerHTML += `
            <div class="attach-wrap">
                <a href="#" class="attach-box type-link">
                    <span class="thumb-img-area">
                        <span>링크</span>
                    </span>
                <div class="corp-info-area qnaSpA">
                    <p class="content">${getText.value}</p>
                    <span class="content-url">${getURL.value}</span>
                </div>
            </a>
            <button type="button" class="remove-button qnaSpB">삭제하기</button>
            </div>
        `;
    }
    const a = document.querySelector(".ApiOpenGraphResult");
    a.innerHTML = "";
    getText.value = "";
    getURL.value = "";

    textarea.addEventListener("click", (e) => {
        if (e.target.classList.contains("remove-button")) {
            console.log("들어옴");
            e.target.closest(".attach-wrap").remove();
        }
    });
});

// 링크 삭제
const removeLink = document.querySelector(".attach-box.type-link");

// 이미지 버튼
const addPicture = document.querySelector(".icon-photo.qnaSpB.btn-layer-open");
// 숨겨진 파일 input
const photoInput = document.getElementById("photoInput");

// 버튼 클릭 → 숨겨진 input 클릭
addPicture.addEventListener("click", () => {
    photoInput.click();
});

// 파일 선택 시 처리
photoInput.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (!file) return;

    // 이미지 파일인지 확인
    if (!file.type.startsWith("image/")) {
        alert("이미지 파일만 올릴 수 있습니다.");
        return;
    }

    const reader = new FileReader();
    reader.readAsDataURL(file);

    reader.addEventListener("load", (v) => {
        const path = v.target.result;

        // 이미지를 화면에 추가
        textarea.innerHTML += `
            <div class="attach-wrap attach-image">
                <div class="attach-box type-image">
                    <img src="${path}" alt="첨부 이미지">
                </div>
                <button type="button" class="remove-button qnaSpB">삭제하기</button>
            </div>
        `;
    });

    // 같은 파일 다시 선택 가능하도록 초기화
    e.target.value = "";
});

// 이미지 삭제 (이벤트 위임)
textarea.addEventListener("click", (e) => {
    if (e.target.classList.contains("remove-button")) {
        e.target.closest(".attach-wrap").remove();
    }
});

// 드롭다운 버튼 (선택 결과 표시)
const jobSelectResult = document.querySelector(
    ".btn-select.qnaSpA.devQnaWriteBizJobtypeDropDownButton",
);

// 직무선배 체크박스 (적용 버튼용)
const jobCheckboxForApply = document.getElementById("lb_targetCheck2");

jobApplyButton.addEventListener("click", (e) => {
    const selectedRadio = document.querySelector(
        'input[name="jobCategorySmallId"]:checked',
    );

    if (selectedRadio) {
        const selectedItem = selectedRadio.closest(".devBizJobtypeItem");
        const jobName = selectedItem.dataset.bizjobtypeName;
        const jobCode = selectedItem.dataset.bizjobtypeCode;

        // 기존 코드 (드롭다운 버튼에 적용)
        jobSelectResult.dataset.bizjobtypeName = jobName;
        jobSelectResult.dataset.bizjobtypeCode = jobCode;
        jobSelectResult.textContent = jobName;

        // 최근검색에 추가하는 코드 (서버 연동 시 주석 해제)
        // addToRecentSearch(jobName, jobCode);

        // 나머지 기존 코드...
        if (jobCheckboxForApply) {
            jobCheckboxForApply.checked = true;
        }
        jobSelectResult.classList.add("on");
        jobCheckDropDown.classList.remove("open");
        searchDetailButtonResult.classList.remove("open");
    } else {
        alert("직무를 선택해주세요");
    }
});

// 최근검색 추가 함수(서버쪽 코드)
// function addToRecentSearch(jobName, jobCode) {
//     const layerContJob = document.querySelector(
//         ".layer-cont.devRecentSearchBizJobtype",
//     );
//     const recentSearchList = layerContJob.querySelector(".searchList");
//     const emptySearchResult = document.querySelector(
//         ".layer-cont.devRecentSearchBizJobtypeEmpty",
//     );

//     console.log("recentSearchList:", recentSearchList);
//     console.log("jobName:", jobName, "jobCode:", jobCode);

//     // 중복 체크
//     const existing = recentSearchList.querySelector(
//         `[data-bizjobtype-code="${jobCode}"]`,
//     );
//     if (existing) return;

//     // 새 li 생성
//     const newLi = document.createElement("li");
//     newLi.className = "devBizJobtypeMyItem";
//     newLi.dataset.bizjobtypeName = jobName;
//     newLi.dataset.bizjobtypeCode = jobCode;
//     newLi.innerHTML = `<button type="button" class="qnaSpB">${jobName}</button>`;

//     // 목록 맨 앞에 추가
//     recentSearchList.prepend(newLi);

//     // "없을 때" → "있을 때"로 전환
//     if (layerContJob.style.display === "none") {
//         layerContJob.style.display = "block";
//         emptySearchResult.style.display = "none";
//     }
// }
// 탭 메뉴 (직무/산업)
const tabMenuButtons = document.querySelectorAll(
    ".tab-box-wrap > .tab-menu > li.tab",
);
const categoryGroups = document.querySelectorAll(
    ".tab-box-wrap > .category-group",
);

tabMenuButtons.forEach((tabMenuButton, index) => {
    tabMenuButton.addEventListener("click", (e) => {
        tabMenuButtons.forEach((btn) => btn.classList.remove("selected"));
        tabMenuButton.classList.add("selected");

        categoryGroups.forEach((group, i) => {
            group.classList.toggle("on", i === index);

            if (i === index) {
                const categoryButtons = group.querySelectorAll(".tab.qnaSpA");
                categoryButtons.forEach((btn) =>
                    btn.classList.remove("selected"),
                );
                if (categoryButtons[0])
                    categoryButtons[0].classList.add("selected");

                const contLists = group.querySelectorAll(".cont-list");
                contLists.forEach((list) => {
                    list.classList.remove("attached");
                    list.style.display = "none";
                });

                const blankBox = group.querySelector(".blank-box-wrap");
                if (blankBox) blankBox.style.display = "block";

                group
                    .querySelectorAll('input[type="radio"]')
                    .forEach((r) => (r.checked = false));
            }
        });
    });
});

// 카테고리 버튼 클릭
categoryGroups.forEach((group) => {
    const categoryButtons = group.querySelectorAll(".tab.qnaSpA");
    const contLists = group.querySelectorAll(".cont-list");
    const blankBox = group.querySelector(".blank-box-wrap");
    let tempSelectRadio = null;

    categoryButtons.forEach((btn, index) => {
        btn.addEventListener("click", (e) => {
            categoryButtons.forEach((b) => b.classList.remove("selected"));
            btn.classList.add("selected");

            if (blankBox) blankBox.style.display = "none";

            contLists.forEach((list, i) => {
                list.style.display = i === index ? "block" : "none";
                list.classList.toggle("attached", i === index);
            });

            tempSelectRadio = null;
            group
                .querySelectorAll('input[type="radio"]')
                .forEach((r) => (r.checked = false));
        });
    });

    group.querySelectorAll('input[type="radio"]').forEach((radio) => {
        radio.addEventListener("change", () => {
            if (tempSelectRadio && tempSelectRadio !== radio)
                tempSelectRadio.checked = false;
            tempSelectRadio = radio;
        });
    });
});

// 나의직무 / 최근검색 바로 적용
// 직무선배
const layerBoxJob = document.querySelector(".layer-box-wrap.job.innerScroll");
// 나의직무/최근검색 버튼들 (직무선배 내부의 searchList 안의 qnaSpB만)
const quickSelectButtons = layerBoxJob.querySelectorAll(".searchList .qnaSpB");
// 드롭다운 트리거 버튼
const jobTriggerBtn = document.querySelector(
    ".btn-select.devQnaWriteBizJobtypeDropDownButton",
);
// 직무선배 체크박스
const jobCheckboxForQuick = document.getElementById("lb_targetCheck2");

quickSelectButtons.forEach((btn) => {
    btn.addEventListener("click", () => {
        const jobName = btn.textContent.trim();

        // 드롭다운 버튼 텍스트 변경
        if (jobTriggerBtn) {
            jobTriggerBtn.textContent = jobName;
        }

        // 모달 닫기
        if (layerBoxJob) {
            layerBoxJob.classList.remove("open");
        }

        // 직무선배 체크박스 체크 + 드롭다운 활성화
        if (jobCheckboxForQuick) {
            jobCheckboxForQuick.checked = true;
        }
        if (jobTriggerBtn) {
            jobTriggerBtn.classList.add("on");
        }
    });
});

// 기업선배 드롭다운
const layerBoxCorp = document.querySelector(
    ".layer-box-wrap.corporation.innerScroll",
);
// 최근검색 버튼들
const corpQuickSelectButtons = layerBoxCorp.querySelectorAll(
    ".searchList .qnaSpB",
);
// 드롭다운 트리거 버튼
const corpTriggerBtn = document.querySelector(
    ".btn-select.devQnaWriteCompanyDropDownButton",
);
// 기업선배 체크박스
const corpCheckboxForQuick = document.getElementById("lb_targetCheck3");

corpQuickSelectButtons.forEach((btn) => {
    btn.addEventListener("click", () => {
        const corpName = btn.textContent.trim();

        // 드롭다운 버튼 텍스트 변경
        if (corpTriggerBtn) {
            corpTriggerBtn.textContent = corpName;
        }

        // 모달 닫기
        if (layerBoxCorp) {
            layerBoxCorp.classList.remove("open");
        }

        // 기업선배 체크박스 체크 + 드롭다운 활성화
        if (corpCheckboxForQuick) {
            corpCheckboxForQuick.checked = true;
        }
        if (corpTriggerBtn) {
            corpTriggerBtn.classList.add("on");
        }
    });
});

// 등록하기 버튼
const admitButton = document.querySelector(".btnQuestion.devQnaWriteButton");
// 제목 가져오기
const writeTitle = document.querySelector(".jkSchInp.devQnaWriteTitle");
// 내용 가져오기
const writeContent = document.querySelector(".devQnaWriteCntnt.custom-editor");

admitButton.addEventListener("click", (e) => {
    if (!confirm("수정 하시겠습니까?")) {
        e.preventDefault();
    }
    if (!writeTitle.value) {
        alert("제목을 입력해주세요");
        e.preventDefault();
    } else if (!writeContent.value) {
        alert("내용을 입력해주세요");
        e.preventDefault();
    } else {
        alert("수정 완료되었습니다.");
        location.href = "";
    }
});

// 취소하기 버튼
const cancelButton = document.querySelector(
    ".btnCancel.bg_white.devQnaWriteCancelButton",
);

cancelButton.addEventListener("click", (e) => {
    if (!confirm("다른 페이지로 이동 시, 수정된 글이 저장되지 않습니다.")) {
        e.preventDefault(); //새로고침을 막아준다.
        return false;
    }
    location.href = "";
});
