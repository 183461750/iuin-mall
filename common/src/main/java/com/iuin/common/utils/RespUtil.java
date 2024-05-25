package com.iuin.common.utils;

import cn.hutool.core.collection.CollUtil;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.common.model.exceptions.BusinessException;
import com.iuin.common.model.resp.PageDataResp;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Resp工具类
 *
 * @author Fa
 */
public final class RespUtil<T> {
    private static final RespUtil<?> EMPTY = new RespUtil<>();

    private final RespResult<T> value;

    private RespUtil() {
        this.value = null;
    }

    private RespUtil(RespResult<T> value) {
        this.value = value;
    }

    private static <T> RespUtil<T> empty() {
        @SuppressWarnings("unchecked")
        RespUtil<T> t = (RespUtil<T>) EMPTY;
        return t;
    }

    public static <T> RespUtil<T> ofNullable(RespResult<T> value) {
        return value == null ? empty() : new RespUtil<>(value);
    }

    public RespResult<T> getWrapper() {
        return value;
    }

    public T getData() {
        return baseGetData(value, () -> null);
    }

    public T getData(T other) {
        return baseGetData(value, () -> other);
    }

    public T getData(Supplier<? extends T> other) {
        Objects.requireNonNull(other, SUPPLIER_IS_NULL);
        return baseGetData(value, other);
    }

    public RespUtil<T> tryThrowWhenFail() {
        baseTryThrow(value, null, w -> isOk(w) ? true : null);
        return this;
    }

    public RespUtil<T> tryThrowWhenFail(ResponseCodeEnum responseCodeEnum) {
        baseTryThrow(value, responseCodeEnum, w -> isOk(w) ? true : null);
        return this;
    }

    public RespUtil<T> tryThrowWhenFailAndLog(ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        baseTryThrowAndLog(value, responseCodeEnum, log, template, params, RespUtil::isOk);
        return this;
    }

    public RespUtil<T> tryThrowWhenDataIsNull() {
        baseTryThrow(value, null, w -> checkData(w) ? true : null);
        return this;
    }

    public RespUtil<T> tryThrowWhenDataIsNull(ResponseCodeEnum responseCodeEnum) {
        baseTryThrow(value, responseCodeEnum, w -> checkData(w) ? true : null);
        return this;
    }

    public RespUtil<T> tryThrowWhenDataIsNullAndLog(ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        baseTryThrowAndLog(value, responseCodeEnum, log, template, params, RespUtil::checkData);
        return this;
    }

    @SuppressWarnings("unchecked")
    public RespUtil<T> tryThrowWhenDataIsEmpty() {
        baseTryThrow(null, Collection.class, (v) -> checkCollectionData((RespResult<? extends Collection<T>>) v));
        return this;
    }

    @SuppressWarnings("unchecked")
    public RespUtil<T> tryThrowWhenDataIsEmpty(ResponseCodeEnum responseCodeEnum) {
        baseTryThrow(responseCodeEnum, Collection.class, (v) -> checkCollectionData((RespResult<? extends Collection<T>>) v));
        return this;
    }

    @SuppressWarnings("unchecked")
    public RespUtil<T> tryThrowWhenDataIsEmptyAndLog(ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        baseTryThrowAndLog(value, responseCodeEnum, log, template, params, Collection.class, (v) -> checkCollectionData((RespResult<? extends Collection<T>>) v));
        return this;
    }

    @SuppressWarnings("unchecked")
    public RespUtil<T> tryThrowWhenPageDataIsEmpty() {
        baseTryThrow(null, PageDataResp.class, (v) -> checkPageData((RespResult<? extends PageDataResp<T>>) v));
        return this;
    }

    @SuppressWarnings("unchecked")
    public RespUtil<T> tryThrowWhenPageDataIsEmpty(ResponseCodeEnum responseCodeEnum) {
        baseTryThrow(responseCodeEnum, PageDataResp.class, (v) -> checkPageData((RespResult<? extends PageDataResp<T>>) v));
        return this;
    }

    @SuppressWarnings("unchecked")
    public RespUtil<T> tryThrowWhenPageDataIsEmptyAndLog(ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        baseTryThrowAndLog(value, responseCodeEnum, log, template, params, PageDataResp.class, (v) -> checkPageData((RespResult<? extends PageDataResp<T>>) v));
        return this;
    }

    public <U> RespUtil<U> transferData(Function<? super RespResult<T>, ? extends U> mapper) {
        Objects.requireNonNull(mapper, FUNCTION_IS_NULL);
        if (!isOk(value))
            return empty();
        else {
            return RespUtil.ofNullable(success(mapper.apply(value)));
        }
    }

    //------------------------------------ 上面是链式调用api，下面是基础方法api ------------------------------------

    public static <T> RespResult<T> success() {
        ResponseCodeEnum responseCodeEnum = ResponseCodeEnum.SUCCESS;
        return new RespResult<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> success(T data) {
        ResponseCodeEnum responseCodeEnum = ResponseCodeEnum.SUCCESS;
        return new RespResult<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), data, System.currentTimeMillis());
    }

    public static <K> RespResult<PageDataResp<K>> success(List<K> data, Long totalCount) {
        ResponseCodeEnum responseCodeEnum = ResponseCodeEnum.SUCCESS;
        PageDataResp<K> pageDataResp = new PageDataResp<>(totalCount, data);
        return new RespResult<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), pageDataResp, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(ResponseCodeEnum responseCodeEnum) {
        return new RespResult<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> failService(String errorMessage) {
        return new RespResult<>(ResponseCodeEnum.SERVICE_ERROR.getCode(), errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(String errorMessage) {
        return new RespResult<>(ResponseCodeEnum.BUSINESS_ERROR.getCode(), errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(int responseCode, String errorMessage) {
        return new RespResult<>(responseCode, errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(int responseCode) {
        return new RespResult<>(responseCode, null, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(RespResult<?> wrapperResp) {
        return new RespResult<>(wrapperResp.getCode(), wrapperResp.getMessage(), null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(ResponseCodeEnum responseCodeEnum, String errorMessage) {
        return new RespResult<>(responseCodeEnum.getCode(), errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(ResponseCodeEnum responseCodeEnum, T data) {
        return new RespResult<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), data, System.currentTimeMillis());
    }

    public static boolean isOk(Integer code) {
        return Objects.equals(code, ResponseCodeEnum.SUCCESS.getCode());
    }

    public static boolean isOk(RespResult<?> wrapperResp) {
        return Optional.ofNullable(wrapperResp).map((w) -> isOk(w.getCode())).orElse(false);
    }

    public static boolean isFail(Integer code) {
        return !Objects.equals(code, ResponseCodeEnum.SUCCESS.getCode());
    }

    public static boolean isFail(RespResult<?> wrapperResp) {
        return Optional.ofNullable(wrapperResp).map((w) -> isFail(w.getCode())).orElse(false);
    }

    public static boolean checkData(RespResult<?> wrapperResp) {
        return Optional.ofNullable(wrapperResp).map((w) -> isOk(w.getCode()) && Objects.nonNull(w.getData())).orElse(false);
    }

    public static boolean checkCollectionData(RespResult<? extends Collection<?>> wrapperResp) {
        return Optional.ofNullable(wrapperResp).map((w) -> isOk(w.getCode()) && !CollUtil.isEmpty(w.getData())).orElse(false);
    }

    public static boolean checkPageData(RespResult<? extends PageDataResp<?>> wrapperResp) {
        return Optional.ofNullable(wrapperResp).map((w) -> Objects.nonNull(w.getData()) && !CollUtil.isEmpty(w.getData().getData())).orElse(false);
    }

    public static <T> T getData(RespResult<T> wrapperResp) {
        return baseGetData(wrapperResp, () -> null);
    }

    public static <T> T getData(RespResult<T> wrapperResp, Supplier<? extends T> other) {
        Objects.requireNonNull(other, SUPPLIER_IS_NULL);
        return baseGetData(wrapperResp, other);
    }

    public static boolean throwWhenFail(RespResult<?> wrapperResp) {
        return baseTryThrow(wrapperResp, null, w -> isOk(w) ? true : null);
    }

    public static boolean throwWhenFail(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum) {
        return baseTryThrow(wrapperResp, responseCodeEnum, w -> isOk(w) ? true : null);
    }

    public static boolean throwWhenFailAndLog(RespResult<?> wrapperResp, Logger log) {
        return baseTryThrowAndLog(wrapperResp, log, RespUtil::isOk);
    }

    public static boolean throwWhenFailAndLog(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        return baseTryThrowAndLog(wrapperResp, responseCodeEnum, log, template, params, RespUtil::isOk);
    }

    public static boolean throwWhenDataIsNull(RespResult<?> wrapperResp) {
        return baseTryThrow(wrapperResp, null, w -> checkData(w) ? true : null);
    }

    public static boolean throwWhenDataIsNull(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum) {
        return baseTryThrow(wrapperResp, responseCodeEnum, w -> checkData(w) ? true : null);
    }

    public static boolean throwWhenDataIsNullAndLog(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        return baseTryThrowAndLog(wrapperResp, responseCodeEnum, log, template, params, RespUtil::checkData);
    }

    @SuppressWarnings("unchecked")
    public static boolean throwWhenDataIsEmpty(RespResult<? extends Collection<?>> wrapperResp) {
        return baseTryThrow(wrapperResp, null, w -> checkCollectionData((RespResult<? extends Collection<?>>) w) ? true : null);
    }

    @SuppressWarnings("unchecked")
    public static boolean throwWhenDataIsEmpty(RespResult<? extends Collection<?>> wrapperResp, ResponseCodeEnum responseCodeEnum) {
        return baseTryThrow(wrapperResp, responseCodeEnum, w -> checkCollectionData((RespResult<? extends Collection<?>>) w) ? true : null);
    }

    @SuppressWarnings("unchecked")
    public static boolean throwWhenDataIsEmptyAndLog(RespResult<? extends Collection<?>> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        return baseTryThrowAndLog(wrapperResp, responseCodeEnum, log, template, params, (v) -> checkCollectionData((RespResult<? extends Collection<?>>) v));
    }

    @SuppressWarnings("unchecked")
    public static boolean throwWhenPageDataIsEmpty(RespResult<? extends PageDataResp<?>> wrapperResp) {
        return baseTryThrow(wrapperResp, null, w -> checkPageData((RespResult<? extends PageDataResp<?>>) w) ? true : null);
    }

    @SuppressWarnings("unchecked")
    public static boolean throwWhenPageDataIsEmpty(RespResult<? extends PageDataResp<?>> wrapperResp, ResponseCodeEnum responseCodeEnum) {
        return baseTryThrow(wrapperResp, responseCodeEnum, w -> checkPageData((RespResult<? extends PageDataResp<?>>) w) ? true : null);
    }

    @SuppressWarnings("unchecked")
    public static boolean throwWhenPageDataIsEmptyAndLog(RespResult<? extends PageDataResp<?>> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        return baseTryThrowAndLog(wrapperResp, responseCodeEnum, log, template, params, (v) -> checkPageData((RespResult<? extends PageDataResp<?>>) v));
    }

    public static <T> T getDataOrThrow(RespResult<T> wrapperResp, ResponseCodeEnum responseCodeEnum) {
        return baseGetDataOrThrow(wrapperResp, responseCodeEnum, RespUtil::checkData);
    }

    public static <T> T getDataOrThrow(RespResult<T> wrapperResp) {
        return baseGetDataOrThrow(wrapperResp, null, RespUtil::checkData);
    }

    public static <T> T getDataOrThrowAndLog(RespResult<T> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        return baseGetDataOrThrowAndLog(wrapperResp, responseCodeEnum, log, template, params, RespUtil::checkData);
    }

    public static <T> Collection<T> getCollectionDataOrThrow(RespResult<? extends Collection<T>> wrapperResp) {
        return baseGetDataOrThrow(wrapperResp, null, RespUtil::checkCollectionData);
    }

    public static <T> Collection<T> getCollectionDataOrThrow(RespResult<? extends Collection<T>> wrapperResp, ResponseCodeEnum responseCodeEnum) {
        return baseGetDataOrThrow(wrapperResp, responseCodeEnum, RespUtil::checkCollectionData);
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> getCollectionDataOrThrowAndLog(RespResult<? extends Collection<T>> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        return baseGetDataOrThrowAndLog(wrapperResp, responseCodeEnum, log, template, params, (v) -> checkCollectionData((RespResult<? extends Collection<?>>) v));
    }

    public static <T> PageDataResp<T> getPageDataOrThrow(RespResult<? extends PageDataResp<T>> wrapperResp) {
        return baseGetDataOrThrow(wrapperResp, null, RespUtil::checkPageData);
    }

    public static <T> PageDataResp<T> getPageDataOrThrow(RespResult<? extends PageDataResp<T>> wrapperResp, ResponseCodeEnum responseCodeEnum) {
        return baseGetDataOrThrow(wrapperResp, responseCodeEnum, RespUtil::checkPageData);
    }

    @SuppressWarnings("unchecked")
    public static <T> PageDataResp<T> getPageDataOrThrowAndLog(RespResult<? extends PageDataResp<T>> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        return baseGetDataOrThrowAndLog(wrapperResp, responseCodeEnum, log, template, params, (v) -> checkPageData((RespResult<? extends PageDataResp<?>>) v));
    }

    public static String safeGetMessage(RespResult<?> wrapperResp) {
        return Optional.ofNullable(wrapperResp).map(RespResult::getMessage).orElse(ResponseCodeEnum.BUSINESS_ERROR.getMessage());
    }

    public static String safeGetMessage(RespResult<?> wrapperResp, Supplier<? extends String> other) {
        Objects.requireNonNull(other, SUPPLIER_IS_NULL);
        return Optional.ofNullable(wrapperResp).map(RespResult::getMessage).orElseGet(other);
    }

    //---------------------------------------- 上面是公有api，下面是私有api ----------------------------------------

    private static <T> T baseGetData(RespResult<T> wrapperResp, Supplier<? extends T> other) {
        return Optional.ofNullable(wrapperResp).map(RespResult::getData).orElseGet(other);
    }

    private static <T> T baseGetDataOrThrow(RespResult<T> wrapperResp, ResponseCodeEnum responseCodeEnum, Function<? super RespResult<T>, Boolean> checkFunction) {
        if (!checkFunction.apply(wrapperResp)) {
            throw getException(wrapperResp, responseCodeEnum).get();
        }
        return baseGetData(wrapperResp, () -> null);
    }

    private static boolean baseTryThrow(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum, Function<? super RespResult<?>, Boolean> checkFunction) {
        return Optional.ofNullable(wrapperResp).map(checkFunction).orElseThrow(getException(wrapperResp, responseCodeEnum));
    }

    private void baseTryThrow(ResponseCodeEnum responseCodeEnum, Class<?> clazz, Function<? super RespResult<T>, Boolean> checkFunction) {
        baseInstanceOfOrThrow(value, responseCodeEnum, clazz);
        if (!checkFunction.apply(value)) {
            throw getException(value, responseCodeEnum).get();
        }
    }

    private static boolean baseTryThrowAndLog(RespResult<?> wrapperResp, Logger log, Predicate<? super RespResult<?>> predicate) {
        quickThrowAndLog(wrapperResp, log, predicate);
        return true;
    }

    private static boolean baseTryThrowAndLog(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object[] params, Class<?> clazz, Predicate<? super RespResult<?>> predicate) {
        simpleCheck(log, template);
        baseInstanceOfOrThrow(wrapperResp, responseCodeEnum, clazz);
        quickThrowAndLog(wrapperResp, responseCodeEnum, log, template, params, predicate);
        return true;
    }

    private static boolean baseTryThrowAndLog(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object[] params, Predicate<? super RespResult<?>> predicate) {
        simpleCheck(log, template);
        quickThrowAndLog(wrapperResp, responseCodeEnum, log, template, params, predicate);
        return true;
    }

    private static <T> T baseGetDataOrThrowAndLog(RespResult<T> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object[] params, Predicate<? super RespResult<?>> predicate) {
        simpleCheck(log, template);
        quickThrowAndLog(wrapperResp, responseCodeEnum, log, template, params, predicate);
        return baseGetData(wrapperResp, () -> null);
    }

    private static void baseInstanceOfOrThrow(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum, Class<?> clazz) {
        if (!quickInstanceOf(wrapperResp, clazz)) {
            throw getException(wrapperResp, responseCodeEnum).get();
        }
    }

    private static void quickThrowAndLog(RespResult<?> wrapperResp, Logger log, Predicate<? super RespResult<?>> predicate) {
        Optional.ofNullable(wrapperResp).filter(predicate).orElseThrow(() -> getExceptionAndLog(wrapperResp, log));
    }

    private static void quickThrowAndLog(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object[] params, Predicate<? super RespResult<?>> predicate) {
        Optional.ofNullable(wrapperResp).filter(predicate).orElseThrow(() -> getExceptionAndLog(wrapperResp, responseCodeEnum, log, template, params));
    }

    private static boolean quickInstanceOf(RespResult<?> wrapperResp, Class<?> clazz) {
        return Optional.ofNullable(wrapperResp).map(RespResult::getData).map(Object::getClass).map(clazz::isAssignableFrom).orElse(false);
    }

    private static void simpleCheck(Logger log, String template) {
        Objects.requireNonNull(log, LOG_IS_NULL);
        Objects.requireNonNull(template, TEMPLATE_IS_NULL);
    }

    private static BusinessException getExceptionAndLog(RespResult<?> wrapperResp, Logger log) {
        String message = Objects.nonNull(wrapperResp) ? String.valueOf(wrapperResp.getCode()).concat(":").concat(wrapperResp.getMessage()) : String.valueOf(ResponseCodeEnum.BUSINESS_ERROR.getCode()).concat(":").concat(ResponseCodeEnum.BUSINESS_ERROR.getMessage());
        log.error(message);
        return getWrapperException(wrapperResp).get();
    }

    private static BusinessException getExceptionAndLog(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        log.error(template, params);
        return getException(wrapperResp, responseCodeEnum).get();
    }

    private static Supplier<BusinessException> getException(RespResult<?> wrapperResp, ResponseCodeEnum responseCodeEnum) {
        return Objects.nonNull(responseCodeEnum) ? getExceptionByResponseCode(responseCodeEnum) : getExceptionByWrapper(wrapperResp);
    }

    private static Supplier<BusinessException> getExceptionByResponseCode(ResponseCodeEnum responseCodeEnum) {
        return () -> new BusinessException(responseCodeEnum);
    }

    private static Supplier<BusinessException> getExceptionByWrapper(RespResult<?> wrapperResp) {
        return () -> Optional.ofNullable(wrapperResp).map(RespResult::getMessage).map(errorMsg -> new BusinessException(wrapperResp.getCode(), wrapperResp.getMessage())).orElseGet(getWrapperException(wrapperResp));
    }

    private static Supplier<BusinessException> getWrapperException(RespResult<?> wrapperResp) {
        return () -> new BusinessException(ResponseCodeEnum.getByCode(Optional.ofNullable(wrapperResp).map(RespResult::getCode).orElse(0)));
    }

    private static Supplier<BusinessException> getDefaultException() {
        return () -> new BusinessException(ResponseCodeEnum.getByCode(0));
    }

    private static final String WRAPPER_IS_NULL = "Wrapper对象为空";
    private static final String FUNCTION_IS_NULL = "Function函数为空";
    private static final String SUPPLIER_IS_NULL = "Supplier函数为空";
    private static final String LOG_IS_NULL = "Log对象为空";
    private static final String TEMPLATE_IS_NULL = "Template对象为空";
}