一、问题描述
15数码问题同八数码问题，是人工智能中一个很典型的智力问题。15数码问题是在4×4方格盘上，放有15个数码，剩下一个位置为空（方便起见，用0表示空），每一空格其上下左右的数码可移至空格。本问题给定初始位置和目标位置，要求通过一系列的数码移动，将初始状态转化为目标状态。

状态转换的规则：空格四周的数移向空格，我们可以看作是空格移动，它最多可以有4个方向的移动，即上、下、左、右。问题的求解方法，就是从给定的初始状态出发，不断地将空格上下左右的数码移至空格，将一个状态转化成其它状态，直到产生目标状态。

本报告利用A*算法，给出了15数码问题的C++算法实现。

二、A*算法
2.1算法简介
A*（A-Star）算法是求解最短路最有效的直接搜索方法，也是许多其他问题的常用启发式算法。

其基本思想是：定义一个评价函数f，对当前的搜索状态进行评估，找出一个最有希望的结点来扩展。评价函数形式为：f(n)=g(n)+h(n)。其中f(n)是从初始状态经由状态n到目标状态的代价估计，g(n) 是在状态空间中从初始状态到状态n的实际代价，h(n) 是从状态n到目标状态的最佳路径的估计代价。

A*算法要求启发函数中的h(n)是处在h*(n)的下届范围，即满足h(n)<=h*(n).

2.2评价函数设置
本报告取f(n)=g(n)+h(n)=d(n)+P(n)。其中，d(n)为当前结点的深度，P(n)为当前状态下各将牌到目标位置的距离之和。距离的定义为：“某将牌行下标与目标位置行下标之差的绝对值 + 列下标与目标位置列下标之差的绝对值”。距离越小，该节点的效果越好。某个状态所有将牌到目标位置的距离之和用“h值”表示。

三、算法设计
本课程使用产生式系统的结构。

3.1综合数据库
主要包括：目标状态、初始状态、当前状态、状态所属表、当前状态的评价函数值、当前状态的移动方向。

3.2规则库
主要包括：向左移动、向上移动、向右移动、向下移动。

3.3控制系统
算法的功能：产生15数码问题的解(由初始状态到达目标状态的过程） 

输入：初始状态，目标状态 

输出：从初始状态到目标状态的一系列过程 

算法描述： 

Begin：
读入初始状态和目标状态，并计算初始状态评价函数值f；
初始化两个open表和closed表，将初始状态放入open表中
If（open表为空）
    查找失败；
End if
else
①	在open表中找到评价值最小的节点，作为当前结点，并放入closed表中；
②	判断当前结点状态和目标状态是否一致，若一致，跳出循环；否则跳转到③；
③	对当前结点，分别按照上、下、左、右方向移动空格位置来扩展新的状态结点，并计算新扩展结点的评价值f并记录其父节点；
④	对于新扩展的状态结点，进行如下操作：
A．新节点既不在open表中，也不在closed表中，则ADD (mj, OPEN)；
B．新节点在open表中，则IF f(n-mk) < f(mk)
THEN f(mk):=f(n-mk)，
C．新节点在closed表中，则IF f(n-ml) < f(ml)
THEN f(ml):=f(n-ml)，
⑤	把当前结点从open表中移除；
End if
End

原文：https://blog.csdn.net/xxy0118/article/details/54346486