package one.widebox.showcase.services;

import java.util.Arrays;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.criterion.Restrictions;

import one.widebox.foggyland.tapestry5.hibernate.services.Dao;
import one.widebox.showcase.entities.User;

public class UserServiceImpl implements UserService {

	@Inject
	private Dao dao;
	@Inject
	private PasswordService passwordService;

	@Override
	public void saveOrUpdate(User user) {
		if (user.getId() == null) {
			String digest = passwordService.digest(user.getPassword());
			user.setDigest(digest);
		}
		dao.saveOrUpdate(user);
	}

	@Override
	public User findUser(Long id) {
		return dao.findById(User.class, id);
	}

	@Override
	public List<User> listUser() {
		return dao.list(User.class);
	}

	@Override
	public boolean deleteUser(Long id) {
		return dao.delete(User.class, id);
	}

	@Override
	public User findUserByUsername(String username) {
		return dao.find(User.class, Arrays.asList(Restrictions.eq("username", username)));
	}

	@Override
	public boolean isUsernameRepetead(User user) {
		return dao.isPropertyValueDuplicated(User.class, user.getId(), "username", user.getUsername());
	}

	@Override
	public void resetPassword(Long userId, String newPassword) {
		User user = findUser(userId);
		if (user.getId() != null) {
			user.setDigest(passwordService.digest(newPassword));
			dao.saveOrUpdate(user);
		}
	}

}
