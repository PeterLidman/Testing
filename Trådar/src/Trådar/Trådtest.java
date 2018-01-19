package Tr�dar;

public class Tr�dtest extends Thread {
	private int _start;
	private int _stopp;

	public Tr�dtest(int start, int stopp) {
		_start = start;
		_stopp = stopp;
	}

	public void run() {
		int summa = 0;
		for (int i = _start; i <= _stopp; i++) {
			summa = i + summa;
			System.out.println("sum=" + summa);
		}
	}

	public static void main(String[] args) {
		Tr�dtest t1 = new Tr�dtest(101, 105);
		Tr�dtest t2 = new Tr�dtest(10, 12);
		Tr�dtest t3 = new Tr�dtest(2001, 2005);
		t1.start();
		t2.start();
		t3.start();

	}
}
