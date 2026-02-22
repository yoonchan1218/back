const notificationService = (() => {
    const update = async (notificationId, callback) => {
        const response = await fetch("/mypage/notification/read?notificationId=" + notificationId, {
            method: "POST",
        });
        const result = await response.json();
        if (callback) callback(result);
    };
    return {update};
})();
