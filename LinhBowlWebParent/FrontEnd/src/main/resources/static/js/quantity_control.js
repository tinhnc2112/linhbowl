
$(document).ready(function (){
    $(".message").hide()
    $(".link-minus").on("click", function (e){
        e.preventDefault();
        productId = $(this).attr("pid");
        quantityInput = $("#quantity" + productId);
        newQuantity = parseInt(quantityInput.val()) - 1;
        if(newQuantity > 0){
            quantityInput.val(newQuantity);
            $(".message").hide()
        }
    });
    $(".link-plus").on("click", function (e){
        e.preventDefault();
        productId = $(this).attr("pid");
        quantityInput = $("#quantity" + productId);
        newQuantity = parseInt(quantityInput.val()) + 1;
        if(newQuantity <= 5){
            quantityInput.val(newQuantity);
        }else{
            $(".message-warning").text("The maximum allowable quantity is 5.")
            $(".message-success").hide()
            $(".message").show()
        }
    })

})
