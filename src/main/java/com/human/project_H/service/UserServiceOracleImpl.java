package com.human.project_H.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.project_H.Dao.UserDao;
import com.human.project_H.entity.User;
import com.human.project_H.entity.UserByMonth;

@Service
public class UserServiceOracleImpl implements UserService {
	@Autowired private UserDao userDao;

	@Override
	public int getUserCount() {
		int count = userDao.getUserCount();
		return count;
	}

	@Override
	public User getUser(String custId) {
		
		return userDao.getUser(custId);
	}

	@Override
	public List<User> getUserList(int page) {
		int offset = (page - 1) * RECORDS_PER_PAGE;
		int limit = page * RECORDS_PER_PAGE;
		List<User> list = userDao.getUserList(offset, limit);
		return list;
	}

	@Override
	public void insertUser(User user) {
		userDao.insertUser(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public void deleteUser(String custId) {
		userDao.deleteUser(custId);
	}


	@Override
	public List<Integer> getPageList() {
		return null;
	}
	@Override
    public int login(String custId, String pwd) {
        User user = userDao.getUser(custId);
        if (user == null)
            return CUSTID_NOT_EXIST;
        if (BCrypt.checkpw(pwd, user.getPwd()))
            if (user.getIsDeleted() == 0)
                return CORRECT_LOGIN;
            else
                return ISDELETED;
        else
            return WRONG_PASSWORD;
    }
	
	@Override
    public boolean isUserDeleted(String custId) {

        return false;
    }
	
	// Admin page 통계용
		@Override
		public List<UserByMonth> getNumberOfUser() {
			List<UserByMonth> monthList = userDao.getNumberOfUser();
			return monthList;
		}

		@Override
		public int[] getSocialCount() {
			int kakaoUsers = userDao.getSocialCount(0, "%kakao%");
			int naverUsers = userDao.getSocialCount(0, "%naver%");
			int leaveUsers = userDao.getSocialCount(1, "%%");
			int[] intArr = {kakaoUsers, naverUsers, leaveUsers};
			return intArr;
		}

		@Override
		public List<UserByMonth> leaveNumberOfUser() {
			List<UserByMonth> list = userDao.leaveNumberOfUser();
			return list;
		}
		
		@Override
		public int getSearchCount(String query) {
			query = "%" + query + "%";
			int count = userDao.getSearchCount(query);
			return count;
		}

		@Override
		public List<User> getSearchList(String query, int page) {
			query = "%" + query + "%";
			int offset = (page - 1) * RECORDS_PER_PAGE;
			int limit = page * RECORDS_PER_PAGE;
			List<User> list = userDao.getSearchList(offset, limit, query);
			return list;
		}
	
}