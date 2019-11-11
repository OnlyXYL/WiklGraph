package top.wikl.orientdb.service.impl;

import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wikl.entity.graph.input.*;
import top.wikl.entity.graph.input.vo.*;
import top.wikl.exception.NotFoundException;
import top.wikl.orientdb.service.OrientDbMethodService;
import top.wikl.orientdb.utils.WiklOrientdbUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author XYL
 * @title: OrientDbMethodServiceImpl
 * @description: SQL Methods In OrientDb {@see https://orientdb.org/docs/3.0.x/sql/SQL-Methods.html}
 * @date 2019/10/9 10:16
 * @return
 * @since V1.0
 */
@Slf4j
@Service
public class OrientDbMethodServiceImpl implements OrientDbMethodService {

    @Resource
    private ODatabasePool pool;

    @Override
    public String convert(ConvertInput convertInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取输入参数
        List<ConvertInputParamVo> input = convertInput.getInput();

        //查询条件
        Map<String, String> conditions = convertInput.getConditions();

        //label
        String label = convertInput.getLabel();

        //遍历输入参数和类型
        if (Objects.nonNull(input)) {

            int index = 0;

            for (ConvertInputParamVo paramVo : input) {

                String field = paramVo.getField();

                String type = paramVo.getType();

                if (index == 0) {
                    sql.append(field + ".convert('" + type + "') as " + field + "_" + type);
                } else {
                    sql.append("," + field + ".convert('" + type + "') as " + field + "_" + type);
                }

                index++;

            }
        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + convertInput.getLimit());

        log.info("The Sql Of Convert is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String append(AppendInput appendInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<AppendInputParamVo> input = appendInput.getInput();

        //class
        String label = appendInput.getLabel();

        //查询条件
        Map<String, String> conditions = appendInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (AppendInputParamVo paramVo : input) {

                String field = paramVo.getField();

                String appendStr = paramVo.getAppendStr();

                if (index == 0) {
                    sql.append(field + ".append('" + appendStr + "') as " + field);
                } else {
                    sql.append("," + field + ".convert('" + appendStr + "') as " + field);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + appendInput.getLimit());

        log.info("The Sql Of Append is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String charAt(CharAtInput charAtInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<CharAtInputParamVo> input = charAtInput.getInput();

        //class
        String label = charAtInput.getLabel();

        //查询条件
        Map<String, String> conditions = charAtInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (CharAtInputParamVo paramVo : input) {

                String field = paramVo.getField();

                Integer position = paramVo.getPosition();

                if (index == 0) {
                    sql.append(field + ".charAt(" + position + ") as " + field + "_" + position);
                } else {
                    sql.append("," + field + ".charAt(" + position + ") as " + field + "_" + position);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + charAtInput.getLimit());

        log.info("The Sql Of ChartAt is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;

    }

    @Override
    public String indexOf(IndexOfInput indexOfInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<IndexOfInputParamVo> input = indexOfInput.getInput();

        //class
        String label = indexOfInput.getLabel();

        //查询条件
        Map<String, String> conditions = indexOfInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (IndexOfInputParamVo paramVo : input) {

                String field = paramVo.getField();

                String character = paramVo.getCharacter();

                if (index == 0) {
                    sql.append(field + ".indexOf('" + character + "') as " + field + "_" + character);
                } else {
                    sql.append("," + field + ".indexOf('" + character + "') as " + field + "_" + character);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + indexOfInput.getLimit());

        log.info("The Sql Of indexOf is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String left(LeftInput leftInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<LeftInputParamVo> input = leftInput.getInput();

        //class
        String label = leftInput.getLabel();

        //查询条件
        Map<String, String> conditions = leftInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (LeftInputParamVo paramVo : input) {

                String field = paramVo.getField();

                Integer length = paramVo.getLength();

                if (index == 0) {
                    sql.append(field + ".left(" + length + ") as " + field + "_left_" + length);
                } else {
                    sql.append("," + field + ".left('" + length + ") as " + field + "_left_" + length);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + leftInput.getLimit());

        log.info("The Sql Of Left is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String right(RightInput rightInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<RightInputParamVo> input = rightInput.getInput();

        //class
        String label = rightInput.getLabel();

        //查询条件
        Map<String, String> conditions = rightInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (RightInputParamVo paramVo : input) {

                String field = paramVo.getField();

                Integer length = paramVo.getLength();

                if (index == 0) {
                    sql.append(field + ".right(" + length + ") as " + field + "_right_" + length);
                } else {
                    sql.append("," + field + ".right(" + length + ") as " + field + "_right_" + length);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + rightInput.getLimit());

        log.info("The Sql Of Right is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String prefix(PrefixInput prefixInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<PrefixInputParamVo> input = prefixInput.getInput();

        //class
        String label = prefixInput.getLabel();

        //查询条件
        Map<String, String> conditions = prefixInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (PrefixInputParamVo paramVo : input) {

                String field = paramVo.getField();

                String prefix = paramVo.getPrefix();

                if (index == 0) {
                    sql.append(field + ".prefix('" + prefix + "') as " + field + "_" + prefix);
                } else {
                    sql.append("," + field + ".prefix('" + prefix + "') as " + field + "_" + prefix);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + prefixInput.getLimit());

        log.info("The Sql Of prefix is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String replace(ReplaceInput replaceInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<ReplaceInputParamVo> input = replaceInput.getInput();

        //class
        String label = replaceInput.getLabel();

        //查询条件
        Map<String, String> conditions = replaceInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (ReplaceInputParamVo paramVo : input) {

                String field = paramVo.getField();

                //原始字符
                String originString = paramVo.getOriginString();

                //替换的字符
                String replace = paramVo.getReplace();

                if (index == 0) {
                    sql.append(field + ".replace('" + originString + "','" + replace + "') as " + field);
                } else {
                    sql.append("," + field + ".replace('" + originString + "','" + replace + "') as " + field);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + replaceInput.getLimit());

        log.info("The Sql Of replace is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String length(LengthInput lengthInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<LengthInputParamVo> input = lengthInput.getInput();

        //class
        String label = lengthInput.getLabel();

        //查询条件
        Map<String, String> conditions = lengthInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (LengthInputParamVo paramVo : input) {

                String field = paramVo.getField();

                if (index == 0) {
                    sql.append(field + ".length() as " + field);
                } else {
                    sql.append("," + field + ".length() as " + field);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + lengthInput.getLimit());

        log.info("The Sql Of length is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String substring(SubStringInput subStringInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<SubStringInputParamVo> input = subStringInput.getInput();

        //class
        String label = subStringInput.getLabel();

        //查询条件
        Map<String, String> conditions = subStringInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (SubStringInputParamVo paramVo : input) {

                //属性字段
                String field = paramVo.getField();

                //开始索引
                Integer startIndex = paramVo.getStartIndex();

                //结束索引
                Integer endIndex = paramVo.getEndIndex();

                if (index == 0) {
                    sql.append(field + ".substring(" + startIndex + "," + endIndex + ") as " + field);
                } else {
                    sql.append("," + field + ".substring(" + startIndex + "," + endIndex + ") as " + field);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + subStringInput.getLimit());

        log.info("The Sql Of substring is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String toLowerCase(ToLowerCaseInput toLowerCaseInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<ToLowerCaseInputParamVo> input = toLowerCaseInput.getInput();

        //class
        String label = toLowerCaseInput.getLabel();

        //查询条件
        Map<String, String> conditions = toLowerCaseInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (ToLowerCaseInputParamVo paramVo : input) {

                //属性字段
                String field = paramVo.getField();

                if (index == 0) {
                    sql.append(field + ".toLowerCase() as " + field);
                } else {
                    sql.append("," + field + ".toLowerCase() as " + field);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + toLowerCaseInput.getLimit());

        log.info("The Sql Of toLowerCase is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String toUpperCase(ToUpperCaseInput toUpperCaseInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<ToUpperCaseInputParamVo> input = toUpperCaseInput.getInput();

        //class
        String label = toUpperCaseInput.getLabel();

        //查询条件
        Map<String, String> conditions = toUpperCaseInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (ToUpperCaseInputParamVo paramVo : input) {

                //属性字段
                String field = paramVo.getField();

                if (index == 0) {
                    sql.append(field + ".toUpperCase() as " + field);
                } else {
                    sql.append("," + field + ".toUpperCase() as " + field);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + toUpperCaseInput.getLimit());

        log.info("The Sql Of toUpperCase is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    @Override
    public String format(FormatInput formatInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //获取请求参数
        List<FormatInputParamVo> input = formatInput.getInput();

        //class
        String label = formatInput.getLabel();

        //查询条件
        Map<String, String> conditions = formatInput.getConditions();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (FormatInputParamVo paramVo : input) {

                //属性字段
                String field = paramVo.getField();

                String rule = paramVo.getRule();

                if (index == 0) {
                    sql.append(field + ".format('" + rule + "') as " + field);
                } else {
                    sql.append("," + field + ".format('" + rule + "') as " + field);
                }

                index++;

            }

        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + formatInput.getLimit());

        log.info("The Sql Of format is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(pool, sql, label);

        return executeSql;
    }

    /**
     * 执行sql
     *
     * @param pool
     * @param sql
     * @param label
     * @return java.lang.String
     * @author XYL
     * @date 2019/10/10 18:35
     * @since V1.0
     */
    public String executeSql(ODatabasePool pool, StringBuilder sql, String label) {

        try (ODatabaseSession session = pool.acquire();) {

            //判断class是否存在
            OClass aClass = session.getClass(label);

            if (Objects.isNull(aClass)) {
                throw new NotFoundException("class not found: " + label);
            }

            OResultSet resultSet = session.command(sql.toString());

            while (resultSet.hasNext()) {

                OResult next = resultSet.next();

                OResultInternal oResultInternal = (OResultInternal) next;

                String result = oResultInternal.toJSON();

                return result;
            }

            //关闭资源
            WiklOrientdbUtil.close(resultSet);

        }

        return null;

    }

    /**
     * 拼接sql的查询条件
     *
     * @param sql
     * @param condition
     * @return void
     * @author XYL
     * @date 2019/10/10 18:09
     * @since V1.0
     */
    public void appendWhere(StringBuilder sql, Map<String, String> condition) {

        //拼接查询条件
        if (Objects.nonNull(condition)) {

            //条件索引
            int conditionIndex = 0;

            for (Map.Entry<String, String> entry : condition.entrySet()) {

                //条件key
                String key = entry.getKey();

                //条件值
                String value = entry.getValue();

                if (conditionIndex == 0) {
                    sql.append(" where " + key + " = '" + value + "'");
                } else {
                    sql.append(" and " + key + " = '" + value + "'");
                }

                conditionIndex++;
            }
        }
    }

}
