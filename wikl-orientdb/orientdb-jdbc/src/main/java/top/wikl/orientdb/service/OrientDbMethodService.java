package top.wikl.orientdb.service;

import top.wikl.entity.graph.input.*;

/**
 * @author XYL
 * @title: OrientDbMethodService
 * @description: SQL Methods In OrientDb {@see https://orientdb.org/docs/3.0.x/sql/SQL-Methods.html}
 * @date 2019/10/9 10:13
 * @return
 * @since V1.0
 */
public interface OrientDbMethodService {

    /**
     * Convert a value to another type.
     * <p>
     * Syntax: <value>.convert(<type>)
     *
     * @param convertInput
     * @return
     * @author XYL
     * @date 2019/10/9 10:29
     * @since V1.0
     */
    String convert(ConvertInput convertInput);

    /**
     * Appends a string to another one.
     * <p>
     * Syntax: <value>.append(<value>)
     *
     * @param appendInput
     * @return
     * @author XYL
     * @date 2019/10/10 17:21
     * @since V1.0
     */
    String append(AppendInput appendInput);

    /**
     * Returns the character of the string contained in the position 'position'. 'position' starts from 0 to string length.
     * <p>
     * Syntax: <value>.charAt(<position>)
     *
     * @param charAtInput
     * @return
     * @author XYL
     * @date 2019/10/11 9:53
     * @since V1.0
     */
    String charAt(CharAtInput charAtInput);

    /**
     * Returns the position of the 'string-to-search' inside the value. It returns -1 if no occurrences are found. 'begin-position' is the optional position where to start, otherwise the beginning of the string is taken (=0).
     * <p>
     * Syntax: <value>.indexOf(<string-to-search> [, <begin-position>)
     *
     * @param indexOfInput
     * @return
     * @author XYL
     * @date 2019/10/11 18:05
     * @since V1.0
     */
    String indexOf(IndexOfInput indexOfInput);

    /**
     * Returns a substring of the original cutting from the begin and getting 'len' characters.
     * <p>
     * Syntax: <value>.left(<length>)
     *
     * @param leftInput
     * @return
     * @author XYL
     * @date 2019/10/11 18:27
     * @since V1.0
     */
    String left(LeftInput leftInput);

    /**
     * Prefixes a string to another one.
     * <p>
     * Syntax: <value>.prefix('<string>')
     *
     * @param rightInput
     * @return
     * @author XYL
     * @date 2019/10/11 18:34
     * @since V1.0
     */
    String right(RightInput rightInput);

    /**
     * Prefixes a string to another one.
     * <p>
     * Syntax: <value>.prefix('<string>')
     *
     * @param prefixInput
     * @return
     * @author XYL
     * @date 2019/10/12 15:16
     * @since V1.0
     */
    String prefix(PrefixInput prefixInput);

    /**
     * Replace a string with another one.
     * <p>
     * Syntax: <value>.replace(<to-find>, <to-replace>)
     *
     * @param replaceInput
     * @return
     * @author XYL
     * @date 2019/10/12 15:27
     * @since V1.0
     */
    String replace(ReplaceInput replaceInput);

    /**
     * Returns the length of the string. If the string is null 0 will be returned.
     * <p>
     * Syntax: <value>.length()
     *
     * @param lengthInput
     * @return
     * @author XYL
     * @date 2019/10/12 15:43
     * @since V1.0
     */
    String length(LengthInput lengthInput);

    /**
     * Returns a substring of the original cutting from 'begin' index up to 'end' index (not included).
     * <p>
     * Syntax: <value>.subString(<begin> [,<end>] )
     *
     * @param subStringInput
     * @return
     * @author XYL
     * @date 2019/10/12 15:52
     * @since V1.0
     */
    String substring(SubStringInput subStringInput);

    /**
     * Returns the string in lower case.
     * <p>
     * Syntax: <value>.toLowerCase()
     *
     * @param toLowerCaseInput
     * @return
     * @author XYL
     * @date 2019/10/12 15:59
     * @since V1.0
     */
    String toLowerCase(ToLowerCaseInput toLowerCaseInput);

    /**
     * Returns the string in upper case.
     * <p>
     * Syntax: <value>.toUpperCase()
     *
     * @param toUpperCaseInput
     * @return
     * @author XYL
     * @date 2019/10/12 16:02
     * @since V1.0
     */
    String toUpperCase(ToUpperCaseInput toUpperCaseInput);

    /**
     * Returns the value formatted using the common "printf" syntax. For the complete reference goto Java Formatter JavaDoc. To know more about it, look at Managing Dates.
     * <p>
     * Syntax: <value>.format(<format>)
     *
     * @param formatInput
     * @return
     * @author XYL
     * @date 2019/10/12 16:28
     * @since V1.0
     */
    String format(FormatInput formatInput);

}
