package com.platform.fund.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.platform.fund.entity.EtfList;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Date 2023-01-09 2:28 PM
 */

@Slf4j
public class EtfListModel {

    private static final String a_match = "(?<=\\>)(.+?)(?=\\</)";
    private static final String date_match = "[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";//"(?<=\\()(.+?)(?=\\))";
    private static final String money_match = "(?<=\\>:)(.*)(?=\\()";
    private static final String a_tag_match = "(?<=a\\>)(.*)";
    private static final String h_tag_match = "(?<=h3\\>)(.*)";
    private static final String div_tag_match = "(?<=\\>)(.*)(?=\\<)";


    public static List<EtfList> captureEtfList() throws Exception {

        String url = "http://fund.eastmoney.com/ETFN_jzzzl.html";
        Document document = Jsoup.connect(url).get();

        List<EtfList> rateList = new ArrayList<>();

        Elements bgcolors = document.getElementsByAttributeValue("bgcolor", "#F5FFFF");

        for (Element element : bgcolors) {

            Elements tds = element.getElementsByTag("td");
            // System.out.println("\n------------------------------------------\n");
            String code = tds.get(3).html();
            // 基金名称
            String name = tds.get(4).getElementsByTag("a").get(0).html();
            // 折价率
            String rate = tds.get(12).getElementsByTag("span").get(0).html();

            // 连接 http://fund.eastmoney.com/512170.html
            // 格式化基金变动信息
            EtfList etfRate = new EtfList(); //formatFoundRateInfo(code, name, rate);
            etfRate.setCode(code);
            etfRate.setName(name);
            rateList.add(etfRate);
        }
        return rateList;

    }

    /**
     * 抓取场内基金代码
     *
     * @return
     */
    public static List<String> crawlEtfCodeOnline() {

        String server = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=fb&ft=ct&gs=0&sc=clrq&st=desc&pi=1&pn=5000";
        List<String> codeList = Lists.newArrayList();

        try {

            HttpRequest request = HttpUtil.createGet(server);
            request.header("Referer", "http://fund.eastmoney.com/data/fbsfundranking.html");
            HttpResponse execute = request.execute();
            String body = execute.body();
            String trim = body.replace("var rankData =", "")
                    .replace(";", "").trim();
            JSONObject json = JSONObject.parseObject(trim);
            JSONArray datas = json.getJSONArray("datas");
            int len = datas.size();
            for (int i = 0; i < len; i++) {
                String node = datas.getString(i);
                String code = node.split(",")[0].replace("\"", "");
                codeList.add(code);
            }
            System.out.println(codeList);

            return codeList;
        } catch (Exception e) {
            log.error(" error {}", e.getMessage());
            return codeList;
        }

    }

    /**
     * 根据 code 查询 etf 变动信息
     */
    public static EtfList queryEtfFoundRateInfo(String code) {
        return formatFoundRateInfo(code, null, null);
    }

    public static void fillField(Object obj, String field1, Object value) {

        try {
            Class<?> clazz = obj.getClass();
            Field objField = clazz.getDeclaredField(field1);
            objField.setAccessible(true);
            objField.set(obj, value);
        } catch (Exception e) {
            log.error("fill error and e is {}", e.getMessage(), e);
        }
    }

    /**
     * 组装基金变动信息
     */
    private static EtfList formatFoundRateInfo(String code, String name, String rate) {
        EtfList etfRate = new EtfList();
        try {
            String href = "http://fund.eastmoney.com/" + code + ".html";
            JSONObject jsons = captureDetail(href);

            jsons.put("code", code);
            // jsons.put("name", name);
            jsons.put("href", href);
            // jsons.put("rate", rate);
            if (jsons.containsKey("msg") && jsons.get("msg").equals("-1")) {
                etfRate.setEndFlag("1");
                return etfRate;
            }

            formatEtfInfo(jsons, etfRate);
            // etf 变动信息填充夏普信息 标准差 信息比率
            fundSharpInfo(etfRate);

            return etfRate;
        } catch (Exception e) {
            log.error("code is {} error info is {}", code, e.getMessage(), e);
            etfRate.setCode(code);
            etfRate.setName(name);
            return etfRate;
        }
    }

    private static JSONObject captureDetail(String href) throws IOException {
        JSONObject result = new JSONObject(true);

        Document document = Jsoup.connect(href).get();
        Element infoOfFund = document.getElementsByClass("infoOfFund").get(0);
        Element title = document.getElementsByAttributeValue("style", "float: left").get(0);
        Elements funds = infoOfFund.getElementsByTag("td");
        // gz_gsz 净值        1.6290
        // gz_gszze 增长幅度  +0.0123
        // 变动比例 gz_gszzl  +0.76%
        String body = document.toString();
        if (body.contains("本基金已终止")) {
            result.put("msg", "-1");
            return result;
        }

        Element dd = null;
        try {

            dd = document.getElementsByClass("dataItem02").get(0);
        } catch (Exception e) {
            log.error("e is {}", e.getMessage());
        }

        // Element tmp = document.getElementsByClass("dataItem02").get(0).getElementsByClass("dataNums").get(0);
        String price = document.getElementById("gz_gsz").html();
        String priceChange = document.getElementById("gz_gszze").html();
        String rateChange = document.getElementById("gz_gszzl").html();
        try {
            Elements spans = dd.getElementsByClass("dataNums").get(0).getElementsByTag("span");
            if (StrUtil.isBlank(price) || "--".equalsIgnoreCase(price)) {
                price = spans.get(0).html();
            }
            if (StrUtil.isBlank(rateChange) || "--".equalsIgnoreCase(rateChange)) {
                rateChange = spans.get(1).html();
            }

        } catch (Exception e) {

        }

        String formatTime = matchStr(dd.html(), date_match);
        JSONObject base = acquireFoundInfo(funds);
        Elements stages = document.getElementById("increaseAmount_stage").getElementsByTag("tr");

        JSONObject stage = acquireFoundRateInfo(stages);

        // result.put("formatTime", updateDate);
        result.putAll(stage);
        result.putAll(base);
        result.put("formatTime", formatTime);
        result.put("price", price);
        result.put("priceChange", priceChange);
        result.put("rateChange", rateChange);
        String tmp = title.text();
        result.put("code", tmp.split("\\(")[1]);
        result.put("name", tmp.split("\\(")[0]);
        return result;

    }

    /**
     * 获取基金变动信息
     */
    public static JSONObject acquireFoundRateInfo(Elements elements) {

        JSONObject json = new JSONObject(true);

        int size = elements.size();

        // System.out.println(elements);
        // 1 阶段涨幅
        Element element1 = elements.get(1);
        JSONObject json1 = formatBody(element1);
        // 2 同类平均
        Element element2 = elements.get(2);
        JSONObject json2 = formatBody(element2);
        // 3 沪深300
        Element element3 = elements.get(3);
        JSONObject json3 = formatBody(element3);
        // 4 跟踪标的
        Element element4 = elements.get(4);
        JSONObject json4 = formatBody(element4);
        // 5 同类排名
        Element element5 = elements.get(5);
        JSONObject json5 = formatBody(element5);
        // 6 四分位排名
        if (size >= 7) {
            Element element6 = elements.get(6);
            JSONObject json6 = formatBody(element6);
            // 四分位排名
            json.put("rank4", json6);
        }
        // 阶段涨幅
        json.put("stageRate", json1);
        // 同类平均
        json.put("average", json2);
        // 沪深300
        json.put("hs300", json3);
        // 跟踪标的
        json.put("target", json4);
        // 同类平均
        json.put("rank", json5);

        return json;
    }

    public static String matchStr(String managers, String regex) {
        List<String> list = matchStrs(managers, regex);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return "";
        }
    }

    public static List<String> matchStrs(String managers, String regex) {
        List<String> ls = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(managers);
        while (matcher.find())
            ls.add(matcher.group());
        return ls;
    }

    private static JSONObject formatBody(Element element) {

        JSONObject json = new JSONObject(true);
        Elements tds = element.getElementsByTag("td");

        String b1 = body(tds, 1);
        json.put("week1", b1);

        String b2 = body(tds, 2);
        json.put("month1", b2);

        String b3 = body(tds, 3);
        json.put("month3", b3);

        String b4 = body(tds, 4);
        json.put("month6", b4);

        String b5 = body(tds, 5);
        json.put("year", b5);

        String b6 = body(tds, 6);
        json.put("year1", b6);

        String b7 = body(tds, 7);
        json.put("year2", b7);

        String b8 = body(tds, 8);
        json.put("year3", b8);

        return json;
    }


    private static String body(Elements tds, Integer d) {
        Element element = tds.get(d);
        String b = matchStr(replaceBlank(element.html()), div_tag_match);
        b = b.replace("%", "");
        return b;
    }

    private static JSONObject acquireFoundInfo(Elements funds) {

        JSONObject json = new JSONObject(true);

        Element element0 = funds.get(0);
        String filter0 = HtmlUtil.filter(element0.html());
        filter0 = matchStr(filter0, a_match);
        // 基金类型
        json.put("fundType", filter0);
        // System.out.println(filter0);

        Element element1 = funds.get(1);
        String filter1 = HtmlUtil.filter(element1.html());
        filter1 = replaceStr(filter1);
        String date = matchStr(filter1, date_match);
        String money = matchStr(filter1, money_match).replace("亿元", "");
        // 更新日期
        json.put("updateDate", date);
        // 基金规模
        json.put("fundSize", money);
        //System.out.println("date ->  " + date + "  money ->  " + money);

        Element element2 = funds.get(2);
        String filter2 = HtmlUtil.filter(element2.html());
        filter2 = replaceStr(filter2);
        filter2 = matchStr(filter2, a_match);
        // 基金经理
        json.put("fundManager", filter2);
        //System.out.println(filter2);

        Element element3 = funds.get(3);
        String filter3 = HtmlUtil.filter(element3.html());
        filter3 = replaceStr(filter3);
        filter3 = matchStr(filter3, date_match);
        //System.out.println(filter3);
        // 成立日期
        json.put("fundCreate", filter3);

        Element element4 = funds.get(4);
        String filter4 = HtmlUtil.filter(element4.html());
        filter4 = replaceStr(filter4);
        filter4 = matchStr(filter4, a_match);
        //System.out.println(filter4);
        // 基金公司
        json.put("fundCompany", filter4);

        Element element5 = funds.get(5);
        String filter5 = HtmlUtil.filter(element5.html());
        filter5 = replaceStr(filter5);
        filter5 = matchStr(filter5, a_tag_match) + "";
        //System.out.println(filter5);
        // 基金评级
        json.put("fundLevel", filter5.replace(":", ""));

        if (funds.size() >= 7) {
            Element element6 = funds.get(6);
            String filter6 = HtmlUtil.filter(element6.html());
            filter6 = replaceStr(filter6);
            String[] splits = filter6.split("\\|");
            //
            if (splits.length >= 1) {
                String filter61 = matchStr(splits[0], a_tag_match);
                // 基金标的
                json.put("indexTarget", replaceBlank(filter61));
            }
            if (splits.length >= 2) {
                String filter62 = matchStr(splits[1], a_tag_match);
                // 基金评级
                json.put("indexError", replaceBlank(filter62));
            }
            //System.out.println(StringUtil.replaceBlank(filter61));
            //System.out.println(filter62);

        }
        return json;
    }

    private static String replaceStr(String str) {

        return str.replace("：", ":")
                .replace("--", "")
                .replace("（", "(")
                .replace("）", ")");
    }


    public static String replaceBlank(String str) {

        if (StrUtil.isBlank(str)) {
            return "";
        }

        return str.replaceAll("\\s*", "").replaceAll("\\r\\n", "");


    }

    /**
     * 截取数据根据位置获取
     */
    private static String acquireVal(String node, Integer idx) {
        String temp = (node + "").replace("%", "").replace("--", "");
        return StrUtil.split(temp, " ")[idx];
    }

    /**
     * 统计相应的夏普信息
     */
    public static void fundSharpInfo(EtfList rate) {

        String fundCode = rate.getCode();

        try {

            String url = StrUtil.format("http://fundf10.eastmoney.com/tsdata_{}.html", fundCode);
            // log.info("url is {}", url);
            Document doc = Jsoup.connect(url).get();

            Element element0 = doc.getElementsByClass("fxtb").get(0);
            Elements allfxdj = doc.getElementsByClass("allfxdj");

            Elements tags = element0.getElementsByTag("tr");

            String error = acquireVal(tags.get(1).text(), 1);
            String sharp = acquireVal(tags.get(2).text(), 1);

            rate.setStandError(trans2Bg(error));
            rate.setSharpRate(trans2Bg(sharp));

        } catch (IOException e) {
            log.info("encounter exception code is {} and e is{}", fundCode, e.getMessage());
        }

    }

    public static BigDecimal trans2Bg(Double dd) {
        if (dd == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(dd).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal trans2Bg(String dd) {

        if (StrUtil.isEmpty(dd)) {
            return BigDecimal.ZERO;
        }
        try {
            dd = dd.replace(",", "");
            return trans2Bg(Double.valueOf(dd.trim()));
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }

    }

    public static Integer extractInt2(JSONObject json, String key) {

        Integer num = extractInt(json, key);
        return num == 0 ? -1 : num;

    }

    public static Integer extractInt(JSONObject json, String key) {

        try {
            if (json == null) {
                return -1;
            }


            String val = json.getString(key) + "";
            val = val.trim();
            if (StrUtil.isBlank(val)) {
                return 0;
            }
            val = val.replace("-", "");
            return Integer.valueOf(val);

        } catch (Exception e) {
            log.error("extractBg  error and e is {} content {}", e.getMessage(), json.get(key), e);
            return 0;
        }

    }


    // 格式换信息
    private static void formatEtfInfo(JSONObject jsons, EtfList list) {


        fillField(list, "fundSize", extractBg(jsons, "fundSize"));
        list.setFundManager(jsons.getString("fundManager"));
        list.setFundCompany(jsons.getString("fundCompany"));
        fillField(list, "createTime", extractInt(jsons, "fundCreate"));
        fillField(list, "updateTime", extractInt(jsons, "updateDate"));
        fillField(list, "formatTime", extractInt(jsons, "formatTime"));
        String indexTarget = jsons.getString("indexTarget");
        if (StrUtil.equalsAny(indexTarget, "0", "该基金无跟踪标的")) {
            list.setIndexTarget("");
        }

        fillField(list, "indexError", extractBg(jsons, "indexError"));
        fillField(list, "price", extractBg(jsons, "price"));
        fillField(list, "priceChange", extractBg(jsons, "priceChange"));
        fillField(list, "rateChange", extractBg(jsons, "rateChange"));
        // etfRate.setHref(jsons.getString("href"));
        // etfRate.setFundType(formatFundType(jsons.getString("fundType")));

        JSONObject stageRate = jsons.getJSONObject("stageRate");

        fillField(list, "stageWeek1", extractBg(stageRate, "week1"));
        fillField(list, "stageMonth1", extractBg(stageRate, "month1"));
        fillField(list, "stageMonth3", extractBg(stageRate, "month3"));
        fillField(list, "stageMonth6", extractBg(stageRate, "month6"));
        fillField(list, "stageYear", extractBg(stageRate, "year"));
        fillField(list, "stageYear1", extractBg(stageRate, "year1"));
        fillField(list, "stageYear2", extractBg(stageRate, "year2"));
        fillField(list, "stageYear3", extractBg(stageRate, "year3"));


        list.setCode(jsons.getString("code"));
        list.setName(jsons.getString("name"));
        list.setHref(jsons.getString("href"));


    }

    public static BigDecimal extractBigDecimal(JSONObject json, String key, int keep) {

        try {
            String val = json.getString(key) + "";
            val = val.replace("+-", "").replace("-+", "").trim();
            if (val.startsWith("+")) {
                val = val.substring(1, val.length() - 1);
            }
            if (StrUtil.isBlank(val) || "--".equalsIgnoreCase(val) || "null".equalsIgnoreCase(val) || "-".equalsIgnoreCase(val)) {
                return BigDecimal.ZERO;
            }

            val = val.replace("%", "");
            return BigDecimal.valueOf(Double.valueOf(val)).setScale(keep, BigDecimal.ROUND_HALF_UP);

        } catch (Exception e) {
            log.error("extractBg  error and e is {} content {}", e.getMessage(), json.get(key), e);
            return BigDecimal.ZERO;
        }

    }


    public static BigDecimal extractBg(JSONObject json, String key) {
        return extractBigDecimal(json, key, 5);

    }


    public static BigDecimal extractBgD100(JSONObject json, String key) {
        return extractBg(json, key).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 转换 股票 ETF 代码
     *
     * @param code
     * @return
     */
    public static String formatStock(String code) {
        String secid = "";
        if (code.startsWith("5") || code.startsWith("1")) {
            secid = code.startsWith("5") ? "1" : "0";
        } else {
            secid = code.startsWith("6") ? "1" : "0";
        }
        return secid + "." + code;

    }


    /**
     * 查询 ETF 简码信息
     */
    public static String capEtfBriefName(String code) {

        if (StrUtil.isEmpty(code)) {
            return "";
        }

        try {

            String secid = formatStock(code);
            HttpRequest request = HttpUtil.createGet("http://push2.eastmoney.com/api/qt/stock/get?invt=2&fltt=1&fields=f58&secid=" + secid);
            HttpResponse execute = request.execute();
            String response = execute.body();

            JSONObject jsonObject = JSONObject.parseObject(response);
            return jsonObject.getJSONObject("data").getString("f58");
        } catch (Exception e) {
            log.error(" error  code {} e {}", code, e);
            return "";
        }
    }

    /**
     * 处理 etf k 线信息
     * klt  60 小时线
     * klt: 101 日线
     * klt: 102 周线
     * klt: 103 月线
     * klt: 104 季线
     * klt: 105 半年线
     * klt: 106 年线
     *
     * @param etfList
     */
    public static void handleEtfKline(EtfList etfList) {

        String uri = "http://54.push2his.eastmoney.com/api/qt/stock/kline/get";
        String code = etfList.getCode();
        String s = formatStock(code);

        Map<String, String> params = Maps.newHashMap();
        params.put("secid", s);
        params.put("ut", "fa5fd1943c7b386f172d6893dbfba10b");
        params.put("fields1", "f1,f2,f3,f4,f5,f6");
        params.put("fields2", "f51,f52,f53,f54,f55,f56,f57,f58,f59,f60,f61");
        params.put("klt", "102");
        params.put("fqt", "1");
        params.put("beg", "20210101");
        params.put("end", "20300101");
        params.put("lmt", "10000000");

        String result = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result += entry.getKey() + "=" + entry.getValue() + "&";
        }
        result = StrUtil.sub(result, 0, -1);
        String url = uri + "?" + result;
        // log.info(" url is {}", url);
        // 发起请求
        HttpRequest request = HttpUtil.createGet(url);
        HttpResponse execute = request.execute();
        JSONObject jsonObject = JSONObject.parseObject(execute.body());
        // log.info(" json is {}", jsonObject);

        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray klines = data.getJSONArray("klines");
        String name = data.getString("name");
        etfList.setName(name);
        // "时间", "开盘", "收盘", "最高", "最低", "成交量", "成交额", "振幅", "涨跌幅", "涨跌额", "换手率"

        int cnt = 1;
        int len = klines.size();
        for (int i = len - 1; i >= 0; i--) {
            String node = klines.getString(i);
            String[] split = node.split(",");
            if(cnt == 1){
                etfList.setMonthA(split[8]);
            }

            if(cnt == 2){
                etfList.setMonthB(split[8]);
            }
            if(cnt == 3){
                etfList.setMonthC(split[8]);
            }
            if(cnt == 4){
                etfList.setMonthD(split[8]);
            }
            if(cnt == 5){
                etfList.setMonthE(split[8]);
            }
            if(cnt == 6){
                etfList.setMonthF(split[8]);
            }
            if(cnt == 7){
                etfList.setMonthG(split[8]);
                break;
            }
            cnt = cnt + 1;
        }


    }


    public static void main(String[] args) {

        EtfList list = new EtfList();
        list.setCode("510050");
        handleEtfKline(list);


    }


}
