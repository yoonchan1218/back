const mainService = (() => {
    const insert = async (file) => {
        const formData = new FormData();
        formData.append("file", file);
        const response = await fetch("/mypage/profile-image", {
            method: "POST",
            body: formData
        });
        return await response.text();
    };

    return {insert};
})();
