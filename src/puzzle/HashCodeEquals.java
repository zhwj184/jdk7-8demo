package puzzle;

import java.util.HashSet;
import java.util.Set;

public class HashCodeEquals {
	public static void main(String[] args) {
		Set<Name> s = new HashSet<Name>();
		s.add(new Name("aaa", "bbb"));
		System.out.println(s.contains(new Name("aaa","bbb")));
	}

}

class Name {
	private String firstName;
	private String LastName;
	public Name(String firstName,String lastName){
		this.firstName = firstName;
		this.LastName = lastName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Name)) {
			return false;
		}
		return ((Name)obj).getFirstName().equals(this.getFirstName()) && ((Name)obj).getLastName().equals(this.getLastName());
	}
	
	@Override
	public int hashCode() {
		return 31 * this.getFirstName().hashCode() + this.getLastName().hashCode();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}
}
