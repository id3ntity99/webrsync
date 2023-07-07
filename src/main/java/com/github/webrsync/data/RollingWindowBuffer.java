package com.github.webrsync.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

@Deprecated
public class RollingWindowBuffer {
    private int offset = 0;
    private final byte[] buffer;
    private int windowSize = 512;
    /**
     * Total number of the unread bytes in this buffer.
     */
    private int available;
    /**
     * Index of offset limitation. <br>
     * This value is calculated by {@code buffer.length - (buffer.length % windowSize - 1)}. <br>
     * That means the right next index of last block's starting index
     */
    private final int offsetLimit;
    private static final Logger LOGGER = LoggerFactory.getLogger(RollingWindowBuffer.class);

    public RollingWindowBuffer(InputStream in) throws IOException {
        this.buffer = in.readAllBytes();
        this.available = buffer.length - offset;
        this.offsetLimit = determineLimit();
        in.close();
    }

    public RollingWindowBuffer(InputStream in, int windowSize) throws IOException {
        this.buffer = in.readAllBytes();
        this.windowSize = windowSize;
        this.offsetLimit = determineLimit();
        in.close();
    }

    private int determineLimit() {
        Objects.requireNonNull(buffer);
        assert windowSize != 0;
        if (buffer.length % windowSize == 0)
            return buffer.length - windowSize;
        else
            return buffer.length - (buffer.length % windowSize);

    }

    private boolean underLimit() {
        return offset < offsetLimit;
    }

    /**
     * Increment offset by 1.
     */
    public void increment() {
        if (underLimit()) {
            offset++;
            available = buffer.length - offset;
        } else {
            throw new OffsetLimitException(offset, offsetLimit);
        }
    }

    /**
     * Move current offset to the starting index of the next block.
     * That means if the size is 512, and current offset is 0, calling this method will move the offset to 512.
     */
    public void nextBlock() {
        if (underLimit()) {
            offset += windowSize;
            available = buffer.length - offset;
        } else {
            throw new OffsetLimitException(offset, offsetLimit);
        }
    }

    /**
     * Get current offset.
     *
     * @return Current offset of this buffer.
     */
    public int offset() {
        return offset;
    }

    /**
     * Get limitation of offset. The limitation is calculated by subtracting size from the total buffer length.
     * For example, if the buffer length is 1024 and {@link RollingWindowBuffer#windowSize} is 512, then the offset limit is
     * 512
     *
     * @return
     */
    public int limit() {
        return offsetLimit;
    }

    /**
     * Get bytes from the current window. For example, if current offset is 0 and the size of
     * the window is 512 then invocation of this method will return the bytes from index 0 to 511.
     *
     * @return
     */
    public byte[] subBytes() {
        /*
        ##### 다음 커밋까지 본문을 삭제하지 말 것. #####
        아래의 Arrays.copyOfRange는 항상 길이가 512인 바이트 어레이를 만들어낸다. Rsync의 체크섬 알고리즘은 아들러-32에 기반하므로 어레이의 길이가 체크섬의 결과값에 영향을 미친다.
        예를들어 512바이트의 B값은 512+ 512*x1 + 512*x2 + ... + 1*x512가 되지만 길이가 13바이트의 B값은 13 + 13*x1 + ... + 1*x13이 된다.
        설령 모든 xn값이 0이라 하더라도, 위의 수식에서의 상수항 값이 다르므로 최종적인 아들러-32 체크섬의 값도 달라질 수 밖에 없다.
        따라서 뽑아낼 subBytes의 전체 길이가 512보다 작으면 -- 예를들어 그 크기가 13바이트라면 13바이트만 뽑아내야 한다.
        만약 전체 파일의 크기가 513바이트라면 512바이트-블럭의 최종 개수는 2개이며 하나는 512바이트, 나머지 하나는 1바이트가 되어야 한다는 것이다.

        또한, Arrays.copyOfRange(byte[] original, int from, int to) 메서드는 original byte array에서 [from, to) 구간만을 선택하여 반환한다.

        ##### 기존 코드 #####
            byte[] retrieved = Arrays.copyOfRange(buffer, offset, offset + size - 1);
            LOGGER.info("Retrieving bytes from {} to {}.", offset, offset + size - 1);
            System.out.println(Arrays.toString(retrieved));
            return retrieved;
        */
        if (available < windowSize) {
            int endIndex = offset + available;
            LOGGER.debug("Getting bytes from sub-range [{}, {})", offset, endIndex);
            return Arrays.copyOfRange(buffer, offset, endIndex);
        } else {
            LOGGER.debug("Getting bytes from sub-range [{}, {})", offset, offset + windowSize);
            return Arrays.copyOfRange(buffer, offset, offset + windowSize);
        }
    }
}
