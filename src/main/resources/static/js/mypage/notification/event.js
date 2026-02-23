document.querySelectorAll(".btn-read-notification").forEach((btn) => {
    btn.addEventListener("click", () => {
        const notificationId = btn.dataset.id;
        if (!notificationId) return;

        notificationService.update(notificationId, (success) => {
            if (success) {
                notificationLayout.showRead(btn);
            }
        });
    });
});
