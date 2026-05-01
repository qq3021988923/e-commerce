package 专业技能面试实战指南.技能1;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedListDemo {
    public static void main(String[] args) {

        // ====== 测试1：随机访问性能（ArrayList胜出）======
//        System.out.println("===== 测试1：随机访问10万次 =====");
//
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();
//
//        // 先填充10万个元素
//        for (int i = 0; i < 100000; i++) {
//            arrayList.add("元素" + i);
//            linkedList.add("元素" + i);
//        }
//        // 测试LinkedList随机访问
//        long start2 = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            linkedList.get(i);  // O(n) 每次都要从头遍历
//        }
//        long end2 = System.currentTimeMillis();
//        System.out.println("LinkedList随机访问耗时: " + (end2 - start2) + "ms");
//
//        // 测试ArrayList随机访问
//        long start1 = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            arrayList.get(i);  // O(1) 直接通过下标访问
//        }
//        long end1 = System.currentTimeMillis();
//        System.out.println("ArrayList随机访问耗时: " + (end1 - start1) + "ms");

//        long start1 = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            arrayList.get(i);  // O(1) 直接通过下标访问
//        }
//        long end1 = System.currentTimeMillis();
//        System.out.println("ArrayList随机访问耗



        // ====== 测试2：中间插入性能（LinkedList胜出）======
        System.out.println("\n===== 测试2：中间位置插入1万次 =====");

        long start1 = System.currentTimeMillis();
        long end1 = System.currentTimeMillis();
        long end2 = System.currentTimeMillis();
        long start2 = System.currentTimeMillis();

        // 重置两个List
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
        for (int i = 0; i < 10000; i++) {
            arrayList.add("元素" + i);
            linkedList.add("元素" + i);
        }


        // 测试LinkedList在位置5000处插入
        start2 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            linkedList.add(5000, "新元素");  // O(1) 只需修改指针
            linkedList.remove(5000);
        }
        end2 = System.currentTimeMillis();
        System.out.println("LinkedList中间插入耗时: " + (end2 - start2) + "ms");

        // 测试ArrayList在位置5000处插入
        start1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            arrayList.add(5000, "新元素");  // O(n) 后续元素都要后移
            arrayList.remove(5000);          // 刚插入的删除掉
        }
        end1 = System.currentTimeMillis();
       System.out.println("ArrayList中间插入耗时: " + (end1 - start1) + "ms");

    }
}
