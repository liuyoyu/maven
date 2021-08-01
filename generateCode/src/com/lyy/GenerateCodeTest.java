package com.lyy;

import com.lyy.Api.GenerateCodeApi;
import com.lyy.Service.GenerateCodeService;

/**
 * author liuyongyu
 * date 2021/8/1
 */
public class GenerateCodeTest {
    public static void main(String[] args) {
        GenerateCodeApi api = new GenerateCodeService();
        api.generateVO();
    }
}
