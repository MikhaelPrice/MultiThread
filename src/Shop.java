import java.util.ArrayDeque;
import java.util.Queue;

public class Shop extends Thread implements Runnable, IBuyer, IUseBasket {

    int buyerNumber;
    static int countBuyer = 0;
    static String[] goods = {"bread for 1.5p", "butter", "salt", "sugar", "meat", "milk", "sweets", "chocolate", "pasta", "eggs", "wine", "cigarettes"};
    static String[] prices = {"1.5p", "2.4p", "0.7p", "1p", "10p", "2.3p", "4p", "3p", "2.7p", "2.9p", "28p", "3.35p"};
    static boolean pensioner;

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
        System.out.println(this + "has entered the shop");
    }

    @Override
    public void takeBacket() {
        int time = Rnd(100, 200);
        pensioner = countBuyer % 4 == 0;
        try {
            if (pensioner) {
                time *= 1.5;
            }
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.err.println(this + "//некорректное время ожидания");
        }
        System.out.println(this + "has taken a basket");
    }

    @Override
    public void chooseGoods() {
        int choosing = Rnd(0.5, 2);
        String[] products = new String[goods.length];
        StringBuilder choice = new StringBuilder();
        for (int i = 0; i < goods.length; i++) {
            products[i] = goods[i] + " for " + prices[i];
        }
        int count = Rnd(1, 4);
        pensioner = countBuyer % 4 == 0;
        try {
            if (pensioner) {
                choosing *= 1.5;
            }
            for (int i = 0; i < count; i++) {
                choice.append(products[Rnd(0, products.length - 1)]).append(" ");
            }
            Thread.sleep(choosing);
        } catch (InterruptedException e) {
            System.err.println(this + "//некорректное время ожидания");
        }
        System.out.println(this + "has chosen a good " + choice);
    }

    @Override
    public void putGoodsToBacket() {
        int putting = Rnd(100, 200);
        pensioner = countBuyer % 4 == 0;
        try {
            if (pensioner) {
                putting *= 1.5;
            }
            Thread.sleep(putting);
        } catch (InterruptedException e) {
            System.err.println(this + "//некорректное время ожидания");
        }
        System.out.println(this + "has put a good into basket");
    }

    @Override
    public void goOut() {
        System.out.println(this + "has gone out");
    }

    @Override
    public void run() {
        enterToMarket();
        takeBacket();
        chooseGoods();
        putGoodsToBacket();
        goOut();
    }

    public static int Rnd(double from, double to) {
        return (int) (Math.random() * (to - from + 1) + from);
    }

    public static void main(String[] args) throws InterruptedException {
        Queue<Shop> queue = new ArrayDeque<>();
        int time = 0, minute = 0, buyerInMinute = 0;
        while (time < 120) {
            Thread.sleep(1000);
            int count = Rnd(0, 2);
            for (int i = 0; i < count; i++) {
                if ((time - minute) / 30 < 1) {
                    if (countBuyer - buyerInMinute < 10) {
                        countBuyer++;
                        Shop buyer = new Shop(countBuyer);
                        queue.add(buyer);
                    }
                } else {
                    if (countBuyer - buyerInMinute < 40) {
                        countBuyer++;
                        Shop buyer = new Shop(countBuyer);
                        queue.add(buyer);
                    }
                }
            }
            if (time == 60) {
                minute = time;
                buyerInMinute = countBuyer;
            }
            time++;
        }
    }
}
