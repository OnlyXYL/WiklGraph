package top.wikl.wikljava.log.console;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * 日志目标源输出
 *
 * @author XYL
 * @title: MultiOutputStream
 * @description: TODO
 * @date 2020/4/7 15:35
 * @return
 * @since V1.0
 */
public class MultiOutputStream extends OutputStream {

    OutputStream outputStream1;
    OutputStream outputStream2;

    public MultiOutputStream(OutputStream stream1) {
        outputStream1 = stream1;
    }

    public MultiOutputStream(OutputStream stream1, OutputStream stream2) {
        outputStream1 = stream1;
        outputStream2 = stream2;
    }

    @Override
    public void write(int b) throws IOException {
        if (Objects.nonNull(outputStream1)) {
            outputStream1.write(b);
        }

        if (Objects.nonNull(outputStream2)) {
            outputStream2.write(b);
        }
    }
}
