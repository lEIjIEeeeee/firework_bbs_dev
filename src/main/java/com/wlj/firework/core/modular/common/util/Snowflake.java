package com.wlj.firework.core.modular.common.util;

import cn.hutool.core.date.SystemClock;
import cn.hutool.core.util.StrUtil;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Snowflake {
    private static final long serialVersionUID = 1L;

    private final long twepoch;
    /**
     * 机器 id 10位
     */
    private static final long workerIdBits = 10L;
    @SuppressWarnings("FieldCanBeLocal")
    private static final long maxWorkerId = ~(-1L << workerIdBits);
    /**
     * 序列号12位
     */
    private static final long sequenceBits = 12L;
    private static final long workerIdShift = sequenceBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits;
    /**
     * 序列掩码，用于限定序列最大值不能超过4095
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final long sequenceMask = ~(-1L << sequenceBits);

    private final long workerId;
    private final boolean useSystemClock;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String SHUFFLE_CHARS = "KJ2ACj7lZGdU4hqpPIFMNyLYxvBkTDbo5VmS3iRWHgnte16a0s9zcufwXEOQ8r";

    /**
     * 构造
     *
     * @param workerId 终端ID
     */
    public Snowflake(long workerId) {
        this(workerId, false);
    }

    /**
     * 构造
     *
     * @param workerId         终端ID
     * @param isUseSystemClock 是否使用{@link SystemClock} 获取当前时间戳
     */
    public Snowflake(long workerId, boolean isUseSystemClock) {
        this(null, workerId, isUseSystemClock);
    }

    /**
     * @param epochDate        初始化时间起点（null表示默认起始日期）,后期修改会导致id重复,如果要修改连workerId dataCenterId，慎用
     * @param workerId         工作机器节点id
     * @param isUseSystemClock 是否使用{@link SystemClock} 获取当前时间戳
     * @since 5.1.3
     */
    public Snowflake(Date epochDate, long workerId, boolean isUseSystemClock) {
        if (null != epochDate) {
            this.twepoch = epochDate.getTime();
        } else {
            // Thu, 04 Nov 2010 01:42:54 GMT
            this.twepoch = 1288834974657L;
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(StrUtil.format("worker Id can't be greater than {} or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
        this.useSystemClock = isUseSystemClock;
    }

    /**
     * 根据Snowflake的ID，获取机器id
     *
     * @param id snowflake算法生成的id
     * @return 所属机器的id
     */
    public long getWorkerId(long id) {
        return id >> workerIdShift & ~(-1L << workerIdBits);
    }

    /**
     * 根据Snowflake的ID，获取生成时间
     *
     * @param id snowflake算法生成的id
     * @return 生成的时间
     */
    public long getGenerateDateTime(long id) {
        return (id >> timestampLeftShift & ~(-1L << 41L)) + twepoch;
    }

    /**
     * 下一个ID
     *
     * @return ID
     */
    public synchronized long nextId() {
        long timestamp = genTime();
        if (timestamp < this.lastTimestamp) {
            if (this.lastTimestamp - timestamp < 2000) {
                // 容忍2秒内的回拨，避免NTP校时造成的异常
                timestamp = lastTimestamp;
            } else {
                // 如果服务器时间有问题(时钟后退) 报错。
                throw new IllegalStateException(StrUtil.format("Clock moved backwards. Refusing to generate id for {}ms", lastTimestamp - timestamp));
            }
        }

        if (timestamp == this.lastTimestamp) {
            final long seq = (this.sequence + 1) & sequenceMask;
            if (seq == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
            this.sequence = seq;
        } else {
            // 序列号起始值随机
            sequence = ThreadLocalRandom.current().nextInt(100);
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
    }

    /**
     * 下一个ID（字符串形式）
     *
     * @return ID 字符串形式
     */
    public String nextIdStr() {
        return Long.toString(nextId());
    }

    public String nextIdEncode() {
        return nextIdEncode(false);
    }

    public String nextIdEncode(boolean isShuffle) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        long num = nextId();
        String chars = isShuffle ? SHUFFLE_CHARS : CHARS;
        int scale = chars.length();

        while (num > 0) {
            // 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
            remainder = Math.toIntExact(num % scale);
            sb.append(chars.charAt(remainder));

            num = num / scale;
        }
        return sb.reverse().toString();
    }

    public String nextIdEncode(int length) {
        return nextIdEncode(length, false);
    }

    public String nextIdEncode(int length, boolean isShuffle) {
        if (length < 11) {
            throw new IllegalArgumentException("length can`t less than 11");
        }
        String idEncode = nextIdEncode(isShuffle);
        String randomString = getRandomString(length - idEncode.length());
        return randomString + idEncode;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------ Private method start

    /**
     * 循环等待下一个时间
     *
     * @param lastTimestamp 上次记录的时间
     * @return 下一个时间
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = genTime();
        // 循环直到操作系统时间戳变化
        while (timestamp == lastTimestamp) {
            timestamp = genTime();
        }
        if (timestamp < lastTimestamp) {
            // 如果发现新的时间戳比上次记录的时间戳数值小，说明操作系统时间发生了倒退，报错
            throw new IllegalStateException(
                    StrUtil.format("Clock moved backwards. Refusing to generate id for {}ms", lastTimestamp - timestamp));
        }
        return timestamp;
    }

    /**
     * 生成时间戳
     *
     * @return 时间戳
     */
    private long genTime() {
        return this.useSystemClock ? SystemClock.now() : System.currentTimeMillis();
    }

    private String getRandomString(int len) {
        if (len <= 0) {
            return StrUtil.EMPTY;
        }
        int scale = SHUFFLE_CHARS.length();
        StringBuilder stringBuilder = new StringBuilder(len);
        while (len-- > 0) {
            stringBuilder.append(SHUFFLE_CHARS.charAt(ThreadLocalRandom.current().nextInt(scale)));
        }
        return stringBuilder.toString();
    }
    // ------------------------------------------------------------------------------------------------------------------------------------ Private method end
}
