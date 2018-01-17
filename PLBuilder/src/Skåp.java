
public class Skåp {

	int _koppar;
	int _glas;
	int _tallrikar;

	private Skåp(Builder b) {
		_koppar = b._koppar;
		_glas = b._glas;
		_tallrikar = b._tallrikar;
	}

	public int get_koppar() {
		return _koppar;
	}

	public int get_glas() {
		return _glas;
	}

	public int get_tallrikar() {
		return _tallrikar;
	}

	@Override
	public String toString() {
		return "Skåp [_koppar=" + _koppar + ", _glas=" + _glas + ", _tallrikar=" + _tallrikar + "]";
	}

	public static class Builder {
		int _koppar=242;
		int _glas=242;
		int _tallrikar=242;

		public Builder set_koppar(int _koppar) {
			this._koppar = _koppar;
			return this;
		}

		public Builder set_glas(int _glas) {
			this._glas = _glas;
			return this;
		}

		public Builder set_tallrikar(int _tallrikar) {
			this._tallrikar = _tallrikar;
			return this;
		}

		public Skåp build() {
			return new Skåp(this);
		}
	}
}
