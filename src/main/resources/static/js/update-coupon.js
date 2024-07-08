document.getElementById("back").addEventListener("click", function () {
    window.history.back();
});

document.getElementById("update").addEventListener("click", function () {
    const couponId = document.getElementById('update').getAttribute('data-coupon-id');

    const formData = new FormData();

    const updateData = {
        couponName: document.getElementById("coupon-name").value,
        couponContent: document.getElementById("coupon-content").value,
        totalQuantity: document.getElementById("total-quantity").value,
        remainQuantity: document.getElementById("remain-quantity").value,
        openAt: document.getElementById("start-date").value,
        closedAt: document.getElementById("closed-date").value,
        expiredAt: document.getElementById("end-date").value
    };

    const openAtDate = new Date(updateData.openAt);
    const closedAtDate = new Date(updateData.closedAt);
    const expiredAtDate = new Date(updateData.expiredAt);

    if (openAtDate >= closedAtDate) {
        alert("쿠폰 발급 시작일은 쿠폰 발급 마감일보다 이전이어야 합니다.");
        return;
    }
    if (openAtDate >= expiredAtDate) {
        alert("쿠폰 발급 시작일은 쿠폰 만료일보다 이전이어야 합니다.");
        return;
    }
    if (closedAtDate >= expiredAtDate) {
        alert("쿠폰 발급 마감일은 쿠폰 만료일보다 이전이어야 합니다.");
        return;
    }

    formData.append('param', new Blob([JSON.stringify(updateData)], {type: 'application/json'}));

    const couponImage = document.getElementById("coupon-image").files[0];
    if (couponImage) {
        formData.append("couponImage", couponImage);
    }

    const requestOptions = {
        method: 'PATCH',
        body: formData

    };

    fetch(`/admin/coupon/${couponId}`, requestOptions)
        .then(response => {
            if (response.ok) {
                return response.json().catch(() => ({}));  // 응답이 비어있으면 빈 객체 반환
            } else {
                return response.json().then(errorData => {
                    throw new Error(errorData.error || "Unknown error");
                });
            }
        })
        .then(data => {
            console.log("Success: ", data);
            window.location.href = "/admin";
        })
        .catch((error) => {
            console.error("Error: ", error);
            alert("쿠폰 수정에 실패했습니다.");
        });
});