# 问题

### 模版id 和 节点 id 是同一个字段吗
可能是同一个字段，那么如果有模版复用的情况怎么办呢？？？？  id 应该作为，唯一的存在才对啊


### layout-type 类型和  模版id 是不是互斥的
是的，layout 和 模版id 是互斥的
layout 可以理解为 ViewGroup 用来划定位置的 ，而模版可以理解为 view，用来渲染具体内容的。  

### data-version 和 container-version 是不是重合了
不是，只有  id 和 version 同时对应上才算是相等，可能存在同一个


### position 是必须字段吗，该如何使用呢？
position 是必须字段，除了 root 的直接子节点 position 应该使用者指定，其他间接子节点可以直接继承自父节点。


### 如果有两个 view 设置了同一个 position 会怎样的，还是只有 root 直接子 view 才能设置 position
在渲染过程中，只有 root 的子节点 position 才有意义，其他节点的 position 只是作为一个属性，对渲染流程没有影响。

先按照数组的顺序进行渲染，但是当发现  position 为浮窗等非线性布局的时候，就将节点进行对应的特殊位置渲染

### type  layout  style 是否可以扁平化

### 如果切换 tab 后的页面是由  再次拉取数据渲染而来，那么在 tabContent 的节点上是如何体现出来的呢？
回复：将节点设置为 waterfall ，不展开数组。
建议：将节点设置为  FrameLayout 等占位控件，先不指定排版；排版内容由下次 协议内容给出

### tabLayout  是由多个一整个 cube 模版渲染而来， 还是由多个 cube 模版渲染出来的？

是由一个 cube 渲染来的，至于其中的切换事件，由 native 和 cube 通讯完成

### 需要做 view 层级的协议吗，比如 TextView  ImageView  等简单的视图；
一切视图或者事件都交由 cube 去做，这个协议不再关注这些事情。

### insert 等操作的是不是还得有 "加载更多" 属性

是的，肯定要指明更新后是否还需要加载更多等属性

### suspension 可以直接写到  渲染view 上吗，还是要写在 layout 层

### 为什么没有事件相关的属性

因为事件交由 cube 来做，这也就意味着，要做一层 cube 事件转发到 这层协议 的动作；

# 思路

## 关于 tab 切换

暴露 tab 切换的接口给业务方，业务方通过 tab 切换监听，拿到 tab 相关信息， 请求接口。获取数据后，通过回调让 fragment 部分开始进行解析渲染

整个 tabLayout  都是 cube 渲染，那么如何让 tabLayout 和 viewPager 进行联动呢。  viewPager 可以监听 tab 切换（基于 cube 事件），然后进行切换。

tab 如何监听 viewPager 的切换呢？再通过反向监听？？再通过   native 调用 cube 机制，更改选中 tab 


## 关与  粘性布局的实现  

可能会采用   recycler view  layoutManager 来实现

## 下拉刷新 和 加载更多 的实现方案





