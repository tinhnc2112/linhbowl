$(document).ready(function (){
    $("#btnAddToCart").on("click", function (e) {
        quantity = $("#quantity" + productId).val()
        $(".message-warning").hide()
        addToCart(quantity);
    })
})

function addToCart(quantity){
    url = contextPath + "cart/add/" + productId + "/" + quantity
    login = contextPath + "login"
    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfHeaderValue);
        }
    }).done(function (res){
        if(res == "login"){
            window.location = login
        }else{
            $(".message-success").text(quantity + " item(s) have been added to cart.")
            $(".message").show()
        }
    }).fail(function (res){
        console.log("fail")
    })
}