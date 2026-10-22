/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.inventoryai.workbench;

import static cn.zhuatech.inventoryai.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "items");
    Set<String> ids = new HashSet<>();
    List<Map<String, Object>> items = new ArrayList<>();
    BigDecimal total = BigDecimal.ZERO;
    for (var i : rows(r, "items", 1, 500)) {
      keys(i, "id", "annualQuantity", "unitCost");
      String id = id(i, "id");
      unique(ids, id);
      BigDecimal v = decimal(i, "annualQuantity", 0, 1e9).multiply(decimal(i, "unitCost", 0, 1e9));
      total = total.add(v);
      items.add(Map.of("id", id, "annualValue", v));
    }
    items.sort(
        Comparator.<Map<String, Object>, BigDecimal>comparing(
                i -> (BigDecimal) i.get("annualValue"))
            .reversed()
            .thenComparing(i -> i.get("id").toString()));
    BigDecimal cumulative = BigDecimal.ZERO;
    List<Map<String, Object>> out = new ArrayList<>();
    for (var item : items) {
      BigDecimal value = (BigDecimal) item.get("annualValue");
      String tier =
          total.signum() == 0 || value.signum() == 0
              ? "C"
              : cumulative.compareTo(total.multiply(new BigDecimal("0.8"))) < 0
                  ? "A"
                  : cumulative.compareTo(total.multiply(new BigDecimal("0.95"))) < 0 ? "B" : "C";
      cumulative = cumulative.add(value);
      out.add(
          Map.of(
              "id",
              item.get("id"),
              "annualValue",
              value,
              "class",
              tier,
              "cumulativePercent",
              total.signum() == 0
                  ? BigDecimal.ZERO
                  : cumulative
                      .multiply(new BigDecimal("100"))
                      .divide(total, 6, RoundingMode.HALF_UP)
                      .stripTrailingZeros()));
    }
    return Map.of(
        "annualValueTotal", total, "items", out, "aThresholdPercent", 80, "bThresholdPercent", 95);
  }
}
