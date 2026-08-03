package com.alibaba.otter.canal.client.adapter.es.core.support;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * ESSyncUtil 测试类
 * 
 * @author jianghang
 */
public class ESSyncUtilTest {

    @Test
    public void testScaledFloatPrecision() {
        // 测试高精度 decimal 转换为 scaled_float
        BigDecimal originalValue = new BigDecimal("1234567890.12345678901234567890");

        // 转换为 scaled_float
        Object result = ESSyncUtil.typeConvert(originalValue, "scaled_float");

        // 验证结果仍然是 BigDecimal，保持精度
        Assert.assertTrue("scaled_float 转换结果应该是 BigDecimal 类型", result instanceof BigDecimal);
        Assert.assertEquals("scaled_float 转换应该保持原始精度", originalValue, result);

        // 验证 toString 表示
        Assert.assertEquals("scaled_float 精度应该保持完整", "1234567890.12345678901234567890", result.toString());
    }

    @Test
    public void testScaledFloatFromString() {
        // 测试字符串转换为 scaled_float
        String originalString = "9876543210.98765432109876543210";

        // 转换为 scaled_float
        Object result = ESSyncUtil.typeConvert(originalString, "scaled_float");

        // 验证结果是 BigDecimal
        Assert.assertTrue("scaled_float 字符串转换结果应该是 BigDecimal 类型", result instanceof BigDecimal);

        // 验证值正确
        BigDecimal expected = new BigDecimal(originalString);
        Assert.assertEquals("scaled_float 字符串转换应该正确解析", expected, result);
    }

    @Test
    public void testScaledFloatFromDouble() {
        // 测试 double 转换为 scaled_float（注意：double 本身有精度限制）
        Double originalDouble = 1234567890.1234567890;

        // 转换为 scaled_float
        Object result = ESSyncUtil.typeConvert(originalDouble, "scaled_float");

        // 验证结果是 BigDecimal
        Assert.assertTrue("scaled_float double 转换结果应该是 BigDecimal 类型", result instanceof BigDecimal);

        // 验证值正确（由于 double 精度限制，这里验证转换过程正确）
        BigDecimal expected = new BigDecimal(originalDouble.toString());
        Assert.assertEquals("scaled_float double 转换应该正确处理", expected, result);
    }

    @Test
    public void testOtherFloatTypes() {
        // 测试其他浮点类型仍然正常工作
        BigDecimal value = new BigDecimal("123.45");

        // 测试 float 类型
        Object floatResult = ESSyncUtil.typeConvert(value, "float");
        Assert.assertTrue("float 转换结果应该是 Float 类型", floatResult instanceof Float);

        // 测试 half_float 类型
        Object halfFloatResult = ESSyncUtil.typeConvert(value, "half_float");
        Assert.assertTrue("half_float 转换结果应该是 Float 类型", halfFloatResult instanceof Float);
    }
}
