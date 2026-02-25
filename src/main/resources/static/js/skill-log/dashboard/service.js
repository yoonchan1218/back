const skillLogService = (() => {
    const getList = async (page, type, memberId, callback) => {
        page = page || 1;

        const response = await fetch(`/api/skill-log/my-list/${page}?type=${type}&memberId=${memberId}`)
        const skillLogWithPaging = await response.json();
        if(callback){
            callback(skillLogWithPaging);
        }
    }

    return {getList: getList};
})();