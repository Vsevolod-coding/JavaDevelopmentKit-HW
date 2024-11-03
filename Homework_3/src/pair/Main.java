package pair;

public class Main {
    public static void main(String[] args) {
        Pair<String, Double> box = new Pair<>("Pi", 3.141592);
        System.out.println(box.getFirst());
        System.out.println(box.getSecond());
        System.out.println(box);
    }
}
