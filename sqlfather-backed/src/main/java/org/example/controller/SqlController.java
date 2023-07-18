package org.example.controller;

import io.common.utils.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.core.GeneratorFacade;
import org.example.core.schema.TableSchema;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: ZhaoXing

 */
@RestController
@Slf4j
@RequestMapping("/sql")
public class SqlController {

    @ApiOperation(tags = "sql生成",value = "sql生成")
    @PostMapping("/generate/schema")
    public Result generateBySchema(@RequestBody TableSchema tableSchema){
        return new Result().ok(GeneratorFacade.generateAll(tableSchema));
    }
}
