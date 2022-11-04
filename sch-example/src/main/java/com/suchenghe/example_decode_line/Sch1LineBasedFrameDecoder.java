package com.suchenghe.example_decode_line;

import io.netty.handler.codec.LineBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2018/8/3 13:50
 */
public class Sch1LineBasedFrameDecoder extends LineBasedFrameDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sch1LineBasedFrameDecoder.class);

    public Sch1LineBasedFrameDecoder(int maxLength) {
        super(maxLength);
    }

    public Sch1LineBasedFrameDecoder(int maxLength, boolean stripDelimiter, boolean failFast) {
        super(maxLength, stripDelimiter, failFast);
    }
}
