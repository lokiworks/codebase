package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {

    static void bindRowTablePolicy(ConfigureBuilder builder, JSONObject o, String parentNodeName) {

        for (Map.Entry<String, Object> entry : o.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            String fullName;
            if (parentNodeName.isEmpty()) {
                fullName = name;
            } else {
                fullName = parentNodeName + "." + name;
            }
            if (value instanceof JSONArray) {
                builder.bind(fullName, new LoopRowTableRenderPolicy());
            } else {
                if (value instanceof JSONObject) {
                    bindRowTablePolicy(builder, (JSONObject) value, fullName);
                }
            }

        }
    }


    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException {

        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("item.goods", policy).build();

        final List<Goods> goodsList = new ArrayList<>();
        {
            Goods goods = Goods.builder().name("货物1").desc("货物1描述").count(1).discount(1).price(1).tax(1).totalPrice(1).build();
            goodsList.add(goods);
        }
        {
            Goods goods = Goods.builder().name("货物2").desc("货物2描述").count(2).discount(2).price(2).tax(2).totalPrice(2).build();
            goodsList.add(goods);
        }
        OrderItem item = new OrderItem();
        item.setGoods(goodsList);
        item.setId("123");

        Order order = Order.builder().item(item).customerName("张三").build();
        String s = JSON.toJSONString(order);
        System.out.println(s);
        JSONObject o = JSON.parseObject(s);

        ConfigureBuilder builder = Configure.builder();
        bindRowTablePolicy(builder, (JSONObject) o, "");


        String filePath = this.getClass().getResource("/title.docx").getPath();
        XWPFTemplate template = XWPFTemplate.compile(filePath, config).render(
                o);
        template.writeAndClose(new FileOutputStream("output.docx"));
    }
}
