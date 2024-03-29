/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package de.buw.se4de;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import de.buw.se4de.Habit.Category;
import de.buw.se4de.Habit.Cycle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Vector;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppTest {
	@Test
	@Order(1)
	void DBReaderCanCreateUserAndHabits() {
		DBReader dbr = new DBReader("jdbc:h2:./src/main/resources/h2User");
        dbr.InitializeDB();
        Vector<User> uservec = new Vector<>();
        dbr.getusers(uservec);

		assertTrue(dbr.connected, "Database should be connected");

		dbr.insertuser("TestUser");
		dbr.getusers(uservec);
		boolean user_added = false;
		User test_User = null;
		for (User user : uservec) {
			if (user.username.equals("TestUser")) {
				test_User = user;
				user_added = true;
			}
		}
		assertTrue(user_added, "User should have been added");

		Habit h1 = new Habit("TestHabit1", "Test description 1", Category.Meditation, Cycle.FOUR_PER_WEEK);
		Habit h2 = new Habit("TestHabit2", "Test description 2", Category.Sport, Cycle.FIVE_PER_WEEK);
		Habit h3 = new Habit("TestHabit3", "Test description 3", Category.Kunst, Cycle.ONE_PER_WEEK);

		dbr.inserthabit(h1, test_User);
		dbr.inserthabit(h2, test_User);
		dbr.inserthabit(h3, test_User);

		Vector<Habit> habits = dbr.gethabits(test_User);

		boolean h1_found = false;
		boolean h2_found = false;
		boolean h3_found = false;
		for (Habit habit : habits) {
			if (h1.name.equals(habit.name) && h1.description.equals(habit.description)) {
				h1_found = true;
			}
			if (h2.name.equals(habit.name) && h2.description.equals(habit.description)) {
				h2_found = true;
			}
			if (h3.name.equals(habit.name) && h3.description.equals(habit.description)) {
				h3_found = true;
			}
		}

		assertTrue(h1_found, "First Test habit should have been created");
		assertTrue(h2_found, "Second Test habit should have been created");
		assertTrue(h3_found, "Third Test habit should have been created");
	}

	@Test
	@Order(2)
	void DBReaderCanMarkHabits() {
		DBReader dbr = new DBReader("jdbc:h2:./src/main/resources/h2User");
        dbr.InitializeDB();
        Vector<User> uservec = new Vector<>();
        dbr.getusers(uservec);

		User testUser = null;
		for (User user : uservec) {
			if (user.username.equals("TestUser")) {
				testUser = user;
			}
		}

		// hat 3 Elemente von der vorheriegen Funktion
		Vector<Habit> habits = dbr.gethabits(testUser);

		dbr.trackhabit(habits.get(0), new Date());
		// Gestern
		dbr.trackhabit(habits.get(1), new Date(System.currentTimeMillis()-24*60*60*1000));
		dbr.trackhabit(habits.get(2), new Date());

		assertTrue(dbr.donetoday(habits.get(0)), "First habit should have been completed today");
		assertTrue(dbr.donetoday(habits.get(2)), "Third habit should have been completed today");

		boolean is_yesterday = false;
		// bissl hacky
		Vector<Date> dates = dbr.getdates(habits.get(1));
		if ((dates.get(0).getTime() <= (System.currentTimeMillis()-24*60*60*1000)) 
		 && (dates.get(0).getTime() >= System.currentTimeMillis()-24*60*60*1000*2)) {
			is_yesterday = true;
		}
		assertTrue(is_yesterday, "Second habit should have been completed yesterday");

	}

	@Test
	@Order(3)
	void DBReaderCanDeleteUserAndHabits() {
		DBReader dbr = new DBReader("jdbc:h2:./src/main/resources/h2User");
        dbr.InitializeDB();
        Vector<User> uservec = new Vector<>();
        dbr.getusers(uservec);

		assertTrue(dbr.connected, "Database should be connected");

		User testUser = null;
		for (User user : uservec) {
			if (user.username.equals("TestUser")) {
				testUser = user;
			}
		}

		Vector<Habit> habits = dbr.gethabits(testUser);
		for (Habit habit : habits) {
			dbr.deletehabit(habit);
		}
		habits = dbr.gethabits(testUser);

		assertTrue(habits.isEmpty(), "Habits should be empty after deletion");

		dbr.deleteuser(testUser);
		uservec.remove(testUser);
		dbr.getusers(uservec);
		boolean user_deleted = true;
		for (User user : uservec) {
			if (user.username.equals("TestUser")) {
				user_deleted = false;
			}
		}
		// User ist aus der Datenbank entfernt, aber nicht aus dem User Vector
		assertTrue(user_deleted, "User should have been deleted");
	}
}
