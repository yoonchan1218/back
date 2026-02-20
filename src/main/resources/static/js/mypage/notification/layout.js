const notificationLayout = (() => {
    const showRead = (btn) => {
        const li = btn.closest("li");
        if (li) li.classList.add("read");
        btn.remove();
    }
    return {showRead};
})();
