package com.gate.redis;

/**
 * 查看Redis进程号：ps -fe | grep redis
 * 启动Redis：redis -cli。 redis -cli -p 6380(连接端口号是6380的库)，如果不加 -p port，默认连接的是6379.
 * Redis默认16个库，库是独立的，你的key创建在哪个区域别的库是看不到的，连接上redis默认是0号库，使用命令select 8 可以切换到8号库。
 * redis -cli -p 6380 -n 8（连接端口号是6380的redis的8号库）。
 * keys * 可以看到所有的key。
 * FLUSHDB 清库。 相当于rm -rf不要操作。
 *
 * Redis中有一个二进制安全：
 * 在Redis进程与外界交互的时候，只拿字节流。
 * 例：用UTF-8编码的客户端写入：set k1 中 ； strlen k1 -> 3
 *    用GBK编码的客户端写入：set k2 中 ； strlen k1 -> 2
 *    再用加入规定编码的方式登录redis：redis -cli --raw：
 *      get k1 -> 涓  （乱码了）
 *      get k2 -> 中
 *    也就是说客户端在向Redis存储数据的时候，不管客户端是什么编码格式，Redis都会先把这些数据转成字节类型存储。
 *    所以当多个客户端向Redis存储数据的时候，一定要先统一好编码格式。
 *
 * help @String. 展示关于String的所有命令。
 *   Redis是key-value的，value有五种类型，先看String类型的。String类型包括字符串、数值、bitmap。
 *   set(key,value)设置一个key和value，get(key)取出。
 *   type k1: 返回k1对应value的类型。key里面会有一个type属性，描述了value的类型。
 *   [--Code start-------------------------------------------------------------------------------------------]
 *    因为String中包含字符串、数值、bitmap，如何区分数据库里的值是字符串、数值还是bitmap? 用 object encoding key
 *    set k1 99; type k1-> String; OBJECT encoding t1 -> int;
 *    set k2 hello; type k2 -> String; OBJECT encoding t2 -> embstr;
 *   [--Code end--------------------------------------------------------------------------------------------]
 *
 *   String中字符串类型的value的操作：set k1 hello、 get k1。 mget k1 k2：取多个值。
 *   [--Code start-------------------------------------------------------------------------------------------]
 *    msetnx k1 a k2 b: 添加两条数据
 *    mget k1 k2: 取出两条数据 -> a b
 *    msetnx k2 c k3 d: 添加k2为c，k3为d。会失败，因为原子性操作，nx代表只有key不存在的时候才会设置值，如果key存在则不操作，
 *      因为上面k2已经设置了b，已经存在，所以这条语句的k2不会操作，k3因为原子性的关系也不会操作。
 *    get k1 k2 k3: a b  ；
 *   [--Code end--------------------------------------------------------------------------------------------]
 *      set k1 hello nx: 如果k1这个key不存在，就把k1设置成hello，如果k1存在，不会覆盖k1的原始值。
 *      set k2 hello xx: 如果k2这个key不存在，不会为k2这个key设置值，如果k2这个key存在，会更新之前k2这个key对应的value。
 *          nx: 不存在则设置，存在则不操作； xx: 存在才更新，不存在不操作。
 *      append k1 world: 给k1的value追加world。
 *      getrange key start end : 取出value中某部分的值。可以理解为substr字符串截取。
 *          正反向索引：end为-1的时候表示取到最后一位，start为0的时候表示从开始取。
 *      setrange key offset value : 修改key对应的value，offset表示从第几位开始修改。
 *          比如setrange k1 6 ok : 表示k1的值从第六位开始修改ok。
 *      strlen k1: k1对应value的长度。
 *      getset key value: 相当于执行了get key  + set key value。 getset命令只发送一次请求，get+set会发送两次请求。所以减少一次I/O通信。
 *   String中数值类型的value的操作：
 *      INCR k1: 给k1 + 1 ； DECR k1: 给k1 -1
 *      INCRBY k1 6: 给k1 + 6 ； DECRBY k1 6: 给k1 - 6
 *      INCRBYFLOAT k1 0.5: 给k1 + 0.5，加小数用INCRBYFLOAT。
 *
 */
public class T02_RedisString {
}
