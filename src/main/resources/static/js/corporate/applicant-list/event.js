// 위의 전체, 수락, 거절, 미응답 누르면 span태그 class에 active 삽입
const listChecks = document.querySelectorAll(".status-tabs li span");

let templist = listChecks[0];

listChecks[0].classList.add("active");

listChecks.forEach((list, i) => {
    list.addEventListener("click", (e) => {
        if (templist) {
            templist.classList.remove("active");
        }
        templist = listChecks[i];
        listChecks[i].classList.add("active");
    });
});

// 조회 버튼 클릭시 숨겨진 모달 나오게한다

const showBtn = document.querySelector(".dropdown-trigger");
const monthModal = document.querySelector(".dropdown-menu");

const applicantWrapper = document.querySelector(".page-container");

showBtn.addEventListener("click", (e) => {
    e.stopPropagation();
    monthModal.style.display = "block";
});

document.addEventListener("click", () => {
    monthModal.style.display = "none";
});

// 모달의 n개월 클릭시 버튼의 내용 변경한다.
const monthModalList = document.querySelectorAll(".dropdown-menu ul li button");

monthModalList.forEach((list, i) => {
    list.addEventListener("click", (e) => {
        showBtn.textContent = list.textContent;
    });
});

// 필터 아이콘 클릭하면 필터모달이 나온다.
const filterBtn = document.querySelector(".btn-filter-toggle");
const filterModal = document.querySelector(".filter-modal");

filterBtn.addEventListener("click", (e) => {
    e.stopPropagation();
    filterModal.style.display = "block";
});

document.addEventListener("click", () => {
    filterModal.style.display = "none";
});

// 하,,,
const filterBtnList = document.querySelectorAll(".filter-option");
const addbox = document.querySelector(".selected-filters");
const modalResetBtn = document.querySelector(".btn-filter-reset");
const maleCheckbox = document.getElementById("gender-male");
const femaleCheckbox = document.getElementById("gender-female");

console.log(modalResetBtn);

filterBtnList.forEach((list, i) => {
    filterModal.addEventListener("click", (e) => {
        e.stopPropagation();
    });
    list.addEventListener("click", (e) => {
        const value = list.textContent;
        const spanId = `filter${i}`;
        const isActive = list.classList.toggle("active");

        const existSpan = document.getElementById(spanId);

        if (isActive && !existSpan) {
            const span = document.createElement("span");
            span.id = spanId;
            span.classList.add("selected-filter-tag");
            span.textContent = value;
            console.log(value);
            const btn = document.createElement("button");
            btn.type = "button";
            btn.className = "btnSelDel mtcBtnB devFilterItemDelBtn";
            btn.dataset.filterGroup = "1_" + (i + 1);

            const skip = document.createElement("span");
            skip.className = "blind";
            skip.textContent = "삭제";

            span.append(btn);
            btn.append(skip);

            addbox.append(span);
            return;
        }

        if (!isActive && existSpan) {
            existSpan.remove();
        }
    });

    addbox.addEventListener("click", (e) => {
        const delBtn = e.target.closest(".btnSelDel");
        if (!delBtn) return;

        const item = delBtn.closest(".selected-filter-tag");
        const index = item.id.replace("filter", "");

        filterBtnList[index].classList.remove("active");
        item.remove();
    });

    maleCheckbox.addEventListener("change", (e) => {
        const value = "남성";
        const spanId = `filterMale`;
        const isActive = maleCheckbox.classList.toggle("active");

        const existSpan = document.getElementById(spanId);

        if (maleCheckbox.checked) {
            const span = document.createElement("span");
            span.id = spanId;
            span.classList.add("selected-filter-tag");
            span.textContent = value;
            console.log(value);
            const btn = document.createElement("button");
            btn.type = "button";
            btn.className = "btnSelDel mtcBtnB devFilterItemDelBtn";
            btn.dataset.filterGroup = "1_" + (i + 1);

            const skip = document.createElement("span");
            skip.className = "blind";
            skip.textContent = "삭제";

            span.append(btn);
            btn.append(skip);

            addbox.append(span);
        }

        if (!maleCheckbox.checked || existSpan) {
            existSpan.remove();
        }
    });

    femaleCheckbox.addEventListener("change", (e) => {
        const value = "여성";
        const spanId = `filterFemale`;
        const isActive = femaleCheckbox.classList.toggle("active");

        const existSpan = document.getElementById(spanId);

        if (femaleCheckbox.checked) {
            const span = document.createElement("span");
            span.id = spanId;
            span.classList.add("selected-filter-tag");
            span.textContent = value;
            console.log(value);
            const btn = document.createElement("button");
            btn.type = "button";
            btn.className = "btnSelDel mtcBtnB devFilterItemDelBtn";
            btn.dataset.filterGroup = "1_" + (i + 1);

            const skip = document.createElement("span");
            skip.className = "blind";
            skip.textContent = "삭제";

            span.append(btn);
            btn.append(skip);

            addbox.append(span);
        }

        if (!femaleCheckbox.checked || existSpan) {
            existSpan.remove();
        }
    });

    modalResetBtn.addEventListener("click", () => {
        // 1. 필터 버튼 active 제거
        filterBtnList.forEach((btn) => {
            btn.classList.remove("active");
        });

        // 2. 선택된 필터 태그 전부 제거
        addbox.innerHTML = "";

        // 3. 성별 체크박스 초기화
        maleCheckbox.checked = false;
        femaleCheckbox.checked = false;

        maleCheckbox.classList.remove("active");
        femaleCheckbox.classList.remove("active");
    });
});

// 검색에서 초기화 버튼 누를 시 초기화
const filterResetBtn = document.querySelector(".btn-clear-filters");
const filterList = document.querySelectorAll(".filter-list__items");
const listdelete = document.querySelectorAll(".selected-filter-tag-bar");

filterResetBtn.addEventListener("click", (e) => {
    filterList.forEach((list) => list.remove());
});

// 승인 완료 모달 열고 닫기
const approvalBtn = document.querySelector(".btn-approve");
const rejectBtn = document.querySelector(".btn-reject");

const approvalModal = document.querySelector(".modal--approve");
const rejectModal = document.querySelector(".modal--reject");
const modal_Btn = document.querySelectorAll(".modal__confirm");

approvalBtn.addEventListener("click", (e) => {
    approvalModal.classList.add("active");
});

rejectBtn.addEventListener("click", (e) => {
    rejectModal.classList.add("active");
});

modal_Btn.forEach((Btn, i) => {
    Btn.addEventListener("click", (e) => {
        if (Btn.classList.contains("approve")) {
            approvalModal.classList.remove("active");
        } else if (Btn.classList.contains("reject")) {
            rejectModal.classList.remove("active");
        }
    });
});

// 체크 박스 클릭시 리스트에 모든 체크 박스 클릭
const checkAll = document.querySelector(".checkbox-all");
const checkItems = document.querySelectorAll(".checkbox-item");

console.log(checkAll);
console.log(checkItems);

checkAll.addEventListener("change", (e) => {
    if (checkAll.checked === true) {
        checkItems.forEach((check, i) => {
            check.checked = true;
        });
    }
    if (checkAll.checked === false) {
        checkItems.forEach((check, i) => {
            check.checked = false;
        });
    }
});

let checkedCount = 0;

checkItems.forEach((check) => {
    check.addEventListener("change", () => {
        checkedCount += check.checked ? 1 : -1;
        checkAll.checked = checkedCount === checkItems.length;
    });
});
