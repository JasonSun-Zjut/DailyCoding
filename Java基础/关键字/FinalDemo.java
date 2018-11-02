/**
 * @author Jason
 * @date /11/02
 */

/**
 * final能够修饰变量，方法和类。同时变量又可以分为成员变量和局部变量。
 * 成员变量分为：类变量和实例变量
 * final修饰方法时，子类无法重写父类final修饰的方法
 * final修饰类时，表示该类不能被子类继承
 */
public class FinalDemo {
    //对于基本类型的变量，final修饰表示只能赋值一次。
    private final int val = 0;
    //final修饰引用类型
    private final Person person=new Person(23,140);
    //对于局部变量，同样只能赋值一次
    public void localfinal() {
        final int b;
        b = 10;
    }
    //对final修饰的引用类型对象，可以改变引用类型对象的属性值,但是无法改变final变量的引用
    public void changePerson(int age,int height)
    {
        person.age=age;
        person.height=height;
    }
    static class Person{
        private int age;
        private int height;

        public Person(int age, int height) {
            this.age = age;
            this.height = height;
        }
    }

}
