package com.github.schedulejob.util;

import com.github.schedulejob.common.Response;
import com.github.schedulejob.common.RetCodeConst;

import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 2016/6/25 0025.
 */
public class TestResponseBuilder {
    public static void main(String[] args) {
        List<Integer> idList = Arrays.asList(1, 2, 3, 4, 5, 6);
        Response<List<Integer>> resp = ResponseBuilder.newResponse()
                .withData(idList)
                .withRetCode(RetCodeConst.ERROR)
                .build();
        System.out.println("resp = " + resp);
    }
}
