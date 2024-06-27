document.addEventListener("DOMContentLoaded", function () {
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
});

document.addEventListener("DOMContentLoaded", function() {
    const myPageLink = document.getElementById("nav-my-page");

    myPageLink.addEventListener("click", function(event) {
        event.preventDefault();
        const url = this.getAttribute("href");

        fetch(url)
            .then(response => {
                if (response.ok) {
                    window.location.href = url;
                } else if (response.status === 401) {
                    window.location.href = "/login";
                } else {
                    throw new Error('페이지를 불러오는데 실패했습니다');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
});


document.getElementById("issue").addEventListener("click", function () {
    const formData = {
        couponId: this.getAttribute('data-coupon-id')
    };

    fetch("/user/coupon", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData),
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            else if (response.status === 401) {
                window.location = "/login"
                return Promise.reject(new Error("로그인이 필요한 서비스입니다."));
            }
            else {
                return response.json().then(errorData => {
                    throw new Error(errorData.error);
                });
            }
        })
        .then(data => {
            console.log("Success: ", data);
            alert("쿠폰 발급에 성공했습니다.");
            location.reload();
        })
        .catch((error) => {
            console.error("Error: ", error.message);
            alert("쿠폰 발급에 실패했습니다: " + error.message);
        });
});


// == 카드 높이 조정 스크립트 == //
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

// == 페이징 처리 스크립트 == //
document.addEventListener("DOMContentLoaded", function () {
    const itemsPerPage = 9;
    const pagesPerGroup = 10;
    const container = document.querySelector('.row');
    const allCards = container.querySelectorAll('.col');
    const totalItems = allCards.length;
    const pageCount = Math.ceil(totalItems / itemsPerPage);
    let currentGroup = 1;
    let currentPage = 1;

    function setupPagination() {
        const paginationContainer = document.getElementById('pagination');
        paginationContainer.innerHTML = '';

        const totalGroups = Math.ceil(pageCount / pagesPerGroup);
        const groupStart = (currentGroup - 1) * pagesPerGroup + 1;
        const groupEnd = Math.min(pageCount, currentGroup * pagesPerGroup);


        if (currentGroup > 1) {
            addButton(paginationContainer, '〈〈', 1, 'First');
            addButton(paginationContainer, '〈', groupStart - 1, 'Previous group');
        }

        // Page numbers
        for (let i = groupStart; i <= groupEnd; i++) {
            addButton(paginationContainer, i, i);
        }

        // 'Next Group' and 'Last' buttons
        if (currentGroup < totalGroups) {
            addButton(paginationContainer, '〉', groupEnd + 1, 'Next group');
            addButton(paginationContainer, '〉〉', pageCount, 'Last');
        }
    }

    function addButton(container, text, page, ariaLabel = '') {
        const pageItem = document.createElement('li');
        pageItem.className = 'page-item';
        if (page === currentPage) pageItem.classList.add('active');
        const pageLink = document.createElement('a');
        pageLink.className = 'page-link';
        pageLink.href = '#';
        pageLink.textContent = text;
        pageLink.setAttribute('aria-label', ariaLabel || text);
        pageLink.addEventListener('click', function (e) {
            e.preventDefault();
            currentPage = page;
            if (text === '〈〈' || text === '〈' || text === '〉' || text === '〉〉') {
                currentGroup = Math.ceil(page / pagesPerGroup);
            }
            renderPage(page);
            updateActivePage(page);
        });
        pageItem.appendChild(pageLink);
        container.appendChild(pageItem);
    }

    function updateActivePage(page) {
        document.querySelectorAll('#pagination .page-item').forEach(item => {
            item.classList.remove('active');
        });
        const activePage = Array.from(document.querySelectorAll('#pagination .page-link')).find(link => link.textContent == page.toString());
        if (activePage) {
            activePage.parentElement.classList.add('active');
        }
    }

    function renderPage(pageNumber) {
        const startIndex = (pageNumber - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;
        allCards.forEach((card, index) => {
            card.style.display = 'none';
            if (index >= startIndex && index < endIndex) {
                card.style.display = 'block';
            }
        });
        setupPagination();
    }

    setupPagination();
    renderPage(currentPage);
});

document.addEventListener('DOMContentLoaded', function () {
    const timerElement = document.getElementById('timer');
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

    // 매 초마다 업데이트
    setInterval(updateRemainingTime, 1000);
    updateRemainingTime(); // 초기 실행
});

document.addEventListener("DOMContentLoaded", function () {
    const couponRows = document.querySelectorAll('tr[data-bs-toggle="modal"]');
    couponRows.forEach(function (row) {
        row.addEventListener('click', function () {
            const couponName = this.getAttribute('data-name');
            const couponDescription = this.getAttribute('data-description');
            const createdDate = this.getAttribute('data-created');
            const expiredDate = this.getAttribute('data-expired');
            const couponStatus = this.getAttribute('data-status');

            // 모달 요소에 값을 설정
            document.getElementById('modalCouponName').innerText = couponName;
            document.getElementById('modalCouponDescription').innerText = couponDescription;
            document.getElementById('modalCouponCreated').innerText = createdDate;
            document.getElementById('modalCouponExpired').innerText = expiredDate;
            const statusBadge = document.getElementById('modalCouponStatus');
            statusBadge.innerText = couponStatus;
            switch (couponStatus) {
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
        });
    });
});

