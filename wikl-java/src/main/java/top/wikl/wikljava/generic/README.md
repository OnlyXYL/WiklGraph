### 泛型

####  泛型类 [泛型类](top.wikl.wikljava.generic.model.Pair)

#### 泛型方法 [泛型方法](top.wikl.wikljava.generic.ArrayAlg.getMiddle)

 * 类型变量放在修饰符的后面，返回类型的前面
  
 * 编译器对于泛型方法调用最最终推断出哪种类型：各个参数类的共同超类

#### 类型变量的限定 [类型变量的限定](top.wikl.wikljava.generic.ArrayAlg.min)
 
#### 泛型代码和虚拟机

`虚拟机没有泛型类型对象—— 所有对象都属于普通类`
  
> 类型擦除
  
  ##### 无论何时定义一个泛型类型，都自动提供了一个相应的原始类型。原始类型的名字就是删去类型参数后的泛型类型名。擦除类型变量，并替换为限定类型（无限定类型的变量用 Object ）
  
 ``` 

   原始类型用第一个限定的类型变量来替换，如果没有给定限定类型就用 Object 替换，例如：
    
       public class Interval<T extends Comparable & Serializable> implements Serializable {
      
           private T lower;
           
           private T upper;
   
           public Interval(T first,T second){
               if(first.compareTo(second) <= 0){
                   lower = first;
                   upper = second;
               }else{
                   lower = second;
                   upper = first;
               }
           }
       
       }
       
       原始类型 Interval 如下所示：
       
       public calss Interval implements Serializable {
           
           private Comparable lower;
           
           private Comparable upper;
           
           public Inteval(Comparable first, Comparable second) {. . . }
       }
       
      
      注意
      
       思考：切换限定  calss Interval<T extends Serializable & Comparable> 会发生什么。
   
       如果这样做，原始类型用 Serializable 替换 T ， 而编译器在必要的时候要向 Comparable 插入强制类型转换。为了提高效率，应该将标签接口（即没有方法的接口）放在边界列表的末尾。
   
 ```
   

> 翻译泛型表达式
  
  - 当程序调用泛型方法时，如果擦除返回类型，编译器插入强制类型转换。例如，下面这个语句序列：
  
 ````
     Pair<Employee> buddies = ...;
     
     Employee buddy = buddies.getFirst();
 ```` 

擦除 getFirst 的返回类型后将返回 Object 类型。编译器自动插入 Employee 的强制类型转换。也就是说，编译器把这个方法的调用翻译为两条虚拟机指令：

 * 对原始方法 Pair.getFirst 的调用

 * 将返回的 Object 类型强制转换为 Employee 类型
 
 ---
 

> 翻译泛型方法

``` 
public class DateInterval extends Pair<LocalDate> {

    @Override
    public void setSecond(LocalDate second){

        if (second.compareTo(getFirst()) >=0) {
            super.setSecond(second);
        }

    }
}

类型擦除后变成：
public class DateInterval extends Pair {

    @Override
    public void setSecond(LocalDate second){
        . . . 
    }
}

存在另外一个从 Pair 继承的 setSecond 方法，即：

public void setSecond(Object second)

这显然是一个不同的方法，因为参数类型不一样。然而不应该不一样。考虑下面的语句序列：

    DateInterval dateInterval = new DateInterval();

    Pair<LocalDate> pair = dateInterval;

    pair.setSecond(LocalDate.now());
    
这里，希望对 setSecond 的调用具有多态性，并调用最合适的那个方法。由于 Pair 引用 DateInterval 对象，所以应该调用 DateInterval.setSecond。问题在于类型擦除与多态性发生了冲突。要解决这个问题，就需要编译器在 DateInterval 类中生成一个`桥方法`

public void setSecond(Object second) {
    setSecond((Date)second);
}

工作过程：

pair.setSecond(LocalDate.now());

变量 pair 已经声明为类型 Pair<LocalDate>，并且这个类型只有一个简单的方法叫 setSecond，即 setSecond(Object)。虚拟机用 pair 引用的对象调用这个方法。这个对象是 DateInterval 类型的，因而将会调用 DateInterval.setSecond(Object) 方法。这个方法是合成的桥方法。它调用 DateInterval.setSecond(Date)，这正是我们所期望的操作效果。

```

> java 泛型转换的事实：

* 虚拟机中没有泛型，只有普通的类和方法。

* 所有的类型参数都用它们的限定类型替换。

* 桥方法被合成来保持多台。

* 为了保持类型安全性，必要时插入强制类型转换。
  
#### 约束和局限性

* 不能用基本类型实例化类型参数

* [运行时类型查询只适用于原始类型](top.wikl.wikljava.generic.GenericLimit.noRunTinmeQuery)

* [不能创建参数化类型额数组](top.wikl.wikljava.generic.GenericLimit.notArray)

* 不能实例化类型变量

* 不能构造泛型数组

