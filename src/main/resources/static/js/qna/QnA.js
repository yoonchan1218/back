// 글쓰기 눌렀을 때 툴팁
const writeButton = document.querySelector(
    ".select-tooltip.btn-question.qnaSpB",
);
const tooltipOpenDiv = document.querySelector(".navi-top-area.has-tooltip");

writeButton.addEventListener("click", (e) => {
    tooltipOpenDiv.classList.toggle("tooltip-open");
});

// 자동완성 기능
const inputFocus = document.querySelector(".jkSchInput.keywordSearch.devFocus");
const inputSearch = document.getElementById("AJAX_TS_Search");
const autocomplete = document.querySelector(
    ".keyword-search-area.autocomplete",
);

// input focus 시 부모에 'focus' 클래스 추가
inputSearch.addEventListener("focusin", () => {
    inputFocus.classList.add("focus");
});

// input blur 시 'focus' 클래스 제거
inputSearch.addEventListener("focusout", () => {
    inputFocus.classList.remove("focus");
});

// 입력값에 따라 autocomplete 표시/숨김
inputSearch.addEventListener("input", () => {
    if (inputSearch.value.trim() !== "") {
        autocomplete.style.display = "block";
    } else {
        autocomplete.style.display = "none";
    }
});

// 슬라이드 hover 효과
const slides = document.querySelectorAll(".recommendSwiper .swiper-slide");

slides.forEach((slide) => {
    slide.addEventListener("mouseenter", () => {
        slide.classList.add("hover");
    });

    slide.addEventListener("mouseleave", () => {
        slide.classList.remove("hover");
    });
});

// Swiper 슬라이더 초기화
const recommendSwiper = new Swiper(".recommendSwiper .swiper-container", {
    loop: true,
    autoplay: {
        delay: 2000,
        disableOnInteraction: false,
    },
    speed: 1200,
    slidesPerView: 3,
    spaceBetween: 10,
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
});

// 정렬 버튼 클릭 이벤트 (최신순, 인기순, 좋아요, 댓글순)
const sortButtons = document.querySelectorAll(".sort-list-btn.devBtnOrderType");

sortButtons.forEach((btn) => {
    btn.addEventListener("click", () => {
        const sortType = btn.dataset.ordertype;
        const url = new URL(location.href);
        url.searchParams.set("sort", sortType);
        url.searchParams.set("page", "1"); // 정렬 변경 시 1페이지로
        location.href = url.toString();
    });
});

// // 북마크 등록(로그인)
// const buttonBookMark = document.querySelector(
//     ".devQnaDetailBookmark.btnBookmark.qnaSpB",
// );
//
// const bookMarkLayer = document.querySelector(
//     ".book-mark-layer.tooltip-layer.qnaSpA",
// );
//
// if (buttonBookMark) {
//     buttonBookMark.addEventListener("click", (e) => {
//         if (!buttonBookMark.classList.contains("on")) {
//             if (bookMarkLayer) {
//                 bookMarkLayer.style.display = "block"; // 먼저 보이게
//                 bookMarkLayer.style.opacity = "1";
//                 setTimeout(() => {
//                     bookMarkLayer.style.opacity = "0";
//                     setTimeout(() => {
//                         bookMarkLayer.style.display = "none"; // 사라진 후 숨김
//                     }, 300); // transition 시간만큼 대기
//                 }, 975);
//             }
//         }
//         buttonBookMark.classList.toggle("on");
//     });
// }
// 신고 열리는 버튼들 (모든 버튼 선택)
const reportActiveButtons = document.querySelectorAll(
    ".icon-more-button.qnaSpB.devQnaListPopupMenuButton",
);
// 신고창 뜨는 신고버튼들
const reportButtons = document.querySelectorAll(
    ".view-more-layer.devQnaListPopupMenu",
);

// 신고창
const pressReportButton = document.querySelector(".mtuLyWrap.lyQnaReport");
// 삭제 확인창
const pressDeleteButton = document.querySelector(".mtuLyWrap.lyQnaDelete");
// 신고창 x버튼
const pressReportButtonClose = document.querySelector(
    ".butClose.mtuSpImg.devLyBtnClose",
);

// 신고창 text-area 문구
const reportFormBox = document.querySelector(
    ".qnaFormBx.qnaTxaBx.devTplSchPh span",
);
// 신고창 text-area
const reportTextArea = document.getElementById("lb_reason_8");
// 신고창 신고하기 버튼
const reportSubmitButton = document.querySelector(".btnReport.devBtnReportIns");
// 신고창 첫번째 이유(input radio)
const reportFirstReasonRadio = document.querySelectorAll(
    ".reportBx.radioCommWrap li input",
);

// 신고하기 눌렀을 때 알림띄우기(confirm 사용 시 확인,취소 뜸)
reportSubmitButton.addEventListener("click", (e) => {
    const reportSubmitMessage = confirm(
        "신고된 글은 운영자에게 전달됩니다. 신고하시겠습니까?",
    );
    if (reportSubmitMessage) {
        alert("신고 처리 완료되었습니다.");
        location.href = "/QnA.html";
    }
});

// 신고창 text-area 누를 시 문구 삭제
reportTextArea.addEventListener("click", (e) => {
    reportFormBox.style.display = "none";
    reportTextArea.focus();
});

// 신고창 text-area 바깥 누를시 다시 문구 띄우기
reportTextArea.addEventListener("blur", (e) => {
    if (reportTextArea.value.trim() === "") {
        reportFormBox.style.display = "block";
    }
});

// 모든 더보기 버튼에 이벤트 연결
reportActiveButtons.forEach((btn, index) => {
    const reportLayer = reportButtons[index];

    btn.addEventListener("click", (e) => {
        e.stopPropagation();

        // 다른 열린 메뉴들 닫기
        reportButtons.forEach((layer, i) => {
            if (i !== index) {
                layer.classList.remove("active");
            }
        });

        // 현재 메뉴 토글
        reportLayer.classList.toggle("active");
    });

    // 신고/삭제 버튼 클릭 이벤트
    reportLayer.addEventListener("click", (e) => {
        const clickedItem = e.target.closest(".view-more-item");
        if (!clickedItem) return;

        if (clickedItem.classList.contains("devSingo")) {
            // 신고 클릭
            if (clickedItem.dataset.loggedIn === "false") {
                location.href = "/main/log-in";
                return;
            }
            pressReportButton.style.display = "block";
            reportFirstReasonRadio.forEach((radio) => {
                if (radio.value === "1") {
                    radio.checked = true;
                }
            });
        } else if (clickedItem.classList.contains("devDelete")) {
            // 삭제 클릭
            const deleteConfirm = confirm("정말 삭제하시겠습니까?");
            if (deleteConfirm) {
                const qnaId = clickedItem.dataset.qstnNo;
                fetch("/qna/delete", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: "qnaId=" + qnaId
                })
                .then(res => res.json())
                .then(data => {
                    if (data.success) {
                        location.href = "/qna/list";
                    } else {
                        alert(data.message || "삭제에 실패했습니다.");
                    }
                })
                .catch(() => alert("삭제 중 오류가 발생했습니다."));
            }
        }

        reportLayer.classList.remove("active");
    });
});

// 바깥 클릭 시 메뉴 닫기
document.addEventListener("click", (e) => {
    if (!e.target.closest(".post-util-item")) {
        reportButtons.forEach((layer) => {
            layer.classList.remove("active");
        });
    }
});

// 신고창 닫기
pressReportButtonClose.addEventListener("click", (e) => {
    pressReportButton.style.display = "none";
});

const pressReportCancelButton = document.querySelector(
    ".btnCancel.bg_white.devLyBtnClose",
);
pressReportCancelButton.addEventListener("click", (e) => {
    pressReportButton.style.display = "none";
});

// 게시 시간 상대 표시
function timeForToday(datetime) {
    const today = new Date();
    const date = new Date(datetime.replace(" ", "T"));
    let gap = Math.floor((today.getTime() - date.getTime()) / 1000 / 60);
    if (gap < 1) return "방금 전";
    if (gap < 60) return `${gap}분 전`;
    gap = Math.floor(gap / 60);
    if (gap < 24) return `${gap}시간 전`;
    gap = Math.floor(gap / 24);
    if (gap < 31) return `${gap}일 전`;
    gap = Math.floor(gap / 31);
    if (gap < 12) return `${gap}개월 전`;
    gap = Math.floor(gap / 12);
    return `${gap}년 전`;
}

document.querySelectorAll(".devQnaCreatedTime").forEach(el => {
    el.textContent = timeForToday(el.dataset.created);
});
