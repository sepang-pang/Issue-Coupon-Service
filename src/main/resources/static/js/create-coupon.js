document.addEventListener("DOMContentLoaded", function () {
    console.log("DOMContentLoaded event triggered");
    // 페이지 로드 시 관리자 권한 확인
    fetch('/user/check-role')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log("Received data:", data);
            if (!data.isAdmin) {
                alert('관리자 권한이 필요합니다.');
                window.location.href = '/';
            }
        })
        .catch(error => {
            console.error('Error fetching admin status:', error);
            alert('권한 확인 중 오류가 발생했습니다.');
            window.location.href = '/';
        });
});

document.getElementById("back").addEventListener("click", function () {
    window.location.href = "/";
});
document.getElementById("create").addEventListener("click", function () {

    const formData = new FormData();

    const param = {
        couponName: document.getElementById("coupon-name").value,
        couponContent: document.getElementById("coupon-content").value,
        totalQuantity: document.getElementById("total-quantity").value,
        openAt: document.getElementById("start-date").value,
        closedAt: document.getElementById("closed-date").value,
        expiredAt: document.getElementById("end-date").value
    };

    const openAtDate = new Date(formData.openAt);
    const closedAtDate = new Date(formData.closedAt);
    const expiredAtDate = new Date(formData.expiredAt);

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

    formData.append('param', new Blob([JSON.stringify(param)], {type: 'application/json'}));
    formData.append('couponImage', document.getElementById("coupon-image").files[0]);

    const requestOptions = {
        method: 'POST',
        body: formData

    };
    console.log("Sending data: ", formData);

    fetch("/admin/coupon", requestOptions)
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
            window.location.href = "/";
        })
        .catch((error) => {
            console.error("Error: ", error);
            alert("쿠폰 생성에 실패했습니다.");
        });
});