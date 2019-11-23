package com.gate.redis;

/**
 * Redis中的List、Set、Hash、SortedSet
 * List 有序可重复
 * Set  无序不重复
 * Sorted Set 有序可不重复
 *
 *<pre>
 * List: 双向链表。 Redis的key当中有两个指针，head（头指针）和 tail（尾指针）。head能指到list的头部，tail可以指到list的尾部。可以快速访问list的第一个和最后一个元素。
 *       Redis当中的List有正负索引，也就是从前数和从后数索引都可以。
 *       应用场景：
 *          List中的Redis可以通过同向命令如(lpush，lpop)做一个栈（后入先出）、也可以通过反向命令如(lpush，rpop)做一个队列（先入先出）、
 *           还可以通过索引进行操作如(lindex，lset)做一个数组、还可以通过阻塞弹出(blpop，brpop)做一个阻塞队列或单播队列（可以做消息订阅）。
 *
 * help @List
 *     L开头的命令是left的意思，也有是只有list可以操作的意思。R开头的是right的意思。
 *     对list链表增删改查：
 *     [--Code start---------------------------------------------------------------------------------------------------------]
 *       lpush k1 a b c d e f：从左边开始向list添加a b c d e f。
 *       rpush k2 a b c d e f：从右边开始向list添加a b c d e f。
 *       lpop k1：弹出一个元素 -> f。 f是最后一个被推进去的，是第一个被弹出来的。
 *          如果使用的lpush和lpop，也就是同向命令操作的时候，Redis的List可以描述一个栈。
 *          如果使用的lpush和rpop，也就是反向命令操作的时候，Redis的List可以描述一个队列。
 *       lrange key start stop：取出key中从start到stop的元素。 lrange里的l不是左边的意思，是List的意思。
 *       lindex key index：可以根据索引取数。根据索引操作的时候就相当于Java中对数组的操作。
 *       lset key index value：可以根据索引修改数值。
 *       lrem key count value：从key的value中移除count个value。比如 lrem k1 2 a 就是从k1中移除2个a。如果count为正数就从左往右移除，负数就从右往左移除。
 *       linsert key before/after pivot value：在哪个元素的前面或后面插入一个value。 linsert k1 before f e：就是在f前面插入一个e。如果有两个f，它会在第一个f前面插入。
 *       llen key：统计key中有多少个元素。
 *       blpop key/[key...] timeout：阻塞timeout时间从左边开始弹出元素。如果key中没有元素则一直阻塞，直到key中有元素可以弹出。
 *          通过blplp可以做阻塞队列或者单播队列。先阻塞的线程，在key中有数据的时候先弹出。
 *       ltrim key start stop：将start-stop两端的数据删除。如 ltrim k1 1 3：将k1中1到3包含首尾的两端的数据删除，删除的是 a e f。
 *     [--Code end-----------------------------------------------------------------------------------------------------------]
 *</pre>
 *
 *
 *<pre>
 * hash: 像Java中的HashMap。hash类型的value也是键值对。
 *       应用场景：
 *          比如有很多用户，每个用户又都有姓名、电话、地址等属性。
 *          比如打开一个页面，一个商品有很多的信息。用hash就可以给每个商品存入很多属性，客户端调用的时候只需要调用一次商品，就可以返回所有的属性。
 *             这些商品的属性的信息，比如销售量等需要计算，hash也提供了计算相关的方法。
 *
 * help @hash
 *      hash的命令大部分都是h开头的。
 *      [--Code start----------------------------------------------------------------------------------]
 *        hmset k1 age 18 address bj name w：在k1对应的value中存入name=w、age=18、address=bj的数据。
 *        hget k1 age：取出k1对应的age的value。-> 18
 *        hkeys k1：取出k1中value里的key。 -> age address name
 *        hvals k1：取出k1中value对应的value。 -> 18 bj w
 *        hgetall k1：取出k1中value里的key和value。 -> age address name 18 xj w
 *        hincrbyfloat k1 age 0.5：对k1中的age对应的value增加0.5；这时候age = 18.5
 *        hincrbyfloat k1 age -0.5：对k1中的age对应的value减0.5；这时候age = 18
 *      [--Code end------------------------------------------------------------------------------------]
 *</pre>
 *
 *
 *<pre>
 * set: 对比list. list是有序（存入和取出的顺序）的，list是可重复的。也就是有序可重复的。
 *      set是一种去重的集合，而且里面不维护排序，不维护存入和取出的顺序。也就是无须不可重复的。
 *      应用场景：
 *          1.set可以通过srandmember key count做随机事件，随机取数。可以做抽奖。
 *            比如：抽奖一共有10个奖品，用户数可能大于10或者小于10，抽奖也可能会出现重复或不重复（一个用户多次中奖）。
 *                 在k1中加入参与抽奖的人数，sadd k1 1 2 3 4 5，比如有5个人，三件奖品，那么：
 *                 如果count是3，就会有3个不重复的人中奖。如果count是-3，就可能出现重复的用户拿走3个奖品。
 *                 如果奖品数大于参与用户的数量，比如有10个奖品，那么需要k1里面的5个人重复中奖：
 *                 srandmember k1 -10：这样就会在参与抽奖的5个人(k1)中随机抽取10个人(负数且大于集合k1的元素数所以是可重复的)来中奖。
 *            比如年会抽奖：可以用spop
 *                 spop k1：每次在k1中取出一个数，直接取出，不放回。
 *
 * help @set
 *      set的命令大多数都是s开头的。
 *      [--Code start----------------------------------------------------------------------------------]
 *        sadd k1 tom jack honey tom：向k1中添加 tom jack honey tom，会去重所以只存入了一个tom。
 *        sadd k2 tom jack jon jone：向k2中添加 tom jack jon jone。
 *        smembers k1：取出k1中的元素 -> tom jack honey
 *        srem k1 tom jack：移除k1里面的tom和jack。
 *        sinter k1 k2：查出k1和k2集合间的交集。
 *        sinterstore k3 k1 k2：查出k1和k2集合间的交集并放入k3中。  smembers k3 -> tom jack
 *        sunion k1 k2：查出k1和k2集合间的并集（查出k1和k2的所有元素并自动去重）。
 *        sunionstore k4 k1 k2：查出k1和k2集合间的并集并放入k4中。 smembers k4 -> tom jack honey jon jone.
 *        sdiff k1 k2：查出k1和k2之间的差集。 这个会查出k1里面有k2里面没有的数据。 -> honey
 *           sdiff key key：sdiff后面跟着的两个key顺序不同，取出的数据不同，谁在前面，就取出谁的值(另一个集合没有的)。
 *        srandmember key count：随机事件，随机取数。
 *           srandmember k1 2：如果为正数且小于集合中元素的数量，会在已有集合当中取出想应数量的不会重复的结果集。
 *           srandmember k1 -2：如果为负数且小于集合中元素的数量，取出想应数量的会重复的结果集。
 *           srandmember k1 5：如果为正数且大于集合中元素的数量，会在已有集合当中取出想应数量的会重复的结果集。
 *           srandmember k1 -5：如果为负数且小于集合中元素的数量，取出想应数量的会重复的结果集。
 *      [--Code end------------------------------------------------------------------------------------]
 *</pre>
 *
 *
 *<pre>
 * sorted_set：不重复的可排序的集合。
 *            首先它是个set，它里面必须有元素，其次它是sorted排序的，所以每个元素都需要给出分值。
 *                每个元素还都有自己的正负向索引。
 *                当分值都为1的时候，这个时候会按照名称的字典顺序排序。
 *                物理内存，左小右大，不会随命令变化而变化。比如zrange是从左到右取出从小到大的，zrevrange是从右到左取出从大到小的。
 *            应用场景：
 *                实时排行榜：可以用zincrby k1 value member，当对集合中元素的分值进行增减时，sorted_set可以进行实时排序。
 *
 *            sorted_set的排序是怎么实现的？对元素增删改查的速度快不快？
 *                sorted_set底层用的是skip list(跳跃表)实现的。
 *                跳跃表原理：
 *                     3------------------>nil              (最上面层是第一个元素指向nil)
 *                     ↑
 *                     3------------------>26                (其中的两个又向上造了一层)
 *                     ↑                   ↑                 (集合中的元素会跳跃的向上造层，可以按规则跳跃也可以不按规则跳跃)
 *                     3---------15------->26                (比如集合中的5个元素其中的3个向上造了一层)
 *                     ↑         ↑         ↑                 (集合中的元素会跳跃的向上造层，可以按规则跳跃也可以不按规则跳跃)
 *                     3----6----15---18-->26                (这5个是集合中的元素)
 *                             ↑
 *                             11                           （新加入的元素11，加入步骤如下描述）：
 *
 *                     当有一个新元素11加入的时候；
 *                     11先回去第一层跟3比较，如果小于3则直接插到最前面，如果大于3由于3指的是nil，所以11会降到下一层；
 *                     11到了下一层之后，比3大比26小，所以继续往下一层找；
 *                     11到下了一层后，比3大比15小，所以继续往下一层找；
 *                     11到了最后一层，跟3 6 15比较，插入正确的位置。
 *                     当11插入后，会为11随机造层，方便下次新元素加入时比较。
 *                     也就是说新加入的元素从上往下找应该插入的位置，并且在新加入的元素插入到正确位置后，为这个新元素自动随机造层。
 *                     一般情况下造层元素的层次不一样，越往上数越少，可以加快效率。
 *
 *
 * help @sorted_set:
 *      sorted_set的命令大部分都是z开头的。
 *      [--Code start------------------------------------------------------------------------------------------------------]
 *        zadd k1 8 apple 2 banana 3 orange：向k1中添加apple banana orange，分值分别为8 2 3.
 *        zadd k2 7 apple 2 watermelon 4 grape：向k2中添加apple watermelon grape，分值分别为 7 2 4.
 *        zrange k1 0 -1：按照索引取出k1中的元素 ->banana orange apple。按照分值左小右大排序。
 *        zrange k1 0 -1 withscores：按照索引取出k1中的元素和分值 -> banana 2 orange 3 apple 8
 *        zrangebyscore k1 3 8：取出分值3到8的元素 -> orange apple
 *        zrange k1 0 1：按照索引从左到右取出前两个元素 -> banana orange
 *        zrevrange k1 0 1：按照索引从右到左取出前两个元素 -> apple orange
 *        zscore k1 apple：通过元素取出分值 -> 8
 *        zrank k1 apple：通过元素取出排名 -> 2
 *        zincrby k1 2.5 banana：给k1中的banana的分值增加2.5，增减分值时，sorted_set会实时对集合里的元素进行排序。
 *        也具备集合操作（并集、交集、差集）：因为set里是单元素，而sotred_set中既有元素又有分值，当做集合操作的时候有分值不相同的一样的元素怎么处理？
 *        并集操作：
 *          zunionstore k3 2 k1 k2：取并集 放入k3 2个集合 集合k1 集合k2.
 *            这种情况下 zrange k3 0 -1 withscores：-> banana 2 watermelon 2 orange 3 grape 4 apple 15.
 *            在zunionstore不输入权重和聚合参数的时候，对于不同分值同样元素，sorted_set的处理是将元素的分值想加。
 *          zunionstore k4 2 k1 k2 weights 1 0.5：取并集 放入k4 2个集合 集合k1 集合k2 权重 k1权重1 k2权重0.5
 *            在这种情况（加了权重）下 zrange k4 0 -1 withscores：-> watermelon 1 banana 2 grape 2 orange 3 apple 11.5
 *            在zunionstore输入了权重的情况下，元素对应的分值会乘以输入的集合对应的参数，再进行重新排序展示。
 *          zunionstore k5 2 k1 k2 weights 1 0.5 aggregate max：取并集 放入k5 2个集合 集合k1 集合k2 权重 k1权重1 k2权重0.5 分值聚合方式 最大
 *            在这种情况下（加了权重和分值聚合方式） zange k5 0 -1 withscores： -> watermelon 1 banana 2 grape 2 orange 3 apple 8
 *            zunionstore输入了分值聚合方式的参数后，会按照输入的参数对不同分值的相同元素进行处理，比如max，取最大分值，min最小，sum求和，avg平均值。
 *      [--Code end--------------------------------------------------------------------------------------------------------]
 *</pre>
 */

public class T02_RedisListSetHash {

}
