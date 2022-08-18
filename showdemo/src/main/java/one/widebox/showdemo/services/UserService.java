package one.widebox.showdemo.services;

import java.util.List;

import one.widebox.showdemo.entities.User;

public interface UserService {

	void saveOrUpdate(User user);

	User findUser(Long id);

	List<User> listUser();

	boolean deleteUser(Long id);

	User findUserByUsername(String username);

	boolean isUsernameRepetead(User user);

	void resetPassword(Long userId, String newPassword);

}
