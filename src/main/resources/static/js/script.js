document.addEventListener("DOMContentLoaded", function () {
    const currentPath = window.location.pathname;
    if (currentPath === "/") {
        document.getElementById("nav-home").classList.add("active");
    } else if (currentPath === "/upcoming") {
        document.getElementById("nav-upcoming").classList.add("active");
    } else if (currentPath === "/contact") {
        document.getElementById("nav-contact").classList.add("active");
    }
});

// == 카드 높이 조정 스크립트 == //
window.onload = function() {
    let maxHeight = 0;
    const cardBodies = document.querySelectorAll('.card-body');
    cardBodies.forEach(function(card) {
        if (card.offsetHeight > maxHeight) {
            maxHeight = card.offsetHeight;
        }
    });
    cardBodies.forEach(function(card) {
        card.style.height = maxHeight + 'px';
    });
};

// == 페이징 처리 스크립트 == //
document.addEventListener("DOMContentLoaded", function() {
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
        pageLink.addEventListener('click', function(e) {
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
