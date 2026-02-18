package com.app.trycatch.dto.mypage;

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
}
