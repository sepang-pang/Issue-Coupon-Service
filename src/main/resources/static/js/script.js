document.addEventListener("DOMContentLoaded", function () {
    const currentPath = window.location.pathname;
    if (currentPath === "/") {
        document.getElementById("nav-home").classList.add("active");
    } else if (currentPath === "/features") {
        document.getElementById("nav-features").classList.add("active");
    } else if (currentPath === "/contact") {
        document.getElementById("nav-contact").classList.add("active");
    }
});
