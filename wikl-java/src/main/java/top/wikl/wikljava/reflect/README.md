#### Reflect

* Java 运行时系统始终为所有对象维护一个被称为运行时的类型标识。这个信息跟踪这每个对象的所属类，java 中保存这些信息的类被称为 Class.

> 反射机制可以用来：

* 在运行时分析类 [ReflectTest](top.wikl.wikljava.reflect.ReflectTest)

* 在运行时查看对象 [Demo2](top.wikl.wikljava.reflect.Demo2) , 例如，编写一个 toString 方法供所有类使用

* 实现通用的数组操作

* 利用 Method 对象


> 获得 Class 类对象的三种方法

* Class aClass = T.getClass();

* Class aClass = Class.forName("java.util.Random") //全路径

* Class aClass = T.class;


> 注意

* 一个 Class 对象实际上表示的是一个类型，而这个类型未必一定是一种类。例如，int 不是类，int.Class 是一个 Class 类型的对象

* 反射机制默认行为受限于 java 的访问权限。使用 AccessibleObject.setAccessible() 方法，可以免除 java 安全机制的管理