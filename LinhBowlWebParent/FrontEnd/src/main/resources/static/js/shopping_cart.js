
$(document).ready(function (){
    $(".link-minus").on("click", function (e){
        e.preventDefault();
        productId = $(this).attr("pid");
        quantityInput = $("#quantity" + productId);
        newQuantity = parseInt(quantityInput.val()) - 1;
        if(newQuantity > 0){
            quantityInput.val(newQuantity);
            updateQuantity(productId, newQuantity)
        }
    });
    $(".link-plus").on("click", function (e){
        e.preventDefault();
        productId = $(this).attr("pid");
        quantityInput = $("#quantity" + productId);
        newQuantity = parseInt(quantityInput.val()) + 1;
        if(newQuantity <= 5){
            quantityInput.val(newQuantity);
            updateQuantity(productId, newQuantity)
        }
    })
    $(".link-remove").on("click", function (e){
        e.preventDefault();
        removeProduct($(this));
    })
})

function updateQuantity(productId, quantity){
    url = contextPath + "cart/update/" + productId + "/" + quantity

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfHeaderValue);
        }
    }).done(function (subTotal){
        updateSubTotal(subTotal, productId)
        updateTotal()
    })
}

function updateSubTotal(subTotal, productId){
    $("#subtotal" + productId).text(subTotal)
}

function updateTotal(){
    total = 0.0
    productCount = 0
    $(".subtotal").each(function (index, element){
        productCount++
        total += parseFloat(element.innerHTML)
    })
    if(productCount < 1){
        showEmptyCart()
    }else {
        $("#total").text(total)
    }
}

function removeProduct(link){
    url = link.attr("href")

    $.ajax({
        type: "DELETE",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfHeaderValue);
        }
    }).done(function (){
        rowNum = link.attr("rowNum")
        removeHTMLElement(rowNum)
        updateTotal()
        updateCountNumber()
    })
}

function removeHTMLElement(rowNum){
    $("#row" + rowNum).remove()
}
function updateCountNumber(){
    $(".index").each(function (index, element){
        element.innerHTML = "" + (index + 1)
    })
}

function showEmptyCart(){
    $(".total-price").hide()
    $("#sectionEmpty").removeClass("d-none");
}