$(document).ready(function () {
    $("#btnCancel").on("click", function () {
        window.location = moduleURL
    });
    $("#fileImage").change(function () {
            showImageThumbnail(this);
    })
});

function showImageThumbnail(fileInput) {
    var file = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function (e) {
        $("#thumbnail").attr("src", e.target.result);
    }
    reader.readAsDataURL(file);
}