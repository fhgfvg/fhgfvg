package org.example.core.model.vo;

import lombok.Data;
import org.example.core.schema.TableSchema;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Author: ZhaoXing

 */
@Data
public class GeneratorVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 表概要
     */
    private TableSchema tableSchema;

    /**
     * sql语句
     */
    private String createSql;

    /**
     * 数据列表
     */
    private List<Map<String, Object>> dataList;

    /**
     *
     */
    private String insertSql;

    private String dataJson;

    private String javaEntityCode;

    private String javaObjectCode;

    private String typescriptTypeCode;
}
