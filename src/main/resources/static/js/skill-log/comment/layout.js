const commentLayout = (() => {
    const showCommentList = ({comments, criteria}, memberId) => {
        const commentContainer = document.getElementById("answerArea");
        const pageWrap = document.querySelector(".tplPagination.newVer");
        const pageList = document.createElement("ul");
        let text = ``;

        commentContainer.innerHTML = "";
        pageWrap.innerHTML = "";

        comments.forEach((comment) => {
            const li = document.createElement("li");
            const condition = memberId === comment.memberId;
            text += `
                <!-- 댓글 -->
                <!-- [Dev] 내 답변일 경우 contSec에 클래스 myCmt 추가, cellBx 버튼: 수정/삭제만 노출 -->
                <div class="contSec devContSection ${condition && "myCmt"}" style="display: block">
<!--                    프로필 -->
                    <div class="infoBx">
                        <a href="/User/Qstn/MainProfile?Target=29448138" class="my-profile" target="_blank">
                            <span class="proThumb">
                                <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/m/ver_2/user/qna/profile_thumb/random_5.jpg" alt="프로필 이미지">
                            </span>
                            <span class="nickname">${commet.memberName}</span>
                        </a>
                        <span class="lvIcon">Lv ${comment.individualMemberLevel}</span>
                    </div>
<!--                    댓글 내용 -->
                    <p class="cont">${comment.skillLogCommentContent}</p>
                    `;

            // 첨부 파일
            if(comment.fileName) {
                text += `
                    <div class="attach-wrap">
                        <div class="attach-emoticon">
                            <img src="/api/files/display?filePath=${comment.filePath}&fileName=${comment.fileName}\" alt="">
                        </div>
                    </div>
                `;
            }

            text += `
                    <div class="cellBx">
<!--                        작성 시간-->
                        <span class="cell devAnswerDate">${comment.createdDatetime}</span>`;

            // 작성자 본인이라면 수정/삭제 버튼
            if(condition) {
                text += `
                            <span class="cell">
                                <button type="button" class="btnEdit devAnswerEditButton ${comment.id}">수정</button>
                            </span>
                            <span class="cell">
                                <button type="button" class="btnDelete devAnswerDeleteButton ${comment.id}">삭제</button>
                            </span>`;
            }

            text += `
                    </div>
                    <div class="btnBx devComtRoot ${comment.id}">
                        <!-- 댓글, 좋아요 버튼 클릭시 클래스 active 추가 -->
                        <button type="button" class="btnCmt devBtnComtList">
                            댓글 <em>2</em>
                        </button>
                        <button type="button" class="btnHeart qnaSpB devBtnAnswerLike">
                            0
                        </button>
                    </div>
                </div>`;
            // 댓글

            // 내 댓글 수정
            if(condition) {
                text += `
                    <!-- [Dev] 내 답글 수정 영역 -->
                    <div class="contSec modify-answer" style="display: none">
                        <div class="writeBoxWrap cmtWrite case">
                            <form id="myComtPut" action="/api/skill-log/comments/${comment.id}" oncopy="return false;" oncut="return false;" onpaste="return false;">
                                <fieldset>
                                    <legend>
                                        후배에게 답변하기
                                        입력
                                    </legend>
                                    <div class="uiPlaceholder">
                                        <span class="ph ph_1" style="display: block;">
                                            솔직하고 따뜻한 답변을 남겨주세요.
                                        </span>
                                        <span class="ph ph_2" style="display: none;">
                                            솔직하고 따뜻한 답변을 남겨주세요.
                                            <br>*휴대폰번호, 메일주소, 카카오톡 ID 등 개인정보가 포함된 내용은 비노출 처리 될 수 있습니다.
                                        </span>
                                        <textarea name="skillLogCommentContent" maxlength="1000" title="답변쓰기"></textarea>
                                    </div>
                                    <div class="btnWrap">
                                        <div class="answer-util-wrap">
                                            <div class="answer-util-item">
                                                <button type="button" class="icon-emoticon qnaSpB">
                                                    이모티콘
                                                </button>
                                            </div>
                                            <div class="answer-util-item">
                                                <label>
                                                    <input type="file" style="display: none;" class="reply-file">
                                                    <button type="button" class="button icon-photo qnaSpB">
                                                        사진
                                                    </button>
                                                </label>
                                            </div>
                                        </div>
                                        <span class="byte"><b id="count">0</b> / 1,000</span>
                                        <button type="button" id="btnSubmit" class="btnSbm devAnswerEditSubmitButton ${comment.id}">
                                            등록
                                        </button>
                                        <div class="layer-box-wrap emotion-layer">
                                            <p class="layer-header">
                                                이모티콘
                                            </p>
                                            <ul class="emotion-area">
                                                <!-- [dev] 이모티콘 총 36개 -->
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_1.png" data-idx="1" alt="">
                                                    </button>
                                                </li>
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_2.png" data-idx="2" alt="">
                                                    </button>
                                                </li>
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_3.png" data-idx="3" alt="">
                                                    </button>
                                                </li>
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_4.png" data-idx="4" alt="">
                                                    </button>
                                                </li>
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_5.png" data-idx="5" alt="">
                                                    </button>
                                                </li>
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_6.png" data-idx="6" alt="">
                                                    </button>
                                                </li>
                                            </ul>
                                            <button type="button" class="btn-layer-close qnaSpB">
                                                <span class="blind">닫기</span>
                                            </button>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                            <button type="button" class="btnModifyCancel qnaSpB">
                                <span class="blind">수정 취소</span>
                            </button>
                        </div>
                    </div>
                    <!-- // 내 답글 수정 영역 -->
                    <div class="commentSec" style="display: none;">
                        <div class="cmtArea">
                            <ul class="cmtList replyWrap">
<!--                                대댓글 -->
                                <!-- [Dev] 내 댓글일 경우 contSec에 클래스 myCmt 추가, cellBx 버튼: 삭제만 노출 -->
                            </ul>

<!--                            대댓글 작성 -->
                            <div class="writeBoxWrap cmtWrite qnaSpB dev-ComtEditor case">
                                <form id="" action="/api/skill-log/comments/${comment.id}" method="put" oncopy="return false;" oncut="return false;" onpaste="return false;">
                                    <fieldset>
                                        <legend>
                                            답변하기 입력
                                        </legend>
                                        <div class="uiPlaceholder">
                                            <span class="ph ph_1" style="
                                                    display: block;
                                                ">댓글을
                                                입력해주세요.</span>
                                            <span class="ph ph_2" style="
                                                    display: none;
                                                ">
                                                개인정보를
                                                공유 및
                                                요청하거나,
                                                명예 훼손,
                                                무단 광고,
                                                불법 정보
                                                유포 시 이에
                                                대한
                                                민형사상
                                                책임은
                                                작성자에게<br>
                                                있습니다.
                                                부적절한
                                                댓글은
                                                비노출 또는
                                                서비스 이용
                                                정지 사유가
                                                될 수
                                                있습니다.
                                            </span>

                                            <textarea name="skillLogCommentContent" maxlength="1000" class="devComtWrite" title="답변쓰기"></textarea>
                                        </div>
                                        <div class="btnWrap">
                                            <div class="answer-util-wrap">
                                                <div class="answer-util-item">
                                                    <button type="button" class="icon-emoticon qnaSpB">
                                                        이모티콘
                                                    </button>
                                                </div>
                                                <div class="answer-util-item">
                                                    <label>
                                                        <input type="file" style="
                                                                display: none;
                                                            " class="reply-file">
                                                        <button type="button" class="button icon-photo qnaSpB">
                                                            사진
                                                        </button>
                                                    </label>
                                                </div>
                                            </div>
                                            <span class="byte"><b id="count">0</b>
                                                /
                                                1,000</span>
                                            <button type="button" id="btnSubmit" class="btnSbm devBtnComtWrite">
                                                등록
                                            </button>
                                            <div class="layer-box-wrap emotion-layer">
                                                <p class="layer-header">
                                                    이모티콘
                                                </p>
                                                <ul class="emotion-area">
                                                    <li class="emotion-item">
                                                        <button type="button" class="emotion-button">
                                                            <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_1.png" data-idx="1" alt="">
                                                        </button>
                                                    </li>
                                                    <li class="emotion-item">
                                                        <button type="button" class="emotion-button">
                                                            <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_2.png" data-idx="2" alt="">
                                                        </button>
                                                    </li>
                                                    <li class="emotion-item">
                                                        <button type="button" class="emotion-button">
                                                            <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_3.png" data-idx="3" alt="">
                                                        </button>
                                                    </li>
                                                    <li class="emotion-item">
                                                        <button type="button" class="emotion-button">
                                                            <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_4.png" data-idx="4" alt="">
                                                        </button>
                                                    </li>
                                                    <li class="emotion-item">
                                                        <button type="button" class="emotion-button">
                                                            <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_5.png" data-idx="5" alt="">
                                                        </button>
                                                    </li>
                                                    <li class="emotion-item">
                                                        <button type="button" class="emotion-button">
                                                            <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_6.png" data-idx="6" alt="">
                                                        </button>
                                                    </li>
                                                </ul>
                                                <button type="button" class="btn-layer-close qnaSpB">
                                                    <span class="blind">닫기</span>
                                                </button>
                                            </div>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                        <button type="button" class="btnCmtClose qnaSpA qnaBtnClose">
                            댓글접기
                        </button>
                    </div>
                `;
            }



            li.innerHTML = text;
            commentContainer.appendChild(li);
        });
        // ##########################################################################
        if(criteria.startPage > 1){
            const previousButton = document.createElement("p");
            previousButton.innerHTML = `
                <a href="${criteria.startPage - 1}" class="tplBtn btnPgn btnPgnPrev paging">
                    <span class="blind">이전</span>
                    <i class="ico"></i>
                </a>
            `;
            pageWrap.appendChild(previousButton);
        }

        text = ``;
        for(let i = criteria.startPage; i <= criteria.endPage; i++){
            if(criteria.page === i){
                text += `
                    <li>
                        <span class="now">${i}</span>
                    </li>
                `;
                continue;
            }
            text += `
                <li>
                    <a href="${i}" class="paging">${i}</a>
                </li>
            `;
        }
        pageList.innerHTML = text;
        pageWrap.appendChild(pageList);

        if(criteria.endPage !== criteria.realEnd) {
            const nextButton = document.createElement("p");
            nextButton.innerHTML = `
                <a href="${criteria.endPage + 1}" class="tplBtn btnPgnNext paging">
                    <span class="blind">다음</span>
                    <i class="ico"></i>
                </a>
            `;
            pageWrap.appendChild(nextButton);
        }

        commentContainer.innerHTML = text;
    }

    const showNestedCommentList = ({comments, criteria}, memberId) => {
        let text = `
        <div class="commentSec" style="display: block;">
            <div class="cmtArea">
                <ul class="cmtList replyWrap">`;

        comments.forEach((comment) => {
            // 대댓글 내용
            text = `
                <li class="devCmtWrap ${comment.memberId === memberId && 'myCmt'}" id="parentCommentId${comment.id}">
                    <div class="devComtSection">
                        <div class="infoBx">
                            <i class="icoCmt qnaSpB">댓글</i>
                            <a href="/User/Qstn/MainProfile?Target=50176432" class="my-profile" target="_blank">
                                <span class="proThumb">
                                    <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/m/ver_2/user/qna/profile_thumb/random_13.jpg" alt="프로필 이미지" 
                                        onerror="this.src = 'https://cdn-assets.jobkorea.co.kr/images/jk-pc/m/ver_2/user/qna/profile_thumb/random_default.jpg'"
                                    >
                                </span>
                                <span class="nickname">${comment.memberName}</span>
                            </a>
                            <span class="lvIcon">Lv ${comment.individualMemberLevel}</span>
                        </div>
                        <p class="cont">${comment.skillLogCommentContent}</p>
                        <div class="cellBx">
                            <span class="cell devComtDate">${comment.createdDatetime}</span>
                            <span class="cell">
                                <button type="button" class="btnReport devBtnReport ${comment.id}">신고</button>
                            </span>
                        </div>
                    </div>`;
            //
            // 내 대댓글 수정
            if(comment.memberId === memberId){
                text += `
                    <div class="modify-comt" style="display: none;">
                        <div class="writeBoxWrap cmtWrite qnaSpB modify-comt" style="display: block;">
                            <form action="/api/skill-log/comments/${comment.id}" method="put">
                                <fieldset>
                                    <legend>
                                        댓글하기
                                        입력
                                    </legend>
                                    <div class="uiPlaceholder focus">
                                        <span class="ph ph_2" style="display: none;">
                                            댓글을 입력해주세요.<br>*휴대폰번호, 메일주소, 카카오톡 ID 등 개인정보가 포함된 내용은 비노출 처리될 수 있습니다.
                                        </span>
                                        <textarea name="Cntnt" maxlength="1000" title="답변쓰기" value=""></textarea>
                                    </div>
                                    <div class="btnWrap">
                                        <div class="answer-util-wrap">
                                            <div class="answer-util-item">
                                                <button type="button" class="icon-emoticon qnaSpB on">
                                                    이모티콘
                                                </button>
                                            </div>
                                            <label>
                                                <input type="file" style="
                                                        display: none;
                                                    " class="reply-file">
                                                <button type="button" class="button icon-photo qnaSpB">
                                                    사진
                                                </button>
                                            </label>
                                        </div>
                                        <span class="byte"><b id="count">0</b>/1,000</span>
                                        <button type="button" id="btnSubmit" class="btnSbm devComtEditSubmitButton" data-comtno="94628">
                                            등록
                                        </button>
                                        <div class="layer-box-wrap emotion-layer open">
                                            <p class="layer-header">
                                                이모티콘
                                            </p>
                                            <ul class="emotion-area">
                                                <!-- [dev] 이모티콘 총 36개 -->
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_1.png" data-idx="1" alt="">
                                                    </button>
                                                </li>
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_2.png" data-idx="2" alt="">
                                                    </button>
                                                </li>
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_3.png" data-idx="3" alt="">
                                                    </button>
                                                </li>
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_4.png" data-idx="4" alt="">
                                                    </button>
                                                </li>
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_5.png" data-idx="5" alt="">
                                                    </button>
                                                </li>
                                                <li class="emotion-item">
                                                    <button type="button" class="emotion-button">
                                                        <img src="https://cdn-assets.jobkorea.co.kr/images/jk-pc/user/qna/emoticon/emoticon_6.png" data-idx="6" alt="">
                                                    </button>
                                                </li>
                                            </ul>
                                            <button type="button" class="btn-layer-close qnaSpB">
                                                <span class="blind">닫기</span>
                                            </button>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                            <button type="button" class="close-button qnaSpB btnComtModifyCancel">
                                닫기
                            </button>
                        </div>
                    </div>
                    `;
            }
        });

    }


    return {showCommentList: showCommentList}
})();
















