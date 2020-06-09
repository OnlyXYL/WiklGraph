package top.wikl.orientdb.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wikl.entity.graph.input.*;
import top.wikl.exception.InputException;
import top.wikl.orientdb.service.OrientDbMethodService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author XYL
 * @title: OrientDbMethodController
 * @description: SQL Method In OrientDb
 * @date 2019/10/9 11:22
 * @return
 * @since V1.0
 */
@Api(tags = "SQL Method In OrientDb")
@RestController
@RequestMapping(value = "/orientdb/method")
public class OrientDbMethodController {

    @Resource
    private OrientDbMethodService orientDbMethodService;

    /**
     * Convert a value to another type.
     * <p>
     * Syntax: <value>.convert(<type>)
     *
     * @param convertInput 输入参数
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/9 11:24
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field_type 组成")})
    @ApiOperation(value = "转换值的类型", notes = "Convert a value to another type.")
    @PostMapping(value = "/convert")
    public ResponseEntity<String> convert(@RequestBody @Valid ConvertInput convertInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        String convert = orientDbMethodService.convert(convertInput);

        return ResponseEntity
                .ok()
                .body(
                        convert
                );
    }

    /**
     * Appends a string to another one.
     * <p>
     * Syntax: <value>.append(<value>)
     *
     * @param appendInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/10 18:20
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key是查询的属性，value是拼接之后的结果"))
    @ApiOperation(value = "字符串拼接", notes = "字符串拼接")
    @PostMapping(value = "/append")
    public ResponseEntity<String> append(@RequestBody @Valid AppendInput appendInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String append = orientDbMethodService.append(appendInput);

        return ResponseEntity
                .ok()
                .body(
                        append
                );
    }

    /**
     * Returns the character of the string contained in the position 'position'. 'position' starts from 0 to string length.
     * <p>
     * Syntax: <value>.charAt(<position>)
     *
     * @param charAtInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/10 18:20
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field_position 组成，value是得到的结果"))
    @ApiOperation(value = "返回字符串的位置", notes = "返回字符串的位置")
    @PostMapping(value = "/charAt")
    public ResponseEntity<String> charAt(@RequestBody @Valid CharAtInput charAtInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String charAt = orientDbMethodService.charAt(charAtInput);

        return ResponseEntity
                .ok()
                .body(
                        charAt
                );
    }

    /**
     * Returns the position of the 'string-to-search' inside the value. It returns -1 if no occurrences are found. 'begin-position' is the optional position where to start, otherwise the beginning of the string is taken (=0).
     * <p>
     * Syntax: <value>.indexOf(<string-to-search> [, <begin-position>)
     *
     * @param indexOfInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/10 18:20
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field_character 组成，value是得到的结果"))
    @ApiOperation(value = "返回字符串中指定字符的下标", notes = "返回字符串中指定字符的下标")
    @PostMapping(value = "/indexOf")
    public ResponseEntity<String> indexOf(@RequestBody @Valid IndexOfInput indexOfInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String indexOf = orientDbMethodService.indexOf(indexOfInput);

        return ResponseEntity
                .ok()
                .body(
                        indexOf
                );
    }

    /**
     * Returns a substring of the original cutting from the begin and getting 'len' characters.
     * <p>
     * Syntax: <value>.left(<length>)
     *
     * @param leftInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/10 18:20
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field_left_length 组成，value是得到的结果"))
    @ApiOperation(value = "返回从开始位置到指定长度的字符串", notes = "返回从开始位置到指定长度的字符串")
    @PostMapping(value = "/left")
    public ResponseEntity<String> left(@RequestBody @Valid LeftInput leftInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String left = orientDbMethodService.left(leftInput);

        return ResponseEntity
                .ok()
                .body(
                        left
                );
    }

    /**
     * Returns a substring of the original cutting from the end of the string 'length' characters.
     * <p>
     * Syntax: <value>.right(<length>)
     *
     * @param rightInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/10 18:20
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field_right_length 组成，value是得到的结果"))
    @ApiOperation(value = "返回从结束位置到指定长度的字符串", notes = "返回从结束位置到指定长度的字符串")
    @PostMapping(value = "/right")
    public ResponseEntity<String> right(@RequestBody @Valid RightInput rightInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String right = orientDbMethodService.right(rightInput);

        return ResponseEntity
                .ok()
                .body(
                        right
                );
    }

    /**
     * Prefixes a string to another one.
     * <p>
     * Syntax: <value>.prefix('<string>')
     *
     * @param prefixInput, bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/12 15:19
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field_prefix 组成，value是得到的结果"))
    @ApiOperation(value = "给指定属性的值拼接前缀并返回", notes = "给指定属性的值拼接前缀并返回")
    @PostMapping(value = "/prefix")
    public ResponseEntity<String> prefix(@RequestBody @Valid PrefixInput prefixInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String prefix = orientDbMethodService.prefix(prefixInput);

        return ResponseEntity
                .ok()
                .body(
                        prefix
                );
    }

    /**
     * Replace a string with another one.
     * <p>
     * Syntax: <value>.replace(<to-find>, <to-replace>)
     *
     * @param replaceInput, bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/12 15:32
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field 组成，value是得到的结果"))
    @ApiOperation(value = "使用字符串替换目标属性值中指定的字符串并返回", notes = "使用字符串替换目标属性值中指定的字符串并返回")
    @PostMapping(value = "/replace")
    public ResponseEntity<String> replace(@RequestBody @Valid ReplaceInput replaceInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String replace = orientDbMethodService.replace(replaceInput);

        return ResponseEntity
                .ok()
                .body(
                        replace
                );
    }

    /**
     * Returns the length of the string. If the string is null 0 will be returned.
     * <p>
     * Syntax: <value>.length()
     *
     * @param lengthInput, bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/12 15:45
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field 组成，value是得到的结果"))
    @ApiOperation(value = "返回目标属性的长度", notes = "返回目标属性的长度")
    @PostMapping(value = "/length")
    public ResponseEntity<String> length(@RequestBody @Valid LengthInput lengthInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String length = orientDbMethodService.length(lengthInput);

        return ResponseEntity
                .ok()
                .body(
                        length
                );
    }

    /**
     * Returns a substring of the original cutting from 'begin' index up to 'end' index (not included).
     * <p>
     * Syntax: <value>.subString(<begin> [,<end>] )
     *
     * @param subStringInput, bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/12 15:56
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field 组成，value是得到的结果"))
    @ApiOperation(value = "返回指定索引区间的字符串", notes = "返回指定索引区间的字符串")
    @PostMapping(value = "/subString")
    public ResponseEntity<String> subString(@RequestBody @Valid SubStringInput subStringInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String substring = orientDbMethodService.substring(subStringInput);

        return ResponseEntity
                .ok()
                .body(
                        substring
                );
    }

    /**
     * Returns the string in lower case.
     * <p>
     * Syntax: <value>.toLowerCase()
     *
     * @param toLowerCaseInput, bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/12 16:07
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field 组成，value是得到的结果"))
    @ApiOperation(value = "返回小写字符串", notes = "返回小写字符串")
    @PostMapping(value = "/toLowerCase")
    public ResponseEntity<String> toLowerCase(@RequestBody @Valid ToLowerCaseInput toLowerCaseInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String toLowerCase = orientDbMethodService.toLowerCase(toLowerCaseInput);

        return ResponseEntity
                .ok()
                .body(
                        toLowerCase
                );
    }

    /**
     * Returns the string in upper case.
     * <p>
     * Syntax: <value>.toUpperCase()
     *
     * @param toUpperCaseInput, bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/12 16:08
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field 组成，value是得到的结果"))
    @ApiOperation(value = "返回大写字符串", notes = "返回大写字符串")
    @PostMapping(value = "/toUpperCase")
    public ResponseEntity<String> toUpperCase(@RequestBody @Valid ToUpperCaseInput toUpperCaseInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String toUpperCase = orientDbMethodService.toUpperCase(toUpperCaseInput);

        return ResponseEntity
                .ok()
                .body(
                        toUpperCase
                );
    }

    /**
     * Returns the value formatted using the common "printf" syntax. For the complete reference goto Java Formatter JavaDoc. To know more about it, look at Managing Dates.
     * <p>
     * Syntax: <value>.format(<format>)
     *
     * @param formatInput, bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/12 16:31
     * @since V1.0
     */
    @ApiResponses(@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key由 field 组成，value是得到的结果"))
    @ApiOperation(value = "格式化数据", notes = "格式化数据")
    @PostMapping(value = "/format")
    public ResponseEntity<String> format(@RequestBody @Valid FormatInput formatInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {

            throw new InputException(bindingResult);
        }

        //调用方法
        String format = orientDbMethodService.format(formatInput);

        return ResponseEntity
                .ok()
                .body(
                        format
                );
    }

}
