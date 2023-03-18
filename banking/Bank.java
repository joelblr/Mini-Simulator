package banking;

import java.util.*;


public interface Bank<B> {


	void deleteAccount(HashMap<String, B> asd);


	void signOutAccount();


	void loanInterest();


	void amountBalance();


	void amountDeposit();


	void amountWithdraw();


}