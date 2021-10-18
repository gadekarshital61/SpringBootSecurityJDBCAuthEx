package in.nit.raghu;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
   public static void main(String []args) {
	   BCryptPasswordEncoder enc=new BCryptPasswordEncoder();
	   String ep=enc.encode("ajay");
	   System.out.println(ep);
   }
}
