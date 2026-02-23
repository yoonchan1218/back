const commentService = (() => {

    // 추가
    const write = async (formData) => {
        await fetch("/api/skill-log/comments/write", {
            method: "POST",
            body: formData
        });
    }

    // 댓글 목록
    const getList = async (page, skillLogId, memberId, callback) => {
        const response = await fetch(`/api/skill-log/comments/comment-list/${page}?skillLogId=${skillLogId}`);
        const comments = await response.json();
        if(callback){
            callback(comments, memberId);
        }
    }
    // 대댓글 목록
    const getNestedList = async (page, skillLogId, commentId, memberId, callback) => {
        const response = await fetch(`/api/skill-log/comments/nested-comment-list/${page}?skillLogId=${skillLogId}&commentId=${commentId}`);
        const comments = await response.json();
        if(callback){
            callback(comments, memberId);
        }
    }

    // 수정
    const update = async (reply) => {
        await fetch(`/api/replies/${reply.id}`, {
            method: "PUT",
            body: JSON.stringify(reply),
            headers: {
                "Content-Type": "application/json"
            }
        })
    }

    // 삭제
    const remove = async (id) => {
        await fetch(`/api/replies/${id}`, {
            method: "DELETE"
        });
    }

    return {write: write, getList: getList, update: update, remove: remove};
})();












