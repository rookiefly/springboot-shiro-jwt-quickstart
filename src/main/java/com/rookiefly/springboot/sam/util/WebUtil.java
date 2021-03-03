package com.rookiefly.springboot.sam.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rookiefly.springboot.sam.common.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class WebUtil {

    static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * response发送响应数据Result
     *
     * @param body responseBody数据
     */
    public static void sendResponse(ServletResponse response, Result body) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(objectMapper.writeValueAsString(body));
        }
    }

}