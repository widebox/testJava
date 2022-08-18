package one.widebox.showcase.services;

import java.util.List;

import one.widebox.showcase.entities.User;

public interface UserService {

	void saveOrUpdate(User user);

	User findUser(Long id);

	List<User> listUser();

	boolean deleteUser(Long id);

	User findUserByUsername(String username);

	boolean isUsernameRepetead(User user);

	void resetPassword(Long userId, String newPassword);

}
