##### Talib 
###### 0 简介
Talib是一款非常强大的技术分析指标计算第三方包，于1999年由Mario Fortier最早上传。由于底层框架是用C语言搭建的，
所以python在使用时的帮助文档较少。为了方便使用，参考HuaRongSAO在github上的发布的内容整理出以下文档。

```

import talib
https://github.com/HuaRongSAO/talib-document
Whl下载地址：https://www.lfd.uci.edu/~gohlke/pythonlibs/#TA-Lib

pip install xxx.whl

```


``` 
# 取个数据验证一下
set_token('')
data = history(symbol = 'SHSE.600519',frequency = '1d',start_time = '2015-01-01',end_time = '2019-12-31',fields = 'open, high, low, close,amount,volume,eob',df = True)

```
###### 1、重叠研究（overlap studies）

```
# 1.简单移动平均指标SMA
# 参数说明：talib.SMA(a,b)
# a:要计算平均数的序列；b:计算平均线的周期。表示计算a的b日移动平均
close = data['close'].values
SMA = talib.SMA(close,5)

# 2.布林线BBANDS
# 参数说明：talib.BBANDS(close, timeperiod, matype)
# close:收盘价；timeperiod:周期；matype:平均方法(bolling线的middle线 = MA，用于设定哪种类型的MA)
# MA_Type: 0=SMA, 1=EMA, 2=WMA, 3=DEMA, 4=TEMA, 5=TRIMA, 6=KAMA, 7=MAMA, 8=T3 (Default=SMA)
upper, middle, lower = talib.BBANDS(close,5,matype = talib.MA_Type.EMA)

# 3. DEMA 双移动平均线:DEMA = 2*EMA-EMA(EMA)
# 参数说明：talib.DEMA(close, timeperiod = 30)
DEMA = talib.DEMA(close, timeperiod = 30)

# 4. MA
# 参数说明：MA(close, timeperiod = 30, matype=0)
# close:收盘价；timeperiod:周期；matype:计算平均线方法
MA = talib.MA(close, timeperiod = 30, matype = 0)

# 5. EMA
# 参数说明：EMA = talib.EMA(np.array(close), timeperiod=6)
# close:收盘价；timeperiod:周期；matype:计算平均线方法
EMA = talib.EMA(np.array(close), timeperiod = 6)

# 6.KAMA：考夫曼的自适应移动平均线
# 参数说明：KAMA = talib.KAMA(close, timeperiod = 30)
KAMA = talib.KAMA(close, timeperiod = 30)

# 7. MIDPRICE：阶段中点价格
# talib.MIDPOINT(close, timeperiod)
# 参数说明：close:收盘价；timeperiod:周期；
MIDPOINT = talib.MIDPOINT(close, timeperiod=14)

# 8.SAR：抛物线指标
# SAR(high, low, acceleration=0, maximum=0)
# 参数说明：high：最高价；low:最低价；acceleration：加速因子；maximum：极点价
SAR = talib.SAR(data['high'].values, data['low'].values, acceleration=0, maximum=0)

# 9.MIDPRICE：阶段中点价格（Midpoint Price over period）
# talib.MIDPOINT(close, timeperiod=14)
# 参数说明：close:收盘价；timeperiod:周期；
MIDPOINT = talib.MIDPOINT(close, timeperiod=14)

# 10. T3:三重移动平均线
# talib.T3(close, timeperiod=5, vfactor=0)
# 参数说明：close:收盘价；timeperiod:周期；vfactor: va 系数，当va=0时，T3就是三重移动平均线；va=1时，就是DEMA
T3 = talib.T3(close, timeperiod = 5, vfactor = 0)

# 11.TEMA：三重指数移动平均线
# talib.TEMA(close, timeperiod = 30)
# 参数说明：close:收盘价；timeperiod:周期；
TEMA = talib.TEMA(close, timeperiod=30)

# 12.SAREXT:SAR的抛物面扩展
# talib.SAREXT(high_p, low_p, startvalue=0, offsetonreverse=0, accelerationinitlong=0, accelerationlong=0, accelerationmaxlong=0, accelerationinitshort=0, accelerationshort=0, accelerationmaxshort=0)
SAREXT = talib.SAREXT(data['high'].values, data['low'].values, startvalue=0, offsetonreverse=0, accelerationinitlong=0, accelerationlong=0, accelerationmaxlong=0, accelerationinitshort=0, accelerationshort=0, accelerationmaxshort=0)

# 13.WMA：移动加权平均法
# talib.WMA(close, timeperiod = 30)
# 参数说明：close:收盘价；timeperiod:周期；
WMA = talib.WMA(close, timeperiod = 30)

```
###### 2.波动量指标
```
1.ATR：真实波动幅度均值
# ATR(high, low, close, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close:收盘价,timeperiod:周期
ATR = talib.ATR(data['high'].values, data['low'].values, close, timeperiod=14)

# 2.NATR:归一化波动幅度均值
# NATR(high, low, close, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close:收盘价,timeperiod:周期
NATR = talib.NATR(data['high'].values, data['low'].values, close, timeperiod=14)

# 3.TRANGE：真正的范围
# TRANGE(high, low, close)
# 参数说明：high:最高价；low:最低价；close:收盘价
TRANGE = talib.TRANGE(data['high'].values, data['low'].values, close)


```

###### 3. 量价指标
```
# 1. AD:量价指标
# AD(high, low, close, volume)
# 参数说明：high:最高价；low:最低价；close:收盘价,volume:成交量
AD = talib.AD(data['high'], data['low'], close,data['volume'])

# 2.ADOSC:震荡指标
# ADOSC(high, low, close, volume, fastperiod=3, slowperiod=10)
# 参数说明：high:最高价；low:最低价；close:收盘价,volume:成交量; fastperiod:快周期；slowperiod：慢周期
ADOSC = talib.ADOSC(data['high'], data['low'], close,data['volume'], fastperiod=3, slowperiod=10)

# 3.OBV：能量潮
# OBV(close, volume)
# 参数说明：close:收盘价,volume:成交量
OBV = talib.OBV(close, data['volume'])
```

###### 4 周期指标
```

# 1.HT_DCPERIOD：希尔伯特变换-主导周期
# HT_DCPERIOD(close)
# 参数说明：close:收盘价
HT_DCPERIOD = talib.HT_DCPERIOD(close)

# 2.HT_DCPHASE：希尔伯特变换-主导循环阶段
# HT_DCPHASE(close)
# 参数说明：close:收盘价
HT_DCPHASE = talib.HT_DCPHASE(close)

# 3.HT_PHASOR：希尔伯特变换-希尔伯特变换相量分量
# inphase, quadrature = HT_PHASOR(close)
# 参数说明：close:收盘价
HT_PHASOR_inphase,HT_PHASOR_quadrature = talib.HT_PHASOR(close)

# 4.HT_SINE：希尔伯特变换-正弦波
# sine, leadsine = HT_SINE(close)
# 参数说明：close:收盘价
HT_SINE_sine,HT_SINE_leadsine = talib.HT_SINE(close)

# 5.HT_TRENDMODE：希尔伯特变换-趋势与周期模式
# integer = HT_TRENDMODE(close)
# 参数说明：close:收盘价
HT_TRENDMODE = talib.HT_TRENDMODE(close)

```


###### 5.价格变化函数
```
# 1. AVGPRICE：平均价格函数
# real = AVGPRICE(open, high, low, close)
AVGPRICE = talib.AVGPRICE(data['open'].values, data['high'].values, data['low'].values, close)

# 2. MEDPRICE:中位数价格
# real = MEDPRICE(high, low)
# 参数说明：high:最高价；low:最低价；
MEDPRICE = talib.MEDPRICE(data['high'].values, data['low'].values)

# 3. TYPPRICE ：代表性价格
# real = TYPPRICE(high, low, close)
# 参数说明：high:最高价；low:最低价；close：收盘价
TYPPRICE = talib.TYPPRICE(data['high'].values, data['low'].values, close)

# 4. WCLPRICE ：加权收盘价
# real = WCLPRICE(high, low, close)
# 参数说明：high:最高价；low:最低价；close：收盘价
WCLPRICE = talib.WCLPRICE(data['high'].values, data['low'].values, close)

```

###### 6.动量指标
```
# 1. ADX：平均趋向指数
# real = ADX(high, low, close, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
ADX = talib.ADX(data['high'].values, data['low'].values, close, timeperiod=14)

# 2. ADXR：平均趋向指数的趋向指数
# real = ADXR(high, low, close, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
ADXR = talib.ADXR(data['high'].values, data['low'].values, close, timeperiod=14)

# 3. APO ：价格震荡指数
# real = APO(close, fastperiod=12, slowperiod=26, matype=0)
# 参数说明：close：收盘价；fastperiod:快周期；slowperiod：慢周期
APO = talib.APO(close, fastperiod=12, slowperiod=26, matype=0)

# 4. AROON ：阿隆指标
# aroondown, aroonup = AROON(high, low, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
AROON_aroondown,AROON_aroonup = talib.AROON(data['high'].values, data['low'].values, timeperiod=14)

# 5.AROONOSC ：阿隆振荡
# real = AROONOSC(high, low, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
AROONOSC = talib.AROONOSC(data['high'].values, data['low'].values, timeperiod=14)

# 6. BOP ：均势指标
# real = BOP(open, high, low, close)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
BOP= talib.BOP(data['open'].values, data['high'].values, data['low'].values, close)

# 7. CCI ：顺势指标
# real = CCI(high, low, close, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
CCI = talib.CCI(data['high'].values, data['low'].values,close, timeperiod=14)

# 8. CMO ：钱德动量摆动指标
# real = CMO(close, timeperiod=14)
# 参数说明：close：收盘价；timeperiod：时间周期
CMO = talib.CMO(close, timeperiod=14)

# 9. DX ：动向指标或趋向指标
# real = DX(high, low, close, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
DX = talib.DX(data['high'].values, data['low'].values, close, timeperiod=14)

# 10. MACD:平滑异同移动平均线
# macd, macdsignal, macdhist = MACD(close, fastperiod=12, slowperiod=26, signalperiod=9)
# 参数说明：high:最高价；low:最低价；close：收盘价；fastperiod:快周期；slowperiod：慢周期
MACD_macd,MACD_macdsignal,MACD_macdhist = talib.MACD(close, fastperiod=12, slowperiod=26, signalperiod=9)

# 11. MACDEXT :MACD延伸
# macd, macdsignal, macdhist = MACDEXT(close, fastperiod=12, fastmatype=0, slowperiod=26, slowmatype=0, signalperiod=9, signalmatype=0)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
MACDEXT_macd,MACDEXT_macdsignal,MACDEXT_macdhist = talib.MACDEXT(close, fastperiod=12, fastmatype=0, slowperiod=26, slowmatype=0, signalperiod=9, signalmatype=0)

# 12. MFI ：资金流量指标
# real = MFI(high, low, close, volume, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
MFI = talib.MFI(data['high'].values, data['low'].values, close, data['volume'], timeperiod=14)

# 13. MINUS_DI：DMI 中的DI指标 负方向指标
# real = MINUS_DI(high, low, close, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
MINUS_DI = talib.MINUS_DI(data['high'].values, data['low'].values, close, timeperiod=14)

# 14. MINUS_DM：上升动向值
# real = MINUS_DM(high, low, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
MINUS_DM = talib.MINUS_DM(data['high'].values, data['low'].values, timeperiod=14)

```

###### 7.波动率指标
```
# 1.MOM：上升动向值
# real = MOM(close, timeperiod=10)
# 参数说明：close：收盘价；timeperiod：时间周期
MOM = talib.MOM(close, timeperiod=10)

# 2.PLUS_DI
# real = PLUS_DI(high, low, close, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
PLUS_DI = talib.PLUS_DI(data['high'].values, data['low'].values, close, timeperiod=14)

# 3.PLUS_DM
# real = PLUS_DM(high, low, timeperiod=14)
# 参数说明：high:最高价；low:最低价；close：收盘价；timeperiod：时间周期
PLUS_DM = talib.PLUS_DM(data['high'].values, data['low'].values, timeperiod=14)

# 4. PPO：价格震荡百分比指数
# real = PPO(close, fastperiod=12, slowperiod=26, matype=0)
# 参数说明：close：收盘价；timeperiod：时间周期，fastperiod:快周期；slowperiod：慢周期
PPO = talib.PPO(close, fastperiod=12, slowperiod=26, matype=0)

# 5.ROC：变动率指标
# real = ROC(close, timeperiod=10)
# 参数说明：close：收盘价；timeperiod：时间周期
ROC = talib.ROC(close, timeperiod=10)

# 6. ROCP：变动百分比
# real = ROCP(close, timeperiod=10)
# 参数说明：close：收盘价；timeperiod：时间周期
ROCP = talib.ROCP(close, timeperiod=10)

# 7.ROCR ：变动百分率
# real = ROCR(close, timeperiod=10)
# 参数说明：close：收盘价；timeperiod：时间周期
ROCR = talib.ROCR(close, timeperiod=10)

# 8. ROCR100 ：变动百分率（*100）
# real = ROCR100(close, timeperiod=10)
# 参数说明：close：收盘价；timeperiod：时间周期
ROCR100 = talib.ROCR100(close, timeperiod=10)

# 9. RSI：相对强弱指数
# real = RSI(close, timeperiod=14)
# 参数说明：close：收盘价；timeperiod：时间周期
RSI = talib.RSI(close, timeperiod=14)

# 10.STOCH ：随机指标,俗称KD
# slowk, slowd = STOCH(high, low, close, fastk_period=5, slowk_period=3, slowk_matype=0, slowd_period=3, slowd_matype=0)
# 参数说明：high:最高价；low:最低价；close：收盘价；fastk_period：N参数, slowk_period：M1参数, slowk_matype：M1类型, slowd_period:M2参数, slowd_matype：M2类型
STOCH_slowk,STOCH_slowd = talib.STOCH(data['high'].values, data['low'].values, close, fastk_period=5, slowk_period=3, slowk_matype=0, slowd_period=3, slowd_matype=0)

# 11. STOCHF ：快速随机指标
# fastk, fastd = STOCHF(high, low, close, fastk_period=5, fastd_period=3, fastd_matype=0)
STOCHF_fastk,STOCHF_fastd = talib.STOCHF(data['high'].values, data['low'].values, close, fastk_period=5, fastd_period=3, fastd_matype=0)

# 12.STOCHRSI：随机相对强弱指数
# fastk, fastd = STOCHRSI(high, low, close, timeperiod=14, fastk_period=5, fastd_period=3, fastd_matype=0)
STOCHRSI_fastk,STOCHRSI_fastd = talib.STOCHF(data['high'].values, data['low'].values, close, fastk_period = 5, fastd_period = 3, fastd_matype = 0)

# 13.TRIX：1-day Rate-Of-Change (ROC) of a Triple Smooth EMA
# real = TRIX(close, timeperiod=30)
TRIX = talib.TRIX(close, timeperiod=30)

# 14.ULTOSC：终极波动指标
# real = ULTOSC(high, low, close, timeperiod1=7, timeperiod2=14, timeperiod3=28)
ULTOSC = talib.ULTOSC(data['high'].values, data['low'].values, close, timeperiod1=7, timeperiod2=14, timeperiod3=28)

# 15.WILLR ：威廉指标
# real = WILLR(high, low, close, timeperiod=14)
WILLR = talib.WILLR(data['high'].values, data['low'].values, close, timeperiod = 14)

```

###### 8 Statistic Functions 统计学指标
```

# 1. BETA：β系数也称为贝塔系数
# real = BETA(high, low, timeperiod=5)
BETA = talib.BETA(data['high'].values, data['low'].values, timeperiod = 5)

# 2. CORREL ：皮尔逊相关系数
# real = CORREL(high, low, timeperiod=30)
CORREL = talib.CORREL(data['high'].values, data['low'].values, timeperiod = 30)

# 3.LINEARREG ：线性回归
# real = LINEARREG(close, timeperiod=14)
LINEARREG = talib.LINEARREG(close, timeperiod=14)

# 4.LINEARREG_ANGLE ：线性回归的角度
# real = LINEARREG_ANGLE(close, timeperiod=14)
LINEARREG_ANGLE = talib.LINEARREG_ANGLE(close, timeperiod=14)

# 5. LINEARREG_INTERCEPT ：线性回归截距
# real = LINEARREG_INTERCEPT(close, timeperiod=14)
LINEARREG_INTERCEPT = talib.LINEARREG_INTERCEPT(close, timeperiod=14)

# 6.LINEARREG_SLOPE：线性回归斜率指标
# real = LINEARREG_SLOPE(close, timeperiod=14)
LINEARREG_SLOPE = talib.LINEARREG_SLOPE(close, timeperiod=14)

# 7.STDDEV ：标准偏差
# real = STDDEV(close, timeperiod=5, nbdev=1)
STDDEV = talib.STDDEV(close, timeperiod=5, nbdev=1)

# 8.TSF：时间序列预测
# real = TSF(close, timeperiod=14)
TSF = talib.TSF(close, timeperiod=14)

# 9. VAR：方差
# real = VAR(close, timeperiod=5, nbdev=1)
VAR = talib.VAR(close, timeperiod=5, nbdev=1)

```

