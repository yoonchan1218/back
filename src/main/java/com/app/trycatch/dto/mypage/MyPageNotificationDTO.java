package com.app.trycatch.dto.mypage;

import com.app.trycatch.domain.mypage.MainNotificationVO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class MyPageNotificationDTO {
    private Long id;
    private Long memberId;
    private String notificationType;
    private String notificationTitle;
    private String notificationContent;
    private boolean notificationIsRead;
    private String notificationReceiveAt;
    private String createdDatetime;
    private String updatedDatetime;

    public MainNotificationVO toVO() {
        return MainNotificationVO.builder()
                .id(id)
                .memberId(memberId)
                .notificationType(notificationType)
                .notificationTitle(notificationTitle)
                .notificationContent(notificationContent)
                .notificationIsRead(notificationIsRead)
                .notificationReceiveAt(notificationReceiveAt)
                .createdDatetime(createdDatetime)
                .updatedDatetime(updatedDatetime)
                .build();
    }
}
