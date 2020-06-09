package top.wikl.component.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.File;

public class DocFmtConvert {
    /**
     * doc格式
     */
    private static final int DOC_FMT = 0;
    /**
     * docx格式
     */
    private static final int DOCX_FMT = 12;

    /**
     * 描述 The entry point of application.
     *
     * @param args the input arguments
     * @author Harley Hong
     * @created 2017 /08/09 16:14:44
     */
    public static void main(String[] args) {
        DocFmtConvert dfc = new DocFmtConvert();
        String srcDocPath = "";
        String descDocPath = "";
        try {
            dfc.convertDocFmt(srcDocPath, descDocPath, DOC_FMT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式大全:前缀对应以下方法的fmt值
     * 0:Microsoft Word 97 - 2003 文档 (.doc)
     * 1:Microsoft Word 97 - 2003 模板 (.dot)
     * 2:文本文档 (.txt)
     * 3:文本文档 (.txt)
     * 4:文本文档 (.txt)
     * 5:文本文档 (.txt)
     * 6:RTF 格式 (.rtf)
     * 7:文本文档 (.txt)
     * 8:HTML 文档 (.htm)(带文件夹)
     * 9:MHTML 文档 (.mht)(单文件)
     * 10:MHTML 文档 (.mht)(单文件)
     * 11:XML 文档 (.xml)
     * 12:Microsoft Word 文档 (.docx)
     * 13:Microsoft Word 启用宏的文档 (.docm)
     * 14:Microsoft Word 模板 (.dotx)
     * 15:Microsoft Word 启用宏的模板 (.dotm)
     * 16:Microsoft Word 文档 (.docx)
     * 17:PDF 文件 (.pdf)
     * 18:XPS 文档 (.xps)
     * 19:XML 文档 (.xml)
     * 20:XML 文档 (.xml)
     * 21:XML 文档 (.xml)
     * 22:XML 文档 (.xml)
     * 23:OpenDocument 文本 (.odt)
     * 24:WTF 文件 (.wtf)
     * <p>
     * <p>
     * 根据格式类型转换doc文件
     *
     * @param srcPath  doc path 源文件
     * @param descPath the docx path 目标文件
     * @param fmt      fmt 所转格式
     * @return the file
     * @throws Exception the exception
     * @author Harley Hong
     * @created 2017 /08/09 16:14:07 Convert docx 2 doc file.
     */
    public File convertDocFmt(String srcPath, String descPath, int fmt) throws Exception {

        // 实例化ComThread线程与ActiveXComponent
        ComThread.InitSTA();

        ActiveXComponent app = new ActiveXComponent("Word.Application");

        try {

            // 文档隐藏时进行应用操作
            app.setProperty("Visible", new Variant(false));

            // 实例化模板Document对象
            Dispatch document = app.getProperty("Documents").toDispatch();

            // 打开Document进行另存为操作
            Dispatch doc = Dispatch.invoke(document, "Open", Dispatch.Method,
                    new Object[]{srcPath, new Variant(true), new Variant(true)}, new int[1]).toDispatch();

            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[]{descPath, new Variant(fmt)}, new int[1]);

            Dispatch.call(doc, "Close", new Variant(false));

            return new File(descPath);
        } catch (Exception e) {
            throw e;
        } finally {
            // 释放线程与ActiveXComponent
            app.invoke("Quit", new Variant[]{});
            ComThread.Release();
        }
    }
}