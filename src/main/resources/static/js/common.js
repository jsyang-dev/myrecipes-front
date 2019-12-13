$(window).on("load", function () {
    $(".loader").delay(1000).fadeOut("slow");
});

$(document).ready(function () {
    $("#currentYear").text((new Date()).getFullYear());
    attachTopScroller(".scrollUp");
});

function attachTopScroller(elementId) {
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $(elementId).fadeIn();
        } else {
            $(elementId).fadeOut();
        }
    });
    // Scroll To Top Animation
    $(elementId).click(function () {
        $("html, body").animate({
            scrollTop: 0
        }, 1000);
        return false;
    });
}

/******************************* Custom *******************************/
$.fn.serializeObject = function () {
    const result = {};
    const extend = function (i, element) {
        const node = result[element.name];
        if ("undefined" !== typeof node && node !== null) {
            if ($.isArray(node)) {
                node.push(element.value)
            } else {
                result[element.name] = [node, element.value]
            }
        } else {
            result[element.name] = element.value
        }
    };

    $.each(this.serializeArray(), extend);
    return result
};

const uploadImage = function (path, $imageFile, $image, $imagePreview) {
    const file = $imageFile[0].files[0];
    const sendData = new FormData();
    sendData.append("file", file);
    sendData.append("path", path);

    $.ajax({
        type: "POST",
        url: "/recipe/upload/ajax",
        data: sendData,
        processData: false,
        contentType: false,
        async: true,
        beforeSend: function (xhr) {
            xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));
        },
        success: function (data, status, xhr) {
            $image.val(data);
            $imagePreview.attr("src", data);
            alert("사진이 등록되었습니다.");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.responseJSON.message);
            alert("사진이 등록이 실패하였습니다.");
        }
    });
};

const showErrorMessage = function (response) {
    const errorFields = response.responseJSON.errors;

    if (!errorFields) {
        alert(response.responseJSON.message);
        return;
    }

    for (let i = 0, length = errorFields.length; i < length; i++) {
        const error = errorFields[i];
        const splitField = error.field.split(".");
        let $field = "";
        let errorMessage = "";

        if (splitField.length === 1) {
            $field = $("#" + error.field);
            errorMessage = "<span class=\"error-message text-small text-danger\">" + error.defaultMessage + "</span>"
        } else {
            if (splitField[1] === "tag") {
                $field = $("#" + splitField[1]);
                errorMessage = "<span class=\"error-message text-small text-danger\">" + error.defaultMessage + "</span>"
            } else {
                const form = splitField[0].split("[")[0];
                const seq = Number(splitField[0].split("[")[1].split("]")[0]) + 1;
                $field = $("#" + form).find(".row:nth-child(" + seq + ")");
                errorMessage = "<span class=\"error-message text-small text-danger text-center\"> └ " + error.defaultMessage + "</span>"
            }
        }

        if ($field && $field.length > 0) {
            $field.siblings(".error-message").remove();
            $field.after(errorMessage);
        }
    }
};

const logout = function () {
    $("#logoutForm")[0].submit();
};