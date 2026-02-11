// 비밀번호 도움말 버튼
const btnHelp = document.querySelector(".btnHelp");
const passwordHelp = document.querySelector(".row.mbr_passwd");
const passwordHelpChat = document.querySelector(".lyHelp");

btnHelp.addEventListener("click", (e) => {
    passwordHelp.style.zIndex =
        passwordHelp.style.zIndex === "9100" ? "auto" : "9100";
    passwordHelpChat.style.display =
        passwordHelpChat.style.display === "none" ? "block" : "none";
});

// 비밀번호 표시
const showPasswordButton = document.querySelector(
    ".mbrBtnAuth.dev-password-dp",
);
const showPassword = document.getElementById("M_Pwd");

showPasswordButton.addEventListener("click", (e) => {
    showPassword.type = showPassword.type === "password" ? "text" : "password";
    showPasswordButton.classList.toggle("selected");
});

// 입력정보 경고
const fields = [
    {
        input: "idcheck",
        selector: ".mbr_id",
        notice: "notice_msg_id",
        regexp: /^[a-z0-9]{4,16}$/,
        errorMsg: "4~16자의 영문 소문자, 숫자만 사용 가능합니다.",
    },
    {
        input: "M_Pwd",
        selector: ".mbr_passwd",
        notice: "notice_msg_pwd",
        regexp: /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,16}$/,
        errorMsg: "8~16자의 영문, 숫자, 특수문자를 모두 포함해야 합니다.",
    },
    {
        input: "M_Name",
        selector: ".mbr_name",
        notice: "notice_msg_name",
        regexp: /^[가-힣a-zA-Z\s]{2,12}$/,
        errorMsg: "2~12자의 한글, 영문만 입력 가능합니다.",
    },
    {
        input: "Born",
        selector: ".mbr_age",
        notice: "notice_msg_age",
        regexp: /^(19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])$/,
        errorMsg: "생년월일 8자리를 입력해주세요. (예: 20000131)",
    },
    {
        input: "M_Email",
        selector: ".mbr_email",
        notice: "notice_msg_mail",
        regexp: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
        errorMsg: "올바른 이메일 형식을 입력해주세요.",
    },
    {
        input: "M_Phone",
        selector: ".mbr_phone",
        notice: "notice_msg_phone",
        regexp: /^01[016789]-?\d{3,4}-?\d{4}$/,
        errorMsg: "올바른 휴대폰 번호를 입력해주세요.",
    },
    {
        input: "Certify_Num",
        selector: ".authentication_check",
        notice: "notice_msg_certify",
        regexp: /^\d{6}$/,
        errorMsg: "인증번호 6자리를 입력해주세요.",
        skip: true,
    },
];

// 포커스 / 블러 이벤트
fields.forEach(({ input, selector, notice, regexp, errorMsg, skip }) => {
    const inputEl = document.getElementById(input);
    const col1 = document.querySelector(`${selector} .col_1`);
    const col2 = document.querySelector(`${selector} .col_2`);
    const noticeEl = document.getElementById(notice);

    if (!inputEl || !col1 || !col2) return;

    // 포커스
    inputEl.addEventListener("focus", () => {
        col1.classList.add("focus");
        col2.classList.add("focus");
    });

    // 블러 (포커스 해제)
    inputEl.addEventListener("blur", () => {
        // 값 없으면 포커스 스타일 제거
        if (!inputEl.value) {
            col1.classList.remove("focus");
            col2.classList.remove("focus");
        }

        // 인증번호는 blur 검증 제외
        if (skip) return;

        // 정규식 검증
        if (regexp && noticeEl) {
            if (!inputEl.value) {
                noticeEl.innerHTML = "필수 입력 항목입니다.";
                noticeEl.classList.add("failure");
                noticeEl.style.display = "block";
            } else if (!regexp.test(inputEl.value)) {
                noticeEl.innerHTML = errorMsg;
                noticeEl.classList.add("failure");
                noticeEl.style.display = "block";
            } else {
                noticeEl.innerHTML = "";
                noticeEl.classList.remove("failure");
                noticeEl.style.display = "none";
            }
        }
    });
});

// 가입하기 버튼
const mbrBtnRegist = document.querySelector(".mbrBtnRegist");

mbrBtnRegist.addEventListener("click", (e) => {
    e.preventDefault();

    let isValid = true;

    // 입력 필드 검증 (인증번호 제외)
    fields.forEach(({ input, notice, regexp, errorMsg, skip }) => {
        if (skip) return;

        const inputEl = document.getElementById(input);
        const noticeEl = document.getElementById(notice);

        if (!inputEl || !noticeEl) return;

        if (!inputEl.value) {
            noticeEl.innerHTML = "필수 입력 항목입니다.";
            noticeEl.classList.add("failure");
            noticeEl.style.display = "block";
            isValid = false;
        } else if (!regexp.test(inputEl.value)) {
            noticeEl.innerHTML = errorMsg;
            noticeEl.classList.add("failure");
            noticeEl.style.display = "block";
            isValid = false;
        } else {
            noticeEl.innerHTML = "";
            noticeEl.classList.remove("failure");
            noticeEl.style.display = "none";
        }
    });

    // 성별 검증
    const genderChecked = document.querySelector(
        'input[name="memberGender"]:checked',
    );
    const genderNotice = document.getElementById("notice_msg_gender");
    if (!genderChecked) {
        genderNotice.innerHTML = "성별을 선택해주세요.";
        genderNotice.classList.add("failure");
        genderNotice.style.display = "block";
        isValid = false;
    } else {
        genderNotice.innerHTML = "";
        genderNotice.classList.remove("failure");
        genderNotice.style.display = "none";
    }

    // 필수 약관 검증
    const ageAgree = document.getElementById("lb_chk_age");
    if (!ageAgree.checked) {
        isValid = false;
    }

    // 검증 결과
    if (!isValid) {
        alert("필수 항목을 확인해주세요.");
    } else {
        alert("환영합니다!");
        document.getElementById("frm").submit();
    }
});



// 체크박스 (input 요소에 이벤트 걸기)
const inputChkAll = document.getElementById("lb_chk_all");
const inputChkAge = document.getElementById("lb_chk_age");
const inputChkPrivacy = document.getElementById("lb_chk_privacyOptional");
const inputChkAdinfo = document.getElementById("lb_chk_adinfo");

const chkAll = document.querySelector(".chk_all");
const chkAge = document.querySelector(".chk_age");
const chkPrivacyOptional = document.querySelector(".chk_privacyOptional");
const chkAdinfo = document.querySelector(".chk_adinfo");

const checkBoxInputs = [inputChkAge, inputChkPrivacy, inputChkAdinfo];
const checkBoxLabels = [chkAge, chkPrivacyOptional, chkAdinfo];

// 전체 선택
inputChkAll.addEventListener("change", () => {
    const isChecked = inputChkAll.checked;

    chkAll.classList.toggle("on", isChecked);

    checkBoxInputs.forEach((input, idx) => {
        input.checked = isChecked;
        checkBoxLabels[idx].classList.toggle("on", isChecked);
    });
});

// 개별 선택
checkBoxInputs.forEach((input, idx) => {
    input.addEventListener("change", () => {
        checkBoxLabels[idx].classList.toggle("on", input.checked);

        const allChecked = checkBoxInputs.every((cb) => cb.checked);

        inputChkAll.checked = allChecked;
        chkAll.classList.toggle("on", allChecked);
    });
});

// 내용보기 클릭
const mbrBtnPolicies = document.querySelectorAll(".mbrBtnPolicy");

mbrBtnPolicies.forEach((btn) => {
    btn.addEventListener("click", (e) => {
        e.preventDefault(); // 링크 기본 동작 방지

        btn.classList.toggle("on");

        // 버튼의 부모 요소에서 해당하는 policyTplBox 찾기
        const policyTplBox = btn.closest(".row").querySelector(".policyTplBox");

        if (policyTplBox) {
            policyTplBox.style.display =
                policyTplBox.style.display === "block" ? "none" : "block";
        }
    });
});

// 성별 체크시 문자 제거
const boy = document.getElementById("boy");
const girl = document.getElementById("girl");
const noticeMsgGender = document.getElementById("notice_msg_gender");
const gender = [boy, girl];

gender.forEach((gender) => {
    gender.addEventListener("click", (e) => {
        if (boy.checked || girl.checked) {
            noticeMsgGender.style.display = "none";
        }
    });
});
