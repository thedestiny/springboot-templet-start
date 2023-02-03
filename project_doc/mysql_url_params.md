
mysql ip 地址
inet_aton('127.0.0.1') -> 转换为 long 类型进行存储
inet_ntoa(ip_addr)     -> 转为为ip 地址

1字节 TINYINT         -128到127
2字节 SMALLINT        -32768到32767
3字节 MEDIUMINT       -8388608到8388607
4字节 INT             -2147483648到2147483647  2^31
8字节 BIGINT          -9223372036854775808到9223372036854775807


alter table table_name auto_increment = 1

