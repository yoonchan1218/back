document.querySelectorAll(".btn-read-notification").forEach((btn) => {
    btn.addEventListener("click", async () => {
        const notificationId = btn.dataset.id;
        if (!notificationId) return;

        const success = await notificationService.update(notificationId);
        if (success) {
            notificationLayout.showRead(btn);
        }
    });
});
