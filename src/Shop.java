import java.util.ArrayDeque;
import java.util.Queue;

public class Shop extends Thread implements Runnable, IBuyer,IUseBasket {

    int buyerNumber;

    public void setBuyerNumber(int buyerNumber) {
        this.buyerNumber = buyerNumber;
    }

    public Shop(int buyerNumber) {
        this.buyerNumber = buyerNumber;
        this.setName("Buyer number " + buyerNumber + " ");
        start();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public void enterToMarket() {
        System.out.println(this + "entered the shop");
    }

    @Override
    public void takeBacket() {

    }

    @Override
    public void chooseGoods() {
        int choosing = Rnd(0.5, 2);
        try {
            Thread.sleep(choosing);
        } catch (InterruptedException e) {
            System.err.println(this + "//некорректное время ожидания");
        }
        System.out.println(this + "has chosen the good");
    }

    @Override
    public void putGoodsToBacket() {

    }

    @Override
    public void goOut() {
        System.out.println(this + "has gone out");
    }

    @Override
    public void run() {
        enterToMarket();
        chooseGoods();
        goOut();
    }

    public static int Rnd(double from, double to) {
        return (int) (Math.random() * (to - from) + from);
    }

    static int countBuyer = 0;

    public static void main(String[] args) throws InterruptedException {
        Queue<Shop> queue = new ArrayDeque<>();
        int time = 0;
        while (time < 120) {
            Thread.sleep(1000);
            int count = Rnd(0, 2);
            for (int i = 0; i <= count; i++) {
                countBuyer++;
                Thread.yield();
                Shop buyer = new Shop(countBuyer);
                queue.add(buyer);
            }
            time++;
        }
    }
}
