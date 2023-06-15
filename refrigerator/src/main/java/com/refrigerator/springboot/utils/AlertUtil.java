package com.refrigerator.springboot.utils;



import org.apache.coyote.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AlertUtil {

    void loginAlert(HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=euc-kr");
        response.setCharacterEncoding("euc-kr");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('로그인 후 이용 가능합니다.');history.back();</script>");
        out.flush();
    }
}
