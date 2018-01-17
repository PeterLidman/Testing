
public class TestSkåp {

	public static void main(String[] args) {
		Skåp s = new Skåp.Builder()//
				.set_glas(3)//
				.build();
		System.out.println("mitt skåp" + s.toString());

	}
}
