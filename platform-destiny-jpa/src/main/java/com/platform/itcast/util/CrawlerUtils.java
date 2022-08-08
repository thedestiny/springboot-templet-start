package com.platform.itcast.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.platform.itcast.pojo.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrawlerUtils {

    private static final ObjectMapper MAPPER =  new ObjectMapper();


    private static  Snowflake flake;

    static {
        flake = new Snowflake(0,1);
    }


    public static void main(String[] args) {

        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&cid2=653&cid3=655&s=113&click=0&page=";

       //  handleMain(url);

        saveImage("https://img14.360buyimg.com/n0/jfs/t1/137220/19/22769/33983/6231aef3E11b2bdb8/137510330b385962.jpg");
    }


    public static List<String> handleMain(String url) {
        HttpResponse response = HttpUtil.createGet(url).execute();
        String html = response.body();
        Document doc = Jsoup.parse(html);

        Elements attributes = doc.select("li[data-sku]");
        List<String> dataList = new ArrayList<>();
        for (Element node : attributes) {
            // String skuId = node.attr("data-sku");
            String spuId = node.attr("data-spu");
            // 100016931023
            // System.out.println(" node spuId "  + spuId);
            String detail = "https://item.jd.com/" + spuId + ".html";
            dataList.add(detail);
        }

        return dataList;

    }

    public static List<Item> handleDetail(String detail){
        //  String detail = "https://item.jd.com/" + spuId + ".html";
        String spuId = detail.replace("https://item.jd.com/","").replace(".html","");
        String detailBody = HttpUtil.createGet(detail).execute().body();
        Document detailHtml = Jsoup.parse(detailBody);
        Elements skuEles = detailHtml.select("div[data-sku]");

        List<Item> dataList = new ArrayList<>();

        for (Element ele : skuEles) {

            try {
                String dataSkuId = ele.select("[data-sku]").attr("data-sku");
                // System.out.println(" dataSkuId "  +  dataSkuId);
                //根据sku查询商品数据
                Item item = new Item();

                //获取商品的详情的url
                String itemUrl = "https://item.jd.com/" + dataSkuId + ".html";
                // url地址 sku spu
                item.setUrl(itemUrl);
                item.setSku(Long.parseLong(dataSkuId));
                item.setSpu(Long.parseLong(spuId));

                String dd = HttpUtil.createGet(itemUrl).execute().body();
                Document docNode = Jsoup.parse(dd);

                String skuName = docNode.select("div.sku-name").get(0).text();
                item.setTitle(skuName);
                String body = HttpUtil.createGet("https://p.3.cn/prices/mgets?skuIds=J_" + dataSkuId).execute().body();
                try {
                    double price = MAPPER.readTree(body).get(0).get("p").asDouble();
                    // 价格和商品名称
                    item.setPrice(price);
                }catch (Exception e){
                    item.setPrice(0d);
                }
                // 图片名称

                String attr = docNode.getElementById("spec-img").attr("data-origin");
                String uri = "https://img14.360buyimg.com/n0/jfs" + attr.split("_jfs")[1];
                // String picName = httpUtil.doGetImage(picUrl);
                String s = saveImage(uri);
                item.setPic(s);
                item.setCreated(new Date());
                item.setUpdated(item.getCreated());
                // System.out.println("item :: " + item);
                dataList.add(item);
            }catch (Exception e){}



        }

        return dataList;

    }

    public static String saveImage(String uri){

        HttpResponse execute = HttpUtil.createGet(uri).execute();
        //获取图片的后缀
        String extName = uri.substring(uri.lastIndexOf("."));

        //创建图片名，重命名图片
        String picName = flake.nextIdStr() + extName;

        //下载图片
        //声明OutPutStream
        String property = System.getProperty("user.dir");
        File file = new File(property + File.separatorChar + "upload" + File.separatorChar + picName);

        FileUtil.writeFromStream(execute.bodyStream() ,file);
        return picName;

    }


}
