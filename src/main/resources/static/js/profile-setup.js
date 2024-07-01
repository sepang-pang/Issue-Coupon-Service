function previewImage(input) {
    const reader = new FileReader();
    reader.onload = function (e) {
        document.getElementById('image-preview').src = e.target.result;
    };
    reader.readAsDataURL(input.files[0]);
}

document.getElementById("skip").addEventListener("click", function () {
    window.location.href = "/";
})

document.getElementById("submit").addEventListener("click", function () {
    const formData = new FormData();
    const param = {
        nickName: document.querySelector(".form-control").value
    }

    formData.append('param', new Blob([JSON.stringify(param)], {type: 'application/json'}));
    formData.append('userImage', document.getElementById("file-input").files[0]);

    fetch("/user/profile/setup", {
        method: 'PATCH',
        body: formData
    }).then(response => response.json())
        .then(data => {
            console.log("Success: ", data);
            window.location.href = "/my-page";
        })
        .catch((error) => {
            console.error("Error: ", error);
            alert("프로필 업데이트에 실패했습니다.");
        });
});


// function collectFormDate() {
//     const formData = new FormData();
//     const image = document.getElementById("file-input").files[0];
//     const nickName = document.querySelector(".form-control").value;
//
//     formData.append("file", image); // 파일 추가
//     formData.append("param", new Blob([JSON.stringify({ nickName: nickName })], { type: "application/json" }));
//
//     return formData;
// }
