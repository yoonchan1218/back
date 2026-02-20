const notificationService = (() => {
    const update = async (notificationId) => {
        const response = await fetch("/mypage/notification/read?notificationId=" + notificationId, {
            method: "POST",
        });
        return await response.json();
    }
    return {update};
})();
