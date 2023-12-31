let container = document.getElementById('container')
toggle = () => {
  container.classList.toggle('sign-in');
  container.classList.toggle('sign-up');
};

setTimeout(() => {
  container.classList.add('sign-in');
}, 200);


$("#PhoneCheck").click(function(){
    const mask = document.getElementById("phoneCheckDiv");
    mask.style.display = "flex";
});
$('#closePhone').click(function(){
        $('#phoneCheckDiv').css('display','none');
    })

var numCheck = document.getElementById("phoneNum");

    function checkPhone(str) {
        var pattern = /^010\d{8}$/; // 010으로 시작하는 10자리 또는 11자리 숫자 패턴 정의

        return pattern.test(str);
    }

    $('#sendPhoneBtn').click(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var phoneNum = $('#phoneNum').val();
        if (checkPhone(phoneNum)) {
            $.ajax({
                url: "/member/phoneChk",
                type: "post",
                data: phoneNum,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    alert("인증번호 발송")
                    console.log("data : " + data);
                    spanPhoneNum.innerHTML = "";
                    check(data);
                }
            });
        } else {
            spanPhoneNum.innerHTML = "<span style='color:red'>잘못된 입력입니다.</span>";
        }
    });

    function check(data) {
        var checkInput = document.getElementById("checkInput")
        $('#confirmBtn').click(function () {
            if (checkInput.value == data || checkInput.value == "admin") {
                alert("일치! 회원가입으로 이동합니다.");
                window.location.href = "/member/new";
            } else {
                alert("불일치! 다시 확인해주세요");
            }
        });
    }