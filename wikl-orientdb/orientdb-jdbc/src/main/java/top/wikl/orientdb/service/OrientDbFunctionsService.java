package top.wikl.orientdb.service;


import top.wikl.entity.graph.input.*;

/**
 * @author XYL
 * @title: OrientDbFunctionsService
 * @description: SQL Functions In OrientDb {@see https://orientdb.org/docs/3.0.x/sql/SQL-Functions.html}
 * @date 2019/10/9 10:13
 * @return
 * @since V1.0
 */
public interface OrientDbFunctionsService {

    /**
     * Get the adjacent incoming vertices starting from the current record as Vertex.
     * <p>
     * Syntax:
     * <p>
     * in([<label-1>][,<label-n>]*)
     *
     * @param inInput
     * @return
     * @author XYL
     * @date 2019/10/14 10:18
     * @since V1.0
     */
    String in(InInput inInput);

    /**
     * Get the adjacent outgoing vertices starting from the current record as Vertex.
     * <p>
     * Syntax: out([<label-1>][,<label-n>]*)
     *
     * @param outInput
     * @return
     * @author XYL
     * @date 2019/10/14 10:28
     * @since V1.0
     */
    String out(OutInput outInput);

    /**
     * Get the adjacent outgoing and incoming vertices starting from the current record as Vertex.
     * <p>
     * Syntax:
     * <p>
     * both([<label1>][,<label-n>]*)
     *
     * @param bothInput
     * @return
     * @author XYL
     * @date 2019/10/14 10:29
     * @since V1.0
     */
    String both(BothInput bothInput);

    /**
     * Get the adjacent outgoing edges starting from the current record as Vertex.
     * <p>
     * Syntax:
     * <p>
     * outE([<label1>][,<label-n>]*)
     *
     * @param outEInput
     * @return
     * @author XYL
     * @date 2019/10/14 15:38
     * @since V1.0
     */
    String outE(OutEInput outEInput);

    /**
     * Get the adjacent incoming edges starting from the current record as Vertex.
     * <p>
     * Syntax:
     * <p>
     * inE([<label1>][,<label-n>]*)
     *
     * @param inEInput
     * @return
     * @author XYL
     * @date 2019/10/14 16:03
     * @since V1.0
     */
    String inE(InEInput inEInput);

    /**
     * Get the adjacent outgoing and incoming edges starting from the current record as Vertex.
     * <p>
     * Syntax: bothE([<label1>][,<label-n>]*)
     *
     * @param bothEInput
     * @return
     * @author XYL
     * @date 2019/10/14 16:35
     * @since V1.0
     */
    String bothE(BothEInput bothEInput);

    /**
     * Get the adjacent outgoing and incoming vertices starting from the current record as Edge.
     * <p>
     * Syntax: bothV()
     *
     * @param bothVInput
     * @return
     * @author XYL
     * @date 2019/10/14 17:03
     * @since V1.0
     */
    String bothV(BothVInput bothVInput);

    /**
     * Get outgoing vertices starting from the current record as Edge.
     * <p>
     * Syntax:
     * <p>
     * outV()
     *
     * @param outVInput
     * @return
     * @author XYL
     * @date 2019/10/14 17:12
     * @since V1.0
     */
    String outV(OutVInput outVInput);

    /**
     * Get incoming vertices starting from the current record as Edge.
     * <p>
     * Syntax:
     * <p>
     * inV()
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/10/14 17:21
     * @since V1.0
     */
    String inV(InVInput inVInput);
}
