package Trådar;

public class Trådtest extends Thread {
	private int _start;
	private int _stopp;

	public Trådtest(int start, int stopp) {
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
		Trådtest t1 = new Trådtest(101, 105);
		Trådtest t2 = new Trådtest(10, 12);
		Trådtest t3 = new Trådtest(2001, 2005);
		t1.start();
		t2.start();
		t3.start();

	}
}
