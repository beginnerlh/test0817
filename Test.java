package thread.produce;

/**
 * 商品类
 */

class Goods{
    private String goodsName;
    private int count;

    public synchronized void set(String goodsName) throws InterruptedException {

        if(this.count > 0){
            System.out.println("还有库存，等待消费者..");
            wait();
        }

        this.goodsName = goodsName;
        this.count = count+1;
        Thread.sleep(1000);
        System.out.println("生产"+toString());
        notify();
    }
    //消费方法

    public synchronized void get () throws InterruptedException {
        if(this.count == 0){
            System.out.println("商品卖完了，客观等等哦");
            wait();
        }
        this.count = this.count-1;
        Thread.sleep(1000);
        System.out.println("消费"+toString());
        notify();
    }
    @Override
    public String toString(){
        return "Goods [goodsName = "+goodsName+",库存为"+count+"件]";
    }
}

/**
 * 生产者
 */

class Producer implements Runnable{
    private Goods goods;
    public Producer(Goods goods){
        super();
        this.goods = goods;
    }

    @Override
    public void run() {
        try {
            this.goods.set("奔驰车一辆");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

/**
 * 消费者
 */
class Consumer implements Runnable{
    private Goods goods;

    public Consumer(Goods goods){
        super();
        this.goods = goods;
    }
    @Override
    public void run() {
        try {
            this.goods.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Test {
    public static void main(String[] args) {
        Goods goods = new Goods();
        Thread produceThread = new Thread(new Producer(goods));
        Thread consumerThread = new Thread(new Consumer(goods));
        consumerThread.start();
        produceThread.start();
    }
}
