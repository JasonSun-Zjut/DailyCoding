/**
 * @author Jason
 * @date /11/02
 */

/**
 *static方法内部不能调用非静态方法，反过来是可以的，而且在没有
 * 创建任何对象的前提下，仅仅通过类本身来调用static方法。
 *被static修饰的变量或者方法不需要依赖对象来进行访问，可以通过类名去访问
 * */
public class StaticDemo {
    private int num=10;
    //静态变量能被所有实例对象所共享，当且仅当类在加载时被初始化
    private static int times;
    //静态代码块用来初始化静态变量
    static {
        times=0;
    }
    //静态方法不能访问非静态成员方法和成员变量
    //构造方法也是静态方法
    public StaticDemo(int num) {
        this.num = num;
    }

    public static void print()
  {
      System.out.println("hello World");
  }

    public static void main(String[] args) {
        StaticDemo.print();
    }
}
