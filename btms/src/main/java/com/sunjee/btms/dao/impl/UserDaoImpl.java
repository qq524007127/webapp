package com.sunjee.btms.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sunjee.btms.dao.UserDao;
import com.sunjee.component.bean.Module;
import com.sunjee.component.bean.User;

@Repository("userDao")
public class UserDaoImpl extends SupportDaoImpl<User> implements UserDao {

	private static final long serialVersionUID = -145776372827483926L;
	
	/**
	 * 修改用户密码
	 */
	@Override
	public void changePassword(String userId, String newPassword) {
		String hql = "update User set password = :password where userId = :userId";
		Map<String, Object> whereParams = new HashMap<String, Object>();
		whereParams.put("password", newPassword);
		whereParams.put("userId", userId);
		createQuery(null,hql, whereParams).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Module> getModulesOfUser(User user) {
		Map<String, Object> whereParams = new HashMap<>();
		whereParams.put("userId", user.getUserId());
		String hql = "select distinct root from User u join u.roleSet r join r.modSet m join m.parentModule root"
				+ " where u.userId=:userId and m.permit = true order by root.moduleSort";
		List<Module> roots = createQuery(null, hql, whereParams).list();
		for(Module root : roots){
			root.setChildSet(new HashSet<Module>());
		}
		hql = "select distinct m from User u join u.roleSet r join r.modSet m where u.userId=:userId and m.permit = true order by m.moduleSort";
		List<Module> children = createQuery(null, hql, whereParams).list();
		for(Module root : roots){
			for(Module m : children){
				Module parent = m.getParentModule();
				if(parent != null && parent.getModuleId().equals(root.getModuleId())){
					root.getChildSet().add(m);
				}
			}
		}
		return roots;
	}
}
