const experienceService = (() => {
    const cancelApply = async (applyId, callback) => {
        const response = await fetch("/mypage/experience/cancel?applyId=" + applyId, {
            method: "POST",
        });
        const result = await response.json();
        if (callback) callback(result);
    };

    return {cancelApply};
})();
