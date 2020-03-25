public class MeaningOfThis {                                //5가 나옴. 익명클래스에 익숙해지기 위한 문제. this는 runnable을 참조하기에 5가나옴

    public final int value = 4;

    public void doIt() {
        int value = 6;
        Runnable r = new Runnable() {
            public final int value = 5;
            @Override
            public void run() {
                int value = 10;
                System.out.println(this.value);
            }
        };
        r.run();
    }

    public static void main(String... args) {
        MeaningOfThis m = new MeaningOfThis();
        m.doIt(); // ???
    }

}
