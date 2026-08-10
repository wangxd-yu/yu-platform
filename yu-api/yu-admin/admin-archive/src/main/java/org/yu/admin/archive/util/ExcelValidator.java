package org.yu.admin.archive.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ExcelValidator {

    /**
     * 验证Excel文件的标题行是否符合预期
     *
     * @param in              Excel文件的输入流
     * @param expectedHeaders 预期的标题列表
     * @return 如果标题行符合预期，返回true；否则返回false
     */
    public static boolean validateHeaders(InputStream in, List<String> expectedHeaders) {
        try {
            HeaderCheckListener headerListener = new HeaderCheckListener(expectedHeaders);
            EasyExcel.read(in, headerListener).headRowNumber(1).sheet().doRead();
            return headerListener.isHeadersValid();
        } catch (Exception e) {
            log.error("读取 Excel 标题失败", e);
            return false;
        }
    }

    /**
     * 自定义监听器用于验证标题行
     */
    static class HeaderCheckListener extends AnalysisEventListener<Map<Integer, String>> {
        private final List<String> expectedHeaders;
        private boolean headersValid = true;

        public HeaderCheckListener(List<String> expectedHeaders) {
            this.expectedHeaders = expectedHeaders;
        }

        @Override
        public void invoke(Map<Integer, String> data, AnalysisContext context) {
            // 不处理数据行
        }

        @Override
        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
            List<String> headers = headMap.values().stream().collect(Collectors.toList());
            if (!headers.equals(expectedHeaders)) {
                headersValid = false;
                log.warn("标题行不匹配: {}", headers);
                throw new RuntimeException("Excel标题行不符合预期！");
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            // 不需要处理
        }

        public boolean isHeadersValid() {
            return headersValid;
        }
    }
}