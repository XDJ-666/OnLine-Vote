function ajaxUpload() {
//获取表单数据,并添加到FormData中
    let myfile = $("#image")[0].files[0];
    let formData = new FormData();
    formData.append("myfile", myfile);
//发送AJAX请求
    $.ajax({
//定义请求地址
        url: "${pageContext.request.contextPath}/doAjaxUpload",
//定义请求类型
        type: "post",
//定义请求参数（使用js对象）
        data: formData,
//告诉jQuery不要去处理发送的数据，我们已经通过FormData处理了
        processData: false,
        contentType: false,
//定义服务器响应的数据格式为json
        dataType: "json",
//成功响应的结果，data就是服务器返回的json对象
        success: function (data) {
            alert(data.msg);
            if (data.flag == "1") {
                $("#img").attr("src", "${pageContext.request.contextPath}/uploadfiles/" + data.content);
            }
        }
    });
}