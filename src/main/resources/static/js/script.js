document.addEventListener("DOMContentLoaded", function () {
    // 페이지의 현재 경로에 따라 내비게이션 탭 활성화
    const currentPath = window.location.pathname;
    if (currentPath === "/") {
        document.getElementById("nav-home").classList.add("active");
    } else if (currentPath === "/upcoming-coupons") {
        document.getElementById("nav-upcoming").classList.add("active");
    } else if (currentPath === "/past-coupons") {
        document.getElementById("nav-past").classList.add("active");
    } else if (currentPath === "/my-page") {
        document.getElementById("nav-my-page").classList.add("active");
    }

    // 모달 관련 이벤트 리스너 설정
    const couponElements = document.querySelectorAll('tr[data-bs-toggle="modal"]');
    couponElements.forEach(function (element) {
        element.addEventListener('click', function () {
            const couponName = this.getAttribute('data-name');
            const couponDescription = this.getAttribute('data-description');
            const createdDate = this.getAttribute('data-created');
            const expiredDate = this.getAttribute('data-expired');
            const couponStatus = this.getAttribute('data-status');

            // 모달 요소에 값을 설정
            updateModalContent(couponName, couponDescription, createdDate, expiredDate, couponStatus);
        });
    });

    // My Page 링크 클릭 이벤트 처리
    const myPageLink = document.getElementById("nav-my-page");
    myPageLink.addEventListener("click", function (event) {
        event.preventDefault();
        handleMyPageLink(this.getAttribute("href"));
    });

    // 쿠폰 요청 이벤트 리스너
    document.getElementById("issue").addEventListener("click", function () {
        issueCoupon(this.getAttribute('data-coupon-id'));
    });

    // 타이머 업데이트
    const timerElement = document.getElementById('timer');
    updateTimer(timerElement);
});

// 타이머 업데이트 함수
function updateTimer(timerElement) {
    const closedAt = new Date(timerElement.dataset.closedAt);

    function updateRemainingTime() {
        const now = new Date();
        const timeDifference = closedAt - now;

        if (timeDifference > 0) {
            const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
            const hours = Math.floor((timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            const minutes = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);

            document.getElementById('days').textContent = days;
            document.getElementById('hours').textContent = hours.toString().padStart(2, '0');
            document.getElementById('minutes').textContent = minutes.toString().padStart(2, '0');
            document.getElementById('seconds').textContent = seconds.toString().padStart(2, '0');
        } else {
            // 시간이 지났을 때 처리
            document.getElementById('days').textContent = '0';
            document.getElementById('hours').textContent = '00';
            document.getElementById('minutes').textContent = '00';
            document.getElementById('seconds').textContent = '00';
        }
    }

    setInterval(updateRemainingTime, 1000); // 매 초마다 업데이트
    updateRemainingTime(); // 초기 실행
}


// 모달 내용 업데이트 함수
function updateModalContent(name, description, created, expired, status) {
    document.getElementById('modalCouponName').innerText = name;
    document.getElementById('modalCouponDescription').innerText = description;
    document.getElementById('modalCouponCreated').innerText = created;
    document.getElementById('modalCouponExpired').innerText = expired;
    updateStatusBadge(status);
}

// My Page 링크 핸들링 함수
function handleMyPageLink(url) {
    fetch(url).then(response => {
        if (response.ok) {
            window.location.href = url;
        } else if (response.status === 401) {
            window.location.href = "/login";
        } else {
            throw new Error('페이지를 불러오는데 실패했습니다.');
        }
    }).catch(error => {
        console.error('Error:', error);
    });
}

// 쿠폰 발급 요청 함수
function issueCoupon(couponId) {
    const formData = {couponId: couponId};
    fetch("/user/coupon", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: JSON.stringify(formData),
    }).then(handleCouponResponse)
        .catch((error) => {
            console.error("Error: ", error.message);
            alert("쿠폰 발급에 실패했습니다: " + error.message);
        });
}


// 쿠폰 응답 처리 함수
function handleCouponResponse(response) {
    if (response.ok) {
        return response.json().then(data => {
            alert("쿠폰 발급에 성공했습니다.");
            location.reload();
        });
    } else if (response.status === 401) {
        window.location = "/login";
        return Promise.reject(new Error("로그인이 필요합니다."));
    } else {
        return response.json().then(errorData => {
            throw new Error(errorData.error);
        });
    }
}


// 상태 배지 업데이트
function updateStatusBadge(status) {
    const statusBadge = document.getElementById('modalCouponStatus');
    statusBadge.innerText = status;
    switch (status) {
        case '만료':
            statusBadge.className = 'badge bg-danger';
            break;
        case '만료 임박':
            statusBadge.className = 'badge bg-warning';
            break;
        case '유효':
            statusBadge.className = 'badge bg-success';
            break;
    }
}

window.onload = function () {
    let maxHeight = 0;
    const cardBodies = document.querySelectorAll('.card-body');
    cardBodies.forEach(function (card) {
        if (card.offsetHeight > maxHeight) {
            maxHeight = card.offsetHeight;
        }
    });
    cardBodies.forEach(function (card) {
        card.style.height = maxHeight + 'px';
    });
};

