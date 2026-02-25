// 알림 유형 필터
const sortSel = document.getElementById("sortSel");
if (sortSel) {
    sortSel.addEventListener("change", () => {
        const type = sortSel.value;
        document.querySelectorAll("#js-alarmFrame .alarmList[data-type]").forEach((item) => {
            if (!type || item.dataset.type === type) {
                item.style.display = "";
            } else {
                item.style.display = "none";
            }
        });
    });
}
