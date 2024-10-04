package fr.fms.business;

import java.util.ArrayList;

import fr.fms.entities.Account;
import fr.fms.entities.CurrentAccount;
import fr.fms.entities.SavingAccount;

public class IBankImpl implements IBank {

	private ArrayList<Account> listOfAccounts;

	// Constructeur
	public IBankImpl() {
		this.listOfAccounts = new ArrayList<>();
	}

	// Ajouter un compte
	public void addAccount(Account account) {
		listOfAccounts.add(account);
	}

	// Déposer de l'argent sur un compte
	public void deposit(int accountId, double amount, boolean transfer) {
		boolean foundAcc = false;

		for (Account account : listOfAccounts) {
			if (account.getIdAccount() == accountId) {
				account.setBalanceAccount(account.getBalanceAccount() + amount);
				if (!transfer)
					System.out.printf("Versement de %,d€ sur le compte n° %d%n", (int) amount, accountId);
				foundAcc = true;
			}
		}
		if (!foundAcc)
			System.out.println("Compte non trouvé !");
	}

	public boolean checkIfCanBeDebited(Account account, double amount) {

		if (account instanceof CurrentAccount) {
			CurrentAccount currentAcc = (CurrentAccount) account;
			if (currentAcc.getBalanceAccount() - amount >= -currentAcc.getAuthorizedOverdraft()) {
				return true;
			}
		} else if (account instanceof SavingAccount) {
			SavingAccount saveAcc = (SavingAccount) account;
			if (saveAcc.getBalanceAccount() - amount > 0) {
				return true;
			}
		}
		return false;
	}

	// Retirer de l'argent d'un compte
	public void withdraw(int accountId, double amount, boolean transfer) {
		boolean foundAcc = false;

		for (Account account : listOfAccounts) {
			if (account.getIdAccount() == accountId) {
				if (checkIfCanBeDebited(account, amount)) {
					account.setBalanceAccount(account.getBalanceAccount() - amount);
					if (!transfer)
						System.out.printf("Retrait de %,d€ sur le compte n° %d %n", (int) amount, accountId);
					foundAcc = true;
				} else {
					System.out.println("Fonds insuffisants !");
					foundAcc = true;
				}
			}
		}

		if (!foundAcc)
			System.out.println("Compte non trouvé !");
	}

	// Transférer de l'argent entre deux comptes
	public void transferMoney(int startAccountId, int destinationAccountId, double amount) {

		Account startAcc = null;
		Account destAcc = null;

		for (Account account : listOfAccounts) {
			if (account.getIdAccount() == startAccountId)
				startAcc = account;
			else if (account.getIdAccount() == destinationAccountId)
				destAcc = account;
		}

		if (startAcc.getIdAccount() == destAcc.getIdAccount()) {
			System.out.println("Vous ne pouvez pas retier et verser sur le même compte.");
		} else {
			if (this.checkIfCanBeDebited(startAcc, amount)) {
				this.withdraw(startAcc.getIdAccount(), amount, true);
				this.deposit(destAcc.getIdAccount(), amount, true);
				System.out.printf("Transfer de %,d€ effectué de %s à %s. %n", (int) amount,
						startAcc.getUser().getFirstnameUser(), destAcc.getUser().getFirstnameUser());
			} else {
				System.out.println("Fonds insuffisants pour le transfer !");
			}
		}
	}

	// Consulter le solde d'un compte
	public void consultBalanceAccount(int accountId) {

		for (Account acc : listOfAccounts) {
			if (acc.getIdAccount() == accountId) {
				System.out.printf("solde de %s : %,d€%n", acc.getUser().getFirstnameUser(),
						(int) acc.getBalanceAccount());
			}
		}
	}

	// Lister tous les comptes
	public void listAccounts(String email) {
		boolean firstMatch = true;
		for (Account acc : listOfAccounts) {
			if (acc.getUser().getMailUser().equalsIgnoreCase(email)) {
				if (firstMatch) {
					System.out.printf(
							"%n----------------------List de tout les comptes de %s----------------------------%n",
							acc.getUser().getFirstnameUser());
					firstMatch = false;
				}
				System.out.println(acc);
			}
		}
	}
}
