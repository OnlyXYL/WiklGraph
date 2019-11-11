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
import top.wikl.orientdb.service.OrientDbFunctionsService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author XYL
 * @title: OrientDbFunctionsController
 * @description: SQL Function In OrientDb
 * @date 2019/10/9 11:21
 * @return
 * @since V1.0
 */
@Api(tags = "SQL Function In OrientDb")
@RestController
@RequestMapping(value = "/orientdb/functions")
public class OrientDbFunctionsController {

    @Resource
    private OrientDbFunctionsService orientDbFunctionsService;

    /**
     * Get the adjacent incoming vertices starting from the current record as Vertex.
     * <p>
     * Syntax:
     * <p>
     * in([<label-1>][,<label-n>]*)
     *
     * @param inInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/14 10:34
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key 是属性，value 是对应的值")})
    @ApiOperation(value = "Get the adjacent incoming vertices starting from the current record as Vertex.", notes = "Get the adjacent incoming vertices starting from the current record as Vertex.")
    @PostMapping(value = "/in")
    public ResponseEntity<String> in(@RequestBody @Valid InInput inInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        String map = orientDbFunctionsService.in(inInput);

        return ResponseEntity
                .ok()
                .body(
                        map
                );
    }

    /**
     * Get the adjacent outgoing vertices starting from the current record as Vertex.
     * <p>
     * Syntax: out([<label-1>][,<label-n>]*)
     *
     * @param outInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/14 10:36
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key 是属性，value 是对应的值")})
    @ApiOperation(value = "Get the adjacent outgoing vertices starting from the current record as Vertex.", notes = "Get the adjacent outgoing vertices starting from the current record as Vertex.")
    @PostMapping(value = "/out")
    public ResponseEntity<String> out(@RequestBody @Valid OutInput outInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        String out = orientDbFunctionsService.out(outInput);

        return ResponseEntity
                .ok()
                .body(
                        out
                );
    }

    /**
     * Get the adjacent outgoing and incoming vertices starting from the current record as Vertex.
     * <p>
     * Syntax:
     * <p>
     * both([<label1>][,<label-n>]*)
     *
     * @param bothInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/14 10:37
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key 是属性，value 是对应的值")})
    @ApiOperation(value = "Get the adjacent outgoing and incoming vertices starting from the current record as Vertex", notes = "Get the adjacent outgoing and incoming vertices starting from the current record as Vertex")
    @PostMapping(value = "/both")
    public ResponseEntity<String> both(@RequestBody @Valid BothInput bothInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        String both = orientDbFunctionsService.both(bothInput);

        return ResponseEntity
                .ok()
                .body(
                        both
                );
    }

    /**
     * Get the adjacent outgoing edges starting from the current record as Vertex.
     * <p>
     * Syntax:
     * <p>
     * outE([<label1>][,<label-n>]*)
     *
     * @param outEInput, bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/14 15:48
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key 是属性，value 是对应的值")})
    @ApiOperation(value = "Get the adjacent outgoing edges starting from the current record as Vertex.", notes = "Get the adjacent outgoing edges starting from the current record as Vertex.")
    @PostMapping(value = "/outE")
    public ResponseEntity<String> outE(@RequestBody @Valid OutEInput outEInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        String outE = orientDbFunctionsService.outE(outEInput);

        return ResponseEntity
                .ok()
                .body(
                        outE
                );

    }

    /**
     * Get the adjacent incoming edges starting from the current record as Vertex.
     * <p>
     * Syntax:
     * <p>
     * inE([<label1>][,<label-n>]*)
     *
     * @param inEInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/14 16:05
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key 是属性，value 是对应的值")})
    @ApiOperation(value = "Get the adjacent incoming edges starting from the current record as Vertex.", notes = "Get the adjacent incoming edges starting from the current record as Vertex.")
    @PostMapping(value = "/inE")
    public ResponseEntity<String> inE(@RequestBody @Valid InEInput inEInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        String inE = orientDbFunctionsService.inE(inEInput);

        return ResponseEntity
                .ok()
                .body(
                        inE
                );
    }

    /**
     * Get the adjacent outgoing and incoming edges starting from the current record as Vertex.
     * <p>
     * Syntax: bothE([<label1>][,<label-n>]*)
     *
     * @param bothEInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/14 16:38
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key 是属性，value 是对应的值")})
    @ApiOperation(value = "Get the adjacent outgoing and incoming edges starting from the current record as Vertex.", notes = "Get the adjacent outgoing and incoming edges starting from the current record as Vertex.")
    @PostMapping(value = "/bothE")
    public ResponseEntity<String> bothE(@RequestBody @Valid BothEInput bothEInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        String bothE = orientDbFunctionsService.bothE(bothEInput);

        return ResponseEntity
                .ok()
                .body(
                        bothE
                );
    }

    /**
     * Get the adjacent outgoing and incoming vertices starting from the current record as Edge.
     * <p>
     * Syntax: bothV()
     *
     * @param bothVInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/14 17:06
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key 是属性，value 是对应的值")})
    @ApiOperation(value = "Get the adjacent outgoing and incoming vertices starting from the current record as Edge.", notes = "Get the adjacent outgoing and incoming vertices starting from the current record as Edge.")
    @PostMapping(value = "/bothV")
    public ResponseEntity<String> bothV(@RequestBody @Valid BothVInput bothVInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        String bothV = orientDbFunctionsService.bothV(bothVInput);

        return ResponseEntity
                .ok()
                .body(
                        bothV
                );
    }

    /**
     * Get outgoing vertices starting from the current record as Edge.
     * <p>
     * Syntax:
     * <p>
     * outV()
     *
     * @param outVInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/14 17:18
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key 是属性，value 是对应的值")})
    @ApiOperation(value = "Get outgoing vertices starting from the current record as Edge.", notes = "Get outgoing vertices starting from the current record as Edge.")
    @PostMapping(value = "/outV")
    public ResponseEntity<String> outV(@RequestBody @Valid OutVInput outVInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        String outV = orientDbFunctionsService.outV(outVInput);

        return ResponseEntity
                .ok()
                .body(
                        outV
                );
    }

    /**
     * Get incoming vertices starting from the current record as Edge.
     * <p>
     * Syntax:
     * <p>
     * inV()
     *
     * @param inVInput
     * @param bindingResult
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     * @author XYL
     * @date 2019/10/14 17:24
     * @since V1.0
     */
    @ApiResponses({@ApiResponse(code = 200, message = "{key,value} ==> 返回的Json串中，key 是属性，value 是对应的值")})
    @ApiOperation(value = "Get outgoing vertices starting from the current record as Edge.", notes = "Get outgoing vertices starting from the current record as Edge.")
    @PostMapping(value = "/inV")
    public ResponseEntity<String> inV(@RequestBody @Valid InVInput inVInput, BindingResult bindingResult) {

        //参数校验
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult);
        }

        //调用方法
        String inV = orientDbFunctionsService.inV(inVInput);

        return ResponseEntity
                .ok()
                .body(
                        inV
                );
    }


}
