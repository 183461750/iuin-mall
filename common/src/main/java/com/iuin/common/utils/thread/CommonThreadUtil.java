package com.iuin.common.utils.thread;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 通用线程池工具(默认推荐配置)
 * <p>后期可考虑接入动态线程池
 *
 * @author fa
 * @see <a href="https://github.com/dromara/dynamic-tp">轻量级动态线程池</a>
 * @see <a href="https://github.com/opengoofy/hippo4j">异步线程池框架</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonThreadUtil {

    /**
     * 阻塞系数, 标准核心线程数的推荐设置(IO密集型任务)
     * <p>核心线程数可以设置得较高，因为 I/O 操作会导致线程阻塞，增加线程数可以提高并发处理能力。
     * <p>通常，核心线程数可以设置为 CPU 核数的 2 到 4 倍。
     */
    public static final float BC_RECOMMEND_IO_CORE = 0.5f;

    /**
     * 阻塞系数, 标准最大线程数的推荐设置(IO密集型任务)
     * <p>设置过高的最大线程数可能会导致系统资源耗尽，而设置过低则可能无法充分利用系统资源。
     * <p>通常，最大线程数可以设置为核心线程数的 1.5 到 2 倍。
     */
    public static final float BC_RECOMMEND_IO_MAX = BC_RECOMMEND_IO_CORE + 0.25f;

    /**
     * 阻塞系数, 标准核心线程数的推荐设置(CPU密集型任务)
     * <p>核心线程数通常设置为 CPU 核数加上 1 或 2，以充分利用 CPU 资源，同时留出一些余地处理其他任务或系统开销。
     */
    public static final float BC_RECOMMEND_CPU_CORE = 0.1f;

    /**
     * 阻塞系数, 标准最大线程数的推荐设置(CPU密集型任务)
     * <p>最大线程数通常设置为核心线程数的 1.5 倍左右，以防止过多的线程导致性能下降。
     */
    public static final float BC_RECOMMEND_CPU_MAX = BC_RECOMMEND_CPU_CORE + 0.25f;

    /**
     * 任务队列容量, 标准推荐设置(IO密集型任务)
     * <p>推荐设置为 100 到 500，具体值需要根据实际任务量和系统性能进行调整。
     */
    public static final int RECOMMEND_IO_QUEUE_CAPACITY = 500;

    /**
     * 任务队列容量, 标准推荐设置(CPU密集型任务)
     * <p>推荐设置为 50 到 100，具体值需要根据实际任务量和系统性能进行调整。
     */
    public static final int RECOMMEND_CPU_QUEUE_CAPACITY = 100;

    /**
     * 自动计算线程池大小
     *
     * <p>线程数的推荐设置(点击@see看详细说明)
     * <p>假设任务为IO密集型 且 CPU 核数为 8 核，以下是一些推荐的核心线程数设置：
     * <p>最小值：8 核 * 2 = 16 即参数 {@param blockingCoefficient 设置为 0.5f}
     * <p>最大值：8 核 * 4 = 32 即参数 {@param blockingCoefficient 设置为 0.75f}
     * <p>具体设置可以根据实际应用的负载情况进行调整。可以通过监控系统的 CPU 使用率、线程利用率和响应时间来优化线程池的配置。
     * <p>任务队列容量：推荐设置为 100 到 500，具体值需要根据实际任务量和系统性能进行调整。
     *
     * @param blockingCoefficient 阻塞系数，阻塞因子介于0~1之间的数，阻塞因子越大，线程池中的线程数越多。
     * @return poolSize-最佳的线程数
     * @see CommonThreadUtil#BC_RECOMMEND_IO_CORE
     * @see CommonThreadUtil#BC_RECOMMEND_IO_MAX
     * @see CommonThreadUtil#BC_RECOMMEND_CPU_CORE
     * @see CommonThreadUtil#BC_RECOMMEND_CPU_MAX
     */
    public static int poolSizeByBlockingCoefficient(float blockingCoefficient) {
        if (blockingCoefficient >= 1 || blockingCoefficient < 0) {
            throw new IllegalArgumentException("[blockingCoefficient] must between 0 and 1, or equals 0.");
        }

        // 最佳的线程数 = CPU可用核心数 / (1 - 阻塞系数)
        return (int) (Runtime.getRuntime().availableProcessors() / (1 - blockingCoefficient));
    }

    /**
     * 核心线程数的推荐设置(IO密集型任务)
     * <p>(点击@see看详细说明)
     *
     * @see CommonThreadUtil#BC_RECOMMEND_IO_CORE
     */
    public static int recommendIOCorePoolSize() {
        return poolSizeByBlockingCoefficient(BC_RECOMMEND_IO_CORE);
    }

    /**
     * 最大线程数的推荐设置(IO密集型任务)
     * <p>(点击@see看详细说明)
     *
     * @see CommonThreadUtil#BC_RECOMMEND_IO_MAX
     */
    public static int recommendIOMaxPoolSize() {
        return poolSizeByBlockingCoefficient(BC_RECOMMEND_IO_MAX);
    }

    /**
     * 核心线程数的推荐设置(CPU密集型任务)
     * <p>(点击@see看详细说明)
     *
     * @see CommonThreadUtil#BC_RECOMMEND_CPU_CORE
     */
    public static int recommendCPUCorePoolSize() {
        return poolSizeByBlockingCoefficient(BC_RECOMMEND_CPU_CORE);
    }

    /**
     * 最大线程数的推荐设置(CPU密集型任务)
     * <p>(点击@see看详细说明)
     *
     * @see CommonThreadUtil#BC_RECOMMEND_CPU_MAX
     */
    public static int recommendCPUMaxPoolSize() {
        return poolSizeByBlockingCoefficient(BC_RECOMMEND_CPU_MAX);
    }

    /**
     * 创建线程池 -- CPU密集型任务
     */
    public static ThreadPoolExecutor threadPoolExecutorCPU(@Nonnull Class<?> clazz) {
        int poolSize = CommonThreadUtil.poolSizeByBlockingCoefficient(BC_RECOMMEND_CPU_CORE);
        return new ThreadPoolExecutor(
                poolSize, poolSize * 2, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(RECOMMEND_CPU_QUEUE_CAPACITY),
                // 这里使用类名区分类型
//                new CommonThreadFactory("CPU-" + clazz.getSimpleName()),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 创建线程池 -- IO密集型任务
     *
     * @param clazz 用于区分线程池类型 -- 直接使用调用该方法的类即可
     */
    public static ThreadPoolExecutor threadPoolExecutorIO(@Nonnull Class<?> clazz) {
        return threadPoolExecutorIO("IO-" + clazz.getSimpleName());
    }

    /**
     * 创建线程池 -- IO密集型任务
     */
    public static ThreadPoolExecutor threadPoolExecutorIO(@Nonnull String threadFactoryType) {
        int poolSize = CommonThreadUtil.poolSizeByBlockingCoefficient(BC_RECOMMEND_IO_CORE);
        return new ThreadPoolExecutor(
                poolSize, poolSize * 2, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(RECOMMEND_IO_QUEUE_CAPACITY),
                // 这里使用类名区分类型
//                new CommonThreadFactory(threadFactoryType),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

}
